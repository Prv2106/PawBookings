package com.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domain_layer.Cane;

import domain_layer.Corso;
import domain_layer.CorsoBase;
import domain_layer.PawBookings;
import domain_layer.PeriodoAffido;
import domain_layer.Turno;

import static org.junit.jupiter.api.Assertions.*;


import java.time.LocalDate;
import java.time.LocalTime;
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


    @Test
    void testLoadCorsi() {
        // il costruttore della classe singleton PB chiama il metodo "loadCorsi"
        // il quale crea 3 corsi, pertanto verifichiamo che la lunghezza combacia
        LinkedList<Corso> elencoCorsi = PB.elencoCorsi;

        assertEquals(3, elencoCorsi.size());
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

    @Test
    void testConfermaIscrizioneCorso() {        
        assertFalse(PB.confermaIscrizioneCorso(null));
        assertTrue(PB.confermaIscrizioneCorso(new CorsoBase(1, 10, 200.0F, "Corso Base")));
    }

    // Viene fatto un controllo sul valore booleano restituito relativamente al turno passato come parametro al metodo selezionaTurno
    @Test
    void testSelezionaTurno() {
        // Per eseguire il test viene effettuata l'iscrizione di caneSelezionato ad un Corso, ad esempio Corso Base
        PB.getCaneSelezionato().aggiornaAttualmenteIscritto(PB.elencoCorsi.get(0));
        assertFalse(PB.selezionaTurno(null));
        assertTrue(PB.selezionaTurno(new Turno(LocalDate.of(2024, 1, 20), LocalTime.of(9, 0), LocalTime.of(10, 0))));  
        
    }

    // Test Whitebox 
    // Il metodo restituisce un'istanza della classe Cane se il parametro cane è diverso da null 
    // ed inoltre verifica che il nome del cane sia uguale a "Luna"
    @Test
    void testSelezionaCane() {
        assertNotNull(PB.getCaneSelezionato());
        assertEquals("Luna", PB.getCaneSelezionato().getNome());
    }

    // Il metodo da testare restituisce un elenco di cani che non sono attualmente in affido
    @Test
    void testSelezionaPeriodo(){

        // Simuliamo la registrazione di un utente e dei suoi cani
        PB.registrati("Giuseppe", "Leocata", "562562", "0000");
        PB.accedi("Giuseppe1", "0000");
        PB.aggiungiCane("Sole", "Barboncino");
        PB.aggiungiCane("Luna", "Rottweiler");
        PB.aggiungiCane("Vanessa", "Labrador");
        //simuliamo l'affido di uno dei cani
        LinkedList<PeriodoAffido> lp = PB.affido();
        PB.selezionaPeriodo(lp.get(0));       
        PB.confermaAffido(PB.getClienti().get("Giuseppe1").getCane(1));

        // verifichiamo che nella lista restituita da selezionaPeriodo non sia presente il cane messo in affido prima
        LinkedList<Cane> elencoCaniNonInAffido = PB.selezionaPeriodo(PB.affido().get(0));
        for (Cane cane : elencoCaniNonInAffido) {
            assertFalse(cane.attualmenteInAffido);
        }


        // viene anche testato che periodoSlezionato sia uguale a PB.affido().get(0)
        assertEquals(lp.get(0), PB.getPeriodoSelezionato());

    }


    @Test
    void testConfermaAffido(){
        //Recuperiamo la lista di periodi disponibili
        LinkedList<PeriodoAffido> lp= PB.affido();

        // Prendiamo il primo periodo e affidiamo 3 cani
        lp.get(1).getCaniAffido().add(new Cane(1,"Stella", "Pastore Tedesco"));
        lp.get(1).getCaniAffido().add(new Cane(2,"Walker", "Pastore Tedesco"));
        lp.get(1).getCaniAffido().add(new Cane(3,"Sole", "Barboncino"));

        // settiamo il periodo selezionato
        PB.setPeriodoSelezionato(lp.get(1));

        Cane nuovoCane = new Cane(4,"Rex","Pastore Tedesco");

        // Simuliamo l'affido di un nuovo cane tramite confermaAffido
        PB.confermaAffido(nuovoCane);

        // ci aspettiamo che elenco cani affido del periodo selezionato contenga 4 Cani
        assertEquals(4, PB.getPeriodoSelezionato().getCaniAffido().size());

        // ci aspettiamo che contenga Rex
        assertTrue( PB.getPeriodoSelezionato().getCaniAffido().contains(nuovoCane));

        // ci aspettiamo che il numero di posti disponibili sia 1
        assertEquals(1, PB.getPeriodoSelezionato().getNumeroPosti());

        // Simuliamo l'affido di un nuovo cane tramite confermaAffido 
        nuovoCane = new Cane(5,"Vanessa","Barboncino");
        PB.confermaAffido(nuovoCane);
        // ci aspettiamo che il numero di posti diventi 0
        assertEquals(0, PB.getPeriodoSelezionato().getNumeroPosti());
        // ci aspettiamo che il periodo selezionato non appartenga più all'elenco di periodi disponibili di PB
        assertFalse(PB.affido().contains(PB.getPeriodoSelezionato()));      



    }




    @Test
    void testConcludiAffido(){

        PB.registrati("Daniele", "Lucifora", "9921319", "0000");
        PB.accedi("Daniele1", "0000");
        // Il codice sarà 1
        PB.aggiungiCane("Stella", "Pastore Tedesco");
        // Il codice sarà 2
        PB.aggiungiCane("Asso", "Corso");

        // Iscriviamo Asso al Periodo 1
        LinkedList<PeriodoAffido> lp = PB.affido();
        PB.selezionaPeriodo(lp.get(0));
        Cane asso = PB.getClienti().get("Daniele1").getCane(2);
        PB.confermaAffido(asso);
        PeriodoAffido pa;
        // Testiamo il metodo
        pa = PB.concludiAffido("Daniele1", 2);

        // ci aspettiamo che cane selezionato corrisponda ad asso
        assertEquals(asso, PB.getCaneSelezionato());

        // ci aspettiamo che l'affido che stiamo rimuovendo sia il periodo 1
        assertEquals(lp.get(0), pa);


    }

  
}