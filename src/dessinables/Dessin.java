package dessinables;

import java.util.List;

import dessinables.geometrie.Figure;
import dessinables.geometrie.Point;

import java.awt.Graphics;
import java.util.ArrayList;

public class Dessin {
    private List<Figure> figures;

    public Dessin() {
        figures = new ArrayList<>();
    }

    public void ajouterFigure(Figure figure) {
        figures.add(figure);
    }

    public void dessiner(Graphics g) {
        for (Figure figure : figures) {
            figure.dessiner(g);
        }
    }

    public List<Point> getAllPoints() {
        List<Point> points = new ArrayList<>();
        for (Figure figure : figures) {
            points.addAll(figure.getPoints());
        }
        return points;
    }
}