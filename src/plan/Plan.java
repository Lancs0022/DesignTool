package plan;
import javax.swing.JPanel;

import dessinables.Point_2D;
import java.awt.Color;
import java.awt.Graphics;

public class Plan extends JPanel{
    protected int pixelParUnite;
    protected Point_2D centre = new Point_2D();
    public Plan () {
    //     System.out.println("-- Constructeur avec paramètres vide --");
    setUnitePx(40);
    //     int largeurFenetre = this.getWidth();
    //     int hauteurFenetre = this.getHeight();        
    //     System.out.println("hauteur de la fenetre = " + hauteurFenetre + "\nLargeur de la fenetre = " + largeurFenetre);
    //     System.out.println("hauteur de la fenetre/2 = " + hauteurFenetre/2 + "\nLargeur de la fenetre/2 = " + largeurFenetre/2);
    //     centre.setX(0.0); centre.setY(0.0);
    //     centre.setAbsEnPixel(largeurFenetre/2);
    //     centre.setOrdEnPixel(hauteurFenetre/2);
    }
    
    public Point_2D convertirCoordonneesEnPixels(double x, double y) {
        Point_2D point = new Point_2D(x, y);
        point.setAbsEnPixel((int) (x*pixelParUnite + centre.getAbsEnPixel()));
        point.setOrdEnPixel(centre.getOrdEnPixel() - (int) (y*pixelParUnite));
        return point;
    }
    public void convertirCoordonneesEnPixels(Point_2D point){
        point.setAbsEnPixel((int) (point.getAbsEnPixel()*pixelParUnite + centre.getAbsEnPixel()));
        point.setOrdEnPixel(centre.getOrdEnPixel() - (int) (point.getOrdEnPixel()*pixelParUnite));
    }

    public double getUnitePx() {
        return pixelParUnite;
    }
    public void setUnitePx(int unitePx) {
        this.pixelParUnite = unitePx;
    }
    public Point_2D getCentre() {
        return centre;
    }
    public void setCentre(Point_2D centre) {
        this.centre = centre;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Définir le centre au milieu du panneau
        centre.setAbsEnPixel(this.getWidth() / 2);
        centre.setOrdEnPixel(this.getHeight() / 2);
        System.out.println("hauteur de la fenetre = " + this.getHeight() + "\nLargeur de la fenetre = " + this.getWidth());

        cadrillageDynamique(g);
    }

    private void cadrillageDynamique(Graphics g){
        // Dessiner le grillage centré
        g.setColor(Color.LIGHT_GRAY);

        // Dessiner les lignes horizontales
        for (int y = centre.getOrdEnPixel(); y < getHeight(); y += pixelParUnite) {
            g.drawLine(0, y, getWidth(), y);
        }
        for (int y = centre.getOrdEnPixel(); y > 0; y -= pixelParUnite) {
            g.drawLine(0, y, getWidth(), y);
        }

        // Dessiner les lignes verticales
        for (int x = centre.getAbsEnPixel(); x < getWidth(); x += pixelParUnite) {
            g.drawLine(x, 0, x, getHeight());
        }
        for (int x = centre.getAbsEnPixel(); x > 0; x -= pixelParUnite) {
            g.drawLine(x, 0, x, getHeight());
        }

        // Dessiner les lignes de croix au centre
        g.setColor(Color.RED);
        g.drawLine(centre.getAbsEnPixel(), 0, centre.getAbsEnPixel(), getHeight());
        g.drawLine(0, centre.getOrdEnPixel(), getWidth(), centre.getOrdEnPixel());
    }
}
