package fr.eisti.LionHour;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/******************************************************************************
 *Description: classe qui permet la synchronnisation entre la BDD et les objets Java
 * @author HOURCLATS Teddy, LION Cédric
 * @version 1.0
 ******************************************************************************/
public class DataManager {

    //variable de la classe
    private static List<Etape> historique;

    /**
     * Paramètre le connecteur avec un login et un mot de passe
     * permettant d'accéder au serveur Oracle local.
     * A faire une fois dans l'application.
     * @param pLogin login
     * @param pPasswd mot de passe
     */
    public static void initOracleLocal(String pLogin, String pPasswd) {
        MyConnector.setParametersOracleLocal(pLogin, pPasswd);
    }

    /**
     * Paramètre le connecteur avec un login et un mot de passe
     * permettant d'accéder au serveur Oracle de l'école.
     * A faire une fois dans l'application.
     * @param pLogin login
     * @param pPasswd mot de passe
     */
    public static void initOracleEisti(String pLogin, String pPasswd) {
        MyConnector.setParametersOracleEisti(pLogin, pPasswd);
    }

    /**
     * Paramètre le connecteur Oracle avec un login et un mot de passe
     * permettant d'accéder au serveur Oracle.
     * A faire une fois dans l'application.
     * @param pDriver le driver
     * @param pServer le serveur
     * @param pPort le port
     * @param pBddname le nom de la bdd
     * @param pDriverClass la classe du driver
     * @param pLogin login
     * @param pPasswd mot de passe
     */
    public static void initOracle(String pDriver, String pServer, int pPort,
            String pBddname, String pDriverClass, String pLogin, String pPasswd) {
        MyConnector.setParameters(pDriver, pServer, pPort, pBddname, pDriverClass, pLogin, pPasswd);
    }
     /**
     * Paramètre le connecteur Oracle avec un login et un mot de passe
     * permettant d'accéder au serveur Oracle.
     * A faire une fois dans l'application.
     * @param pUrl url de la bdd
     * @param pDriverClass la classe du driver
     * @param pLogin login
     * @param pPasswd mot de passe
     */
    public static void initOracle(String pUrl, String pDriverClass, String pLogin, String pPasswd) {
        MyConnector.setParameters(pUrl, pDriverClass, pLogin, pPasswd);
    }

