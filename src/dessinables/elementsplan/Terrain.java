package dessinables.elementsplan;

import dessinables.geometrie.Point;
import java.util.ArrayList;
import java.util.List;

public class Terrain extends ElementDuPlan implements Conteneur {
    private List<Contenu> contenus;

    public Terrain(Point pointDepart, int largeur, int hauteur, String nom) {
        super(pointDepart, largeur, hauteur, nom);
        this.contenus = new ArrayList<>();
    }

    public boolean peutAjouterMaison(Maison maison) {
        // Vérifier si la maison est entièrement dans le terrain
        if (!maison.getRectangle().neDepassePasConteneur(this.getRectangle())) {
            System.out.println("Maison hors des limites du terrain");
            System.out.println("Maison: " + maison.ptDepart + ", " + maison.getLargeur() + "x" + maison.getHauteur());
            System.out.println("Terrain: " + this.ptDepart + ", " + this.getLargeur() + "x" + this.getHauteur());
            return false;
        }
    
        // Vérifier si la maison ne se superpose pas avec les contenus existants
        for (Contenu contenu : contenus) {
            if (contenu instanceof Maison && maison.intersect((Maison) contenu)) {
                System.out.println("Collision détectée avec une maison existante");
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
        if (element instanceof Maison) {
            if (peutAjouterMaison((Maison) element)) {
                contenus.add((Contenu) element);
                return true;
            }
        }
        return false;
    }
}