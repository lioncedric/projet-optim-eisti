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

    public static void load(GrapheValue gr) throws SQLException, ClassNotFoundException {
        List<Personne> pers = new ArrayList<Personne>();
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
                System.out.print(rs.getInt(1) + rs.getString(2) + rs.getString(3) + rs.getString(4) + "\n");
                Personne p = new Personne(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), new HashSet<CentreInteret>(),new HashSet<Sejour>());
                pers.add(p);
            }
            // //creation des liens
            st = conn.createStatement();
            query = "SELECT id_personne,c.id_centreInteret,c.libelle,categorie  FROM Aimer a,CentreInteret c where a.id_centreInteret = c.id_centreInteret  ";
            rs = st.executeQuery(query);
            while (rs.next()) {
                System.out.print(rs.getInt(1) + rs.getString(2) + rs.getString("libelle")+ rs.getString("categorie") + "\n");
                 CentreInteret c = new CentreInteret(rs.getString("libelle"), rs.getString("categorie"));
                for(Personne p:pers){
                    if (p.getId()==rs.getInt("id_personne")){
                        p.addCentreInteret(c);
                    }
                }
            }
           //creation des etablissements
            st = conn.createStatement();
            query = "SELECT id_personne,c.id_centreInteret,c.libelle,categorie  FROM Aimer a,CentreInteret c where a.id_centreInteret = c.id_centreInteret  ";
            rs = st.executeQuery(query);
            while (rs.next()) {
                System.out.print(rs.getInt(1) + rs.getString(2) + rs.getString("libelle")+ rs.getString("categorie") + "\n");
                 CentreInteret c = new CentreInteret(rs.getString("libelle"), rs.getString("categorie"));
                for(Personne p:pers){
                    if (p.getId()==rs.getInt("id_personne")){
                        p.addCentreInteret(c);
                    }
                }
            }
            System.out.println(pers);
        } finally {
            // close result, statement and connection
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void save(GrapheValue gr) {
    }
}
