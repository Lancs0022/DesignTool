package dessinables.geometrie;

import java.util.List;
import java.awt.Graphics;
import java.util.ArrayList;

public abstract class Geometrie extends Figure {
    protected List<Point> points;

    public Geometrie() {
        this.points = new ArrayList<>();
    }

    public void ajouterPoint(Point point) {
        points.add(point);
        // Logique pour mettre à jour les vecteurs
    }

    public void supprimerPoint(Point point) {
        points.remove(point);
        // Logique pour mettre à jour les vecteurs
    }

    public void modifierPoint(int index, Point nouveauPoint) {
        points.set(index, nouveauPoint);
        // Logique pour mettre à jour les vecteurs
    }

    @Override
    public abstract void dessiner(Graphics g);

    @Override
    public List<Point> getPoints() {
        return points;
    }
}