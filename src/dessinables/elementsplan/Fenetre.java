package dessinables.elementsplan;

import dessinables.geometrie.Point;

public class Fenetre extends ElementDuPlan implements Contenu{
    public Fenetre(Point pointDepart, double largeur, double hauteur, String nom, Conteneur parent) {
        super(pointDepart, largeur, hauteur, nom, parent);
    }
}
