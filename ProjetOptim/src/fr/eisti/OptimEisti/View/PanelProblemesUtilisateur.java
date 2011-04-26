package fr.eisti.OptimEisti.View;

import fr.eisti.OptimEisti.Controler.PanelProblemesUtilisateurListener;
import fr.eisti.OptimEisti.Model.BddProbleme;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
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
 * @version: 1.0
 */
public final class PanelProblemesUtilisateur extends JPanel {

    private JList list;                         //declaration de la liste que l'on voit visuellement
    private JScrollPane listScrollPane;         //declaration d'un jscrollpane destiné à contenir notre liste
    private DefaultListModel listModel;         //declaration d'une listeModel qui est la liste abstraite que va contenir notre liste visuelle
    private JButton boutonEnregistrer;          //declaration du bouton permettant l'enregistrement
    private JButton boutonSolution;             //declaration du bouton permettant de générer la solution
    private JButton boutonNew;                  //declaration du bouton permettant de créer un nouveau problème
    private JPanel panBoutons;                  //declaration du panel qui va contenir tous les boutons
    private JPanel panCentre;                   //declaration du panel qui correspond au centre
    private PanelRechercheProbleme panRecherche;//declaration du panel permettant la recherche
    private JPopupMenu jpopup;                  //declaration du jpopupmenu permettant l'affichage du menu déroulant après un clic droit sur un problème de la liste
    private JMenuItem jMenuItemOuvrir;          //declaration d'un item ouvrir de la liste du jmenupup
    private JMenuItem jMenuItemExporter;        //declaration d'un item exporter de la liste du jmenupup
    private JMenuItem jMenuItemSuppr;           //declaration d'un item supprimer de la liste du jmenupup

    /**
     * Constructeur permettant de créer le panel utilisateur
     */
    public PanelProblemesUtilisateur() {
        super(new BorderLayout());
        initialiserVariables();
        layoutListenersEtAutres();
        traitement();
        mettreCouleur(Color.WHITE);
    }

    /**
     * Procédure permettant d'initialiser toutes les variables déclarées précédemment
     */
    public void initialiserVariables() {
          this.listModel = new DefaultListModel();
        this.list = new JList(listModel);
        this.listScrollPane = new JScrollPane(list);
      
        this.boutonEnregistrer = new JButton("Enregistrer");
        this.boutonSolution = new JButton("Trouver solution");
        this.boutonSolution.setEnabled(false);
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
        this.boutonSolution.addActionListener(new PanelProblemesUtilisateurListener(this));
     
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
       
        this.panBoutons.add(this.boutonSolution);
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
    public void mettreCouleur(Color couleur) {
        this.panCentre.setOpaque(false);
        this.listScrollPane.setOpaque(false);
        this.panBoutons.setOpaque(false);
        this.setBackground(couleur);
        this.list.setBackground(new Color(209, 238, 238));
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
        //on commence par supprimer tous les éléments
        this.listModel.removeAllElements();
        //puis on ajoute un à un tous les problèmes
        for (int i = 0; i < BddProbleme.nombreProblemes(); i++) {
            this.listModel.addElement(BddProbleme.getProbleme(i).getTitre());
        }

    }

    //LES GETTERS AND SETTERS
    public JList getList() {
        return list;
    }

    public DefaultListModel getListModel() {
        return listModel;
    }

    public JButton getBoutonNew() {
        return boutonNew;
    }


    public JButton getBoutonSolution() {
        return boutonSolution;
    }

    public JMenuItem getjMenuItemOuvrir() {
        return jMenuItemOuvrir;
    }

    public JMenuItem getjMenuItemSuppr() {
        return jMenuItemSuppr;
    }

    public JMenuItem getjMenuItemExporter() {
        return jMenuItemExporter;
    }

    public JPopupMenu getJpopup() {
        return jpopup;
    }

    public PanelRechercheProbleme getPanRecherche() {
        return panRecherche;
    }
}
