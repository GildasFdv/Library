# Introduction

Commençons par un rappel de l'énoncé ci-dessous, dans un ordres modifiés et avec des titres pour structurer notre reflexion:

## Les fonctionnalités

### L'utilisateur doit pouvoir réaliser les opérations suivantes:
- Se connecter en fournissant son identifiant
- Lister les livres disponibles
  - Output: Liste des titres de livres ainsi que leurs ISBNs
- Voir les détails d'un livre
  - Input: l'ISBN
  - Output: La totalité des informations sur le livre (ISBN, titre, auteur, description, nombre d'exemplaires disponibles)
- Louer un livre
  - Input: l'ISBN
  - Output: Message de confirmation
- Rendre un livre
  - Input: l'ISBN
  - Output: Message de confirmation
- Exporter le catalogue (pour générer un fichier JSON contenant le catalogue des livres actuellement disponibles dans l'application)
  - Input: nom et chemin de fichier
  - Output: Message de confirmation

## Les modèles et leurs contraintes

### Un livre a: 
  - Un numéro ISBN unique
  - Un titre
  - Une description
  - Un nom d'auteur
  - Un prix
  
### Un utilisateur est défini par:
  - son identifiant
  - Chaque utilisateur peut louer un livre
  - Le même utilisateur ne peut pas louer plus que trois livres
  - Le même utilisateur ne peut pas louer plus qu'un seul exemplaire du même livre

## Analyse du sujet

Nous avons donc la description des fonctionnalités qui nous font directement penser aux menus que nous allons avoir. Etant donné qu'il y a une connexion par identifiant possible, nous aurons deux menus, un non-connecté et un autre pour un utilisateur connecté.

### Menu non-connecté:
![image](https://github.com/user-attachments/assets/1935a788-361f-4028-9845-ffd037f372a7)

### Menu connecté:
![image](https://github.com/user-attachments/assets/6b49c283-1b8a-4c08-af36-b27f93c3c1d4)

Maintenant techniquement, nous avons une vue et des modèles. Plus qu'à ajouter un controller pour gérer les interactions entre ces deux et nous obtenons la structure de notre programme.

# Mise en oeuvre

## Utilisation d'interfaces

Je choisi d'utiliser des interfaces pour décrire les dépendances entre les différents composants de mon application. Prenons l'exemple de la '**Library**', qui est l'objet qui renferme toute la logique de notre librairie. Elle implémente l'interface '**ILibrary**' dont le code est ci-dessous. Si nous voulions changer notre système de sauvegarde, ne plus passer par JSON mais par du SQL par exemple, il n'y aurait qu'à implémenter cette interface avec la nouvelle implémentation de librairie qui pourrait s'appeler '**LibrarySQL**' par exemple.

```
public interface ILibrary {

    void loadDatabase() throws IOException;

    void saveDatabase() throws IOException;

    boolean addUser(IUser user);

    IUser getUser(String id);

    boolean rentBook(IUser user, String ISBN) throws NoBookFound;

    boolean returnBook(IUser user, String ISBN) throws NoBookFound;

    CustomHashSet<IBook> getAvailableBooks();

    IBook getBook(String ISBN);

    void exportCatalog(String path) throws IOException;

}
```

## LINQ

### Explications

En C#, j'ai l'habitude d'utiliser LINQ (Language Integrated  Query) et il n'y pas vraiment d'équivalent en Java (le plus proche serait https://docs.oracle.com/javase/8/docs/api/?java/util/stream/Stream.html). Je comprends rapidement que je vais devoir utiliser des HashSet à travers l'application car on veut souvent avoir des collections d'objet unique. Je comprends aussi rapidement que je vais souvent chercher à travers ces collections pour trouver une instance. Alors je veux confortablement utiliser la méthode '**firstOfDefault**' qui renvoie le premier élément correspondant à la predicate passé en paramètre sinon revoie '**null**'. En C# j'aurais pu faire une méthode d'extension dans ce cas là, mais là encore cela n'existe en Java et l'héritage reste tout de même plus propre à mon goût. Bref, je crée ma propre implémentation de HashSet avec une méthode '**firstOrDefault**'.

```
import java.util.HashSet;
import java.util.function.Predicate;

public class CustomHashSet<T> extends HashSet<T> {

    // ajout d'une méthode pour obtenir le premier élément correspondant à un prédicat (inspiré de Linq en C#)
    public T firstOrDefault(Predicate<T> predicate) {
        for (T element : this) {
            if (predicate.test(element)) {
                return element;
            }
        }
        return null;
    }
}
```

### Utilisation

Petit exemple sur une collection de '**IUser**', j'ai juste à faire un firstOrDefault et passer ma predicate qui indique que je veux que les ids soient égaux.

```
public class Library implements ILibrary {

    ...

    // HashSet: Les utilisateurs sont uniques + Recherche et insertion rapide
    private CustomHashSet<IUser> users;

    ...

    @Override
    public IUser getUser(String id) {
        return users.firstOrDefault(u -> u.getId().equals(id));
    }

    ...
```

Avec l'api stream cela aurait donné: 
```
@Override
public IUser getUser(String id) {
    return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
}
```
## Singleton pour la classe '**Library**' ?

Au départ, j'avais choisi de faire un singleton pour la classe '**Library**'. Finalement, je me suis ravisé. En effet, cela ne me permettait pas de faire des tests unitaires indépendant les uns des autres. De plus, cela peut poser problème pour l'extensibilité du code, si on devait gérer plusieurs librairies par la suite.

## Hiérarchie des classes

![image](https://github.com/user-attachments/assets/469a8d8b-a247-4d23-bed9-46e90046e1bc)

### La Vue

Dans cette hiérachie on retrouve notre Vue ('**AppView**' qui implémente '**IAppView**') qui contient toute la logique d'interface utilisateur. Elle contient les méthodes qui font de l'affichage mais aussi celles qui gèrent la saisie et la validation de données. 

### Le modèles

### '**Library**' qui implémente '**ILibrary**'

Contient la définition des propriétés d'une librairie ainsi que la logique propre à la librairie. J'aurais pu gérer la logique de sauvegarde à l'extérieur de cette classe.

### '**User**' qui implémente '**IUser**'

Contient la définition des propriétés d'un utilisateur ainsi que la logique propre à l'utilisateur.

### '**Book**' qui implémente '**IBook**'

Contient la définition des propriétés d'un livre ainsi que la logique propre à un livre.

## Les controllers

### '**BookController**' qui implémente '**IBookController**'

Fait la liaison entre la library, et la vue. Cela permet de donner toutes les fonctionnalités de la librairie à la vue (louer un livre, rendre un livre, obtenir la liste des livres disponibles, exporter la liste des livres disponibles ...).

### '**UserController**' qui implémente '**IUserController**'

Fait la liaison entre la user, et la vue. Cela permet principalement de gérer l'authentification.

### '**AppController**' qui implémente '**IAppController**'

Fait le liens entre tous les modèles et la vue. Elle contient un '**IUserController**' et un '**IBookController**'. 

# Installation

à venir...

