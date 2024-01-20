package domain_layer;

import java.util.ArrayList;
import java.util.LinkedList;

public class Cane {
    private final int codiceCane;
    private final String nome;
    private final String razza;
    private boolean attualmenteIscritto;
    private Corso corsoCorrente;
    private ArrayList<Corso> corsiCompletati;
    private LinkedList<Lezione> lezioniSeguite;

    public Cane(int codiceCane, String nome, String razza, boolean attualmenteIscritto) {
        this.codiceCane = codiceCane;
        this.nome = nome;
        this.razza = razza;
        this.attualmenteIscritto = attualmenteIscritto;
    }

    // ************* metodi ****************
    public Lezione getLezioneSuccessiva() {
        ArrayList<Lezione> programma = this.corsoCorrente.getLezioni();
        return this.calcolaLezioneSuccessiva(programma);
    }

    public Lezione calcolaLezioneSuccessiva(ArrayList<Lezione> programma) {
        return programma.get(lezioniSeguite.size());
        
    }

    public void aggiornaAttualmenteIscritto(Corso cs) {
        this.attualmenteIscritto = !this.attualmenteIscritto;
        corsoCorrente = attualmenteIscritto == true ? cs : null;
    }

    public void aggiornaStatoAvanzamento(Turno ts) {
        ArrayList<Lezione> programma = this.corsoCorrente.getLezioni();
        Lezione lezioneSuccessiva = calcolaLezioneSuccessiva(programma);
        lezioneSuccessiva.aggiornaTurniDisponibili(ts);
        lezioniSeguite.add(lezioneSuccessiva);

        if (lezioniSeguite.size() == programma.size()) {
            corsiCompletati.add(corsoCorrente);
            aggiornaAttualmenteIscritto(corsoCorrente);
            corsoCorrente.aggiornaCaniIscritti(this);
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

}
