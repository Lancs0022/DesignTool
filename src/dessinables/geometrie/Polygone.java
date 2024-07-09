package dessinables.geometrie;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

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

    public double calculerAngle(Point p1, Point sommet, Point p3) {
        double angle;
        
        // Vecteur v1 : sommet -> p1
        double v1x = p1.getX() - sommet.getX();
        double v1y = p1.getY() - sommet.getY();
        
        // Vecteur v2 : sommet -> p3
        double v2x = p3.getX() - sommet.getX();
        double v2y = p3.getY() - sommet.getY();
        
        // Produit scalaire
        double dotProduct = v1x * v2x + v1y * v2y;
        
        // Magnitude des vecteurs
        double magnitudeV1 = Math.sqrt(v1x * v1x + v1y * v1y);
        double magnitudeV2 = Math.sqrt(v2x * v2x + v2y * v2y);
        
        // Calcul de l'angle en radians
        angle = Math.acos(dotProduct / (magnitudeV1 * magnitudeV2));
        
        // Conversion en degrés
        angle = Math.toDegrees(angle);
        
        return angle;
    }

    private Point trouverPointPourCentre(Point p2, Point p3, Point centre) {
        int x1 = 3 * centre.getX() - p2.getX() - p3.getX();
        int y1 = 3 * centre.getY() - p2.getY() - p3.getY();
        return new Point(x1, y1);
    }
}