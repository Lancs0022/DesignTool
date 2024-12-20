package dessinables.elementsplan;

import dessinables.geometrie.Point;
import dessinables.geometrie.Vecteur;

public class Fenetre extends ElementDuPlan implements Ouverture{
    private String face;
    public Fenetre(Point pointDepart, double largeur, String face, String nom, Conteneur parent) {
        super(pointDepart, largeur, face, nom, parent);
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