package model;
import java.awt.Point;
import java.awt.Polygon;

import util.geometry.Vector2;
import utils.CircleUtils;
import views.DrawAtom;

public class CovalentBond {
    private static final int MAX_NUMBER_OF_BOND = 3;
    
    private AnAtom atom1;
    private AnAtom atom2;

    private Point zonePoint1BoundToAtom1, zonePoint2BoundToAtom1;
    private Point zonePoint1BoundToAtom2, zonePoint2BoundToAtom2;

    private int electronShared;

    public CovalentBond(AnAtom a1, AnAtom a2, int elec) {
        this.atom1 = a1;
        this.atom2 = a2;
        this.electronShared = elec;
    }

    public AnAtom getAtomLinkedTo(AnAtom anAtom) {
        return (anAtom == this.atom1 ? this.atom2 : this.atom1);
    }

    public boolean contains(Point pt) {
        if (this.zonePoint1BoundToAtom1 == null) {
            return false;
        }

        int[] xs = {
                this.zonePoint1BoundToAtom1.x, this.zonePoint2BoundToAtom1.x,
                this.zonePoint1BoundToAtom2.x, this.zonePoint2BoundToAtom2.x,
        };
        int[] ys = {
                this.zonePoint1BoundToAtom1.y, this.zonePoint2BoundToAtom1.y,
                this.zonePoint1BoundToAtom2.y, this.zonePoint2BoundToAtom2.y,
        };

        return (new Polygon(xs, ys, 4)).contains(pt);
    }
    
    public void setPointBoundTo(AnAtom atom, Point p1, Point p2) {
        if (this.atom1 == atom) {
            this.zonePoint1BoundToAtom1 = p1;
            this.zonePoint2BoundToAtom1 = p2;
        } else if (this.atom2 == atom) {
            this.zonePoint1BoundToAtom2 = p1;
            this.zonePoint2BoundToAtom2 = p2;
        }
    }

    public void addBond() {
        if (this.atom1.canBeBound() && this.atom2.canBeBound()) {
            this.electronShared = (this.electronShared + 1) % (MAX_NUMBER_OF_BOND + 1);
        } else {
            this.electronShared = 0;
        }
    }

    public int getNumberOfBond() {
        return this.electronShared;
    }

    public void destroyZone() {
        this.zonePoint1BoundToAtom1 = null;
        this.zonePoint2BoundToAtom1 = null;
        this.zonePoint1BoundToAtom2 = null;
        this.zonePoint2BoundToAtom2 = null;
    }

    public Point[] getPointsBoundTo(AnAtom atom) {
        Point[] pts = new Point[2];
        if (this.atom1 == atom) {
            pts[0] = this.zonePoint1BoundToAtom1;
            pts[1] = this.zonePoint2BoundToAtom1;
        } else if (this.atom2 == atom) {
            pts[0] = this.zonePoint1BoundToAtom2;
            pts[1] = this.zonePoint2BoundToAtom2;
        } else {
            pts = null;
        }
        return pts;
    }

    
    public void createZone(Point p1, Point p2) {
        Vector2 c1 = new Vector2((double) p1.x + DrawAtom.SIZE / 2, (double) p1.y + DrawAtom.SIZE / 2);
        Vector2 c2 = new Vector2((double) p2.x + DrawAtom.SIZE / 2, (double) p2.y + DrawAtom.SIZE / 2);        
        int r1 = DrawAtom.SIZE / 2;
        int r2 = (int) Math.sqrt(Math.pow(p1.distance(p2), 2) + Math.pow(r1, 2));
        
        Point[] pts = CircleUtils.findIntersect(c1, c2, r1, r2);
        this.setPointBoundTo(atom1, pts[0], pts[1]);

        pts = CircleUtils.findIntersect(c2, c1, r1, r2);
        this.setPointBoundTo(atom2, pts[0], pts[1]);
    }
}
