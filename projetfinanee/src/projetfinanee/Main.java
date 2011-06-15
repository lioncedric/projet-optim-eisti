/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projetfinanee;

import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ModeleBDD.load(null);
    }

}
