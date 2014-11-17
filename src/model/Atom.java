package model;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Atom extends Particule{
    private int allowedNumberOfBonds;

    public Atom(String name, String symbol, int allowedNumberOfBonds) {
        super(name, symbol);
        this.allowedNumberOfBonds = allowedNumberOfBonds;
    }

    public String toString() {
        return this.getSymbol();
    }

    public int getNumberOfBondsAllowed() {
        return this.allowedNumberOfBonds;
    }

    public void drawParticule(Graphics g, JPanel pnl){
        
    }
}
