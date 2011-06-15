package projetfinanee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.*;

public class ModeleBDD {

    public static void load(GrapheValue gr) throws SQLException, ClassNotFoundException {

        Connection conn = null;
        try {
            // create new connection and statement
            MyConnector.setParametersOracleLocal("projet", "projet");
            conn = MyConnector.getConnection();
            Statement st = conn.createStatement();

            String query = "SELECT * FROM personne";
            ResultSet rs = st.executeQuery(query);
            
            while (rs.next()) {
                System.out.print(rs.getString(1) + rs.getString("prenom")+"\n");
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
