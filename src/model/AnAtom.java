package model;
import java.awt.Point;
import java.util.ArrayList;

public class AnAtom {

    private Atom type;
    private ArrayList<CovalentBond> bonds;

    private Point coord;

    public AnAtom(Atom type, ArrayList<AnAtom> atomList) {
        this.type = type;
        this.bonds = new ArrayList<CovalentBond>();

        this.linkToAllAtoms(atomList);
    }

    private void linkToAllAtoms(ArrayList<AnAtom> atomList) {
        for(AnAtom atom : atomList) {
            this.bindAtom(atom);
        }
    }

    public void bindAtom(AnAtom atom) {
        if (this.canBeBindedWith(atom)) {
            CovalentBond cov = new CovalentBond(this, atom, 0);
            this.bonds.add(cov);
            atom.addCovalentBond(cov);
        }
    }

    private boolean canBeBindedWith(AnAtom atom) {
        return atom != this && !this.isBindedWith(atom) && !atom.isBindedWith(this);
    }

    public boolean isBindedWith(AnAtom atom) {
        return this.getBondWith(atom) != null;
    }

    public CovalentBond getBondWith(AnAtom atom) {
        for (CovalentBond cov : this.bonds) {
            if (cov.getAtomLinkedTo(this) == atom) {
                return cov;
            }
        }
        return null;
    }

    private void addCovalentBond(CovalentBond cov) {
        this.bonds.add(cov);
    }

    public void addBondToCovalentBondWith(AnAtom atom) {
        this.getBondWith(atom).addBond();
    }

    public CovalentBond getCovalentBondAt(Point pt) {
        for (CovalentBond cov : this.bonds) {
            if (cov.contains(pt)) {
                return cov;
            }
        }
        return null;
    }

    public boolean canBeBound() {
        int bond = getNumberOfBond();
        return bond < this.type.getNumberOfBondsAllowed();

    }

    private int getNumberOfBond() {
        int bond = 0;
        for (CovalentBond cov : this.bonds) {
            bond += cov.getNumberOfBond();
        }
        return bond;
    }

    public void setCoord(Point p) {
        this.coord = p;
    }

    public Point getCoord() {
        return this.coord;
    }

    public Atom getAtom() {
        return this.type;
    }

}
