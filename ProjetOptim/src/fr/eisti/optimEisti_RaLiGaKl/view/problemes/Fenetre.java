/**
 * 
 */
package fr.eisti.optimEisti_RaLiGaKl.view.problemes;

import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import fr.eisti.optimEisti_RaLiGaKl.controler.problemes.FenetreListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

/**
 * @author Administrator
 *
 */
public class Fenetre extends JFrame {

    //Declaration des variables pour le menubar
    private static final long serialVersionUID = 1L;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fichier = new JMenu("Fichier");
    private JMenu edition = new JMenu("Edition");
    private JMenu affichage = new JMenu("Affichage");
    private JMenu aide = new JMenu("Aide");
    private JMenu importT = new JMenu("Importer");
    private JMenuItem sauvegarder = new JMenuItem("Sauvegarder");
    private JMenuItem recharger = new JMenuItem("Recharger");
    private JMenuItem quitter = new JMenuItem("Quitter");
    private JMenuItem deconnexion = new JMenuItem("Se déconnecter");
    private JMenuItem aideItem = new JMenuItem("Aide");
    private JMenuItem aPropos = new JMenuItem("a Propos");
    private JMenuItem importXml = new JMenuItem("Importer des problèmes XML");
    private JMenuItem preferences = new JMenuItem("Preferences");
    private JMenuItem rechercher = new JMenuItem("Rechercher");
    private JMenuItem pleinEcran = new JMenuItem("Plein écran");
    private JMenuItem petitEcran = new JMenuItem("Mettre en petite taille");
    private JMenuItem profil = new JMenuItem("Gérer votre profil");
    private JMenuItem affResHtml = new JMenuItem("Générer une page HTML");
    //declaration des variables pour le panel de gauche
    private JSplitPane splitPane;
    private PanelProblemesUtilisateur gauche;
    private JTabbedPane droite;
    private PanelProfil panProfil;
    private JPanel panGauche;

    /**
     * Constructeur
     */
    public Fenetre() {
        initFenetre();

        traitementMenuItem();

    }

