package projetfinanee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Personne {

    //variables de la classe
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

        sommetsVisites.add(this);
        Iterator<Personne> i = this.recupererAmis();
        while (i.hasNext()) {
            sommetsVisites.add(i.next());
        }
        i = this.recupererAmis();
        double note;
        while (i.hasNext()) {
           Personne suivant = i.next();
           note= (this.gr.getEvaluation(this, suivant)*1.0/100);
           parcoursProfondeur(this.gr,suivant, sommetsVisites, choixRetenus, nbAmis, profondeur, note);
        }
        for (Choix c : choixRetenus) {
            personnesRetenues.add(c.getP());
        }
        return personnesRetenues;
    }

    public static void parcoursProfondeur(GrapheValue g, Personne origine,
            Set<Personne> sommetsVisites, List<Choix> sommetsRetenus, int nbAmis, int profondeur, double note) {
       // sommetsVisites.add(origine);
        Iterator<Personne> i = origine.recupererAmis();
        double eval;
        int rang;
        while (i.hasNext()) {
            Personne suivant = i.next();
            eval = note * (g.getEvaluation(origine, suivant)*1.0/100);
            if (!sommetsVisites.contains(suivant)) {
              
                if (sommetsRetenus.size() < nbAmis) {
                    sommetsRetenus.add(new Choix(suivant, eval));
                } else {
                    Collections.sort(sommetsRetenus);
                    if (sommetsRetenus.get(0).getInteret() < eval) {
                       
                        sommetsRetenus.set(0, new Choix(suivant, eval));
                    }
                }
                rang=profondeur-1;
                if (rang > 0) {
                    parcoursProfondeur(g, suivant, sommetsVisites, sommetsRetenus, nbAmis, rang, eval);
                }
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
