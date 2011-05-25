/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.eisti.optimEisti_RaLiGaKl.model;

import java.io.File;
import javax.swing.filechooser.FileFilter;
/**
 * Classe pour ajouter des types de fichier(ici utilisée pour .csv et .sci)
  * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Meriadec
 */

public class FiltreSimple extends   FileFilter{
   //Description et extension acceptée par le filtre
   private String description;
   private String extension;
   /**
    * Constructeur à partir de la description et de l'extension acceptée
    * @param description
    * @param extension
    */
   public FiltreSimple(String description, String extension){
      if(description == null || extension ==null){
         throw new NullPointerException("La description (ou extension) ne peut être null.");
      }
      this.description = description;
      this.extension = extension;
   }
   /**
    * regarde si un fichier valide un filtre
    * @param file le fichier
    * @return si le fichier est valide
    */
    @Override
   public boolean accept(File file){
      if(file.isDirectory()) {
         return true;
      }
      String nomFichier = file.getName().toLowerCase();

      return nomFichier.endsWith(extension);
   }
   /**
    * Description du filtre
    * @return le description
    */
    @Override
      public String getDescription(){
      return description;
   }
}