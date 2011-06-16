/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projetfinanee;

/**
 *
 * @author Administrator
 */
public class Choix implements Comparable<Choix>{

    //variables de la classe
    private Personne p;
    private double interet;

    public Choix(Personne p, double interet) {
        this.p = p;
        this.interet = interet;
    }

    @Override
    public int compareTo(Choix o) {
      return (int)(interet-o.getInteret());
    }

    @Override
    public String toString() {
        return "Choix{" + "p=" + p + "interet=" + interet + '}';
    }

    public double getInteret() {
        return interet;
    }

    public Personne getP() {
        return p;
    }

}
