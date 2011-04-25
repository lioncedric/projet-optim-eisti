/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eisti.OptimEisti.View;

import fr.eisti.OptimEisti.View.contraintes.Tableau;
import fr.eisti.OptimEisti.Controler.FenetreSaisieListener;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import fr.eisti.OptimEisti.Controler.MoreListener;
import fr.eisti.OptimEisti.Controler.SaveListener;
import fr.eisti.OptimEisti.Main;
import fr.eisti.OptimEisti.Model.BddProbleme;
import fr.eisti.OptimEisti.Model.Contrainte;
import fr.eisti.OptimEisti.Model.Probleme;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author 
 */
public class JPanelProbleme extends JPanel {

    private JPanel pan2;
    private JPanel pan3;
    private JPanel panTitre;
    private JPanel panVariables;
    private JPanel panObjectif;
    private JPanel panDonnees;
    private JPanel panEst;
    private JPanel panNord;
    private JLabel titre;
    private JLabel description;
    private JLabel variables;
    private JLabel objectif;
    private JLabel donnees;
    private ButtonGroup boutonsObjectif;
    private JRadioButton maximiser;
    private JRadioButton minimiser;
    private JButton ajouter;
    private JTextField textfield;
    private JTextField jtfTitre;
    static final int min = 1;
    static final int max = 10;
    static final int init = 1;
    private JSlider slide;
    private int nbVariable;
    private Image fond;
    private Image item;
    private ImageIcon icon;
    private Probleme probleme;
    private int numProbleme;
    private Tableau panTableau;
    //declaration des varables pour le panel de droite
    private PanelProblemesUtilisateur liste;
    // private ArrayList<Contrainte> contraintes;
    private FenetreSaisieListener fsl;

    public JPanelProbleme(int numero) {
        super();
        fsl = new FenetreSaisieListener();
        this.probleme = new Probleme();
        probleme.setNumero(numero);
        initialiserVariables();
        traitementPanel();
        raffraichitTabContrainte(probleme.getContraintes());



    }

    public JPanelProbleme(Probleme probleme) {
        super();
        fsl = new FenetreSaisieListener();
        this.probleme = probleme;
        initialiserVariables();
        traitementPanel();
        remplissage();
       
    }

    /**
     * initialise le proble aux valeurs rentrées par l'utilisateur
     * @param fenetre le fenetre contenant les informations du probleme
     */
    public void enregisrer(int indexTab) {
        boolean titreOK;
        titreOK = !(this.getJtfTitre().getText().equals(""));
        boolean descriptionOK;
        descriptionOK = !(this.getTextfield().getText().equals(""));
        boolean ligneRempli;
        ligneRempli = this.getPanTableau().ligneRempli();
        boolean variablesOK = true;
        for (int i = 0; i < this.getSlide().getValue(); i++) {
            JTextField jtf = (JTextField) (this.getPanDonnees().getComponent(3 * i + 1));
            variablesOK = variablesOK && !(jtf.getText().equals("")) && !(jtf.getText().equals("0"));
            try {
                double valeur = Double.valueOf(jtf.getText());
            } catch (NumberFormatException nfe) {
                variablesOK = false;
            }
        }
        if (titreOK && descriptionOK && variablesOK && ligneRempli) {
            Probleme p = this.getProbleme();
            p.renseignerProbleme(this);
            if (p.getNumero() < BddProbleme.nombreProblemes()) {
                int numero = p.getNumero();
                System.out.print(numero + "/");
                BddProbleme.supprimerProbleme(numero);
                for (int i = 0; i < Main.fenetrePrincipale.getDroite().getTabCount(); i++) {
                    int numProbeCours = ((JPanelProbleme) Main.fenetrePrincipale.getDroite().getComponentAt(i)).getProbleme().getNumero();
                    if (numProbeCours > numero) {
                        ((JPanelProbleme) Main.fenetrePrincipale.getDroite().getComponentAt(i)).getProbleme().setNumero(numProbeCours - 1);

                    }

                }
            }
            p.setNumero(BddProbleme.nombreProblemes());
            BddProbleme.addProbleme(p);

            Main.fenetrePrincipale.getDroite().setTitleAt(indexTab, p.getTitre());
            SaveListener.estmodifié();
            Main.fenetrePrincipale.getPanProfil().miseAJour();
            Main.fenetrePrincipale.getGauche().miseajour();

        } else {
            JOptionPane.showMessageDialog(null, "Vous n'avez pas bien rempli tous les parametres " + titreOK + descriptionOK + variablesOK + ligneRempli, "Erreur", JOptionPane.ERROR_MESSAGE);
        }
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
        pan3.removeAll();
        this.panNord.removeAll();
        this.panEst.removeAll();
        nbVariable = this.slide.getValue();
        if (contraintes.isEmpty()) {
            panTableau = new Tableau(nbVariable);
        } else {
            panTableau = new Tableau(contraintes.size(), nbVariable);
        }
        panNord.add(new JLabel("Gestion des contraintes"));
        panNord.setPreferredSize(new Dimension(100, 50));
        pan3.add(panNord, BorderLayout.NORTH);
        pan3.add(panTableau, BorderLayout.CENTER);
        ajouter = new JButton("Ajouter une ligne");
        ajouter.addActionListener(new MoreListener(panTableau, nbVariable));
        panEst.add(ajouter);
        pan3.add(panEst, BorderLayout.SOUTH);
    }

