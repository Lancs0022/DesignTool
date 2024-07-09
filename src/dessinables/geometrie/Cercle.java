package dessinables.geometrie;

import java.awt.Graphics;

public class Cercle extends Geometrie {
    private Vecteur rayon;
    // private int angle;

    public Cercle(Point centre, Vecteur rayon) {
        this.rayon = rayon;
        this.points.add(centre);
    }

    @Override
    public void dessiner(Graphics g) {
        Point centre = points.get(0);
        int rayon = (int) Math.sqrt(Math.pow(this.rayon.getP2().getX() - centre.getX(), 2) + 
                                    Math.pow(this.rayon.getP2().getY() - centre.getY(), 2));
        g.drawOval(centre.getX() - rayon, centre.getY() - rayon, rayon * 2, rayon * 2);
    }
}