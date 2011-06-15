package projetfinanee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class GrapheValue {

    private ArrayList<Personne> sommets;
    private Set<AreteValuee> aretes;

   public  List<Personne> recupererAmis(Personne p) {
      Iterator<AreteValuee> it= aretes.iterator();
      List<Personne> res=new LinkedList<Personne>();
      while(it.hasNext()){
        AreteValuee ar= it.next();
        if(ar.getP1().equals(p)){
            res.add(ar.getP2());
        }
      }
      return res;
    }
}