    private void traitementPanel() {

        pan2.setLayout(new GridLayout(7, 1, 0, 0));
        this.setLayout(new GridBagLayout());
        GridBagConstraints contrainteLayout = new GridBagConstraints();
        contrainteLayout.fill = GridBagConstraints.BOTH;
        contrainteLayout.gridx = 0;
        contrainteLayout.gridy = 0;
        contrainteLayout.weighty = 0.7;
        contrainteLayout.weightx = 1;

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        panTitre.add(titre);
        panTitre.add(jtfTitre);

        boutonsObjectif.add(maximiser);
        boutonsObjectif.add(minimiser);
        maximiser.setSelected(true);

        panObjectif.add(objectif);
        panObjectif.add(maximiser);
        panObjectif.add(minimiser);

        panVariables.setLayout(new FlowLayout(1, 100, 0));
        panVariables.add(variables);
        panVariables.add(slide);

        panDonnees.add(new JLabel("y="));
        panDonnees.add(new JTextField(3));
        panDonnees.add(new JLabel("x0"));

        pan2.add(panTitre);
        pan2.add(description);
        pan2.add(textfield);
        pan2.add(panVariables);
        pan2.add(panObjectif);
        pan2.add(donnees);
        pan2.add(panDonnees);

        pan3.setLayout(new BorderLayout());
        //pan3.setPreferredSize(new Dimension(200,200));
        this.raffraichitTabContrainte(fsl.getContraintes());


        try {
            item = ImageIO.read(new File("images/icon2.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        icon = new ImageIcon(item);

        this.add(pan2, contrainteLayout);
        contrainteLayout.gridx = 0;
        contrainteLayout.gridy = 1;

        contrainteLayout.weighty = 0.3;
        this.add(pan3, contrainteLayout);
        this.validate();
    }

    private void initialiserVariables() {
        pan2 = new JPanel();
        pan3 = new JPanel();
        panTitre = new JPanel();

        panVariables = new JPanel();
        panObjectif = new JPanel();
        panDonnees = new JPanel();
        panEst = new JPanel();
        panNord = new JPanel();
        titre = new JLabel("Titre du probleme: ");
        description = new JLabel("Description du problème", JLabel.CENTER);

        variables = new JLabel("Nombre de variables");
        objectif = new JLabel("Fonction objectif");
        donnees = new JLabel("Remplir les données", JLabel.CENTER);

        boutonsObjectif = new ButtonGroup();

        maximiser = new JRadioButton("Maximiser");
        minimiser = new JRadioButton("Minimiser");
        maximiser.addMouseListener(new SaveListener());
        minimiser.addMouseListener(new SaveListener());

        jtfTitre = new JTextField("Mon probleme", 30);
        jtfTitre.addKeyListener(new SaveListener());
        textfield = new JTextField("Entrer votre description du problème");
        textfield.addKeyListener(new SaveListener());
        //---------------------------le slide--------------------------//
        slide = new JSlider(min, max, init);
        slide.addChangeListener(fsl);
        slide.addChangeListener(new SaveListener());
        slide.setMinorTickSpacing(1);
        slide.setMajorTickSpacing(1);
        slide.setPaintTicks(true);
        slide.setPaintLabels(true);
        //--------------------------fin du slide-----------------------//

        fond = null;
        item = null;
        icon = null;
    }

    public JButton getAjouter() {
        return ajouter;
    }

    public void setAjouter(JButton ajouter) {
        this.ajouter = ajouter;
    }

    public ButtonGroup getBoutonsObjectif() {
        return boutonsObjectif;
    }

    public void setBoutonsObjectif(ButtonGroup boutonsObjectif) {
        this.boutonsObjectif = boutonsObjectif;
    }

    public JLabel getDescription() {
        return description;
    }

    public void setDescription(JLabel description) {
        this.description = description;
    }

    public JLabel getDonnees() {
        return donnees;
    }

    public void setDonnees(JLabel donnees) {
        this.donnees = donnees;
    }

    public Image getFond() {
        return fond;
    }

    public void setFond(Image fond) {
        this.fond = fond;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public Image getItem() {
        return item;
    }

    public void setItem(Image item) {
        this.item = item;
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

    public void setMaximiser(JRadioButton maximiser) {
        this.maximiser = maximiser;
    }

    public JRadioButton getMinimiser() {
        return minimiser;
    }

    public void setMinimiser(JRadioButton minimiser) {
        this.minimiser = minimiser;
    }

    public int getNumProbleme() {
        return numProbleme;
    }

    public void setNumProbleme(int numProbleme) {
        this.numProbleme = numProbleme;
    }

    public JLabel getObjectif() {
        return objectif;
    }

    public void setObjectif(JLabel objectif) {
        this.objectif = objectif;
    }

    public JPanel getPan2() {
        return pan2;
    }

    public void setPan2(JPanel pan2) {
        this.pan2 = pan2;
    }

    public JPanel getPan3() {
        return pan3;
    }

    public void setPan3(JPanel pan3) {
        this.pan3 = pan3;
    }

    public JPanel getPanDonnees() {
        return panDonnees;
    }

    public void setPanDonnees(JPanel panDonnees) {
        this.panDonnees = panDonnees;
    }

    public JPanel getPanEst() {
        return panEst;
    }

    public void setPanEst(JPanel panEst) {
        this.panEst = panEst;
    }

    public JPanel getPanNord() {
        return panNord;
    }

    public void setPanNord(JPanel panNord) {
        this.panNord = panNord;
    }

    public JPanel getPanObjectif() {
        return panObjectif;
    }

    public void setPanObjectif(JPanel panObjectif) {
        this.panObjectif = panObjectif;
    }

    public Tableau getPanTableau() {
        return panTableau;
    }

    public void setPanTableau(Tableau panTableau) {
        this.panTableau = panTableau;
    }

    public JPanel getPanTitre() {
        return panTitre;
    }

    public void setPanTitre(JPanel panTitre) {
        this.panTitre = panTitre;
    }

    public JPanel getPanVariables() {
        return panVariables;
    }

    public void setPanVariables(JPanel panVariables) {
        this.panVariables = panVariables;
    }

    public Probleme getProbleme() {
        return probleme;
    }

    public void setProbleme(Probleme probleme) {
        this.probleme = probleme;
    }

    public JSlider getSlide() {
        return slide;
    }

    public void setSlide(JSlider slide) {
        this.slide = slide;
    }

    public JTextField getTextfield() {
        return textfield;
    }

    public void setTextfield(JTextField textfield) {
        this.textfield = textfield;
    }

    public JLabel getTitre() {
        return titre;
    }

    public void setTitre(JLabel titre) {
        this.titre = titre;
    }

    public JLabel getVariables() {
        return variables;
    }

    public void setVariables(JLabel variables) {
        this.variables = variables;
    }
}
