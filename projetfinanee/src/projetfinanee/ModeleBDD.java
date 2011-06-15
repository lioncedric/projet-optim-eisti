package projetfinanee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ModeleBDD {

    public static void loadDriver() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }

    public static Connection newConnection() throws SQLException {
        final String url = "jdbc:mysql://localhost/dbessai";
        Connection conn = DriverManager.getConnection(url, "bduser", "SECRET");
        return conn;
    }

    public static void load(GrapheValue gr) throws SQLException {
        Connection conn = null;
        try {
            // create new connection and statement
            conn = newConnection();
            Statement st = conn.createStatement();

            String query = "SELECT nom,prenom,age FROM personne ORDER BY age";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                System.out.printf("%-20s | %-20s | %3d\n", rs.getString(1), rs.getString("prenom"), rs.getInt(3));
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
