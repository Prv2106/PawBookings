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

/**
 * Represents a foster-care period.
 * Tracks capacity, registered dogs, health-status notifications, and the
 * applicable discount policy.
 */
public class PeriodoAffido extends Observable {

    private final int codice;
    private final LocalDate dataInizio;
    private final LocalDate dataFine;
    private final float costo;
    private int numPosti;
    private int CapienzaMassima;
    Map<Integer, String> mappaStatoSalute;

    private LinkedList<Cane> elencoCaniAffido;
    private PoliticaScontoAffidoFactory PAF;

    /**
     * Creates a foster period.
     *
     * @param codice unique period code
     * @param dataInizio first day of the period
     * @param dataFine last day of the period
     * @param costo full price of the period
     */
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

    /**
     * Registers a dog in the period and updates the available places.
     *
     * @param cn dog to register
     * @return {@code true} when the dog was added
     */
    public boolean registraAffido(Cane cn) {
        if (this.elencoCaniAffido.add(cn)) {
            aggiornaNumeroPosti();
            return true;
        } else
            return false;
    }

    /**
     * Concludes a dog's stay in the period.
     *
     * @param cn dog to remove
     * @return {@code true} when the dog was removed
     */
    public boolean concludiAffido(Cane cn) {
        if (this.elencoCaniAffido.remove(cn))
            return true;
        else
            return false;
    }

    /**
     * Recalculates the number of available places.
     */
    public void aggiornaNumeroPosti() {
        this.numPosti = this.CapienzaMassima - this.elencoCaniAffido.size();
    }

    /**
     * Checks whether any of the customer's dogs is registered in the period.
     *
     * @param cl customer to inspect
     * @return {@code true} when at least one dog is registered
     */
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

    /**
     * Publishes a health-status update to the registered observers.
     *
     * @param mappaStatoSalute map of dog codes to health-status messages
     */
    public void aggiornaStatoSalute(Map<Integer, String> mappaStatoSalute) {
        setMappaStatoSalute(mappaStatoSalute);
        notifyObservers();
    }

    /**
     * Sets the health-status map.
     *
     * @param mappaStatoSalute map to store, or {@code null} to clear it
     * @return {@code true} when a non-null map was set
     */
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

    /**
     * Returns the current health-status map.
     *
     * @return health-status map
     */
    public Map<Integer, String> getState(){
        return this.mappaStatoSalute;
    }

    /**
     * Returns the number of available places.
     *
     * @return available places
     */
    public int getNumeroPosti(){
        return this.numPosti;
    }

    /**
     * Returns the first day of the period.
     *
     * @return start date
     */
    public LocalDate getDataInizio() {
        return this.dataInizio;
    }

    /**
     * Returns the last day of the period.
     *
     * @return end date
     */
    public LocalDate getDataFine() {
        return this.dataFine;
    }

    /**
     * Returns the full price of the period.
     *
     * @return period price
     */
    public float getCosto() {
        return this.costo;
    }

    /**
     * Returns the dogs registered in the period.
     *
     * @return registered dogs
     */
    public LinkedList<Cane> getCaniAffido(){
        return this.elencoCaniAffido;
    }

    /**
     * Returns the period code.
     *
     * @return period code
     */
    public int getCodice() {
        return this.codice;
    }

    /**
     * Returns the maximum capacity.
     *
     * @return maximum number of dogs
     */
    public int getCapienzaMassima() {
        return this.CapienzaMassima;
    }

    /**
     * Returns the registered observers.
     *
     * @return observer list
     */
    public List<Observer> getObservers() {
        return this.observers;
    }

    /**
     * Returns the health-status map.
     *
     * @return health-status map
     */
    public Map<Integer, String> getMappaStatoSalute(){
        return this.mappaStatoSalute;
    }

    /**
     * Sets the number of available places.
     *
     * @param val new value
     */
    public void setNumPosti(int val){
        this.numPosti = val;
    }

    /**
     * Calculates the amount due using a foster-care price policy.
     *
     * @param numCaniAffido number of dogs in foster care for the customer
     * @param tipoPolitica policy name
     * @return amount due
     */
    public float calcolaImportoDovuto(int numCaniAffido, String tipoPolitica){
        IpoliticaScontoAffido p=PAF.getPoliticaScontoAffido(numCaniAffido, tipoPolitica);
        return p.getPrezzo(this, numCaniAffido);
    }

    /**
     * Checks whether the pickup qualifies as early.
     *
     * @return {@code true} when the current date is more than six days before the
     *         end of the period, which corresponds to at least one week
     */
    public Boolean verificaAnticipo(){
        return (LocalDate.now().isBefore(dataFine.minusDays(6)));
    }

}
