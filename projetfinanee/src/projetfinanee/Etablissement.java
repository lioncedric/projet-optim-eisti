package projetfinanee;

public class Etablissement {

    //variables de la classe
    private int id;
    private String nom;
    private String description;
    private Lieu lieu;

    public Etablissement(int id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.lieu = new Lieu();
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public String getNom() {
        return nom;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    @Override
    public String toString() {
        return "Etablissement{" + "id=" + id + "nom=" + nom + "description=" + description + "Lieu=" + lieu + '}';
    }
    
}
