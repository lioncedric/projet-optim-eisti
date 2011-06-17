
import fr.eisti.LionHour.Connexion.DataManager;
import fr.eisti.LionHour.graphe.GrapheValue;
import fr.eisti.LionHour.Donnes.Personne;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/******************************************************************************
 *Description: classe qui permet de tester l'ensemble des autres classes
 * @author HOURCLATS Teddy, LION Cédric
 * @version 1.0
 ******************************************************************************/
public class Main {

    /************************************************************************************************************
     *Description: permet de créer et tester des objets à partir des autres classes du programme
     *@param args un tableau de chaînes
     *************************************************************************************************************/
    public static void main(String[] args) throws Exception {

        //on crée et on initialise un graphe
        DataManager.initOracleLocal("projet", "projet");
        GrapheValue gr = DataManager.load();
        //on affiche ce graphe
        System.out.println(gr);
        System.out.println("---------");
        //on affiche les personnes proposées comme amies à la personne Myriam
        for (Personne p : gr.getPersonneByPrenom("myriam").rechercheAmis(2, 5, false)) {
            System.out.println(p);
        }
        System.out.println("---------");
        //on affiche les personnes amies avec Myriam
        Iterator<Personne> it = gr.getPersonneByPrenom("myriam").recupererAmis();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        //on supprime Rémy de la liste d'amis de Myriam
        gr.getPersonneByPrenom("myriam").supprimerUnAmi(gr.getPersonneByPrenom("rémy"));
        System.out.println("---------");
        //on affiche les personnes amies avec Myriam
        it = gr.getPersonneByPrenom("myriam").recupererAmis();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        System.out.println("---------");
        //on affiche les personnes proposées comme amies à la personne Myriam
        for (Personne p : gr.getPersonneByPrenom("myriam").rechercheAmis(5, 5, false)) {
            System.out.println(p);
        }
        try {
            //on crée un lien d'amitié entre Myriam et Rémy
            gr.getPersonneByPrenom("myriam").evaluerAmitie(gr.getPersonneByPrenom("rémy"), 20);
        } catch (Exception ex) {
         
        }
        System.out.println("---------");
        //on affiche les personnes amies avec Myriam
        it = gr.getPersonneByPrenom("myriam").recupererAmis();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        System.out.println("---------");
        //on affiche les personnes proposées comme amies à Myriam
        for (Personne p : gr.getPersonneByPrenom("myriam").rechercheAmis(5, 5, false)) {
            System.out.println(p);
        }
        //on teste les différentes fonctionnalités de recherche de personnes dans un graphe
        System.out.println("---------");
        System.out.println(gr.rechercherParNomPrenom("lion", null));
        System.out.println("---------");
        System.out.println(gr.rechercheParCentreInteret("sport", "basket"));
        System.out.println("---------");
        System.out.println(gr.rechercheParParcours("eisti", "pau", 2010));

        //Mise à jour de la BDD
        // ModeleBDD.sychronize();

    }
}
