package trash;
public class Point_2D {
    private int x;
    private int y;

    public Point_2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point_2D(){
        this.setX(0); this.setY(0);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}