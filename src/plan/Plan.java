package plan;

import dessinables.Dessin;
import dessinables.elementsplan.Maison;
import dessinables.elementsplan.Terrain;
import dessinables.geometrie.Point;
import outils.ManipList;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Plan extends JPanel{
    private List<Dessin> dessins;
    private ParametresPlan parametres = new ParametresPlan(50, getBackground(), "None");
    
    private BufferedImage image;

    public Plan() {
        parametres.setPixelsParMetre(50);
        this.dessins = new ArrayList<>();
        this.image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
    }

    public void ajouterDessin(Dessin dessin) {
        ManipList.addElement(this.dessins, dessin);
        redraw();
    }

    public List<Dessin> getDessins() {
        return dessins;
    }

    public List<Dessin> getTerrains() {
        List<Dessin> terrains = new ArrayList<>();
        for (Dessin dessin : dessins) {
            if (dessin instanceof Terrain) {
                terrains.add(dessin);
            }
        }
        return terrains;
    }

    public List<Dessin> getMaisons() {
        List<Dessin> maisons = new ArrayList<>();
        for (Dessin dessin : dessins) {
            if (dessin instanceof Maison) {
                maisons.add(dessin);
            }
        }
        return maisons;
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

    @SuppressWarnings("unused")
    private void dessinerCadrillage(Graphics g){
        g.setColor(Color.LIGHT_GRAY);
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