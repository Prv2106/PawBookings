package com.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import domain_layer.Cane;
import domain_layer.Cliente;
import domain_layer.Corso;
import domain_layer.CorsoAgility;
import domain_layer.CorsoAvanzato;
import domain_layer.CorsoBase;
import domain_layer.PawBookings;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.LinkedList;

class PawBookingsTest {

    static PawBookings PB;

    // Il metodo viene eseguito prima di tutti i test
    // inizializza l'istanza di PawBookings
    // crea un'istanza di Cane e la seleziona
    @BeforeAll
    public static void initTest() {
        PB = PawBookings.getInstance();
        PB.selezionaCane(new Cane(1, "Luna", "Barboncino"));
    }

    // Il metodo restituisce un'istanza della classe PawBookings se PB è null.
    @Test
    void testGetInstance() {
        assertNotNull(PB);
    }

    // Il metodo crea con successo tre istanze della classe Cliente.
    @Test
    void testLoadClienti() {
        PB.loadClienti();
        Map<Integer, Cliente> clienti = PB.getClienti();
    
        assertEquals(3, clienti.size());
        assertTrue(clienti.containsKey(1));
        assertTrue(clienti.containsKey(2));
        assertTrue(clienti.containsKey(3));
    }

    @Test
    void testLoadCorsi() {
        LinkedList<Corso> elencoCorsi = PB.elencoCorsi;

        assertEquals(3, elencoCorsi.size());
        assertTrue(elencoCorsi.stream().anyMatch(corso -> corso instanceof CorsoBase));
        assertTrue(elencoCorsi.stream().anyMatch(corso -> corso instanceof CorsoAvanzato));
        assertTrue(elencoCorsi.stream().anyMatch(corso -> corso instanceof CorsoAgility));
    }

    // Test Whitebox
    // Il metodo verifica che la lista dei corsi disponibili contenga corsi 
    // la cui capienza sia effettivamente maggiore di 0
    void nuovaIscrizioneCorso() {
        LinkedList<Corso> elencoCorsi = new LinkedList<>();
        elencoCorsi = PB.nuovaIscrizioneCorso();
    
        for (Corso corso : elencoCorsi) {
            assertTrue(corso.getCapienza() > 0);
        }

    }

    @Test
    void confermaIscrizioneCorso() {        
        assertFalse(PB.confermaIscrizioneCorso(null));
        assertTrue(PB.confermaIscrizioneCorso(new CorsoBase(1, 10, 200.0F, "Corso Base")));
    }

    @Test
    void prenotaTurnoLezione() {
    }

    @Test
    void selezionaTurno() {
    }

    // Test Whitebox 
    // Il metodo restituisce un'istanza della classe Cane se il parametro cane è diverso da null 
    // ed inoltre verifica che il nome del cane sia uguale a "Luna"
    @Test
    void testSelezionaCane() {
        assertNotNull(PB.getCaneSelezionato());
        assertEquals("Luna", PB.getCaneSelezionato().getNome());
    }

    @Test
    void getClienti() {
    }

    @Test
    void getCaneSelezionato() {
    }
}