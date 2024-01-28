package domain_layer;

public class Esercizio {
    private String nome;
    private String descrizione;

    public Esercizio(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
    }

    // ******* getters semplici *******
    public String getNome() {
        return this.nome;
    }

    public String getDescrizione() {
        return this.descrizione;
    }
}
