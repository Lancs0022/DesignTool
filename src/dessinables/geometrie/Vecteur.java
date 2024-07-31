package dessinables.geometrie;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import plan.ParametresPlan;

public class Vecteur extends Figure {
    private Point p1;
    private Point p2;

    public Vecteur(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Vecteur(Point ptDepart, double distance, String orientation) {
        this.p1 = ptDepart;
        this.p2 = new Point();
        distance *= ParametresPlan.getPixelsParMetre();
    
        switch (orientation.toLowerCase()) {
            case "nord":
                this.p2.setX(ptDepart.getX());
                this.p2.setY(ptDepart.getY() - (int) distance);
                break;
            case "sud":
                this.p2.setX(ptDepart.getX() + (int) distance);
                this.p2.setY(ptDepart.getY());
                break;
            case "ouest":
                this.p2.setX(ptDepart.getX() - (int) distance);
                this.p2.setY(ptDepart.getY());
                break;
            case "est":
                this.p2.setX(ptDepart.getX() + (int) distance);
                this.p2.setY(ptDepart.getY());
                break;
            default:
                throw new IllegalArgumentException("Orientation inconnue : " + orientation);
        }
    }

    public Direction getDirection() {
        if (p1.getX() == p2.getX()) {
            // Vecteur vertical
            return p2.getY() > p1.getY() ? Direction.BAS : Direction.HAUT;
        } else if (p1.getY() == p2.getY()) {
            // Vecteur horizontal
            return p2.getX() > p1.getX() ? Direction.DROITE : Direction.GAUCHE;
        }
        // Si le vecteur n'est pas strictement horizontal ou vertical, lever une exception ou gérer autrement
        throw new IllegalStateException("Le vecteur n'est ni strictement horizontal ni vertical.");
    }

    public double getLongueur() {
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    @Override
    public void dessiner(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(6.0f));
        g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    @Override
    public String toString() {
        return "Vecteur{" +
                "start=" + getP1() +
                ", end=" + getP2() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vecteur vecteur = (Vecteur) o;
        return Objects.equals(getP1(), vecteur.getP1()) && Objects.equals(getP2(), vecteur.getP2());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getP1(), getP2());
    }
    
    @Override
    public List<Point> getPoints() {
        return Arrays.asList(p1, p2);
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }
}