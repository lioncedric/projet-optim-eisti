package fr.eisti.OptimEisti.Model;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import java.io.*;
import fr.eisti.OptimEisti.Main;
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;

/**
 * classe relative a un document de type xml chargé en memoire
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Meriadec
 */
public class BddProbleme {

    private static Document bdd;

    /**
     * Retourne le nombres de problemes de la personne
     * @return le nombre de problemes
     */
    public static int nombreProblemes() {
        return bdd.getDocumentElement().getElementsByTagName("probleme").getLength();
    }

    /**
     * permet charger le fichier xml correspondant a l'utilisateur
     * @param nomUtilisateur le nom de l'utilisateur
     */
    public static void load(String nomUtilisateur) throws IOException {
        File fichier = new File("bdd/" + nomUtilisateur + ".xml");
        if (fichier.exists()) {
            bdd = Utilitaire.parseXmlDom(fichier);
        } else {
            try {
                // Création d'un nouveau DOM
                DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
                DocumentBuilder constructeur = fabrique.newDocumentBuilder();
                bdd = constructeur.newDocument();

                // Propriétés du DOM
                bdd.setXmlVersion("1.0");
                bdd.setXmlStandalone(true);
                // Création de l'arborescence du DOM
                Element racine = bdd.createElement("listeProblemes");
                bdd.appendChild(racine);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(BddProbleme.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * sauvegarde les modifications dans le fichier xml correspondant a l'utilisateur
     * @param nomUtilisateur le nom de l'utilisateur
     */
    public static void save(String nomUtilisateur) {
        Utilitaire.transformerXml(bdd, "bdd/" + nomUtilisateur + ".xml");
    }

    /**
     * supprime un  probleme
     * @param num le numero de probleme a supprimer
     */
    public static void supprimerProbleme(int num) {
        //on recupere tous les coefficients de la fonction objectif
        Element racine = bdd.getDocumentElement();
        NodeList liste = racine.getElementsByTagName("probleme");
        racine.removeChild(liste.item(num));
    }

    /**
     * ajouter des problemes directement a partir d'un fichier xml de problemes
     * @param nom le fichier a importer
     */
    public static void importerProbleme(File nom) {
        Schema schema = Utilitaire.loadSchema("bdd/schema.xsd");
        Document document = Utilitaire.parseXmlDom(nom);
        if (document != null & Utilitaire.validateXml(schema, document)) {
            NodeList liste = document.getDocumentElement().getElementsByTagName("probleme");
            for (int i = 0; i < liste.getLength(); i++) {
                Probleme p = getProblemeImport((Element) liste.item(i));
                if (p != null) {
                    addProbleme(p);

                }
            }
        } else {
        }
        Main.fenetrePrincipale.getGauche().miseajour();
    }

    /**
     * ajouter un probleme
     * @param p le probleme a ajouter
     */
    public static void addProbleme(Probleme p) {

        //on recupere tous les coefficients de la fonction objectif

        Element racine = bdd.getDocumentElement();
        Element probleme = bdd.createElement("probleme");
        Element titre = bdd.createElement("titre");
        titre.setTextContent(p.getTitre());
        probleme.appendChild(titre);
        Element decription = bdd.createElement("description");
        decription.setTextContent(p.getDescription());
        probleme.appendChild(decription);
        Element objectif = bdd.createElement("objectif");
        objectif.setAttribute("type", "" + p.getObjectif());
        for (int i = 0; i < p.getCoeffVariables().size(); i++) {
            Element variable = bdd.createElement("variable");
            variable.setAttribute("coeff", "" + p.getCoeffVariables().get(i));
            objectif.appendChild(variable);
        }
        probleme.appendChild(objectif);
        for (int i = 0; i < p.getContraintes().size(); i++) {
            Element contrainte = bdd.createElement("contrainte");
            contrainte.setAttribute("type", p.getContraintes().get(i).getInegalite());
            for (int j = 0; j < p.getContraintes().get(i).getCoeffVariables().size(); j++) {

                Element variable2 = bdd.createElement("variable");
                variable2.setAttribute("coeff", "" + p.getContraintes().get(i).getCoeffVariables().get(j));
                contrainte.appendChild(variable2);
            }
            Element constante = bdd.createElement("constante");
            constante.setAttribute("valeur", "" + p.getContraintes().get(i).getConstante());
            contrainte.appendChild(constante);
            probleme.appendChild(contrainte);
        }
        racine.appendChild(probleme);
    }

    /**
     *  Recuperer le nieme probleme
     * @param nb numero de probleme
     * @return le probleme
     */
    public static Probleme getProbleme(int nb) {
        Element NoeudProbleme = (Element) bdd.getDocumentElement().getElementsByTagName("probleme").item(nb);
        return getProblemeImport(NoeudProbleme);
    }

    /**
     * Recupere un probleme a partir d'un noeud dom
     * @param NoeudProbleme le noeud a parser
     * @return le probleme
     */
    public static Probleme getProblemeImport(Element NoeudProbleme) {
        Probleme probleme = new Probleme();
        probleme.setTitre(NoeudProbleme.getElementsByTagName("*").item(0).getTextContent());
        probleme.setDescription(NoeudProbleme.getElementsByTagName("*").item(1).getTextContent());
        probleme.setObjectif(NoeudProbleme.getElementsByTagName("*").item(2).getAttributes().getNamedItem("type").getTextContent());
        int nbvariables1 = ((Element) (NoeudProbleme.getElementsByTagName("*").item(2))).getElementsByTagName("*").getLength();
        for (int i = 0; i < nbvariables1; i++) {
            probleme.getCoeffVariables().add(Double.parseDouble(((Element) (NoeudProbleme.getElementsByTagName("*").item(2))).getElementsByTagName("*").item(i).getAttributes().getNamedItem("coeff").getTextContent()));
        }
        NodeList contraintes = NoeudProbleme.getElementsByTagName("contrainte");
        for (int i = 0; i < contraintes.getLength(); i++) {
            probleme.getContraintes().add(new Contrainte());
            probleme.getContraintes().get(i).setInegalite(contraintes.item(i).getAttributes().getNamedItem("type").getTextContent());
            NodeList variables = ((Element) contraintes.item(i)).getElementsByTagName("variable");
            NodeList constantes = ((Element) contraintes.item(i)).getElementsByTagName("constante");
            probleme.getContraintes().get(i).setConstante(Double.parseDouble((constantes.item(0).getAttributes().getNamedItem("valeur").getTextContent())));
            int nbvariables2 = variables.getLength();
            if (nbvariables1 == nbvariables2) {
                for (int j = 0; j < nbvariables2; j++) {
                    probleme.getContraintes().get(i).getCoeffVariables().add(Double.parseDouble(variables.item(j).getAttributes().getNamedItem("coeff").getTextContent()));
                }
            } else {

                return null;
            }
        }
        return probleme;
    }

    /**
     * fonction qui permet d'exporter un probleme sur Scilab
     * @param p probleme d'entrée
     * @param nom fichier de sortie
     */
    public static void exporterScilab(Probleme p, String nom) {

        double[][] probleme = p.formaliserProbleme();
        int lignes = probleme.length;
        int colonnes = probleme[0].length;
        String c = "c=[";
        String Z1 = "Z1=[";
        String b = "b=[";
        String A = "A=[";
        for (int i = 0; i < colonnes - lignes; i++) {
            c = c + probleme[lignes - 1][i];
            Z1 = Z1 + "0";
            if (i == colonnes - lignes - 1) {
                c = c + "]";
                Z1 = Z1 + "]";
            } else {
                c = c + ";";
                Z1 = Z1 + ";";
            }
        }
        for (int i = 0; i < lignes - 1; i++) {
            b = b + probleme[i][colonnes - 1];
            if (i == lignes - 2) {
                b = b + "]";
            } else {
                b = b + ";";
            }
        }
        for (int i = 0; i < lignes - 1; i++) {
            for (int j = 0; j < colonnes - lignes; j++) {
                A = A + probleme[i][j];
                if (i < lignes - 2 || j < colonnes - lignes - 1) {
                    if (j == colonnes - lignes - 1) {
                        A = A + ";";
                    } else {
                        A = A + ",";
                    }
                }
            }
        }
        A = A + "]";
        FileWriter fw = null;
        try {
   
            fw = new FileWriter(nom + ".sci", false);
            BufferedWriter sortie = new BufferedWriter(fw);

            sortie.write("//Programmation lineaire: " + p.getTitre() + "\n");
            sortie.write(c + ";\n");
            sortie.write(b + ";\n");
            sortie.write(A + ";\n");
            sortie.write("Zu=[]" + ";\n");
            sortie.write(Z1 + ";\n");
            sortie.write("[Zopt,lag,CA]=linpro(c,A,b,Z1,Zu)" + ";\n");
            sortie.close();
        } catch (IOException ex) {
        }

    }

    /**
     * fonction qui permet d'exporter un probleme sur excel
     * @param p probleme d'entrée
     * @param nom fichier de sortie
     */
    public static void exporterExcel(Probleme p, String nom) {
        // Déclaration et initialisation de variables
        int nbVariablesDecision = 0;//longueur de la liste de variables
        int j = 0;//compteur
        char monChar = 'B';//Lettre de la colonne initiale ou se trouve la premiere variables de décision
        int monAscii = (new Character(monChar)).hashCode();//son code ascii correspondant
        int cpt = 0;//un compteur
        String temp = "=";//un string ou on stocke la formule excel

        File file = new File(nom + ".csv");

        if (file.exists()) {
            file.delete();
        }
        try {
            //Déclaration et initialisation de flux et variables
            FileWriter fw = new FileWriter(nom + ".csv", true);
            BufferedWriter output = new BufferedWriter(fw);

            ArrayList<Double> coeff = p.getCoeffVariables();//liste des coefficients du problème
            Iterator<Double> it = coeff.iterator();//nouvel iterateur pour parcourir la liste

            ArrayList<Contrainte> contraintes = p.getContraintes();//liste des contraintes du probleme

            output.write("Programmation lineaire: " + p.getTitre() + "\n\n\n");
            //On saute une ligne
            output.write(";");
            //calcul de la longueur de la liste de variables
            while (it.hasNext()) {
                nbVariablesDecision++;
                it.next();
            }
            //Ecriture du nom des variables
            while (j < nbVariablesDecision) {
                j++;
                output.write("x" + j + ";");
            }
            j = 0;
            output.write("\n");
            output.write(";");
            //Ecriture de 0 sous les variables
            while (j < nbVariablesDecision) {
                j++;
                output.write("0;");
            }

            output.write("\n");
            output.write("F0;");

            //Ecriture des coefficients de la fonction objective
            for (Double i : coeff) {
                //on convertit le double en string
                String str = String.valueOf(i);
                //on remplace tous les . par des virgules pour que les formaules excel marchent
                str = str.replace(".", ",");
                //On l'écrit dans le fichier
                output.write("" + str + ";");
            }
            //Ecriture de la formule pour la case du résultat
            //on parcours le nombre de variables de décisions
            while (cpt < nbVariablesDecision) {
                //Si c'est la derniere variable de décision, la formule ne termine pas par un +
                if (cpt == nbVariablesDecision - 1) {
                    temp += monChar + "6*" + monChar + "5";
                } //sinon elle se termine par un +
                else {
                    temp += monChar + "6*" + monChar + "5+";
                }
                //Incrémentation du compteur et du code ascii
                cpt++;
                monAscii++;
                //caractère ascii correspondant
                monChar = (char) (monAscii);
            }
            output.write(temp);
            //On saute deux lignes
            output.write("\n\n");
            output.write("contraintes;");
            j = 8;//la première contrainte se trouve à la ligne 8
            //Ecriture des contraintes
            for (Contrainte t : contraintes) {
                monChar = 'B';//la première contrainte se trouve à la colonne B
                monAscii = (new Character(monChar)).hashCode();//son code ascii correspondant
                temp = "=";
                //On parcours les coefficients des variables pour chaque contrainte
                for (Double coefficient : t.getCoeffVariables()) {
                    //on convertit le double en string
                    String str = String.valueOf(coefficient);
                    //on remplace tous les . par des virgules pour que les formaules excel marchent
                    str = str.replace(".", ",");
                    output.write("" + str + ";");
                }
                cpt = 0;
                while (cpt < nbVariablesDecision) {
                    //Si c'est la derniere variable de décision, la formule ne termine pas par un +
                    if (cpt == nbVariablesDecision - 1) {
                        temp += monChar + "" + j + "*" + monChar + "5";
                    } //sinon elle se termine par un +
                    else {
                        temp += monChar + "" + j + "*" + monChar + "5+";
                    }
                    //Incrémentation du code ascii et du compteur
                    monAscii++;
                    cpt++;
                    //caractère ascii correspondant
                    monChar = (char) (monAscii);
                }
                j++;
                output.write(temp);
                if (t.getInegalite().equals("Infériorité")) {
                    monAscii = 60;
                    monChar = (char) (monAscii);
                    //Affichage de l'inégalité
                    output.write(";" + monChar + "=");
                } else if (t.getInegalite().equals("Supériorité")) {
                    monAscii = 62;
                    monChar = (char) (monAscii);
                    //Affichage de l'inégalité
                    output.write(";" + monChar + "=");
                } else if (t.getInegalite().equals("Egalité")) {
                    //Affichage de l'inégalité
                    output.write(";=");
                }
                //Ecriture de la constante liée à cette contrainte
                output.write(";" + t.getConstante());

                output.write("\n;");

            }

            output.close();

        } catch (IOException ex) {
            Logger.getLogger(BddProbleme.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
