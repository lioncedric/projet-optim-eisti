/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eisti.OptimEisti.Controler;

import fr.eisti.OptimEisti.View.Compte.JPanelFondCreerCompte;
import fr.eisti.OptimEisti.View.Compte.JPanelFondGestionProfil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
public class FileChooserListener implements ActionListener {

    private JPanel pan;

    public FileChooserListener(JPanel p) {
        this.pan = p;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(this.pan);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                String nameFile = file.getAbsolutePath();
                if (this.pan instanceof JPanelFondCreerCompte) {
                    ((JPanelFondCreerCompte) this.pan).getJtfAvatar().setText(nameFile);
                }
                else if(this.pan instanceof JPanelFondGestionProfil){
                    ((JPanelFondGestionProfil) this.pan).getJtfAvatar().setText(nameFile);
                }
            }
        } catch (Exception ex) {
        }
    }
}
