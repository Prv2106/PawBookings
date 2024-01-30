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


/*
 * Test dei casi d'uso UC9-UC11-UC12-UC13
 * Inoltre Test di alcuni metodi dei casi d'uso UC2-UC3-UC4 necessari per i casi d'uso aggiunti
 */



class PawBookingsTest4 {
    static PawBookings PB;

    // Il metodo viene eseguito prima di tutti i test
    // inizializza l'istanza di PawBookings
    // crea un'istanza di Cane e la seleziona
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

        PB.inserisciNuovoCorso("Corso Avanzato", 10, 250.0F);
        Corso corsoAvanzato = PB.modificaProgrammaCorso().get(1);
        PB.selezionaCorso(corsoAvanzato);

        // Il codice di questa lezione sarà 3
        PB.nuovaLezione("Svolte, dietro front e variazioni andature");
        PB.inserisciEsercizio("Dietro Front", "Insegna al cane a girare rapidamente di 180 gradi su comando, promuovendo una risposta veloce e coordinata.");
        PB.inserisciEsercizio("Variazioni Andature", "Pratica diverse andature come il passo veloce e il passo lento, migliorando il controllo del cane nelle varie situazioni.");
        PB.inserisciEsercizio("Svolte", "Esercita il cane a eseguire svolte a destra e sinistra durante la camminata al guinzaglio, migliorando la coordinazione e la capacità di seguire le indicazioni del proprietario.");
        PB.confermaLezione();

        // Il codice di questa lezione sarà 4
        PB.nuovaLezione("Riporto in piano di un oggetto");
        PB.inserisciEsercizio("Introduzione al Riporto", "Insegna al cane a prendere un oggetto e a rilasciarlo gentilmente, utilizzando rinforzi positivi come premi o carezze.");
        PB.inserisciEsercizio("Riporto con Distrazioni", " Pratica il riporto introducendo distrazioni come suoni o altri oggetti, migliorando la capacità del cane di concentrarsi sul compito principale.");
        PB.inserisciEsercizio("Riporto a Distanza", "Insegna al cane a riportare un oggetto anche a distanza, incrementando gradualmente la distanza tra il proprietario e il punto in cui l'oggetto deve essere riportato.");
        PB.confermaLezione();

        // Inserimento turni
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

    // Il metodo restituisce un'istanza della classe PawBookings se PB è null.
    @Test
    void testGetInstance() {
        assertNotNull(PB);
    }


    @Test
    void testVerificaIdoneitaScambioTurno(){
        // Turno valido
        Turno t1 = new Turno(10, LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0));
        assertTrue(PB.verificaIdoneitaScambioTurno(t1));
        
