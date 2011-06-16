package projetfinanee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModeleBDD {

    private static List<Etape> historique;

    public static GrapheValue load() throws SQLException, ClassNotFoundException {
        historique = new ArrayList<Etape>();
        List<Personne> pers = new ArrayList<Personne>();
        List<Etablissement> etbs = new ArrayList<Etablissement>();
        Set<AreteValuee> ar = new HashSet<AreteValuee>();
        Connection conn = null;
        try {
            // create new connection and statement
            MyConnector.setParametersOracleLocal("projet", "projet");
            conn = MyConnector.getConnection();
            //creation des lieux
            List<Lieu> lieux = new ArrayList<Lieu>();
            Statement st = conn.createStatement();
            String query = "SELECT id_lieu, pays, ville, adresse, numero  FROM lieu  ";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Lieu l = new Lieu(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
                lieux.add(l);
            }
            //creation des personnes
            st = conn.createStatement();
            query = "SELECT id_personne,nom,prenom,s.libelle,p.id_lieuNaiss, p.id_lieuRes  FROM personne p, sexe s where p.id_sexe=s.id_sexe";
            rs = st.executeQuery(query);
            while (rs.next()) {
                System.out.print(rs.getInt(1) + rs.getString(2) + rs.getString(3) + rs.getString(4) + "\n");
                Personne p = new Personne(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), new HashSet<CentreInteret>(), new HashSet<Sejour>());
                for(Lieu ln: lieux){
                    if(rs.getInt(5)==ln.getIdLieu()){
                        p.setLieuNaiss(ln);
                    }
                }
                for(Lieu lr: lieux){
                    if(rs.getInt(5)==lr.getIdLieu()){
                        p.setLieuRes(lr);
                    }
                }
                pers.add(p);
            }
            //creation des centres d'interet
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
            //creation des etablissements
            st = conn.createStatement();
            query = "SELECT id_etablissement,id_lieu,libelle,description  FROM etablissement";
            rs = st.executeQuery(query);
            while (rs.next()) {
                Etablissement e = new Etablissement(rs.getInt(1), rs.getString("libelle"), rs.getString("description"));
                for (Lieu l: lieux) {
                    if (l.getIdLieu() == rs.getInt(2)) {
                        e.setLieu(l);
                    }
                }
                etbs.add(e);
            }
            //creation des parcours
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
            //creation des liens d'amitie
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
            return new GrapheValue(pers, ar);
        } finally {
            // close result, statement and connection
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void sychronize() throws SQLException {
        Connection conn = MyConnector.getConnection();
        for (Etape e : historique) {
            String query = "";
            if (e.getType() == 1) {
                query = "INSERT INTO EtreAmi (id_personne1,id_personne2,evaluation) VALUES ('" + e.getId_Personne1() + "','" + e.getId_Personne2() + "','" + e.getValeur() + "')";

            }
            if (e.getType() == 2) {
                query = "UPDATE EtreAmi SET evaluation = ['" + e.getValeur() + "'] WHERE id_personne1=" + e.getId_Personne1() + " and id_personne2=" + e.getId_Personne2();

            }
            if (e.getType() == 3) {
                query = "delete from EtreAmi  WHERE id_personne1=" + e.getId_Personne1() + " and id_personne2=" + e.getId_Personne2();

            }
            Statement st = conn.createStatement();
            st.executeQuery(query);
            query = "commit;";
            st = conn.createStatement();
            st.executeQuery(query);
        }
    }

    public static void update(int p1, int p2, int valeur) {
        historique.add(new Etape(3, p1, p2, valeur));
    }

    public static void create(int p1, int p2, int valeur) {
        historique.add(new Etape(1, p1, p2, valeur));
    }

    public static void delete(int p1, int p2) {
        historique.add(new Etape(2, p1, p2, 0));
    }
}
