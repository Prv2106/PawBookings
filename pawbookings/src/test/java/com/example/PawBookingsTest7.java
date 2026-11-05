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

/**
 * Integration tests for domain pricing rules R1-R4.
 */
public class PawBookingsTest7 {
    static PawBookings PB;
    static Cane Stella;
    static Cane Asso;
    static Cane Maya;
    static Cane Leila;
    static Corso corsoBase;
    static Cliente Alberto;

    // Runs before all tests.
    // Initializes the PawBookings instance.

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

        // Adds six shifts to lesson 1 and two shifts to lesson 2.
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

    // Tests domain rules R2 and R4.
    @Test
    void testConfermaConclusioneAffido(){
        // Rule R2: early pickup at least one week before the period end.
        // The period ends in seven days, which qualifies as early pickup.
        PeriodoAffido periodoTestAnticipo = new PeriodoAffido(11, LocalDate.now().minusMonths(1), LocalDate.now().plusDays(7), 100.0F);
        // Places Stella in foster care for this period.
        PB.setClienteLoggato(Alberto);
        PB.selezionaPeriodo(periodoTestAnticipo);
        PB.confermaAffido(Stella);
        assertNotNull(Stella.getAffidoCorrente());
        // Simulates concluding Stella's foster period.
        PB.selezionaCane(Stella);
        PB.setClienteSelezionato(Alberto);
        // Expected amount due: 80.0F (20% discount).
        assertEquals(80.0F, PB.confermaConclusioneAffido());

        // Rule R4: pickup with multiple dogs in foster care.
        // The period ends in six days, which does not qualify as early pickup.
        PeriodoAffido periodoTestAffidoMultiplo = new PeriodoAffido(11, LocalDate.now().minusMonths(1), LocalDate.now().plusDays(6), 100.0F);
        // Places Stella and Asso in foster care in periodoTestAffidoMultiplo.
        PB.selezionaPeriodo(periodoTestAffidoMultiplo);
        PB.confermaAffido(Stella);
        PB.confermaAffido(Asso);

        // Concludes Stella's foster care and then Asso's.
        // Expected: the first pickup receives a 20% discount.
        PB.selezionaCane(Stella);
        PB.setClienteSelezionato(Alberto);
        // Expected amount due: 80.0F (20% discount).
        assertEquals(80.0F, PB.confermaConclusioneAffido());
        // Expected: the second pickup receives no discount.
        PB.selezionaCane(Asso);
        PB.setClienteSelezionato(Alberto);
        // Expected amount due: 100.0F (no discount).
        assertEquals(100.0F, PB.confermaConclusioneAffido());

        // Rules R2 and R4 overlap: early pickup with multiple dogs.
        // The period ends in seven days, which qualifies as early pickup.
        PeriodoAffido testSovrapposizionePolitiche = new PeriodoAffido(11, LocalDate.now().minusMonths(1), LocalDate.now().plusDays(7), 100.0F);
        PB.selezionaPeriodo(testSovrapposizionePolitiche);
        PB.confermaAffido(Stella);
        PB.confermaAffido(Asso);

        // Concludes Stella's foster care and then Asso's.
        // Expected: the first pickup receives a 40% discount (20% + 20%).
        PB.selezionaCane(Stella);
        PB.setClienteSelezionato(Alberto);
        // Expected amount due: 60.0F (40% discount).
        assertEquals(60.0F, PB.confermaConclusioneAffido());
        // Expected: the second pickup receives a 20% discount.
        PB.selezionaCane(Asso);
        PB.setClienteSelezionato(Alberto);
        // Expected amount due: 80.0F (20% discount).
        assertEquals(80.0F, PB.confermaConclusioneAffido());
    }

    // Tests domain rules R1 and R3.
    @Test
    void testConfermaTimbroTurno(){
        Lezione l1 = corsoBase.getLezioni().getFirst();
        Lezione l2 =corsoBase.getLezioni().getLast();
        PB.setClienteSelezionato(Alberto);
        // Rule R1: lesson recovery.
        // The basic course has two lessons and costs 200.0F.
        // Enrolls Maya and simulates that the second lesson must be recovered.
        PB.selezionaCane(Maya);
        PB.confermaIscrizioneCorso(corsoBase);
        Turno turnoRecupero = l2.getTurniDisponibili().getFirst();
        Maya.getLezioniSeguite().add(l1);
        Maya.getLezioniSeguite().add(l2);

        // Simulates recovering lesson 2.
        PB.selezionaTurnoRecupero(turnoRecupero);
        assertTrue(Maya.getLezioneDaRecuperare());
        // Calls confermaTimbroTurno.
        // Expected amount due: 50.0F.
        // (50% of the course price divided by the number of lessons).
        assertEquals(50.0F, PB.confermaTimbroTurno());

        // Resets the starting conditions.
        Maya.getLezioniSeguite().clear();

        // Rule R3: multiple enrollment.
        // Enrolls Leila in the basic course.
        PB.selezionaCane(Leila);
        PB.confermaIscrizioneCorso(corsoBase);
        // Books Leila and Maya for the first lesson.
        PB.selezionaTurno(l1.getTurniDisponibili().getLast());
        PB.selezionaCane(Maya);
        PB.selezionaTurno(l1.getTurniDisponibili().getFirst());

        assertEquals(l1,Maya.getLezioniSeguite().getFirst());
        assertEquals(l1,Leila.getLezioniSeguite().getLast());

        // Calls confermaTimbroTurno.
        // Expected: the first dog pays 140.0F (30% discount).
        PB.selezionaCane(Maya);
        assertEquals(140.0F, PB.confermaTimbroTurno());
        // Expected: the second dog pays 200.0F (no discount).
        PB.selezionaCane(Leila);
        assertEquals(200.0F, PB.confermaTimbroTurno());

        // Resets the starting conditions.
        Maya.getLezioniSeguite().clear();
        Leila.getLezioniSeguite().clear();

        // Rules R1 and R3: recovery of a multiple-enrollment lesson.
        // Simulates both dogs recovering the first lesson.
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

        // Calls confermaTimbroTurno.
        // Expected: the first dog pays 190.0F.
        // (30% discount plus 50% of the course price divided by the number of lessons).
        PB.selezionaCane(Maya);
        assertEquals(190.0F, PB.confermaTimbroTurno());
        // Expected: the second dog pays 250.0F.
        // (full price plus 50% of the course price divided by the number of lessons).
        PB.selezionaCane(Leila);
        assertEquals(250.0F, PB.confermaTimbroTurno());

        // Resets the starting conditions.
        Maya.getLezioniSeguite().clear();
        Leila.getLezioniSeguite().clear();

        // Tests the method when the amount due is zero.

        PB.selezionaCane(Maya);
        Maya.getLezioniSeguite().add(l1);

        PB.selezionaTurno(l2.getElencoTurniDisponibili().getFirst());
        // Calls confermaTimbroTurno.
        // Expected: zero because the shift is the second lesson and the course is already paid.

        assertEquals(0, PB.confermaTimbroTurno());

    }

}
