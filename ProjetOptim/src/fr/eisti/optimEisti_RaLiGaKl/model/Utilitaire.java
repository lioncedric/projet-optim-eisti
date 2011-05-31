package fr.eisti.optimEisti_RaLiGaKl.model;

import fr.eisti.optimEisti_RaLiGaKl.Main;
import java.awt.Color;
import org.w3c.dom.*;
import java.io.*;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
     * @param schema : le schema
     * @param document : le document relatif au fichier xml
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
     * @param name : le nom du schema
     * @return le Schema
     */
    public static Schema loadSchema(String name) {
        Schema schemaFile = null;
        try {
            String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory factory = SchemaFactory.newInstance(language);

            schemaFile = factory.newSchema(new StreamSource(Utilitaire.class.getResourceAsStream(name)));

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return schemaFile;
    }

    /**
     * Charge un fichier xml en memoire dans un document
     * @param name : nom du fichier xml
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
     * @param document : le document
     * @param fichier : le nom du fichier xml
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
     * @param xml : nom du fichier xml
     * @param xsl : nom du fichier xsl
     * @param html : nom du fichier html
     * @throws Exception
     */
    public static void creerHTML(String xml, String xsl, String html) throws Exception {
        // Création de la source DOM
        DocumentBuilderFactory fabriqueD = DocumentBuilderFactory.newInstance();
        DocumentBuilder constructeur = fabriqueD.newDocumentBuilder();
        File fileXml = new File(xml);
        //si le fichier .xml associé à l'utilisateur existe
        if (fileXml.exists()) {
            Document document = constructeur.parse(fileXml);
            Source source = new DOMSource(document);

            // Création du fichier de sortie
            File fileHtml = new File(html);
            Result resultat = new StreamResult(fileHtml);

            // Configuration du transformer
            TransformerFactory fabriqueT = TransformerFactory.newInstance();
            InputStream in = Utilitaire.class.getResourceAsStream(xsl);
            StreamSource stylesource = new StreamSource(in);
            Transformer transformer = fabriqueT.newTransformer(stylesource);
            transformer.setOutputProperty(OutputKeys.METHOD, "html");

            // Transformation
            transformer.transform(source, resultat);
        }

    }

    /**
     * copie un fichier
     * @param fichierIn fichier d'entree contenu dans le jar
     * @param fichierout fichier dde sortie
     */
    public static void copieFichierJar(String fichierIn, String fichierout) throws FileNotFoundException, IOException {
        //Déclaration et initialisation de variables
        InputStream in;//flux d'entrée contenant le fichier à copier
        OutputStream out;//flux de sortie contenant également le chemin où va être copié le fichier d'entrée
        //instanciation des flux
        in = Utilitaire.class.getResourceAsStream(fichierIn);
        out = new FileOutputStream(fichierout);
        int c = 0;//compteur pour connaître la fin du fichier
        //tant qu'on n'est pas à la fin du fichier
        while ((c = in.read()) != -1) {
            //on écrit le caractère dans le fichier
            out.write(c);
        }
        //femeture des flux
        in.close();
        out.close();

    }

    /**
     * sauvegarde du fichier de sauvegarde des preferences de couleur
     * @param nom : emplacement du ficher
     * @throws IOException exeception potentielle
     */
    public static void Save(String nom) throws IOException {
        Color[] tab = new Color[12];
        //on recuper les couleurs
        tab[0] = (Main.fenetrePrincipale.getPanProfil().getCouleurFond());
        tab[1] = (Main.fenetrePrincipale.getPanProfil().getCouleurTexte());
        tab[2] = (Main.fenetrePrincipale.getGauche().getCouleur1());
        tab[3] = (Main.fenetrePrincipale.getGauche().getCouleur2());
        tab[4] = (Main.fenetrePrincipale.getGauche().getListePR().getCouleurTexte());
        tab[5] = (Main.fenetrePrincipale.getGauche().getListePR().getCouleurFondSelection());
        tab[6] = (Main.fenetrePrincipale.getGauche().getListePR().getCouleurTexteSelection());
        tab[7] = (Main.fenetrePrincipale.getGauche().getCouleurFondPanelBoutons());
        tab[8] = (Main.fenetrePrincipale.getCouleur1());
        tab[9] = (Main.fenetrePrincipale.getCouleur2());
        tab[10] = (Main.fenetrePrincipale.getCouleurTexte());
        tab[11] = (Main.fenetrePrincipale.getCouleurComposantsTransparents());
        //save
        ObjectOutputStream oos;//creation d'un flux de sortie
        oos = new ObjectOutputStream(new FileOutputStream(nom));//connexion
        //ecriture de l'arbre dans le fichier
        oos.writeObject(tab);
        //fermeture du flux
        oos.close();


    }

    /**
     * ecriture du fichier de sauvegarde des preferences de couleur
     * @param nom : emplacement du ficher
     * @throws IOException exeception potentielle
     * @throws ClassNotFoundException exeception potentielle
     */
    public static void Load(String nom) throws IOException, ClassNotFoundException {
        Color[] tab = new Color[12];
        ObjectInputStream ois;//cretion  d'un flux d'entree



        ois = new ObjectInputStream(new FileInputStream(nom));
        tab = (Color[]) ois.readObject();
        ois.close();//fermeture du flux

        //mise a jour des couleurs
        Main.fenetrePrincipale.getPanProfil().mettreCouleurFond(tab[0]);
        Main.fenetrePrincipale.getPanProfil().mettreCouleurTexte(tab[1]);
        Main.fenetrePrincipale.getGauche().changerCouleurDegrade1(tab[2]);
        Main.fenetrePrincipale.getGauche().changerCouleurDegrade2(tab[3]);
        Main.fenetrePrincipale.getGauche().getListePR().changerCouleurTexte(tab[4]);
        Main.fenetrePrincipale.getGauche().getListePR().changerCouleurFondSelection(tab[5]);
        Main.fenetrePrincipale.getGauche().getListePR().changerCouleurTexteSelection(tab[6]);
        Main.fenetrePrincipale.getGauche().changerCouleurFondPanelBoutons(tab[7]);
        Main.fenetrePrincipale.setCouleur1(tab[8]);
        Main.fenetrePrincipale.setCouleur2(tab[9]);
        Main.fenetrePrincipale.setCouleurTexte(tab[10]);
        Main.fenetrePrincipale.setCouleurComposantsTransparents(tab[11]);
        Main.fenetrePrincipale.repaint();

    }

    /**
     * Copy any input stream to output stream. Once the data will be copied
     * both streams will be closed.
     *
     * @param input  - InputStream to copy from
     * @param output - OutputStream to copy to
     * @throws IOException - io error in function
     * @throws OSSMultiException - double error in function
     */
    public static void copyStreamToStream(InputStream input, OutputStream output) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        int ch;

        try {
            if (input instanceof BufferedInputStream) {
                is = input;
            } else {
                is = new BufferedInputStream(input);
            }
            if (output instanceof BufferedOutputStream) {
                os = output;
            } else {
                os = new BufferedOutputStream(output);
            }

            while ((ch = is.read()) != -1) {
                os.write(ch);
            }
            os.flush();
        } finally {
            IOException exec1 = null;
            IOException exec2 = null;
            try {
                // because this close can throw exception we do next close in
                // finally statement
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException exec) {
                        exec1 = exec;
                    }
                }
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException exec) {
                        exec2 = exec;
                    }
                }
            }
            if ((exec1 != null) && (exec2 != null)) {
                throw exec1;
            } else if (exec1 != null) {
                throw exec1;
            } else if (exec2 != null) {
                throw exec2;
            }
        }
    }

    public static String encode(String password, String algorithm)
            throws NoSuchAlgorithmException {
        byte[] hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            hash = md.digest(password.getBytes());
        } catch (NoSuchAlgorithmException e) {
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hash.length; ++i) {
            String hex = Integer.toHexString(hash[i]);
            if (hex.length() == 1) {
                sb.append(0);
                sb.append(hex.charAt(hex.length() - 1));
            } else {
                sb.append(hex.substring(hex.length() - 2));
            }
        }
        return sb.toString();
    }
}
