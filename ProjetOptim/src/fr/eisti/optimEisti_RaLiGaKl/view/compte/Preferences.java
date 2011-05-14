/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eisti.optimEisti_RaLiGaKl.view.compte;

import fr.eisti.optimEisti_RaLiGaKl.controler.compte.PreferencesListener;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
public class Preferences extends JDialog {

    private JPanel tout;
    private JPanel gauche;
    private JPanel droite;
    private JLabel fondPanelProfil;
    private JLabel couleurTextePanelProfil;
    private JLabel fondPanelProblemes1;
    private JLabel fondPanelProblemes2;
    private JLabel couleurTextePanelProblemes;
    private JLabel couleurFondSelectionPanelProblemes;
    private JLabel couleurTexteSelectionPanelProblemes;
    private JLabel fondPanelBoutons;
    private JLabel couleurTextePanelBoutons;
    private JLabel fondFenetre1;
    private JLabel fondFenetre2;
    private JLabel couleurTexteFenetre;
    private JLabel opaciteComposantsFenetre;
    private JButton b1;
    private JButton b2;
    private JButton b3;
    private JButton b4;
    private JButton b5;
    private JButton b6;
    private JButton b7;
    private JButton b8;
    private JButton b9;
    private JButton b10;
    private JButton b11;
    private JButton b12;
    private JButton b13;

    /**
     * Constructeur permettant de créer le jdialog
     */
    public Preferences() {
        init();
        initialiserVariables();
        ajoutListeners();
        traitement();
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

    public void initialiserVariables() {
        this.tout = new JPanel();
        this.gauche = new JPanel();
        this.droite = new JPanel();
        this.tout.setLayout(new BorderLayout());
        this.gauche.setLayout(new GridLayout(13, 1));
        this.droite.setLayout(new GridLayout(13, 1));

        this.fondPanelProfil = new JLabel("Couleur de fond du bloc 'Profil'");
        this.couleurTextePanelProfil = new JLabel("Couleur de texte du bloc 'Profil'");

        this.fondPanelProblemes1 = new JLabel("Couleur de dégradé 1 de la liste des problèmes");
        this.fondPanelProblemes2 = new JLabel("Couleur de dégradé 2 de la liste des problèmes");
        this.couleurTextePanelProblemes = new JLabel("Couleur de texte de la liste des problèmes");
        this.couleurFondSelectionPanelProblemes = new JLabel("Couleur de fond lors de la sélection d'un problème");
        this.couleurTexteSelectionPanelProblemes = new JLabel("Couleur du texte lors de la sélection d'un problème");

        this.fondPanelBoutons = new JLabel("Couleur de fond du bloc 'Actions'");
        this.couleurTextePanelBoutons = new JLabel("Couleur de texte du bloc 'Actions'");

        this.fondFenetre1 = new JLabel("Couleur de dégradé 1 de la fenêtre principale");
        this.fondFenetre2 = new JLabel("Couleur de dégradé 2 de la fenêtre principale");
        this.couleurTexteFenetre = new JLabel("Couleur de texte de la fenêtre principale");
        this.opaciteComposantsFenetre = new JLabel("Opacité des composants de la fenêtre principale");


        this.b1 = new JButton(".");
        this.b2 = new JButton(".");
        this.b3 = new JButton(".");
        this.b4 = new JButton(".");
        this.b5 = new JButton(".");
        this.b6 = new JButton(".");
        this.b7 = new JButton(".");
        this.b8 = new JButton(".");
        this.b9 = new JButton(".");
        this.b10 = new JButton(".");
        this.b11 = new JButton(".");
        this.b12 = new JButton(".");
        this.b13 = new JButton(".");
    }

    public void ajoutListeners(){
        this.b1.addActionListener(new PreferencesListener(this));
        this.b2.addActionListener(new PreferencesListener(this));
        this.b3.addActionListener(new PreferencesListener(this));
        this.b4.addActionListener(new PreferencesListener(this));
        this.b5.addActionListener(new PreferencesListener(this));
        this.b6.addActionListener(new PreferencesListener(this));
        this.b7.addActionListener(new PreferencesListener(this));
        this.b8.addActionListener(new PreferencesListener(this));
        this.b9.addActionListener(new PreferencesListener(this));
        this.b10.addActionListener(new PreferencesListener(this));
        this.b11.addActionListener(new PreferencesListener(this));
        this.b12.addActionListener(new PreferencesListener(this));
        this.b13.addActionListener(new PreferencesListener(this));
    }
    /**
     * Ajoute le panel sur la fenêtre
     */
    public void traitement() {
        this.gauche.add(fondPanelProfil);
        this.droite.add(b1);

        this.gauche.add(couleurTextePanelProfil);
        this.droite.add(b2);

        this.gauche.add(fondPanelProblemes1);
        this.droite.add(b3);

        this.gauche.add(fondPanelProblemes2);
        this.droite.add(b4);

        this.gauche.add(couleurTextePanelProblemes);
        this.droite.add(b5);

        this.gauche.add(couleurFondSelectionPanelProblemes);
        this.droite.add(b6);

        this.gauche.add(couleurTexteSelectionPanelProblemes);
        this.droite.add(b7);

        this.gauche.add(fondPanelBoutons);
        this.droite.add(b8);

        this.gauche.add(couleurTextePanelBoutons);
        this.droite.add(b9);

        this.gauche.add(fondFenetre1);
        this.droite.add(b10);

        this.gauche.add(fondFenetre2);
        this.droite.add(b11);

        this.gauche.add(couleurTexteFenetre);
        this.droite.add(b12);

        this.gauche.add(opaciteComposantsFenetre);
        this.droite.add(b13);

        this.tout.add(this.gauche,BorderLayout.CENTER);
        this.tout.add(this.droite,BorderLayout.EAST);
        this.setContentPane(tout);
    }
}