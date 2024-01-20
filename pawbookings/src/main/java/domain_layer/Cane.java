package domain_layer;

public class Cane {
    private int codiceCane;
    private String nome;
    private String razza;
    private boolean attualmenteIscritto;

    public Cane(int codiceCane, String nome, String razza, boolean attualmenteIscritto) {
        this.codiceCane = codiceCane;
        this.nome = nome;
        this.razza = razza;
        this.attualmenteIscritto = attualmenteIscritto;
    }

    

    public String getNome() {
        return this.nome;
    }

    public String getRazza() {
        return this.razza;
    }

}
