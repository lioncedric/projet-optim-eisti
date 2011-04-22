package fr.eisti.OptimEisti;

/**
 * 
 */
/**
 * @author Administrator
 *
 */
import fr.eisti.OptimEisti.View.Compte.Accueil;
import fr.eisti.OptimEisti.View.Fenetre;

public class Main {

    public static Fenetre fenetrePrincipale;
    static Accueil accueil;

    /**
     * @param args
     */
    public static void main(String[] args) {
        accueil = new Accueil();
        accueil.setVisible(true);

    }

    public static Accueil getAccueil() {
        return accueil;
    }
    
    
}
