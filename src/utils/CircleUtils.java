package utils;

import java.awt.Point;

import util.geometry.Circle;
import util.geometry.CircleCircleIntersection;
import util.geometry.Vector2;

public class CircleUtils {
    
    public static Point[] findIntersect(Vector2 p1, Vector2 p2, int r1, int r2) {

        Circle c1 = new Circle(p1, r1);
        Circle c2 = new Circle(p2, r2);

        CircleCircleIntersection cIn = new CircleCircleIntersection(c1, c2);
        Vector2[] inters = cIn.getIntersectionPoints();

        Point[] pts = {
                new Point((int) inters[0].x, (int) inters[0].y),
                new Point((int) inters[1].x, (int) inters[1].y)
        };

        return pts;
    }
}
