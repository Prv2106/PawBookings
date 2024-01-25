package domain_layer;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class PawBookings {
    
    private static PawBookings PB;

    public LinkedList<Corso> elencoCorsi;
    private LinkedList<Corso> elencoCorsiDisponibili;
    private Map<String, Cliente> clienti;
    private Cane caneSelezionato;
    private int pinAdmin;
    private LinkedList<PeriodoAffido> elencoPeriodiDisponibili;
    private Cliente clienteLoggato;
    private PeriodoAffido periodoSelezionato;
    private int numCani;


 

    // metodo costruttore
    private PawBookings(){
        this.elencoCorsi = new LinkedList<>();
        this.elencoCorsiDisponibili = new LinkedList<>();
        this.clienti = new HashMap<>();
        this.elencoPeriodiDisponibili = new LinkedList<>();
        this.pinAdmin = 1234;
        this.numCani= 0;
        this.elencoPeriodiDisponibili = new LinkedList<>();
        this.loadCorsi();
        this.loadPeriodiAffido();
    }


     // metodo per recuperare l'unica istanza della classe PawBookings per il pattern GoF Singleton
     public static PawBookings getInstance() {
         if(PB == null)
             PB = new PawBookings();
        return PB;
    }


    public void loadCorsi(){
        CorsoBase corsoBase = new CorsoBase(1,10,200.0F,"Corso Base");
        CorsoAvanzato corsoAvanzato = new CorsoAvanzato(2,10,250.0F,"Corso Avanzato");
        CorsoAgility corsoAgility = new CorsoAgility(3,5,300.0F,"Corso Agility");

        this.elencoCorsi.add(corsoBase);
        this.elencoCorsi.add(corsoAvanzato);
        this.elencoCorsi.add(corsoAgility);

        System.out.println("Caricamento Corsi completato con successo!");        
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
        esito = this.periodoSelezionato.registraAffido(cn);
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
        return this.caneSelezionato.conclusioneAffido();
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
            return null;
        } else {
            setCaneSelezionato(cn);
            return cn.getAffido();
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
}
