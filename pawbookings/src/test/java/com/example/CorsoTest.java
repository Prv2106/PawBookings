package com.example;
import org.junit.jupiter.api.Test;
import domain_layer.Cane;
import domain_layer.Corso;
import domain_layer.CorsoBase;
import static org.junit.jupiter.api.Assertions.*;

class CorsoTest {

    // Verifica che il cane viene aggiunto alla lista dei cani iscritti al corso scelto
    @Test
    void testConfermaIscrizione() {
        Corso cs = new CorsoBase(1,10,200.0F,"Corso Base");
        int lunghezza = cs.getCaniIscritti().size();
        cs.confermaIscrizione(new Cane(5,"Walker","Pastore Tedesco"));
        assertEquals(lunghezza + 1, cs.getCaniIscritti().size());     
    }

    @Test
    void testAggiornaCapienza() {
        Corso cs = new CorsoBase(2,10,200.0F,"Corso Base");
        cs.aggiornaCapienza();
        assertEquals(9, cs.getCapienza());
    }
}
