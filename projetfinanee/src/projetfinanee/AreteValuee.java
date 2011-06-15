
package projetfinanee;


class AreteValuee {
    private Personne p1;
    private Personne p2;
    private int evaluation;

    public AreteValuee(Personne p1, Personne p2, int evaluation) {
        this.p1 = p1;
        this.p2 = p2;
        this.evaluation = evaluation;
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


}
