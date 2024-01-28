package com.example;
import org.junit.jupiter.api.Test;
import domain_layer.Cane;
import domain_layer.Corso;
import domain_layer.Lezione;

import static org.junit.jupiter.api.Assertions.*;

class CorsoTest {
    // Verifica che il cane viene aggiunto alla lista dei cani iscritti al corso scelto
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
        
        //Ci asepttiamo che il valore restituito da setLezioneCorrente sia true e che lezioneCorrente sia uguale ad lz
        assertTrue(corsoBase.setLezioneCorrente(lz));
        assertEquals(lz, corsoBase.getLezioneCorrente());

         //Ci asepttiamo che il valore restituito da setLezioneCorrente sia false e che lezioneCorrente sia uguale ad null
         assertFalse(corsoBase.setLezioneCorrente(null));
         assertEquals(null, corsoBase.getLezioneCorrente());
    }







}