package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.Atom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLAtomLoader {
    private ArrayList<Atom> list;
    private Document doc;

    public XMLAtomLoader(String fileName) {
        list = new ArrayList<Atom>();
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
        NodeList nList = doc.getElementsByTagName("atom");

        for (int i = 0; i < nList.getLength(); i++) {
            this.loadOneAtom(nList.item(i));
        }
    }

    private void loadOneAtom(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {

            Element element = (Element) node;
            String name = element.getAttribute("name");
            String symbol = element.getAttribute("symbol");
            int bond = Integer.parseInt(element.getAttribute("bond"));

           this.list.add(new Atom(name, symbol, bond));
        }
    }
    
    public ArrayList<Atom> getAtomList() {
        return this.list;
    }
}
