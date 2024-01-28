package com.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domain_layer.Cane;
import domain_layer.Corso;
import domain_layer.PawBookings;
import domain_layer.Turno;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;


/*
 * Test dei casi d'uso UC1-UC2
 */


class PawBookingsTest1 {

    static PawBookings PB;
    // Il metodo viene eseguito prima di tutti i test
    // inizializza l'istanza di PawBookings
    // crea un'istanza di Cane e la seleziona
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
        // Recuperiamo il turno delle 9 della lezione 1 del corsoBase
        Corso corsoBase = PB.modificaProgrammaCorso().get(0);
        Turno ts = corsoBase.getLezioni().get(0).getTurniDisponibili().get(0);
        assertTrue(PB.selezionaTurno(ts));  
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