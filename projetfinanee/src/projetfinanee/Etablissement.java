package projetfinanee;

public class Etablissement {

    private int id;
    private String nom;
    private String description;

    public Etablissement(int id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Etablissement{" + "id=" + id + "nom=" + nom + "description=" + description + '}';
    }
    
}
