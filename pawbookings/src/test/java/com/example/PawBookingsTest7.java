package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domain_layer.Cane;
import domain_layer.Cliente;
import domain_layer.Corso;
import domain_layer.Lezione;
import domain_layer.PawBookings;
import domain_layer.PeriodoAffido;
import domain_layer.Turno;


/*
 * Test delle regole di dominioR1-R2-R3-R4
*/


public class PawBookingsTest7 {
    static PawBookings PB;
    static Cane Stella;
    static Cane Asso;
    static Cane Maya;
    static Cane Leila;
    static Corso corsoBase;
    static Cliente Alberto;
    



    // Il metodo viene eseguito prima di tutti i test
    // inizializza l'istanza di PawBookings

    @BeforeAll
    public static void initTest() {
        PB = PawBookings.getInstance();
        PB.registrati("Alberto", "Provenzano", "156322345678", "0000");
        PB.aggiungiCane("Stella", "Pastore Tedesco");
        PB.aggiungiCane("Asso", "Corso");
        PB.aggiungiCane("Maya", "Pastore Tedesco");
        PB.aggiungiCane("Leila", "Labrador");
        PB.aggiungiCane("Duchessa", "Labrador");
        PB.logout();

        Alberto = PB.getClienti().get("Alberto1");
        Stella = Alberto.getCane(1);
        Asso = Alberto.getCane(2);
        Maya = Alberto.getCane(3);
        Leila = Alberto.getCane(4);
        

        PB.inserisciNuovoCorso("Corso Base", 10, 200.0F);
        corsoBase = PB.modificaProgrammaCorso().get(0);
        PB.selezionaCorso(corsoBase);


        PB.nuovaLezione("Comandi di Base");
        PB.inserisciEsercizio("Saluto amichevole", "Questo esercizio aiuta a promuovere una socializzazione positiva tra cani");
        PB.confermaLezione();

        PB.nuovaLezione("Guinzaglio e Camminare al Guinzaglio");
        PB.inserisciEsercizio("Introduzione al Guinzaglio", "Insegnare ai proprietari come presentare il guinzaglio al cane in modo positivo, facendolo abituare gradualmente alla sensazione e premiando il comportamento calmo e collaborativo.");
        PB.confermaLezione();



        // Inserimento turni 6 per la lezione 1 e 2 per la lezione 2
        PB.selezionaCorsoModificaTurni(corsoBase);
        PB.selezionaLezione(corsoBase.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(19, 0), LocalTime.of(20, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(20, 0), LocalTime.of(21, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(15, 0), LocalTime.of(16, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(0));
        

        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(15, 0), LocalTime.of(16, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(15, 0), LocalTime.of(16, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(15, 0), LocalTime.of(16, 0));
      

    }




    /* Test regole di dominio R2-R4 */
    @Test
    void testConfermaConclusioneAffido(){
        /*  Test R2 -Ritiro Anticipato (almeno 1 settimana prima della dataFine del periodo) */
        // Questo periodo termina tra 7 giorni(rientriamo nell'anticipo della regola di dominio)
        PeriodoAffido periodoTestAnticipo = new PeriodoAffido(11, LocalDate.now().minusMonths(1), LocalDate.now().plusDays(7), 100.0F);
        // Simuliamo l'affido di stella a tale periodo
        PB.setClienteLoggato(Alberto);
        PB.selezionaPeriodo(periodoTestAnticipo);
        PB.confermaAffido(Stella);
        assertNotNull(Stella.getAffidoCorrente());
        // Adesso simuliamo la conclusione dell'affido di stella a questo periodo
        PB.selezionaCane(Stella);
        PB.setClienteSelezionato(Alberto);
        // Ci aspettiamo che l'importo dovuto sia 80.0F (costo del periodo scontato del 20%)
        assertEquals(80.0F, PB.confermaConclusioneAffido());

        /* Test R4 -Ritiro con più cani in affido*/
        // Questo periodo termina tra 6 giorni (non comprende quindi la regola R2)
        PeriodoAffido periodoTestAffidoMultiplo = new PeriodoAffido(11, LocalDate.now().minusMonths(1), LocalDate.now().plusDays(6), 100.0F);
        // Mettiamo Stella e Asso in affido nel periodoTestAffidoMultiplo 
        PB.selezionaPeriodo(periodoTestAffidoMultiplo);
        PB.confermaAffido(Stella);
        PB.confermaAffido(Asso);
        
        // Concludendo adesso l'affido di Stella e poi di Asso
        // Ci aspettiamo che per il primo Cane del quale viene concluso l'affido ci sia uno sconto del 20%
        PB.selezionaCane(Stella);
        PB.setClienteSelezionato(Alberto);
        // Ci aspettiamo che l'importo dovuto sia 80.0F (costo del periodo scontato del 20%)
        assertEquals(80.0F, PB.confermaConclusioneAffido());
        // Ci aspettiamo che per il secondo Cane del quale viene concluso l'affido non ci sia alcuno sconto
        PB.selezionaCane(Asso);
        PB.setClienteSelezionato(Alberto);
        // Ci aspettiamo che l'importo dovuto sia 100.0F (nessuno sconto)
        assertEquals(100.0F, PB.confermaConclusioneAffido());


        /* Test Sovrapposizione R2 ed R4 -Ritiro anticipato con più cani in affido*/
        // Questo periodo termina tra 7 giorni(rientriamo nell'anticipo della regola di dominio)
        PeriodoAffido testSovrapposizionePolitiche = new PeriodoAffido(11, LocalDate.now().minusMonths(1), LocalDate.now().plusDays(7), 100.0F);
        PB.selezionaPeriodo(testSovrapposizionePolitiche);
        PB.confermaAffido(Stella);
        PB.confermaAffido(Asso);

        // Concludendo adesso l'affido di Stella e poi di Asso
        // Ci aspettiamo che per il primo Cane del quale viene concluso l'affido ci sia uno sconto del 40% (20% + 20%)
        PB.selezionaCane(Stella);
        PB.setClienteSelezionato(Alberto);
        // Ci aspettiamo che l'importo dovuto sia 60.0F (costo del periodo scontato del 40%)
        assertEquals(60.0F, PB.confermaConclusioneAffido());
        // Ci aspettiamo che per il secondo Cane del quale viene concluso l'affido ci sia uno sconto del 20%
        PB.selezionaCane(Asso);
        PB.setClienteSelezionato(Alberto);
        // Ci aspettiamo che l'importo dovuto sia 80.0F  (costo del periodo scontato del 20%)
        assertEquals(80.0F, PB.confermaConclusioneAffido());
    }

    /* Test regole di dominio R1-R3 */
    @Test
    void testConfermaTimbroTurno(){
        Lezione l1 = corsoBase.getLezioni().getFirst();
        Lezione l2 =corsoBase.getLezioni().getLast();
        PB.setClienteSelezionato(Alberto);
        /* Test R1 - Recupero Lezione */
        // Il corsoBase ha 2 lezioni, il costo del corsoBase è di 200.0F
        // Simuliamo l'iscrizione di Maya al corsoBase e simuliamo che debba essere recuperata la seconda lezione
        PB.selezionaCane(Maya);
        PB.confermaIscrizioneCorso(corsoBase);
        Turno turnoRecupero = l2.getTurniDisponibili().getFirst();
        Maya.getLezioniSeguite().add(l1);
        Maya.getLezioniSeguite().add(l2);

        // Simuliamo il recupero della lezione 2
        PB.selezionaTurnoRecupero(turnoRecupero);
        assertTrue(Maya.getLezioneDaRecuperare());
        // chiamando il metodo confermaTimbroTurno
        // Ci aspettiamo che venga restituito, come importo dovuto, 50.0F 
        // ( 50% del costo totale del corso diviso il numero di lezioni previste da questo)
        assertEquals(50.0F, PB.confermaTimbroTurno());

        // Resettiamo le condizioni di partenza
        Maya.getLezioniSeguite().clear();

        /* Test R3 - Iscrizione multipla */
        // Simuliamo l'iscrizione di Leila al corsoBase 
        PB.selezionaCane(Leila);
        PB.confermaIscrizioneCorso(corsoBase);
        // Prenotiamo sia Leila che Maya alla prima lezione del corsoBase
        PB.selezionaTurno(l1.getTurniDisponibili().getLast());
        PB.selezionaCane(Maya);
        PB.selezionaTurno(l1.getTurniDisponibili().getFirst());

        assertEquals(l1,Maya.getLezioniSeguite().getFirst());
        assertEquals(l1,Leila.getLezioniSeguite().getLast());

        // chiamando il metodo confermaTimbroTurno
        // Ci aspettiamo che l'importo dovuto per il primo cane che segue la prima lezione sia 140.0F (prezzo di corsoBase scontato del 30%)
        PB.selezionaCane(Maya);
        assertEquals(140.0F, PB.confermaTimbroTurno());
        // Ci aspettiamo che l'importo dovuto per il secondo cane che segue la prima lezione sia 200.0F (prezzo di corsoBase senza sconto)
        PB.selezionaCane(Leila);
        assertEquals(200.0F, PB.confermaTimbroTurno());

        // Resettiamo le condizioni di partenza
        Maya.getLezioniSeguite().clear();
        Leila.getLezioniSeguite().clear();
        

        /* Test R1-R3 - Recupero Lezione Iscrizione Multipla */
        // Simuliamo che sia Leila che Maya debbano recuperare la prima lezione del corsoBase
        Turno t1 = l1.getTurniDisponibili().getFirst();
        Turno t2 = l2.getElencoTurniDisponibili().getLast();
        Maya.getLezioniSeguite().add(l1);
        Leila.getLezioniSeguite().add(l1);

        PB.selezionaCane(Maya);
        PB.selezionaTurnoRecupero(t1);
        assertTrue(Maya.getLezioneDaRecuperare());

        PB.selezionaCane(Leila);
        PB.selezionaTurnoRecupero(t2);
        assertTrue(Leila.getLezioneDaRecuperare());

        // chiamando il metodo confermaTimbroTurno
        // Ci aspettiamo che l'importo dovuto per il primo cane che segue la prima lezione sia 190.0F 
        // (prezzo di corsoBase scontato del 30% + 50% del costo del corsoBase diviso il numero delle sue lezioni)
        PB.selezionaCane(Maya);
        assertEquals(190.0F, PB.confermaTimbroTurno());
        // Ci aspettiamo che l'importo dovuto per il secondo cane che segue la prima lezione sia 250.0F 
        // (prezzo di corsoBase senza sconto + 50% del costo del corsoBase diviso il numero delle sue lezioni)
        PB.selezionaCane(Leila);
        assertEquals(250.0F, PB.confermaTimbroTurno());

        // Resettiamo le condizioni di partenza
        Maya.getLezioniSeguite().clear();
        Leila.getLezioniSeguite().clear();
        

        /* Test metodo quando l'importo dovuto è 0 */
        // // Prenotiamo Maya alla seconda lezione del corsoBase
        PB.selezionaCane(Maya);
        Maya.getLezioniSeguite().add(l1);
    
        PB.selezionaTurno(l2.getElencoTurniDisponibili().getFirst());
        // chiamando il metodo confermaTimbroTurno
        // Ci aspettiamo che l'importo dovuto sia 0 in quanto il turno corrisponde alla seconda lezione del corsoBase
        // di conseguenza il corso è stato già pagato
        assertEquals(0, PB.confermaTimbroTurno());
        

    }










}
