package test;

import javax.swing.*;
import java.awt.*;

public class ArcExample extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int x = 50;
        int y = 50;
        int width = 100;
        int height = 100;
        int startAngle = 0;
        int arcAngle = 160;

        // Dessiner le rectangle englobant
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawRect(x, y, width, height);

        // Dessiner l'arc
        g2d.setColor(Color.BLACK);
        g2d.drawArc(x, y, width, height, startAngle, arcAngle);

        // Calculer les points pour les rayons
        double startRad = Math.toRadians(startAngle);
        double endRad = Math.toRadians(startAngle + arcAngle);

        int startX = (int) (x + width / 2 + width / 2 * Math.cos(startRad));
        int startY = (int) (y + height / 2 - height / 2 * Math.sin(startRad));

        int endX = (int) (x + width / 2 + width / 2 * Math.cos(endRad));
        int endY = (int) (y + height / 2 - height / 2 * Math.sin(endRad));

        int centerX = x + width / 2;
        int centerY = y + height / 2;

        // Dessiner les rayons
        g2d.drawLine(centerX, centerY, startX, startY);
        g2d.drawLine(centerX, centerY, endX, endY);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Arc Example");
        ArcExample panel = new ArcExample();
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
