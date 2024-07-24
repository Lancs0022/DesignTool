package dessinables.elementsplan;

import dessinables.geometrie.Point;
import java.util.ArrayList;
import java.util.List;

public class Maison extends ElementDuPlan {
    private Terrain terrain;
    private List<Piece> pieces;

    public Maison(Point pointDepart, double largeur, double hauteur, String nom, Terrain terrain) {
        super(pointDepart, largeur, hauteur, nom);
        this.terrain = terrain;
        this.pieces = new ArrayList<>();
    }

    public boolean peutAjouterPiece(Piece piece) {
        // Vérifier si la pièce est entièrement dans la maison
        if (piece.ptDepart.getX() < this.ptDepart.getX() ||
            piece.ptDepart.getY() < this.ptDepart.getY() ||
            piece.ptDepart.getX() + piece.getLargeur() > this.ptDepart.getX() + this.getLargeur() ||
            piece.ptDepart.getY() + piece.getHauteur() > this.ptDepart.getY() + this.getHauteur()) {
            System.out.println("Pièce hors des limites de la maison");
            System.out.println("Pièce: " + piece.ptDepart + ", " + piece.getLargeur() + "x" + piece.getHauteur());
            System.out.println("Maison: " + this.ptDepart + ", " + this.getLargeur() + "x" + this.getHauteur());
            return false;
        }
    
        // Vérifier si la pièce ne se superpose pas avec les pièces existantes
        for (Piece p : pieces) {
            if (piece.intersect(p)) {
                System.out.println("Collision détectée avec une pièce existante");
                return false;
            }
        }
    
        return true;
    }
    
    public boolean ajouterPiece(Piece piece) {
        if (peutAjouterPiece(piece)) {
            pieces.add(piece);
            return true;
        }
        return false;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public List<Piece> getPieces() {
        return pieces;
    }
}
