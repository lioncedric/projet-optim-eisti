/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eisti.optimEisti_RaLiGaKl.view.problemes;

import fr.eisti.optimEisti_RaLiGaKl.view.problemes.contraintes.Tableau;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import fr.eisti.optimEisti_RaLiGaKl.controler.problemes.contraintes.MoreListener;
import fr.eisti.optimEisti_RaLiGaKl.controler.problemes.SaveListener;
import fr.eisti.optimEisti_RaLiGaKl.Main;
import fr.eisti.optimEisti_RaLiGaKl.controler.problemes.PanelProblemeListener;
import fr.eisti.optimEisti_RaLiGaKl.model.BDDUtilisateur;
import fr.eisti.optimEisti_RaLiGaKl.model.BddProbleme;
import fr.eisti.optimEisti_RaLiGaKl.model.Contrainte;
import fr.eisti.optimEisti_RaLiGaKl.model.Probleme;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

/**
 * Classe qui represente un probleme a l'ecran
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 */
public class PanelProbleme extends JPanel {

    private JPanel panDonnees;
    private JLabel titre;
    private JLabel description;
    private JLabel variables;
    private JLabel objectif;
    private JLabel donnees;
    private JLabel contraintes;
    private ButtonGroup boutonsObjectif;
    private JRadioButton maximiser;
    private JRadioButton minimiser;
    private JButton ajouter;
    private JTextArea textfield;
    private JTextField jtfTitre;
    static final int min = 1;
    static final int max = 10;
    static final int init = 1;
    private JSlider slide;
    private int nbVariable;
    private Probleme probleme;
    private int numProbleme;
    private int hauteur = 0;
    private Tableau panTableau;
    private PanelResultat panelResultat;
    private PanelHautBas boutonHautBAs;
    //declaration des varables pour le panel de droite
    private PanelProblemesUtilisateur liste;
    // private ArrayList<Contrainte> contraintes;
    private PanelProblemeListener fsl;
    //Les couleurs pour le dégradé
    private Color couleur1;
    private Color couleur2;
    private Color couleurTexte;
    private Color couleurFondComposants;
    private int coeffTransparence;

    public PanelProbleme() {
        super();
        fsl = new PanelProblemeListener(this);
        this.probleme = new Probleme();
        probleme.setNumero(-100);
        initialiserVariables();
        traitementPanel();
        mettreCouleurFond(Color.white, new Color(209, 238, 238));
        mettreCouleurTexte(Color.BLACK);
        mettreFondComposants(Color.RED);
        slide.addChangeListener(fsl);
        slide.addChangeListener(new SaveListener());
    }

    public PanelProbleme(Probleme probleme) {
        super();
        fsl = new PanelProblemeListener(this);
        this.probleme = probleme;
        initialiserVariables();
        traitementPanel();
        remplissage();
        mettreCouleurFond(Color.white, new Color(209, 238, 238));
        mettreCouleurTexte(Color.BLACK);
        mettreFondComposants(Color.RED);
        slide.addChangeListener(fsl);
        slide.addChangeListener(new SaveListener());
    }

    private void initialiserVariables() {
        panelResultat = new PanelResultat(this);
        panDonnees = new JPanel();

        titre = new JLabel("Titre du probleme : ");
        description = new JLabel("Description : ");
        donnees = new JLabel("Optimisation :");
        variables = new JLabel("Nombre de variables : ");
        objectif = new JLabel("Fonction objectif :");
        contraintes = new JLabel("Liste des contraintes :");

        boutonHautBAs = new PanelHautBas(this);
        boutonsObjectif = new ButtonGroup();

        maximiser = new JRadioButton("Maximiser");
        minimiser = new JRadioButton("Minimiser");
        maximiser.addMouseListener(new SaveListener());
        minimiser.addMouseListener(new SaveListener());

        jtfTitre = new JTextField("Mon probleme", 30);
        jtfTitre.addKeyListener(new SaveListener());
        textfield = new JTextArea("Entrer votre description du problème");
        textfield.addKeyListener(new SaveListener());
        //---------------------------le slide--------------------------//
        slide = new JSlider(min, max, init);

        slide.setMinorTickSpacing(1);
        slide.setMajorTickSpacing(1);
        slide.setPaintTicks(true);
        slide.setPaintLabels(true);
        //--------------------------fin du slide-----------------------//

    }

    public void mettreCouleurFond(Color c1, Color c2) {
        this.couleur1 = c1;
        this.couleur2 = c2;
    }

