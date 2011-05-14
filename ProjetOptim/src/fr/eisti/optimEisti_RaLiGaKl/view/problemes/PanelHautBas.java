/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eisti.optimEisti_RaLiGaKl.view.problemes;

import fr.eisti.optimEisti_RaLiGaKl.model.BDDUtilisateur;
import fr.eisti.optimEisti_RaLiGaKl.model.Basculer;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
public class PanelHautBas extends JPanel implements MouseListener {

    private boolean devoilerSolution;
    private Image imageON;
    private Image imageOFF;
    private PanelProbleme panelProbleme;

    public PanelHautBas(PanelProbleme panelProbleme) {
        this.devoilerSolution = false;
        this.addMouseListener(this);
        this.panelProbleme = panelProbleme;
        this.setOpaque(false);
        try {
            imageON = ImageIO.read(new File("images/button-vert.png"));
            imageOFF = ImageIO.read(new File("images/button-rouge.png"));
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de l'image");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if ((panelProbleme.getHauteur() == 0 || panelProbleme.getHauteur() == -270) && panelProbleme.verifier()) {
            this.devoilerSolution = !this.devoilerSolution;
            Thread basculer = new Thread(new Basculer(this.devoilerSolution));
            basculer.start();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if (this.devoilerSolution) {
            g.drawImage(imageOFF, 0, 0, this.getWidth(), this.getHeight(), this);
        } else {
            g.drawImage(imageON, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (this.devoilerSolution) {
            this.setToolTipText("Masquer la solution du problème");
        } else {
            this.setToolTipText("Afficher la solution du problème");
        }
        Cursor main = new Cursor(Cursor.HAND_CURSOR);
        setCursor(main);

    }

    @Override
    public void mouseExited(MouseEvent e) {
        Cursor normal = new Cursor(Cursor.DEFAULT_CURSOR);
        setCursor(normal);
    }
}
