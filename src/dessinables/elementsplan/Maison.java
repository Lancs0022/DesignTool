package dessinables.elementsplan;

import dessinables.geometrie.Point;
import java.util.ArrayList;
import java.util.List;

public class Maison extends ElementDuPlan {
    private List<Piece> pieces;

    public Maison(Point pointDepart, double largeur, double hauteur, String nom, Terrain parent) {
        super(pointDepart, largeur, hauteur, nom, parent);
        this.pieces = new ArrayList<>();
    }

    public void ajouterPiece(Piece piece) {
        pieces.add(piece);
    }

    public List<Piece> getPieces() {
        return pieces;
    }
}
