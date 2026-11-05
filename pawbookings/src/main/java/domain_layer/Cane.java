package domain_layer;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Map;

/**
 * Represents a dog owned by a customer.
 * Tracks course enrollment, completed courses, attended lessons, the current
 * shift, and foster-care state.
 */
public class Cane {
    private final int codiceCane;
    private final String nome;
    private final String razza;
    private Boolean lezioneDaRecuperare;
    private boolean attualmenteIscritto;
    private boolean attualmenteInAffido;

    private Corso corsoCorrente;
    private LinkedList<Corso> corsiCompletati;
    private PeriodoAffido affidoCorrente;
    private LinkedList<Lezione> lezioniSeguite;
    private Turno turnoCorrente;

    /**
     * Creates a dog with the given identity data.
     *
     * @param codiceCane unique dog code
     * @param nome dog name
     * @param razza dog breed
     */
    public Cane(int codiceCane, String nome, String razza) {
        this.codiceCane = codiceCane;
        this.nome = nome;
        this.razza = razza;
        this.attualmenteIscritto = false;
        this.lezioneDaRecuperare = false;
        this.corsiCompletati = new LinkedList<>();
        this.lezioniSeguite = new LinkedList<>();
    }

    /**
     * Returns the next lesson scheduled for the dog in its current course.
     *
     * @return the next lesson, or the first lesson when none has been attended
     * @throws IndexOutOfBoundsException if the programme is already complete
     */
    public Lezione getLezioneSuccessiva() {
        LinkedList<Lezione> programma = this.corsoCorrente.getLezioni();
        return this.calcolaLezioneSuccessiva(programma);
    }

    /**
     * Checks whether the most recently attended lesson is the first lesson of the
     * current course.
     *
     * @return {@code true} when the last attended lesson is the first programme
     *         lesson; {@code false} otherwise or when no course is active
     */
    public Boolean checkPrimaLezione(){
            if(this.corsoCorrente !=null){
                Lezione primaLezione = this.corsoCorrente.getLezioni().getFirst();
                return (lezioniSeguite.getLast().equals(primaLezione));
            }
            else{
                return false;
            }
    }

    /**
     * Sets whether the next lesson must be recovered.
     *
     * @param val {@code true} when a make-up lesson is pending
     */
    public void setLezioneDaRecuperare(Boolean val){
        this.lezioneDaRecuperare = val;
    }

    /**
     * Returns whether a make-up lesson is pending.
     *
     * @return {@code true} when a lesson must be recovered
     */
    public Boolean getLezioneDaRecuperare(){
        return this.lezioneDaRecuperare;
    }

    /**
     * Removes the most recently attended lesson from the attendance history.
     */
    public void rimuoviUltimaLezioneSeguita(){
        this.lezioniSeguite.removeLast();
    }

    /**
     * Calculates the next lesson to attend in the supplied programme.
     *
     * <p>The method counts how many lessons of the programme are already in the
     * attendance history and returns the lesson at that position.</p>
     *
     * @param programma ordered course programme
     * @return the next lesson in the programme
     * @throws IndexOutOfBoundsException if all lessons have already been attended
     */
    public Lezione calcolaLezioneSuccessiva(LinkedList<Lezione> programma){
       LinkedList<Lezione> lezioniCorsoSeguite = new LinkedList<>();
       Boolean esito;
       for(Lezione lz: programma){
             esito =this.lezioniSeguite.contains(lz);
            if(esito){
                lezioniCorsoSeguite.add(lz);
            }
       }
       return programma.get(lezioniCorsoSeguite.size());
    }

    /**
     * Toggles the enrollment state and updates the current course reference.
     *
     * @param cs course to associate when the dog becomes enrolled
     */
    public void aggiornaAttualmenteIscritto(Corso cs) {
        this.attualmenteIscritto = !this.attualmenteIscritto;
        corsoCorrente = this.attualmenteIscritto ? cs : null;
    }

