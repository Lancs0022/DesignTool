package dessinables.elementsplan;

import dessinables.geometrie.Point;
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

    @Override
    public List<Contenu> getElementsFilles() {
        return contenus;
    }

    @Override
    public boolean ajouterElement(ElementDuPlan element) {
        if (element instanceof Piece) {
            if (peutAjouterPiece((Piece) element)) {
                contenus.add((Contenu) element);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getFace() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFace'");
    }

    @Override
    public boolean peutAjouterElementSurFace(Contenu element, String face) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'peutAjouterElementSurFace'");
    }
}