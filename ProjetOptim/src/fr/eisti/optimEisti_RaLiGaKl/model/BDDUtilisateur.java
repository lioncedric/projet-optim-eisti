package fr.eisti.optimEisti_RaLiGaKl.model;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.*;

/**
 * classe qui permet à l'utilisateur de s'authifier correctement
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Meriadec
 */
public class BDDUtilisateur {

    //déclaration d'attribut
    private static String NomUtilisateur;
    private static String motDePasse;
    private static String image;
    private static String chFichierIdentification = "./bdd/identification.xml";

    /**
     * permet de créer le répertoire bdd/identification.xml si celui-ci n'existe pas
     */
    public static void creerFichierIdentification(){
        try {            
            //création du répertoire bdd à la racine du projet
            File rep = new File("bdd");
            rep.mkdir();
            boolean createNewFile = new File(chFichierIdentification).createNewFile();
            
            if(createNewFile){
                // Création d'un nouveau DOM
                DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
                DocumentBuilder constructeur;
                constructeur = fabrique.newDocumentBuilder();
                Document document = constructeur.newDocument();

                // Propriétés du DOM
                document.setXmlVersion("1.0");
                document.setXmlStandalone(true);

                // Création de l'arborescence du DOM
                Element racine = document.createElement("annuaire");
                //on ajoute la racine au xml
                document.appendChild(racine);
                //permet de transformer le dom en xml
                Utilitaire.transformerXml(document, chFichierIdentification);
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(BDDUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BDDUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * permet de créer un nouvel utilisateur
     * @param NomUtilisateur : nom de l'utilisateur
     * @param mdp : mot de passe de l'utilisateur
     * @param imageSrc : le chemin de l'image le représentant
     */
    public static void ajouterUtilisateur(String NomUtilisateur, String mdp, String imageSrc) {

            // Parse an XML document into a DOM tree.
            Document document;
            document = Utilitaire.parseXmlDom(new File(chFichierIdentification));
            //on recupere le noeud racine
            Element racine = document.getDocumentElement();
            //On créé un nouveau noeud Personne
            Element personne = document.createElement("personne");
            //on modifie ces attributs login et password
            personne.setAttribute("login", NomUtilisateur);
            personne.setAttribute("password", mdp);
            //si l'utilisateur ne choisit pas d'image
            if(imageSrc.equals("")){
                //on en mets une par défaut
                imageSrc=new File("images").getParent()+"/tete.jpg";
            }
            //on ajoute un attribut avec le src de son avatar
            personne.setAttribute("imagesrc", imageSrc);
            //on ajoute le noeuf fils à la racine
            racine.appendChild(personne);
            //permet de transformer le dom en xml
            Utilitaire.transformerXml(document, chFichierIdentification);
        
    }


    /**
     * permet de modifier nom, mot de passe et image d'un utilisateur ainsi que de reattribuer le fichier xml de ses problemes
     * @param login : nom de l'utilisateur
     * @param mdp : mot de passe de l'utilisateur
     *  @param imageSrc : le chemin de l'image le représentant
     */
    public static void modifierUtilisateur(String login, String mdp, String imageSrc) {
        //Déclaration et initialisation des variables
        int i = 0;
        boolean trouve = false;
        String NomUtilisateurTmp = "";
        String pass = "";
        // Parse an XML document into a DOM tree.
        Document document = Utilitaire.parseXmlDom(new File(chFichierIdentification));
        
        //on recupere le noeud racine
        Element racine = document.getDocumentElement();
        //on recupere tous noeuds personne
        NodeList liste = racine.getElementsByTagName("personne");
        //Tant qu'on a pas parcouru toute la liste de noeud
        while (i < liste.getLength() && !trouve) {
            //on récupère le nom d'utilisateur du noeud en question
            NomUtilisateurTmp = liste.item(i).getAttributes().getNamedItem("login").getNodeValue();
            //ainsi que le mot de passe
            pass = liste.item(i).getAttributes().getNamedItem("password").getNodeValue();
            //si il sont égaux au nom d'utilisateur et mot de passe en paramètre alors
            if (NomUtilisateurTmp.equals(NomUtilisateur) && pass.equals(motDePasse)) {
                trouve = true;
                liste.item(i).getAttributes().getNamedItem("imagesrc").setNodeValue(imageSrc);
                
                liste.item(i).getAttributes().getNamedItem("password").setNodeValue(mdp);

                liste.item(i).getAttributes().getNamedItem("login").setNodeValue(login);
                //permet de transformer le dom en xml
                Utilitaire.transformerXml(document, chFichierIdentification);
                //on modifie l'image de l'utilisateur
                BDDUtilisateur.setImage(imageSrc);
                //on modifie le nom d'utilisateur
                BDDUtilisateur.setNomUtilisateur(login);
                //on modifie le mot de passe de l'utilisateur
                BDDUtilisateur.setMotDePasse(mdp);
                //instanciation du fichier xml de lancien nom d'utilisateur
                File fichier = new File("bdd/" + NomUtilisateur + ".xml");
                //si il existe
                if (fichier.exists()) {
                    //on le renomme au nouveau nom d'utilisateur
                    fichier.renameTo(new File("bdd/" + login + ".xml"));
                    //et on appelle la fonction qui va modifier son image
                    modifierFichierXmlUtilisateur("bdd/" + login + ".xml", imageSrc);
                }
            }
            //incrémentation du i
            i++;
        }



    }

    /**
     * permet de modifier l'url de la nouvelle image dans le fichier xml
     * @param nomFichier nom du nouveau fichier xml de l'utilisateur
     * @param imageSrc chemin de la nouvelle image (si elle est nouvelle sinon le chemin de l'ancienne image)
     */
    public static void modifierFichierXmlUtilisateur(String nomFichier, String imageSrc){
        // Parse an XML document into a DOM tree.
        Document document = Utilitaire.parseXmlDom(new File(nomFichier));

        //on recupere le noeud racine, le noeud listeProbleme
        Element racine = document.getDocumentElement();
        //on modifie l'url de la nouvelle image
        racine.setAttribute("url", imageSrc);

        //permet de transformer le dom en xml
        Utilitaire.transformerXml(document, nomFichier);
    }

    /**
     * classe qui permet de lire dans le fichier et de retourner si login et mot de passe sont contenus dans le fichier
     * @param login nom d'utilisateur
     * @param mdp mot de passe de l'utilisateur
     * @return existe existence du compte 
     */
    public static boolean existeCompte(String login, String mdp) {
        //Déclaration des variables
        boolean existe;//booleen qui permet de retourner si login et mdp sont dans le fichier
        //Initialisation des variables
        existe = false;
        String NomUtilisateurTmp = "";
        String pass = "";
        int i = 0;
        // Parse an XML document into a DOM tree.
        Document document = Utilitaire.parseXmlDom(new File(chFichierIdentification));

        //on recupere le noeud racine
        Element racine = document.getDocumentElement();
        //on recupere tous les coefficients de la fonction objectif
        NodeList liste = racine.getElementsByTagName("personne");
        //Tant qu'on a pas parcouru toute la liste de noeud
        while (i < liste.getLength()) {
            //on récupère le nom d'utilisateur du noeud en question
            NomUtilisateurTmp = liste.item(i).getAttributes().getNamedItem("login").getNodeValue();
            //ainsi que le mot de passe
            pass = liste.item(i).getAttributes().getNamedItem("password").getNodeValue();
            //si il sont égaux au nom d'utilisateur et mot de passe en paramètre alors
            if (NomUtilisateurTmp.equals(login) && pass.equals(mdp)) {
                //existe est vrai car le compte existe
                existe = true;
                //on modifie le nom d'utilisateur avec le login
                NomUtilisateur = login;
                //on met à jour les variables de mot de passe et d'image avec les bonnes valeurs
                motDePasse = mdp;
                image = liste.item(i).getAttributes().getNamedItem("imagesrc").getNodeValue();
              
            }
            //incrémentation du i
            i++;
        }
        //on retourne le booléen
        return existe;
    }

    /**
     * classe qui permet de lire dans le fichier et de retourner si on peut creer ou non le nouveau compte
     * @param login : nom d'utilisateur
     * @return existe : existence du nom d'utilisateur
     */
    public static boolean existeUtilisateur(String login) {
        //Déclaration des variables
        boolean existe;//booleen qui permet de retourner si login et mdp sont dans le fichier
        //Initialisation des variables
        existe = false;
        String nom = "";
        int i = 0;
        // Parse an XML document into a DOM tree.
        Document document = Utilitaire.parseXmlDom(new File(chFichierIdentification));

        //on recupere le noeud racine
        Element racine = document.getDocumentElement();
        //on recupere tous les coefficients de la fonction objectif
        NodeList liste = racine.getElementsByTagName("personne");
        //Tant qu'on a pas parcouru toute la liste de noeud
        while (i < liste.getLength()) {
            //on récupère le nom d'utilisateur du noeud en question
            nom = liste.item(i).getAttributes().getNamedItem("login").getNodeValue();
            //si le nom d'utilisateur est égal à celui en paramètre alors
            if (nom.equals(login)) {
                //existe est vrai car le compte existe déjà
                existe = true;
            }
            //incrémentation du i
            i++;
        }
        //on retourne le booléen
        return existe;
    }

    /**
     * fonction qui supprime un compte
     * @param login : nom d'utilisateur
     * @return trouve : si le compte a été supprimé
     */
    public static boolean supprimerCompte(String login) {
        //Déclaration et initialisation des variables
        int i = 0;//compteur
        boolean trouve = false;//booleen pour savoir si on a trouvé le compte à supprimer
        String NomUtilisateurTmp = "";

        //Si l'utilisateur existe bien
        if (BDDUtilisateur.existeUtilisateur(login)) {
            // Parse an XML document into a DOM tree.
            Document document;
            document = Utilitaire.parseXmlDom(new File(chFichierIdentification));

            //on recupere le noeud racine
            Element racine = document.getDocumentElement();
            //on recupere toutes les personnes
            NodeList liste = racine.getElementsByTagName("personne");
            //Tant qu'on a pas parcouru toute la liste de noeud
            while (i < liste.getLength() && !trouve) {
                //on récupère le nom d'utilisateur du noeud en question
                NomUtilisateurTmp = liste.item(i).getAttributes().getNamedItem("login").getNodeValue();
                //si il sont égaux au nom d'utilisateur et mot de passe en paramètre alors
                if (NomUtilisateurTmp.equals(login)) {
                    //trouve est vrai
                    trouve = true;
                    //on supprime le noeud
                    racine.removeChild(liste.item(i));
                    //Si le fichier xml propre à l'utilisateur existe bien
                    if(new File("./bdd/"+BDDUtilisateur.getNomUtilisateur()+".xml").exists()){
                        //on supprime le fichier
                        new File("./bdd/"+BDDUtilisateur.getNomUtilisateur()+".xml").delete();
                    }
                    //permet de transformer le dom en xml
                    Utilitaire.transformerXml(document, chFichierIdentification);
                }
                //incrémentation du i
                i++;
            }
        }
        //on retourne le resultat de la fonction
        return trouve;
    }

    /**
     * recuperer le nom de l'utilisateur en cours
     * @return l'utilisateur en cours
     */
    public static String getNomUtilisateur() {
        return NomUtilisateur;
    }

    /**
     * modifie le nom d'utilisateur
     * @param NomUtilisateur : le nom de l'utilisateur
     */
    public static void setNomUtilisateur(String NomUtilisateur) {
        BDDUtilisateur.NomUtilisateur = NomUtilisateur;
    }

    /**
     * récupère l'image
     * @return image
     */
    public static String getImage() {
        return image;
    }

    /**
     * modifie l'image
     * @param image
     */
    public static void setImage(String image) {
        BDDUtilisateur.image = image;
    }

    /**
     * récupère le mot de passe
     * @return motDePasse
     */
    public static String getMotDePasse() {
        return motDePasse;
    }

    /**
     * modifie le mot de passe
     * @param motDePasse
     */
    public static void setMotDePasse(String motDePasse) {
        BDDUtilisateur.motDePasse = motDePasse;
    }
}
