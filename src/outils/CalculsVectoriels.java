package outils;

import dessinables.geometrie.Vecteur;
import dessinables.geometrie.Point;

public class CalculsVectoriels {
    public static boolean vectSontAllignes(Vecteur v1, Vecteur v2) {
        // Calculer les vecteurs
        Point p1 = v1.getP1();
        Point p2 = v1.getP2();
        Point p3 = v2.getP1();
        Point p4 = v2.getP2();

        // Vecteurs directionnels
        int dx1 = p2.getX() - p1.getX();
        int dy1 = p2.getY() - p1.getY();
        int dx2 = p4.getX() - p3.getX();
        int dy2 = p4.getY() - p3.getY();

        // Vérifier si les vecteurs sont colinéaires (produit vectoriel nul)
        return (dx1 * dy2 - dy1 * dx2) == 0;
    }

    public static boolean vectSontEgaux(Vecteur v1, Vecteur v2) {
        // Vérifier si les vecteurs ont les mêmes points de début et de fin
        return v1.getP1().equals(v2.getP1()) && v1.getP2().equals(v2.getP2());
    }

    public static double mesurerVecteur(Vecteur v, double pixelParMetre) {
        // Calculer la longueur du vecteur en pixels
        int dx = v.getP2().getX() - v.getP1().getX();
        int dy = v.getP2().getY() - v.getP1().getY();
        double longueurPixels = Math.sqrt(dx * dx + dy * dy);

        // Convertir en mètres
        return longueurPixels / pixelParMetre;
    }
}
