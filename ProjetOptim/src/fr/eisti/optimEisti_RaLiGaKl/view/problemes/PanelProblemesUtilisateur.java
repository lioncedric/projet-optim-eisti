package fr.eisti.optimEisti_RaLiGaKl.view.problemes;

import fr.eisti.optimEisti_RaLiGaKl.controler.problemes.PanelProblemesUtilisateurListener;
import fr.eisti.optimEisti_RaLiGaKl.model.BddProbleme;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 * Classe permettant de créer le panel utilisateur contenant tous ces problèmes ainsi que le panel de recherche et les boutons d'actions
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 */
public final class PanelProblemesUtilisateur extends JPanel {

    private JList list;                         //declaration de la liste que l'on voit visuellement
    private JScrollPane listScrollPane;         //declaration d'un jscrollpane destiné à contenir notre liste
    private DefaultListModel listModel;         //declaration d'une listeModel qui est la liste abstraite que va contenir notre liste visuelle
    private ListProblemeRenderer listePR;       //declaration d'une liste renderer pour la configuration des couleurs
    private JButton boutonHtml;                 //declaration du bouton permettant de générer le html
    private JButton boutonNew;                  //declaration du bouton permettant de créer un nouveau problème
    private JPanel panBoutons;                  //declaration du panel qui va contenir tous les boutons
    private JPanel panCentre;                   //declaration du panel qui correspond au centre
    private PanelRechercheProbleme panRecherche;//declaration du panel permettant la recherche
    private JPopupMenu jpopup;                  //declaration du jpopupmenu permettant l'affichage du menu déroulant après un clic droit sur un problème de la liste
    private JMenuItem jMenuItemOuvrir;          //declaration d'un item ouvrir de la liste du jmenupup
    private JMenuItem jMenuItemExporter;        //declaration d'un item exporter de la liste du jmenupup
    private JMenuItem jMenuItemSuppr;           //declaration d'un item supprimer de la liste du jmenupup
    private Color couleur1;                     //declaration de la couleur de fond degrade 1
    private Color couleur2;                     //declaration de la couleur de fond degrade 2
    private final Color couleurFondListe;       //declaration de la couleur de fond de la liste
    private Color couleurTexteBoutons;          //declaration de la couleur de texte
    private Color couleurFondPanelBoutons;      //declaration de la couleur de fond du panel bas des boutons

    /**
     * Constructeur permettant de créer le panel utilisateur
     */
    public PanelProblemesUtilisateur() {
        super(new BorderLayout());
        initialiserVariables();
        layoutListenersEtAutres();
        traitement();
        this.couleurFondListe = new Color(255, 255, 255,0);
        mettreCouleur();
    }

    /**
     * Procédure permettant d'initialiser toutes les variables déclarées précédemment
     */
    public void initialiserVariables() {
        this.listModel = new DefaultListModel();
        this.list = new JList(listModel);
        this.listePR = new ListProblemeRenderer();
        this.list.setCellRenderer(this.listePR);
        this.listScrollPane = new JScrollPane(list);

        this.boutonHtml = new JButton("Générer HTML");
        this.boutonNew = new JButton("Nouveau probleme");
        this.panBoutons = new JPanel();
        this.panCentre = new JPanel();
        this.panRecherche = new PanelRechercheProbleme();
        this.jpopup = new JPopupMenu();
        this.jMenuItemOuvrir = new JMenuItem("Ouvrir");
        this.jMenuItemExporter = new JMenuItem("Exporter");
        this.jMenuItemSuppr = new JMenuItem("Supprimer");
        
    }

