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
   // public static arr
    public static GrapheValue load() throws SQLException, ClassNotFoundException {
        List<Personne> pers = new ArrayList<Personne>();
        List<Etablissement> etbs = new ArrayList<Etablissement>();
        Set<AreteValuee> ar = new HashSet<AreteValuee>();
        Connection conn = null;
        try {
            // create new connection and statement
            MyConnector.setParametersOracleLocal("projet", "projet");
            conn = MyConnector.getConnection();
            //creation des personnes
            Statement st = conn.createStatement();
            String query = "SELECT id_personne,nom,prenom,s.libelle  FROM personne p, sexe s where p.id_sexe=s.id_sexe";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Personne p = new Personne(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), new HashSet<CentreInteret>(), new HashSet<Sejour>());
                pers.add(p);
            }
            //creation des liens
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
            query = "SELECT id_etablissement,id_lieu,libelle,description  FROM etablissement  ";
            rs = st.executeQuery(query);
            while (rs.next()) {
                Etablissement e = new Etablissement(rs.getInt(1), rs.getString("libelle"), rs.getString("description"));
                etbs.add(e);
            }
            // //creation des sejour
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

    public static void sychronize(GrapheValue gr) {
    }

    public static void update(GrapheValue gr) {
    }

    public static void create(GrapheValue gr) {
    }

    public static void delete(GrapheValue gr) {
    }
}
