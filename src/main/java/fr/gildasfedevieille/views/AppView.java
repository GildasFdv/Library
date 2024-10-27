package fr.gildasfedevieille.views;

import java.util.InputMismatchException;
import java.util.Scanner;

import fr.gildasfedevieille.models.IBook;

public class AppView implements IAppView {

    private Scanner scanner = null;

    public AppView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public int getInt(int min, int max, String message) {
        int response = 0;
        boolean isValid = false;

        do {
            System.out.print(message);
            try {
                response = scanner.nextInt();
                scanner.nextLine();

                if (isValidInt(response, min, max)) {
                    isValid = true;
                } else {
                    StringBuilder invalidMessage = new StringBuilder();
                    invalidMessage.append("Saisie invalide. Veuillez entrer un nombre entre ")
                            .append(min)
                            .append(" et ")
                            .append(max)
                            .append(".");

                    System.out.println(invalidMessage.toString());
                }
            } catch (InputMismatchException e) {
                System.out.println("Erreur : veuillez entrer un nombre entier.");
                scanner.nextLine();
            }
        } while (!isValid);

        return response;
    }

    public void close() {
        scanner.close();
    }

    private boolean isValidInt(int value, int min, int max) {
        return value >= min && value <= max;
    }

    @Override
    public void displayUnloggedMenu() {
        System.out.println("________MENU________");
        System.out.println("1. Connexion");
        System.out.println("2. Inscription");
        System.out.println("0. Quitter");
    }

    @Override
    public int getUnloggedMenuChoice() {
        return getInt(0, 2, "Choix: ");
    }

    @Override
    public void displayLoggedMenu() {
        System.out.println("________MENU________");
        System.out.println("1. Voir les livres disponibles");
        System.out.println("2. Voir les détails d'un livre");
        System.out.println("3. Louer un livre");
        System.out.println("4. Rendre un livre");
        System.out.println("5. Exporter le catalogue");
        System.out.println("0. Quitter");
    }

    @Override
    public int getLoggedMenuChoice() {
        return getInt(0, 5, "Choix: ");
    }

    @Override
    public String getUserId() {
        return getString("Entrez votre identifiant: ");
    }

    @Override
    public String getISBN() {
        return getString("Entrez l'ISBN du livre: ");
    }

    @Override
    public String getExportPath() {
        return getString("Entrez le chemin d'export: ");
    }

    @Override
    public void displayNotFoundUserMessage() {
        System.out.println("Utilisateur inconnu.");
    }

    @Override
    public void displayUserAlreadyExistsMessage() {
        System.out.println("Utilisateur déjà existant.");
    }

    @Override
    public void displayBookInsight(IBook book) {
        System.out.println(book.toShortString());
    }

    @Override
    public void displayBookDetails(IBook book) {
        System.out.println(book);
    }

    @Override
    public void displayUnknownBookMessage() {
        System.out.println("Livre inconnu.");
    }

    @Override
    public void displayBookRentedMessage() {
        System.out.println("Le livre a bien été loué.");
    }

    @Override
    public void displayBookReturnedMessage() {
        System.out.println("Le livre a bien été rendu.");
    }

    @Override
    public void displayBookNotRentedMessage() {
        System.out.println("Le livre n'a pas pu être loué.");
    }

    @Override
    public void displayBookNotReturnedMessage() {
        System.out.println("Le livre n'a pas pu être rendu.");
    }

    @Override
    public void displayExportFailedMessage() {
        System.out.println("Export échoué.");
    }

    @Override
    public void displayExportSuccessMessage() {
        System.out.println("Export réussi.");
    }

    @Override
    public void displayGoodbyeMessage() {
        System.out.println("Au revoir!");
    }

    @Override
    public void displaySaveFailedMessage() {
        System.out.println("Erreur lors de la sauvegarde.");
    }
}