    /**
     * Procédure qui ajoute les listeners sur les composants
     */
    public void layoutListenersEtAutres() {
        this.jMenuItemOuvrir.addActionListener(new PanelProblemesUtilisateurListener(this));
        this.jMenuItemSuppr.addActionListener(new PanelProblemesUtilisateurListener(this));
        this.jMenuItemExporter.addActionListener(new PanelProblemesUtilisateurListener(this));
        this.boutonNew.addActionListener(new PanelProblemesUtilisateurListener(this));
        this.boutonHtml.addActionListener(new PanelProblemesUtilisateurListener(this));

        this.list.addMouseListener(new PanelProblemesUtilisateurListener(this));

        this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.panBoutons.setLayout(new GridLayout(2, 1));
        this.panCentre.setLayout(new BorderLayout());

        this.panBoutons.setBorder(BorderFactory.createTitledBorder(null, "Actions possibles", 0, 0, new Font("Serif", Font.ITALIC, 14)));
        this.listScrollPane.setBorder(BorderFactory.createTitledBorder(null, "Mes problemes", 0, 0, new Font("Serif", Font.ITALIC, 14)));
    }

    /**
     * Procédure permettant de faire tous les traitements nécessaires
     */
    public void traitement() {
        this.jpopup.add(this.jMenuItemOuvrir);
        this.jpopup.add(this.jMenuItemSuppr);
        this.jpopup.add(this.jMenuItemExporter);

        this.list.setSelectedIndex(0);
        this.panBoutons.add(this.boutonNew);

        this.panBoutons.add(this.boutonHtml);
        this.panCentre.add(this.panRecherche, BorderLayout.NORTH);
        this.panCentre.add(this.listScrollPane, BorderLayout.CENTER);

        this.add(panCentre, BorderLayout.CENTER);
        this.add(panBoutons, BorderLayout.PAGE_END);
        miseajour();
    }

    /**
     * Procédure permetant de mettre le fond du panel en couleur
     * @param couleur : la couleur que l'on veut mettre en fond
     */
    public void mettreCouleur() {
        this.panCentre.setOpaque(false);
        this.listScrollPane.setOpaque(false);
        this.panBoutons.setBackground(Color.WHITE);
        this.list.setBackground(this.couleurFondListe);
        this.couleur1 = Color.WHITE;
        this.couleur2 = new Color(20, 145, 238);
    }

    public void changerCouleurDegrade(Color c1, Color c2) {
        this.couleur1 = c1;
        this.couleur2 = c2;
    }

    public void changerCouleurFondPanelBoutons(Color c) {
        this.couleurFondPanelBoutons = c;
    }

    public void changerCouleurTexteBoutons(Color c) {
        this.couleurTexteBoutons = c;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(new GradientPaint(0, 0, couleur1, 0, this.getHeight(), couleur2, true));
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        this.updateUI();
    }

    /**
     * Fonction qui renvoie un booleen pour dire si le nom du problème passé en paramètre existe déjà dans la liste ou pas
     * @param name
     * @return le booleen correspondant
     */
    protected boolean alreadyInList(String name) {
        return this.listModel.contains(name);
    }

    /**
     * Procédure miseajour qui raffraichit la liste des problèmes de l'utilisateur
     */
    public void miseajour() {
        List liste = BddProbleme.rechercheProbleme(this.panRecherche.getJtfRecherche().getText());
        //on commence par supprimer tous les éléments
        this.listModel.removeAllElements();
        //puis on ajoute un à un tous les problèmes
        while (!liste.isEmpty()) {
            this.listModel.addElement(BddProbleme.getProbleme(((LinkedList<Integer>) liste).getFirst()));
            liste.remove(0);
        }
        this.list.revalidate();
    }

    //LES GETTERS AND SETTERS
    public JButton getBoutonHtml() {
        return boutonHtml;
    }

    public JButton getBoutonNew() {
        return boutonNew;
    }

    public JMenuItem getjMenuItemExporter() {
        return jMenuItemExporter;
    }

    public JMenuItem getjMenuItemOuvrir() {
        return jMenuItemOuvrir;
    }

    public JMenuItem getjMenuItemSuppr() {
        return jMenuItemSuppr;
    }

    public JPopupMenu getJpopup() {
        return jpopup;
    }

    public JList getList() {
        return list;
    }

    public ListProblemeRenderer getListePR() {
        return listePR;
    }
}
