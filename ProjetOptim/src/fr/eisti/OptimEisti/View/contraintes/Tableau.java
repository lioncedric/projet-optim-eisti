package fr.eisti.OptimEisti.View.contraintes;

import fr.eisti.OptimEisti.Controler.ButtonEditor;
import fr.eisti.OptimEisti.Controler.SaveListener;
import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.table.TableColumn;
import fr.eisti.OptimEisti.Model.Contrainte;
import java.util.ArrayList;

public class Tableau extends JPanel{

	private JTable table;
	private String[] title;
        private Object[][] data;
        private int nbVariable;

        /**
         * Constructeur d'un panel contenant un tableau
         * @param f : fenetre qui contient le tableau
         * @param nbVariable : nb de variable voulu
         */
	public Tableau(int nbVariable){
         
                this.nbVariable = nbVariable;
		//on initialise le titre et le contenu du tableau
                title = new String[nbVariable+3];
		data = new Object[1][nbVariable+3];

                //on rempli le contenu du tableau
		for(int i=0; i<title.length; i++){
			title[i] = "x"+i;
			data[0][i]=0;
		}
		title[nbVariable] = "opérateur";
		data[0][nbVariable]=null;
		title[nbVariable+1] = "b";
		data[0][nbVariable+1]=0;
		title[nbVariable+2] = "";
		data[0][nbVariable+2]=null;
		//on appel la fonction d'initialisation du panel contenant un tableau
                init(data,title,nbVariable);
	}
                /**
                 * Constructeur d'un Panel contenant un Tableau
                 * @param f : la fenetre contenant le panel
                 * @param nbLigne : le nombre de ligne que le tableau doit contenir
                 * @param nbVariable : le nombre de variable comprise dans le tableau
                 */
        	public Tableau(int nbLigne,int nbVariable){
                      this.nbVariable = nbVariable;
                    //si on appel le constructeur avec un nombre de ligne égale à zéro, on en met au moins une
                    if(nbLigne==0){
                        nbLigne=1;
                    }
                    //on initialise le titre et le contenu du tableau
                    title = new String[nbVariable+3];
                    data = new Object[nbLigne][nbVariable+3];
                    //on rempli le contenu du tableau
                    for(int j=0;j<nbLigne;j++){
                        for(int i=0; i<title.length; i++){
                                title[i] = "x"+i;
                                data[j][i]=0;
                        }
                        title[nbVariable] = "opérateur";
                        data[j][nbVariable]=null;
                        title[nbVariable+1] = "b";
                        data[j][nbVariable+1]=0;
                        title[nbVariable+2] = "";
                        data[j][nbVariable+2]=null;
                    }

                    init(data,title,nbVariable);
                }

                /**
                 * Fonction d'initialisation du tableau et du panel
                 * @param f
                 * @param data
                 * @param title
                 * @param nbVariable
                 */
                public void init(Object[][] data, String[] title, int nbVariable){
                    this.setLayout(new BorderLayout(2,1));
                    //on creer un model au tableau afin de le personaliser
                    ModelTab t = new ModelTab(data,title);
                    //on creer un tableau a partir de ce model
                    table = new JTable(t);
                    table.addKeyListener(new SaveListener());
                    table.addMouseListener(new SaveListener());
                    //on creer une JCombobox qui nous permettra de donner à l'utilisateur un choix restreint d'opérateur à choisir
                    JComboBox test = new JComboBox();

                    test.addItem("=");
                    test.addItem("<=");
                    test.addItem(">=");

                    //on instancie deux renderer, un pour la combobox et un pour le bouton afin qu'il soit actif dans le tableau
                    ComboBoxRenderer cbr = new ComboBoxRenderer();
                    JButtonRenderer chbr = new JButtonRenderer();

                    //on récupère la colonne contenant les opérateurs
                    TableColumn signe = this.table.getColumnModel().getColumn(nbVariable);
                    signe.setCellRenderer(cbr);
                    signe.setCellEditor(new DefaultCellEditor(test));
                    signe.setPreferredWidth(100);

                    //permet d'empecher le mouvement des colonnes
                    this.table.getTableHeader().setReorderingAllowed(false);
                    //on récupère la colonne contenant le bouton
                    TableColumn supprimerLigne =  this.table.getColumnModel().getColumn(nbVariable+2);
                    supprimerLigne.setCellRenderer(chbr);
                    supprimerLigne.setCellEditor(new ButtonEditor(new JCheckBox()));
                    supprimerLigne.setMaxWidth(10);
                    this.add(new JScrollPane(table),BorderLayout.CENTER);
                }

