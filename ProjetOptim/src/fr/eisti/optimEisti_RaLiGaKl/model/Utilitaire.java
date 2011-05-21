package fr.eisti.optimEisti_RaLiGaKl.model;

import org.w3c.dom.*;
import java.io.*;
import javax.swing.JFileChooser;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

/**
 * classe contenant des methodes utiles pour comuniquer avec du xml
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Meriadec
 */
public class Utilitaire {

    /**
     * Valide un document(xml) a partir d'un xsd
     * @param schema le schema
     * @param document le document relatif au fichier xml
     * @return valide ou pas le document
     */
    public static boolean validateXml(Schema schema, Document document) {
        try {
            // creating a Validator instance
            Validator validator = schema.newValidator();
            // validating the document against the schema
            validator.validate(new DOMSource(document));

            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    /**
     * charge un schema
     * @param name le nom du schema
     * @return le Schema
     */
    public static Schema loadSchema(String name) {
        Schema schema = null;
        try {
            String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory factory = SchemaFactory.newInstance(language);
            schema = factory.newSchema(new File(name));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return schema;
    }

    /**
     * Charge un fichier xml en memoire dans un document
     * @param name nom du fichier xml
     * @return le ducument chargé
     */
    public static Document parseXmlDom(File name) {
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(name);
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
        return document;
    }

    /**
     * sauvegarde un document dans un fichier xml
     * @param document le document
     * @param fichier le nom du fichier xml 
     */
    public static void transformerXml(Document document, String fichier) {
        try {
            // Création de la source DOM
            Source source = new DOMSource(document);

            // Création du fichier de sortie
            File file = new File(fichier);
            Result resultat = new StreamResult(fichier);

            // Configuration du transformer
            TransformerFactory fabrique = TransformerFactory.newInstance();
            Transformer transformer = fabrique.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

            // Transformation
            transformer.transform(source, resultat);
        } catch (Exception e) {
        }
    }

    /**
     * Création d'un document HTML avec XSLT 
     * @param xml
     * @param xsl
     * @param html
     * @throws Exception
     */
    public static void creerHTML(String xml, String xsl, String html) throws Exception {
        // Création de la source DOM
        DocumentBuilderFactory fabriqueD = DocumentBuilderFactory.newInstance();
        DocumentBuilder constructeur = fabriqueD.newDocumentBuilder();
        File fileXml = new File(xml);
        if(fileXml.exists()){
            Document document = constructeur.parse(fileXml);
        Source source = new DOMSource(document);

        // Création du fichier de sortie
        File fileHtml = new File(html);
        Result resultat = new StreamResult(fileHtml);

        // Configuration du transformer
        TransformerFactory fabriqueT = TransformerFactory.newInstance();
        StreamSource stylesource = new StreamSource(xsl);
        Transformer transformer = fabriqueT.newTransformer(stylesource);
        transformer.setOutputProperty(OutputKeys.METHOD, "html");

        // Transformation
        transformer.transform(source, resultat);
        }
        
    }
/**
 * copie un fichier
 * @param fichierIn fichier d'entree
 * @param fichierout fichier dde sortie
 */
    public static void copie(String fichierIn, String fichierout) throws FileNotFoundException, IOException {
        InputStream in;
        OutputStream out;
        in = new FileInputStream(fichierIn);
        out = new FileOutputStream(fichierout);
        int c = 0;
        while ((c = in.read()) != -1) {
            out.write(c);
        }
        in.close();
        out.close();

    }
}


