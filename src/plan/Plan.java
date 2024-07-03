package plan;

import dessinables.Dessin;
import dessinables.Point;
import outils.manipList;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Plan extends JPanel {
    private List<Dessin> dessins;
    private BufferedImage image;

    public Plan() {
        this.dessins = new ArrayList<>();
        this.image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
    }

    public void ajouterDessin(Dessin dessin) {
        manipList.addElement(this.dessins, dessin);
        redraw();
    }

    private void redraw() {
        Graphics g = image.getGraphics();
        g.clearRect(0, 0, image.getWidth(), image.getHeight());
        for (Dessin dessin : dessins) {
            dessin.dessiner(g);
        }
        g.dispose();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    @Override
    public Dimension getPreferredSize() {
        int maxX = 800; // Taille minimale en X
        int maxY = 600; // Taille minimale en Y

        for (Dessin dessin : dessins) {
            List<Point> points = dessin.getAllPoints();
            for (Point point : points) {
                if (point.getX() > maxX) {
                    maxX = point.getX();
                }
                if (point.getY() > maxY) {
                    maxY = point.getY();
                }
            }
        }

        return new Dimension(maxX + 20, maxY + 20); // Ajouter une marge de 20 pixels
    }
}