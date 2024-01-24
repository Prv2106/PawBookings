package com.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domain_layer.Cane;
import domain_layer.Corso;
import domain_layer.PawBookings;
import static org.junit.jupiter.api.Assertions.*;
import java.util.LinkedList;

class PawBookingsTest {

    static PawBookings PB;
    // Il metodo viene eseguito prima di tutti i test
    // inizializza l'istanza di PawBookings
    // crea un'istanza di Cane e la seleziona
    @BeforeAll
    public static void initTest() {
        //Configurazioni che ci servono prima dell'esecuzione dei metodi di test
        PB = PawBookings.getInstance();
        PB.selezionaCane(new Cane(10, "Luna", "Barboncino"));
        // Luna è iscritta al corso Base
        PB.confermaIscrizioneCorso(PB.elencoCorsi.get(0));
    }

    // Il metodo restituisce un'istanza della classe PawBookings se PB è null.
    @Test
    void testGetInstance() {
        assertNotNull(PB);
    }
    
    // Test Whitebox
    // Il metodo verifica che la lista dei corsi disponibili contenga corsi 
    // la cui capienza sia effettivamente maggiore di 0
    void testNuovaIscrizioneCorso() {
        LinkedList<Corso> elencoCorsi = new LinkedList<>();
        elencoCorsi = PB.nuovaIscrizioneCorso();
        for (Corso corso : elencoCorsi) {
            assertTrue(corso.getCapienza() > 0);
        }
    }

    // Viene fatto un controllo sul valore booleano restituito relativamente al turno passato come parametro al metodo selezionaTurno
    @Test
    void testSelezionaTurno() {
        assertFalse(PB.selezionaTurno(null));
        assertTrue(PB.selezionaTurno( PB.getCorsi().get(0).getLezioni().get(0).getTurniDisponibili().get(0)));  
    }

    // Test Whitebox 
    // Il metodo restituisce un'istanza della classe Cane se il parametro cane è diverso da null 
    // ed inoltre verifica che il nome del cane sia uguale a "Luna"
    @Test
    void testSelezionaCane() {
        assertNotNull(PB.getCaneSelezionato());
        assertEquals("Luna", PB.getCaneSelezionato().getNome());
    }



  
}