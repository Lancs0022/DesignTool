package plan;

import dessinables.elementsplan.ElementDuPlan;
import dessinables.elementsplan.Fenetre;
import dessinables.elementsplan.Maison;
import dessinables.elementsplan.Piece;
import dessinables.elementsplan.Porte;
import dessinables.elementsplan.Terrain;
import dessinables.geometrie.Figure;
import dessinables.geometrie.Point;
import dessinables.geometrie.RectangleEpais;
import outils.ManipList;

import javax.swing.JPanel;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Plan extends JPanel {
    private List<ElementDuPlan> elements;
    private ParametresPlan parametres = new ParametresPlan();
    private ElementDuPlan elementSelectionne = null;
    private Point pointSelectionne;
    private boolean afficherPoints = false;

    public Plan() {
        parametres.setPixelsParMetre(50);
        this.elements = new ArrayList<>();
        this.elements.add(new Terrain(new Point(0,0), 10 * this.parametres.getPixelsParMetre(), 10 * this.parametres.getPixelsParMetre(), "Terrain auto"));
        ajouterEcouteurs();
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
        dessinerCadrillage(g);
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
            System.out.println("Elements : " + element.toString());
            element.dessiner(g);
            if (element == elementSelectionne && afficherPoints) {
                element.afficherPoints(g);
            }
        }
    }

    private void dessinerCadrillage(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        int pixelsParMetre = parametres.getPixelsParMetre();
        int width = getWidth();
        int height = getHeight();

        // Dessiner les lignes verticales
        for (int x = 0; x < width; x += pixelsParMetre) {
            g.drawLine(x, 0, x, height);
        }

        // Dessiner les lignes horizontales
        for (int y = 0; y < height; y += pixelsParMetre) {
            g.drawLine(0, y, width, y);
        }
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

    private void ajouterEcouteurs() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point clickPoint = new Point(e.getX(), e.getY());
                gererSelection(clickPoint);
                if (elementSelectionne != null) {
                    pointSelectionne = trouverPointSelectionne(clickPoint);
                    if (pointSelectionne != null) {
                        System.out.println("Point sélectionné : " + pointSelectionne);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (elementSelectionne != null && pointSelectionne != null) {
                    Point releasePoint = new Point(e.getX(), e.getY());
                    deplacerPoint(pointSelectionne, releasePoint);
                    pointSelectionne = null;
                    repaint();
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (elementSelectionne != null && pointSelectionne != null) {
                    Point dragPoint = new Point(e.getX(), e.getY());
                    deplacerPoint(pointSelectionne, dragPoint);
                    repaint();
                }
            }
        });
    }

    private void gererSelection(Point point) {
        ElementDuPlan nouvelElementSelectionne = null;
        for (ElementDuPlan element : elements) {
            if (element.contient(point)) {
                if (nouvelElementSelectionne == null || (elementSelectionne != null && elementSelectionne.compareTo(element) < 0)) {
                    nouvelElementSelectionne = element;
                }
            }
        }
        elementSelectionne = nouvelElementSelectionne;
        if (nouvelElementSelectionne != null) {
            System.out.println("Élément sélectionné : " + elementSelectionne.getNom() + ", ID : " + elementSelectionne.getId() +
                               ", Largeur : " + elementSelectionne.getLargeur() + ", Hauteur : " + elementSelectionne.getHauteur());
            afficherPoints = true;
        } else {
            System.out.println("Aucun élément sélectionné.");
            elementSelectionne = null;
            afficherPoints = false;
        }

        repaint();
    }

    private Point trouverPointSelectionne(Point point) {
        for (Point p : elementSelectionne.getAllPoints()) {
            if (point.estProche(p, 10)) { // Augmenter la tolérance à 10 pixels
                return p;
            }
        }
        return null;
    }

    private void deplacerPoint(Point pointOriginal, Point nouveauPoint) {
        if (elementSelectionne != null) {
            for (Figure figure : elementSelectionne.getFigures()) {
                if (figure instanceof RectangleEpais) {
                    RectangleEpais rectangle = (RectangleEpais) figure;
                    List<Point> pointsAdjacents = rectangle.trouverPointsAdjacents(pointOriginal);
    
                    if (pointsAdjacents.size() == 3) {
                        // Premier point : varie seulement sur l'axe X
                        Point premierPoint = pointsAdjacents.get(0);
                        premierPoint.setX(nouveauPoint.getX());
    
                        // Deuxième point : point en paramètre
                        Point deuxiemePoint = pointsAdjacents.get(1);
                        deuxiemePoint.setX(nouveauPoint.getX());
                        deuxiemePoint.setY(nouveauPoint.getY());
    
                        // Troisième point : varie seulement sur l'axe Y
                        Point troisiemePoint = pointsAdjacents.get(2);
                        troisiemePoint.setY(nouveauPoint.getY());
                    }
                    break; // On sort de la boucle car on a trouvé le RectangleEpais
                }
            }
            elementSelectionne.updateDimensions();
        }
    }

    public void setPixelsParMetre(int pixelsParMetre) {
        int ancienneValeur = parametres.getPixelsParMetre();
        parametres.setPixelsParMetre(pixelsParMetre);
        for (ElementDuPlan element : this.elements) {
            element.adapterEchelle(ancienneValeur, pixelsParMetre);
        }
        repaint();
    }
}