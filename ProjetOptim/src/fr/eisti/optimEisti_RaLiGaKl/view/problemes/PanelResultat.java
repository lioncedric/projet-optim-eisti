/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eisti.optimEisti_RaLiGaKl.view.problemes;

import fr.eisti.optimEisti_RaLiGaKl.model.Probleme;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.border.BevelBorder;

/**
 *
 * @author Administrator
 */
public class PanelResultat extends JPanel {

    private JTextArea outpout;
    private JList res;
    private PanelProbleme panelProbleme;
    private JLabel texteres;
    private DefaultListModel listModel;

    public PanelResultat(PanelProbleme panelProbleme) {
        this.listModel = new DefaultListModel();
        this.res = new JList(listModel);
        outpout = new JTextArea();

        outpout.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        res.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        Probleme p = panelProbleme.getProbleme();
        if (!p.getResultat().isEmpty()) {
            listModel.addElement("y = " + p.getResultat().get(0));

            for (int i = 1; i < p.getResultat().size(); i++) {
                listModel.addElement("x" + i + " = " + p.getResultat().get(i));
            }
        }
        texteres = new JLabel("Resultat :");
        this.add(texteres);
        this.panelProbleme = panelProbleme;
        this.add(outpout);
        this.add(res);
        this.setBackground(new Color(255, 255, 255, 255));
    }

    @Override
    public void paintComponent(Graphics g) {

        texteres.setBounds(this.getWidth() * 10 / 100, this.getHeight() * 5 / 100, this.getWidth() * 20 / 100, this.getHeight() * 15 / 100);
        outpout.setBounds(this.getWidth() * 5 / 100, this.getHeight() * 20 / 100, this.getWidth() * 40 / 100, this.getHeight() * 60 / 100);
        res.setBounds(this.getWidth() * 55 / 100, this.getHeight() * 20 / 100, this.getWidth() * 40 / 100, this.getHeight() * 60 / 100);
        this.updateUI();
    }
}
