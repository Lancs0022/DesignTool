package dessinables.geometrie;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class PolygoneEpais extends Geometrie{

    private float épaisseur;

    public PolygoneEpais(List<Point> points, float épaisseur) {
        this.points = points;
        this.épaisseur = épaisseur;
    }

    public void dessiner(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(épaisseur));

        for (int i = 0; i < points.size(); i++) {
            Point p1 = points.get(i);
            Point p2 = points.get((i + 1) % points.size());
            g2.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Polygone à Double Épaisseur");
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                List<Point> points = new ArrayList<>();
                points.add(new Point(100, 100));
                points.add(new Point(200, 100));
                points.add(new Point(200, 200));
                points.add(new Point(100, 200));
                points.add(new Point(5, 50));

                PolygoneEpais polygone = new PolygoneEpais(points, 5f);
                polygone.dessiner(g);
            }
        };

        frame.add(panel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
