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

    public void ajouterMaison(Maison maison) {
        maisons.add(maison);
    }

    public List<Maison> getMaisons() {
        return maisons;
    }
}
