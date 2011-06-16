package projetfinanee;

public class AreteValuee {

    private Personne p1;
    private Personne p2;
    private int evaluation;

    public AreteValuee(Personne p1, Personne p2, int evaluation) throws Exception {
        if (evaluation > 100 || evaluation < 0) {
            throw new Exception("une liason entre deux personnes doit etre entier et comprise entre 0 et 100");
        } else {
            this.p1 = p1;
            this.p2 = p2;
            this.evaluation = evaluation;
        }
    }

    public int getEvaluation() {
        return evaluation;
    }

    public Personne getP1() {
        return p1;
    }

    public Personne getP2() {
        return p2;
    }

    public void setEvaluation(int evaluation) throws Exception   {
        if (evaluation > 100 || evaluation < 0) {
            throw new Exception("une liason entre deux personnes doit etre entier et comprise entre 0 et 100");
        }
        this.evaluation = evaluation;
    }

    @Override
    public String toString() {
        return "AreteValuee{p1=" + this.p1 + ", p2=" + this.p2 + ", valeur=" + this.evaluation + "}";
    }
}
