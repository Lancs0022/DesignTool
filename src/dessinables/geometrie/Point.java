package dessinables.geometrie;

import java.awt.Graphics;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Point extends Figure{
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(){
        this.setX(0); this.setY(0);
    }

    public double distance(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public void translateX(int dx) {
        this.x += dx;
    }

    public void translateY(int dy) {
        this.x += dy;
    }

    public Point decaler(double distance, Direction direction) {
        switch (direction) {
            case HAUT:
                return new Point(x, (int) (y - distance));
            case BAS:
                return new Point(x, (int) (y + distance));
            case GAUCHE:
                return new Point((int) (x - distance) , y);
            case DROITE:
                return new Point((int) (x + distance), y);
            default:
                throw new IllegalArgumentException("Direction inconnue : " + direction);
        }
    }

    public boolean estProche(Point autrePoint, int tolerance) {
        int dx = this.x - autrePoint.getX();
        int dy = this.y - autrePoint.getY();
        return (dx * dx + dy * dy) <= (tolerance * tolerance);
    }

    @Override
    public void dessiner(Graphics g) {
        g.fillOval(x - 5, y - 5, 10, 10); // Un point comme un petit cercle
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public List<Point> getPoints() {
        return Collections.singletonList(this);
    }
}
