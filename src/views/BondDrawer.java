package views;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

import model.AnAtom;
import model.CovalentBond;

public class BondDrawer {
    private static final double MINIMUM_DISTANCE_FOR_LINK = 150;

    private AnAtom atom1, atom2;
    private CovalentBond bond;
    private Point p1, p2;
    private Graphics drawer;

    public BondDrawer(DrawAtom atom1, DrawAtom atom2) {
        this.atom1 = atom1.getAtom();
        this.atom2 = atom2.getAtom();

        this.bond = this.atom1.getBondWith(this.atom2);

        this.p1 = atom1.getLocation();
        this.p2 = atom2.getLocation();
    }

    public void setGraphics(Graphics g) {
        this.drawer = g;
    }

    public void paintBond() {
        if (this.bond.getNumberOfBond() > 0) {
            this.drawBondBetween();
        } else {
            this.drawPossibleBondBetween();
        }
    }

    private void drawPossibleBondBetween() {
        if (this.p1.distance(this.p2) < MINIMUM_DISTANCE_FOR_LINK && atom1.canBeBound()
                && atom2.canBeBound()) {
            this.createBashedGraphics();
            this.bond.createZone(this.p1, this.p2);
            this.drawLine();
            drawer.dispose();
        } else {
            this.destroyZone();
        }
    }

    private void drawLine() {
        this.drawer.drawLine(this.p1.x + DrawAtom.SIZE / 2, this.p1.y + DrawAtom.SIZE / 2,
                this.p2.x + DrawAtom.SIZE / 2, this.p2.y + DrawAtom.SIZE / 2);
    }

    private void createBashedGraphics() {
        Graphics2D g2d = (Graphics2D) this.drawer.create();
        Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
                new float[] {
                    9
                }, 0);
        g2d.setStroke(dashed);
        this.drawer = g2d;
    }

    private void destroyZone() {
        this.bond.destroyZone();
    }

    private void drawBondBetween() {
        this.drawBond();
        this.bond.createZone(this.p1, this.p2);
    }
    
    private void drawBond() {
        Point[] pts = this.bond.getPointsBoundTo(this.atom1);
        Point[] linesCoord1 = this.calculatePointsForBoundLines(pts[0], pts[1]);

        pts = this.bond.getPointsBoundTo(this.atom2);
        Point[] linesCoord2 = this.calculatePointsForBoundLines(pts[0], pts[1]);

        for (int i = 0; i < this.bond.getNumberOfBond(); ++i) {
            this.drawer.drawLine(linesCoord1[i].x, linesCoord1[i].y, linesCoord2[this.bond.getNumberOfBond() - (i + 1)].x, linesCoord2[this.bond.getNumberOfBond() - (i + 1)].y);
        }
    }

    private Point[] calculatePointsForBoundLines(Point p1, Point p2) {
        int x_intervale = (p2.x - p1.x) / bond.getNumberOfBond();
        int y_intervale = (p2.y - p1.y) / bond.getNumberOfBond();

        Point[] pts = new Point[bond.getNumberOfBond()];
        for (int x = x_intervale / 2, y = y_intervale / 2, i = 0; i < bond.getNumberOfBond(); x += x_intervale, y += y_intervale, ++i) {
            pts[i] =  new Point(p1.x + x, p1.y + y);
        }

        return pts;
    }



}
