package dessinables;

import java.util.List;
import java.awt.Graphics;
import java.util.ArrayList;

public abstract class Polygone extends Geometrie {
    protected List<Vecteur> vecteurs;

    public Polygone(List<Point> points) {
        super();
        this.points = points;
        this.vecteurs = new ArrayList<>();
        construireVecteurs();
    }

    private void construireVecteurs() {
        vecteurs.clear();
        for (int i = 0; i < points.size(); i++) {
            Point p1 = points.get(i);
            Point p2 = points.get((i + 1) % points.size());
            vecteurs.add(new Vecteur(p1, p2));
        }
    }

    @Override
    public void dessiner(Graphics g) {
        for (Vecteur vecteur : vecteurs) {
            vecteur.dessiner(g);
        }
    }
}