/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eisti.OptimEisti.View;

import fr.eisti.OptimEisti.Controler.PanelRechercheProblemeListener;
import fr.eisti.OptimEisti.Main;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Administrator
 */
public class PanelRechercheProbleme extends JPanel {

    private JTextField jtfRecherche;
    private Image loupe;

    public PanelRechercheProbleme() {
        initialiserVariables();
        recupererImage();
        traitement();
    }

    public void initialiserVariables() {
        this.jtfRecherche = new JTextField("Rechercher ...");
    }

    public void recupererImage() {
        try {
            this.loupe = ImageIO.read(new File("images/loupe.png"));
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de l'image");
        }
    }

    public void traitement() {
        this.setPreferredSize(new Dimension(100,25));
        this.setLayout(null);
        this.add(this.jtfRecherche);
        this.jtfRecherche.addKeyListener(new PanelRechercheProblemeListener(this));
        this.jtfRecherche.addMouseListener(new PanelRechercheProblemeListener(this));
    }

    /**
     * Redefinition de la methode paintComponent
     */
    @Override
    public void paintComponent(Graphics g) {
        this.jtfRecherche.setBounds(32, 0, Main.fenetrePrincipale.getGauche().getWidth()-33, 25);
        //on dessine l'image sur le panel
        g.drawImage(loupe,5, 0, this);
    }

    public JTextField getJtfRecherche() {
        return jtfRecherche;
    }
    
    
}
