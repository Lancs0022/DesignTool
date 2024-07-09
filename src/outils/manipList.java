package outils;

import java.util.List;

public class ManipList {

    // Méthode pour ajouter un élément à la fin de la liste
    public static <T> void addElement(List<T> list, T element) {
        list.add(element);
    }

    // Méthode pour supprimer un élément à un index donné
    public static <T> void removeElement(List<T> list, int index) {
        if (index >= 0 && index < list.size()) {
            list.remove(index);
        } else {
            System.out.println("Index hors des limites");
        }
    }

    // Méthode pour modifier un élément à un index donné
    public static <T> void modifyElement(List<T> list, int index, T newElement) {
        if (index >= 0 && index < list.size()) {
            list.set(index, newElement);
        } else {
            System.out.println("Index hors des limites");
        }
    }

    // Méthode pour décaler les éléments à gauche à partir d'un index donné
    public static <T> void decalerElementsGauche(List<T> list, int fromIndex) {
        if (fromIndex >= 0 && fromIndex < list.size()) {
            for (int i = fromIndex; i < list.size() - 1; i++) {
                list.set(i, list.get(i + 1));
            }
            list.remove(list.size() - 1);
        } else {
            System.out.println("Index hors des limites");
        }
    }

    // Méthode pour décaler les éléments à droite à partir d'un index donné
    public static <T> void decalerElementsDroite(List<T> list, int fromIndex) {
        if (fromIndex >= 0 && fromIndex < list.size()) {
            list.add(null); // Augmenter la taille de la liste
            for (int i = list.size() - 1; i > fromIndex; i--) {
                list.set(i, list.get(i - 1));
            }
        } else {
            System.out.println("Index hors des limites");
        }
    }

    // Méthode pour insérer un élément à un index donné
    public static <T> void insererElement(List<T> list, int index, T element) {
        if (index >= 0 && index <= list.size()) {
            list.add(index, element);
        } else {
            System.out.println("Index hors des limites");
        }
    }

    // Méthode pour rechercher un élément dans la liste et renvoyer son index
    public static <T> int findElement(List<T> list, T element) {
        return list.indexOf(element);
    }

    // Méthode pour afficher les éléments de la liste
    public static <T> void printList(List<T> list) {
        for (T element : list) {
            System.out.println(element);
        }
    }
}