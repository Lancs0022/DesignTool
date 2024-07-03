package dessinables;

import java.util.List;

public class Rectangle extends Polygone {
    public Rectangle(Point p1, Point p2, Point p3, Point p4) {
        super(List.of(p1, p2, p3, p4));
    }
}