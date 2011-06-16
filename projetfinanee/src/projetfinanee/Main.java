/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projetfinanee;

/**
 *
 * @author Administrator
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception  {

        GrapheValue gr = ModeleBDD.load("projet", "projet");

        System.out.println(gr);

        gr.evaluerAmitie(gr.getPersonne(1), gr.getPersonne(2), 12);
       gr.evaluerAmitie(gr.getPersonne(2), gr.getPersonne(3), 12);
        gr.evaluerAmitie(gr.getPersonne(2), gr.getPersonne(4), 14);
        gr.evaluerAmitie(gr.getPersonne(4), gr.getPersonne(0), 12);
        gr.evaluerAmitie(gr.getPersonne(0), gr.getPersonne(1), 13);
        for (Personne p : gr.getPersonne(0).rechercheAmis(5,10)) {
            System.out.println(p);
        }
         // System.out.println(gr.rechercherParNomPrenom(null, "lion"));
            System.out.println(gr.rechercheParCentreInteret("basket", "sport"));
      


      // ModeleBDD.sychronize();

    }
}
