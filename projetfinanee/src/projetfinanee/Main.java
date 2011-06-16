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
        GrapheValue gr = ModeleBDD.load();
        System.out.println(gr);
        gr.supprimerUnAmi(gr.getPersonne(0), gr.getPersonne(1));
        System.out.println(gr.getEvaluation(gr.getPersonne(0), gr.getPersonne(1)));
        gr.ajouterAmis(gr.getPersonne(0), gr.getPersonne(1), 12);
        System.out.println(gr.getEvaluation(gr.getPersonne(0), gr.getPersonne(1)));
    }
}
