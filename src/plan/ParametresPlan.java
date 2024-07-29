package plan;

import java.awt.Color;

public class ParametresPlan {
    private static int pixelsParMetre;
    private Color couleurBordTerrain;
    private Color couleurBordMaison;
    private Color couleurBordPiece;
    private Color couleurPorte;
    private Color couleurFenetre;
    private Color couleurFondTerrain;
    private Color couleurFondMaison;
    private Color couleurFondPiece;
    private String inputMode;

    public ParametresPlan() {
        pixelsParMetre = 10;
        this.inputMode = "None";
        this.couleurBordTerrain = Color.GREEN;
        this.couleurBordMaison = new Color(255, 140, 0);
        this.couleurBordPiece = Color.ORANGE;
        this.couleurPorte = Color.RED;
        this.couleurFenetre = Color.CYAN;
        this.couleurFondTerrain = new Color(0, 255, 0, 100);
        this.couleurFondMaison = new Color(255, 140, 0, 100);
        this.couleurFondPiece = new Color(255, 165, 0, 100);
    }

    public static int getPixelsParMetre() {
        return pixelsParMetre;
    }

    public void setPixelsParMetre(int pixelsParMetre) {
        ParametresPlan.pixelsParMetre = pixelsParMetre;
    }

    public String getInputMode() {
        return inputMode;
    }

    public void setInputMode(String inputMode) {
        this.inputMode = inputMode;
    }

    public Color getCouleurBordTerrain() {
        return couleurBordTerrain;
    }

    public void setCouleurBordTerrain(Color couleurBordTerrain) {
        this.couleurBordTerrain = couleurBordTerrain;
    }

    public Color getCouleurBordMaison() {
        return couleurBordMaison;
    }

    public void setCouleurBordMaison(Color couleurBordMaison) {
        this.couleurBordMaison = couleurBordMaison;
    }

    public Color getCouleurBordPiece() {
        return couleurBordPiece;
    }

    public void setCouleurBordPiece(Color couleurBordPiece) {
        this.couleurBordPiece = couleurBordPiece;
    }

    public Color getCouleurPorte() {
        return couleurPorte;
    }

    public void setCouleurPorte(Color couleurPorte) {
        this.couleurPorte = couleurPorte;
    }

    public Color getCouleurFenetre() {
        return couleurFenetre;
    }

    public void setCouleurFenetre(Color couleurFenetre) {
        this.couleurFenetre = couleurFenetre;
    }

    public Color getCouleurFondTerrain() {
        return couleurFondTerrain;
    }

    public void setCouleurFondTerrain(Color couleurFondTerrain) {
        this.couleurFondTerrain = couleurFondTerrain;
    }

    public Color getCouleurFondMaison() {
        return couleurFondMaison;
    }

    public void setCouleurFondMaison(Color couleurFondMaison) {
        this.couleurFondMaison = couleurFondMaison;
    }

    public Color getCouleurFondPiece() {
        return couleurFondPiece;
    }

    public void setCouleurFondPiece(Color couleurFondPiece) {
        this.couleurFondPiece = couleurFondPiece;
    }

    @Override
    public String toString() {
        return "ParametresPlan{" +
                "pixelsParMetre=" + pixelsParMetre +
                ", couleurBordTerrain=" + couleurBordTerrain +
                ", couleurBordMaison=" + couleurBordMaison +
                ", couleurBordPiece=" + couleurBordPiece +
                ", couleurPorte=" + couleurPorte +
                ", couleurFenetre=" + couleurFenetre +
                ", couleurFondTerrain=" + couleurFondTerrain +
                ", couleurFondMaison=" + couleurFondMaison +
                ", couleurFondPiece=" + couleurFondPiece +
                ", inputMode='" + inputMode + '\'' +
                '}';
    }
}
