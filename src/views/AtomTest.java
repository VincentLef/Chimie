package views;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import model.AnAtom;
import model.Atom;
import model.CovalentBond;
import model.Molecule;


public class AtomTest extends JPanel {

    private static final long serialVersionUID = 168393654472992047L;

    private ArrayList<DrawAtom> atomPanels;
    private MovingThread thread;
    private ArrayList<AnAtom> atoms;
    
    private Molecule goal;

    public AtomTest(Molecule mol) {
        this.goal = mol;
        this.atomPanels = new ArrayList<DrawAtom>();
        this.thread = new MovingThread();
        
        this.createAtomsList();
        this.draw();
        this.addMouseListener(new AtomTestMouseController());

        this.thread.start();
    }
    
    private void createAtomsList() {
        this.atoms = new ArrayList<AnAtom> ();
        for(Atom atom : this.goal.getStructure()) {
            this.atoms.add(new AnAtom(atom, this.atoms));
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(g);

        for (int i = 0; i < atomPanels.size(); ++i) {
            for (int j = i + 1; j < atomPanels.size(); ++j) {
                drawBond(graphics, atomPanels.get(i), atomPanels.get(j));
            }
        }
    }

    private void draw() {
        for (AnAtom atom : this.atoms) {
            this.addAtomPanel(atom);
        }
    }

    private void addAtomPanel(AnAtom atom) {
        DrawAtom panel = new DrawAtom(atom, this.thread);
        atomPanels.add(panel);
        this.add(panel);
    }

    private void drawBond(Graphics g, AtomPanel atom1, AtomPanel atom2) {
        BondDrawer bd = new BondDrawer(atom1, atom2);
        bd.setGraphics(g);
        bd.paintBond();
    }


    public CovalentBond getCovalentBondAt(Point pt) {
        for (AnAtom atom : this.atoms) {
            CovalentBond cov = atom.getCovalentBondAt(pt);
            if (cov != null) {
                return cov;
            }
        }
        return null;
    }
    
    public boolean moleculeIsValid() {
        return this.goal.isComplete(this.atoms);
    }

    public final class AtomTestMouseController implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            CovalentBond cov = getCovalentBondAt(e.getPoint());
            if (cov != null) {
                cov.addBond();
                if(moleculeIsValid()){
                    System.out.println("Done !!");
                }
            }
            repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

    }
}
