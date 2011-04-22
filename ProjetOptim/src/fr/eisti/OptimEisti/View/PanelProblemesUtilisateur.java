package fr.eisti.OptimEisti.View;

import fr.eisti.OptimEisti.Controler.PanelProblemesUtilisateurListener;
import fr.eisti.OptimEisti.Model.BddProbleme;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

public final class PanelProblemesUtilisateur extends JPanel{

    private JList list;
    private DefaultListModel listModel;
    private JLabel labelProblemes;
    private JButton boutonEnregistrer;
    private JButton boutonSolution;
    private JButton boutonNew;
    private JPanel panBoutons;
    
    private JPopupMenu jpopup;
    JMenuItem jMenuItemOuvrir;
    JMenuItem jMenuItemSuppr;

    public PanelProblemesUtilisateur() {
        super(new BorderLayout());
        initialiserVariables();
        traitement();
    }

    public void initialiserVariables() {
        this.boutonNew = new JButton("Nouveau probleme");
        this.boutonSolution=new JButton("Trouver solution");
        this.boutonEnregistrer = new JButton("Enregistrer");
        this.panBoutons = new JPanel();
        this.listModel = new DefaultListModel();
        this.list = new JList(listModel);
        
        
        this.jpopup=new JPopupMenu();
        this.jMenuItemOuvrir=new JMenuItem("Ouvrir");
        jMenuItemOuvrir.addActionListener(new PanelProblemesUtilisateurListener(this)); 
        this.jMenuItemSuppr=new JMenuItem("Supprimer"); 
        jMenuItemSuppr.addActionListener(new PanelProblemesUtilisateurListener(this));
        this.jpopup.add(this.jMenuItemOuvrir);
        this.jpopup.add(this.jMenuItemSuppr);

        this.panBoutons.setBorder(BorderFactory.createTitledBorder(null, "Actions possibles", 0,0, new Font("Serif", Font.ITALIC, 14)));
        
        this.boutonNew.addActionListener(new PanelProblemesUtilisateurListener(this));
        this.boutonSolution.addActionListener(new PanelProblemesUtilisateurListener(this));
        this.boutonEnregistrer.addActionListener(new PanelProblemesUtilisateurListener(this));
        
        this.labelProblemes = new JLabel("Mes problemes", JLabel.CENTER);
        this.panBoutons.setLayout(new GridLayout(3, 1));
        this.labelProblemes.setFont(new Font("Serif", Font.BOLD, 20));
    }
    

    public void traitement() {
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addMouseListener(new PanelProblemesUtilisateurListener(this));
        JScrollPane listScrollPane = new JScrollPane(list);
        listScrollPane.setBorder(BorderFactory.createTitledBorder(null, "Mes problemes", 0,0, new Font("Serif", Font.ITALIC, 14)));
        this.panBoutons.add(this.boutonNew);
        this.panBoutons.add(this.boutonEnregistrer);
        this.panBoutons.add(this.boutonSolution);

        this.add(listScrollPane, BorderLayout.CENTER);
        this.add(panBoutons, BorderLayout.PAGE_END);
        miseajour();
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
    
    public JButton getBoutonEnregistrer() {
        return boutonEnregistrer;
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

    public JPopupMenu getJpopup() {
        return jpopup;
    }
    
    
}
