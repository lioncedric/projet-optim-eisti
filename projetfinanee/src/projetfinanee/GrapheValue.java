package projetfinanee;

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
        String str="";
        for(Personne p: sommets){
            str=str+p.toString()+"\n";
        }
        return str;
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

    public boolean supprimerUnAmi(Personne p1, Personne p2) {
        boolean res = false;
        Iterator<AreteValuee> it = aretes.iterator();
        AreteValuee arARemove = null;
        while (it.hasNext()) {
            AreteValuee ar = it.next();
            if (ar.getP1().equals(p1) && ar.getP2().equals(p2)) {
                arARemove = ar;
                ModeleBDD.delete(p1.getId(), p2.getId());
            }
        }

        return aretes.remove(arARemove);
    }

    public int getEvaluation(Personne p1, Personne p2) {
        Iterator<AreteValuee> it = aretes.iterator();
        while (it.hasNext()) {
            AreteValuee ar = it.next();
            if (ar.getP1().equals(p1) && ar.getP2().equals(p2)) {
                return ar.getEvaluation();
            }
        }
        return 0;
    }

    public boolean evaluerAmitie(Personne p1, Personne p2, int valeur) {
        Boolean res = false;
        Iterator<AreteValuee> it = aretes.iterator();
        while (it.hasNext()) {
            AreteValuee ar = it.next();
            if (ar.getP1().equals(p1) && ar.getP2().equals(p2)) {
                ar.setEvaluation(valeur);
                ModeleBDD.update(p1.getId(), p2.getId(), valeur);
                res = true;
            }
        }
        if (!res) {
            ajouterAmis(p1, p2, valeur);
            res = true;
        }
        return res;
    }

    private void ajouterAmis(Personne p1, Personne p2, int valeur) {
        aretes.add(new AreteValuee(p1, p2, valeur));
        ModeleBDD.create(p1.getId(), p2.getId(), valeur);
    }

   

    public Personne getPersonne(int i) {
        return sommets.get(i);
    }

    public void proposerAmis(Personne p1, Personne p2) {
        p1.ajouterAmisEnAttente(p2);
    }

    public List<Personne> rechercherParNomPrenom(String nom, String prenom) {
        List<Personne> trouves;
        trouves = new LinkedList();
        if (nom != null && prenom != null) {
            for (Personne p : sommets) {
                if (p.getNom().compareToIgnoreCase(nom) == 0 && p.getPrenom().compareToIgnoreCase(prenom) == 0) {
                    trouves.add(p);
                }
            }
        } else if (nom != null) {
            for (Personne p : sommets) {
                if (p.getNom().compareToIgnoreCase(nom) == 0) {
                    trouves.add(p);
                }
            }
        } else if (prenom != null) {
            for (Personne p : sommets) {
                if (p.getPrenom().compareToIgnoreCase(nom) == 0) {
                    trouves.add(p);
                }
            }
        }
        return trouves;
    }

   // public List<Personne> rechercheParParcours(String nomEtablissement, String ville, int annee) {
  //  }
}
