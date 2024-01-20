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

    public Corso(int capienza) { //la capienza viene passata da PawBookings
        this.capienza = capienza;
        this.elencoCaniIscritti = new ArrayList<>();
    }

    public void confermaIscrizione(Cane cn) {
        this.elencoCaniIscritti.add(cn);
        aggiornaCapienza();
    }

    public void aggiornaCapienza() {
        this.capienza--;
    }

    public int getCapienza() {
        return this.capienza;
    }

    public void aggiornaCaniIscritti(Cane cn) {
        this.elencoCaniIscritti.remove(cn);
    }
}
