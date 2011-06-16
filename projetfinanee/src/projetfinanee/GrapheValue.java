package projetfinanee;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class GrapheValue {

    //variables de la classe
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
        //on déclare et on initialise un itérateur permettant de percourir l'ensemble des arêtes nommé aretes
        Iterator<AreteValuee> it = aretes.iterator();
        //on déclare et on initialise une liste de personnes permettant de stocker les amis
        List<Personne> res = new LinkedList<Personne>();
        //tant qu'il existe une arête dans la liste d'arêtes
        while (it.hasNext()) {
            //on initialise une arête par l'intermédiaire de l'itérateur
            AreteValuee ar = it.next();
            //si la personne dont on cherche les amis correspond à la personne p1 de l'arête
            if (ar.getP1().equals(p)) {
                //on ajoute la personne p2 de l'arête dans la liste d'amis
                res.add(ar.getP2());
            }
        }
        //on retourne la liste d'amis
        return res;
    }

    public boolean supprimerUnAmi(Personne p1, Personne p2) {
        //on déclare et on initialise un itérateur permettant de percourir l'ensemble des arêtes nommé aretes
        Iterator<AreteValuee> it = aretes.iterator();
        //on déclare et on initialise l'arête à supprimer à null
        AreteValuee arARemove = null;
        //tant qu'il existe une arête dans la liste d'arêtes
        while (it.hasNext()) {
            //on initialise une arête par l'intermédiaire de l'itérateur
            AreteValuee ar = it.next();
            //si le paramètre p1 correspond à la personne p1 de l'arête
            //et si le paramètre p2 correspond à la personne p2 de l'arête
            if (ar.getP1().equals(p1) && ar.getP2().equals(p2)) {
                //on supprime le lien d'amitié entre p1 et p2
                arARemove = ar;
                //l'action de suppression est mémorisée pour la synchronisation avec la BDD
                ModeleBDD.delete(p1.getId(), p2.getId());
            }
        }
        //on supprime le lien d'amitié entre p1 et p2
        //on retourne l'état de la suppression de ce lien d'amitié
        return aretes.remove(arARemove);
    }

    public int getEvaluation(Personne p1, Personne p2) {
        //on déclare et on initialise un itérateur permettant de percourir l'ensemble des arêtes nommé aretes
        Iterator<AreteValuee> it = aretes.iterator();
        //tant qu'il existe une arête dans la liste d'arêtes
        while (it.hasNext()) {
            //tant qu'il existe une arête dans la liste d'arêtes
            AreteValuee ar = it.next();
            //si un lien d'amitié existe entre p1 et p2
            if (ar.getP1().equals(p1) && ar.getP2().equals(p2)) {
                //on retourne la valeur d'amitié considérée par p1
                return ar.getEvaluation();
            }
        }
        //on retourne une valeur de 0 si aucun lien d'amitié n'existe entre p1 et p2
        return 0;
    }

    public boolean evaluerAmitie(Personne p1, Personne p2, int valeur)  throws  Exception{
        Boolean res = false;
        //on déclare et on initialise un itérateur permettant de percourir l'ensemble des arêtes nommé aretes
        Iterator<AreteValuee> it = aretes.iterator();
        //tant qu'il existe une arête dans la liste d'arêtes
        while (it.hasNext()) {
            //tant qu'il existe une arête dans la liste d'arêtes
            AreteValuee ar = it.next();
            //si une arête ayant pour sommets p1 et p2 existe
            if (ar.getP1().equals(p1) && ar.getP2().equals(p2)) {
                //on remplace sa valeur par celle placée en paramètre
                ar.setEvaluation(valeur);
                //l'action de modification est mémorisée pour la synchronisation avec la BDD
                ModeleBDD.update(p1.getId(), p2.getId(), valeur);
                //le booleen d'affectation de la valeur à une arête prend donc vrai
                res = true;
            }
        }
        //si, après avoir parcouru l'ensemble des arêtes exsitantes, aucune affectation n'a été réalisée
        if (!res) {
            //on appelle la fonction d'ajout d'un lien d'amitié avec les valeurs placées en paramètres
            ajouterAmis(p1, p2, valeur);
            //le booleen d'affectation de la valeur à une arête prend donc vrai
            res = true;
        }
        //on retourne le booleen permettant de confirmer l'affectation de la valeur d'amitié
        return res;
    }

    private void ajouterAmis(Personne p1, Personne p2, int valeur) throws Exception{
        //on ajoute une nouvelle arête, composée des éléments placés en paramètres, à l'ensemble des liens d'amitié
        aretes.add(new AreteValuee(p1, p2, valeur));
        //l'action de création est mémorisée pour la synchronisation avec la BDD
        ModeleBDD.create(p1.getId(), p2.getId(), valeur);
    }

   

    public Personne getPersonne(int i) {
        return sommets.get(i);
    }

    public void proposerAmis(Personne p1, Personne p2) {
        //on ajoute p2 à l'ensemble des personnes étant en attente d'une réponse de la part de p1
        p1.ajouterAmisEnAttente(p2);
    }

    public List<Personne> rechercherParNomPrenom(String nom, String prenom) {
        List<Personne> trouves;
        trouves = new LinkedList<Personne>();
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
                if (p.getPrenom().compareToIgnoreCase(prenom) == 0) {
                    trouves.add(p);
                }
            }
        }
        return trouves;
    }

    public List<Personne> rechercheParParcours(String nomE, String ville, int annee) {
        List<Personne> trouves;
        trouves = new LinkedList<Personne>();
        if (nomE!=null) {
            for (Personne p : sommets) {
               
                for (Sejour parcours : p.getSej()) {
                    
                    if (Integer.parseInt(parcours.getDateDebut().toString().substring(0,4)) <= annee && annee <=Integer.parseInt(parcours.getDateFin().toString().substring(0,4))  && parcours.getEtab().getNom().compareToIgnoreCase(nomE) == 0) {
                        if (ville != null) {
                            if (parcours.getEtab().getLieu().getVille().compareToIgnoreCase(ville) == 0) {
                                trouves.add(p);
                            }
                        } else {
                            trouves.add(p);
                        }
                    }
                }
            }
        }
        return trouves;
    }

    public List<Personne> rechercheParCentreInteret(String categorie,String libelle){
        List<Personne> trouves;
        trouves = new LinkedList<Personne>();
        if (categorie!=null && libelle!=null) {
            
            for (Personne p : sommets) {
                
                for (CentreInteret ci : p.getInterets()){
                      
                    if(ci.getCategorie().compareToIgnoreCase(categorie)==0 && ci.getLibelle().compareToIgnoreCase(libelle)==0){
                     
                        trouves.add(p);
                    }
                }
            }
        }
        else if(categorie!=null){
            for (Personne p : sommets) {
                for (CentreInteret ci : p.getInterets()){
                    if(ci.getCategorie().compareToIgnoreCase(categorie)==0){
                        trouves.add(p);
                    }
                }
            }
        }
        else if(libelle!=null){
            for (Personne p : sommets) {
                for (CentreInteret ci : p.getInterets()){
                    if(ci.getLibelle().compareToIgnoreCase(libelle)==0){
                        trouves.add(p);
                    }
                }
            }
        }
        return trouves;
    }
}
