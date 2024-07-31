package dessinables.elementsplan;

import dessinables.geometrie.Point;
import dessinables.geometrie.Vecteur;
import outils.CalculsVectoriels;

import java.util.ArrayList;
import java.util.List;

public class Maison extends ElementDuPlan implements Conteneur, Contenu {
    private List<Contenu> contenus;

    public Maison(Point ptDepart, double largeur, double hauteur, String nom, Conteneur parent) {
        super(ptDepart, largeur, hauteur, nom, parent);
        this.contenus = new ArrayList<>();
    }

    public boolean peutAjouterPiece(Piece piece) {
        // Vérifier si la pièce est entièrement dans la maison
        if (!piece.getRectangle().neDepassePasConteneur(this.getRectangle())) {
            System.out.println("Pièce hors des limites de la maison");
            return false;
        }

        // Vérifier si la pièce ne se superpose pas avec les contenus existants
        for (Contenu contenu : contenus) {
            if (contenu instanceof Piece && piece.intersect((Piece) contenu)) {
                System.out.println("Collision détectée avec une pièce existante");
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
        if (element instanceof Piece) {
            if (peutAjouterPiece((Piece) element)) {
                contenus.add((Contenu) element);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean ajouterOuverture(Ouverture ouverture) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ajouterOuverture'");
    }

    @Override
    public void mettreAJourOuverture(Ouverture ouverture) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mettreAJourOuverture'");
    }
}