    /************************************************************************************************************
     *Description: permet de créer tous les ojets Java à parir de la bBDD
     *@return retourne un graphe
     *************************************************************************************************************/
    public static GrapheValue load() throws Exception {
        //on initialise l'historique des actions effectuées sur les données extraites de la base de données via les objets Java
        historique = new ArrayList<Etape>();
        //on crée de nouvelles listes de personnes, d'établissements et de liens d'amitié
        List<Personne> pers = new ArrayList<Personne>();
        List<Etablissement> etbs = new ArrayList<Etablissement>();
        Set<AreteValuee> ar = new HashSet<AreteValuee>();
        Connection conn = null;
        // on définit le login et le mot de passe de la BDD en local

        //on ouvre la connexion
        conn = MyConnector.getConnection();
        //creation des lieux à partir des données de la BDD récupérées par une requête
        List<Lieu> lieux = new ArrayList<Lieu>();
        Statement st = conn.createStatement();
        String query = "SELECT id_lieu, pays, ville, adresse, numero  FROM lieu  ";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            Lieu l = new Lieu(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
            lieux.add(l);
        }

        //creation des personnes à partir des données de la BDD récupérées par une requête
        st = conn.createStatement();
        query = "SELECT id_personne,nom,prenom,s.libelle,p.id_lieuNaiss, p.id_lieuRes  FROM personne p, sexe s where p.id_sexe=s.id_sexe";
        rs = st.executeQuery(query);
        while (rs.next()) {
            Personne p = new Personne(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            for (Lieu ln : lieux) {
                if (rs.getInt(5) == ln.getIdLieu()) {
                    p.setLieuNaiss(ln);
                }
            }
            for (Lieu lr : lieux) {
                if (rs.getInt(6) == lr.getIdLieu()) {
                    p.setLieuRes(lr);
                }
            }
            pers.add(p);
        }

        //creation des centres d'interet à partir des données de la BDD récupérées par une requête
        st = conn.createStatement();
        query = "SELECT id_personne,c.id_centreInteret,c.libelle,categorie  FROM Aimer a,CentreInteret c where a.id_centreInteret = c.id_centreInteret  ";
        rs = st.executeQuery(query);
        while (rs.next()) {
            CentreInteret c = new CentreInteret(rs.getString("libelle"), rs.getString("categorie"));
            for (Personne p : pers) {
                if (p.getId() == rs.getInt("id_personne")) {
                    p.addCentreInteret(c);
                }
            }
        }

        //creation des etablissements à partir des données de la BDD récupérées par une requête
        st = conn.createStatement();
        query = "SELECT id_etablissement,id_lieu,libelle,description  FROM etablissement";
        rs = st.executeQuery(query);
        while (rs.next()) {
            Etablissement e = new Etablissement(rs.getInt(1), rs.getString("libelle"), rs.getString("description"));
            for (Lieu l : lieux) {
                if (l.getIdLieu() == rs.getInt(2)) {
                    e.setLieu(l);
                }
            }
            etbs.add(e);
        }

        //creation des parcours scolaire à partir des données de la BDD récupérées par une requête
        st = conn.createStatement();
        query = "SELECT id_personne,id_etablissement,dateDebut,dateFin  FROM AvoirFaitPS a ";
        rs = st.executeQuery(query);
        while (rs.next()) {
            for (Etablissement e : etbs) {
                if (e.getId() == rs.getInt("id_etablissement")) {
                    for (Personne p : pers) {
                        if (p.getId() == rs.getInt("id_personne")) {
                            Sejour s = new Sejour(e, rs.getDate(3), rs.getDate(4));
                            p.addSejour(s);
                        }
                    }

                }
            }
        }

        //creation des parcours professionnels à partir des données de la BDD récupérées par une requête
        st = conn.createStatement();
        query = "SELECT id_personne,id_etablissement,dateDebut,dateFin,p.libelle  FROM AvoirFaitPP a,Poste p where p.id_poste=a.id_poste ";
        rs = st.executeQuery(query);
        while (rs.next()) {
            for (Etablissement e : etbs) {
                if (e.getId() == rs.getInt("id_etablissement")) {
                    for (Personne p : pers) {
                        if (p.getId() == rs.getInt("id_personne")) {
                            SejourP s = new SejourP(e, rs.getDate(3), rs.getDate(4), rs.getString(5));
                            p.addSejour(s);
                        }
                    }

                }
            }
        }

        //creation des liens d'amitié à partir des données de la BDD récupérées par une requête
        st = conn.createStatement();
        query = "SELECT id_personne1,id_personne2,evaluation FROM EtreAmi";
        rs = st.executeQuery(query);
        while (rs.next()) {
            for (Personne p1 : pers) {
                if (rs.getInt(1) == p1.getId()) {
                    for (Personne p2 : pers) {
                        if (rs.getInt(2) == p2.getId()) {

                            ar.add(new AreteValuee(p1, p2, rs.getInt(3)));
                        }
                    }

                }
            }
        }
        //on ferme la connexion
        MyConnector.closeConnection();
        //on crée un nouveau graphe à partir des listes de personnes et de liens d'amitié elles-mêmes crées précédement
        GrapheValue gr = new GrapheValue(pers, ar);
        for (Personne p : pers) {
            //on lie le graphe à chaque personne créée
            p.setGr(gr);
        }
        //on retourne le graphe créé
        return gr;

    }

    /************************************************************************************************************
     *Description: permet de mettre à jour la BDD en fonction des modifications apportées et présente dans l'historique
     *************************************************************************************************************/
    public static void sychronize() throws SQLException {
        Connection conn = null;
        //on ouvre la connexion
        conn = MyConnector.getConnection();
        Statement st;
        String query;
        //pour chaque étape de l'historique
        for (Etape e : historique) {
            //si le type de l'étape est égal à 1
            if (e.getType() == 1) {
                //la requête sera une création
                query = "INSERT INTO EtreAmi (id_personne1,id_personne2,evaluation) VALUES ('" + e.getId_Personne1() + "','" + e.getId_Personne2() + "','" + e.getValeur() + "')";

            } //si le type de l'étape est égal à 2
            else if (e.getType() == 3) {
                //la requête sera une modification
                query = "UPDATE EtreAmi SET evaluation = '" + e.getValeur() + "' WHERE id_personne1=" + e.getId_Personne1() + " and id_personne2=" + e.getId_Personne2();

            } //si le type de l'étape est égal à 3
            else {
                //la requête sera une suppression
                query = "delete from EtreAmi  WHERE id_personne1=" + e.getId_Personne1() + " and id_personne2=" + e.getId_Personne2();

            }
            st = conn.createStatement();
            st.executeQuery(query);
            st.close();

        }
        //on ferme la connexion
        MyConnector.closeConnection();
    }

    /************************************************************************************************************
     *Description: permet d'ajouter une ligne à l'historique afin de préciser qu'un lien à été modifié pour la BDD
     *@param p1 un entier
     *@param p2 un entier
     *@param valeur un entier
     *************************************************************************************************************/
    public static void update(int p1, int p2, int valeur) {
        historique.add(new Etape(3, p1, p2, valeur));
    }

    /************************************************************************************************************
     *Description: permet d'ajouter une ligne à l'historique afin de préciser qu'un lien à été créé pour la BDD
     *@param p1 un entier
     *@param p2 un entier
     *@param valeur un entier
     *************************************************************************************************************/
    public static void create(int p1, int p2, int valeur) {
        historique.add(new Etape(1, p1, p2, valeur));
    }

    /************************************************************************************************************
     *Description: permet d'ajouter une ligne à l'historique afin de préciser qu'un lien à été supprimé pour la BDD
     *@param p1 un entier
     *@param p2 un entier
     *@param valeur un entier
     *************************************************************************************************************/
    public static void delete(int p1, int p2) {
        historique.add(new Etape(2, p1, p2, 0));
    }
}
