/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eisti.optimEisti_RaLiGaKl.view.problemes;

import fr.eisti.optimEisti_RaLiGaKl.Main;
import fr.eisti.optimEisti_RaLiGaKl.controler.problemes.PanelProblemeListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

/**
 * Classe permettant de créer le panel affichant le resultat
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 */
public class PanelResultat extends JPanel {

    private JTextArea outpout;
    private JScrollPane outpoutS;
    private JScrollPane resS;
    private JList res;
    private JLabel texteres;
    private DefaultListModel listModel;
    private JButton effacer;
    private JButton calculer;
    /**
     * mettre a jour les solutions a l'ecran
     * @param lst : la liste des solutions
     */
    public void miseajour(ArrayList<Double> lst) {
        listModel.removeAllElements();
        res.removeAll();
        //si le resultat est de taille nulle, c'est qu'il n'y avait aps de solutions.
        if (lst == null || lst.isEmpty()) {
            listModel.addElement("Pas de solutions !");
        } //sinon, c'est qu'il y a une solution et dans ce cas, on l'affiche
        else {
            for (int i = 0; i < lst.size(); i++) {
                listModel.addElement(lst.get(i).toString());
            }
        }

    }
    /**
     * Constructeur d'un panel où sera afficher le résultat
     * @param panelProbleme : le panel du probleme
     */
    public PanelResultat(PanelProbleme panelProbleme) {
        //initialistion des composants
        effacer = new JButton("Effacer");
        calculer = new JButton("Calculer");
        calculer.addActionListener(new PanelProblemeListener(panelProbleme));
        effacer.addActionListener(new PanelProblemeListener(panelProbleme));
        //initialisation de la liste des resultat
        this.listModel = new DefaultListModel();
        this.res = new JList(listModel);
        res.setCellRenderer(new ListResRenderer());
        outpout = new JTextArea();
        outpout.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        res.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));


        texteres = new JLabel("Resultat :");
        this.add(texteres);

        this.add(calculer);
        outpout.setEditable(false);

        outpoutS = new JScrollPane(outpout);
        resS = new JScrollPane(res);
        this.add(outpoutS);
        this.add(resS);
        this.add(effacer);
        this.setBackground(new Color(255, 255, 255, 255));
        miseajour(panelProbleme.getProbleme().getResultat());
    }
    /**
     * ecriture d'un message dans le outpout de l'application
     * @param s : teste a ecrire
     */
    public static void ecrire(String s) {
        ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanelResultat().getOutpout().append(s);
    }
    /**
     * tout effacer
     */
    public static void effacer() {
        ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanelResultat().getOutpout().setText("");
        ((PanelProbleme) Main.fenetrePrincipale.getDroite().getSelectedComponent()).getPanelResultat().getListModel().removeAllElements();
    }
/**
 * definittion de la peinte du panel
 * @param g : le graphique
 */
    @Override
    public void paintComponent(Graphics g) {

        texteres.setBounds(this.getWidth() * 10 / 100, this.getHeight() * 5 / 100, this.getWidth() * 20 / 100, this.getHeight() * 15 / 100);

        effacer.setBounds(this.getWidth() * 5 / 100, this.getHeight() * 20 / 100, this.getWidth() * 60 / 100, this.getHeight() * 10 / 100);
        outpoutS.setPreferredSize(new Dimension(this.getWidth() * 60 / 100, this.getHeight() * 60 / 100));
        outpoutS.setBounds(this.getWidth() * 5 / 100, this.getHeight() * 30 / 100, this.getWidth() * 60 / 100, this.getHeight() * 60 / 100);
        resS.setPreferredSize(new Dimension(this.getWidth() * 25 / 100, this.getHeight() * 60 / 100));
        resS.setBounds(this.getWidth() * 70 / 100, this.getHeight() * 30 / 100, this.getWidth() * 25 / 100, this.getHeight() * 60 / 100);
        calculer.setBounds(this.getWidth() * 70 / 100, this.getHeight() * 20 / 100, this.getWidth() * 25 / 100, this.getHeight() * 10 / 100);
        this.updateUI();
    }

    /**
     * Fonction qui permet de récupèrer le bouton calculer
     * @return le bouton calculer
     */
    public JButton getCalculer() {
        return calculer;
    }

    /**
     * Fonction qui permet de modifier le bouton calculer
     * @param calculer : le nouveau bouton
     */
    public void setCalculer(JButton calculer) {
        this.calculer = calculer;
    }

    /**
     * Fonction qui permet de récupèrer le bouton effacer
     * @return le bouton effacer
     */
    public JButton getEffacer() {
        return effacer;
    }

    /**
     * Fonction qui permet de récupèrer le textArea
     * @return le texte area
     */
    public JTextArea getOutpout() {
        return outpout;
    }

    /**
     * Fonction qui permet de modifier le bouton effacer
     * @param effacer : le nouveau bouton
     */
    public void setEffacer(JButton effacer) {
        this.effacer = effacer;
    }

    /**
     * Fonction qui permet de recupérer la listModel
     * @return la listModel
     */
    public DefaultListModel getListModel() {
        return listModel;
    }

    /**
     * Fonction qui permet de modifier une listModel
     * @param listModel : La nouvelle liste
     */
    public void setListModel(DefaultListModel listModel) {
        this.listModel = listModel;
    }
}
