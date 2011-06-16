
package projetfinanee;

public class Lieu {

    private int idLieu;
    private String pays;
    private String ville;
    private String adresse;
    private int numero;

    public Lieu(int idLieu, String pays, String ville, String adresse, int numero) {
        this.idLieu = idLieu;
        this.pays = pays;
        this.ville = ville;
        this.adresse = adresse;
        this.numero = numero;
    }

    public Lieu() {
        this.idLieu = 0;
        this.pays = "";
        this.ville = "";
        this.adresse = "";
        this.numero = 1;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getIdLieu() {
        return idLieu;
    }

    public int getNumero() {
        return numero;
    }

    public String getPays() {
        return pays;
    }

    public String getVille() {
        return ville;
    }

    @Override
    public String toString() {
        return "Lieu{n°"+this.numero+" rue "+this.adresse+" à "+this.ville+" en "+this.pays+"}";
    }




}
