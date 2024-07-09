package dessinables.geometrie;

import java.awt.Graphics;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Vecteur extends Figure {
    private Point p1;
    private Point p2;

    public Vecteur(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    @Override
    public void dessiner(Graphics g) {
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
}