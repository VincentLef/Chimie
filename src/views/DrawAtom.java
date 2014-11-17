package views;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.AnAtom;

public class DrawAtom extends JPanel {
    private static final long serialVersionUID = -5900403414494400073L;
    private static final File DEFAULT_IMAGE = new File("res\\Icons\\default.png");
    public static final int SIZE = 20;

    private AnAtom atom;

    public DrawAtom(AnAtom a, MovingThread thread) {
        this.atom = a;
        this.setPreferredSize(new Dimension(20, 20));
        this.addMouseListener(new AtomPanelMouseListener(thread));
    }

    public void paintComponent(Graphics g) {
        try {
            this.drawImage(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawImage(Graphics g) throws IOException {
        Image im = ImageIO.read(this.getSpritePath());
        g.drawImage(im, 0, 0, SIZE, SIZE, null);
    }

    private File getSpritePath() {
        File f = new File("res\\Icons\\" + atom.getAtom().getSymbol() + ".png");
        if (!f.exists()) {
            return DEFAULT_IMAGE;
        }
        return f;
    }

    public AnAtom getAtom() {
        return atom;
    }

    public static final class AtomPanelMouseListener implements MouseListener {

        private MovingThread thread;

        public AtomPanelMouseListener(MovingThread thread) {
            super();
            this.thread = thread;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            this.thread.released();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            DrawAtom draw = this.getPanelPressed(e);
            this.raisePressedFlagToMovingThread(draw);
        }

        private DrawAtom getPanelPressed(MouseEvent e) {
            Component cpt = e.getComponent();
            return (DrawAtom) cpt;
        }

        private void raisePressedFlagToMovingThread(DrawAtom panel) {
            this.thread.pressed(panel);
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }
    }
}
