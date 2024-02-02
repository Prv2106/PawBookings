package domain_layer;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class PawBookings {
    
    private static PawBookings PB;
    private int numCani;
    private int pinAdmin;

    // Riferimenti
    private LinkedList<Corso> elencoCorsi;
    private LinkedList<Corso> elencoCorsiDisponibili;
    private Map<String, Cliente> clienti;
    private Cane caneSelezionato;
    private LinkedList<PeriodoAffido> elencoPeriodiDisponibili;
    private LinkedList<PeriodoAffido> elencoPeriodiAffido;
    private Cliente clienteLoggato;
    private Cliente clienteSelezionato;
    private PeriodoAffido periodoSelezionato;
    private Corso corsoSelezionato;


 

    // metodo costruttore
    private PawBookings(){
        this.elencoCorsi = new LinkedList<>();
        this.elencoCorsiDisponibili = new LinkedList<>();
        this.elencoPeriodiAffido = new LinkedList<>();
        this.elencoPeriodiDisponibili = new LinkedList<>();
        this.clienti = new HashMap<>();
        this.pinAdmin = 1234;
        this.numCani= 0;
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
        PeriodoAffido p1 = new PeriodoAffido(1, LocalDate.now().plusDays(1), LocalDate.now().plusWeeks(2), 150.0f);
        PeriodoAffido p2 = new PeriodoAffido(2, LocalDate.now().plusWeeks(2), LocalDate.now().plusMonths(1), 300.0f);
        PeriodoAffido p3 = new PeriodoAffido(3,LocalDate.now().plusMonths(1) ,LocalDate.now().plusMonths(2), 600.0f);

        this.elencoPeriodiDisponibili.add(p1);
        this.elencoPeriodiDisponibili.add(p2);
        this.elencoPeriodiDisponibili.add(p3);

        this.elencoPeriodiAffido.add(p1);
        this.elencoPeriodiAffido.add(p2);
        this.elencoPeriodiAffido.add(p3);

    }


    public LinkedList<Corso> nuovaIscrizioneCorso(){
        int capienza;
        elencoCorsiDisponibili.clear();

         // per ciascun corso presente in elencoCorsi viene verificato che il corso non sia pieno 
        // e in caso affermativo viene aggiunto all'elencoCorsiDisponibili
        for(Corso i: elencoCorsi){
            capienza = i.getCapienza();
            if(capienza > 0){
                elencoCorsiDisponibili.add(i);
            }
        }

        Boolean esito = this.caneSelezionato.checkNuovaIscrizioneCorso(elencoCorsiDisponibili);
        if(esito){ // estensione 3a e 3b
           
            return elencoCorsiDisponibili;
        }
        else{
            return null;
        }
    }
    

    public Boolean confermaIscrizioneCorso(Corso cs){
        Boolean esito = this.caneSelezionato.checkConfermaIscrizioneCorso(cs);
        if(esito){ // estensione 5a
            cs.confermaIscrizione(caneSelezionato);
            // l'attributo attualmenteIscritto di caneSelezionato diventa true 
            // e viene inizializzata la variabile corsoCorrente di caneSelezionato
            caneSelezionato.aggiornaAttualmenteIscritto(cs);
            return true;
        }
        else{
            return false;
        }
    }


    public LinkedList<Turno> prenotaTurnoLezione(){
        Lezione lezioneSuccesiva;
        Boolean esito = this.caneSelezionato.getAttualmenteIscritto();
        Turno tc = this.caneSelezionato.getTurnoCorrente();
        if(tc == null){
            if(esito){
                //viene recuperata l'istanza della lezione successiva
                lezioneSuccesiva = caneSelezionato.getLezioneSuccessiva();
                
                // Vengono restituiti i turni disponibili relativi alla lezione successiva
                return lezioneSuccesiva.getTurniDisponibili();
            }
            else{
                return null;
            }
        }
        else{
            return null;
        }
    }

    


    public Boolean selezionaTurno(Turno ts){
        Boolean esito = this.caneSelezionato.checkSovrapposizioneDate(ts);
        if(esito){ // estensione 5b
            try { // estensione 5a
                this.caneSelezionato.aggiornaStatoAvanzamento(ts);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        else{
            return false;
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
        int numPosti = periodoSelezionato.getNumeroPosti();
        Boolean esito;
        if(numPosti > 0){ // estensione 6a
            Boolean esitoCheckSovrapposizione = this.clienteLoggato.checkSovrapposizioneDate(cn, periodoSelezionato);
            if(esitoCheckSovrapposizione){ // estensione 6b
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
            else{
                return false;
            }


        }
        else{
            return false;
        }
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

    public float confermaConclusioneAffido(){
        float importoDovuto = 0;
        PeriodoAffido affidoCorrente = this.caneSelezionato.getAffido();
        int numCaniAffido = this.clienteSelezionato.getNumCaniAffido(affidoCorrente);
        this.caneSelezionato.conclusioneAffido();
        Boolean esitoVerifica = affidoCorrente.verificaIscrizione(this.clienteSelezionato);
        if(esitoVerifica == false){
            this.clienteSelezionato.annullamentoIscrizione(affidoCorrente);
        }
        Boolean anticipo = affidoCorrente.verificaAnticipo();

        if((anticipo == true)&&(numCaniAffido > 1)){
            importoDovuto = affidoCorrente.calcolaImportoDovuto(numCaniAffido, "anticipoAffidoMultiplo");
        }

        if((anticipo == true)&&(numCaniAffido <= 1)){
            importoDovuto = affidoCorrente.calcolaImportoDovuto(numCaniAffido, "anticipoAffido");
        }

        if(anticipo == false){
            importoDovuto = affidoCorrente.calcolaImportoDovuto(numCaniAffido, "affidoMultiplo");
        }

        return importoDovuto;

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
        Cliente cl = clienti.get(codiceCliente);
        if (cl != null) {
            if(cl.getPassword().equals(password))
                return clienti.get(codiceCliente);
            else  
                return null;
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
    

    public String registrati(String nome, String cognome, String numeroTelefono, String password) {
        String codiceCliente;
        Cliente nuovoCliente;
        Boolean esito = checkNumTelefono(numeroTelefono);
        if (esito) {
            codiceCliente = this.generaCodiceCliente(nome);
            nuovoCliente = new Cliente(codiceCliente,nome,cognome,password,numeroTelefono);
            this.clienti.putIfAbsent(codiceCliente, nuovoCliente);
            setClienteLoggato(nuovoCliente);
            return codiceCliente;
        } else {
            return null;
        }
    }

    public void addCliente(Cliente cl) {
        this.clienti.put(cl.getCodiceCliente(), cl);
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
        if(cl != null){
            cn = cl.getCane(codiceCane);
            setCaneSelezionato(cn);
            setClienteSelezionato(cl);
            if (cn == null) {
                return null;
            } else {
                return cn.getAffido();
            }
        }
        else{
            return null;
        }
    }

    public PeriodoAffido concludiAffidoDelega(int codiceDelega, int codiceCane){
        int cd;
        int codC;
        LinkedList<Cane> caniPosseduti;
        
        for(Cliente cl: clienti.values()){
            cd=cl.getCodiceDelega();
            if(cd == codiceDelega){
                caniPosseduti = cl.getCani();
                for(Cane c: caniPosseduti){
                    codC=c.getCodiceCane();
                    if(codC == codiceCane){
                        PB.setClienteSelezionato(cl);
                    }
                }
                
            }
        }

        Cane cn;
        if (this.clienteSelezionato != null) {
            cn = clienteSelezionato.getCane(codiceCane);
            setCaneSelezionato(cn);
            if (cn == null) {
                return null;
            } else {
                return cn.getAffido();
            }
        } else {
            return null;
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
            return false;
        }
        else{
            this.corsoSelezionato = cs;
            return true;
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
            codiceLezione += c.getLezioni().size();
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
            if(c.getCaniIscritti().size()>0){
                corsiConCani.add(c);
            }
        } 
        return corsiConCani;
    }


    public LinkedList<Turno> scambioTurno() {
        Boolean esito = this.caneSelezionato.verificaIdoneitaTurno();
        if (esito) {
            Lezione ultimaLezioneSeguita = this.caneSelezionato.getUltimaLezioneSeguita();
            return ultimaLezioneSeguita.getTurniDisponibili();
        } else {
            return null;
        }
    }

    public void selezionaTurnoScambio(Turno ts){
        Turno tc = this.caneSelezionato.getTurnoCorrente();
        Lezione ultimaLezioneSeguita = this.caneSelezionato.getUltimaLezioneSeguita();
        ultimaLezioneSeguita.effettuaScambioTurno(tc, ts);
        this.caneSelezionato.setTurnoCorrente(ts);
    }   


    
    public LinkedList<Corso> visualizzaProgrammaCorso(){
        return this.elencoCorsi;
    }

    public LinkedList<Lezione> visualizzaProgramma(Corso cs){
        return cs.getLezioni();
    }


    public Map<String,LinkedList<Lezione>> mostraStatoAvanzamentoCorso(){
        Boolean esito = this.caneSelezionato.getAttualmenteIscritto();
        if(esito){
            return caneSelezionato.getAvanzamentoCorso();
        }
        else{
            return null;
        }
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
        for(PeriodoAffido pa: this.elencoPeriodiAffido){
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
        return statoSaluteCani;
    }

    public void cancellaNotifiche() {
        this.clienteLoggato.resettaStatoSaluteCani();
    }

    public Boolean verificaDatiTurno(LocalDate data, LocalTime oraInizio, LocalTime oraFine){
        if((LocalDate.now().isBefore(data)) && (oraInizio.isBefore(oraFine))){
            return true;
        }
        else return false;
    }

    public int delega(){
        int codiceDelega = this.generaCodiceDelega();
        this.clienteLoggato.setCodiceDelega(codiceDelega);
        return codiceDelega;
    }

    public int generaCodiceDelega(){
        Random random = new Random();
        return random.nextInt(999);
    }


    public Corso getCorsoSelezionato() {
        return this.corsoSelezionato;
    }

    public LinkedList<Corso> getCorso(){
        return this.elencoCorsi;
    }


    public LinkedList<PeriodoAffido> getPeriodiAffido(){
        return this.elencoPeriodiAffido;
    }

    public LinkedList<PeriodoAffido> getPeriodiAffidoDisponibili(){
        return this.elencoPeriodiDisponibili;
    }


    public void addPeriodoAffido(PeriodoAffido pa) {
        this.elencoPeriodiAffido.add(pa);
    }

   
    public Turno timbraPrenotazioneTurno(String codiceCliente, int codiceCane){
        Cliente cl = this.clienti.get(codiceCliente);
        if (cl != null) {
            Cane cn = cl.getCane(codiceCane);
            setCaneSelezionato(cn);
            if (cn == null) {
                return null;
            } else {
                return cn.getTurnoCorrente();
            }
        } else {
            return null;
        }
    }


    public void confermaTimbroTurno() {
        this.caneSelezionato.setTurnoCorrente(null);
        Boolean primaLezione = checkPrimaLezione();



    }


    public boolean disdiciTurno() {
        boolean esito;
        
    }


    public void logoutAdmin() {
        setCaneSelezionato(null);
        setClienteSelezionato(null);
        setPeriodoSelezionato(null);
        setCorsoSelezionato(null);
    }
















}
