package fr.eisti.optimEisti_RaLiGaKl.model;

import java.util.ArrayList;

/**
 * La classe permettant de gerer les contraintes d'un probleme
 * @author Razavet Maël, Lion Cédric, Klelifa Sarah, Gallet Meriadec
 */
public class Contrainte {

    private ArrayList<Double> coeffVariables;
    private String inegalite;
    private double constante;

    @Override
    public String toString() {
        return "Contrainte{" + "coeffVariables=" + coeffVariables + "inegalite=" + inegalite + "constante=" + constante + '}';
    }
    
   /**
    * constructeur
    * @param coeffVariables les coeficients des variables
    * @param inegalite le type de contrainte
    * @param constante la valeur de la constante
    */
    public Contrainte(ArrayList<Double> coeffVariables, String inegalite, double constante){
        this.coeffVariables = coeffVariables;
        this.inegalite = inegalite;
        this.constante = constante;
    }
/**
 * constructeur
 */
    public Contrainte(){
        this.coeffVariables = new ArrayList<Double>();
    }
    /**
     * @return the coeffVariables
     */
    public ArrayList<Double> getCoeffVariables() {
        return coeffVariables;
    }

    /**
     * @param coeffVariables the coeffVariables to set
     */
    public void setCoeffVariables(ArrayList<Double> coeffVariables) {
        this.coeffVariables = coeffVariables;
    }

    /**
     * @return the inegalite
     */
    public String getInegalite() {
        return inegalite;
    }

    /**
     * @param inegalite the inegalite to set
     */
    public void setInegalite(String inegalite) {
        this.inegalite = inegalite;
    }

    /**
     * @return the constante
     */
    public double getConstante() {
        return constante;
    }

    /**
     * @param constante the constante to set
     */
    public void setConstante(double constante) {
        this.constante = constante;
    }
    
}