    /**
     * Records attendance of the next lesson and books the supplied shift.
     *
     * <p>When the last lesson of the programme is attended, the course is moved to
     * the completed list and the dog is marked as no longer enrolled.</p>
     *
     * @param ts shift selected for the next lesson
     */
    public void aggiornaStatoAvanzamento(Turno ts) {
        LinkedList<Lezione> programma = this.corsoCorrente.getLezioni();
        Lezione lezioneSuccessiva = calcolaLezioneSuccessiva(programma);
        lezioneSuccessiva.aggiornaTurniDisponibili(ts);
        lezioniSeguite.add(lezioneSuccessiva);
        setTurnoCorrente(ts);

        if (lezioniSeguite.size() == programma.size()) {
            corsiCompletati.add(corsoCorrente);
            corsoCorrente.aggiornaCaniIscritti(this);
            aggiornaAttualmenteIscritto(corsoCorrente);
        }
    }

    /**
     * Toggles the foster-care state and updates the current foster period.
     *
     * @param pa foster period to associate when the dog enters foster care
     */
    public void aggiornaAttualmenteInAffido(PeriodoAffido pa) {
        this.attualmenteInAffido = !this.attualmenteInAffido;
        affidoCorrente = this.attualmenteInAffido ? pa : null;
    }

    /**
     * Concludes the current foster period for this dog.
     *
     * @return the result of removing the dog from the foster period
     */
    public boolean conclusioneAffido() {
        Boolean esito;
        esito = affidoCorrente.concludiAffido(this);
        this.aggiornaAttualmenteInAffido(null);
        return esito;
    }

    /**
     * Removes the dog from its current course when it is still enrolled.
     */
    public void aggiornaAssociazioniCane(){
        if (this.attualmenteIscritto) {
            this.corsoCorrente.annullaIscrizione(this);
            corsoCorrente.getCaniIscritti().remove(this);
        }
    }

    /**
     * Returns the attended and missing lessons for the current course.
     *
     * @return map with the keys {@code "lezioni seguite"} and
     *         {@code "lezioni mancanti"}
     */
    public Map<String, LinkedList<Lezione>> getAvanzamentoCorso() {
        return this.corsoCorrente.calcolaStatoAvanzamento(this.lezioniSeguite);

    }

    /**
     * Checks whether the current shift can still be cancelled or exchanged.
     *
     * @return {@code true} when a shift exists and its date is in the future
     */
    public Boolean verificaIdoneitaTurno(){
         if (this.turnoCorrente != null) {
            if (this.turnoCorrente.getData().isAfter(LocalDate.now()))
                return true;
            else
                return false;
        } else
            return false;
    }

    /**
     * Returns the dog name.
     *
     * @return dog name
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Returns the dog breed.
     *
     * @return dog breed
     */
    public String getRazza() {
        return this.razza;
    }

    /**
     * Returns the dog code.
     *
     * @return unique dog code
     */
    public int getCodiceCane() {
        return this.codiceCane;
    }

    /**
     * Returns whether the dog is currently enrolled in a course.
     *
     * @return {@code true} when enrolled
     */
    public boolean getAttualmenteIscritto() {
        return this.attualmenteIscritto;
    }

    /**
     * Returns the course currently attended by the dog.
     *
     * @return current course, or {@code null} when the dog is not enrolled
     */
    public Corso getCorsoCorrente() {
        return this.corsoCorrente;
    }

    /**
     * Returns the lessons attended by the dog.
     *
     * @return ordered attendance history
     */
    public LinkedList<Lezione> getLezioniSeguite() {
        return this.lezioniSeguite;
    }

    /**
     * Returns the courses completed by the dog.
     *
     * @return completed courses
     */
    public LinkedList<Corso> getCorsiCompletati(){
        return this.corsiCompletati;
    }

