package dessinables.elementsplan;

import dessinables.geometrie.Point;
import dessinables.geometrie.Vecteur;

public class Porte extends ElementDuPlan implements Ouverture {
    private String face;
    public Porte(Point pointDepart, double largeur, String face, String nom, Conteneur parent) {
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

    @Override
    public String toString() {
        return  "Ouverture : " + this.nom +
                " \nLargeur : " + this.largeur +
                "\nface : " + this.face +
                "\nParent : " + this.parent.getNom() +
                "\nVecteur : " + this.getVecteur();
    }
}
