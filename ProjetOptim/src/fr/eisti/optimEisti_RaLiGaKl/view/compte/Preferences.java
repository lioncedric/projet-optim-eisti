package fr.eisti.optimEisti_RaLiGaKl.view.compte;

import fr.eisti.optimEisti_RaLiGaKl.Main;
import fr.eisti.optimEisti_RaLiGaKl.controler.compte.PreferencesListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Classe qui permet de gérer les couleurs d'a peu près tous les composants de la fenêtre, du moins, les plus essentiels
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 */
public class Preferences extends JDialog {

    private JPanel tout;                            //le panel global qui contient tout
    private JPanel gauche;                          //le panel de gauche, qui contient les textes
    private JPanel droite;                          //le panel de droite contient toutes les couleurs des composants
    private JLabel fondPanelProfil;                 //le jlabel dans lequel on va stocker la couleur du panel profil
    private JLabel couleurTextePanelProfil;         //le jlabel qui va contenir la couleur du texte
    private JLabel fondPanelProblemes1;             //le jlabel qui contient la premiere couleur du degrade
    private JLabel fondPanelProblemes2;             //le jlabel qui contient la deuxieme couleur du degrade
    private JLabel couleurTextePanelProblemes;      //le jlabel qui contient la couleur de texte
    private JLabel couleurFondSelectionPanelProblemes;//le jlabel qui contient la couleur de fond lors de la selection
    private JLabel couleurTexteSelectionPanelProblemes;//le jlabel qui contient la couleur de texte lors de la selection
    private JLabel fondPanelBoutons;                //le jlabel qui contient la couleur de fond du panl des boutons
    private JLabel fondFenetre1;                    //le jlabel qui contient la couleur de degrade 1 de la fenetre principale
    private JLabel fondFenetre2;                    //le jlabel qui contient la couleur de degrade 2 de la fenetre principale
    private JLabel couleurTexteFenetre;             //le jlabel qui contient la couleur de texte de la fenetre
    private JLabel couleurComposantsTransparents;   //le jlabel qui contient la couleur des composants transparents
    
    private JTextField[] tab;//un tableau de jtextfield qui va contenir tous

    /**
     * Constructeur permettant de créer le jdialog
     */
    public Preferences() {
        init();
        initialiserVariables();
        traitement();
        attribuerCouleurs();
    }

    /**
     * Initialise la fenêtre
     */
    public void init() {
        this.setTitle("Gestion de votre profil");
        this.setSize(450, 300);                 //on redimenssione la fenetre en cours
        this.setLocationRelativeTo(null);       //on centre la fenetre a l'ecran
        this.setResizable(false);               //on demande a ce que la fenetre ne puisse pas etre redimentionnee
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //permet de fermer correctement la fenetre
        this.setModal(true);                    //on desactive tout le reste
    }

    
    /**
     * Procedure qui permet d'initialiser les variables
     */
    public void initialiserVariables() {
        this.tout = new JPanel();
        this.gauche = new JPanel();
        this.droite = new JPanel();
        this.tout.setLayout(new BorderLayout());
        this.gauche.setLayout(new GridLayout(12, 1));
        this.droite.setLayout(new GridLayout(12, 1));

        this.fondPanelProfil = new JLabel("Couleur de fond du bloc 'Profil'");
        this.couleurTextePanelProfil = new JLabel("Couleur de texte du bloc 'Profil'");

        this.fondPanelProblemes1 = new JLabel("Couleur de dégradé 1 de la liste des problèmes");
        this.fondPanelProblemes2 = new JLabel("Couleur de dégradé 2 de la liste des problèmes");
        this.couleurTextePanelProblemes = new JLabel("Couleur de texte de la liste des problèmes");
        this.couleurFondSelectionPanelProblemes = new JLabel("Couleur de fond lors de la sélection d'un problème");
        this.couleurTexteSelectionPanelProblemes = new JLabel("Couleur du texte lors de la sélection d'un problème");

        this.fondPanelBoutons = new JLabel("Couleur de fond du bloc 'Actions'");

        this.fondFenetre1 = new JLabel("Couleur de dégradé 1 de la fenêtre principale");
        this.fondFenetre2 = new JLabel("Couleur de dégradé 2 de la fenêtre principale");
        this.couleurTexteFenetre = new JLabel("Couleur de texte de la fenêtre principale");
        this.couleurComposantsTransparents = new JLabel("Couleur des composants en transparent");


        tab=new JTextField[13];//declaration du tableau destine a contenir les couleurs cliquables de notre application
        for(int i=0;i<13;i++){
            tab[i]=new JTextField(5);
            tab[i].addMouseListener(new PreferencesListener(this));
            tab[i].setEditable(false);//on fait en sorte que le textfield soit non modifiable
        }
       
    }
    
    
    /**
     * Procedure qui permet d'atttribuer les couleurs de notre programme aux texfields de ce panel
     */
    public void attribuerCouleurs(){
        tab[0].setBackground(Main.fenetrePrincipale.getPanProfil().getCouleurFond());
        tab[1].setBackground(Main.fenetrePrincipale.getPanProfil().getCouleurTexte());
        tab[2].setBackground(Main.fenetrePrincipale.getGauche().getCouleur1());
        tab[3].setBackground(Main.fenetrePrincipale.getGauche().getCouleur2());
        tab[4].setBackground(Main.fenetrePrincipale.getGauche().getListePR().getCouleurTexte());
        tab[5].setBackground(Main.fenetrePrincipale.getGauche().getListePR().getCouleurFondSelection());
        tab[6].setBackground(Main.fenetrePrincipale.getGauche().getListePR().getCouleurTexteSelection());
        tab[7].setBackground(Main.fenetrePrincipale.getGauche().getCouleurFondPanelBoutons());
        tab[8].setBackground(Main.fenetrePrincipale.getCouleur1());
        tab[9].setBackground(Main.fenetrePrincipale.getCouleur2());
        tab[10].setBackground(Main.fenetrePrincipale.getCouleurTexte());
        tab[11].setBackground(Main.fenetrePrincipale.getCouleurComposantsTransparents());
    }

    /**
     * Procedure qui permet de faire le traitement nécessaire pour stcker dans nos panels de droite et de gauche
     */
    public void traitement() {
        this.gauche.add(fondPanelProfil);
        this.droite.add(tab[0]);

        this.gauche.add(couleurTextePanelProfil);
        this.droite.add(tab[1]);

        this.gauche.add(fondPanelProblemes1);
        this.droite.add(tab[2]);

        this.gauche.add(fondPanelProblemes2);
        this.droite.add(tab[3]);

        this.gauche.add(couleurTextePanelProblemes);
        this.droite.add(tab[4]);

        this.gauche.add(couleurFondSelectionPanelProblemes);
        this.droite.add(tab[5]);

        this.gauche.add(couleurTexteSelectionPanelProblemes);
        this.droite.add(tab[6]);

        this.gauche.add(fondPanelBoutons);
        this.droite.add(tab[7]);

        this.gauche.add(fondFenetre1);
        this.droite.add(tab[8]);

        this.gauche.add(fondFenetre2);
        this.droite.add(tab[9]);

        this.gauche.add(couleurTexteFenetre);
        this.droite.add(tab[10]);

        this.gauche.add(couleurComposantsTransparents);
        this.droite.add(tab[11]);

        this.tout.add(this.gauche,BorderLayout.CENTER);
        this.tout.add(this.droite,BorderLayout.EAST);
        this.setContentPane(tout);
    }

    
    public JTextField[] getTab() {
        return tab;
    }
    
}