    public void mettreCouleurTexte(Color couleur) {
        this.couleurTexte = couleur;
        //cela modifie automatiquement toutes les coleur du panel
        titre.setForeground(couleurTexte);
        description.setForeground(couleurTexte);
        variables.setForeground(couleurTexte);
        objectif.setForeground(couleurTexte);
        donnees.setForeground(couleurTexte);
        contraintes.setForeground(couleurTexte);
        ajouter.setForeground(couleurTexte);
    }

    public void mettreFondComposants(Color couleurFond) {
        this.couleurFondComposants = couleurFond;
        //ces composants doivent toujpours rester avec un fond transparent
        slide.setBackground(new Color(couleurFond.getRed(), couleurFond.getGreen(), couleurFond.getBlue(), 0));
        maximiser.setBackground(new Color(couleurFond.getRed(), couleurFond.getGreen(), couleurFond.getBlue(), 0));
        minimiser.setBackground(new Color(couleurFond.getRed(), couleurFond.getGreen(), couleurFond.getBlue(), 0));
        panDonnees.setBackground(new Color(couleurFond.getRed(), couleurFond.getGreen(), couleurFond.getBlue(), 0));
        //par defaut
        this.coeffTransparence = 50;
        jtfTitre.setBackground(new Color(couleurFond.getRed(), couleurFond.getGreen(), couleurFond.getBlue(), coeffTransparence));
        textfield.setBackground(new Color(couleurFond.getRed(), couleurFond.getGreen(), couleurFond.getBlue(), coeffTransparence));
        ajouter.setBackground(new Color(couleurFond.getRed(), couleurFond.getGreen(), couleurFond.getBlue(), coeffTransparence));
    }

    public void changerFondComposants(Color couleurFond, int coeffTranspa) {
        this.couleurFondComposants = couleurFond;
        this.coeffTransparence = coeffTranspa;
        jtfTitre.setBackground(new Color(couleurFond.getRed(), couleurFond.getGreen(), couleurFond.getBlue(), coeffTransparence));
        textfield.setBackground(new Color(couleurFond.getRed(), couleurFond.getGreen(), couleurFond.getBlue(), coeffTransparence));
        ajouter.setBackground(new Color(couleurFond.getRed(), couleurFond.getGreen(), couleurFond.getBlue(), coeffTransparence));
    }

    private void traitementPanel() {
        this.setLayout(null);

        boutonsObjectif.add(maximiser);
        boutonsObjectif.add(minimiser);
        maximiser.setSelected(true);

        panDonnees.setLayout(new FlowLayout(FlowLayout.CENTER));
        panDonnees.add(new JLabel("y="));
        panDonnees.add(new JTextField(3));
        panDonnees.add(new JLabel("x0"));


        this.add(boutonHautBAs);
        this.add(titre);
        this.add(jtfTitre);
        jtfTitre.setHorizontalAlignment(JTextField.CENTER);
        jtfTitre.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        this.add(description);
        textfield.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        this.add(textfield);
        this.add(slide);
        this.add(variables);
        this.add(objectif);
        this.add(maximiser);
        this.add(minimiser);
        this.add(donnees);
        this.add(panDonnees);
        this.add(contraintes);
        this.add(panelResultat);
        //pan3.setPreferredSize(new Dimension(200,200));
        this.raffraichitTabContrainte(fsl.getContraintes());

        this.validate();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(new GradientPaint(0, 0, couleur1, 0, this.getHeight(), couleur2, true));
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        titre.setBounds(this.getWidth() * 10 / 100, hauteur * this.getHeight() / 700 + this.getHeight() * 6 / 100, this.getWidth() * 30 / 100, this.getHeight() * 6 / 100);
        jtfTitre.setBounds(this.getWidth() * 42 / 100, hauteur * this.getHeight() / 700 + this.getHeight() * 6 / 100, this.getWidth() * 30 / 100, this.getHeight() * 6 / 100);
        description.setBounds(this.getWidth() * 10 / 100, hauteur * this.getHeight() / 700 + this.getHeight() * 15 / 100, this.getWidth() * 15 / 100, this.getHeight() * 15 / 100);
        textfield.setBounds(this.getWidth() * 42 / 100, hauteur * this.getHeight() / 700 + this.getHeight() * 15 / 100, this.getWidth() * 50 / 100, this.getHeight() * 20 / 100);
        donnees.setBounds(this.getWidth() * 10 / 100, hauteur * this.getHeight() / 700 + this.getHeight() * 38 / 100, this.getWidth() * 20 / 100, this.getHeight() * 15 / 100);
        maximiser.setBounds(this.getWidth() * 42 / 100, hauteur * this.getHeight() / 700 + this.getHeight() * 40 / 100, this.getWidth() * 20 / 100, this.getHeight() * 10 / 100);
        minimiser.setBounds(this.getWidth() * 65 / 100, hauteur * this.getHeight() / 700 + this.getHeight() * 40 / 100, this.getWidth() * 20 / 100, this.getHeight() * 10 / 100);
        slide.setBounds(this.getWidth() * 42 / 100, hauteur * this.getHeight() / 700 + this.getHeight() * 50 / 100, this.getWidth() * 40 / 100, this.getHeight() * 10 / 100);
        variables.setBounds(this.getWidth() * 10 / 100, hauteur * this.getHeight() / 700 + this.getHeight() * 48 / 100, this.getWidth() * 40 / 100, this.getHeight() * 15 / 100);
        panDonnees.setBounds(this.getWidth() * 25 / 100, hauteur * this.getHeight() / 700 + this.getHeight() * 60 / 100, this.getWidth() * 75 / 100, this.getHeight() * 10 / 100);
        objectif.setBounds(this.getWidth() * 10 / 100, hauteur * this.getHeight() / 700 + this.getHeight() * 58 / 100, this.getWidth() * 20 / 100, this.getHeight() * 15 / 100);
        contraintes.setBounds(this.getWidth() * 10 / 100, hauteur * this.getHeight() / 700 + this.getHeight() * 68 / 100, this.getWidth() * 30 / 100, this.getHeight() * 10 / 100);
        panTableau.setBounds(this.getWidth() * 5 / 100, hauteur * this.getHeight() / 700 + this.getHeight() * 78 / 100, this.getWidth() * 90 / 100, this.getHeight() * 20 / 100);
        ajouter.setBounds(this.getWidth() * 42 / 100, hauteur * this.getHeight() / 700 + this.getHeight() * 70 / 100, this.getWidth() * 40 / 100, this.getHeight() * 5 / 100);
        panelResultat.setBounds(0, hauteur * this.getHeight() / 700 + this.getHeight(), this.getWidth(), this.getHeight() * 40 / 100);
        boutonHautBAs.setBounds(this.getWidth() * 96 / 100, this.getHeight() * 2 / 100, this.getWidth() * 3 / 100, this.getWidth() * 3 / 100);
        this.updateUI();
    }

