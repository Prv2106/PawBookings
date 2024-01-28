package domain_layer;

import java.util.LinkedList;
import java.util.Map;

public class Cane {
    private final int codiceCane;
    private final String nome;
    private final String razza;
    private boolean attualmenteIscritto;
    private Corso corsoCorrente;
    private LinkedList<Corso> corsiCompletati;
    private boolean attualmenteInAffido;
    private PeriodoAffido affidoCorrente;
    private LinkedList<Lezione> lezioniSeguite;
    private Turno turnoCorrente;

    public Cane(int codiceCane, String nome, String razza) {
        this.codiceCane = codiceCane;
        this.nome = nome;
        this.razza = razza;
        this.attualmenteIscritto = false;
        this.corsiCompletati = new LinkedList<>();
        this.lezioniSeguite = new LinkedList<>();
    }

    // ************* metodi ****************
    public Lezione getLezioneSuccessiva() {
        LinkedList<Lezione> programma = this.corsoCorrente.getLezioni();
        return this.calcolaLezioneSuccessiva(programma);
    }

    public Lezione calcolaLezioneSuccessiva(LinkedList<Lezione> programma) {
        return programma.get(lezioniSeguite.size());
    }

    public void aggiornaAttualmenteIscritto(Corso cs) {
        this.attualmenteIscritto = !this.attualmenteIscritto;
        corsoCorrente = this.attualmenteIscritto ? cs : null;
    }

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

    // Il metodo aggiorna lo stato attualmente in affido.
    public void aggiornaAttualmenteInAffido(PeriodoAffido pa) {
        this.attualmenteInAffido = !this.attualmenteInAffido;
        affidoCorrente = this.attualmenteInAffido ? pa : null;
    }

    // Il metodo richiama il metodo concludiAffido e il metodo aggiornaAttualmenteInAffido.
    public boolean conclusioneAffido() {
        Boolean esito;
        esito = affidoCorrente.concludiAffido(this);
        this.aggiornaAttualmenteInAffido(affidoCorrente);
        return esito;
    }
    
    public void aggiornaAssociazioniCane(){
        if (this.attualmenteIscritto) {
            this.corsoCorrente.annullaIscrizione(this);
            corsoCorrente.getCaniIscritti().remove(this);
        }
    }

    public Map<String, LinkedList<Lezione>> getAvanzamentoCorso() {
        return this.corsoCorrente.calcolaStatoAvanzamento(this.lezioniSeguite);

    }

    // ********** getters 'semplici' *********
    public String getNome() {
        return this.nome;
    }

    public String getRazza() {
        return this.razza;
    }

    public int getCodiceCane() {
        return this.codiceCane;
    }

    public boolean getAttualmenteIscritto() {
        return this.attualmenteIscritto;
    }

    public Corso getCorsoCorrente() {
        return this.corsoCorrente;
    }

    public LinkedList<Lezione> getLezioniSeguite() {
        return this.lezioniSeguite;
    }

    public LinkedList<Corso> getCorsiCompletati(){
        return this.corsiCompletati;
    }



    // ************ setters ************************
    public void setAttualmenteIscritto(boolean bl) {
        this.attualmenteIscritto = bl;
    }

    public void setTurnoCorrente(Turno ts) {
        this.turnoCorrente = ts;
    }



    /****      metodi per testing        ****/  

    public void aggiungiLezioneSeguita(Lezione lz) {
        this.lezioniSeguite.add(lz);
    }

    public PeriodoAffido getAffido() {
        return this.affidoCorrente;
    }

    public boolean getAttualmenteInAffido(){
        return this.attualmenteInAffido;
    }


    public PeriodoAffido getAffidoCorrente(){
        return this.affidoCorrente;
    }



    public Turno getTurnoCorrente(){
        return this.turnoCorrente;
    }


    public Lezione getUltimaLezioneSeguita(){
        return this.lezioniSeguite.getLast();
    }

}
