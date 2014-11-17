package model;

import java.util.ArrayList;

import javax.swing.JFrame;

import utils.XMLAtomLoader;
import utils.XMLMoleculeLoader;
import views.AtomTest;
import views.MovingThread;

public class Game {

    private static final String ATOM_FILE = "res\\atoms.xml";
    private static final String MOLECULE_FILE = "res\\molecules.xml";
    public static AtomTest mainFrame;
    public static ArrayList<AnAtom> atomsOnScreen;

    private static ArrayList<Atom> atoms = new ArrayList<Atom>();
    private static ArrayList<Molecule> molecules = new ArrayList<Molecule>();

    public static void main(String[] args) {
        loadAtoms();
        loadMolecules();

        JFrame wind = new JFrame("Test");
        wind.setSize(550, 350);
        wind.setResizable(false);

        mainFrame = new AtomTest(Game.findMoleculeBySymbol("CO2"));

        wind.setContentPane(mainFrame);

        wind.setVisible(true);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                MovingThread.stopAll();
            }
        });
    }

    private static void loadAtoms() {
        XMLAtomLoader loader = new XMLAtomLoader(ATOM_FILE);
        loader.loadListfromXmlFile();
        atoms = loader.getAtomList();
    }

    private static void loadMolecules() {
        XMLMoleculeLoader loader = new XMLMoleculeLoader(MOLECULE_FILE);
        loader.loadListfromXmlFile();
        molecules = loader.getMoleculeList();
    }

    public static Atom findAtomByName(String name) {
        for (Atom a : atoms) {
            if (a.getName().equals(name)) {
                return a;
            }
        }
        return null;
    }

    public static Molecule findMoleculeBySymbol(String symbol) {
        for (Molecule m : molecules) {
            if (m.getSymbol().equals(symbol)) {
                return m;
            }
        }
        return null;
    }

    public static ArrayList<AnAtom> getAllAtomsCreated() {
        return atomsOnScreen;
    }
}
