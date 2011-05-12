package fr.eisti.optimEisti_RaLiGaKl;

/**
 *
 * @author Administrator
 */
import fr.eisti.optimEisti_RaLiGaKl.model.BDDUtilisateur;
import fr.eisti.optimEisti_RaLiGaKl.model.BddProbleme;
import fr.eisti.optimEisti_RaLiGaKl.model.Probleme;
import fr.eisti.optimEisti_RaLiGaKl.model.Simplexe;
import java.io.IOException;

/**
 * Classe permettant de lancer le programme en appelant la page d'accueil
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 */
public class Main21 {

    private static double[][] matrice;

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {
        BDDUtilisateur.existeCompte("meri", "meri");
        BddProbleme.load(BDDUtilisateur.getNomUtilisateur(), BDDUtilisateur.getImage());
        Probleme p=BddProbleme.getProbleme(9);
       // BddProbleme.exporterScilab(p,"C:\\Users\\Administrator\\Desktop\\ok.sci");
     //   p.formaliserProbleme();
       //  Simplexe solution = new Simplexe(p.formaliserProbleme(), p.getCoeffVariables().size());
    }
}
