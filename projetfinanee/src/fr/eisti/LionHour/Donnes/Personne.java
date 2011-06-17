package fr.eisti.LionHour.Donnes;

import fr.eisti.LionHour.graphe.GrapheValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/******************************************************************************
 *Description: classe qui permet de définir une personne
 * @author HOURCLATS Teddy, LION Cédric
 * @version 1.0
 ******************************************************************************/
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

    /************************************************************************************************************
     *Description: permet de créer une personne à partir d'un id, d'un nom, d'un prenom, d'un sexe
     *@param id un entier qui permet de définir une personne de façon unique
     *@param nom une chaîne
     *@param prenom une chaîne
     *@param sexe une chaîne
     *************************************************************************************************************/
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

    /************************************************************************************************************
     *Description: permet de créer une personne sans aucun paramètre
     *************************************************************************************************************/
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

    /************************************************************************************************************
     *Description: permet de récupérer les personnes qui sont susceptibles de devenir ami avec la personne qui appelle cette fonction
     *@param nbAmis un entier qui correspond au nombre de personnes que l'utilisateur voudra obtenir dans la liste de suggestion
     *@param profondeur un entier qui permet de dire jusqu'à quelle profondeur de l'arbre on peut rechercher des personnes
     *@return retourne un ensemble de personnes
     * @param augmenterVitesse si vrai augmente la vitesse de calcul mais diminue la precision
     *************************************************************************************************************/
    public Set<Personne> rechercheAmis(int nbAmis, int profondeur, boolean augmenterVitesse) {
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
            note = (this.gr.getEvaluation(this, suivant) * 1.0 / 100);
            parcoursProfondeur(this.gr, suivant, sommetsVisites, choixRetenus, nbAmis, profondeur, note, augmenterVitesse);
        }
        for (Choix c : choixRetenus) {
            personnesRetenues.add(c.getP());
        }
        return personnesRetenues;
    }

    /******************************************************************************************************************
     * Descritpion: permet de parcourir tout le graphe, à une profondeur donnée et permet de stocker les personnes susceptibles de devenir ami avec la personne qui appelle cette fonction
     * @param g un graphe à parcourir
     * @param origine une personne
     * @param sommetsVisites un ensemble de personnes que l'on a déjà visité pendant le parcours
     * @param sommetsRetenus une liste de choix correspondant aux personnes trouvées
     * @param nbAmis un entier qui représente le nombre d'amis désiré
     * @param profondeur un entier
     * @param note un réel
     * @param augmenterVitesse si vrai augmente la vitesse de calcul mais diminue la precision
     ******************************************************************************************************************/
    public static void parcoursProfondeur(GrapheValue g, Personne origine,
            Set<Personne> sommetsVisites, List<Choix> sommetsRetenus, int nbAmis, int profondeur, double note, boolean augmenterVitesse) {
        if (augmenterVitesse) {
            sommetsVisites.add(origine);
        }
        Iterator<Personne> i = origine.recupererAmis();
        double eval;
        int rang;
        while (i.hasNext()) {
            Personne suivant = i.next();
            eval = note * (g.getEvaluation(origine, suivant) * 1.0 / 100);
            if (!sommetsVisites.contains(suivant)) {

                if (sommetsRetenus.size() < nbAmis) {
                    sommetsRetenus.add(new Choix(suivant, eval));
                } else {
                    Collections.sort(sommetsRetenus);
                    boolean existedeja = false;
                    for (Choix c : sommetsRetenus) {
                        if (c.getP() == suivant) {
                            c.setInteret(Math.max(c.getInteret(), eval));
                            existedeja = true;
                        }
                    }
                    if (sommetsRetenus.get(0).getInteret() < eval && !existedeja) {

                        sommetsRetenus.set(0, new Choix(suivant, eval));
                    }
                }
                rang = profondeur - 1;
                if (rang > 0) {
                    parcoursProfondeur(g, suivant, sommetsVisites, sommetsRetenus, nbAmis, rang, eval, augmenterVitesse);
                }
            }
        }

    }

    /************************************************************************************************************
     *Description: permet de récupérer des amis sous forme d'un itérateur
     *@return un iterateur de personnes
     *************************************************************************************************************/
    public Iterator<Personne> recupererAmis() {
        return gr.recupererAmis(this).iterator();
    }

    /************************************************************************************************************
     *Description: permet de récupérer une personne sous forme de chaîne
     *@return retourne une chaîne
     *************************************************************************************************************/
    @Override
    public String toString() {
        return "id: " + id + ", nom: " + nom + ", prenom : " + prenom + ", sexe: " + sexe + ", interets: " + interets + ", parcours : " + sej;
    }

    /************************************************************************************************************
     *Description: permet d'ajouter une personne dans la liste d'amis en attente
     *@param p une personne
     *************************************************************************************************************/
    public void ajouterAmisEnAttente(Personne p) {
        amisEnAttente.add(p);
    }

    /************************************************************************************************************
     *Description: permet de supprimer une personne dans la liste d'amis en attente afin de la refuser
     *@param p une personne
     *@return un booleen de confirmation de suppression
     *************************************************************************************************************/
    public boolean refuserAmis(Personne p) {
        return amisEnAttente.remove(p);
    }

    /************************************************************************************************************
     *Description: permet de récupérer l'id d'une personne
     *@return retourne un entier
     *************************************************************************************************************/
    public int getId() {
        return id;
    }

    /************************************************************************************************************
     *Description: permet de récupérer le nom d'une personne
     *@return retourne une chaîne
     *************************************************************************************************************/
    public String getNom() {
        return nom;
    }

    /************************************************************************************************************
     *Description: permet de récupérer le prenom d'une personne
     *@return retourne une chaîne
     *************************************************************************************************************/
    public String getPrenom() {
        return prenom;
    }

    /************************************************************************************************************
     *Description: permet de récupérer le sexe d'une personne
     *@return retourne une chaîne
     *************************************************************************************************************/
    public String getSexe() {
        return sexe;
    }

    /************************************************************************************************************
     *Description: permet de récupérer le lieuNaiss d'une personne
     *@return retourne un lieu
     *************************************************************************************************************/
    public Lieu getLieuNaiss() {
        return lieuNaiss;
    }

    /************************************************************************************************************
     *Description: permet de récupérer le lieuRes d'une personne
     *@return retourne un lieu
     *************************************************************************************************************/
    public Lieu getLieuRes() {
        return lieuRes;
    }

    /************************************************************************************************************
     *Description: permet de récupérer l'ensemble amisEnAttente d'une personne
     *@return retourne un ensemble de personnes
     *************************************************************************************************************/
    public Set<Personne> getAmisEnAttente() {
        return amisEnAttente;
    }

    /************************************************************************************************************
     *Description: permet de récupérer l'ensemble interets d'une personne
     *@return retourne un ensemble de Centres d'Interet
     *************************************************************************************************************/
    public Set<CentreInteret> getInterets() {
        return interets;
    }

    /************************************************************************************************************
     *Description: permet de récupérer l'ensemble sej d'une personne
     *@return retourne un ensemble de sejour
     *************************************************************************************************************/
    public Set<Sejour> getSej() {
        return sej;
    }

    /************************************************************************************************************
     *Description: permet de modifier le lieu de naissance en le remplaçant par la valeur placée en paramètre
     *@param lieuNaiss un lieu
     *************************************************************************************************************/
    public void setLieuNaiss(Lieu lieuNaiss) {
        this.lieuNaiss = lieuNaiss;
    }

    /************************************************************************************************************
     *Description: permet de modifier le lieu de résidence en le remplaçant par la valeur placée en paramètre
     *@param lieuRes un lieu
     *************************************************************************************************************/
    public void setLieuRes(Lieu lieuRes) {
        this.lieuRes = lieuRes;
    }

    /************************************************************************************************************
     *Description: permet d'ajouter un centre d'interet à l'ensemble des interets d'une personne
     *@param c un CentreInteret
     *************************************************************************************************************/
    public void addCentreInteret(CentreInteret c) {
        interets.add(c);
    }

    /************************************************************************************************************
     *Description: permet d'ajouter un sejour à l'ensemble des sejours d'une personne
     *@param s un Sejour
     *************************************************************************************************************/
    public void addSejour(Sejour s) {
        sej.add(s);
    }

    /************************************************************************************************************
     *Description: permet d'ajouter un graphe à une personne
     *@param gr un GrapheValue
     *************************************************************************************************************/
    public void setGr(GrapheValue gr) {
        this.gr = gr;
    }
}
