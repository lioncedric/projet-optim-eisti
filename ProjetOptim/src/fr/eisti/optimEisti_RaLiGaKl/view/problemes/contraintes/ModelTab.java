package fr.eisti.optimEisti_RaLiGaKl.view.problemes.contraintes;

import javax.swing.table.AbstractTableModel;

/**
 * Classe permettant de gérer le modele d'un tableau
 *@author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Meriadec
 */
public class ModelTab extends AbstractTableModel{
	
	private static final long serialVersionUID = 1L;
	private Object[][] data;
	private String[] title;

        /**
         * Constructeur d'un Model de tableau
         * @param data : le tableau de donnée qui compose le Model
         * @param title : l'entete
         */
	public ModelTab(Object[][] data, String[] title){
		this.data=data;
		this.title=title;
	}

        /**
         * Fonction qui renvoit le tableau de donnée
         * @return le tableau de donnée
         */
        public Object[][] getData(){
            return data;
        }

        /**
         * Fonction qui renvoit l'entête du tableau
         * @return l'entête 
         */
        public String[] getTitle(){
            return this.title;
        }

        /**
         * Fonction qui permet de modifier le tableau de donnée
         * @param data : le nouveau tableau de donnée
         */
        public void setData(Object[][] data){
            this.data = data;
            //on indique au model que on a changé de tableau de donnée
            this.fireTableDataChanged();
            this.fireTableStructureChanged();
        }

        @Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.title.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.data.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return this.data[rowIndex][columnIndex];
	}
	
        @Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            try{
                this.data[rowIndex][columnIndex]=aValue;
              if(aValue==null){
                   this.data[rowIndex][columnIndex]=0;
              }
                //Ce qui permet une mise à jours complète du tableau
            }catch(Exception e){
            }
                this.fireTableDataChanged();
        }

        @Override
	public String getColumnName(int col) {
	  return this.title[col];
	}
	
        @Override
	public Class getColumnClass(int col){
		//On retourne le type de la cellule de la colonne demandée
		//On se moque de la ligne puisque les donn�es sur chaque ligne sont les m�mes
		//On choisit donc la premi�re ligne
                 return this.data[0][col].getClass();
	}

	/**
        * Méthode permettant de retirer une ligne du tableau
        * @param position : positon de la ligne à supprimer
        */
	public void removeRow(int position){
                //On creer un tableau de donnée plus petit que l'ancien
        	int indice = 0, indice2 = 0, nbRow = this.getRowCount()-1, nbCol = this.getColumnCount();
		Object temp[][] = new Object[nbRow][nbCol];

                //le nouveau tableau prend toutes les lignes de l'ancien sauf celle que l'on veut supprimer
                for(Object[] value : this.data){
			if(indice != position){
				temp[indice2++] = value;
			}
			indice++;
		}
		this.data = temp;
		temp = null;
		//Cette méthode permet d'avertir le tableau que les données ont été modifiées
		//Ce qui permet une mise à jours complète du tableau
		this.fireTableDataChanged();
        }

	/**
         * Fonction qui permet d'ajouter une ligne
         * @param data : les données de la nouvelle ligne à ajouter
         */
	public void addRow(Object[] data){
		int indice = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();
		//on creer un tableau de donnée plus grand que celui d'origine
		Object temp[][] = this.data;
		this.data = new Object[nbRow+1][nbCol];
		//on lui met les valeurs de l'ancien tableau de donnée
		for(Object[] value : temp)
			this.data[indice++] = value;

                //on ajoute notre nouvelle ligne au nouveau tableau donnée
		this.data[indice] = data;
		temp = null;
		//Cette m�thode permet d'avertir le tableau que les donn�es ont �t� modifi�es
		//Ce qui permet une mise � jours compl�te du tableau
		this.fireTableDataChanged();
	}

        @Override
	public boolean isCellEditable(int row, int col){
		return true; 
	}



}