    private void remplissage() {
        this.jtfTitre.setText(this.probleme.getTitre());
        this.textfield.setText(this.probleme.getDescription());
        this.slide.setValue(this.probleme.getCoeffVariables().size());

        if (this.probleme.getObjectif().equals("Maximiser")) {
            this.maximiser.setSelected(true);
        } else if (this.probleme.getObjectif().equals("Minimiser")) {
            this.minimiser.setSelected(true);
        } else {
        }
        this.panDonnees.removeAll();
        this.panDonnees.add(new JLabel("y="));
        this.panDonnees.add(new JTextField(3));
        this.panDonnees.add(new JLabel("x0"));

        for (int i = 1; i < this.probleme.getCoeffVariables().size(); i++) {
            this.ajouterVariable(i);
        }
        for (int i = 0; i < this.probleme.getCoeffVariables().size(); i++) {
            try {
                JTextField jtf = (JTextField) this.panDonnees.getComponent(3 * i + 1);
                jtf.setText(String.valueOf(this.probleme.getCoeffVariables().get(i)));
            } catch (Exception e) {
            }
        }

        //remplissage du tableau
        if (!this.probleme.getContraintes().isEmpty()) {
            panTableau = new Tableau(this.probleme.getContraintes().size(), this.probleme.getContraintes().get(0).getCoeffVariables().size());
            fsl.setContraintes(this.probleme.getContraintes());
            this.raffraichitTabContrainte(this.probleme.getContraintes());
            fsl.setContraintes(this.probleme.getContraintes());
            this.panTableau.rempliTableau(this.probleme.getContraintes());
        } else {
            this.raffraichitTabContrainte(this.probleme.getContraintes());
            panTableau = new Tableau(nbVariable);
        }
    }

    public void ajouterVariable(int i) {
        JTextField jtf;
        jtf = new JTextField(3);
        JLabel xi;
        xi = new JLabel("x" + i);
        panDonnees.add(new JLabel("+"));
        panDonnees.add(jtf);
        jtf.addKeyListener(new SaveListener());
        panDonnees.add(xi);
        nbVariable = i;
    }

