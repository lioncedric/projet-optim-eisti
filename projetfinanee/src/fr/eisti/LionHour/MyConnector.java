package fr.eisti.LionHour;

/**
 * classe MyConnector
 * permet de se connecter/déconnecter à une base de données Oracle
 * assure l'unicité de la connection (inspiré du singleton)
 *
 * Utiliser uniquement les méthodes de MyConnector pour ouvrir une
 * nouvelle connexion (ou obtenir celle en cours) et fermer une
 * connexion :
 *
 * Connection connection = MyConnector.getConnection();
 * // use connection
 * MyConnector.closeConnection();
 *
 * @author Matthias Colin
 * version 2.3 (11/01/2011)
 *
 * version 2 : paramètrage login/passwd
 * version 2.2 : paramètrage driver,serveur,port, bddname
 * version 2.3 : par defaut sur Oracle, paramétrable pour mysql
 */


import java.sql.*;

public class MyConnector {

    // champs nécessaires à la connexion

	// url de connexion à la base
    private static String url= null;

	// détails
    private static final String DEFAULT_DRIVER = "jdbc:oracle:thin:";
    private static final String EISTI_SERVER = "area.etude.pau.eisti.fr";
    private static final String LOCAL_SERVER = "localhost";
    private static final int DEFAULT_PORT = 1521;
    private static final String EISTI_BDD = "BDDETU";
    private static final String LOCAL_BDD = "XE";

    // classe du pilote qui gère la connexion Oracle
    private static final String ORACLE_DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
    private static String driverClass = null;

    // login et mot de passe sur le serveur de BDD
    private static String login= null;
    private static String passwd= null;

    // champs assurant l'unicité de la connexion et du chargement du driver
    private static Connection refUniqueConnection = null;
    private static boolean isDriverRegistered = false;

	/**
 	 * Paramètre le connecteur avec un login et un mot de passe
 	 * permettant d'accéder au serveur Oracle local.
 	 * A faire une fois dans l'application.
 	 */ 	
	public static void setParametersOracleLocal(String pLogin, String pPasswd) {
		setParameters(DEFAULT_DRIVER, LOCAL_SERVER, DEFAULT_PORT, LOCAL_BDD,
            ORACLE_DRIVER_CLASS, pLogin, pPasswd);
	}

	/**
 	 * Paramètre le connecteur avec un login et un mot de passe
 	 * permettant d'accéder au serveur Oracle de l'école.
 	 * A faire une fois dans l'application.
 	 */ 	
	public static void setParametersOracleEisti(String pLogin, String pPasswd) {
		setParameters(DEFAULT_DRIVER, EISTI_SERVER, DEFAULT_PORT, EISTI_BDD,
            ORACLE_DRIVER_CLASS, pLogin, pPasswd);
	}

	/**
 	 * Paramètre le connecteur Oracle avec un login et un mot de passe
 	 * permettant d'accéder au serveur Oracle.
 	 * A faire une fois dans l'application.
 	 */ 	
	public static void setParameters(String pDriver, String pServer, int pPort,
			String pBddname, String pDriverClass, String pLogin, String pPasswd) {
		url = pDriver + "@" + pServer + ":" + pPort + ":" + pBddname;
        driverClass = pDriverClass;
		login = pLogin;
		passwd = pPasswd;
	}

    /**
 	* renvoie une connexion unique vers une base de donnée.
 	*/
    public static Connection getConnection(){
		//on regarde si la connexion existe déjà
		if(refUniqueConnection == null){
		    try{
		        // chargement du driver de la BDD si besoin
	        	if (!isDriverRegistered) {
        			// chargement du driver
        		    DriverManager.registerDriver((Driver) (Class.forName(driverClass).newInstance()));
        		    isDriverRegistered = true;
	        	}
        		// création de la connection
		        refUniqueConnection = DriverManager.getConnection(url,login,passwd);
		    } catch (Exception e) {
				// on renvoie une connection nulle en cas d'erreurs de connexion
				System.err.println("Erreur de connexion à la base : "
					+e.getMessage());
                refUniqueConnection = null;
		    }
		}
		//on renvoie la connexion
		return refUniqueConnection;
	}

	/**
 	 * ferme la connexion en cours.
 	 */
    public static void closeConnection() {
		try{
			// fermeture de la connexion
		    refUniqueConnection.close();
			// suppression de la référence vers la connexion close
		    refUniqueConnection = null;
		}catch (SQLException e) {
			// TODO : comportement modifiable
			// on quitte l'application en cas d'erreur
			System.err.println("Erreur de fermeture d'accès à la base : "
				+ e.getMessage());
		    // e.printStackTrace();
		    // System.exit(1);
		}
    }

}
