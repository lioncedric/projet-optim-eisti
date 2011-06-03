/**
 * 
 */
package fr.eisti.optimEisti_RaLiGaKl.view.problemes;

import fr.eisti.optimEisti_RaLiGaKl.Main;
import fr.eisti.optimEisti_RaLiGaKl.controler.compte.PanelProfilListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import fr.eisti.optimEisti_RaLiGaKl.controler.problemes.FenetreListener;
import fr.eisti.optimEisti_RaLiGaKl.view.compte.JPanelFondNormal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Classe relative à un problème
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
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
    private JMenuItem recharger = new JMenuItem("Recharger");
    private JMenuItem quitter = new JMenuItem("Quitter");
    private JMenuItem deconnexion = new JMenuItem("Se déconnecter");
    private JMenuItem supprimerCompte = new JMenuItem("Supprimer mon compte");
    private JMenuItem aideItem = new JMenuItem("Aide");
    private JMenuItem importXml = new JMenuItem("Importer des problèmes XML");
    private JMenuItem preferences = new JMenuItem("Preferences");
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
    //Les couleurs pour le dégradé du panel de droite(que l'on ne peut pas mettre 
    //dans le panel de droite puisqu'il n'est pas récupérable depuis la classe Fenetre)
    private Color couleur1;
    private Color couleur2;
    private Color couleurTexte;
    private Color couleurFondComposants;
    private Color couleurComposantsTransparents;
    private int coeffTransparence;

    /**
     * Constructeur
     */
    public Fenetre() {
        initFenetre();
        traitementMenuItem();

    }

    /**
     * Fonction qui permet d'initialiser la fenêtre
     */
    private void initFenetre() {
        this.setTitle("Fenêtre de saisie du problème");
        this.setSize(960, 620);    //on redimenssione la fenetre en cours
        this.setMinimumSize(new Dimension(900, 600));
        this.setLocationRelativeTo(null);//on centre la fenetre a l'ecran
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //permet de fermer correctement la fenetre
        this.addComponentListener(new FenetreListener(this));//on ajoute un ecouteur à la fenetre
        this.panGauche = new JPanel();
        this.panGauche.setLayout(new BorderLayout());
        this.panProfil = new PanelProfil();//on creer un panel qui gere le profil de l'utilisateur dans la fenetre


        gauche = new PanelProblemesUtilisateur();//on creer un panel qui gere tous les problèmes de l'utilisateur logger
        this.panGauche.add(this.panProfil, BorderLayout.NORTH);
        this.panGauche.add(this.gauche, BorderLayout.CENTER);
        droite = new JTabbedPane();

        droite.setBackground(Color.WHITE);
        //on change la couleur du tabbedPane
        UIManager.put("TabbedPane.selected", new Color(209, 238, 238));
        SwingUtilities.updateComponentTreeUI(droite);
        //on divise la fenetre en deux parties, la gestion du problème et celle de l'utilisateur (profil+probleme precedemment cree)
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panGauche, droite);
        gauche.setMinimumSize(new Dimension(180, this.getHeight()));
        droite.setMinimumSize(new Dimension(700, this.getHeight()));
        splitPane.setOneTouchExpandable(false);
        splitPane.setDividerSize(4);//definit la taille de la zone de separation entre les deux composants
        splitPane.setContinuousLayout(true);
        splitPane.setBackground(Color.lightGray);
        setContentPane(splitPane);

        //on définie quelques couleur pour la fenêtre
        this.couleur1 = Color.WHITE;
        this.couleur2 = new Color(209, 238, 238);
        this.couleurComposantsTransparents = Color.GRAY;
        this.coeffTransparence = 50;
        this.couleurTexte = Color.BLACK;

    }

    /**
     * Fonction qui gère la division de la fenêtreen fonction de la taille de celle-ci
     */
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

        this.fichier.add(recharger);

        this.fichier.addSeparator();
        this.fichier.add(importT);

        this.fichier.addSeparator();
        this.fichier.add(deconnexion);
        this.deconnexion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_MASK));//ctrl + d
        this.fichier.add(supprimerCompte);
        this.fichier.add(quitter);
        this.quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));//ctrl + q


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

        this.importT.add(importXml);


        this.menuBar.add(fichier);
        this.menuBar.add(edition);
        this.menuBar.add(affichage);
        this.menuBar.add(aide);

        this.setJMenuBar(menuBar);
        //on creer tous les listeners nécessaire pour nos menus
        profil.addActionListener(new PanelProfilListener());
        importXml.addActionListener(new FenetreListener(this));
        recharger.addActionListener(new FenetreListener(this));
        deconnexion.addActionListener(new FenetreListener(this));
        supprimerCompte.addActionListener(new FenetreListener(this));
        quitter.addActionListener(new FenetreListener(this));
        pleinEcran.addActionListener(new FenetreListener(this));
        petitEcran.addActionListener(new FenetreListener(this));
        affResHtml.addActionListener(new FenetreListener(this));
        preferences.addActionListener(new FenetreListener(this));
        aideItem.addActionListener(new FenetreListener(this));
    }

    //accesseurs
    public JMenuItem getSupprimerCompte() {
        return supprimerCompte;
    }

    public void setSupprimerCompte(JMenuItem supprimerCompte) {
        this.supprimerCompte = supprimerCompte;
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

    public JMenuItem getPreferences() {
        return preferences;
    }

    public Color getCouleur1() {
        return couleur1;
    }

    public Color getCouleur2() {
        return couleur2;
    }

    public void setCouleur1(Color couleur1) {
        this.couleur1 = couleur1;
    }

    public void setCouleur2(Color couleur2) {
        this.couleur2 = couleur2;
    }

    public void setCouleurFondComposants(Color couleurFondComposants) {
        this.couleurFondComposants = couleurFondComposants;
    }

    public void setCouleurTexte(Color couleurTexte) {
        this.couleurTexte = couleurTexte;
        int i = 0;
        while (i < this.droite.getComponents().length) {
            if (this.droite.getComponents()[i] instanceof PanelProbleme) {
                ((PanelProbleme) this.droite.getComponents()[i]).mettreCouleurTexte(couleurTexte);
            }
            i++;
        }
    }

    public Color getCouleurFondComposants() {
        return couleurFondComposants;
    }

    public Color getCouleurTexte() {
        return couleurTexte;
    }

    public void setCouleurComposantsTransparents(Color couleurComposantsTransparents) {
        this.couleurComposantsTransparents = couleurComposantsTransparents;
        int i = 0;
        while (i < this.droite.getComponents().length) {
            if (this.droite.getComponents()[i] instanceof PanelProbleme) {
                ((PanelProbleme) this.droite.getComponents()[i]).changerFondComposants(couleurComposantsTransparents);
            }
            i++;
        }
    }

    public Color getCouleurComposantsTransparents() {
        return couleurComposantsTransparents;
    }

    public int getCoeffTransparence() {
        return coeffTransparence;
    }
}
