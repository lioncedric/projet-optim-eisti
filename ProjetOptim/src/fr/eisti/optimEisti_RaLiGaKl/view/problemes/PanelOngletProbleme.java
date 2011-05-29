package fr.eisti.optimEisti_RaLiGaKl.view.problemes;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;

/**
 * Classe permettant de créer l'onglet assosié a chaque probleme
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Mériadec
 */
public class PanelOngletProbleme extends JPanel {

    private final JTabbedPane pane;
    SaveButton save;

    public SaveButton getSave() {
        return save;
    }

    /**
     * Le constructeur
     * @param pane le gestionnaire d'onglet
     */
    public PanelOngletProbleme(final JTabbedPane pane) {
        //unset default FlowLayout' gaps
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));

        if (pane == null) {
            throw new NullPointerException("TabbedPane is null");
        }
        this.pane = pane;
        setOpaque(false);
        save = new SaveButton();
        add(save);
        //make JLabel read titles from JTabbedPane
        JLabel label = new JLabel() {

            @Override
            public String getText() {
                int i = pane.indexOfTabComponent(PanelOngletProbleme.this);
                if (i != -1) {
                    return pane.getTitleAt(i);
                }
                return null;
            }
        };

        add(label);
        //add more space between the label and the button
        label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        //tab button
        JButton button = new TabButton();
        add(button);
        //add more space to the top of the component
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }

    /**
     * classe qui met en place une disquette pour sauvegarder
     */
    public class SaveButton extends JButton implements ActionListener, MouseListener {

        /**
         * le constructeur
         */
        public SaveButton() {
            super();
            Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icone_sauvegardeR.png"));
            this.setIcon(new ImageIcon(img));


            setBorderPainted(false);

            //int size = 17;
            //setPreferredSize(new Dimension(size, size));
            setMargin(new Insets(0, 0, 0, 0));
            setToolTipText("sauver le probleme");
            //Make the button looks the same for all Laf's
            setUI(new BasicButtonUI());
            //Make it transparent
            setContentAreaFilled(false);
            //No need to be focusable
            setFocusable(false);
            //Close the proper tab by clicking the button
            addMouseListener(this);
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = pane.indexOfTabComponent(PanelOngletProbleme.this);
            if (i != -1) {
                ((PanelProbleme) pane.getComponentAt(i)).enregisrer(i);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icone_sauvegarde.png"));
            setIcon(new ImageIcon(img));

        }

        @Override
        public void mouseExited(MouseEvent e) {
            Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icone_sauvegardeR.png"));
            setIcon(new ImageIcon(img));
        }
    }

    /**
     * classe permetant d'ajouter une croix pour fermer l'onglet
     */
    private class TabButton extends JButton implements ActionListener {

        /**
         * Le constructeur
         */
        public TabButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("fermer l'onglet");
            //Make the button looks the same for all Laf's
            setUI(new BasicButtonUI());
            //Make it transparent
            setContentAreaFilled(false);
            //No need to be focusable
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            //Making nice rollover effect
            //we use the same listener for all buttons
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);
            //Close the proper tab by clicking the button
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = pane.indexOfTabComponent(PanelOngletProbleme.this);
            if (i != -1) {
                pane.remove(i);

            }
        }

        /**
         * desactivation de la methode update ui
         */
        @Override
        public void updateUI() {
        }

        /**
         * dessin de la croix
         * @param g le graphique
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            //shift the image for pressed buttons
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(2));
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
    }
    /**
     * ecouteur de souris pour la croix
     */
    private final static MouseListener buttonMouseListener = new MouseAdapter() {

        @Override
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };
}
