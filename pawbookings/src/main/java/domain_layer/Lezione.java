package domain_layer;


import java.time.LocalDate;
import java.time.LocalTime;
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
        // Inizializzazione dell'elenco dei turni disponibili come un nuovo ArrayList
        this.elencoTurniDisponibili = new LinkedList<>();
        this.loadTurni();
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



    private void  loadTurni(){

        Turno t1 = new Turno(LocalDate.of(2024, 1, 20), LocalTime.of(9, 0), LocalTime.of(10, 0));
        Turno t2 = new Turno(LocalDate.of(2024, 1, 20), LocalTime.of(15, 0), LocalTime.of(16, 0));
        Turno t3 = new Turno(LocalDate.of(2024, 1, 20), LocalTime.of(18, 0), LocalTime.of(19, 0));
        
      
        this.elencoTurniDisponibili.add(t1);
        this.elencoTurniDisponibili.add(t2);
        this.elencoTurniDisponibili.add(t3);

        
    }



}
