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

 

    // metodo costruttore
    private PawBookings(){
        this.elencoCorsi = new LinkedList<>();
        this.elencoCorsiDisponibili = new LinkedList<>();
        this.clienti = new HashMap<>();
        this.elencoPeriodiDisponibili = new LinkedList<>();
        this.pinAdmin = 1234; 
        this.loadCorsi();
        this.loadPeriodiAffido();
    }


     // metodo per recuperare l'unica istanza della classe PawBookings per il pattern GoF Singleton
     public static PawBookings getInstance() {
         if(PB == null){
             PB = new PawBookings();
             System.out.println("istanza creata");
         }
         else{
             System.out.println("Istanza già creata");
         }
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

            // la lista elencoCorsiDisponibili viene svuotata
            elencoCorsiDisponibili.clear();

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
        this.periodoSelezionato.registraAffido(cn);
        numeroPostiDisponibili= this.periodoSelezionato.getNumeroPosti();
        if(numeroPostiDisponibili == 0){
            this.elencoPeriodiDisponibili.remove(this.periodoSelezionato);
        }
        return true;
    }

    public void setPeriodoSelezionato(PeriodoAffido pa){
        this.periodoSelezionato = pa;
    }

    public Boolean confermaConclusioneAffido(){
        return this.caneSelezionato.conclusioneAffido();
    }



    public Boolean accedi(String codiceCliente, String password){
        Cliente cl;
        cl=this.verificaCliente(codiceCliente, password);
        this.setClienteLoggato(cl);
        return true;
    }


    public Cliente verificaCliente(String codiceCliente, String password){
        if(clienti.get(codiceCliente).getPassword() == password){
            return clienti.get(codiceCliente);
        }
        else return null;
    }



    public void setClienteLoggato(Cliente cl){
        this.clienteLoggato = cl;
    }
    

    public Boolean registrati(String nome, String cognome, String numeroTelefono, String password){
        String codiceCliente;
        Cliente nuovoCliente;
        codiceCliente= this.generaCodiceCliente(nome);
        nuovoCliente = new Cliente(codiceCliente,nome,cognome,password,numeroTelefono);
        this.clienti.putIfAbsent(codiceCliente, nuovoCliente);
        return true;
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
        return clienteLoggato.registraCane(nome, razza);
    }

    public PeriodoAffido concludiAffido(String codiceCliente, int codiceCane){
        Cliente cl;
        Cane cn;
        PeriodoAffido affido;
        cl = this.clienti.get(codiceCliente);
        cn = cl.getCane(codiceCane);
        affido = cn.getAffido();
        setCaneSelezionato(cn);
        return affido;
    }

    public Boolean accediComeAdmin(int pin){
        return checkPin(pin);
    }

    public Boolean checkPin(int pin){
        if(pin == this.pinAdmin){
            return true;
        }
        else{
            return false;
        }
    }


    public void setCaneSelezionato(Cane cn){
        this.caneSelezionato = cn;
    }


}
