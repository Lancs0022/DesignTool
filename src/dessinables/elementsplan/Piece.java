package dessinables.elementsplan;

import dessinables.geometrie.Point;
import dessinables.geometrie.RectangleEpais;
import dessinables.geometrie.Vecteur;
import outils.CalculsVectoriels;
import plan.ParametresPlan;

import java.util.ArrayList;
import java.util.List;

public class Piece extends ElementDuPlan implements Conteneur, Contenu {
    private List<Contenu> contenus;

    public Piece(Point pointDepart, double largeur, double hauteur, String nom, Conteneur parent) {
        super(pointDepart, largeur, hauteur, nom, parent);
        this.contenus = new ArrayList<>();
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

    // Nouvelle méthode pour ajouter du contenu collé sur les faces
    public boolean peutAjouterElementSurFace(Contenu contenu, String face) {
        Vecteur vecteurContenu = contenu.getVecteur();
        RectangleEpais rectangle = this.getRectangle();
        Vecteur faceVecteur;

        // Sélectionner le vecteur correspondant à la face
        switch (face.toLowerCase()) {
            case "nord":
                faceVecteur = rectangle.getFaceNord();
                break;
            case "est":
                faceVecteur = rectangle.getFaceEst();
                break;
            case "sud":
                faceVecteur = rectangle.getFaceSud();
                break;
            case "ouest":
                faceVecteur = rectangle.getFaceOuest();
                break;
            default:
                throw new IllegalArgumentException("Face inconnue : " + face);
        }

        System.out.println("Face concerné : " + faceVecteur);

        // Vérification de l'alignement du vecteur contenu sur la face sélectionnée
        if (!estColleSurFace(vecteurContenu, faceVecteur)) {
            System.out.println("Contenu non aligné sur la face " + face);
            return false;
        }

        // Vérifier si le vecteur ne se superpose pas avec les éléments existants
        for (Contenu existingContent : contenus) {
            if (CalculsVectoriels.intersect(vecteurContenu, existingContent.getVecteur())) {
                System.out.println("Collision détectée avec un élément existant sur la face");
                return false;
            }
        }

        return true;
    }

    private boolean estColleSurFace(Vecteur vecteur, Vecteur face) {
        // Vérifie si les vecteurs sont horizontaux ou verticaux
        boolean horizontal = face.getP1().getY() == face.getP2().getY();
        boolean vertical = face.getP1().getX() == face.getP2().getX();
        
        if (horizontal) {
            // Vérifie si le vecteur est aligné horizontalement sur la face
            int minX = Math.min(face.getP1().getX(), face.getP2().getX());
            int maxX = Math.max(face.getP1().getX(), face.getP2().getX());
            return vecteur.getP1().getY() == face.getP1().getY() &&
                   vecteur.getP2().getY() == face.getP2().getY() &&
                   vecteur.getP1().getX() >= minX &&
                   vecteur.getP2().getX() <= maxX;
        } else if (vertical) {
            // Vérifie si le vecteur est aligné verticalement sur la face
            int minY = Math.min(face.getP1().getY(), face.getP2().getY());
            int maxY = Math.max(face.getP1().getY(), face.getP2().getY());
            return vecteur.getP1().getX() == face.getP1().getX() &&
                   vecteur.getP2().getX() == face.getP2().getX() &&
                   vecteur.getP1().getY() >= minY &&
                   vecteur.getP2().getY() <= maxY;
        }
        
        return false;
    }

    @Override
    public List<Contenu> getElementsFilles() {
        return contenus;
    }

    @Override
    public boolean ajouterElement(ElementDuPlan element) {
        // if (peutAjouterElement(element)) {
        //     contenus.add((Contenu) element);
        //     return true;
        // }
        if (peutAjouterElementSurFace((Contenu) element, this.getFace())) {
            contenus.add((Contenu) element)
            return true;
        }
        return false;
    }

    @Override
    public String getFace() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFace'");
    }
}