        public JTable getTable(){
            return this.table;
        }

        public void setTable(JTable newTable){
            this.table = newTable;
        }

        public ArrayList<Contrainte> enregistrerContraintes() {
            ArrayList<Contrainte> ListeContraintes = new ArrayList<Contrainte>();
            data = ((ModelTab)this.table.getModel()).getData();
            for (int i = 0; i < this.table.getRowCount(); i++) {
                if(this.table.getValueAt(i, this.table.getColumnCount() - 3) != null) {

                    Contrainte contrainte1 = new Contrainte();
                      if (this.data[i][this.table.getColumnCount() - 3].toString().equals("<=")) {
                        contrainte1.setInegalite("Infériorité");
                      }else if (data[i][this.table.getColumnCount() - 3].toString().equals(">=")) {
                        contrainte1.setInegalite("Supériorité");
                      }else{
                        contrainte1.setInegalite("Egalité");
                      }
                      contrainte1.setConstante(Double.parseDouble(data[i][this.table.getColumnCount() - 2].toString()));
                      if (this.table.getColumnCount() - 3 < this.nbVariable) {
                        for (int j = 0; j < this.nbVariable - 1; j++) {
                            contrainte1.getCoeffVariables().add(Double.parseDouble(data[i][j].toString()));
                        }
                      } else {
                        for (int j = 0; j <this.table.getColumnCount() - 3; j++) {
                            contrainte1.getCoeffVariables().add(Double.parseDouble(data[i][j].toString()));
                        }
                      }
                     ListeContraintes.add(contrainte1);
                }
            }
            return ListeContraintes;
        }

        public void rempliTableau(ArrayList<Contrainte> contraintes){

            for (int i = 0; i < contraintes.size(); i++) {
                    for (int j = 0; j < contraintes.get(i).getCoeffVariables().size(); j++) {
                        this.table.setValueAt(contraintes.get(i).getCoeffVariables().get(j), i, j);
                    }
                    if (contraintes.get(i).getInegalite().equals("Infériorité")) {
                        this.table.setValueAt("<=", i, this.table.getColumnCount() - 3);
                    } else if (contraintes.get(i).getInegalite().equals("Supériorité")) {
                        this.table.setValueAt(">=", i, this.table.getColumnCount() - 3);
                    } else {
                        this.table.setValueAt("=", i, this.table.getColumnCount() - 3);
                    }
                    this.table.setValueAt(contraintes.get(i).getConstante(), i, this.table.getColumnCount() - 2);
            }
        }

        public Boolean ligneRempli(){
            //on initialise un booleen qui va nous permettre de savoir si la ligne et bien rempli avant d'en ajouter une autre
            Boolean ligneRempli;
            ligneRempli = false;
            if(this.table.getRowCount()>0){
                //on va parcourir la dernière ligne du tableau afin de savoir si elle est bien rempli
                for(int i=0; i<nbVariable; i++){
                    //on récupère la valeur de chaque cellule correspondant aux variables
                    Object value = this.table.getValueAt(this.table.getRowCount()-1, i);
                    double valueString = Double.parseDouble(value.toString());
                    //on test si il y a au moins une cellule correspondant aux variables qui est différente de zéro et si le signe a bien été choisi
                    ligneRempli = ligneRempli || (!(valueString == 0) && this.table.getValueAt(this.table.getRowCount()-1, this.table.getColumnCount()-3)!=null);
                }
            }else{
                ligneRempli = true;
            }
            return ligneRempli;
        }



}
