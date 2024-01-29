package domain_layer;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Map;

import java.util.List;


public class PeriodoAffido extends Observable {
    // attributi
    private final int codice;
    private final LocalDate dataInizio;
    private final LocalDate dataFine;
    private final float costo;
    private int numPosti; // derivato, sono i posti disponibili
    private int CapienzaMassima;
    Map<Integer, String> mappaStatoSalute;

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

    public boolean verificaIscrizione(Cliente cl) {
        for(Cane c: cl.getCani()) {
            for(Cane cpa: this.elencoCaniAffido){
                if(c.equals(cpa)){
                    return true;
                }
            } 
        }
        return false;
    }

    public void aggiornaStatoSalute(Map<Integer, String> mappaStatoSalute) {
        setMappaStatoSalute(mappaStatoSalute);
        notifyObservers();
    }

    public boolean setMappaStatoSalute(Map<Integer, String> mappaStatoSalute){
        if (mappaStatoSalute != null) {
            this.mappaStatoSalute = mappaStatoSalute;
            return true;
        }
         else {
            this.mappaStatoSalute = null;
            return false;
        }
    }

    public Map<Integer, String> getState(){
        return this.mappaStatoSalute;
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

    public float getCosto() {
        return this.costo;
    }

    /**** metodi per i test ****/

    public LinkedList<Cane> getCaniAffido(){
        return this.elencoCaniAffido;
    }

    public int getCodice() {
        return this.codice;
    }

    public int getCapienzaMassima() {
        return this.CapienzaMassima;
    }

    public List<Observer> getObservers() {
        return this.observers;
    }
    
    public Map<Integer, String> getMappaStatoSalute(){
        return this.mappaStatoSalute;
    }
}