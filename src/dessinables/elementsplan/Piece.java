package dessinables.elementsplan;

import dessinables.geometrie.Point;
import java.util.ArrayList;
import java.util.List;

public class Piece extends ElementDuPlan {
    private Maison maison;
    private List<ElementDuPlan> elements;

    public Piece(Point pointDepart, double largeur, double hauteur, String nom, Maison maison) {
        super(pointDepart, largeur, hauteur, nom);
        this.maison = maison;
        this.elements = new ArrayList<>();
    }

    public boolean peutAjouterElement(ElementDuPlan element) {
        // Vérifier si l'élément est entièrement dans la pièce
        if (element.ptDepart.getX() < this.ptDepart.getX() ||
            element.ptDepart.getY() < this.ptDepart.getY() ||
            element.ptDepart.getX() + element.getLargeur() > this.ptDepart.getX() + this.getLargeur() ||
            element.ptDepart.getY() + element.getHauteur() > this.ptDepart.getY() + this.getHauteur()) {
            System.out.println("Élément hors des limites de la pièce");
            System.out.println("Élément: " + element.ptDepart + ", " + element.getLargeur() + "x" + element.getHauteur());
            System.out.println("Pièce: " + this.ptDepart + ", " + this.getLargeur() + "x" + this.getHauteur());
            return false;
        }
    
        // Vérifier si l'élément ne se superpose pas avec les éléments existants
        for (ElementDuPlan e : elements) {
            if (element.intersect(e)) {
                System.out.println("Collision détectée avec un élément existant");
                return false;
            }
        }
    
        return true;
    }

    public boolean ajouterElement(ElementDuPlan element) {
        if (peutAjouterElement(element)) {
            elements.add(element);
            return true;
        }
        return false;
    }

    public Maison getMaison() {
        return maison;
    }

    public List<ElementDuPlan> getElements() {
        return elements;
    }
}
