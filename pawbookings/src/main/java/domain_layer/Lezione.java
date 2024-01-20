package domain_layer;

import java.util.ArrayList;
import java.util.List;

// Definizione della classe Lezione
public class Lezione {
    // Dichiarazione dei campi privati
    private int codiceLezione;
    private String nome;
    private List<Turno> elencoTurniDisponibili;

    // Costruttore della classe Lezione
    public Lezione(int codiceLezione, String nome) {
        // Inizializzazione dei campi con i valori passati come parametri
        this.codiceLezione = codiceLezione;
        this.nome = nome;
        // Inizializzazione dell'elenco dei turni disponibili come un nuovo ArrayList
        this.elencoTurniDisponibili = new ArrayList<>();
    }

    // Metodo per aggiornare l'elenco dei turni disponibili
    public void aggiornaelencoTurniDisponibili(Turno ts) {
        // Rimozione del turno specificato dall'elenco dei turni disponibili
        this.elencoTurniDisponibili.remove(ts);
    }

    // Metodo per ottenere l'elenco dei turni disponibili
    public List<Turno> getelencoTurniDisponibili() {
        // Restituzione dell'elenco dei turni disponibili
        return this.elencoTurniDisponibili;
    }

}
