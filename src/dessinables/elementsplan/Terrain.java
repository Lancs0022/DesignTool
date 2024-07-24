package dessinables.elementsplan;

import dessinables.geometrie.Point;
import java.util.ArrayList;
import java.util.List;

public class Terrain extends ElementDuPlan {
    private List<Maison> maisons;

    public Terrain(Point pointDepart, int largeur, int hauteur, String nom) {
        super(pointDepart, largeur, hauteur, nom);
        this.maisons = new ArrayList<>();
    }

    public boolean peutAjouterMaison(Maison maison) {
        // Vérifier si la maison est entièrement dans le terrain
        if (maison.ptDepart.getX() < this.ptDepart.getX() ||
            maison.ptDepart.getY() < this.ptDepart.getY() ||
            maison.ptDepart.getX() + maison.getLargeur() > this.ptDepart.getX() + this.getLargeur() ||
            maison.ptDepart.getY() + maison.getHauteur() > this.ptDepart.getY() + this.getHauteur()) {
            System.out.println("Maison hors des limites du terrain");
            System.out.println("Maison: " + maison.ptDepart + ", " + maison.getLargeur() + "x" + maison.getHauteur());
            System.out.println("Terrain: " + this.ptDepart + ", " + this.getLargeur() + "x" + this.getHauteur());
            return false;
        }
    
        // Vérifier si la maison ne se superpose pas avec les maisons existantes
        for (Maison m : maisons) {
            if (maison.intersect(m)) {
                System.out.println("Collision détectée avec une maison existante");
                return false;
            }
        }
    
        return true;
    }
    
    public boolean ajouterMaison(Maison maison) {
        if (peutAjouterMaison(maison)) {
            maisons.add(maison);
            return true;
        }
        return false;
    }

    public List<Maison> getMaisons() {
        return maisons;
    }
}