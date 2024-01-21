package com.example;

import org.junit.jupiter.api.Test;

import domain_layer.Cliente;
import domain_layer.PawBookings;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

class PawBookingsTest {

    @Test
    void testGetInstance() {
        PawBookings PB = PawBookings.getInstance();
        assertNotNull(PB);
    }

    // Il metodo crea con successo tre istanze della classe 'Cliente'.
    @Test
    void testLoadClienti() {
        PawBookings PB = PawBookings.getInstance();
        PB.loadClienti();
        Map<Integer, Cliente> clienti = PB.getClienti();
    
        assertEquals(3, clienti.size());
        assertTrue(clienti.containsKey(1));
        assertTrue(clienti.containsKey(2));
        assertTrue(clienti.containsKey(3));
    }

    @Test
    void testLoadCorsi() {
        
    }

    @Test
    void nuovaIscrizioneCorso() {
    }

    @Test
    void confermaIscrizioneCorso() {
    }

    @Test
    void prenotaTurnoLezione() {
    }

    @Test
    void selezionaTurno() {
    }

    @Test
    void selezionaCane() {
    }

    @Test
    void getClienti() {
    }

    @Test
    void getCaneSelezionato() {
    }
}