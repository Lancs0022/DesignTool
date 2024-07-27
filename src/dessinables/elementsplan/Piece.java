package dessinables.elementsplan;

import dessinables.geometrie.Point;
import java.util.ArrayList;
import java.util.List;

public class Piece extends ElementDuPlan implements Conteneur, Contenu {
    private List<Contenu> contenus;

    public Piece(Point pointDepart, double largeur, double hauteur, String nom, Conteneur parent) {
        super(pointDepart, largeur, hauteur, nom, parent);
        this.contenus = new ArrayList<>();
    }

    public boolean peutAjouterElement(ElementDuPlan element) {
        // Vérifier si l'élément est entièrement dans la pièce
        if (!element.getRectangle().neDepassePasConteneur(this.getRectangle())) {
            System.out.println("Élément hors des limites de la pièce");
            System.out.println("Élément: " + element.ptDepart + ", " + element.getLargeur() + "x" + element.getHauteur());
            System.out.println("Pièce: " + this.ptDepart + ", " + this.getLargeur() + "x" + this.getHauteur());
            return false;
        }

        // Vérifier si l'élément ne se superpose pas avec les éléments existants
        for (Contenu contenu : contenus) {
            if (element.intersect((ElementDuPlan) contenu)) {
                System.out.println("Collision détectée avec un élément existant");
                return false;
            }
        }
        
        return true;
    }

    @Override
    public List<Contenu> getElementsFilles() {
        return contenus;
    }

    @Override
    public boolean ajouterElement(ElementDuPlan element) {
        if (peutAjouterElement(element)) {
            contenus.add((Contenu) element);
            return true;
        }
        return false;
    }
}
