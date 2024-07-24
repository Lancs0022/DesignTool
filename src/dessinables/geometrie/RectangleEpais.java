package dessinables.geometrie;

import java.util.List;

public class RectangleEpais extends PolygoneEpais{

    public RectangleEpais(Point p1, Point p2, Point p3, Point p4, float epaisseur) {
        super(List.of(p1, p2, p3, p4), epaisseur);
    }

    public RectangleEpais(Point point, int largeur, int hauteur, float epaisseur) {
        Point p1 = point;
        Point p2 = new Point(point.getX() + largeur, point.getY());
        Point p3 = new Point(point.getX() + largeur, point.getY() + hauteur);
        Point p4 = new Point(point.getX(), point.getY() + hauteur);

        super(List.of(p1, p2, p3, p4), epaisseur);
    }

    public boolean intersects(RectangleEpais other) {
        return this.getBounds().intersects(other.getBounds());
    }

    private java.awt.Rectangle getBounds() {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Point point : this.points) {
            minX = Math.min(minX, point.getX());
            minY = Math.min(minY, point.getY());
            maxX = Math.max(maxX, point.getX());
            maxY = Math.max(maxY, point.getY());
        }

        return new java.awt.Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    public List<Point> trouverPointsAdjacents(Point point) {
        if (point.equals(points.get(0))) {
            return List.of(points.get(3), points.get(0), points.get(1));
        } else if (point.equals(points.get(1))) {
            return List.of(points.get(2), points.get(1), points.get(0));
        } else if (point.equals(points.get(2))) {
            return List.of(points.get(1), points.get(2), points.get(3));
        } else if (point.equals(points.get(3))) {
            return List.of(points.get(0), points.get(3), points.get(2));
        }
        return List.of();
    }

    public int getMinX() {
        return this.getBounds().x;
    }

    public int getMinY() {
        return this.getBounds().y;
    }

    public int getMaxX() {
        return this.getBounds().x + this.getBounds().width;
    }

    public int getMaxY() {
        return this.getBounds().y + this.getBounds().height;
    }
}
