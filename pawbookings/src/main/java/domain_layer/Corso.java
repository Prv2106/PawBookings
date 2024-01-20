package domain_layer;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Corso {
    private int codice;
    private int capienza;
    private float costo;
    private String tipoCorso;
    private List<Cane> elencoCaniIscritti;
    private LinkedList<Lezione> programma;

    //Costruttore dell classe Corso
    public Corso(int codice, int capienza, float costo, String tipoCorso) { //La capienza viene passata da PawBookings
        this.codice = codice;
        this.capienza = capienza;
        this.costo = costo;
        this.tipoCorso = tipoCorso;
        // Inizializzazione dell'elenco dei cani iscritti come un nuovo ArrayList
        this.elencoCaniIscritti = new ArrayList<>();
        // Inizializzazione del programma del corso come un nuovo LinkedList
        this.programma = new LinkedList<>();
    }

    // Metodo per confermare l'iscrizione di un cane e aggiornare la capienza
    public void confermaIscrizione(Cane cn) {
        this.elencoCaniIscritti.add(cn);
        aggiornaCapienza();
    }

    // Metodo per aggiornare la capienza del corso
    public void aggiornaCapienza() {
        this.capienza--;
    }

    // Metodo per ottenere la capienza del corso
    public int getCapienza() {
        return this.capienza;
    }

    // Metodo per aggiornare l'elenco dei cani iscritti
    public void aggiornaCaniIscritti(Cane cn) {
        this.elencoCaniIscritti.remove(cn);
    }

    public abstract void loadLezioni();

    
}
