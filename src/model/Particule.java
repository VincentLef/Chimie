package model;

import java.awt.Graphics;

import javax.swing.JPanel;

public abstract class Particule {
    private String name;
    private String symbol;
    
    public Particule(String name, String symbol){
        this.name = name;
        this.symbol = symbol;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getSymbol() {
        return this.symbol;
    }
    
    abstract public void drawParticule(Graphics g, JPanel pnl);
}
