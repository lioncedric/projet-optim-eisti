
import fr.eisti.LionHour.Connexion.DataManager;
import fr.eisti.LionHour.graphe.GrapheValue;
import fr.eisti.LionHour.Donnes.Personne;
import java.util.Iterator;



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
        //on ajoute des liens d'amitié dans ce graphe
     //   gr.evaluerAmitie(gr.getPersonneById(0), gr.getPersonneById(1), 100);
     //   gr.evaluerAmitie(gr.getPersonneById(1), gr.getPersonneById(3), 70);
     //   gr.evaluerAmitie(gr.getPersonneById(1), gr.getPersonneById(2), 100);
     //   gr.evaluerAmitie(gr.getPersonneById(2), gr.getPersonneById(3), 66);
     //   gr.evaluerAmitie(gr.getPersonneById(1), gr.getPersonneById(4), 60);
           System.out.println("---------");
        //on affiche les personnes proposées comme amies avec une personne du graphe
        for (Personne p : gr.getPersonneByPrenom("myriam").rechercheAmis(2, 5,false)) {
            System.out.println(p);
        }
        System.out.println("---------");
         //on affiche les personnes amies avec une personne du graphe
         Iterator<Personne> it=gr.getPersonneByPrenom("myriam").recupererAmis();
         while(it.hasNext()){
              System.out.println(it.next());
        }
         gr.getPersonneByPrenom("myriam").supprimerUnAmi(null);
        // System.out.println(gr.rechercherParNomPrenom(null, "lion"));
        //   System.out.println(gr.rechercheParCentreInteret("basket", "sport"));
        // System.out.println(gr.rechercherParNomPrenom(null, "lion"));
        //  System.out.println(gr.rechercheParCentreInteret(null,"basket"));
        // System.out.println(gr.rechercheParParcours("essec", "pau",2010));



        // ModeleBDD.sychronize();

    }
}
