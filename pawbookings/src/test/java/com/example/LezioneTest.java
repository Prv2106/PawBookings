package com.example;

import org.junit.jupiter.api.Test;

import domain_layer.Lezione;
import domain_layer.PawBookings;
import domain_layer.Turno;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Unit tests for lesson exercises, shifts, and shift exchanges.
 */
public class LezioneTest {
    public static PawBookings PB;

    @Test
    void testAggiornaTurniDisponibili(){
        // Verifies that the shift is removed from the available-shift list.
        // The shift was selected by a customer.
        Lezione lezione = new Lezione(1, "Lezione test");
        // Adds the shift and verifies that it appears in the available-shift list.
        lezione.nuovoTurno(1, LocalDate.of(2021, 1, 1), LocalTime.of(10, 0), LocalTime.of(11, 0));
        assertEquals(1, lezione.getTurniDisponibili().size());
        // Updates the available-shift list and verifies that the shift is removed.
        lezione.aggiornaTurniDisponibili(lezione.getElencoTurniDisponibili().getLast());
        assertEquals(0, lezione.getTurniDisponibili().size());
    }

    @Test
    void testNuovoEsercizio(){
        // Verifies that the new exercise is added to the exercise list.
        Lezione lezione = new Lezione(1, "Lezione test");
        // Adds the exercise and verifies that it appears in the list.
        lezione.nuovoEsercizio("Esercizio test", "Descrizione esercizio test");
        assertEquals(1, lezione.getEsercizi().size());
    }

    @Test
    void testNuovoTurno(){
        // Verifies that the new shift is added to the available-shift list.
        Lezione lezione = new Lezione(1, "Lezione test");
        // Adds the shift and verifies that it appears in the list.
        lezione.nuovoTurno(1, LocalDate.of(2021, 1, 1), LocalTime.of(10, 0), LocalTime.of(11, 0));
        assertEquals(1, lezione.getTurniDisponibili().size());
    }

    @Test
    void testEffettuaScambioTurno(){
        // Verifies that the current shift is added to elencoTurniDisponibili.
        // and that the selected shift is removed from elencoTurniDisponibili.
        Lezione lezione = new Lezione(1, "Lezione test");
        // Adds the current and selected shifts to the available-shift list.
        lezione.nuovoTurno(1, LocalDate.of(2021, 1, 1), LocalTime.of(10, 0), LocalTime.of(11, 0));
        lezione.nuovoTurno(2, LocalDate.of(2021, 1, 1), LocalTime.of(11, 0), LocalTime.of(12, 0));
        // Selects the current and replacement shifts.
        Turno turnoCorrente = lezione.getTurniDisponibili().getFirst();
        Turno turnoSelezionato = lezione.getTurniDisponibili().getLast();
        // Exchanges shifts.
        lezione.effettuaScambioTurno(turnoCorrente, turnoSelezionato);
        // Verifies that the current shift has been added to the list of available shifts.
        assertTrue(lezione.getTurniDisponibili().contains(turnoCorrente));
        // Verifies that the selected shift has been removed from the list of available shifts.
        assertFalse(lezione.getTurniDisponibili().contains(turnoSelezionato));
    }
}
