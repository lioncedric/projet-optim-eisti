package fr.eisti.optimEisti_RaLiGaKl.model;

import java.io.*;


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
     * permet de créer un nouvel utilisateur
     * @param NomUtilisateur
     * @param mdp
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
        personne.setAttribute("imagesrc", imageSrc);
        //on ajoute le noeuf fils à la racine
        racine.appendChild(personne);
        //permet de transformer le dom en xml
        Utilitaire.transformerXml(document, chFichierIdentification);
    }

    /**
     * permet de modifier nom, mot de passe et image d'un utilisateur ainsi que de reattribuer le fichier xml de ses problemes
     * @param login nom de l'utilisateur
     * @param mdp mot de passe de l'utilisateur
     *  @param imageSrc image le representant
     */
    public static void modifierUtilisateur(String login, String mdp, String imageSrc) {
        int i = 0;
        boolean trouve = false;
        String NomUtilisateurTmp = "";
        String pass = "";
        Document document = Utilitaire.parseXmlDom(new File(chFichierIdentification));

        //on recupere le noeud racine
        Element racine = document.getDocumentElement();
        //on recupere tous les coefficients de la fonction objectif
        NodeList liste = racine.getElementsByTagName("personne");
        //Tant qu'on a pas parcouru toute la liste de noeud
        while (i < liste.getLength() && !trouve) {
            //on récupère le nom d'utilisateur du noeud en question
            NomUtilisateurTmp = liste.item(i).getAttributes().getNamedItem("login").getNodeValue();
            //ainsi que le mot de passe
            pass = liste.item(i).getAttributes().getNamedItem("password").getNodeValue();
            //si il sont égaux au nom d'utilisateur et mot de passe en paramètre alors
            System.out.println("Courou1");
            if (NomUtilisateurTmp.equals(NomUtilisateur) && pass.equals(motDePasse)) {
                trouve = true;
                System.out.println("Courou2");
                liste.item(i).getAttributes().getNamedItem("imagesrc").setNodeValue(imageSrc);

                liste.item(i).getAttributes().getNamedItem("password").setNodeValue(mdp);
                System.out.println(liste.item(i).getAttributes().getNamedItem("password").getNodeValue());
                liste.item(i).getAttributes().getNamedItem("login").setNodeValue(login);
                System.out.println(liste.item(i).getAttributes().getNamedItem("login").getNodeValue());
                //permet de transformer le dom en xml
                Utilitaire.transformerXml(document, chFichierIdentification);
                File fichier = new File("bdd/" + NomUtilisateur + ".xml");
                if (fichier.exists()) {
                    fichier.renameTo(new File("bdd/" + login + ".xml"));
                }
            }
            //incrémentation du i
            i++;
        }



    }

    /**
     * classe qui permet de lire dans le fichier et de retourner si login et mot de passe sont contenus dans le fichier
     * @param login
     * @param mdp
     * @return existe
     */
    public static boolean existeCompte(String login, String mdp) {
        //Déclaration des variables
        boolean existe;//booleen qui permet de retourner si login et mdp sont dans le fichier
        //Initialisation des variables
        existe = false;
        String NomUtilisateurTmp = "";
        String pass = "";
        int i = 0;
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
     * @param login
     * @return existe
     */
    public static boolean existeUtilisateur(String login) {
        //Déclaration des variables
        boolean existe;//booleen qui permet de retourner si login et mdp sont dans le fichier
        //Initialisation des variables
        existe = false;
        String nom = "";
        int i = 0;
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

    public static String getNomUtilisateur() {
        return NomUtilisateur;
    }

    public static void setNomUtilisateur(String NomUtilisateur) {
        BDDUtilisateur.NomUtilisateur = NomUtilisateur;
    }

    public static String getImage() {
        return image;
    }

    public static void setImage(String image) {
        BDDUtilisateur.image = image;
    }

    public static String getMotDePasse() {
        return motDePasse;
    }

    public static void setMotDePasse(String motDePasse) {
        BDDUtilisateur.motDePasse = motDePasse;
    }
}
