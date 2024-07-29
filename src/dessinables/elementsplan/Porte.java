package dessinables.elementsplan;

import dessinables.geometrie.Point;
import dessinables.geometrie.Vecteur;

public class Porte extends ElementDuPlan implements Contenu {
    private String face;
    public Porte(Point pointDepart, double largeur, String face, Conteneur parent) {
        super(pointDepart, largeur, face, parent);
        this.face = face;
    }
    public String getFace() {
        return face;
    }
    @Override
    public Vecteur getVecteur() {
        return (Vecteur) this.figures.get(0);
    }
}
