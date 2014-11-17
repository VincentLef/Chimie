package model;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Molecule extends Particule{

    private ArrayList<Atom> structure;

    public Molecule(String name, String symbol, Atom... structure) {
        super(name, symbol);
        this.structure = new ArrayList<Atom>();

        for (Atom atom : structure) {
            this.structure.add(atom);
        }
    }

    /**
     * TODO a better method to check if the mol is complete (check the bonds between the atoms ?)
     * Check if the mol is complete for 2 points
     *  - The mol is composed of the same atoms than the original
     *  - All the atoms have reached their maximum number of bound (they are 'complete')
     */
    public boolean isComplete(ArrayList<AnAtom> mol) {
        ArrayList<Atom> structureTested = new ArrayList<Atom>();
        for (AnAtom atom : mol) {
            if (atom.canBeBound()) {
                return false;
            }
            structureTested.add(atom.getAtom());
        }
        return utils.CollectionUtils.containsSameElements(this.structure, structureTested);
    }

    public String toString() {
        return this.getSymbol();
    }

    public ArrayList<Atom> getStructure() {
        return this.structure;
    }

    @Override
    public void drawParticule(Graphics g, JPanel pnl) {
        // TODO Auto-generated method stub
        
    }
}