    public void raffraichitTabContrainte(ArrayList<Contrainte> contraintes) {
        if (panTableau != null) {
            this.remove(ajouter);
            this.remove(panTableau);
        }
        nbVariable = this.slide.getValue();
        if (contraintes.isEmpty()) {
            panTableau = new Tableau(nbVariable);
        } else {
            panTableau = new Tableau(contraintes.size(), nbVariable);
        }

        ajouter = new JButton("Ajouter une ligne");
        ajouter.addActionListener(new MoreListener(panTableau, nbVariable));

        this.add(panTableau);
        panTableau.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        this.add(ajouter);
        ajouter.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        ajouter.setBackground(new Color(0, 0, 0, this.coeffTransparence));
        ajouter.setForeground(this.couleurTexte);
    }

    /**
     * verifier le contenu de la fenetre dans un probleme
     */
    public boolean verifier() {
        boolean titreOK;
        boolean ligneRempli;
        boolean descriptionOK;
        boolean variablesOK = true;

        titreOK = !(this.getJtfTitre().getText().equals(""));
        descriptionOK = !(this.getTextfield().getText().equals(""));
        ligneRempli = this.getPanTableau().ligneRempli();

        for (int i = 0; i < this.getSlide().getValue(); i++) {
            JTextField jtf = (JTextField) (this.getPanDonnees().getComponent(3 * i + 1));
            variablesOK = variablesOK && !(jtf.getText().equals("")) && !(jtf.getText().equals("0"));
            try {
                Double.valueOf(jtf.getText());
            } catch (NumberFormatException nfe) {
                variablesOK = false;
            }
        }
        return titreOK && descriptionOK && variablesOK && ligneRempli;
    }

    /**
     * enregisrer le contenu de la fenetre dans un probleme
     * @param indexTab le fenetre contenant les informations du probleme
     */
    public void enregisrer(int indexTab) {
        if (verifier()) {
            Probleme p = this.getProbleme();
            p.renseignerProbleme(this);
            if (p.getNumero() >= 0 && p.getNumero() < BddProbleme.nombreProblemes()) {
                BddProbleme.supprimerProbleme(p.getNumero());
            }
            //met a jour l'index des autres onglets
            for (int j = 0; j < Main.fenetrePrincipale.getDroite().getTabCount(); j++) {
                int numProbeCours = ((PanelProbleme) Main.fenetrePrincipale.getDroite().getComponentAt(j)).getProbleme().getNumero();
                if (numProbeCours > p.getNumero()) {
                    ((PanelProbleme) Main.fenetrePrincipale.getDroite().getComponentAt(j)).getProbleme().setNumero(numProbeCours - 1);

                }
            }
            BddProbleme.addProbleme(p);
            ((PanelProbleme) Main.fenetrePrincipale.getDroite().getComponentAt(indexTab)).getProbleme().setNumero(BddProbleme.nombreProblemes() - 1);
            p.setNumero(BddProbleme.nombreProblemes() - 1);
            BddProbleme.save(BDDUtilisateur.getNomUtilisateur());
            Main.fenetrePrincipale.getDroite().setTitleAt(indexTab, p.getTitre());
            SaveListener.estmodifié();
            Main.fenetrePrincipale.getPanProfil().miseAJour();
            Main.fenetrePrincipale.getGauche().miseajour();

        } else {
            JOptionPane.showMessageDialog(null, "Vous n'avez pas bien rempli tous les parametres ", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    //GETERS AND SETTERS
    public PanelResultat getPanelResultat() {
        return panelResultat;
    }

    public JButton getAjouter() {
        return ajouter;
    }

    public ButtonGroup getBoutonsObjectif() {
        return boutonsObjectif;
    }

    public JLabel getDescription() {
        return description;
    }

    public JLabel getDonnees() {
        return donnees;
    }

    public JTextField getJtfTitre() {
        return jtfTitre;
    }

    public void setJtfTitre(JTextField jtfTitre) {
        this.jtfTitre = jtfTitre;
    }

    public PanelProblemesUtilisateur getListe() {
        return liste;
    }

    public void setListe(PanelProblemesUtilisateur liste) {
        this.liste = liste;
    }

    public JRadioButton getMaximiser() {
        return maximiser;
    }

    public JRadioButton getMinimiser() {
        return minimiser;
    }

    public int getNumProbleme() {
        return numProbleme;
    }

    public void setNumProbleme(int numProbleme) {
        this.numProbleme = numProbleme;
    }

    public JPanel getPanDonnees() {
        return panDonnees;
    }

    public Tableau getPanTableau() {
        return panTableau;
    }

    public Probleme getProbleme() {
        return probleme;
    }

    public JSlider getSlide() {
        return slide;
    }

    public JTextArea getTextfield() {
        return textfield;
    }

    public JLabel getTitre() {
        return titre;
    }

    public JLabel getVariables() {
        return variables;
    }

    public int getHauteur() {
        return hauteur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }
}