    /**
     * Checks whether the dog may start a new course enrollment.
     *
     * @param elencoCorsiDisponibili courses that currently have free places
     * @return {@code false} if the dog is already enrolled or has completed every
     *         available course; {@code true} otherwise
     */
    public Boolean checkNuovaIscrizioneCorso(LinkedList<Corso> elencoCorsiDisponibili){
        if((attualmenteIscritto == true) || (corsiCompletati.containsAll(elencoCorsiDisponibili))){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Checks whether the selected course respects the progression rules.
     *
     * <p>The basic course is always available to a dog without completed courses;
     * any other course requires the immediately preceding course to be completed.</p>
     *
     * @param cs course selected for enrollment
     * @return {@code true} when enrollment is allowed
     */
    public Boolean checkConfermaIscrizioneCorso(Corso cs){
        if(corsiCompletati.size()==0 && cs.getCodice() == 1){
            return true;
        }
        else{
            if (corsiCompletati.size() == 0) {
                return false;
            }

            Corso ultimoCorsoSeguito = corsiCompletati.getLast();
            if(cs.getCodice() == (ultimoCorsoSeguito.getCodice() +1)){
                return true;
            }
            else return false;
        }
    }

    /**
     * Checks whether a shift overlaps the current foster period.
     *
     * @param ts shift to test
     * @return {@code false} when the shift falls inside the foster period;
     *         {@code true} otherwise or when no foster period is active
     */
    public Boolean checkSovrapposizioneDate(Turno ts){
        if (affidoCorrente == null) {
            return true;
        } else {
            if ((ts.getData().isAfter(affidoCorrente.getDataInizio())) && (ts.getData().isBefore(affidoCorrente.getDataFine()))
            || (ts.getData().equals(affidoCorrente.getDataInizio()))
            || (ts.getData().equals(affidoCorrente.getDataFine()))) {
                return false;
            }
            else {
                return true;
            }
        }
    }

    /**
     * Sets the enrollment flag.
     *
     * @param bl new enrollment state
     */
    public void setAttualmenteIscritto(boolean bl) {
        this.attualmenteIscritto = bl;
    }

    /**
     * Sets the current shift.
     *
     * @param ts shift to store, or {@code null} to clear it
     */
    public void setTurnoCorrente(Turno ts) {
        this.turnoCorrente = ts;
    }

    /**
     * Sets the current foster period.
     *
     * @param pa foster period to store
     */
    public void setAffidoCorrente(PeriodoAffido pa){
        this.affidoCorrente = pa;
    }

    /**
     * Sets the foster-care flag.
     *
     * @param val new foster-care state
     */
    public void setAttualmenteInAffido(Boolean val){
       this.attualmenteInAffido = val;
    }

    /**
     * Appends a lesson to the attendance history.
     *
     * @param lz lesson to record
     */
    public void aggiungiLezioneSeguita(Lezione lz) {
        this.lezioniSeguite.add(lz);
    }

    /**
     * Returns the current foster period.
     *
     * @return current foster period, or {@code null}
     */
    public PeriodoAffido getAffido() {
        return this.affidoCorrente;
    }

    /**
     * Returns whether the dog is currently in foster care.
     *
     * @return {@code true} when in foster care
     */
    public boolean getAttualmenteInAffido(){
        return this.attualmenteInAffido;
    }

    /**
     * Returns the current foster period.
     *
     * @return current foster period, or {@code null}
     */
    public PeriodoAffido getAffidoCorrente(){
        return this.affidoCorrente;
    }

    /**
     * Returns the currently booked shift.
     *
     * @return current shift, or {@code null}
     */
    public Turno getTurnoCorrente() {
        return this.turnoCorrente;
    }

    /**
     * Returns the most recently attended lesson.
     *
     * @return last lesson in the attendance history
     */
    public Lezione getUltimaLezioneSeguita(){
        return this.lezioniSeguite.getLast();
    }

}
