package dessinables.elementsplan;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.Color;

import dessinables.geometrie.Figure;
import dessinables.geometrie.Point;
import dessinables.geometrie.PolygoneEpais;
import dessinables.geometrie.RectangleEpais;
import dessinables.geometrie.Vecteur;

public abstract class ElementDuPlan implements Comparable<ElementDuPlan>{
    private static int idCounter = 0; // Compteur global pour les IDs
    private final int id; // ID unique pour chaque instance
    protected double largeur, hauteur;
    protected Point ptDepart;
    protected String nom;
    protected float epaisseur;
    protected Conteneur parent;

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

    public ElementDuPlan(Point pointDepart, double largeur, double hauteur, String nom, Conteneur parent) {
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

    public ElementDuPlan(Point pointDepart, double largeur, String face, String nom, Conteneur parent){
        this.figures = new ArrayList<>();
        this.ptDepart = pointDepart;
        this.largeur = largeur;
        this.nom = nom;
        this.parent = parent;
        this.id = idCounter++;
        this.getFigures().add(new Vecteur(pointDepart, this.largeur, face));
        System.out.println("Largeur fixé à : " + largeur);
        System.out.println("Vecteur créé : " + this.getFigures().get(0).toString());
    }

    public <T extends ElementDuPlan> List<T> getElements(Class<T> type) {
        if (this instanceof Conteneur) {
            return ((Conteneur) this).getElementsFilles().stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
        }
        return List.of(); // Retourne une liste vide si l'élément n'est pas un conteneur
    }

    public boolean intersect(ElementDuPlan other) {
        return this.getRectangle().intersects(other.getRectangle());
    }

    public boolean contient(Point point) {
        return this.getRectangle().contient(point);
    }

    public void updateDimensions() {
        if (figures.size() > 0 && figures.get(0) instanceof RectangleEpais) {
            RectangleEpais rectangle = (RectangleEpais) figures.get(0);
            this.largeur = rectangle.getMaxX() - rectangle.getMinX();
            this.hauteur = rectangle.getMaxY() - rectangle.getMinY();
            this.ptDepart = new Point(rectangle.getMinX(), rectangle.getMinY());
        }
    }

    public void adapterEchelle(int ancienneValeur, int nouvelleValeur) {
        this.largeur = (this.largeur * nouvelleValeur) / ancienneValeur;
        this.hauteur = (this.hauteur * nouvelleValeur) / ancienneValeur;

        this.ptDepart = new Point((int) ((this.ptDepart.getX() * nouvelleValeur) / ancienneValeur),
                                  (int) ((this.ptDepart.getY() * nouvelleValeur) / ancienneValeur));
        this.getRectangle().mettreAJourDimensions(this.ptDepart , (int) largeur, (int) hauteur);
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

    public RectangleEpais getRectangle() {
        return (RectangleEpais) this.figures.get(0);
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
    public String toString() {
        return "Element : " + this.nom + " \nLargeur : " + this.largeur + "\nHauteur : " + this.hauteur + "\nParent : " + this.parent;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    public Vecteur getVecteur() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getVecteur'");
    }
}