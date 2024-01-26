package com.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domain_layer.Cane;
import domain_layer.Cliente;
import domain_layer.Corso;
import domain_layer.PawBookings;
import domain_layer.PeriodoAffido;


import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

class PawBookingsTestIterazione2 {

    static PawBookings PB;

    // Il metodo viene eseguito prima di tutti i test
    // inizializza l'istanza di PawBookings
    // crea un'istanza di Cane e la seleziona
    @BeforeAll
    public static void initTest() {
        PB = PawBookings.getInstance();
        PB.registrati("Alberto", "Provenzano", "12345678", "0000");
        PB.aggiungiCane("Stella", "Pastore Tedesco");
        PB.aggiungiCane("Asso", "Corso");
        PB.logout();    
    }

    // Il metodo restituisce un'istanza della classe PawBookings se PB è null.
    @Test
    void testGetInstance() {
        assertNotNull(PB);
    }

    @Test
    void testVerificaIdoneitaPrenotazioneTurno() {
        // seguiamo l'accesso con un utene i cui cani non sono iscirtti a nessun corso
        PB.accedi("Alberto1", "0000");
        
        // simuliamo la selezione del cane
        PB.selezionaCane(PB.getClienteLoggato().getCane(1));
        
        // siccome "Stella", il cane il cui codice è 1, non è iscritta a nessun corso, 
        // il test deve restituire false 
        assertFalse(PB.verificaIdoneitaPrenotazioneTurno());

        // adesso iscriviamo il cane al corso base
        PB.confermaIscrizioneCorso(PB.elencoCorsi.get(0));

        // il test deve restituire true
        assertTrue(PB.verificaIdoneitaPrenotazioneTurno());
    }

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
        int lunghezzaPrecedente = PB.getClienti().size();
        int lunghezzaSuccessiva;

         // Il codiceCliente di Pippo deve essere Pippo2
         PB.registrati("Pippo", "Baudo", "12345678", "0000");
         // Il codiceCliente di Giuseppe deve essere Giuseppe3
         PB.registrati("Giuseppe", "Leocata", "562562", "0000");
         // Il codiceCliente di Daniele deve essere Daniele4
         PB.registrati("Daniele", "Lucifora", "9921319", "0000");

        lunghezzaSuccessiva = lunghezzaPrecedente + 3;

