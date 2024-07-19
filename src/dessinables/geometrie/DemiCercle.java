package dessinables.geometrie;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class DemiCercle extends Figure {
    private Vecteur v1;
    private Vecteur v2;
    private Point centre;
    private double angle; // en degrés

    // Constructeur avec deux vecteurs
    public DemiCercle(Vecteur v1, Vecteur v2) {
        this.v1 = v1;
        this.v2 = v2;
        this.centre = calculerCentre(v1, v2);
        this.angle = calculerAngle(v1, v2);
    }

    // Méthode pour calculer le centre du demi-cercle
    private Point calculerCentre(Vecteur v1, Vecteur v2) {
        int x = (v1.getP1().getX() + v2.getP1().getX()) / 2;
        int y = (v1.getP1().getY() + v2.getP1().getY()) / 2;
        return new Point(x, y);
    }

    // Méthode pour calculer l'angle entre les deux vecteurs
    private double calculerAngle(Vecteur v1, Vecteur v2) {
        double angle = Math.toDegrees(Math.atan2(v2.getP2().getY() - v1.getP2().getY(), v2.getP2().getX() - v1.getP2().getX()));
        return angle < 0 ? angle + 360 : angle;
    }

    // Méthode pour dessiner le demi-cercle
    @Override
    public void dessiner(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        double startAngle = Math.min(angle, 180); // angle de départ de l'arc
        double extent = 180; // étendue de l'arc (180° pour un demi-cercle)

        // Calcul du rayon à partir des vecteurs
        double radius = v1.getP1().distance(v1.getP2());

        // Dessiner l'arc représentant le demi-cercle
        Arc2D.Double arc = new Arc2D.Double(
            centre.getX() - radius, centre.getY() - radius,
            2 * radius, 2 * radius,
            startAngle, extent,
            Arc2D.OPEN
        );
        g2d.draw(arc);
    }

    // Getter pour le centre
    public Point getCentre() {
        return centre;
    }

    // Getter pour l'angle
    public double getAngle() {
        return angle;
    }

    @Override
    public List<Point> getPoints() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPoints'");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Test Demi-Cercle");
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Vecteur v1 = new Vecteur(new Point(100, 100), new Point(150, 100));
                Vecteur v2 = new Vecteur(new Point(100, 100), new Point(100, 150));
                DemiCercle demiCercle = new DemiCercle(v1, v2);
                demiCercle.dessiner(g);
            }
        };

        frame.add(panel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}