package fr.eisti.optimEisti_RaLiGaKl.view.problemes;

import fr.eisti.optimEisti_RaLiGaKl.controler.problemes.PanelRechercheProblemeListener;
import fr.eisti.optimEisti_RaLiGaKl.Main;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Classe permettant de créer le panel de profil de l'utilisateur
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 */
public class PanelRechercheProbleme extends JPanel {

    //déclaration des variables nécessaires
    private JTextField jtfRecherche;
    private Image loupe;

    /**
     * Constructeur permettant de créer le panel de recherche
     */
    public PanelRechercheProbleme() {
        initialiserVariables();
        recupererImage();
        traitement();
    }

    /**
     * Procédure permettant d'initialiser toutes les variables déclarées précédemment
     */
    public void initialiserVariables() {
        this.jtfRecherche = new JTextField("");
    }

    /**
     * Procédure permettant d'initialiser notre variables de type Image
     */
    public void recupererImage() {
        try {
            this.loupe = ImageIO.read(new File("images/loupe.png"));
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de l'image");
        }
    }

    /**
     * Procédure permettant de faire tous les traitements nécessaires
     */
    public void traitement() {
        this.setPreferredSize(new Dimension(100,25));
        this.setLayout(null);
        this.add(this.jtfRecherche);
        //on spécifie deux listeners à la zone de recherche
        this.jtfRecherche.addKeyListener(new PanelRechercheProblemeListener());
        this.jtfRecherche.addMouseListener(new PanelRechercheProblemeListener());
    }

    
    @Override
    public void paintComponent(Graphics g) {
        this.jtfRecherche.setBounds(32, 0, Main.fenetrePrincipale.getGauche().getWidth()-33, 25);
        //on dessine l'image sur le panel
        g.drawImage(loupe,5, 0, this);
    }

    /**
     * Fonction qui retourne le champs de texte qui permet la recherche
     * @return le champ de texte
     */
    public JTextField getJtfRecherche() {
        return jtfRecherche;
    }
}