        // Ci asepttiamo che la lunghezza della lista dei clienti di PB sia aumentata di 3
        assertEquals(lunghezzaSuccessiva,PB.getClienti().size());
    }



    @Test
    void testGeneraCodiceCliente(){
        int numeroClienti = PB.getClienti().size();
        //Inserendo come Nome: "Francesco" ci aspettiamo che il codiceCliente generato sia
        // Francesco seguito dal valore dell'espressione numeroClienti + 1
        assertEquals("Francesco" + (numeroClienti+1), PB.generaCodiceCliente("Francesco"));
    }

    @Test
    void testAccedi(){
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

        // Ripristiniamo lo stato
         PB.logout();  

    }



    @Test
    void testLogout(){
        Cliente Alberto = PB.getClienti().get("Alberto1");
        Cane Stella =  PB.getClienti().get("Alberto1").getCane(1);
        PB.accedi("Alberto1", "0000");
        PB.selezionaCane(PB.getClienteLoggato().getCane(1));
        // In questo momento clienteLoggato è = Alberto
        // e caneSelezionato è = Stella
        assertEquals(Alberto, PB.getClienteLoggato());
        assertEquals(Stella, PB.getCaneSelezionato());

        // test del metodo
        // Con il logout ci aspettiamo che sia clienteLoggato che caneSelezionato diventino null
        PB.logout();
        assertEquals(null, PB.getClienteLoggato());
        assertEquals(null, PB.getCaneSelezionato());


    }



    @Test
    void testVerificaCliente(){
        Cliente Alberto = PB.getClienti().get("Alberto1");
         

         // Test del metodo 
         // Inserendo le credenziali corrette ci aspettiamo che il cliente restituito da verificaCliente sia Alberto
         assertEquals(Alberto, PB.verificaCliente("Alberto1", "0000"));

         //Inserendo le credenziali errate ci aspettiamo che il cliente restituito da verificaCliente non sia Alberto
         assertNotEquals(Alberto, PB.verificaCliente("Alberto1", "1234"));


    }




    @Test
    void testSetClienteLoggato(){
        Cliente Alberto = PB.getClienti().get("Alberto1"); 
        Cliente Giuseppe = PB.getClienti().get("Giuseppe3");
        //Test del metodo
        PB.setClienteLoggato(Alberto);
        // Ci aspettiamo che clienteLoggato sia uguale ad Alberto
        assertEquals(Alberto, PB.getClienteLoggato());
        // Ci aspettiamo che clienteLoggato non sia Pippo
        assertNotEquals(Giuseppe, PB.getClienteLoggato());

        // Ripristino lo stato iniziale
        PB.logout();
    }



    @Test
    void testAggiungiCane(){
        int numeroCaniSistemaPrecedente;
        int numeroCaniSistemaSuccessivo;
        int numeroCaniClientePrecedente;
        int numeroCaniClienteSuccessivo;
        PB.accedi("Alberto1", "0000");
        numeroCaniClientePrecedente = PB.getClienteLoggato().getCani().size();
        numeroCaniSistemaPrecedente = PB.getNumeroCani();
        PB.aggiungiCane("Maya", "Pastore Tedesco");
        numeroCaniSistemaSuccessivo = numeroCaniSistemaPrecedente +1;
        numeroCaniClienteSuccessivo = numeroCaniClientePrecedente + 1;

        // Verifico che la lunghezza della lista dei cani posseduti da Alberto è aumentata di 1
        assertEquals(numeroCaniClienteSuccessivo, PB.getClienteLoggato().getCani().size());

        // Verifico che la lista dei cani posseduti da Alberto contiene Maya
        Cane Maya = PB.getClienteLoggato().getCane(numeroCaniSistemaSuccessivo);
        assertTrue(PB.getClienteLoggato().getCani().contains(Maya));
    

    }


    @Test
    void testGeneraCodiceCane(){
        int numCodiceCanePrecedente;
        int numCodiceCaneSuccessivo;
        PB.accedi("Alberto1", "0000");
        // Il codiceCane corrisponde al valore della variabile di PB numCani
        numCodiceCanePrecedente = PB.getNumeroCani();
        numCodiceCaneSuccessivo = numCodiceCanePrecedente + 1;
        // Verifichiamo che chiamando il metodo generaCodiceCane viene incrementato di 1 il codiceCane 
        assertEquals(numCodiceCaneSuccessivo, PB.generaCodiceCane());
    }
    

    
    @Test
    void testRimuoviCane(){
        // Il metodo rimuoviCane deve restituire la lista dei Cani del Cliente che non si trovano attualmente in Affido
        PB.accedi("Alberto1", "0000");
        LinkedList<Cane> caniNonInAffido = PB.rimuoviCane();
        for(Cane cn: caniNonInAffido){
            assertFalse(cn.getAttualmenteInAffido());
        }

    }


    @Test
    void confermaRimozioneCane(){
        int numCani;
        PB.accedi("Alberto1", "0000");
        PB.aggiungiCane("Aki","Pitbull");
        numCani = PB.getClienteLoggato().getCani().size();
        PB.selezionaCane(PB.getClienteLoggato().getCani().get(numCani-1));
        Cane Aki = PB.getCaneSelezionato();
        Corso corsoBase = PB.getCorsi().get(0);
        PB.confermaIscrizioneCorso(corsoBase);
        assertTrue(corsoBase.getCaniIscritti().contains(Aki));
        
        // Viene verificato che il Cane rimosso se partecipava ad un corso abbia annullato l'iscrizione a tale corso
        PB.confermaRimozioneCane(Aki);
        assertFalse(corsoBase.getCaniIscritti().contains(Aki));

        // Viene verificato che il cane rimosso non appartenga più alla lista dei cani posseduti dal cliente
        assertFalse(PB.getClienteLoggato().getCani().contains(Aki));
    

    }


    // Il metodo da testare restituisce un elenco di cani che non sono attualmente in affido
    @Test
    void testSelezionaPeriodo(){

        Cliente Alberto = PB.getClienti().get("Alberto1"); 
        /*
         * Cani di Alberto:
         * PB.aggiungiCane("Stella", "Pastore Tedesco");
         * PB.aggiungiCane("Asso", "Corso");
         */

         // Simuliamo l'accesso del Cliente Alberto
         PB.accedi("Alberto1", "0000");


        //simuliamo l'affido di uno dei cani
        LinkedList<PeriodoAffido> lp = PB.affido();
        PB.selezionaPeriodo(lp.get(0));       
        PB.confermaAffido(Alberto.getCane(2));

        // verifichiamo che nella lista restituita da selezionaPeriodo non sia presente il cane messo in affido prima
        LinkedList<Cane> elencoCaniNonInAffido = PB.selezionaPeriodo(PB.affido().get(0));
        for (Cane cane : elencoCaniNonInAffido) {
            assertFalse(cane.attualmenteInAffido);
        }

        // viene anche testato che periodoSlezionato sia uguale a PB.affido().get(0)
        assertEquals(lp.get(0), PB.getPeriodoSelezionato());

        // Ripristiniamo lo stato iniziale
        PB.logout();

    }


    @Test
    void testConfermaAffido(){
        //Recuperiamo la lista di periodi disponibili
        LinkedList<PeriodoAffido> lp= PB.affido();
        LinkedList<Cane> caniNonInAffido;

        // Prendiamo il primo periodo e affidiamo 3 cani
        PB.accedi("Alberto1", "0000");
        PB.aggiungiCane("Max", "Labrador");
        PB.aggiungiCane("Kia", "Barboncino");
        PB.aggiungiCane("Nettuno", "Pitbull");

        // Viene settato il periodo selezionato
        caniNonInAffido = PB.selezionaPeriodo(lp.get(1));

        // Test del metodo
        PB.confermaAffido(caniNonInAffido.get(1));
        PB.confermaAffido(caniNonInAffido.get(2));
        PB.confermaAffido(caniNonInAffido.get(3));
        PB.logout();

        PB.registrati("Mario", "Rossi", "123456", "0000");
        PB.aggiungiCane("Rex", "Pastore Tedesco");
        PB.aggiungiCane("Maya", "Pastore Tedesco");
        caniNonInAffido = PB.selezionaPeriodo(lp.get(1));
        PB.confermaAffido(caniNonInAffido.get(0));  
        Cane Rex = caniNonInAffido.get(0);
        Cane Maya = caniNonInAffido.get(1);

        // ci aspettiamo che elenco cani affido del periodo selezionato contenga 4 Cani
        assertEquals(4, PB.getPeriodoSelezionato().getCaniAffido().size());
        // ci aspettiamo che contenga Rex
        assertTrue( PB.getPeriodoSelezionato().getCaniAffido().contains(Rex));
        // ci aspettiamo che il numero di posti disponibili sia 1
        assertEquals(1, PB.getPeriodoSelezionato().getNumeroPosti());

        // Simuliamo l'affido di un nuovo cane tramite confermaAffido 
        PB.confermaAffido(Maya);
        // ci aspettiamo che il numero di posti diventi 0
        assertEquals(0, PB.getPeriodoSelezionato().getNumeroPosti());
        // ci aspettiamo che il periodo selezionato non appartenga più all'elenco di periodi disponibili di PB
        assertFalse(PB.affido().contains(PB.getPeriodoSelezionato()));      

        // Ripristiniamo lo stato iniziale
        PB.logout();

    }




    @Test
    void testConcludiAffido(){
        PB.accedi("Alberto1","0000");
        // Il codice sarà 3
        PB.aggiungiCane("Natan", "Pitbull");

        // Iscriviamo Natan al Periodo 3
        LinkedList<PeriodoAffido> lp = PB.affido();
        PB.selezionaPeriodo(lp.get(2));
        Cane Natan = PB.getClienti().get("Alberto1").getCane(3);
        PB.confermaAffido(Natan);
        PeriodoAffido pa;
        // Testiamo il metodo
        pa = PB.concludiAffido("Alberto1", 3);

        // ci aspettiamo che cane selezionato corrisponda ad Natan
        assertEquals(Natan, PB.getCaneSelezionato());

        // ci aspettiamo che l'affido che stiamo concludendo sia il periodo 3
        assertEquals(lp.get(2), pa);


    }


    @Test
    void testConfermaConclusioneAffido(){
        
        // Simuliamo l'iscrizone di un cane ad un periodo di affido
        PB.accedi("Alberto1","0000");
      
        // Iscriviamo Stella al Periodo 3
        LinkedList<PeriodoAffido> lp = PB.affido();
        // tramite selezionaPeriodo viene settato periodoSelezionato con Periodo 3
        PB.selezionaPeriodo(lp.get(2));
        Cane Stella = PB.getClienti().get("Alberto1").getCane(1);
        PB.confermaAffido(Stella);
        PeriodoAffido pa;
      
        // tramite concludiAffido settiamo il caneSelezionato con stella
        pa = PB.concludiAffido("Alberto1", 1);


        // Test del metodo

        PB.confermaConclusioneAffido();

        // Ci aspettiamo che Stella venga rimossa dall'elenco caniAffido del periodo 2
        assertFalse( PB.getPeriodoSelezionato().getCaniAffido().contains(Stella));

        // Ci aspettiamo che l'attributo attualmenteInAffido di Stella diventi false
        assertFalse(Stella.getAttualmenteInAffido());

        // Ci aspettiamo che affidoCorrente di Stella diventi null
        assertEquals(null, Stella.getAffidoCorrente());

    }







  
}