package com.example;
import org.junit.jupiter.api.Test;
import domain_layer.Cane;
import domain_layer.Corso;
import domain_layer.CorsoBase;
import domain_layer.Lezione;
import domain_layer.PawBookings;
import domain_layer.Turno;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;

class CaneTest {
    static PawBookings PB;

    @BeforeAll
    public static void initTest() {
        PB = PawBookings.getInstance();
        PB.selezionaCane(new Cane(1, "Luna", "Barboncino"));
    }

    @Test
    void testAggiornaAttualmenteIscritto() {
        // il booleano è settato a false di default
        Cane cn = new Cane(10, "Sole", "Labrador"); 
        assertFalse(cn.getAttualmenteIscritto());
        assertEquals(null, cn.getCorsoCorrente());

        // simuliamo che il cane si iscrivi al corso base
        cn.aggiornaAttualmenteIscritto(new CorsoBase(20, 20, 20, "Corso Base"));
        assertTrue(cn.getAttualmenteIscritto());
        assertEquals(20, cn.getCorsoCorrente().getCodice());
    }
    @Test
    void testGetLezioneSuccessiva() { 
        // iscriviamo un cane ad un corso e verifichiamo che la lezione successiva sia la prima
        // dopo, aggiungiamo la prima lezione all'elenco delle lezioni seguite del cane, 
        // così da verificare successivamente che la lezione successiva sia la seconda del programma
        
        Corso cs = PB.getCorsi().get(0); // corso base
        Cane cn = PB.getCaneSelezionato();
        /*
            Lezione lezione1 = new Lezione(1,"Comandi di Base");
            Lezione lezione2 = new Lezione(2,"Guinzaglio e Camminare al Guinzaglio");
            Lezione lezione3 = new Lezione(3,"Dai la Zampa e Seduto");
            Lezione lezione4 = new Lezione(4,"Controllo delle Impulsività");
         */
        cs.confermaIscrizione(cn);
        cn.aggiornaAttualmenteIscritto(cs);
        
        // ci aspettiamo che la lezione sia la prima in quanto il canè è stato appena iscritto
        assertEquals("Comandi di Base", cn.getLezioneSuccessiva().getNome());

        // assumiamo che il cane abbia seguito la prima lezione
        cn.aggiungiLezioneSeguita(new Lezione(1, "Comandi di Base"));

        // Seconda verifica: ci aspettiamo che la lezione successiva sia la seconda
        assertEquals("Guinzaglio e Camminare al Guinzaglio", cn.getLezioneSuccessiva().getNome());
    }

    @Test
    void testAggiornaAvanzamentoCorso() {
        // 1) dimensione elenco lezioni seguite, ovvero
        // controlliamo che la lezione successiva sia aggiunta all'elenco

        // prendiamo un cane
        Cane cn = PB.getCaneSelezionato();

        int lunghezzaPrecedente = cn.getLezioniSeguite().size();

        // simuliamo l'iscrizione del cane
        cn.aggiornaAttualmenteIscritto(PB.getCorsi().get(0));

        // recuperiamo il primo turno della prima lezione del corso base a cui è iscritto
        Turno t = PB.getCorsi().get(0).getLezioni().get(0).getTurniDisponibili().get(0);

        cn.aggiornaStatoAvanzamento(t);

        assertEquals(lunghezzaPrecedente + 1, cn.getLezioniSeguite().size());

        // verifichiamo che l'elenco delle lezioni seguite del cane contenga la lezione
        // relativa al turno scelto
        assertTrue(cn.getLezioniSeguite().contains(PB.getCorsi().get(0).getLezioni().get(0)));
    }

    @Test
    void testCompletamentoCorso() {
        // 2) verfichiamo il caso in cui il cane termina il programma di un Corso:

        Turno t;
        Cane cn = PB.getCaneSelezionato();
        // simuliamo l'iscrizione del cane
        cn.aggiornaAttualmenteIscritto(PB.getCorsi().get(0));

        // Simuliamo la prenotazione del Cane ad un turno di ciascuna lezione
        t = PB.getCorsi().get(0).getLezioni().get(0).getTurniDisponibili().get(0);
        cn.aggiornaStatoAvanzamento(t);
        t = PB.getCorsi().get(0).getLezioni().get(1).getTurniDisponibili().get(0);
        cn.aggiornaStatoAvanzamento(t);
        t = PB.getCorsi().get(0).getLezioni().get(2).getTurniDisponibili().get(0);
        cn.aggiornaStatoAvanzamento(t);
        t = PB.getCorsi().get(0).getLezioni().get(3).getTurniDisponibili().get(0);
        cn.aggiornaStatoAvanzamento(t);

        // Verifichiamo che l'attributo attualmenteIscritto di cn è diventato false
        assertFalse(cn.getAttualmenteIscritto());

        // Verifichiamo che l'attributo corsoCorrente di cn è diventato null
        assertEquals(null, cn.getCorsoCorrente());

        // Verifichiamo che la lunghezza della lista lezioniSeguite di cn è diventata 4
        assertEquals(4, cn.getLezioniSeguite().size());

        // Verifichiamo che nell'elenco Corsi completati di cn sia presente il corso che abbiamo completato
        assertTrue(cn.getCorsiCompletati().contains(PB.getCorsi().get(0)));

        // Verifichiamo che l'istanza cn di Cane non sia più presente nell'elencoCaniIscritti del Corso interessato
        assertFalse(PB.getCorsi().get(0).getCaniIscritti().contains(cn));

    }





}
