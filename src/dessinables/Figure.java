package dessinables;

import java.awt.Graphics;
import java.util.List;

public abstract class Figure {
    public void dessiner(Graphics g) {}
    public abstract List<Point> getPoints();
}
