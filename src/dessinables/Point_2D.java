package dessinables;
public class Point_2D {
    private Double abscisse;
    private Double ordonne;
    private int absEnPixel;
    private int ordEnPixel;

    
    public Point_2D(Double abs, Double ord) {
        this.setX(abs); this.setY(ord);
    }
    public Point_2D(){
        this.setX(0.0); this.setY(0.0);
    }

    public Double getX(){
        return this.abscisse;
    }
    public Double getY(){
        return this.ordonne;
    }
    public void setX(Double X){
        this.abscisse = X;
    }
    public void setY(Double Y){
        this.ordonne = Y;
    }
    public int getAbsEnPixel() {
        return absEnPixel;
    }
    public void setAbsEnPixel(int absEnPixel) {
        this.absEnPixel = absEnPixel;
    }
    public Double getAbscisse() {
        return abscisse;
    }
    public void setAbscisse(Double abscisse) {
        this.abscisse = abscisse;
    }
    public Double getOrdonne() {
        return ordonne;
    }
    public void setOrdonne(Double ordonne) {
        this.ordonne = ordonne;
    }
    public int getOrdEnPixel() {
        return ordEnPixel;  
    }
    public void setOrdEnPixel(int setOrdEnPixel) {
        this.ordEnPixel = setOrdEnPixel;
    }
}