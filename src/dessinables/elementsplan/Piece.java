package dessinables.elementsplan;

import dessinables.geometrie.Point;
import java.util.ArrayList;
import java.util.List;

public class Piece extends ElementDuPlan {
    private List<Porte> portes;
    private List<Fenetre> fenetres;

    public Piece(Point pointDepart, double largeur, double hauteur, String nom, Maison parent) {
        super(pointDepart, largeur, hauteur, nom, parent);
        this.portes = new ArrayList<>();
        this.fenetres = new ArrayList<>();
    }

    public void ajouterPorte(Porte porte) {
        portes.add(porte);
    }

    public void ajouterFenetre(Fenetre fenetre) {
        fenetres.add(fenetre);
    }

    public List<Porte> getPortes() {
        return portes;
    }

    public List<Fenetre> getFenetres() {
        return fenetres;
    }
}
