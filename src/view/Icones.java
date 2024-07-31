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
    private ImageIcon poubelle;
    private ImageIcon nouvelleFenetre;
    private ImageIcon nouveau;
    private ImageIcon fermer;
    private ImageIcon sauvegarder;
    private ImageIcon ouvrir;

    public Icones() {
        this.terrain = new ImageIcon("assets\\icons8-terrain-24.png");
        this.maison = new ImageIcon("assets\\icons8-niche-pour-chien-24.png");
        this.piece = new ImageIcon("assets\\icons8-chambre-24.png");
        this.plus = new ImageIcon("assets\\icons8-plus-24.png");
        this.porte = new ImageIcon("assets\\icons8-porte-24.png");
        this.fenetre = new ImageIcon("assets\\icons8-fenêtre-ouverte-24.png");
        this.moins = new ImageIcon("assets\\icons8-moins-24.png");
        this.poubelle = new ImageIcon("assets\\icons8-poubelle-24.png");

        this.nouvelleFenetre = new ImageIcon("assets\\icons8-nouvelle-fenêtre-24.png");
        this.nouveau = new ImageIcon("assets\\icons8-new-file-24px.png");
        this.ouvrir = new ImageIcon("assets\\icons8-new-folder-24px.png");
        this.fermer = new ImageIcon("assets\\icons8-close-24.png");
        this.sauvegarder = new ImageIcon("assets\\icons8-sauvegarder-24.png");
    }

    public ImageIcon getTerrain() {
        return terrain;
    }

    
    public ImageIcon getPoubelle() {
        return poubelle;
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

    public ImageIcon getNouvelleFenetre() {
        return nouvelleFenetre;
    }

    public void setNouvelleFenetre(ImageIcon nouvelleFenetre) {
        this.nouvelleFenetre = nouvelleFenetre;
    }

    public ImageIcon getNouveau() {
        return nouveau;
    }

    public void setNouveau(ImageIcon nouveau) {
        this.nouveau = nouveau;
    }

    public ImageIcon getFermer() {
        return fermer;
    }

    public void setFermer(ImageIcon fermer) {
        this.fermer = fermer;
    }

    public ImageIcon getSauvegarder() {
        return sauvegarder;
    }

    public void setSauvegarder(ImageIcon sauvegarder) {
        this.sauvegarder = sauvegarder;
    }

    public void setTerrain(ImageIcon terrain) {
        this.terrain = terrain;
    }

    public void setMaison(ImageIcon maison) {
        this.maison = maison;
    }

    public void setPiece(ImageIcon piece) {
        this.piece = piece;
    }

    public void setPlus(ImageIcon plus) {
        this.plus = plus;
    }

    public void setPorte(ImageIcon porte) {
        this.porte = porte;
    }

    public void setFenetre(ImageIcon fenetre) {
        this.fenetre = fenetre;
    }

    public void setMoins(ImageIcon moins) {
        this.moins = moins;
    }

    public void setPoubelle(ImageIcon poubelle) {
        this.poubelle = poubelle;
    }

    public ImageIcon getOuvrir() {
        return ouvrir;
    }

    public void setOuvrir(ImageIcon ouvrir) {
        this.ouvrir = ouvrir;
    }
}