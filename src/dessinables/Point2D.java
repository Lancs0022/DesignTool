package dessinables;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Point2D extends JPanel{
    private double abscisse;
    private double ordonne;
    public double getOrdonne() {
        return ordonne;
    }
    private int absPx;
    private int ordPy;

    static private int unitePx;
    static private Point2D centre = new Point2D();

    public Point2D(double abs, double ord, int hauteurFenetre, int largeurFenetre) {
        // hauteurFenetre = this.getHeight();
        // largeurFenetre = this.getWidth();
        System.out.println("hauteur de la fenetre = " + hauteurFenetre + "\nLargeur de la fenetre = " + largeurFenetre);
        System.out.println("hauteur de la fenetre/2 = " + hauteurFenetre/2 + "\nLargeur de la fenetre/2 = " + largeurFenetre/2);
        setCenter(largeurFenetre/2, hauteurFenetre/2);
        this.setX(abs); this.setY(ord);
        autoConvertXY(abs, ord);
    }
    public Point2D(){

    }
    public boolean drawPoint(){
        
        return true;
    }

    public void setCenter(int xPx, int yPx){
        centre.absPx = xPx;
        centre.ordPy = yPx;
        centre.abscisse = 0.0;
        centre.ordonne = 0.0;
    }
    
    private boolean autoConvertXY(double abs, double ord){
        this.absPx = ((int) (abs*unitePx + centre.absPx)) - 20;
        this.ordPy = ((int) (ord*unitePx + centre.ordPy)) - 20;
        return true;
    }

    public void setUnite(int unite) {
        unitePx = unite;
    }

    @Override
    public void paintComponent(Graphics g){
        g.drawOval(absPx, ordPy, 20, 20);
        
    }
    // public double getX(){
    //     return this.abscisse;
    // }
    // public double getY(){
    //     return this.ordonne;
    // }
    public void setX(double X){
        this.abscisse = X;
    }
    public void setY(double Y){
        this.ordonne = Y;
    }

    public double getAbscisse() {
        return abscisse;
    }

    public int getAbsPx() {
        return absPx;
    }

    public int getOrdPy() {
        return ordPy;
    }

    public static int getUnitePx() {
        return unitePx;
    }

    public static Point2D getCentre() {
        return centre;
    }
    
    public static void main(String[] args) {
        JFrame fenetre = new JFrame();
        // fenetre.setDefaultCloseOperation(EXIT_ON_CLOSE);
        fenetre.setSize(1080, 720);
        Point2D point = new Point2D(0, 0, fenetre.getHeight() , fenetre.getWidth());
        fenetre.setContentPane(point);
        fenetre.setVisible(true);
    }
}