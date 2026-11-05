package com.example;
import org.junit.jupiter.api.Test;
import domain_layer.Cane;
import domain_layer.Corso;
import domain_layer.Lezione;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for course capacity, enrollment, lesson management, and shift counters.
 */
class CorsoTest {

    // Verifies that the dog is added to the selected course enrolled-dog list.
    @Test
    void testConfermaIscrizione() {
        Corso cs = new Corso(1,10,200.0F,"Corso Base");
        int lunghezza = cs.getCaniIscritti().size();
        cs.confermaIscrizione(new Cane(5,"Walker","Pastore Tedesco"));
        assertEquals(lunghezza + 1, cs.getCaniIscritti().size());
    }

    @Test
    void testAggiornaCapienza() {
        Corso cs = new Corso(1,10,200.0F,"Corso Base");
        cs.aggiornaCapienza();
        assertEquals(9, cs.getCapienza());
    }

    @Test
    void testSetLezioneCorrente(){
        Corso corsoBase = new Corso(1,10,200.0F,"Corso Base");
        Lezione lz = new Lezione(10, "Lezione test");

        // Expected: lezioneCorrente equals lz and the setter returns true.
        assertTrue(corsoBase.setLezioneCorrente(lz));
        assertEquals(lz, corsoBase.getLezioneCorrente());

         // Expected: lezioneCorrente is null and the setter returns false.
         assertFalse(corsoBase.setLezioneCorrente(null));
         assertEquals(null, corsoBase.getLezioneCorrente());
    }

    @Test
    void testAggiornaCaniIscritti(){
        // Verifies that the dog is removed from the course enrolled-dog list.
        // The dog has completed the last lesson.
        Corso corsoBase = new Corso(1,10,200.0F,"Corso Base");
        Cane cane = new Cane(10, "Luna", "Barboncino");
        // Enrolls the dog and verifies that it appears in the enrolled-dog list.
        corsoBase.confermaIscrizione(cane);
        assertTrue(corsoBase.getCaniIscritti().contains(cane));

        // Updates the enrolled-dog list and verifies that the dog is no longer present.
        corsoBase.aggiornaCaniIscritti(cane);
        assertFalse(corsoBase.getCaniIscritti().contains(cane));
    }

    @Test
    void testAnnullaIscrizione(){
        // Verifies that the dog is removed from the course enrolled-dog list.
        // The customer has removed the dog.
        Corso corsoBase = new Corso(1,10,200.0F,"Corso Base");
        Cane cane = new Cane(10, "Luna", "Barboncino");
        // Enrolls the dog in the course.
        corsoBase.confermaIscrizione(cane);

        // Updates the enrolled-dog list and verifies that the dog is no longer present.
        corsoBase.annullaIscrizione(cane);
        assertFalse(corsoBase.getCaniIscritti().contains(cane));
    }

    @Test
    void testAggiornaInformazioni(){
        // Verifies that the course information is updated.
        Corso corsoBase = new Corso(1,10,200.0F,"Corso Base");
        assertEquals(10, corsoBase.getCapienza());
        assertEquals(200.0F, corsoBase.getCosto());

        // Updates the course information and verifies the change.
        corsoBase.aggiornaInformazioni(20, 300.0F);
        assertEquals(20, corsoBase.getCapienza());
        assertEquals(300.0F, corsoBase.getCosto());
    }

    @Test
    void testNuovaLezione(){
        // Verifies that the new lesson is created and set as lezioneCorrente.
        Corso corsoBase = new Corso(1,10,200.0F,"Corso Base");
        corsoBase.nuovaLezione(10, "Lezione test");

        assertTrue(corsoBase.getLezioneCorrente().getNome().equals("Lezione test"));
    }

    @Test
    void testConfermaInserimentoLezione(){
        // Verifies that the new lesson is added to the course programme.
        Corso corsoBase = new Corso(1,10,200.0F,"Corso Base");
        corsoBase.nuovaLezione(10, "Lezione test");
        corsoBase.confermaInserimentoLezione();

        assertEquals(1, corsoBase.getProgramma().size());
    }

    @Test
    void testGeneraCodiceTurno(){
        // Verifies that the shift counter increases by one.
        Corso corsoBase = new Corso(1,10,200.0F,"Corso Base");
        corsoBase.generaCodiceTurno();
        assertEquals(1, corsoBase.getNumTurni());

        corsoBase.generaCodiceTurno();
        assertEquals(2, corsoBase.getNumTurni());
    }

}
