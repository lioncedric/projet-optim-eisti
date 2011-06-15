package projetfinanee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class GrapheValue {

    private ArrayList<Personne> sommets;
    private Set<AreteValuee> aretes;

    public List<Personne> recupererAmis(Personne p) {
        Iterator<AreteValuee> it = aretes.iterator();
        List<Personne> res = new LinkedList<Personne>();
        while (it.hasNext()) {
            AreteValuee ar = it.next();
            if (ar.getP1().equals(p)) {
                res.add(ar.getP2());
            }
        }
        return res;
    }

   public boolean supprimerUnAmi(Personne p1, Personne p2){
      boolean res = false;
      Iterator<AreteValuee> it= aretes.iterator();
      while(it.hasNext()){
        AreteValuee ar= it.next();
        if(ar.getP1().equals(p1) && ar.getP2().equals(p2)){
            res = aretes.remove(ar);
        }
      }
      return res;
   }
    public int evaluerAmis(Personne p1, Personne p2){

      Iterator<AreteValuee> it= aretes.iterator();
      while(it.hasNext()){
        AreteValuee ar= it.next();
        if(ar.getP1().equals(p1) && ar.getP2().equals(p2)){
           return ar.getEvaluation();
        }
      }
      return 0;
   }
       public void ajouterAmis(Personne p1, Personne p2, int valeur) {
        aretes.add(new AreteValuee(p1, p2, valeur));
    }



    public void proposerAmis(Personne p1, Personne p2) {
        p1.ajouterAmisEnAttente(p2);
    }
}
