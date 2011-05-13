/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eisti.optimEisti_RaLiGaKl.view.problemes;

import fr.eisti.optimEisti_RaLiGaKl.model.Basculer;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
public class PanelHautBas extends JPanel implements MouseListener {

    private boolean position;
    private String NonImageHaut;
    private String NonImageBas;
    private String NonImageSelect;
    private PanelProbleme panelProbleme;

    public PanelHautBas(PanelProbleme panelProbleme) {
        position = false;
        this.setBackground(Color.red);
        this.addMouseListener(this);
        this.panelProbleme = panelProbleme;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if ((panelProbleme.getHauteur() == 0 || panelProbleme.getHauteur() == -270)&& panelProbleme.verifier()) {
            position = !position;
            Thread basculer = new Thread(new Basculer(position));

            basculer.start();
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
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
