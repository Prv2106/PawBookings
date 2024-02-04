package domain_layer;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Map;

import domain_layer.Cane;
import domain_layer.Cliente;
import domain_layer.Observable;
import domain_layer.Observer;
import domain_layer.gestione_politiche_sconto_affido.IpoliticaScontoAffido;
import domain_layer.gestione_politiche_sconto_affido.PoliticaScontoAffidoFactory;

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
    private PoliticaScontoAffidoFactory PAF;

    public PeriodoAffido(int codice, LocalDate dataInizio, LocalDate dataFine, float costo) {
        this.codice = codice;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.costo = costo;
        this.CapienzaMassima = 5;
        this.numPosti = this.CapienzaMassima;
        this.elencoCaniAffido = new LinkedList<>();
        this.PAF = PoliticaScontoAffidoFactory.getInstance();
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

    public void setNumPosti(int val){
        this.numPosti = val;
    }


    public float calcolaImportoDovuto(int numCaniAffido, String tipoPolitica){
        IpoliticaScontoAffido p=PAF.getPoliticaScontoAffido(numCaniAffido, tipoPolitica);
        return p.getPrezzo(this, numCaniAffido);
    }

    public Boolean verificaAnticipo(){
        return (LocalDate.now().isBefore(dataFine.minusDays(6)));
    }

}