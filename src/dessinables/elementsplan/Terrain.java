package dessinables.elementsplan;

import dessinables.geometrie.Point;
import dessinables.geometrie.Vecteur;
import outils.CalculsVectoriels;

import java.util.ArrayList;
import java.util.List;

public class Terrain extends ElementDuPlan implements Conteneur {
    private List<Contenu> contenus;
    private List<Ouverture> ouvertures;

    public Terrain(Point pointDepart, int largeur, int hauteur, String nom) {
        super(pointDepart, largeur, hauteur, nom);
        this.contenus = new ArrayList<>();
    }

    public boolean peutAjouterMaison(Maison maison) {
        // Vérifier si la maison est entièrement dans le terrain
        if (!maison.getRectangle().neDepassePasConteneur(this.getRectangle())) {
            System.out.println("Maison hors des limites du terrain");
            System.out.println("Maison: " + maison.ptDepart + ", " + maison.getLargeur() + "x" + maison.getHauteur());
            System.out.println("Terrain: " + this.ptDepart + ", " + this.getLargeur() + "x" + this.getHauteur());
            return false;
        }
    
        // Vérifier si la maison ne se superpose pas avec les contenus existants
        for (Contenu contenu : contenus) {
            if (contenu instanceof Maison && maison.intersect((Maison) contenu)) {
                System.out.println("Collision détectée avec une maison existante");
                return false;
            }
        }
    
        return true;
    }

    public boolean peutPlacerOuverture(Ouverture ouverture, Conteneur parent, String face) {
    Vecteur vecteurOuverture = ouverture.getVecteur();
    Vecteur faceVecteur = parent.trouverFace(face);

    // Vérifie que l'ouverture est dans les limites de la face
    return CalculsVectoriels.vectSontAlignes(vecteurOuverture, faceVecteur) &&
           vecteurOuverture.getP1().getX() >= faceVecteur.getP1().getX() &&
           vecteurOuverture.getP2().getX() <= faceVecteur.getP2().getX() &&
           vecteurOuverture.getP1().getY() >= faceVecteur.getP1().getY() &&
           vecteurOuverture.getP2().getY() <= faceVecteur.getP2().getY();
    }

    @Override
    public List<Contenu> getElementsFilles() {
        return contenus;
    }

    @Override
    public boolean ajouterContenu(ElementDuPlan element) {
        if (element instanceof Maison) {
            if (peutAjouterMaison((Maison) element)) {
                contenus.add((Contenu) element);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean ajouterOuverture(Ouverture ouverture) {
        if (peutPlacerOuverture(ouverture, parent, nom)) {
            ouvertures.add(ouverture);
            return true;
        }
        return false;
    }

    @Override
    public void mettreAJourOuverture(Ouverture ouverture) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mettreAJourOuverture'");
    }

}