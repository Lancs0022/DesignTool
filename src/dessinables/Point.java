package dessinables;

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
    
    @Override
    public void dessiner(Graphics g) {
        g.fillOval(x - 2, y - 2, 4, 4); // Un point comme un petit cercle
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
