package com.example;
import org.junit.jupiter.api.Test;
import domain_layer.Cane;
import domain_layer.Corso;
import domain_layer.PawBookings;
import domain_layer.Turno;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

class CaneTest {
    static PawBookings PB;

    @BeforeAll
    public static void initTest() {
        //Configurazioni che ci servono prima dell'esecuzione dei metodi di test
        PB = PawBookings.getInstance();
        PB.selezionaCane(new Cane(10, "Luna", "Barboncino"));
        PB.getCaneSelezionato().aggiornaAttualmenteIscritto(PB.elencoCorsi.get(0));
    }
    

    @Test
    void testAggiornaAttualmenteIscritto() {
        // il booleano è settato a false di default
        Cane cn = new Cane(10, "Sole", "Labrador"); 
        assertFalse(cn.getAttualmenteIscritto());
        assertEquals(null, cn.getCorsoCorrente());

        // simuliamo che il cane si iscrivi al corso base
        cn.aggiornaAttualmenteIscritto(PB.elencoCorsi.get(0));
        assertTrue(cn.getAttualmenteIscritto());
        assertEquals(1, cn.getCorsoCorrente().getCodice());
    }



    @Test
    void testGetLezioneSuccessiva() { 
        // Recuperiamo l'istanza di Luna, che è iscritta al corsoBase e verifichiamo che la lezione successiva sia la prima
        // dopo, aggiungiamo la prima lezione all'elenco delle lezioni seguite da Luna, 
        // così da verificare successivamente che la lezione successiva sia la seconda del programma
        
        Corso corsoBase = PB.getCorsi().get(0); // corso base
        Cane Luna = PB.getCaneSelezionato();
        /*  Il programma del corso base è il seguente:
            Lezione lezione1 = new Lezione(1,"Comandi di Base");
            Lezione lezione2 = new Lezione(2,"Guinzaglio e Camminare al Guinzaglio");
            Lezione lezione3 = new Lezione(3,"Dai la Zampa e Seduto");
            Lezione lezione4 = new Lezione(4,"Controllo delle Impulsività");
         */

        assertEquals(PB.getCorsi().get(0), corsoBase);

        // ci aspettiamo che la lezione sia la prima in quanto il Luna si è solo iscritta al corsoBase
        assertEquals("Comandi di Base", Luna.getLezioneSuccessiva().getNome());

        // assumiamo che Luna abbia seguito la prima lezione
        Luna.aggiungiLezioneSeguita(PB.getCorsi().get(0).getLezioni().get(1));

        // Seconda verifica: ci aspettiamo che la lezione successiva sia la seconda
        assertEquals("Guinzaglio e Camminare al Guinzaglio", Luna.getLezioneSuccessiva().getNome());

        // Ripristino lo stato iniziale
        Luna.getLezioniSeguite().remove(PB.getCorsi().get(0).getLezioni().get(1));
    }


    
    @Test
    void testAggiornaAvanzamentoCorso() {
        // 1) dimensione elenco lezioni seguite, ovvero
        // controlliamo che la lezione successiva sia aggiunta all'elenco

        // prendiamo un cane
        Cane Luna = PB.getCaneSelezionato();
        int lunghezzaPrecedente = Luna.getLezioniSeguite().size();

       

        // recuperiamo il primo turno della prima lezione del corso base a cui è iscritto
        Turno t1 = PB.getCorsi().get(0).getLezioni().get(0).getTurniDisponibili().get(0);

        Luna.aggiornaStatoAvanzamento(t1);

        assertEquals(lunghezzaPrecedente + 1, Luna.getLezioniSeguite().size());

        // verifichiamo che l'elenco delle lezioni seguite da Luna contenga la lezione
        // relativa al turno scelto
        assertTrue(Luna.getLezioniSeguite().contains(PB.getCorsi().get(0).getLezioni().get(0)));

        // Ripristino lo stato iniziale
        Luna.getLezioniSeguite().remove(PB.getCorsi().get(0).getLezioni().get(0));

    }
    



     // Questo deve essere l'ultimo metodo di test chiamato
     @AfterAll
     static void testCompletamentoCorso() {
         // 2) verfichiamo il caso in cui il Luna termina il programma di un Corso:
 
         Turno t;
         Cane Luna = PB.getCaneSelezionato();
       
         // Simuliamo la prenotazione di Luna ad un turno di ciascuna lezione del Corso Base
         t = PB.getCorsi().get(0).getLezioni().get(0).getTurniDisponibili().get(0);
         Luna.aggiornaStatoAvanzamento(t);
         t = PB.getCorsi().get(0).getLezioni().get(1).getTurniDisponibili().get(0);
         Luna.aggiornaStatoAvanzamento(t);
         t = PB.getCorsi().get(0).getLezioni().get(2).getTurniDisponibili().get(0);
         Luna.aggiornaStatoAvanzamento(t);
         t = PB.getCorsi().get(0).getLezioni().get(3).getTurniDisponibili().get(0);
         Luna.aggiornaStatoAvanzamento(t);
 
         // Verifichiamo che l'attributo attualmenteIscritto di cn è diventato false
         assertFalse(Luna.getAttualmenteIscritto());
 
         // Verifichiamo che l'attributo corsoCorrente di cn è diventato null
         assertEquals(null, Luna.getCorsoCorrente());
 
         // Verifichiamo che la lunghezza della lista lezioniSeguite di cn è diventata 4
         assertEquals(4, Luna.getLezioniSeguite().size());
 
         // Verifichiamo che nell'elenco Corsi completati di cn sia presente il corso che abbiamo completato
         assertTrue(Luna.getCorsiCompletati().contains(PB.getCorsi().get(0)));
 
         // Verifichiamo che l'istanza cn di Cane non sia più presente nell'elencoCaniIscritti del Corso interessato
         assertFalse(PB.getCorsi().get(0).getCaniIscritti().contains(Luna));
 
     }




   





}












