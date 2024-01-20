package domain_layer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


// Definizione della classe Lezione
public class Lezione {
    // Dichiarazione dei campi privati
    private int codiceLezione;
    private String nome;
    private LinkedList<Turno> elencoTurniDisponibili;

    // Costruttore della classe Lezione
    public Lezione(int codiceLezione, String nome) {
        // Inizializzazione dei campi con i valori passati come parametri
        this.codiceLezione = codiceLezione;
        this.nome = nome;
        // Inizializzazione dell'elenco dei turni disponibili come un nuovo ArrayList
        this.elencoTurniDisponibili = new LinkedList<>();
    }

    // Metodo per aggiornare l'elenco dei turni disponibili
    public void aggiornaTurniDisponibili(Turno ts) {
        // Rimozione del turno specificato dall'elenco dei turni disponibili
        this.elencoTurniDisponibili.remove(ts);
    }

    // Metodo per ottenere l'elenco dei turni disponibili
    public LinkedList<Turno> getelencoTurniDisponibili() {
        // Restituzione dell'elenco dei turni disponibili
        return this.elencoTurniDisponibili;
    }

   

}
