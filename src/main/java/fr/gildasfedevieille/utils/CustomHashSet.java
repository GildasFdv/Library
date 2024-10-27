package fr.gildasfedevieille.utils;

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
