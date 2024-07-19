package view;

import javax.swing.ImageIcon;

public class Icones {
    private ImageIcon terrain;
    private ImageIcon maison;
    private ImageIcon piece;
    private ImageIcon plus;
    private ImageIcon porte;
    private ImageIcon fenetre;
    private ImageIcon moins;

    public Icones() {
        this.terrain = new ImageIcon("assets\\icons8-terrain-24.png");
        this.maison = new ImageIcon("assets\\icons8-niche-pour-chien-24.png");
        this.piece = new ImageIcon("assets\\icons8-chambre-24.png");
        this.plus = new ImageIcon("assets\\icons8-plus-24.png");
        this.porte = new ImageIcon("assets\\icons8-porte-24.png");
        this.fenetre = new ImageIcon("assets\\icons8-fenêtre-ouverte-24.png");
        this.moins = new ImageIcon("assets/moins.png");
    }

    public ImageIcon getTerrain() {
        return terrain;
    }

    public ImageIcon getMaison() {
        return maison;
    }

    public ImageIcon getPiece() {
        return piece;
    }

    public ImageIcon getPlus() {
        return plus;
    }

    public ImageIcon getPorte() {
        return porte;
    }

    public ImageIcon getFenetre() {
        return fenetre;
    }

    public ImageIcon getMoins() {
        return moins;
    }
}