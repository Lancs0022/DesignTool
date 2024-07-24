package dessinables.elementsplan;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import java.awt.Color;
import dessinables.geometrie.Figure;
import dessinables.geometrie.Point;
import dessinables.geometrie.PolygoneEpais;
import dessinables.geometrie.RectangleEpais;

public abstract class ElementDuPlan implements Comparable<ElementDuPlan>{
    private static int idCounter = 0; // Compteur global pour les IDs
    private final int id; // ID unique pour chaque instance
    protected double largeur, hauteur;
    protected Point ptDepart;
    protected String nom;
    protected float epaisseur;
    protected ElementDuPlan parent;

    protected List<Figure> figures;

    public void ajouterFigure(Figure figure) {
        figures.add(figure);
    }

    public void supprimerFigure(Figure figure) {
        figures.remove(figure);
    }

    public List<Figure> getFigures() {
        return figures;
    }

    public List<Point> getAllPoints() {
        List<Point> points = new ArrayList<>();
        for (Figure figure : figures) {
            points.addAll(figure.getPoints());
        }
        return points;
    }

    public ElementDuPlan(Point pointDepart, double largeur, double hauteur, String nom, ElementDuPlan parent) {
        this.id = idCounter++;
        this.figures = new ArrayList<>();
        this.parent = parent;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.nom = nom;
        this.ptDepart = pointDepart;
        this.epaisseur = 5.0f;
        this.getFigures().add(new RectangleEpais(getPtDepart(), (int) largeur, (int) hauteur, this.epaisseur));
        System.out.println("Création de l'élément : " + nom + " avec ID : " + id + ", Largeur : " + largeur + ", Hauteur : " + hauteur);
    }

    public ElementDuPlan(Point pointDepart, double largeur, double hauteur, String nom) {
        this.id = idCounter++;
        this.figures = new ArrayList<>();
        this.parent = null;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.nom = nom;
        this.ptDepart = pointDepart;
        this.epaisseur = 5.0f;

        this.getFigures().add(new RectangleEpais(getPtDepart(), (int) largeur, (int) hauteur, this.epaisseur));
        System.out.println("Création de l'élément : " + nom + " avec ID : " + id + ", Largeur : " + largeur + ", Hauteur : " + hauteur);
    }

    public boolean intersect(ElementDuPlan autre) {
        return this.ptDepart.getX() < autre.ptDepart.getX() + autre.largeur &&
               this.ptDepart.getX() + this.largeur > autre.ptDepart.getX() &&
               this.ptDepart.getY() < autre.ptDepart.getY() + autre.hauteur &&
               this.ptDepart.getY() + this.hauteur > autre.ptDepart.getY();
    }

    public boolean contient(Point point) {
        boolean result = point.getX() >= ptDepart.getX() && point.getX() <= ptDepart.getX() + largeur &&
                         point.getY() >= ptDepart.getY() && point.getY() <= ptDepart.getY() + hauteur;
        System.out.println("Le point " + point + " est contenu dans " + this.nom + ": " + result);
        return result;
    }

    public void updateDimensions() {
        if (figures.size() > 0 && figures.get(0) instanceof RectangleEpais) {
            RectangleEpais rectangle = (RectangleEpais) figures.get(0);
            this.largeur = rectangle.getMaxX() - rectangle.getMinX();
            this.hauteur = rectangle.getMaxY() - rectangle.getMinY();
            this.ptDepart = new Point(rectangle.getMinX(), rectangle.getMinY());
        }
    }

    public void modifierPointFigure(Figure figure, int index, Point nouveauPoint) {
        if (figure instanceof PolygoneEpais) {
            ((PolygoneEpais) figure).modifierPoint(index, nouveauPoint);
            updateDimensions();
        }
    }

    public int getId() {
        return id;
    }

    public double getLargeur() {
        return largeur;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    public double getHauteur() {
        return hauteur;
    }

    public void setHauteur(double hauteur) {
        this.hauteur = hauteur;
    }

    public Point getPtDepart() {
        return ptDepart;
    }

    public void setPtDepart(Point ptDepart) {
        this.ptDepart = ptDepart;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getEpaisseur() {
        return epaisseur;
    }

    public void setEpaisseur(float epaisseur) {
        this.epaisseur = epaisseur;
    }

    public void dessiner(Graphics g) {
        for (Figure figure : figures) {
            figure.dessiner(g);
        }
    }

    public void afficherPoints(Graphics g) {
        g.setColor(Color.RED);
        for (Figure figure : figures) {
            for (Point point : figure.getPoints()) {
                System.out.println("Affichage du point " + point + " pour l'élément " + this.nom);
                point.dessiner(g);
            }
        }
    }

    @Override
    public int compareTo(ElementDuPlan other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ElementDuPlan other = (ElementDuPlan) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}