        //Turno non valido
        Turno t2 = new Turno(10, LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0));
        assertFalse(PB.verificaIdoneitaScambioTurno(t2));
        //Turno mancante
        assertFalse(PB.verificaIdoneitaScambioTurno(null));
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
        // Ci aspettiamo che venga restituita la lista dei turni disponibili e che tra tali turni non sia presente t1
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

        //Test del metodo
        PB.selezionaTurnoScambio(t2);

        //Ci aspettiamo che il turno corrente di Asso adesso sia t2
        assertEquals(t2,Maya.getTurnoCorrente());

    }



    @Test
    void testMostraStatoAvanzamentoCorso(){
        PB.accedi("Alberto1", "0000");
        Cane Leila = PB.getClienteLoggato().getCane(4);
        assertFalse(Leila.getAttualmenteIscritto());
        PB.selezionaCane(Leila);

        // Facciamo sì che Leila abbia completato il corsoBase
        Leila.getCorsiCompletati().add(PB.getCorsi().get(0));
        // Simuliamo l'iscrizione di Leila al Corso Avanzato
        PB.confermaIscrizioneCorso(PB.getCorsi().get(1));

        Lezione lezione1 = Leila.getCorsoCorrente().getLezioni().get(0);
        Lezione lezione2 = Leila.getCorsoCorrente().getLezioni().get(1);
        
        // Test del metodo
        // Chiamando il metodo mostraStatoAvanzamentoCorso adesso
        // Ci aspettiamo che la lista lezioni seguite sia vuota
        // Ci aspettiamo che la lista lezioni mancanti contenga lezione1 e lezione2

        Map<String,LinkedList<Lezione>> mappaLezioni = PB.mostraStatoAvanzamentoCorso();

        assertEquals(0, mappaLezioni.get("lezioni seguite").size());
        assertEquals(2, mappaLezioni.get("lezioni mancanti").size());
        assertTrue(mappaLezioni.get("lezioni mancanti").contains(lezione1));
        assertTrue(mappaLezioni.get("lezioni mancanti").contains(lezione2));
        assertFalse(mappaLezioni.get("lezioni seguite").contains(lezione1));
        assertFalse(mappaLezioni.get("lezioni seguite").contains(lezione2));

        // Prenotando Leila ad un turno della lezione 1
        // Ci aspettiamo che la lista lezioni seguite abbia lunghezza 1
        // Ci aspettiamo che la lista lezioni seguite contenga lezione 1
        // Ci aspettiamo che la lista lezioni mancanti contenga lezione2 ma non lezione 1
        // Ci aspettiamo che la lista lezioni mancanti abbia lumghezza 1

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
        // Calcoliamo la lista iniziale

        LinkedList<PeriodoAffido> elencoPeriodiAffidoCaniRegistrati= new LinkedList<>();
        for(PeriodoAffido pa: PB.getPeriodiAffido()){
            if(pa.getNumeroPosti()<pa.getCapienzaMassima()){
                elencoPeriodiAffidoCaniRegistrati.add(pa);
            }
        }

        int lunghezzaIniziale = elencoPeriodiAffidoCaniRegistrati.size();

        // Test del metodo
        // Ci aspettiamo che venga restituita una lista con lunghezza pari a lunghezzaIniziale
        assertEquals(lunghezzaIniziale, PB.calcolaPeriodoCaneRegistrato().size());

        // Mettendo Stella in affido nel periodo 1
        // Ci aspettiamo che la lista restituita abbia lunghezza pari a lunghezzaIniziale + 1
        // Ci aspettiamo che la lista restituita contenga periodo 1
        PeriodoAffido periodo1 = PB.getPeriodiAffido().getFirst();
        PB.selezionaPeriodo(periodo1);
        Cane Stella = PB.getClienteLoggato().getCane(1);
        PB.confermaAffido(Stella);
        assertEquals(lunghezzaIniziale + 1, PB.calcolaPeriodoCaneRegistrato().size());
        assertTrue(PB.calcolaPeriodoCaneRegistrato().contains(periodo1));

        // Mettendo Asso in affido nel periodo 2
        // Ci aspettiamo che la lista restituita abbia lunghezza pari a lunghezzaIniziale + 2
        // Ci aspettiamo che la lista restituita contenga periodo 1 e periodo 2
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

        // test del metodo
        
        // Ci aspettiamo che venga restituita una lista di lunghezza pari a lunghezzaIniziale
        assertEquals(lunghezzaIniziale, PB.mostraCaniInAffido(periodo3).size());

        // Ci aspettiamo che il periodo selezionato sia periodo 3
        assertEquals(periodo3, PB.getPeriodoSelezionato());


        // Mettendo Maya in Affido nel periodo 3
        // Ci aspettiamo che Maya sia contenuta nella lista
        // Ci aspettiamo che la lista abbia lunghezza pari a lunghezzaIniziale + 1
        PB.selezionaPeriodo(periodo3);
        PB.confermaAffido(Maya);

        assertEquals(lunghezzaIniziale+1, PB.mostraCaniInAffido(periodo3).size());
        assertTrue(PB.mostraCaniInAffido(periodo3).contains(Maya));

        // Concludendo l'affido di Maya
        // Ci aspettiamo che venga restituita una lista pari a lunghezzaIniziale
        PB.setClienteSelezionato(PB.getClienteLoggato());
        PB.selezionaCane(Maya);
        PB.confermaConclusioneAffido();
        
        assertEquals(lunghezzaIniziale, PB.mostraCaniInAffido(periodo3).size());


        // Ripristino lo stato di periodoSelezionato
        PB.setPeriodoSelezionato(null);


    }







    @Test
    void testNotificaClienti(){
        PB.accedi("Alberto1", "0000");
        Cliente Alberto = PB.getClienteLoggato();
        Cane Duchessa = PB.getClienteLoggato().getCane(5);
        PeriodoAffido periodo3 = PB.getPeriodiAffido().get(2);
        PB.selezionaPeriodo(periodo3);
        // Mettiamo Duchessa in affido nel periodo 3
        PB.confermaAffido(Duchessa);

        PB.accedi("Giuseppe2", "0000");
        Cliente Giuseppe = PB.getClienteLoggato();
        Cane Walker = PB.getClienteLoggato().getCane(6);
        PB.selezionaPeriodo(periodo3);
        // Mettiamo Walker in affido nel periodo 3
        PB.confermaAffido(Walker);


        PB.accedi("Daniele3", "0000");
        Cliente Daniele = PB.getClienteLoggato();

        Map<Integer,String> mappaStatoSalute = new HashMap<>();
        mappaStatoSalute.put(5, "test stato salute Duchessa");
        mappaStatoSalute.put(6, "test stato salute Walker");
        PB.setPeriodoSelezionato(periodo3);

        // Test del metodo
        PB.notificaClienti(mappaStatoSalute);

        // Ci aspettiamo che la mappaStatoSalute di periodoSelezionato sia stata settata a mappaStatoSalute
        assertEquals(mappaStatoSalute, periodo3.getMappaStatoSalute());

        // Ci aspettiamo che l'attributo notifica di Alberto e di Giuseppe sia diventato true
        // Ci aspettiamo che l'attributo notifica di Daniele sia rimasto false
         // Ci aspettiamo che la lista statoSaluteCani di Giuseppe e di Alberto non sia vuota
        // Ci aspettiamo che la lista statoSaluteCani di Daniele sia vuota
        assertTrue(Alberto.getNotifica());
        assertTrue(Giuseppe.getNotifica());
        assertFalse(Daniele.getNotifica());
        assertEquals(1, Alberto.getStatoSalute().size());
        assertEquals(1, Giuseppe.getStatoSalute().size());
        assertEquals(0, Daniele.getStatoSalute().size());



        // Ripristino le condizioni iniziali
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
        // Mettiamo Duchessa in affido nel periodo 3
        PB.confermaAffido(Duchessa);

        PB.accedi("Giuseppe2", "0000");
        Cliente Giuseppe = PB.getClienteLoggato();
        Cane Walker = PB.getClienteLoggato().getCane(6);
        PB.selezionaPeriodo(periodo3);
        // Mettiamo Walker in affido nel periodo 3
        PB.confermaAffido(Walker);
        
        // Imposto le condizioni iniziali
        Alberto.getStatoSalute().clear();
        Giuseppe.getStatoSalute().clear();

        Map<Integer,String> mappaStatoSalute = new HashMap<>();
        mappaStatoSalute.put(5, "test stato salute Duchessa");
        mappaStatoSalute.put(6, "test stato salute Walker");
        PB.setPeriodoSelezionato(periodo3);

        PB.notificaClienti(mappaStatoSalute);

        // Ci aspettiamo che la mappaStatoSalute di periodoSelezionato sia stata settata a mappaStatoSalute
        assertEquals(mappaStatoSalute, periodo3.getMappaStatoSalute());

        // Ci aspettiamo che l'attributo notifica di Alberto e di Giuseppe sia diventato true
        // Ci aspettiamo che l'attributo notifica di Daniele sia rimasto false
        assertTrue(Alberto.getNotifica());
        assertTrue(Giuseppe.getNotifica());


        // Test del metodo
        // Accedendo con Alberto e richiamando il metodo cancellaNotifiche
        // Ci aspettiamo che l'attributo Notifica di Alberto sia diventato false
        // Ci aspettiamo che la lista statoSaluteCani di Alberto sia diventata vuota
        // Ci aspettiamo che l'attributo Notifica di Giuseppe sia ancora true
        // Ci aspettiamo che la lista statoSaluteCani di Giuseppe non sia vuota
        PB.accedi("Alberto1", "0000");
        PB.cancellaNotifiche();
        assertFalse(Alberto.getNotifica());
        assertEquals(0, Alberto.getStatoSalute().size());
        assertTrue(Giuseppe.getNotifica());
        assertEquals(1, Giuseppe.getStatoSalute().size());

        


    }

    
}
