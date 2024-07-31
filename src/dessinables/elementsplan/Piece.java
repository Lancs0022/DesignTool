package dessinables.elementsplan;

import dessinables.geometrie.Point;
import dessinables.geometrie.Vecteur;
import outils.CalculsVectoriels;

import java.util.ArrayList;
import java.util.List;

public class Piece extends ElementDuPlan implements Conteneur, Contenu {
    private List<Contenu> contenus;
    private List<Ouverture> ouvertures;

    public Piece(Point pointDepart, double largeur, double hauteur, String nom, Conteneur parent) {
        super(pointDepart, largeur, hauteur, nom, parent);
        this.contenus = new ArrayList<>();
        this.ouvertures = new ArrayList<>();
    }

    public boolean peutAjouterElement(ElementDuPlan element) {
        // Vérifier si l'élément est entièrement dans la pièce
        if (!element.getRectangle().neDepassePasConteneur(this.getRectangle())) {
            System.out.println("Élément hors des limites de la pièce");
            System.out
                    .println("Élément: " + element.ptDepart + ", " + element.getLargeur() + "x" + element.getHauteur());
            System.out.println("Pièce: " + this.ptDepart + ", " + this.getLargeur() + "x" + this.getHauteur());
            return false;
        }

        // Vérifier si l'élément ne se superpose pas avec les éléments existants
        for (Contenu contenu : contenus) {
            if (element.intersect((ElementDuPlan) contenu)) {
                System.out.println("Collision détectée avec un élément existant");
                return false;
            }
        }

        return true;
    }

    @Override
    public List<Contenu> getElementsFilles() {
        return contenus;
    }

    @Override
    public boolean ajouterContenu(ElementDuPlan element) {
        if (peutAjouterElement(element)) {
            contenus.add((Contenu) element);
            return true;
        }
        return false;
    }

    public void mettreAJourOuverture(Ouverture ouverture) {
        // Vérifier si l'ouverture existe déjà dans le conteneur
        for (Ouverture existingOuverture : this.ouvertures) {
            if (existingOuverture.equals(ouverture)) {
                // Mettre à jour le vecteur de l'ouverture avec de nouvelles coordonnées
                existingOuverture.getVecteur().setP1(ouverture.getVecteur().getP1());
                existingOuverture.getVecteur().setP2(ouverture.getVecteur().getP2());
                return;
            }
        }
        // Ajouter l'ouverture si elle n'existe pas déjà
        this.ouvertures.add(ouverture);
    }

    public List<Ouverture> getOuvertures(String face) {
        List<Ouverture> ouverturesSurFace = new ArrayList<>();
        for (Ouverture ouverture : ouvertures) {
            if (ouverture.getFace().equalsIgnoreCase(face)) {
                ouverturesSurFace.add(ouverture);
            }
        }
        return ouverturesSurFace;
    }

    public boolean peutPlacerOuverture(Ouverture ouverture, Conteneur parent, String face) {
        Vecteur vecteurOuverture = ouverture.getVecteur();
        Vecteur faceVecteur = parent.trouverFace(face);
    
        // Vérifier si les vecteurs sont alignés
        boolean sontAlignes = CalculsVectoriels.vectSontAlignes(vecteurOuverture, faceVecteur);
    
        // Conditions spécifiques pour la face sud
        boolean p1DansLimites = true;
        if ("sud".equalsIgnoreCase(face)) {
            p1DansLimites = vecteurOuverture.getP1().getY() == faceVecteur.getP1().getY() &&
                            vecteurOuverture.getP2().getY() == faceVecteur.getP1().getY() &&
                            vecteurOuverture.getP1().getX() >= Math.min(faceVecteur.getP1().getX(), faceVecteur.getP2().getX()) &&
                            vecteurOuverture.getP2().getX() <= Math.max(faceVecteur.getP1().getX(), faceVecteur.getP2().getX());
        } else {
            // Conditions pour les autres faces (nord, est, ouest)
            p1DansLimites = vecteurOuverture.getP1().getX() >= Math.min(faceVecteur.getP1().getX(), faceVecteur.getP2().getX()) &&
                            vecteurOuverture.getP2().getX() <= Math.max(faceVecteur.getP1().getX(), faceVecteur.getP2().getX()) &&
                            vecteurOuverture.getP1().getY() >= Math.min(faceVecteur.getP1().getY(), faceVecteur.getP2().getY()) &&
                            vecteurOuverture.getP2().getY() <= Math.max(faceVecteur.getP1().getY(), faceVecteur.getP2().getY());
        }
    
        System.out.println("Vecteurs alignés: " + sontAlignes);
        System.out.println("P1 dans les limites: " + p1DansLimites);
        System.out.println("Vecteur ouverture: " + vecteurOuverture);
        System.out.println("Vecteur face: " + faceVecteur);
    
        return sontAlignes && p1DansLimites;
    }

    @Override
    public boolean ajouterOuverture(Ouverture ouverture) {
        if (ouvertures.add(ouverture)) {
            return true;
        }
        return false;
    }
}