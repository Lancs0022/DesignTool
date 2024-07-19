package dessinables.elementsplan;

import java.awt.Graphics;

import dessinables.Dessin;
import dessinables.geometrie.Figure;
import dessinables.geometrie.Point;
import dessinables.geometrie.RectangleEpais;

public abstract class ElementDuPlan extends Dessin{
    protected double largeur, hauteur;
    protected Point ptDepart;
    protected String nom;
    protected float epaisseur;
    protected ElementDuPlan parent;

    public ElementDuPlan(Point pointDepart, double largeur, double hauteur, String nom, ElementDuPlan parent) {
        this.parent = parent;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.nom = nom;
        this.ptDepart = pointDepart;
        this.epaisseur = 5.0f;
        
        this.getFigures().add(new RectangleEpais(getPtDepart(), (int) largeur, (int) hauteur, this.epaisseur));
    }

    public ElementDuPlan(Point pointDepart, double largeur, double hauteur, String nom) {
        this.parent = null;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.nom = nom;
        this.ptDepart = pointDepart;
        this.epaisseur = 5.0f;

        this.getFigures().add(new RectangleEpais(getPtDepart(), (int) largeur, (int) hauteur, this.epaisseur));
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
}