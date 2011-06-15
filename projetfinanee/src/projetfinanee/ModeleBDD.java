package projetfinanee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ModeleBDD {

    public static void load(GrapheValue gr) throws SQLException, ClassNotFoundException {
        List<Personne> pers = new ArrayList<Personne>();
        Connection conn = null;
        try {
            // create new connection and statement
            MyConnector.setParametersOracleLocal("projet", "projet");
            conn = MyConnector.getConnection();
            Statement st = conn.createStatement();
            String query = "SELECT id_personne,nom,prenom,s.libelle  FROM personne p, sexe s where p.id_sexe=s.id_sexe";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                System.out.print(rs.getInt(1) + rs.getString(2) + rs.getString(3) + rs.getString(4) + "\n");
                Personne p = new Personne(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), new HashSet<CentreInteret>(),new HashSet<Sejour>());
            }

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
