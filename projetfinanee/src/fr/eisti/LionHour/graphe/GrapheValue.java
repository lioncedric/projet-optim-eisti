package fr.eisti.LionHour.graphe;

import fr.eisti.LionHour.Donnes.CentreInteret;
import fr.eisti.LionHour.Connexion.DataManager;
import fr.eisti.LionHour.Donnes.Personne;
import fr.eisti.LionHour.Donnes.Sejour;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/******************************************************************************
 *Description: classe qui permet de définir un graphe d'amis (les sommets sont les personnes
 *             et les arêtes sont les liens d'amitié entre les personnes
 * @author HOURCLATS Teddy, LION Cédric
 * @version 1.0
 ******************************************************************************/
public class GrapheValue {

    //variables de la classe
    private List<Personne> sommets;
    private Set<AreteValuee> aretes;

    /************************************************************************************************************
     *Description: permet de créer un graphe à partir d'une liste de personnes et d'un ensemble d'AreteValuee
     *@param sommets une liste de personnes qui représente les sommets du graphe
     *@param aretes un ensemble d'AreteValuee qui représente les arêtes du graphe
     *************************************************************************************************************/
    public GrapheValue(List<Personne> sommets, Set<AreteValuee> aretes) {
        this.sommets = sommets;
        this.aretes = aretes;
    }

    /************************************************************************************************************
     *Description: permet de récupérer l'ensemble des sommets d'un graphe sous forme de chaine
     *@return retourne une chaîne
     *************************************************************************************************************/
    @Override
    public String toString() {
        String str = "";
        for (Personne p : sommets) {
            str = str + p.toString() + "\n";
        }
        return str;
    }

