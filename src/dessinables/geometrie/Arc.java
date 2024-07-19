package dessinables.geometrie;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Arc extends Figure {
    private Point point;
    private Point centre;
    private int largeur;
    private int hauteur;
    private int angleDebut;
    private int angleArc;
    private boolean afficherRectangle;

    public Arc(Point point, int largeur, int hauteur, int angleDebut, int angleArc) {
        this.point = point;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.angleDebut = angleDebut;
        this.angleArc = angleArc;
        this.afficherRectangle = false;
        this.centre = new Point(point.getX() + largeur / 2, point.getY() + hauteur / 2);
    }

    public void setAfficherRectangle(boolean afficher) {
        this.afficherRectangle = afficher;
    }

    public boolean isAfficherRectangle() {
        return afficherRectangle;
    }

    public Point getCentre() {
        return centre;
    }

    @Override
    public void dessiner(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Dessiner le rectangle englobant
        if (afficherRectangle) {
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.drawRect(point.getX(), point.getY(), largeur, hauteur);
        }

        // Dessiner l'arc
        g2d.setColor(Color.BLACK);
        g2d.drawArc(point.getX(), point.getY(), largeur, hauteur, angleDebut, angleArc);

        // Calculer les points pour les rayons
        double startRad = Math.toRadians(angleDebut);
        double endRad = Math.toRadians(angleDebut + angleArc);

        int startX = (int) (point.getX() + largeur / 2 + largeur / 2 * Math.cos(startRad));
        int startY = (int) (point.getY() + hauteur / 2 - hauteur / 2 * Math.sin(startRad));

        int endX = (int) (point.getX() + largeur / 2 + largeur / 2 * Math.cos(endRad));
        int endY = (int) (point.getY() + hauteur / 2 - hauteur / 2 * Math.sin(endRad));

        int centreX = point.getX() + largeur / 2;
        int centreY = point.getY() + hauteur / 2;

        // Dessiner les rayons
        g2d.drawLine(centreX, centreY, startX, startY);
        g2d.drawLine(centreX, centreY, endX, endY);
    }

    @Override
    public List<Point> getPoints() {
        return Arrays.asList(point, centre);
    }

    @Override
    public String toString() {
        return "Arc{" +
                "point=" + point +
                ", centre=" + centre +
                ", largeur=" + largeur +
                ", hauteur=" + hauteur +
                ", angleDebut=" + angleDebut +
                ", angleArc=" + angleArc +
                ", afficherRectangle=" + afficherRectangle +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arc arc = (Arc) o;
        return largeur == arc.largeur &&
                hauteur == arc.hauteur &&
                angleDebut == arc.angleDebut &&
                angleArc == arc.angleArc &&
                afficherRectangle == arc.afficherRectangle &&
                point.equals(arc.point) &&
                centre.equals(arc.centre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, centre, largeur, hauteur, angleDebut, angleArc, afficherRectangle);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Test Arc");
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Point point = new Point(50, 50);
                Arc arc = new Arc(point, 100, 100, 0, 160);
                arc.dessiner(g);
            }
        };
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
