package domain_layer;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class PawBookings {
    
    private static PawBookings PB;
    private int numCani;
    private int pinAdmin;

    // Riferimenti
    public LinkedList<Corso> elencoCorsi;
    private LinkedList<Corso> elencoCorsiDisponibili;
    private Map<String, Cliente> clienti;
    private Cane caneSelezionato;
    private LinkedList<PeriodoAffido> elencoPeriodiDisponibili;
    private LinkedList<PeriodoAffido> elencoPeriodi;
    private Cliente clienteLoggato;
    private Cliente clienteSelezionato;
    private PeriodoAffido periodoSelezionato;
    private Corso corsoSelezionato;


 

    // metodo costruttore
    private PawBookings(){
        this.elencoCorsi = new LinkedList<>();
        this.elencoCorsiDisponibili = new LinkedList<>();
        this.clienti = new HashMap<>();
        this.elencoPeriodiDisponibili = new LinkedList<>();
        this.pinAdmin = 1234;
        this.numCani= 0;
        this.elencoPeriodiDisponibili = new LinkedList<>();
        this.elencoPeriodiDisponibili = new LinkedList<>();
        this.loadPeriodiAffido();
    }


     // metodo per recuperare l'unica istanza della classe PawBookings per il pattern GoF Singleton
     public static PawBookings getInstance() {
         if(PB == null)
             PB = new PawBookings();
        return PB;
    }


  

    public void loadPeriodiAffido(){

        // Creazione delle istanze di PeriodoAffido 
        PeriodoAffido p1 = new PeriodoAffido(1, LocalDate.now(), LocalDate.now().plusWeeks(2), 150.0f);
        PeriodoAffido p2 = new PeriodoAffido(2, LocalDate.now().plusWeeks(2), LocalDate.now().plusMonths(1), 300.0f);
        PeriodoAffido p3 = new PeriodoAffido(3,LocalDate.now().plusMonths(1) ,LocalDate.now().plusMonths(2), 600.0f);

        this.elencoPeriodiDisponibili.add(p1);
        this.elencoPeriodiDisponibili.add(p2);
        this.elencoPeriodiDisponibili.add(p3);

        
    }


    public LinkedList<Corso> nuovaIscrizioneCorso(){
        int capienza;
        // per ciascun corso presente in elencoCorsi viene verificato che il corso non sia pieno 
        // e in caso affermativo viene aggiunto all'elencoCorsiDisponibili
        elencoCorsiDisponibili.clear();
        for(Corso i: elencoCorsi){
            capienza = i.getCapienza();
            if(capienza > 0){
                elencoCorsiDisponibili.add(i);
            }
        }
        return elencoCorsiDisponibili;
    }

    public Boolean confermaIscrizioneCorso(Corso cs){
        if(cs == null){
            return false;
        } else {
            cs.confermaIscrizione(caneSelezionato);

            // l'attributo attualmenteIscritto di caneSelezionato diventa true 
            // e viene inizializzata la variabile corsoCorrente di caneSelezionato
            caneSelezionato.aggiornaAttualmenteIscritto(cs);

            return true;
        }
    }


    public LinkedList<Turno> prenotaTurnoLezione(){
        Lezione lezioneSuccesiva;
        //viene recuperata l'istanza della lezione successiva
        lezioneSuccesiva = caneSelezionato.getLezioneSuccessiva();
        
        // Vengono restituiti i turni disponibili relativi alla lezione successiva
        return lezioneSuccesiva.getTurniDisponibili();
    }

    


    public Boolean selezionaTurno(Turno ts){
        if(ts == null){
            return false;
        }
        else{
            this.caneSelezionato.aggiornaStatoAvanzamento(ts);
            return true;
        }
    }

    public void selezionaCane(Cane cn){
        this.caneSelezionato = cn;
    }



    public Map<String, Cliente> getClienti(){
        return this.clienti;
    }

    public Cane getCaneSelezionato() {
        return this.caneSelezionato;
    }

    public LinkedList<Corso> getCorsi() {
        return this.elencoCorsi;
    }


    public LinkedList<PeriodoAffido> affido(){
        return this.elencoPeriodiDisponibili;
    }


    public LinkedList<Cane> selezionaPeriodo(PeriodoAffido pa){
        setPeriodoSelezionato(pa);
        // viene recuperato l'elenco dei cani del cliente loggato che non sono attualmente in affido
        return this.clienteLoggato.getCaniNonInAffido();
    }


    public Boolean confermaAffido(Cane cn){
        int numeroPostiDisponibili;
        Boolean esito;
        Boolean esitoVerifica = this.periodoSelezionato.verificaIscrizione(this.clienteLoggato);
        esito = this.periodoSelezionato.registraAffido(cn);

        if(esitoVerifica == false){
            this.clienteLoggato.iscrizioneNotificheStatoSalute(this.periodoSelezionato);
        }

        cn.aggiornaAttualmenteInAffido(periodoSelezionato);
        numeroPostiDisponibili= this.periodoSelezionato.getNumeroPosti();
        if(numeroPostiDisponibili == 0){
            esito = this.elencoPeriodiDisponibili.remove(this.periodoSelezionato);
        }
        return esito;
    }

    public Boolean setPeriodoSelezionato(PeriodoAffido pa){
        if(pa != null){
            this.periodoSelezionato = pa;
            return true;
        }
        else{
            this.periodoSelezionato = null;
            return false;
        }
    }

    public Boolean confermaConclusioneAffido(){
        Boolean esito = this.caneSelezionato.conclusioneAffido();
        Boolean esitoVerifica = this.periodoSelezionato.verificaIscrizione(this.clienteSelezionato);
        if(esitoVerifica == false){
            PeriodoAffido periodoCorrente = this.caneSelezionato.getAffido();
            this.clienteSelezionato.annullamentoIscrizione(periodoCorrente);
        }
        return esito;
    }



    public Boolean accedi(String codiceCliente, String password){
        Cliente cl;
        cl=this.verificaCliente(codiceCliente, password);
        return this.setClienteLoggato(cl);
    }

    public void logout() {
        setClienteLoggato(null);
        setCaneSelezionato(null);
    }


    public Cliente verificaCliente(String codiceCliente, String password){
        if(clienti.get(codiceCliente).getPassword().equals(password)){
            return clienti.get(codiceCliente);
        }
        else return null;
    }



    public boolean setClienteLoggato(Cliente cl) {
        if (cl == null) {
            this.clienteLoggato = null;
            return false;
        } else {
            this.clienteLoggato = cl;
            return true;
        }
    }
    

    public Boolean registrati(String nome, String cognome, String numeroTelefono, String password) {
        String codiceCliente;
        Cliente nuovoCliente;
        Boolean esito = checkNumTelefono(numeroTelefono);
        if (esito) {
            codiceCliente = this.generaCodiceCliente(nome);
            nuovoCliente = new Cliente(codiceCliente,nome,cognome,password,numeroTelefono);
            this.clienti.putIfAbsent(codiceCliente, nuovoCliente);
            return setClienteLoggato(nuovoCliente);
        } else {
            return false;
        }
    }

    
    public String generaCodiceCliente(String nome){
        return (nome + (clienti.size() +1));
    }


    public Boolean confermaRimozioneCane(Cane cn){
        cn.aggiornaAssociazioniCane();
        return this.clienteLoggato.rimuoviCane(cn);
    }



    public LinkedList<Cane> rimuoviCane(){
        return clienteLoggato.getCaniNonInAffido();
    }


    public Boolean aggiungiCane(String nome, String razza){
        int codiceCane = this.generaCodiceCane();
        return clienteLoggato.registraCane(nome, razza, codiceCane);
    }

    public PeriodoAffido concludiAffido(String codiceCliente, int codiceCane) {
        Cliente cl;
        Cane cn;
        cl = this.clienti.get(codiceCliente);
        cn = cl.getCane(codiceCane);
        if (cn == null) {
            setCaneSelezionato(cn);
            setClienteSelezionato(cl);
            return null;
        } else {
            setCaneSelezionato(cn);
            setClienteSelezionato(cl);
            return cn.getAffido();
        }
    }


    public Boolean setClienteSelezionato(Cliente cl){
        if(cl==null){
            this.clienteSelezionato = null;
            return false;
        }
        else{
            this.clienteSelezionato = cl;
            return true;
        }
    }



    public Boolean accediComeAdmin(int pin){
        return checkPin(pin);
    }

    public Boolean checkPin(Integer pin){
        if(this.pinAdmin == pin){
            return true;
        }
        else{
            return false;
        }
    }


    public Boolean setCaneSelezionato(Cane cn){
        if(cn != null){
            this.caneSelezionato = cn;
            return true;
        }
        else {
            this.caneSelezionato = null;
            return false;
        }
    }

    public Cliente getClienteLoggato() {
        return this.clienteLoggato;
    }


    public PeriodoAffido getPeriodoSelezionato(){
        return this.periodoSelezionato;
    }



    public int generaCodiceCane(){
        this.numCani += 1;
        return this.numCani;
        
    }


    public int getNumeroCani(){
        return this.numCani;
    }

    public boolean checkNumTelefono(String numeroTelefono) {
        for (Cliente c : this.clienti.values()) {
            if (c.getNumTelefono().equals(numeroTelefono)) {
                return false;
            }
        }

        return true;
    }



    public Boolean inserisciNuovoCorso(String tipoCorso, int capienza, float costo){
        int codiceCorso = this.generaCodiceCorso();
        Corso nuovCorso = new Corso(codiceCorso, capienza, costo, tipoCorso);
        return this.elencoCorsi.add(nuovCorso);
    }

    public int generaCodiceCorso(){
        return (this.elencoCorsi.size() +1);
    }


    public LinkedList<Corso> modificaInformazioniCorso(){
        return this.elencoCorsi;
    }


    public void selezionaCorso(Corso cs){
        this.setCorsoSelezionato(cs);
    }

    public Boolean setCorsoSelezionato(Corso cs){
        if(cs == null){
            this.corsoSelezionato = null;
            return true;
        }
        else{
            this.corsoSelezionato = cs;
            return false;
        }
    }


    public Boolean modificaCorso(int capienza, float costo){
            return this.corsoSelezionato.aggiornaInformazioni(capienza,costo);  
    }

    public LinkedList<Corso> modificaProgrammaCorso(){
        return this.elencoCorsi;
    }

    public void nuovaLezione(String nome){
        int codiceLezione = this.generaCodiceLezione();
        this.corsoSelezionato.nuovaLezione(codiceLezione, nome);
    }

    public void inserisciEsercizio(String nome, String descrizione){
        this.corsoSelezionato.aggiornaLezione(nome, descrizione);
    }


    public Boolean confermaLezione(){
        return this.corsoSelezionato.confermaInserimentoLezione();
    }


    public int generaCodiceLezione(){
        int codiceLezione=0;
        for(Corso c: this.elencoCorsi){
            codiceLezione += c.programma.size();
        }
        return (codiceLezione+1);
    }


    public LinkedList<Corso> inserisciTurnoLezione(){
        return this.calcolaCorsiConCaniIscritti();
    }


    public LinkedList<Lezione> selezionaCorsoModificaTurni(Corso cs){   
        LinkedList<Lezione> programma = cs.getLezioni();
        this.setCorsoSelezionato(cs);
        return programma;
    }
    

    public void selezionaLezione(Lezione lz){
        this.corsoSelezionato.setLezioneSelezionata(lz);
    }


    public Boolean nuovoTurno(LocalDate data, LocalTime oraInizio, LocalTime oraFine){
        Boolean esito = verificaDatiTurno(data, oraInizio, oraFine);
        if(esito){
            this.corsoSelezionato.aggiungiTurnoLezione(data, oraInizio, oraFine);
        }
        return esito;
    }
    
    
    public LinkedList<Corso>  calcolaCorsiConCaniIscritti(){
        LinkedList<Corso> corsiConCani = new LinkedList<>();
        for(Corso c: elencoCorsi){
            if(c.elencoCaniIscritti.size()>0){
                corsiConCani.add(c);
            }
        } 
        return corsiConCani;
    }


    public LinkedList<Turno> scambioTurno(){
        Lezione ultimaLezioneSeguita = this.caneSelezionato.getUltimaLezioneSeguita();
        return ultimaLezioneSeguita.getTurniDisponibili();
    }


    public void selezionaTurnoScambio(Turno ts){
        Turno tc = this.caneSelezionato.getTurnoCorrente();
        Lezione ultimaLezioneSeguita = this.caneSelezionato.getUltimaLezioneSeguita();
        ultimaLezioneSeguita.effettuaScambioTurno(tc, ts);
    }   


    public Boolean selezionaCaneScambioTurno(Cane cn){
        Turno tc = cn.getTurnoCorrente();
        return this.verificaIdoneitaScambioTurno(tc);
    }

    public Boolean verificaIdoneitaScambioTurno(Turno tc){
        if((tc.getData().isBefore(LocalDate.now().plusDays(1))) && (tc != null)){
            return true;
        } else{
            return false;
        }
    }

    public LinkedList<Corso> visualizzaProgrammaCorso(){
        return this.elencoCorsi;
    }

    public LinkedList<Lezione> visualizzaProgramma(Corso cs){
        return cs.getLezioni();
    }


    public Map<String,LinkedList<Lezione>> mostraStatoAvanzamentoCorso(){
        return caneSelezionato.getAvanzamentoCorso();
    }

    public LinkedList<PeriodoAffido> notificaStatoSalute(){
        return calcolaPeriodoCaneRegistrato();
    }

    public LinkedList<Cane> mostraCaniInAffido(PeriodoAffido pa){
        LinkedList<Cane> elencoCaniAffido = pa.getCaniAffido();
        setPeriodoSelezionato(pa);
        return elencoCaniAffido;
    }


    public LinkedList<PeriodoAffido> calcolaPeriodoCaneRegistrato(){
        LinkedList<PeriodoAffido> elencoPeriodiAffidoCaniRegistrati= new LinkedList<>();
        for(PeriodoAffido pa: this.elencoPeriodi){
            if(pa.getNumeroPosti()<pa.getCapienzaMassima()){
                elencoPeriodiAffidoCaniRegistrati.add(pa);
            }
        }
        return elencoPeriodiAffidoCaniRegistrati; 
    }



    public void notificaClienti(Map<Integer,String> mappaStatoSalute){
        this.periodoSelezionato.aggiornaStatoSalute(mappaStatoSalute);
    }

    public LinkedList<Map<String,String>> leggiStatoSalute(){
        LinkedList<Map<String,String>> statoSaluteCani = this.clienteLoggato.getStatoSalute();
        this.clienteLoggato.resettaStatoSaluteCani();
        return statoSaluteCani;
    }

    public Boolean verificaDatiTurno(LocalDate data, LocalTime oraInizio, LocalTime oraFine){
        if((LocalDate.now().isBefore(data)) && (oraInizio.isBefore(oraFine))){
            return true;
        }
        else return false;
    }



    public LinkedList<Corso> getCorso(){
        return this.elencoCorsi;
    }

}
