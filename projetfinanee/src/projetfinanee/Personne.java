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
    private boolean visite;
    private Set<Personne> amisEnAttent;

    public Personne(int id, String nom, String prenom, String sexe, Set<CentreInteret> interets, Set<Sejour> sej) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.interets = interets;
        this.sej = sej;
        this.visite = false;
    }

    public Personne() {
        this.id = 0;
        this.nom = "";
        this.prenom = "";
        this.sexe = "";
        this.interets = new HashSet<CentreInteret>();
        this.visite = false;
    }

    @Override
    public String toString() {
        return "Personne{" + "id=" + id + "nom=" + nom + "prenom=" + prenom + "sexe=" + sexe + "interets=" + interets + "sej=" + sej + "visite=" + visite + "amisEnAttent=" + amisEnAttent + '}';
    }

    public void ajouterAmisEnAttente(Personne p) {
        amisEnAttent.add(p);
    }

    public boolean refuserAmis(Personne p) {
        return amisEnAttent.remove(p);
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

    public void addCentreInteret(CentreInteret c) {
        interets.add(c);
    }
     public void addSejour(Sejour s) {
        sej.add(s);
    }

    public boolean isVisite() {
        return visite;
    }

    public void setVisite(boolean visite) {
        this.visite = visite;
    }
}
