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

    public Corso(int capienza) { 
        this.capienza = capienza;
        this.elencoCaniIscritti = new ArrayList<Cane>();
    }

    public void confermaIscrizione(Cane cn) {
        this.elencoCaniIscritti.add(cn);
        aggiornaCapienza();
    }

    public void aggiornaCapienza() {
        this.capienza--;
    }

    public void aggiornaCaniIscritti(Cane cn) {
        this.elencoCaniIscritti.remove(cn);
    }

    public int getCapienza() {
        return this.capienza;
    }

    public String getTipoCorso() {
        return this.tipoCorso;
    }

    public float getCosto() {
        return this.costo;
    }


}