    private void initFenetre() {
        this.setTitle("Fenêtre de saisie du problème");
        this.setSize(960, 620);          //on redimenssione la fenetre en cours
        this.setMinimumSize(new Dimension(775, 500));
        this.setLocationRelativeTo(null);//on centre la fenetre a l'ecran
        //this.setResizable(false);        //on demande a ce que la fenetre ne puisse pas etre redimentionnee
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //permet de fermer correctement la fenetre
        this.addComponentListener(new FenetreListener(this));
        this.panGauche = new JPanel();
        this.panGauche.setLayout(new BorderLayout());
        this.panProfil = new PanelProfil();
        gauche = new PanelProblemesUtilisateur();
        this.panGauche.add(this.panProfil, BorderLayout.NORTH);
        this.panGauche.add(this.gauche, BorderLayout.CENTER);
        droite = new JTabbedPane();
      

        droite.setBackground(Color.WHITE);
        UIManager.put("TabbedPane.selected", new Color(209, 238, 238));
        SwingUtilities.updateComponentTreeUI(droite);
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panGauche, droite);
        gauche.setMinimumSize(new Dimension(180, this.getHeight()));
        droite.setMinimumSize(new Dimension(700, this.getHeight()));
        splitPane.setOneTouchExpandable(false);
        splitPane.setDividerSize(4);//definit la taille de la zone de separation entre les deux composants
        splitPane.setContinuousLayout(true);
         splitPane.setBackground(Color.DARK_GRAY);
        setContentPane(splitPane);
    }

    public void appliquerChangementSplitPane() {
        if (this.getWidth() < 960) {
            droite.setMinimumSize(new Dimension(this.getWidth() * 7 / 10, this.getHeight()));
        } else if (this.getWidth() < 1260) {
            droite.setMinimumSize(new Dimension(this.getWidth() * 15 / 20, this.getHeight()));
        } else {
            droite.setMinimumSize(new Dimension(this.getWidth() * 8 / 10, this.getHeight()));
        }
    }

    /**
     * Methode d'initialisation de la fenetre
     */
    private void traitementMenuItem() {
        //les raccourcis clavier deja pris : ctrl+s, ctrl+d, ctrl+q, ctrl+f, ctrl+l, ctrl+r, ctrl+h
        //on initialise nos menus
        fichier.setMnemonic('F');//faire alt+f pour voir le menu derouler
        edition.setMnemonic('E');
        affichage.setMnemonic('A');
        aide.setMnemonic('H');

        sauvegarder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));//ctrl + s
        this.fichier.add(sauvegarder);
        this.fichier.add(recharger);

        this.fichier.addSeparator();
        this.fichier.add(importT);

        this.fichier.addSeparator();
        this.fichier.add(deconnexion);
        this.deconnexion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_MASK));//ctrl + d
        this.fichier.add(quitter);
        this.quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));//ctrl + q

        this.edition.add(rechercher);
        this.rechercher.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_MASK));//ctrl + f
        this.edition.add(profil);
        this.edition.add(preferences);

        this.affichage.add(pleinEcran);
        this.pleinEcran.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_MASK));//ctrl + l
        this.affichage.add(petitEcran);
        this.petitEcran.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_MASK));//ctrl + r
        this.affichage.add(affResHtml);
        this.affResHtml.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_MASK));//ctrl + h

        this.aide.add(aideItem);
        this.aide.addSeparator();
        this.aide.add(aPropos);
        this.importT.add(importXml);


        this.menuBar.add(fichier);
        this.menuBar.add(edition);
        this.menuBar.add(affichage);
        this.menuBar.add(aide);

        this.setJMenuBar(menuBar);

        importXml.addActionListener(new FenetreListener(this));
        sauvegarder.addActionListener(new FenetreListener(this));
        recharger.addActionListener(new FenetreListener(this));
        deconnexion.addActionListener(new FenetreListener(this));
        quitter.addActionListener(new FenetreListener(this));
        pleinEcran.addActionListener(new FenetreListener(this));
        petitEcran.addActionListener(new FenetreListener(this));
        affResHtml.addActionListener(new FenetreListener(this));
    }

    public JTabbedPane getDroite() {
        return droite;
    }

    public void setDroite(JTabbedPane droite) {
        this.droite = droite;
    }

    public PanelProblemesUtilisateur getGauche() {
        return gauche;
    }

    public void setGauche(PanelProblemesUtilisateur gauche) {
        this.gauche = gauche;
    }

    public JMenuItem getaPropos() {
        return aPropos;
    }

    public void setaPropos(JMenuItem aPropos) {
        this.aPropos = aPropos;
    }

    public JMenu getAide() {
        return aide;
    }

    public void setAide(JMenu aide) {
        this.aide = aide;
    }

    public JMenuItem getAideItem() {
        return aideItem;
    }

    public void setAideItem(JMenuItem aideItem) {
        this.aideItem = aideItem;
    }

    public JMenuItem getDeconnexion() {
        return deconnexion;
    }

    public void setDeconnexion(JMenuItem deconnexion) {
        this.deconnexion = deconnexion;
    }

    public JMenu getFichier() {
        return fichier;
    }

    public void setFichier(JMenu fichier) {
        this.fichier = fichier;
    }

    public JMenu getImportT() {
        return importT;
    }

    public void setImportT(JMenu importT) {
        this.importT = importT;
    }

    public JMenuItem getImportXml() {
        return importXml;
    }

    public void setImportXml(JMenuItem importXml) {
        this.importXml = importXml;
    }

    public void setMenuBar(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public JMenuItem getQuitter() {
        return quitter;
    }

    public void setQuitter(JMenuItem quitter) {
        this.quitter = quitter;
    }

    public JMenuItem getRecharger() {
        return recharger;
    }

    public void setRecharger(JMenuItem recharger) {
        this.recharger = recharger;
    }

    public JMenuItem getSauvegarder() {
        return sauvegarder;
    }

    public void setSauvegarder(JMenuItem sauvegarder) {
        this.sauvegarder = sauvegarder;
    }

    public JSplitPane getSplitPane() {
        return splitPane;
    }

    public void setSplitPane(JSplitPane splitPane) {
        this.splitPane = splitPane;
    }

    public JMenuItem getPetitEcran() {
        return petitEcran;
    }

    public JMenuItem getPleinEcran() {
        return pleinEcran;
    }

    public PanelProfil getPanProfil() {
        return panProfil;
    }

    public JMenuItem getAffResHtml() {
        return affResHtml;
    }

}
