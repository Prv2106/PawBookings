package domain_layer;

import java.util.LinkedList;

public class Cane {
    private final int codiceCane;
    private final String nome;
    private final String razza;
    private boolean attualmenteIscritto;
    private Corso corsoCorrente;
    private LinkedList<Corso> corsiCompletati;
    private LinkedList<Lezione> lezioniSeguite;

    public Cane(int codiceCane, String nome, String razza) {
        this.codiceCane = codiceCane;
        this.nome = nome;
        this.razza = razza;
        this.attualmenteIscritto = false;
        this.corsiCompletati = new LinkedList<Corso>();
        this.lezioniSeguite = new LinkedList<Lezione>();
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

        if (lezioniSeguite.size() == programma.size()) {
            corsiCompletati.add(corsoCorrente);
            corsoCorrente.aggiornaCaniIscritti(this);
            aggiornaAttualmenteIscritto(corsoCorrente);
        }
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



    // setters
    public void setAttualmenteIscritto(boolean bl) {
        this.attualmenteIscritto = bl;
    }

    //  metodi per testing
    public void aggiungiLezioneSeguita(Lezione lz) {
        this.lezioniSeguite.add(lz);
    }

}
