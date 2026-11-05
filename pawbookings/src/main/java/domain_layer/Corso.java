package domain_layer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import domain_layer.gestione_politiche_prezzo_corso.IpoliticaPrezzoCorso;
import domain_layer.gestione_politiche_prezzo_corso.PoliticaPrezzoCorsoFactory;

/**
 * Represents a training course.
 * Manages capacity, enrolled dogs, the lesson programme, and the selection of
 * the applicable price policy.
 */
public class Corso {
    private int codice;
    private int capienza;
    private float costo;
    private String tipoCorso;
    private int numTurni;

    private LinkedList<Cane> elencoCaniIscritti;
    private LinkedList<Lezione> programma;
    private Lezione lezioneCorrente;
    private Lezione lezioneSelezionata;
    private PoliticaPrezzoCorsoFactory PCF;

    /**
     * Creates a course.
     *
     * @param codice unique course code
     * @param capienza maximum number of enrolled dogs
     * @param costo course price
     * @param tipoCorso descriptive course type
     */
    public Corso(int codice, int capienza, float costo, String tipoCorso) {
        this.codice = codice;
        this.capienza = capienza;
        this.costo = costo;
        this.tipoCorso = tipoCorso;
        this.elencoCaniIscritti = new LinkedList<>();
        this.programma = new LinkedList<>();
        this.PCF = PoliticaPrezzoCorsoFactory.getInstance();

    }

    /**
     * Enrolls a dog and decreases the available capacity.
     *
     * @param cn dog to enroll
     */
    public void confermaIscrizione(Cane cn) {
        this.elencoCaniIscritti.add(cn);
        aggiornaCapienza();
    }

    /**
     * Decreases the remaining capacity by one place.
     */
    public void aggiornaCapienza() {
        this.capienza--;
    }

    /**
     * Removes a dog from the enrolled list after course completion.
     *
     * @param cn dog to remove
     */
    public void aggiornaCaniIscritti(Cane cn) {
        this.elencoCaniIscritti.remove(cn);
    }

    /**
     * Returns the remaining capacity.
     *
     * @return available places
     */
    public int getCapienza() {
        return this.capienza;
    }

    /**
     * Returns the course price.
     *
     * @return course price
     */
    public float getCosto() {
        return this.costo;
    }

    /**
     * Returns the course code.
     *
     * @return course code
     */
    public int getCodice() {
        return this.codice;
    }

    /**
     * Returns the course type.
     *
     * @return course type
     */
    public String getTipoCorso() {
        return this.tipoCorso;
    }

    /**
     * Returns the dogs currently enrolled in the course.
     *
     * @return enrolled dogs
     */
    public LinkedList<Cane> getCaniIscritti(){
        return this.elencoCaniIscritti;
    }

    /**
     * Returns the course programme.
     *
     * @return ordered lessons
     */
    public LinkedList<Lezione> getLezioni() {
        return this.programma;
    }

    /**
     * Cancels a dog's enrollment.
     *
     * @param cn dog to remove
     * @return {@code true} when the dog was removed
     */
    public Boolean annullaIscrizione(Cane cn){
        return this.elencoCaniIscritti.remove(cn);
    }

    /**
     * Updates capacity and price when both values are positive.
     *
     * @param capienza new capacity
     * @param costo new price
     * @return {@code true} when the update was applied
     */
    public boolean aggiornaInformazioni(int capienza, float costo){
        if(capienza <= 0 || costo <= 0){
            return false;
        } else {
            this.capienza = capienza;
            this.costo = costo;
            return true;
        }
    }

    /**
     * Creates a lesson and marks it as the lesson being edited.
     *
     * @param codiceLezione unique lesson code
     * @param nome lesson name
     */
    public void nuovaLezione(int codiceLezione, String nome) {
        Lezione nuovaLezione = new Lezione(codiceLezione, nome);
        setLezioneCorrente(nuovaLezione);
    }

    /**
     * Sets the lesson being edited.
     *
     * @param nuovaLezione lesson to store, or {@code null} to clear it
     * @return {@code true} when a non-null lesson was stored
     */
    public boolean setLezioneCorrente(Lezione nuovaLezione){
        if(nuovaLezione == null){
            this.lezioneCorrente = null;
            return false;
        } else {
            this.lezioneCorrente = nuovaLezione;
            return true;
        }
    }

    /**
     * Selects a lesson for shift creation.
     *
     * @param lz lesson to select
     * @return {@code true} when a non-null lesson was selected
     */
    public Boolean setLezioneSelezionata(Lezione lz){
        if(lz == null){
            this.lezioneSelezionata = null;
            return false;
        }
        else{
            this.lezioneSelezionata = lz;
            return true;
        }
    }

    /**
     * Adds the lesson being edited to the course programme.
     *
     * @return {@code true} when the lesson was added
     */
    public boolean confermaInserimentoLezione(){
        return this.programma.add(this.lezioneCorrente);
    }

    /**
     * Generates the next shift code for this course.
     *
     * @return new shift code
     */
    public int generaCodiceTurno(){
        numTurni++;
        return numTurni;
    }

    /**
     * Calculates attended and missing lessons for a dog.
     *
     * @param lezioniSeguite lessons already attended
     * @return map with the keys {@code "lezioni seguite"} and
     *         {@code "lezioni mancanti"}
     */
    public Map<String, LinkedList<Lezione>> calcolaStatoAvanzamento(LinkedList<Lezione> lezioniSeguite) {
        LinkedList<Lezione> lezioniCorsoSeguite = new LinkedList<>();
        LinkedList<Lezione> lezioniCorsoMancanti = new LinkedList<>();
        Map<String, LinkedList<Lezione>> mappaLezioni = new HashMap<>();

        for (Lezione l: this.programma) {
            if (lezioniSeguite.contains(l))
                lezioniCorsoSeguite.add(l);
            else
                lezioniCorsoMancanti.add(l);
        }

        mappaLezioni.put("lezioni seguite", lezioniCorsoSeguite);
        mappaLezioni.put("lezioni mancanti", lezioniCorsoMancanti);

        return mappaLezioni;
    }

    /**
     * Calculates the amount due using a price policy.
     *
     * @param numCaniIscritti number of enrolled dogs from the same customer
     * @param tipoPolitica policy name
     * @return amount due
     */
    public float calcolaImportoDovuto(int numCaniIscritti, String tipoPolitica){
        IpoliticaPrezzoCorso p = PCF.getPoliticaPrezzo(numCaniIscritti, tipoPolitica);
        return p.getPrezzo(this, numCaniIscritti);
    }

    /**
     * Returns the lesson being edited.
     *
     * @return current lesson
     */
    public Lezione getLezioneCorrente(){
        return this.lezioneCorrente;
    }

    /**
     * Returns the course programme.
     *
     * @return ordered lessons
     */
    public LinkedList<Lezione> getProgramma(){
        return this.programma;
    }

    /**
     * Returns the number of shifts generated for the course.
     *
     * @return shift counter
     */
    public int getNumTurni(){
        return this.numTurni;
    }

    /**
     * Returns the lesson selected for shift creation.
     *
     * @return selected lesson
     */
    public Lezione getLezioneSelezionata(){
        return this.lezioneSelezionata;
    }

}
