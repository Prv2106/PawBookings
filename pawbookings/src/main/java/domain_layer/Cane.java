package domain_layer;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Map;

public class Cane {
    private final int codiceCane;
    private final String nome;
    private final String razza;
    private Boolean lezioneDaRecuperare;
    private boolean attualmenteIscritto;
    private boolean attualmenteInAffido;

    // Riferimenti
    private Corso corsoCorrente;
    private LinkedList<Corso> corsiCompletati;
    private PeriodoAffido affidoCorrente;
    private LinkedList<Lezione> lezioniSeguite;
    private Turno turnoCorrente;

    public Cane(int codiceCane, String nome, String razza) {
        this.codiceCane = codiceCane;
        this.nome = nome;
        this.razza = razza;
        this.attualmenteIscritto = false;
        this.lezioneDaRecuperare = false;
        this.corsiCompletati = new LinkedList<>();
        this.lezioniSeguite = new LinkedList<>();
    }

    // ************* metodi ****************
    public Lezione getLezioneSuccessiva() {
        LinkedList<Lezione> programma = this.corsoCorrente.getLezioni();
        return this.calcolaLezioneSuccessiva(programma);
    }

    public Boolean checkPrimaLezione(){
            if(this.corsoCorrente !=null){
                Lezione primaLezione = this.corsoCorrente.getLezioni().getFirst();
                return (lezioniSeguite.getLast().equals(primaLezione));
            }
            else{
                return false;
            }
    }

    public void setLezioneDaRecuperare(Boolean val){
        this.lezioneDaRecuperare = val;
    }

    public Boolean getLezioneDaRecuperare(){
        return this.lezioneDaRecuperare;
    }

    public void rimuoviUltimaLezioneSeguita(){
        this.lezioniSeguite.removeLast();
    }

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
        this.aggiornaAttualmenteInAffido(null);
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


    public Boolean verificaIdoneitaTurno(){
         if (this.turnoCorrente != null) {
            if (this.turnoCorrente.getData().isAfter(LocalDate.now())) 
                return true;
            else 
                return false;
        } else 
            return false;
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



    public Boolean checkNuovaIscrizioneCorso(LinkedList<Corso> elencoCorsiDisponibili){
        if((attualmenteIscritto == true) || (corsiCompletati.containsAll(elencoCorsiDisponibili))){
            return false;
        }
        else{
            return true;
        }
    }

    public Boolean checkConfermaIscrizioneCorso(Corso cs){
        if(corsiCompletati.size()==0 && cs.getCodice() == 1){
            // caso in cui si seleziona in corso base
            return true;
        }
        else{
            // se selezioniamo un qualsiasi altro corso che non sia quello base
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









    // ************ setters ************************
    public void setAttualmenteIscritto(boolean bl) {
        this.attualmenteIscritto = bl;
    }

    public void setTurnoCorrente(Turno ts) {
        this.turnoCorrente = ts;
    }



    
    /****      metodi per testing        ****/  

    public void setAffidoCorrente(PeriodoAffido pa){
        this.affidoCorrente = pa;
    }


    public void setAttualmenteInAffido(Boolean val){
       this.attualmenteInAffido = val;
    }

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



    public Turno getTurnoCorrente() {
        return this.turnoCorrente;
    }


    public Lezione getUltimaLezioneSeguita(){
        return this.lezioniSeguite.getLast();
    }



}
