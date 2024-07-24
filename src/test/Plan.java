package test;

import dessinables.elementsplan.ElementDuPlan;
import dessinables.elementsplan.Fenetre;
import dessinables.elementsplan.Maison;
import dessinables.elementsplan.Piece;
import dessinables.elementsplan.Porte;
import dessinables.elementsplan.Terrain;
import dessinables.geometrie.Point;
import outils.ManipList;
import plan.ParametresPlan;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Plan extends JPanel {
    private List<ElementDuPlan> elements;
    private ParametresPlan parametres = new ParametresPlan();


    public Plan() {
        parametres.setPixelsParMetre(50);
        this.elements = new ArrayList<>();
    }

    public synchronized void ajouterDessin(ElementDuPlan element) {
        ManipList.addElement(this.elements, element);
        repaint();
    }

    public List<ElementDuPlan> getElements() {
        return elements;
    }

    public <T extends ElementDuPlan> List<T> getElements(Class<T> type) {
        return elements.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (ElementDuPlan element : elements) {
            if (element instanceof Terrain) {
                g.setColor(parametres.getCouleurBordTerrain());
                // Set fill color if necessary
                // g.fillXXX(..., parametres.getCouleurFondTerrain());
            } else if (element instanceof Maison) {
                g.setColor(parametres.getCouleurBordMaison());
                // Set fill color if necessary
                // g.fillXXX(..., parametres.getCouleurFondMaison());
            } else if (element instanceof Piece) {
                g.setColor(parametres.getCouleurBordPiece());
                // Set fill color if necessary
                // g.fillXXX(..., parametres.getCouleurFondPiece());
            } else if (element instanceof Porte) {
                g.setColor(parametres.getCouleurPorte());
            } else if (element instanceof Fenetre) {
                g.setColor(parametres.getCouleurFenetre());
            }
            element.dessiner(g);
        }
    }

    @SuppressWarnings("unused")
    private void dessinerCadrillage(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
    }

    @Override
    public Dimension getPreferredSize() {
        int maxX = 800; // Taille minimale en X
        int maxY = 600; // Taille minimale en Y

        for (ElementDuPlan element : elements) {
            List<Point> points = element.getAllPoints();
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

    public void setElements(List<ElementDuPlan> elements) {
        this.elements = elements;
    }

    public ParametresPlan getParametres() {
        return parametres;
    }

    public void setParametres(ParametresPlan parametres) {
        this.parametres = parametres;
    }
}