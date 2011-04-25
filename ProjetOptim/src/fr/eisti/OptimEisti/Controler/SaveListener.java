/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.eisti.OptimEisti.Controler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Administrator
 */
public class SaveListener implements ChangeListener,KeyListener{

    public void stateChanged(ChangeEvent e) {
      System.out.print("-/");
    }

    public void keyTyped(KeyEvent e) {
       System.out.print("-/");
    }

    public void keyPressed(KeyEvent e) {
        System.out.print("-/");
    }

    public void keyReleased(KeyEvent e) {
        System.out.print("-/");
    }

}
