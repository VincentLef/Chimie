package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.Atom;
import model.Game;
import model.Molecule;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLMoleculeLoader {
    private ArrayList<Molecule> list;
    private Document doc;

    public XMLMoleculeLoader(String fileName) {
        list = new ArrayList<Molecule>();
        File xmlFile = new File(fileName);
        this.createDocument(xmlFile);
    }

    private void createDocument(File xmlFile) {
        try {
            this.createDocumentOrThrowException(xmlFile);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private void createDocumentOrThrowException(File xmlFile) throws ParserConfigurationException,
            SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        dBuilder = dbFactory.newDocumentBuilder();
        this.doc = dBuilder.parse(xmlFile);
        this.doc.normalize();
    }

    public void loadListfromXmlFile() {
        NodeList nList = doc.getElementsByTagName("molecule");

        for (int i = 0; i < nList.getLength(); i++) {
            this.loadOneMolecule(nList.item(i));
        }
    }

    private void loadOneMolecule(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {

            Element element = (Element) node;
            
            String name = element.getAttribute("name");
            String symbol = element.getAttribute("symbol");
            Atom[] structure = this.getAtomStructure(element);
            
            this.list.add(new Molecule(name, symbol, structure));
        }
    }

    private Atom[] getAtomStructure(Element node) {
        NodeList nodeList = node.getElementsByTagName("atom");
        Atom[] structure = new Atom[nodeList.getLength()];
        
        for(int i = 0; i < nodeList.getLength(); ++i) {
            structure[i] = this.getAtomFromNode(nodeList.item(i));
        }
        
        return structure;
    }

    private Atom getAtomFromNode(Node item) {
        Element element = (Element) item;
        return Game.findAtomByName(element.getAttribute("name"));
    }

    public ArrayList<Molecule> getMoleculeList() {
        return this.list;
    }
}
