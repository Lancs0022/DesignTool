package dessinables.elementsplan;

import dessinables.geometrie.Point;

public class Porte extends ElementDuPlan {
    public Porte(Point pointDepart, double largeur, double hauteur, String nom, Piece parent) {
        super(pointDepart, largeur, hauteur, nom, parent);
    }
}