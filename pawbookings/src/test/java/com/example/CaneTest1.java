package com.example;
import org.junit.jupiter.api.Test;
import domain_layer.Cane;
import domain_layer.Corso;
import domain_layer.PawBookings;
import domain_layer.Turno;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;



/*
 * Test dei casi d'uso UC1-UC2
 */

class CaneTest1 {
    static PawBookings PB;

    @BeforeAll
    public static void initTest() {
        //Configurazioni che ci servono prima dell'esecuzione dei metodi di test
        PB = PawBookings.getInstance();
        PB.inserisciNuovoCorso("Corso Base", 10, 200.0F);
        Corso corsoBase = PB.modificaProgrammaCorso().get(0);
        PB.selezionaCorso(corsoBase);

        // Il codice di questa lezione sarà 1
        PB.nuovaLezione("Comandi di Base");
        PB.inserisciEsercizio("Saluto amichevole", "Questo esercizio aiuta a promuovere una socializzazione positiva tra cani");
        PB.inserisciEsercizio("Resta", "'Resta'per rimanere in posizione, aumentando gradualmente la distanza dal proprietario");
        PB.inserisciEsercizio("Richiamo", "Insegna al cane a rispondere al comando di richiamo del padrone");
        PB.confermaLezione();

        // Il codice di questa lezione sarà 2
        PB.nuovaLezione("Guinzaglio e Camminare al Guinzaglio");
        PB.inserisciEsercizio("Introduzione al Guinzaglio", "Insegnare ai proprietari come presentare il guinzaglio al cane in modo positivo, facendolo abituare gradualmente alla sensazione e premiando il comportamento calmo e collaborativo.");
        PB.inserisciEsercizio("Camminata Focalizzata", "Praticare una camminata controllata in cui il cane cammina al fianco del proprietario senza tirare al guinzaglio. Utilizzare comandi verbali e premi per incoraggiare un comportamento desiderato.");
        PB.inserisciEsercizio("Affrontare Distrazioni", "Introdurre gradualmente distrazioni durante la camminata al guinzaglio, come altri cani o stimoli ambientali. Insegnare ai proprietari come gestire le situazioni, mantenendo il controllo del cane e premiando il comportamento desiderato in presenza di distrazioni.");
        PB.confermaLezione();

        // Inserimento turni
        PB.selezionaCorsoModificaTurni(corsoBase);
        PB.selezionaLezione(corsoBase.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));

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

        // Inseriamo dei turni per le 2 lezioni del corsoBase
        Corso corsoBase = PB.getCorsi().getFirst();

        // Inserimento turni
        PB.selezionaCorsoModificaTurni(corsoBase);
        PB.selezionaLezione(corsoBase.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));



        Turno t;
        Cane Luna = PB.getCaneSelezionato();
        
       
         // Simuliamo la prenotazione di Luna ad un turno di ciascuna lezione del Corso Base
         t = corsoBase.getLezioni().get(0).getTurniDisponibili().get(0);
         Luna.aggiornaStatoAvanzamento(t);
         t = corsoBase.getLezioni().get(1).getTurniDisponibili().get(1);
         Luna.aggiornaStatoAvanzamento(t);
       
 
         // Verifichiamo che l'attributo attualmenteIscritto di cn è diventato false
         assertFalse(Luna.getAttualmenteIscritto());
 
         // Verifichiamo che l'attributo corsoCorrente di cn è diventato null
         assertEquals(null, Luna.getCorsoCorrente());
 
         // Verifichiamo che la lunghezza della lista lezioniSeguite di cn è diventata 2
         assertEquals(2, Luna.getLezioniSeguite().size());
 
         // Verifichiamo che nell'elenco Corsi completati di cn sia presente il corso che abbiamo completato
         assertTrue(Luna.getCorsiCompletati().contains(PB.getCorsi().get(0)));
 
         // Verifichiamo che l'istanza cn di Cane non sia più presente nell'elencoCaniIscritti del Corso interessato
         assertFalse(PB.getCorsi().get(0).getCaniIscritti().contains(Luna));
 
     }




   





}












