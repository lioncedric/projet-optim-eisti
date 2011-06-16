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

        gr.evaluerAmitie(gr.getPersonne(1), gr.getPersonne(2), 12);
        gr.evaluerAmitie(gr.getPersonne(2), gr.getPersonne(3), 12);
        gr.evaluerAmitie(gr.getPersonne(3), gr.getPersonne(4), 12);
        gr.evaluerAmitie(gr.getPersonne(4), gr.getPersonne(0), 12);
        gr.evaluerAmitie(gr.getPersonne(0), gr.getPersonne(1), 13);
        for (Personne p : gr.getPersonne(0).rechercheAmis()) {

            System.out.println(p);
        }

        //System.out.println(gr.recupererAmis(gr.getPersonne(0)));
        ModeleBDD.sychronize();

    }
}
