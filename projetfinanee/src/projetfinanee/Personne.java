package projetfinanee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Personne {

    private int id;
    private String nom;
    private String prenom;
    private String sexe;
    private Set<CentreInteret> interets;
    private Set<Sejour> sej;
    private Set<Personne> amisEnAttente;
    private Lieu lieuNaiss;
    private Lieu lieuRes;
    private GrapheValue gr;

    public Personne(int id, String nom, String prenom, String sexe) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.lieuNaiss = new Lieu();
        this.lieuRes = new Lieu();
        this.amisEnAttente = new HashSet<Personne>();
        this.interets = new HashSet<CentreInteret>();
        this.sej = new HashSet<Sejour>();

    }

    public Personne() {
        this.id = 0;
        this.nom = "";
        this.prenom = "";
        this.sexe = "";
        this.interets = new HashSet<CentreInteret>();
        this.sej = new HashSet<Sejour>();
        this.amisEnAttente = new HashSet<Personne>();
        this.lieuNaiss = new Lieu();
        this.lieuRes = new Lieu();
    }

    public Set<Personne> rechercheAmis(int nbAmis, int profondeur) {
        Set<Personne> sommetsVisites = new HashSet<Personne>();
        List<Choix> choixRetenus = new ArrayList<Choix>();
        Set<Personne> personnesRetenues = new HashSet<Personne>();
        parcoursProfondeur(this.gr, this, sommetsVisites, choixRetenus, 1, nbAmis, profondeur, 100);
        for (Choix c : choixRetenus) {

            personnesRetenues.add(c.getP());
        }
        return personnesRetenues;
    }

    public static void parcoursProfondeur(GrapheValue g, Personne origine,
        Set<Personne> sommetsVisites, List<Choix> sommetsRetenus, int rang, int nbAmis, int profondeur, double note) {
        sommetsVisites.add(origine);
        Iterator<Personne> i = origine.recupererAmis();
        while (i.hasNext()) {
            Personne suivant = i.next();
            note = note * g.getEvaluation(origine, suivant);
            if (!sommetsVisites.contains(suivant)) {
                if (rang >= 2) {
                    if (sommetsRetenus.size() < nbAmis) {
                        sommetsRetenus.add(new Choix(suivant, note));
                    } else {
                        Collections.sort(sommetsRetenus);
                        sommetsRetenus.set(4, new Choix(suivant, note));
                    }
                }
                rang++;
                parcoursProfondeur(g, suivant, sommetsVisites, sommetsRetenus, rang, nbAmis, profondeur, note);
            }
        }
       
    }

    public Iterator<Personne> recupererAmis() {
        return gr.recupererAmis(this).iterator();
    }

    @Override
    public String toString() {
        return "Personne{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", sexe=" + sexe + ", interets=" + interets + ", sej=" + sej + ", amisEnAttente=" + amisEnAttente + ", lieuNaiss=" + lieuNaiss + ", lieuRes=" + lieuRes + "}";
    }

    public void ajouterAmisEnAttente(Personne p) {
        amisEnAttente.add(p);
    }

    public boolean refuserAmis(Personne p) {
        return amisEnAttente.remove(p);
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

    public Lieu getLieuNaiss() {
        return lieuNaiss;
    }

    public Lieu getLieuRes() {
        return lieuRes;
    }

    public Set<Personne> getAmisEnAttente() {
        return amisEnAttente;
    }

    public Set<CentreInteret> getInterets() {
        return interets;
    }

    public Set<Sejour> getSej() {
        return sej;
    }

    public void setLieuNaiss(Lieu lieuNaiss) {
        this.lieuNaiss = lieuNaiss;
    }

    public void setLieuRes(Lieu lieuRes) {
        this.lieuRes = lieuRes;
    }

    public void addCentreInteret(CentreInteret c) {
        interets.add(c);
    }

    public void addSejour(Sejour s) {
        sej.add(s);
    }

    public void setGr(GrapheValue gr) {
        this.gr = gr;
    }
}
