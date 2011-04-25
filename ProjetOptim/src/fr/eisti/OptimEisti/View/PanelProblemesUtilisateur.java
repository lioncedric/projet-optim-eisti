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
import javax.swing.event.ListSelectionEvent;

public final class PanelProblemesUtilisateur extends JPanel {

    private JList list;
    private JScrollPane listScrollPane;
    private DefaultListModel listModel;
   
    private JButton boutonSolution;
    private JButton boutonNew;
    private JPanel panBoutons;
    private JPanel panCentre;
    private PanelRechercheProbleme panRecherche;
    private JPopupMenu jpopup;
    private JMenuItem jMenuItemOuvrir;
    private JMenuItem jMenuItemExporter;
    private JMenuItem jMenuItemSuppr;

    public PanelProblemesUtilisateur() {
        super(new BorderLayout());
        initialiserVariables();
        traitement();
        mettreCouleur(Color.WHITE);
    }

    public void initialiserVariables() {
        this.boutonNew = new JButton("Nouveau probleme");
        this.boutonSolution = new JButton("Trouver solution");
      
        this.panBoutons = new JPanel();
        this.panCentre = new JPanel();
        this.panRecherche = new PanelRechercheProbleme();
        this.listModel = new DefaultListModel();
        this.list = new JList(listModel);
        this.listScrollPane = new JScrollPane(list);


        this.jpopup = new JPopupMenu();
        this.jMenuItemOuvrir = new JMenuItem("Ouvrir");
        jMenuItemOuvrir.addActionListener(new PanelProblemesUtilisateurListener(this));
        this.jMenuItemSuppr = new JMenuItem("Supprimer");
        jMenuItemSuppr.addActionListener(new PanelProblemesUtilisateurListener(this));
        this.jMenuItemExporter = new JMenuItem("Exporter");
        jMenuItemExporter.addActionListener(new PanelProblemesUtilisateurListener(this));
        this.jpopup.add(this.jMenuItemOuvrir);
        this.jpopup.add(this.jMenuItemSuppr);
         this.jpopup.add(this.jMenuItemExporter);

        this.panBoutons.setBorder(BorderFactory.createTitledBorder(null, "Actions possibles", 0, 0, new Font("Serif", Font.ITALIC, 14)));

        this.boutonNew.addActionListener(new PanelProblemesUtilisateurListener(this));
        this.boutonSolution.addActionListener(new PanelProblemesUtilisateurListener(this));
     

        this.panBoutons.setLayout(new GridLayout(3, 1));
        this.panCentre.setLayout(new BorderLayout());
    }

    public void traitement() {
        this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.list.setSelectedIndex(0);
        this.list.addMouseListener(new PanelProblemesUtilisateurListener(this));
        this.listScrollPane.setBorder(BorderFactory.createTitledBorder(null, "Mes problemes", 0, 0, new Font("Serif", Font.ITALIC, 14)));
        this.panBoutons.add(this.boutonNew);
       
        this.panBoutons.add(this.boutonSolution);

        this.panCentre.add(this.panRecherche, BorderLayout.NORTH);
        this.panCentre.add(this.listScrollPane, BorderLayout.CENTER);
        this.add(panCentre, BorderLayout.CENTER);
        this.add(panBoutons, BorderLayout.PAGE_END);
        miseajour();
    }

    public void mettreCouleur(Color couleur) {
        this.panCentre.setOpaque(false);
        this.listScrollPane.setOpaque(false);
        this.panBoutons.setOpaque(false);
        this.setBackground(couleur);
        this.list.setBackground(new Color(209, 238, 238));
    }

    protected boolean alreadyInList(String name) {
        return listModel.contains(name);
    }

    public void valueChanged(ListSelectionEvent e) {
        /* int numero = ((JList) e.getSource()).getSelectedIndex();
        if (numero != -1) {
        Probleme probleme;
        probleme = BddProbleme.getProbleme(numero);
        Main.fenetrePrincipale.raz();
        Main.fenetrePrincipale.traitementPanelGauche();
        Main.fenetrePrincipale.remplissage(probleme);
        Main.fenetrePrincipale.setNumProbleme(numero);
        }*/
    }

    public void miseajour() {
        listModel.removeAllElements();

        for (int i = 0; i < BddProbleme.nombreProblemes(); i++) {
            listModel.addElement(BddProbleme.getProbleme(i).getTitre());
        }

    }

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
