package outils;

import dessinables.geometrie.Vecteur;
import dessinables.geometrie.Point;

public class CalculsVectoriels {
    public static boolean vectSontAlignes(Vecteur v1, Vecteur v2) {
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

    public static double mesurerVecteur(Vecteur v, int pixelParMetre) {
        // Calculer la longueur du vecteur en pixels
        int dx = v.getP2().getX() - v.getP1().getX();
        int dy = v.getP2().getY() - v.getP1().getY();
        double longueurPixels = Math.sqrt(dx * dx + dy * dy);

        // Convertir en mètres
        return longueurPixels / pixelParMetre;
    }

    public static boolean intersect(Vecteur v1, Vecteur v2) {
        Point p1 = v1.getP1();
        Point p2 = v1.getP2();
        Point p3 = v2.getP1();
        Point p4 = v2.getP2();

        // Déterminer l'orientation des triplets de points
        int o1 = orientation(p1, p2, p3);
        int o2 = orientation(p1, p2, p4);
        int o3 = orientation(p3, p4, p1);
        int o4 = orientation(p3, p4, p2);

        // Vérifier les orientations pour déterminer s'il y a intersection
        if (o1 != o2 && o3 != o4) {
            return true;
        }

        // Vérifier les cas où les points sont colinéaires
        if (o1 == 0 && surSegment(p1, p3, p2)) return true;
        if (o2 == 0 && surSegment(p1, p4, p2)) return true;
        if (o3 == 0 && surSegment(p3, p1, p4)) return true;
        if (o4 == 0 && surSegment(p3, p2, p4)) return true;

        return false;
    }

    private static int orientation(Point p, Point q, Point r) {
        int val = (q.getY() - p.getY()) * (r.getX() - q.getX()) -
                  (q.getX() - p.getX()) * (r.getY() - q.getY());

        if (val == 0) return 0;  // colinéaire
        return (val > 0) ? 1 : 2; // horaire ou anti-horaire
    }

    private static boolean surSegment(Point p, Point q, Point r) {
        return q.getX() <= Math.max(p.getX(), r.getX()) && q.getX() >= Math.min(p.getX(), r.getX()) &&
               q.getY() <= Math.max(p.getY(), r.getY()) && q.getY() >= Math.min(p.getY(), r.getY());
    }
}
