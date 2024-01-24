package com.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domain_layer.Cane;
import domain_layer.Cliente;
import domain_layer.Corso;
import domain_layer.CorsoBase;
import domain_layer.PawBookings;
import domain_layer.PeriodoAffido;
import domain_layer.Turno;

import static org.junit.jupiter.api.Assertions.*;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

class PawBookingsTestIterazione2 {

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


    /***    TEST PER ITERAZIONE 2   ***/



    @Test
    void testAccediComeAdmin(){
        // Inserendo Pin corretto -> 1234
        // Ci asepttiamo che il metodo accediComeAdmin restituisca true
        assertTrue(PB.accediComeAdmin(1234));

        // Inserendo un pin errato
        // Ci asepttiamo che il metodo accediComeAdmin restituisca false
        assertFalse(PB.accediComeAdmin(0232));
    }


    @Test
    void testRegistrati(){
         // Il codiceCliente di Alberto deve essere Alberto1
         PB.registrati("Alberto", "Provenzano", "12345678", "0000");
         // Il codiceCliente di Giuseppe deve essere Giuseppe2
         PB.registrati("Giuseppe", "Leocata", "562562", "0000");
         // Il codiceCliente di Daniele deve essere Daniele3
         PB.registrati("Daniele", "Lucifora", "9921319", "0000");

        
        // Ci asepttiamo che la lunghezza della lista dei clienti di PB sia 3
        assertEquals(3, PB.getClienti().size());

        Cliente Alberto = PB.getClienti().get("Alberto1");
        Cliente Giuseppe = PB.getClienti().get("Giuseppe2");
        Cliente Daniele = PB.getClienti().get("Daniele3");

        // Verifichiamo che i codici clienti siano come ci aspettavamo
        assertEquals("Alberto1",Alberto.getCodiceCliente());
        assertEquals("Giuseppe2",Giuseppe.getCodiceCliente());
        assertEquals("Daniele3",Daniele.getCodiceCliente());

       // Verifichiamo che ciascun cliente appena creato abbia a sua volta creato una lista di cani
       assertNotEquals(null, Alberto.getCani());
       assertNotEquals(null, Giuseppe.getCani());
       assertNotEquals(null, Daniele.getCani());

    }

    @Test
    void testAccedi(){

        // Il codice per accedere con Alberto sarà Alberto1
        PB.registrati("Alberto", "Provenzano", "12345678", "0000");
        PB.registrati("Pippo", "Baudo", "12345678", "0000");
        Cliente Alberto = PB.getClienti().get("Alberto1");
        Boolean esito;

        // test del metodo
        // Accedendo con codiceCliente Alberto1 e password 0000
        esito = PB.accedi("Alberto1", "0000");  

        // Ci aspettiamo che il valore restituito da accedi sia true
        assertTrue(esito);

        // Ci aspettiamo che ClienteLoggato ==  Alberto
        assertEquals(Alberto, PB.getClienteLoggato());

         // Accedendo con codiceCliente Alberto1 e password 0323
         esito = PB.accedi("Alberto1", "0323");
         assertFalse(esito);

         // Ci aspettiamo che ClienteLoggato ==  null
         assertEquals(null, PB.getClienteLoggato());

    }


    @Test
    void testVerificaCliente(){
         // Il codice per accedere con Alberto sarà Alberto1
         PB.registrati("Alberto", "Provenzano", "12345678", "0000");
         PB.registrati("Pippo", "Baudo", "12345678", "0000");

         Cliente Alberto = PB.getClienti().get("Alberto1"); 

         // Test del metodo 
         // Inserendo le credenziali corrette ci aspettiamo che il cliente restituito da verificaCliente sia Alberto
         assertEquals(Alberto, PB.verificaCliente("Alberto1", "0000"));

         //Inserendo le credenziali errate ci aspettiamo che il cliente restituito da verificaCliente non sia Alberto
         assertNotEquals(Alberto, PB.verificaCliente("Alberto1", "1234"));


    }




    @Test
    void testSetClienteLoggato(){
        PB.registrati("Alberto", "Provenzano", "12345678", "0000");
        PB.registrati("Pippo", "Baudo", "12345678", "0000");
        Cliente Alberto = PB.getClienti().get("Alberto1"); 
        Cliente Pippo = PB.getClienti().get("Pippo2"); 
        //Test del metodo
        PB.setClienteLoggato(Alberto);
        // Ci aspettiamo che clienteLoggato sia uguale ad Alberto
        assertEquals(Alberto, PB.getClienteLoggato());
        // Ci aspettiamo che clienteLoggato non sia Pippo
        assertNotEquals(Pippo, PB.getClienteLoggato());


    }



    @Test
    void testAggiungiCane(){
        PB.registrati("Alberto", "Provenzano", "12345678", "0000");
        PB.aggiungiCane("Stella", "Pastore Tedesco");
        PB.aggiungiCane("Asso", "Corso");

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
        // Test del metodo

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

        // ci aspettiamo che l'affido che stiamo concludendo sia il periodo 1
        assertEquals(lp.get(0), pa);


    }


    @Test
    void testConfermaConclusioneAffido(){
        
        // Simuliamo l'iscrizone di un cane ad un periodo di affido
        PB.registrati("Daniele", "Lucifora", "9921319", "0000");
        PB.accedi("Daniele1", "0000");
        // Il codice sarà 1
        PB.aggiungiCane("Stella", "Pastore Tedesco");
        // Il codice sarà 2
        PB.aggiungiCane("Asso", "Corso");

        // Iscriviamo Stella al Periodo 2
        LinkedList<PeriodoAffido> lp = PB.affido();
        PB.selezionaPeriodo(lp.get(1));

        // tramite selezionaPeriodo viene settato periodoSelezionato con Periodo2
        Cane stella = PB.getClienti().get("Daniele1").getCane(1);
        PB.confermaAffido(stella);
        PeriodoAffido pa;
        
        // tramite concludiAffido settiamo il caneSelezionato con stella
        pa = PB.concludiAffido("Daniele1", 1);


        // Test del metodo

        PB.confermaConclusioneAffido();

        // Ci aspettiamo che Stella venga rimossa dall'elenco caniAffido del periodo 2
        assertFalse( PB.getPeriodoSelezionato().getCaniAffido().contains(stella));

        // Ci aspettiamo che l'attributo attualmenteInAffido di Stella diventi false
        assertFalse(stella.getAttualmenteInAffido());

        // Ci aspettiamo che affidoCorrente di Stella diventi null
        assertEquals(null, stella.getAffidoCorrente());

    }







  
}