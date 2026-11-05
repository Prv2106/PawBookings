package com.example;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domain_layer.Cane;
import domain_layer.Cliente;
import domain_layer.Corso;
import domain_layer.Lezione;
import domain_layer.PawBookings;
import domain_layer.PeriodoAffido;
import domain_layer.Turno;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Integration tests for shift exchange, progress tracking, notifications, and
 * foster-period queries (UC9-UC13).
 */
class PawBookingsTest4 {
    static PawBookings PB;

    // Runs before all tests.
    // Initializes the PawBookings instance.
    // Creates a Cane instance and selects it.
    @BeforeAll
    public static void initTest() {
        PB = PawBookings.getInstance();
        PB.registrati("Alberto", "Provenzano", "156322345678", "0000");
        PB.aggiungiCane("Stella", "Pastore Tedesco");
        PB.aggiungiCane("Asso", "Corso");
        PB.aggiungiCane("Maya", "Pastore Tedesco");
        PB.aggiungiCane("Leila", "Labrador");
        PB.aggiungiCane("Duchessa", "Labrador");
        PB.logout();

        PB.registrati("Giuseppe", "Leocata", "562562", "0000");
        PB.aggiungiCane("Walker", "Pastore Tedesco");
        PB.logout();

        PB.registrati("Daniele", "Lucifora", "9921319", "0000");
        PB.aggiungiCane("Sole", "Barboncino");
        PB.aggiungiCane("Luna", "Rottweiler");
        PB.aggiungiCane("Vanessa", "Labrador");
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

        PB.inserisciNuovoCorso("Corso Avanzato", 10, 250.0F);
        Corso corsoAvanzato = PB.modificaProgrammaCorso().get(1);
        PB.selezionaCorso(corsoAvanzato);

        // The lesson code is 3.
        PB.nuovaLezione("Svolte, dietro front e variazioni andature");
        PB.inserisciEsercizio("Dietro Front", "Insegna al cane a girare rapidamente di 180 gradi su comando, promuovendo una risposta veloce e coordinata.");
        PB.inserisciEsercizio("Variazioni Andature", "Pratica diverse andature come il passo veloce e il passo lento, migliorando il controllo del cane nelle varie situazioni.");
        PB.inserisciEsercizio("Svolte", "Esercita il cane a eseguire svolte a destra e sinistra durante la camminata al guinzaglio, migliorando la coordinazione e la capacità di seguire le indicazioni del proprietario.");
        PB.confermaLezione();

        // The lesson code is 4.
        PB.nuovaLezione("Riporto in piano di un oggetto");
        PB.inserisciEsercizio("Introduzione al Riporto", "Insegna al cane a prendere un oggetto e a rilasciarlo gentilmente, utilizzando rinforzi positivi come premi o carezze.");
        PB.inserisciEsercizio("Riporto con Distrazioni", " Pratica il riporto introducendo distrazioni come suoni o altri oggetti, migliorando la capacità del cane di concentrarsi sul compito principale.");
        PB.inserisciEsercizio("Riporto a Distanza", "Insegna al cane a riportare un oggetto anche a distanza, incrementando gradualmente la distanza tra il proprietario e il punto in cui l'oggetto deve essere riportato.");
        PB.confermaLezione();

        // Adds shifts.
        PB.selezionaCorsoModificaTurni(corsoBase);
        PB.selezionaLezione(corsoBase.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(11, 0), LocalTime.of(12, 0));
        PB.selezionaLezione(corsoBase.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(15, 0), LocalTime.of(16, 0));

        PB.selezionaCorsoModificaTurni(corsoAvanzato);
        PB.selezionaLezione(corsoAvanzato.getLezioni().get(0));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        PB.selezionaLezione(corsoAvanzato.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        PB.selezionaLezione(corsoAvanzato.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(11, 0), LocalTime.of(12, 0));
        PB.selezionaLezione(corsoAvanzato.getLezioni().get(1));
        PB.nuovoTurno(LocalDate.now().plusDays(1), LocalTime.of(15, 0), LocalTime.of(16, 0));

    }

    // Returns the singleton PawBookings instance.
    @Test
    void testGetInstance() {
        assertNotNull(PB);
    }

    @Test
    void testScambioTurno(){
        PB.accedi("Alberto1", "0000");
        Cane Asso = PB.getClienteLoggato().getCane(2);
        PB.setCaneSelezionato(Asso);
        PB.confermaIscrizioneCorso(PB.getCorsi().getFirst());

        Turno t1 = PB.getCorsi().getFirst().getLezioni().getFirst().getTurniDisponibili().getFirst();
        PB.selezionaTurno(t1);
        PB.selezionaCane(Asso);
        // Expected: the available-shift list is returned without t1.
        assertFalse(PB.scambioTurno().contains(t1));
    }

    @Test
    void testSelezionaTurnoScambio(){
        PB.accedi("Alberto1", "0000");
        Cane Maya = PB.getClienteLoggato().getCane(3);
        PB.setCaneSelezionato(Maya);
        PB.confermaIscrizioneCorso(PB.getCorsi().getFirst());

        Turno t1 = PB.getCorsi().getFirst().getLezioni().get(1).getTurniDisponibili().getFirst();
        PB.selezionaTurno(t1);
        assertEquals(t1,Maya.getTurnoCorrente());

        LinkedList<Turno> turniDisponibili = PB.scambioTurno();
        Turno t2 = turniDisponibili.getFirst();

        PB.selezionaTurnoScambio(t2);

        // Expected: Asso's current shift is t2.
        assertEquals(t2,Maya.getTurnoCorrente());

    }

    @Test
    void testMostraStatoAvanzamentoCorso(){
        PB.accedi("Alberto1", "0000");
        Cane Leila = PB.getClienteLoggato().getCane(4);
        assertFalse(Leila.getAttualmenteIscritto());
        PB.selezionaCane(Leila);

        // Ensures Leila completed the basic course.
        Leila.getCorsiCompletati().add(PB.getCorsi().get(0));
        // Simulates Leila enrolling in the advanced course.
        PB.confermaIscrizioneCorso(PB.getCorsi().get(1));

        Lezione lezione1 = Leila.getCorsoCorrente().getLezioni().get(0);
        Lezione lezione2 = Leila.getCorsoCorrente().getLezioni().get(1);

        // Calls mostraStatoAvanzamentoCorso.
        // Expected: the attended-lesson list is empty.
        // Expected: the missing-lesson list contains lesson1 and lesson2.

        Map<String,LinkedList<Lezione>> mappaLezioni = PB.mostraStatoAvanzamentoCorso();

        assertEquals(0, mappaLezioni.get("lezioni seguite").size());
        assertEquals(2, mappaLezioni.get("lezioni mancanti").size());
        assertTrue(mappaLezioni.get("lezioni mancanti").contains(lezione1));
        assertTrue(mappaLezioni.get("lezioni mancanti").contains(lezione2));
        assertFalse(mappaLezioni.get("lezioni seguite").contains(lezione1));
        assertFalse(mappaLezioni.get("lezioni seguite").contains(lezione2));

        // Books Leila for a lesson 1 shift.
        // Expected: the attended-lesson list has length 1.
        // Expected: the attended-lesson list contains lesson 1.
        // Expected: the missing-lesson list contains lesson2 but not lesson1.
        // Expected: the missing-lesson list has length 1.

        PB.selezionaTurno(PB.getCorsi().get(1).getLezioni().getFirst().getTurniDisponibili().getFirst());
        mappaLezioni = PB.mostraStatoAvanzamentoCorso();

        assertEquals(1, mappaLezioni.get("lezioni seguite").size());
        assertEquals(1, mappaLezioni.get("lezioni mancanti").size());
        assertFalse(mappaLezioni.get("lezioni mancanti").contains(lezione1));
        assertTrue(mappaLezioni.get("lezioni mancanti").contains(lezione2));
        assertTrue(mappaLezioni.get("lezioni seguite").contains(lezione1));
        assertFalse(mappaLezioni.get("lezioni seguite").contains(lezione2));

    }

    @Test
    void testCalcolaPeriodoCaneRegistrato(){
        PB.accedi("Alberto1", "0000");
        // Calculates the initial list.

        LinkedList<PeriodoAffido> elencoPeriodiAffidoCaniRegistrati= new LinkedList<>();
        for(PeriodoAffido pa: PB.getPeriodiAffido()){
            if(pa.getNumeroPosti()<pa.getCapienzaMassima()){
                elencoPeriodiAffidoCaniRegistrati.add(pa);
            }
        }

        int lunghezzaIniziale = elencoPeriodiAffidoCaniRegistrati.size();

        // Expected: a list of length lunghezzaIniziale.
        assertEquals(lunghezzaIniziale, PB.calcolaPeriodoCaneRegistrato().size());

        // Places Stella in foster care in period 1.
        // Expected: the list length is lunghezzaIniziale + 1.
        // Expected: the list contains period 1.
        PeriodoAffido periodo1 = PB.getPeriodiAffido().getFirst();
        PB.selezionaPeriodo(periodo1);
        Cane Stella = PB.getClienteLoggato().getCane(1);
        PB.confermaAffido(Stella);
        assertEquals(lunghezzaIniziale + 1, PB.calcolaPeriodoCaneRegistrato().size());
        assertTrue(PB.calcolaPeriodoCaneRegistrato().contains(periodo1));

        // Places Asso in foster care in period 2.
        // Expected: the list length is lunghezzaIniziale + 2.
        // Expected: the list contains periods 1 and 2.
        PeriodoAffido periodo2 = PB.getPeriodiAffido().get(1);
        PB.selezionaPeriodo(periodo2);
        Cane Asso = PB.getClienteLoggato().getCane(2);
        PB.confermaAffido(Asso);

        assertEquals(lunghezzaIniziale + 2, PB.calcolaPeriodoCaneRegistrato().size());
        assertTrue(PB.calcolaPeriodoCaneRegistrato().contains(periodo1));
        assertTrue(PB.calcolaPeriodoCaneRegistrato().contains(periodo2));

    }

    @Test
    void setPeriodoSelezionato(){
        PeriodoAffido p = PB.getPeriodiAffido().getFirst();
        assertTrue(PB.setPeriodoSelezionato(p));
        assertEquals(p, PB.getPeriodoSelezionato());
        assertFalse(PB.setPeriodoSelezionato(null));
        assertEquals(null, PB.getPeriodoSelezionato());
    }

    @Test
    void testMostraCaniAffido(){
        PeriodoAffido periodo3 = PB.getPeriodiAffido().get(2);
        PB.accedi("Alberto1", "0000");
        Cane Maya = PB.getClienteLoggato().getCane(3);
        int lunghezzaIniziale = periodo3.getCaniAffido().size();

        // Expected: a list of length lunghezzaIniziale.
        assertEquals(lunghezzaIniziale, PB.mostraCaniInAffido(periodo3).size());

        // Expected: the selected period is period 3.
        assertEquals(periodo3, PB.getPeriodoSelezionato());

        // Places Maya in foster care in period 3.
        // Expected: Maya is included in the list.
        // Expected: the list to have length equal to lunghezzaIniziale + 1.
        PB.selezionaPeriodo(periodo3);
        PB.confermaAffido(Maya);

        assertEquals(lunghezzaIniziale+1, PB.mostraCaniInAffido(periodo3).size());
        assertTrue(PB.mostraCaniInAffido(periodo3).contains(Maya));

        // Concludes Maya's foster care.
        // Expected: a list of length lunghezzaIniziale.
        PB.setClienteSelezionato(PB.getClienteLoggato());
        PB.selezionaCane(Maya);
        PB.confermaConclusioneAffido();

        assertEquals(lunghezzaIniziale, PB.mostraCaniInAffido(periodo3).size());

        // Restores the selected-period state.
        PB.setPeriodoSelezionato(null);

    }

    @Test
    void testNotificaClienti(){
        PB.accedi("Alberto1", "0000");
        Cliente Alberto = PB.getClienteLoggato();
        Cane Duchessa = PB.getClienteLoggato().getCane(5);
        PeriodoAffido periodo3 = PB.getPeriodiAffido().get(2);
        PB.selezionaPeriodo(periodo3);
        // Places Duchessa in foster care in period 3.
        PB.confermaAffido(Duchessa);

        PB.accedi("Giuseppe2", "0000");
        Cliente Giuseppe = PB.getClienteLoggato();
        Cane Walker = PB.getClienteLoggato().getCane(6);
        PB.selezionaPeriodo(periodo3);
        // Places Walker in foster care in period 3.
        PB.confermaAffido(Walker);

        PB.accedi("Daniele3", "0000");
        Cliente Daniele = PB.getClienteLoggato();

        Map<Integer,String> mappaStatoSalute = new HashMap<>();
        mappaStatoSalute.put(5, "test stato salute Duchessa");
        mappaStatoSalute.put(6, "test stato salute Walker");
        PB.setPeriodoSelezionato(periodo3);

        PB.notificaClienti(mappaStatoSalute);

        // Expected: the selected period stores the supplied mappaStatoSalute.
        assertEquals(mappaStatoSalute, periodo3.getMappaStatoSalute());

        // Expected: the notification flags of Alberto and Giuseppe become true.
        // Expected: Daniele's notification flag remains false.
         // Expected: Giuseppe and Alberto have non-empty statoSaluteCani lists.
        // Expected: Daniele's statoSaluteCani list is empty.
        assertTrue(Alberto.getNotifica());
        assertTrue(Giuseppe.getNotifica());
        assertFalse(Daniele.getNotifica());
        assertEquals(1, Alberto.getStatoSalute().size());
        assertEquals(1, Giuseppe.getStatoSalute().size());
        assertEquals(0, Daniele.getStatoSalute().size());

        // Restores the initial conditions.
        PB.setClienteSelezionato(Alberto);
        PB.setCaneSelezionato(Duchessa);
        PB.confermaConclusioneAffido();

        PB.setClienteSelezionato(Giuseppe);
        PB.setCaneSelezionato(Walker);
        PB.confermaConclusioneAffido();

        PB.setCaneSelezionato(null);

    }

    @Test
    void testCancellaNotifiche(){
        PB.accedi("Alberto1", "0000");
        Cliente Alberto = PB.getClienteLoggato();
        Cane Duchessa = PB.getClienteLoggato().getCane(5);
        PeriodoAffido periodo3 = PB.getPeriodiAffido().get(2);
        PB.selezionaPeriodo(periodo3);
        // Places Duchessa in foster care in period 3.
        PB.confermaAffido(Duchessa);

        PB.accedi("Giuseppe2", "0000");
        Cliente Giuseppe = PB.getClienteLoggato();
        Cane Walker = PB.getClienteLoggato().getCane(6);
        PB.selezionaPeriodo(periodo3);
        // Places Walker in foster care in period 3.
        PB.confermaAffido(Walker);

        // Sets the initial conditions.
        Alberto.getStatoSalute().clear();
        Giuseppe.getStatoSalute().clear();

        Map<Integer,String> mappaStatoSalute = new HashMap<>();
        mappaStatoSalute.put(5, "test stato salute Duchessa");
        mappaStatoSalute.put(6, "test stato salute Walker");
        PB.setPeriodoSelezionato(periodo3);

        PB.notificaClienti(mappaStatoSalute);

        // Expected: the selected period stores the supplied mappaStatoSalute.
        assertEquals(mappaStatoSalute, periodo3.getMappaStatoSalute());

        // Expected: the notification flags of Alberto and Giuseppe become true.
        // Expected: Daniele's notification flag remains false.
        assertTrue(Alberto.getNotifica());
        assertTrue(Giuseppe.getNotifica());

        // Logs in as Alberto and calls cancellaNotifiche.
        // Expected: Alberto's notification flag becomes false.
        // Expected: Alberto's statoSaluteCani list becomes empty.
        // Expected: Giuseppe's notification flag remains true.
        // Expected: Giuseppe's statoSaluteCani list is not empty.
        PB.accedi("Alberto1", "0000");
        PB.cancellaNotifiche();
        assertFalse(Alberto.getNotifica());
        assertEquals(0, Alberto.getStatoSalute().size());
        assertTrue(Giuseppe.getNotifica());
        assertEquals(1, Giuseppe.getStatoSalute().size());

    }

}