    /************************************************************************************************************
     *Description: permet de récupérer la liste de toutes les personnes amies de celle placée en paramètre
     *@param p une personne
     *@return retourne une liste de personnes
     *************************************************************************************************************/
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
            if (ar.getP1()==p) {
                //on ajoute la personne p2 de l'arête dans la liste d'amis
                res.add(ar.getP2());
            }
        }
        //on retourne la liste d'amis
        return res;
    }

    /************************************************************************************************************
     *Description: permet de supprimer un ami p2 d'une personne p1
     *@param p1 une personne
     *@param p2 une personne
     *@return retourne un booleen qui est vrai si la suppression a pu être effectuée
     *************************************************************************************************************/
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
                DataManager.delete(p1.getId(), p2.getId());
            }
        }
        //on supprime le lien d'amitié entre p1 et p2
        //on retourne l'état de la suppression de ce lien d'amitié
        return aretes.remove(arARemove);
    }

    /************************************************************************************************************
     *Description: permet de récupérer la valeur d'amitié entreune personne p1 et son ami p2
     *@param p1 une personne
     *@param p2 une personne
     *@return retourne un entier (égal à 0 si aucun lien d'amitié n'existe)
     *************************************************************************************************************/
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

    /************************************************************************************************************
     *Description: permet d'ajouter une valeur d'amitié entre une personne p1 et son ami p2 si elle existe
     *             ou de créer un lien d'amitié avec cette valeur s'il n'existe pas
     *@param p1 une personne
     *@param p2 une personne
     *@param valeur en entier
     *@return retourne un booleen qui est vrai si l'ajout ou la création a pu être effectué
     *************************************************************************************************************/
    public boolean evaluerAmitie(Personne p1, Personne p2, int valeur) throws Exception {
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
                DataManager.update(p1.getId(), p2.getId(), valeur);
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

    /************************************************************************************************************
     *Description: permet d'ajouter un ami p2 à la liste d'amis de p1 sans oublier la valeur d'amitié
     *@param p1 une personne
     *@param p2 une personne
     *@param valeur un entier
     *************************************************************************************************************/
    private void ajouterAmis(Personne p1, Personne p2, int valeur) throws Exception {
        //on ajoute une nouvelle arête, composée des éléments placés en paramètres, à l'ensemble des liens d'amitié
        aretes.add(new AreteValuee(p1, p2, valeur));
        //l'action de création est mémorisée pour la synchronisation avec la BDD
        DataManager.create(p1.getId(), p2.getId(), valeur);
    }

    /************************************************************************************************************
     *Description: permet de récupérer une personne dans la liste des sommets par l'intermédiaire de sa position
     *@param i un entier
     *@return retourne une personne
     *************************************************************************************************************/
    public Personne getPersonneByPosition(int i) {
        return sommets.get(i);
    }

    /************************************************************************************************************
     *Description: permet de récupérer une personne dans la liste des sommets par l'intermédiaire de son indice
     *@param i un entier
     *@return retourne une personne
     *************************************************************************************************************/
    public Personne getPersonneById(int i) {
        for (Personne p : this.sommets) {
            if (p.getId() == i) {
                return p;
            }
        }
        return null;
    }

    /************************************************************************************************************
     *Description: permet de récupérer la premiere personne dans la liste des sommets par l'intermédiaire de son nom
     *@param nom un entier
     *@return retourne une personne
     *************************************************************************************************************/
    public Personne getPersonneByNom(String nom) {
        for (Personne p : this.sommets) {
            if (p.getNom().toLowerCase().equals(nom.toLowerCase())) {
                return p;
            }
        }
        return null;
    }
      /************************************************************************************************************
     *Description: permet de récupérer la premiere personne dans la liste des sommets par l'intermédiaire de son prenom
     *@param prenom un entier
     *@return retourne une personne
     *************************************************************************************************************/
    public Personne getPersonneByPrenom(String prenom) {
        for (Personne p : this.sommets) {
            if (p.getPrenom().toLowerCase().equals(prenom.toLowerCase())) {
                return p;
            }
        }
        return null;
    }

    /************************************************************************************************************
     *Description: permet d'ajouter une personne p2 à la liste de proposition d'amis de p1
     *@param p1 une personne
     *@param p2 une personne
     *************************************************************************************************************/
    public void proposerAmis(Personne p1, Personne p2) {
        //on ajoute p2 à l'ensemble des personnes étant en attente d'une réponse de la part de p1
        p1.ajouterAmisEnAttente(p2);
    }

    /************************************************************************************************************
     *Description: permet de récupérer une liste de personnes dont le nom et/ou le prenom correspondent aux paramètres
     *@param nom une chaine
     *@param prenom une chaine
     *@return retourne une liste de personnes
     *************************************************************************************************************/
    public List<Personne> rechercherParNomPrenom(String nom, String prenom) {
        //on crée et on initialise la liste à retourner
        List<Personne> trouves;
        trouves = new LinkedList<Personne>();
        //si les deux paramètres sont non null
        if (nom != null && prenom != null) {
            //on parcourt l'ensemble des personnes du graphe
            for (Personne p : sommets) {
                //si le nom et le prenom de la personne correspondent aux paramètres
                if (p.getNom().compareToIgnoreCase(nom) == 0 && p.getPrenom().compareToIgnoreCase(prenom) == 0) {
                    //on ajoute cette personne à la liste à retourner
                    trouves.add(p);
                }
            }
        } //si le nom placé en paramètre est non null
        else if (nom != null) {
            //on parcourt l'ensemble des personnes du graphe
            for (Personne p : sommets) {
                //si le nom de la personne correspond au paramètre
                if (p.getNom().compareToIgnoreCase(nom) == 0) {
                    //on ajoute cette personne à la liste à retourner
                    trouves.add(p);
                }
            }
        } //si le prenom placé en paramètre est non null
        else if (prenom != null) {
            //on parcourt l'ensemble des personnes du graphe
            for (Personne p : sommets) {
                //si le prenom de la personne correspond au paramètre
                if (p.getPrenom().compareToIgnoreCase(prenom) == 0) {
                    //on ajoute cette personne à la liste à retourner
                    trouves.add(p);
                }
            }
        }
        //on retourne la liste des personnes trouvées
        return trouves;
    }

    /************************************************************************************************************
     *Description: permet de récupérer une liste de personnes dont le nom d'un des
     *             etablissements du parcours correspond au paramètre nomE,
     *             dont l'annee placée en parametre est comprise dans ce parcours
     *             et/ou la ville de l'etablissement est celle placée en paramètre
     *@param nomE une chaine
     *@param ville une chaine
     *@param annee un entier
     *@return retourne une liste de personnes
     *************************************************************************************************************/
    public List<Personne> rechercheParParcours(String nomE, String ville, int annee) {
        //on crée et on initialise la liste à retourner
        List<Personne> trouves;
        trouves = new LinkedList<Personne>();
        //si le nom de l'établissement est non null
        if (nomE != null) {
            //on parcourt l'ensemble des personnes du graphe
            for (Personne p : sommets) {
                //on parcours l'ensemble des parcours de la personne en cours d'évaluation
                for (Sejour parcours : p.getSej()) {
                    //si la date placée en paramètre est comprise entre la date de début et de fin du parcours
                    if (Integer.parseInt(parcours.getDateDebut().toString().substring(0, 4)) <= annee && annee <= Integer.parseInt(parcours.getDateFin().toString().substring(0, 4)) && parcours.getEtab().getNom().compareToIgnoreCase(nomE) == 0) {
                        //si la ville placée en paramètre est non null
                        if (ville != null) {
                            //si la ville placée en paramètre correspond avec celle du lieu de l'établissement du parcours de la personne
                            if (parcours.getEtab().getLieu().getVille().compareToIgnoreCase(ville) == 0) {
                                //on ajoute cette personne à la liste à retourner
                                trouves.add(p);
                            }
                        } //si la ville placée en paramètre vaut null
                        else {
                            //on ajoute cette personne à la liste à retourner
                            trouves.add(p);
                        }
                    }
                }
            }
        }
        //on retourne la liste des personnes trouvées
        return trouves;
    }

    /************************************************************************************************************
     *Description: permet de récupérer une liste de personnes dont un des centres d'intérêt correspond à celui définit par les paramètres
     *@param categorie une chaine
     *@param libelle une chaine
     *@return retourne une liste de personnes
     *************************************************************************************************************/
    public List<Personne> rechercheParCentreInteret(String categorie, String libelle) {
        //on crée et on initialise la liste à retourner
        List<Personne> trouves;
        trouves = new LinkedList<Personne>();
        //si les deux paramètres sont non null
        if (categorie != null && libelle != null) {
            //on parcourt l'ensemble des personnes du graphe
            for (Personne p : sommets) {
                //on parcourt tous les centres d'intérêt de la personne en cours d'évaluation
                for (CentreInteret ci : p.getInterets()) {
                    //si les deux paramètres correspondent à un de ces centres d'intérêt
                    if (ci.getCategorie().compareToIgnoreCase(categorie) == 0 && ci.getLibelle().compareToIgnoreCase(libelle) == 0) {
                        //on ajoute cette personne à la liste à retourner
                        trouves.add(p);
                    }
                }
            }
        } //si la categorie est non null
        else if (categorie != null) {
            //on parcourt l'ensemble des personnes du graphe
            for (Personne p : sommets) {
                //on parcourt tous les centres d'intérêt de la personne en cours d'évaluation
                for (CentreInteret ci : p.getInterets()) {
                    //si la categorie correspond à celle du centre d'intérêt de la personne
                    if (ci.getCategorie().compareToIgnoreCase(categorie) == 0) {
                        //on ajoute cette personne à la liste à retourner
                        trouves.add(p);
                    }
                }
            }
        } //si le libellé  est non null
        else if (libelle != null) {
            //on parcourt l'ensemble des personnes du graphe
            for (Personne p : sommets) {
                //on parcourt tous les centres d'intérêt de la personne en cours d'évaluation
                for (CentreInteret ci : p.getInterets()) {
                    //si le libelle correspond à celui du centre d'intérêt de la personne
                    if (ci.getLibelle().compareToIgnoreCase(libelle) == 0) {
                        //on ajoute cette personne à la liste à retourner
                        trouves.add(p);
                    }
                }
            }
        }
        //on retourne la liste des personnes trouvées
        return trouves;
    }
}
