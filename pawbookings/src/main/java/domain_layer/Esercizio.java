package domain_layer;

/**
 * Represents an exercise contained in a lesson.
 */
public class Esercizio {
    private String nome;
    private String descrizione;

    /**
     * Creates an exercise.
     *
     * @param nome exercise name
     * @param descrizione exercise description
     */
    public Esercizio(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
    }

    /**
     * Returns the exercise name.
     *
     * @return exercise name
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Returns the exercise description.
     *
     * @return exercise description
     */
    public String getDescrizione() {
        return this.descrizione;
    }
}
