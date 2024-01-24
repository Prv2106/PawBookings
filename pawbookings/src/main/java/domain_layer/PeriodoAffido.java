package domain_layer;

import java.time.LocalDate;
import java.util.LinkedList;

public class PeriodoAffido {
    // attributi
    private final int codice;
    private final LocalDate dataInizio;
    private final LocalDate dataFine;
    private final float costo;
    private int numPosti; // derivato, sono i posti disponibili
    private int CapienzaMassima;

    // riferimenti
    private LinkedList<Cane> elencoCaniAffido;

    public PeriodoAffido(int codice, LocalDate dataInizio, LocalDate dataFine, float costo) {
        this.codice = codice;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.costo = costo;
        this.CapienzaMassima = 5;
        this.numPosti = this.CapienzaMassima;
        this.elencoCaniAffido = new LinkedList<>();
    }

    public boolean registraAffido(Cane cn) {
        if (this.elencoCaniAffido.add(cn)) {
            aggiornaNumeroPosti();
            return true;
        } else 
            return false;
    }    

    public boolean concludiAffido(Cane cn) {
        if (this.elencoCaniAffido.remove(cn))
            return true;
        else 
            return false;        
    }

    public void aggiornaNumeroPosti() {
        this.numPosti = this.CapienzaMassima - this.elencoCaniAffido.size();
    }

    public int getNumeroPosti(){
        return this.numPosti;
    }

    public LocalDate getDataInizio() {
        return this.dataInizio;
    }

    public LocalDate getDataFine() {
        return this.dataFine;
    }

    /**** metodi per i test ****/

    public LinkedList<Cane> getCaniAffido(){
        return this.elencoCaniAffido;
    }

    public int getCodice() {
        return this.codice;
    }
}