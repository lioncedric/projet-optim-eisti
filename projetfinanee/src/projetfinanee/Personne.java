

package projetfinanee;

import java.util.HashSet;
import java.util.Set;

public class Personne {

    private int id;
    private String nom;
    private String prenom;
    private String sexe;
    private Set<CentreInteret> interets;
    private Set<Sejour> sej;

    public Personne(int id, String nom, String prenom, String sexe, Set<CentreInteret> interets, Set<Sejour> sej) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.interets = interets;
        this.sej = sej;
    }

    public Personne() {
        this.id=0;
        this.nom="";
        this.prenom="";
        this.sexe="";
        this.interets=new HashSet<CentreInteret>();
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public Set<CentreInteret> getInterets() {
        return interets;
    }

    public Set<Sejour> getSej() {
        return sej;
    }


}
