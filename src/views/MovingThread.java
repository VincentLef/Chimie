package views;
import java.awt.MouseInfo;
import java.awt.Point;


public class MovingThread extends Thread {

    private boolean pressed;
    private DrawAtom atom;
    private static boolean stopThread = false;

    public MovingThread() {
        this.atom = null;
        this.pressed = false;
    }
    
    public void pressed(DrawAtom a) {
        this.atom = a;
        this.pressed = true;
    }

    public void released() {
        this.pressed = false;
    }

    @Override
    public void run() {
        while (!stopThread) {
            this.processMoving();
        }
    }

    private void processMoving() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {}

        if (pressed) {
            this.movePanel();
        }
    }

    private void movePanel() {
        Point click = this.getClickPositionInParent ();
        atom.setLocation(click);
        this.repaintParent();
    }

    private Point getClickPositionInParent() {
        Point pt = MouseInfo.getPointerInfo().getLocation();
        Point windowPos = this.atom.getParent().getLocationOnScreen();

        return new Point((int) (pt.getX() - windowPos.getX()), (int) (pt.getY() - windowPos.getY()));
    }

    private void repaintParent() {
        this.atom.getParent().repaint();
    }

    public static void stopAll() {
        stopThread = true;
    }
}
