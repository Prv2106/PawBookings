package com.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domain_layer.Cane;
import domain_layer.Cliente;
import domain_layer.Corso;
import domain_layer.PawBookings;
import domain_layer.PeriodoAffido;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

/**
 * Integration tests for authentication, foster care, and dog management (UC3-UC6).
 */
class PawBookingsTest2 {

    static PawBookings PB;

    // Runs before all tests.
    // Initializes the PawBookings instance.
    @BeforeAll
    public static void initTest() {
        PB = PawBookings.getInstance();
        PB.registrati("Alberto", "Provenzano", "156322345678", "0000");
        PB.aggiungiCane("Stella", "Pastore Tedesco");
        PB.aggiungiCane("Asso", "Corso");
        PB.logout();

        PB.inserisciNuovoCorso("Corso Base", 10, 200.0F);
        Corso corsoBase = PB.modificaProgrammaCorso().get(0);
        PB.selezionaCorso(corsoBase);

        // The lesson code is 1.
        PB.nuovaLezione("Comandi di Base");
        PB.inserisciEsercizio("Saluto amichevole", "Questo esercizio aiuta a promuovere una socializzazione positiva tra cani");
        PB.inserisciEsercizio("Resta", "'Resta'per rimanere in posizione, aumentando gradualmente la distanza dal proprietario");
        PB.inserisciEsercizio("Richiamo", "Insegna al cane a rispondere al comando di richiamo del padrone");
        PB.confermaLezione();

        // The lesson code is 2.
        PB.nuovaLezione("Guinzaglio e Camminare al Guinzaglio");
        PB.inserisciEsercizio("Introduzione al Guinzaglio", "Insegnare ai proprietari come presentare il guinzaglio al cane in modo positivo, facendolo abituare gradualmente alla sensazione e premiando il comportamento calmo e collaborativo.");
        PB.inserisciEsercizio("Camminata Focalizzata", "Praticare una camminata controllata in cui il cane cammina al fianco del proprietario senza tirare al guinzaglio. Utilizzare comandi verbali e premi per incoraggiare un comportamento desiderato.");
        PB.inserisciEsercizio("Affrontare Distrazioni", "Introdurre gradualmente distrazioni durante la camminata al guinzaglio, come altri cani o stimoli ambientali. Insegnare ai proprietari come gestire le situazioni, mantenendo il controllo del cane e premiando il comportamento desiderato in presenza di distrazioni.");
        PB.confermaLezione();

        // Adds shifts.
        PB.selezionaCorsoModificaTurni(corsoBase);
        PB.selezionaLezione(corsoBase.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
    }

    // Returns the singleton PawBookings instance.
    @Test
    void testGetInstance() {
        assertNotNull(PB);
    }

    // Tests loadPeriodiAffido, which is called by the constructor.
    @Test
    void testLoadPeriodiAffido(){
        // Creates the expected PeriodoAffido instances.
        LinkedList<PeriodoAffido> expected = new LinkedList<>();

        // These are the instances created by loadPeriodiAffido.
        PeriodoAffido p1 = new PeriodoAffido(1, LocalDate.now(), LocalDate.now().plusWeeks(2), 150.0f);
        PeriodoAffido p2 = new PeriodoAffido(2, LocalDate.now().plusWeeks(2), LocalDate.now().plusMonths(1), 300.0f);
        PeriodoAffido p3 = new PeriodoAffido(3,LocalDate.now().plusMonths(1) ,LocalDate.now().plusMonths(2), 600.0f);
        PeriodoAffido p4 = new PeriodoAffido(4, LocalDate.of(2024, 1, 21), LocalDate.of(2024, 2, 11), 210);
        PeriodoAffido p5 = new PeriodoAffido(0, LocalDate.of(2024, 1, 8), LocalDate.of(2024, 2, 5), 280);

        expected.add(p1);
        expected.add(p2);
        expected.add(p3);
        expected.add(p4);
        expected.add(p5);

        // Expected: the period list has the expected length.
        assertEquals(expected.size(), PB.getPeriodiAffido().size());
        // Expected: the available-period list has the expected length.
        assertEquals(expected.size(), PB.getPeriodiAffidoDisponibili().size());

        boolean areEquals = true;

        // Expected: the period list contains the expected periods.
        LinkedList<PeriodoAffido> elencoPeriodi = PB.getPeriodiAffido();

        for (int i = 0; i < elencoPeriodi.size(); i++) {
            if (elencoPeriodi.get(i).getCodice() != expected.get(i).getCodice()) {
                areEquals = false;
            }
        }

        assertTrue(areEquals);

        // Expected: the available-period list contains the expected periods.
        LinkedList<PeriodoAffido> elencoPeriodiDisponibili = PB.getPeriodiAffidoDisponibili();

        for (int i = 0; i < elencoPeriodiDisponibili.size(); i++) {
            if (elencoPeriodi.get(i).getCodice() != expected.get(i).getCodice()) {
                areEquals = false;
            }
        }

        assertTrue(areEquals);
    }

    @Test
    void testAccediComeAdmin(){
        // Enters the correct PIN, 1234.
        // Expected: accediComeAdmin returns true.
        assertTrue(PB.accediComeAdmin(1234));

        // Enters an incorrect PIN.
        // Expected: accediComeAdmin returns false.
        assertFalse(PB.accediComeAdmin(0232));
    }

    @Test
    void testRegistrati(){
        int lunghezzaPrecedente = PB.getClienti().size();
        int lunghezzaSuccessiva;

         // Pippo is assigned customer code Pippo2.
         PB.registrati("Pippo", "Baudo", "12345678", "0000");
         // Giuseppe is assigned customer code Giuseppe3.
         PB.registrati("Giuseppe", "Leocata", "562562", "0000");
         // Daniele is assigned customer code Daniele4.
         PB.registrati("Daniele", "Lucifora", "9921319", "0000");

        lunghezzaSuccessiva = lunghezzaPrecedente + 3;

        // Expected: the customer list grows by three.
        assertEquals(lunghezzaSuccessiva,PB.getClienti().size());
    }

    @Test
    void testGeneraCodiceCliente(){
        int numeroClienti = PB.getClienti().size();
        // Registering Francesco generates "Francesco" followed by numeroClienti + 1.
        assertEquals("Francesco" + (numeroClienti+1), PB.generaCodiceCliente("Francesco"));
    }

    @Test
    void testAccedi(){
        Cliente Alberto = PB.getClienti().get("Alberto1");
        Boolean esito;

        // Logs in with customer code Alberto1 and password 0000.
        esito = PB.accedi("Alberto1", "0000");

        // Expected: login returns true.
        assertTrue(esito);

        // Expected: clienteLoggato equals Alberto.
        assertEquals(Alberto, PB.getClienteLoggato());

         // Logs in with customer code Alberto1 and password 0323.
         esito = PB.accedi("Alberto1", "0323");
         assertFalse(esito);

         // Expected: clienteLoggato is null.
         assertEquals(null, PB.getClienteLoggato());

        // Restores the state.
         PB.logout();

    }

    @Test
    void testLogout(){
        Cliente Alberto = PB.getClienti().get("Alberto1");
        Cane Stella =  PB.getClienti().get("Alberto1").getCane(1);
        PB.accedi("Alberto1", "0000");
        PB.selezionaCane(PB.getClienteLoggato().getCane(1));
        // At this point clienteLoggato is Alberto
        // and caneSelezionato is Stella.
        assertEquals(Alberto, PB.getClienteLoggato());
        assertEquals(Stella, PB.getCaneSelezionato());

        // Logout clears both clienteLoggato and caneSelezionato.
        PB.logout();
        assertEquals(null, PB.getClienteLoggato());
        assertEquals(null, PB.getCaneSelezionato());

    }

    @Test
    void testVerificaCliente(){
        Cliente Alberto = PB.getClienti().get("Alberto1");

         // Valid credentials return Alberto from verificaCliente.
         assertEquals(Alberto, PB.verificaCliente("Alberto1", "0000"));

         // Invalid credentials do not return Alberto from verificaCliente.
         assertNotEquals(Alberto, PB.verificaCliente("Alberto1", "1234"));

    }

    @Test
    void testSetClienteLoggato(){
        Cliente Alberto = PB.getClienti().get("Alberto1");
        Cliente Giuseppe = PB.getClienti().get("Giuseppe3");
        PB.setClienteLoggato(Alberto);
        // Expected: clienteLoggato equals Alberto.
        assertEquals(Alberto, PB.getClienteLoggato());
        // Expected: clienteLoggato is not Pippo.
        assertNotEquals(Giuseppe, PB.getClienteLoggato());

        // Restores the initial state.
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

        // Verifies that Alberto's dog list grows by one.
        assertEquals(numeroCaniClienteSuccessivo, PB.getClienteLoggato().getCani().size());

        // Verifies that Alberto's dog list contains Maya.
        Cane Maya = PB.getClienteLoggato().getCane(numeroCaniSistemaSuccessivo);
        assertTrue(PB.getClienteLoggato().getCani().contains(Maya));

    }

    @Test
    void testGeneraCodiceCane(){
        int numCodiceCanePrecedente;
        int numCodiceCaneSuccessivo;
        PB.accedi("Alberto1", "0000");
        // The codiceCane corresponds to the value of the PB variable numCani.
        numCodiceCanePrecedente = PB.getNumeroCani();
        numCodiceCaneSuccessivo = numCodiceCanePrecedente + 1;
        // Verifies that generaCodiceCane increments codiceCane by one.
        assertEquals(numCodiceCaneSuccessivo, PB.generaCodiceCane());
    }

    @Test
    void testRimuoviCane(){
        // rimuoviCane returns the customer's dogs not currently in foster care.
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

        // Verifies that the removed dog, if it participated in a course, has canceled its enrollment in that course.
        PB.confermaRimozioneCane(Aki);
        assertFalse(corsoBase.getCaniIscritti().contains(Aki));

        // Verifies that the removed dog is no longer owned by the customer.
        assertFalse(PB.getClienteLoggato().getCani().contains(Aki));

    }

    // The method returns dogs not currently in foster care.
    @Test
    void testSelezionaPeriodo(){

        Cliente Alberto = PB.getClienti().get("Alberto1");

         // Simulates customer Alberto's login.
         PB.accedi("Alberto1", "0000");

        // Simulates fostering one of the dogs.
        LinkedList<PeriodoAffido> lp = PB.affido();
        PB.selezionaPeriodo(lp.get(0));
        PB.confermaAffido(Alberto.getCane(2));

        // Verifies that the previously fostered dog is absent from selezionaPeriodo.
        LinkedList<Cane> elencoCaniNonInAffido = PB.selezionaPeriodo(PB.affido().get(0));
        for (Cane cane : elencoCaniNonInAffido) {
            assertFalse(cane.getAttualmenteInAffido());
        }

        // Verifies that periodoSelezionato equals PB.affido().get(0).
        assertEquals(lp.get(0), PB.getPeriodoSelezionato());

        // Restores the initial state.
        PB.logout();

    }

    @Test
    void testConfermaAffido(){
        // Retrieves the available-period list.
        LinkedList<PeriodoAffido> lp= PB.affido();
        LinkedList<Cane> caniNonInAffido;

        // Uses the first period and fosters three dogs.
        PB.accedi("Alberto1", "0000");
        PB.aggiungiCane("Max", "Labrador");
        PB.aggiungiCane("Kia", "Barboncino");
        PB.aggiungiCane("Nettuno", "Pitbull");

        // Sets the selected period.
        caniNonInAffido = PB.selezionaPeriodo(lp.get(1));

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

        // Expected: the selected period contains four foster dogs.
        assertEquals(4, PB.getPeriodoSelezionato().getCaniAffido().size());
        // Expected: it contains Rex.
        assertTrue( PB.getPeriodoSelezionato().getCaniAffido().contains(Rex));
        // Expected: one available place.
        assertEquals(1, PB.getPeriodoSelezionato().getNumeroPosti());

        // Simulates fostering a new dog through confermaAffido.
        PB.confermaAffido(Maya);
        // Expected: zero available places.
        assertEquals(0, PB.getPeriodoSelezionato().getNumeroPosti());
        // Expected: the selected period is removed from the available periods.
        assertFalse(PB.affido().contains(PB.getPeriodoSelezionato()));

        // Restores the initial state.
        PB.logout();

    }

    @Test
    void testConcludiAffido(){
        PB.accedi("Alberto1","0000");
        // The generated code is 3.
        PB.aggiungiCane("Natan", "Pitbull");

        // Enrolls Natan in period 3.
        LinkedList<PeriodoAffido> lp = PB.affido();
        PB.selezionaPeriodo(lp.get(2));
        Cane Natan = PB.getClienti().get("Alberto1").getCane(3);
        PB.confermaAffido(Natan);
        PeriodoAffido pa;
        pa = PB.concludiAffido("Alberto1", 3);

        // Expected: the selected dog is Natan.
        assertEquals(Natan, PB.getCaneSelezionato());

        // Expected: the foster period being concluded is period 3.
        assertEquals(lp.get(2), pa);

    }

    @Test
    void testConfermaConclusioneAffido(){

        // Simulates registering a dog in a foster period.
        PB.accedi("Alberto1","0000");

        // Enrolls Stella in period 3.
        LinkedList<PeriodoAffido> lp = PB.affido();
        // selezionaPeriodo selects period 3.
        PB.selezionaPeriodo(lp.get(2));
        Cane Stella = PB.getClienti().get("Alberto1").getCane(1);
        PB.confermaAffido(Stella);

        // concludiAffido selects Stella as the current dog.
        PB.concludiAffido("Alberto1", 1);

        PB.confermaConclusioneAffido();

        // Expected: Stella is removed from period 2.
        assertFalse( PB.getPeriodoSelezionato().getCaniAffido().contains(Stella));

        // Expected: Stella's attualmenteInAffido becomes false.
        assertFalse(Stella.getAttualmenteInAffido());

        // Expected: Stella's foster period becomes null.
        assertEquals(null, Stella.getAffidoCorrente());

    }

}
