package projetfinanee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class GrapheValue {

    private List<Personne> sommets;
    private Set<AreteValuee> aretes;

    public GrapheValue(List<Personne> sommets, Set<AreteValuee> aretes) {
        this.sommets = sommets;
        this.aretes = aretes;
    }

    @Override
    public String toString() {
        return "GrapheValue{" + "sommets=" + sommets + '}';
    }

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
       AreteValuee arARemove=null;
      while(it.hasNext()){
        AreteValuee ar= it.next();
        if(ar.getP1().equals(p1) && ar.getP2().equals(p2)){
              arARemove=ar;
           
        }
      }
      return  aretes.remove(arARemove);
   }

    public int getEvaluation(Personne p1, Personne p2){
      Iterator<AreteValuee> it= aretes.iterator();
      while(it.hasNext()){
        AreteValuee ar= it.next();
        if(ar.getP1().equals(p1) && ar.getP2().equals(p2)){
           return ar.getEvaluation();
        }
      }
      return 0;
   }

    public boolean evaluerAmitie(Personne p1, Personne p2, int valeur){
        Boolean res = false;
        Iterator<AreteValuee> it= aretes.iterator();
        while(it.hasNext()){
        AreteValuee ar= it.next();
            if(ar.getP1().equals(p1) && ar.getP2().equals(p2)){
                ar.setEvaluation(valeur);
                res = true;
            }
        }
        if(!res){
            aretes.add(new AreteValuee(p1, p2, valeur));
            res = true;
        }
        return res;
    }

    public void ajouterAmis(Personne p1, Personne p2, int valeur) {
        aretes.add(new AreteValuee(p1, p2, valeur));
    }

    public Personne getPersonne(int i) {
        return sommets.get(i);
    }



    public void proposerAmis(Personne p1, Personne p2) {
        p1.ajouterAmisEnAttente(p2);
    }
}
