package com.example;

import org.junit.jupiter.api.Test;


import domain_layer.Lezione;
import domain_layer.PawBookings;
import domain_layer.Turno;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class LezioneTest {
    public static PawBookings PB;
    
    @Test
    void testAggiornaTurniDisponibili(){
        // Verifica che il turno venga rimosso dall'elenco dei turni disponibili
        // poichè è stato selezionato da un cliente
        Lezione lezione = new Lezione(1, "Lezione test");
        // aggiungiamo il turno all'elenco dei turni disponibili e verifichiamo che sia presente
        lezione.nuovoTurno(1, LocalDate.of(2021, 1, 1), LocalTime.of(10, 0), LocalTime.of(11, 0));
        assertEquals(1, lezione.getTurniDisponibili().size());
        // aggiorniamo l'elenco dei turni disponibili e verifichiamo che il turno non sia più presente
        lezione.aggiornaTurniDisponibili(lezione.getElencoTurniDisponibili().getLast());
        assertEquals(0, lezione.getTurniDisponibili().size());
    }

    @Test
    void testNuovoEsercizio(){
        // Verifica che il nuovo esercizio venga aggiunto alla lista degli esercizi
        Lezione lezione = new Lezione(1, "Lezione test");
        // aggiungiamo l'esercizio alla lista e verifichiamo che sia presente
        lezione.nuovoEsercizio("Esercizio test", "Descrizione esercizio test");
        assertEquals(1, lezione.getEsercizi().size());
    }

    @Test
    void testNuovoTurno(){
        // Verifica che il nuovo turno venga aggiunto alla lista dei turni disponibili
        Lezione lezione = new Lezione(1, "Lezione test");
        // aggiungiamo il turno alla lista e verifichiamo che sia presente
        lezione.nuovoTurno(1, LocalDate.of(2021, 1, 1), LocalTime.of(10, 0), LocalTime.of(11, 0));
        assertEquals(1, lezione.getTurniDisponibili().size());
    }

    @Test
    void testEffettuaScambioTurno(){
        // Verifica che il turno corrente venga aggiunto all'elencoTurniDisponibili
        // e che il turno selezionato venga rimosso dall'elencoTurniDisponibili
        Lezione lezione = new Lezione(1, "Lezione test");
        // aggiungiamo il turno corrente e il turno selezionato all'elenco dei turni disponibili
        lezione.nuovoTurno(1, LocalDate.of(2021, 1, 1), LocalTime.of(10, 0), LocalTime.of(11, 0));
        lezione.nuovoTurno(2, LocalDate.of(2021, 1, 1), LocalTime.of(11, 0), LocalTime.of(12, 0));
        // selezioniamo il turno corrente e il turno selezionato
        Turno turnoCorrente = lezione.getTurniDisponibili().getFirst();
        Turno turnoSelezionato = lezione.getTurniDisponibili().getLast();
        // effettuiamo lo scambio dei turni
        lezione.effettuaScambioTurno(turnoCorrente, turnoSelezionato);
        // verifichiamo che il turno corrente sia stato aggiunto all'elenco dei turni disponibili
        assertTrue(lezione.getTurniDisponibili().contains(turnoCorrente));
        // verifichiamo che il turno selezionato sia stato rimosso dall'elenco dei turni disponibili
        assertFalse(lezione.getTurniDisponibili().contains(turnoSelezionato));        
    }
}
