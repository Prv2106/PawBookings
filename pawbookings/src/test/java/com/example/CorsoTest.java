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


    @Test
    void testAggiornaCaniIscritti(){
        // Verifica che il cane venga rimosso dall'elenco dei cani iscritti al corso
        // poichè ha completato l'ultima lezione
        Corso corsoBase = new Corso(1,10,200.0F,"Corso Base");
        Cane cane = new Cane(10, "Luna", "Barboncino");
        // iscriviamo il cane al corso e verifichiamo che sia presente nella lista dei cani iscritti
        corsoBase.confermaIscrizione(cane);
        assertTrue(corsoBase.getCaniIscritti().contains(cane));

        // aggiorniamo la lista dei cani iscritti al corso e verifichiamo che il cane non sia più presente
        corsoBase.aggiornaCaniIscritti(cane);
        assertFalse(corsoBase.getCaniIscritti().contains(cane));
    }    


    @Test
    void testAnnullaIscrizione(){
        // Verifica che il cane venga rimosso dall'elenco dei cani iscritti al corso
        // poichè il cliente ha rimosso il proprio cane 
        Corso corsoBase = new Corso(1,10,200.0F,"Corso Base");
        Cane cane = new Cane(10, "Luna", "Barboncino");
        // iscriviamo il cane al corso 
        corsoBase.confermaIscrizione(cane);

        // aggiorniamo la lista dei cani iscritti al corso e verifichiamo che il cane non sia più presente
        corsoBase.annullaIscrizione(cane);
        assertFalse(corsoBase.getCaniIscritti().contains(cane));
    }

    @Test
    void testAggiornaInformazioni(){
        // Verifica che le informazioni del corso vengano aggiornate
        Corso corsoBase = new Corso(1,10,200.0F,"Corso Base");
        assertEquals(10, corsoBase.getCapienza());
        assertEquals(200.0F, corsoBase.getCosto());
        
        // aggiorniamo le informazioni del corso e verifichiamo che siano state aggiornate
        corsoBase.aggiornaInformazioni(20, 300.0F);
        assertEquals(20, corsoBase.getCapienza());
        assertEquals(300.0F, corsoBase.getCosto());
    }

    @Test
    void testNuovaLezione(){
        // Verifica che venga creata la nuova lezione e che la nuova lezione venga settata come lezioneCorrente
        Corso corsoBase = new Corso(1,10,200.0F,"Corso Base");
        corsoBase.nuovaLezione(10, "Lezione test");
        
        assertTrue(corsoBase.getLezioneCorrente().getNome().equals("Lezione test"));
    }

    @Test
    void testConfermaInserimentoLezione(){
        // Verifica che la nuova lezione venga aggiunta al programma del corso
        Corso corsoBase = new Corso(1,10,200.0F,"Corso Base");
        corsoBase.nuovaLezione(10, "Lezione test");
        corsoBase.confermaInserimentoLezione();

        assertEquals(1, corsoBase.getProgramma().size());
    }



    @Test
    void testGeneraCodiceTurno(){
        // Verifica che il numero turni venga incrementato di 1
        Corso corsoBase = new Corso(1,10,200.0F,"Corso Base");
        corsoBase.generaCodiceTurno();
        assertEquals(1, corsoBase.getNumTurni());

        corsoBase.generaCodiceTurno();
        assertEquals(2, corsoBase.getNumTurni());
    }


}