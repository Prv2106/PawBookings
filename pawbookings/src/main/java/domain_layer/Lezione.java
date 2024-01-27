package domain_layer;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;



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
        this.elencoTurniDisponibili = new LinkedList<>();
    }

    // Metodo per aggiornare l'elenco dei turni disponibili
    public void aggiornaTurniDisponibili(Turno ts) {
        // Rimozione del turno specificato dall'elenco dei turni disponibili
        this.elencoTurniDisponibili.remove(ts);
    }

    // Metodo per ottenere l'elenco dei turni disponibili
    public LinkedList<Turno> getTurniDisponibili() {
        // Restituzione dell'elenco dei turni disponibili
        return this.elencoTurniDisponibili;
    }

    public String getNome() {
        return this.nome;
    }


}





