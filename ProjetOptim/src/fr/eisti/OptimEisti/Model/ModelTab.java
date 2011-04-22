package fr.eisti.OptimEisti.Model;

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
         * 
         * @param data
         * @param title
         */
	public ModelTab(Object[][] data, String[] title){
		this.data=data;
		this.title=title;
	}


        public Object[][] getData(){
            return data;
        }

        public String[] getTitle(){
            return this.title;
        }

        public void setData(Object[][] data){
            this.data = data;
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
            //Ce qui permet une mise à jours complète du tableau
        }catch(Exception e){
        }
	    this.fireTableDataChanged();
    }

	
	/**
	* Retourne le titre de la colonne � l'indice sp�cif�
	*/
	public String getColumnName(int col) {
	  return this.title[col];
	}
	
         @Override
	public Class getColumnClass(int col){
		//On retourne le type de la cellule � la colonne demand�e
		//On se moque de la ligne puisque les donn�es sur chaque ligne sont les m�mes
		//On choisit donc la premi�re ligne
                try{
                    return this.data[0][col].getClass();
                }catch(Exception e){
                    this.data[0][col] = 0;
                }
                 return this.data[0][col].getClass();
	}

	/**
		 * Méthode permettant de retirer une ligne du tableau
		 * @param position
		 */
		public void removeRow(int position){

			int indice = 0, indice2 = 0, nbRow = this.getRowCount()-1, nbCol = this.getColumnCount();
			Object temp[][] = new Object[nbRow][nbCol];
                        

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

	
	public void addRow(Object[] data){
		int indice = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();
		
		Object temp[][] = this.data;
		this.data = new Object[nbRow+1][nbCol];
		
		for(Object[] value : temp)
			this.data[indice++] = value;
		
			
		this.data[indice] = data;
		temp = null;
		//Cette m�thode permet d'avertir le tableau que les donn�es ont �t� modifi�es
		//Ce qui permet une mise � jours compl�te du tableau
		this.fireTableDataChanged();
	}

            
	public boolean isCellEditable(int row, int col){
		return true; 
	}



}
