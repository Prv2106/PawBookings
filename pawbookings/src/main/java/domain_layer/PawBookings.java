package domain_layer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

/**
 * Application service and domain facade.
 * Implements the Singleton pattern and coordinates customers, dogs, courses,
 * lessons, shifts, foster periods, and pricing policies.
 */
public class PawBookings {

    private static PawBookings PB;
    private int numCani;
    private int pinAdmin;

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

    /**
     * Returns the singleton application instance.
     *
     * @return the single {@code PawBookings} instance
     */
    public static PawBookings getInstance() {
         if(PB == null)
             PB = new PawBookings();
        return PB;
    }

    /**
     * Loads the predefined foster periods used by the application.
     */
    public void loadPeriodiAffido() {

        PeriodoAffido p1 = new PeriodoAffido(1, LocalDate.now().plusDays(1), LocalDate.now().plusWeeks(2), 140.0f);
        PeriodoAffido p2 = new PeriodoAffido(2, LocalDate.now().plusWeeks(2), LocalDate.now().plusMonths(1), 280.0f);
        PeriodoAffido p3 = new PeriodoAffido(3, LocalDate.now().plusMonths(1) ,LocalDate.now().plusMonths(2), 560.0f);
        PeriodoAffido p4 = new PeriodoAffido(4, LocalDate.of(2024, 1, 21), LocalDate.of(2024, 2, 11), 210);
        PeriodoAffido p5 = new PeriodoAffido(0, LocalDate.of(2024, 1, 8), LocalDate.of(2024, 2, 5), 280);

        this.elencoPeriodiDisponibili.add(p1);
        this.elencoPeriodiDisponibili.add(p2);
        this.elencoPeriodiDisponibili.add(p3);
        this.elencoPeriodiDisponibili.add(p4);
        this.elencoPeriodiDisponibili.add(p5);

        this.elencoPeriodiAffido.add(p1);
        this.elencoPeriodiAffido.add(p2);
        this.elencoPeriodiAffido.add(p3);
        this.elencoPeriodiAffido.add(p4);
        this.elencoPeriodiAffido.add(p5);
    }

    /**
     * Returns the courses in which the selected dog may enroll.
     *
     * @return available courses, or {@code null} when enrollment is not allowed
     */
    public LinkedList<Corso> nuovaIscrizioneCorso(){
        int capienza;
        elencoCorsiDisponibili.clear();

        for(Corso i: elencoCorsi){
            capienza = i.getCapienza();
            if(capienza > 0){
                elencoCorsiDisponibili.add(i);
            }
        }

        Boolean esito = this.caneSelezionato.checkNuovaIscrizioneCorso(elencoCorsiDisponibili);
        if(esito){ // Use-case extensions 3a and 3b.

            return elencoCorsiDisponibili;
        }
        else{
            return null;
        }
    }

    /**
     * Enrolls the selected dog in a course after validating progression rules.
     *
     * @param cs course selected for enrollment
     * @return {@code true} when enrollment succeeds
     */
    public Boolean confermaIscrizioneCorso(Corso cs){
        Boolean esito = this.caneSelezionato.checkConfermaIscrizioneCorso(cs);
        if(esito){ // Use-case extension 5a.
            cs.confermaIscrizione(caneSelezionato);

            caneSelezionato.aggiornaAttualmenteIscritto(cs);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Returns the shifts available for the next lesson of the selected dog.
     *
     * @return available shifts, or {@code null} when no booking is allowed
     */
    public LinkedList<Turno> prenotaTurnoLezione(){
        Lezione lezioneSuccesiva;
        Boolean esito = this.caneSelezionato.getAttualmenteIscritto();
        Turno tc = this.caneSelezionato.getTurnoCorrente();
        if(tc == null){
            if(esito){
                lezioneSuccesiva = caneSelezionato.getLezioneSuccessiva();
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

    /**
     * Books a shift for the selected dog after checking date overlaps.
     *
     * @param ts shift to book
     * @return {@code true} when the booking succeeds
     */
    public Boolean selezionaTurno(Turno ts){
        Boolean esito = this.caneSelezionato.checkSovrapposizioneDate(ts);
        if(esito){ // Use-case extension 5b.
            try { // Use-case extension 5a.
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

    /**
     * Selects the dog used by subsequent operations.
     *
     * @param cn dog to select
     */
    public void selezionaCane(Cane cn){
        this.caneSelezionato = cn;
    }

    /**
     * Returns the registered customers indexed by customer code.
     *
     * @return customer map
     */
    public Map<String, Cliente> getClienti(){
        return this.clienti;
    }

    /**
     * Returns the currently selected dog.
     *
     * @return selected dog, or {@code null}
     */
    public Cane getCaneSelezionato() {
        return this.caneSelezionato;
    }

    /**
     * Returns all courses.
     *
     * @return course list
     */
    public LinkedList<Corso> getCorsi() {
        return this.elencoCorsi;
    }

    /**
     * Returns the foster periods that still have free places.
     *
     * @return available foster periods
     */
    public LinkedList<PeriodoAffido> affido(){
        return this.elencoPeriodiDisponibili;
    }

    /**
     * Selects a foster period and returns the customer's eligible dogs.
     *
     * @param pa foster period selected by the customer
     * @return dogs that are not currently in foster care
     */
    public LinkedList<Cane> selezionaPeriodo(PeriodoAffido pa){
        setPeriodoSelezionato(pa);
        return this.clienteLoggato.getCaniNonInAffido();
    }

    /**
     * Registers a dog in the selected foster period.
     *
     * @param cn dog to place in foster care
     * @return {@code true} when the registration succeeds
     */
    public Boolean confermaAffido(Cane cn){
        int numeroPostiDisponibili;
        int numPosti = periodoSelezionato.getNumeroPosti();
        Boolean esito;
        if(numPosti > 0){ // Use-case extension 6a.
            Boolean esitoCheckSovrapposizione = this.clienteLoggato.checkSovrapposizioneDate(cn, periodoSelezionato);
            if(esitoCheckSovrapposizione){ // Use-case extension 6b.
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

    /**
     * Sets the selected foster period.
     *
     * @param pa period to select, or {@code null} to clear it
     * @return {@code true} when a non-null period was set
     */
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

    /**
     * Concludes the selected dog's foster period and calculates the amount due.
     *
     * @return amount owed by the customer
     */
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

    /**
     * Authenticates a customer.
     *
     * @param codiceCliente customer code
     * @param password access password
     * @return {@code true} when the credentials are valid
     */
    public Boolean accedi(String codiceCliente, String password){
        Cliente cl;
        cl=this.verificaCliente(codiceCliente, password);
        return this.setClienteLoggato(cl);
    }

    /**
     * Clears the logged-in customer and the selected dog.
     */
    public void logout() {
        setClienteLoggato(null);
        setCaneSelezionato(null);
    }

    /**
     * Verifies customer credentials.
     *
     * @param codiceCliente customer code
     * @param password access password
     * @return matching customer, or {@code null} when credentials are invalid
     */
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

    /**
     * Sets the logged-in customer.
     *
     * @param cl customer to store, or {@code null} to clear it
     * @return {@code true} when a non-null customer was set
     */
    public boolean setClienteLoggato(Cliente cl) {
        if (cl == null) {
            this.clienteLoggato = null;
            return false;
        } else {
            this.clienteLoggato = cl;
            return true;
        }
    }

    /**
     * Registers a new customer when the telephone number is unique.
     *
     * @param nome first name
     * @param cognome surname
     * @param numeroTelefono telephone number
     * @param password access password
     * @return generated customer code, or {@code null} when the number is already
     *         registered
     */
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

    /**
     * Adds an existing customer to the registry.
     *
     * @param cl customer to add
     */
    public void addCliente(Cliente cl) {
        this.clienti.put(cl.getCodiceCliente(), cl);
    }

    /**
     * Generates a customer code from the name and the current customer count.
     *
     * @param nome customer first name
     * @return generated customer code
     */
    public String generaCodiceCliente(String nome){
        return (nome + (clienti.size() +1));
    }

    /**
     * Removes a dog from the logged-in customer and clears course associations.
     *
     * @param cn dog to remove
     * @return {@code true} when the dog was removed
     */
    public Boolean confermaRimozioneCane(Cane cn){
        cn.aggiornaAssociazioniCane();
        return this.clienteLoggato.rimuoviCane(cn);
    }

    /**
     * Returns the logged-in customer's dogs that can be removed.
     *
     * @return dogs not currently in foster care
     */
    public LinkedList<Cane> rimuoviCane(){
        return clienteLoggato.getCaniNonInAffido();
    }

    /**
     * Registers a new dog for the logged-in customer.
     *
     * @param nome dog name
     * @param razza dog breed
     * @return {@code true} when the dog was added
     */
    public Boolean aggiungiCane(String nome, String razza){
        int codiceCane = this.generaCodiceCane();
        return clienteLoggato.registraCane(nome, razza, codiceCane);
    }

    /**
     * Finds the foster period of a dog owned by a customer.
     *
     * @param codiceCliente customer code
     * @param codiceCane dog code
     * @return foster period, or {@code null} when no match exists
     */
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

    /**
     * Finds a foster period using a delegation code and a dog code.
     *
     * @param codiceDelega delegation code
     * @param codiceCane dog code
     * @return foster period, or {@code null} when no match exists
     */
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

    /**
     * Sets the customer used by administrative operations.
     *
     * @param cl customer to select, or {@code null} to clear it
     * @return {@code true} when a non-null customer was set
     */
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

    /**
     * Authenticates an administrator by PIN.
     *
     * @param pin administrator PIN
     * @return {@code true} when the PIN is valid
     */
    public Boolean accediComeAdmin(int pin){
        return checkPin(pin);
    }

    /**
     * Checks the administrator PIN.
     *
     * @param pin PIN to verify
     * @return {@code true} when the PIN matches
     */
    public Boolean checkPin(Integer pin){
        if(this.pinAdmin == pin){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Sets the selected dog.
     *
     * @param cn dog to select, or {@code null} to clear it
     * @return {@code true} when a non-null dog was set
     */
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

    /**
     * Returns the logged-in customer.
     *
     * @return logged-in customer, or {@code null}
     */
    public Cliente getClienteLoggato() {
        return this.clienteLoggato;
    }

    /**
     * Returns the selected foster period.
     *
     * @return selected foster period, or {@code null}
     */
    public PeriodoAffido getPeriodoSelezionato(){
        return this.periodoSelezionato;
    }

    /**
     * Generates the next dog code.
     *
     * @return new dog code
     */
    public int generaCodiceCane(){
        this.numCani += 1;
        return this.numCani;

    }

    /**
     * Returns the number of dogs registered in the system.
     *
     * @return dog counter
     */
    public int getNumeroCani(){
        return this.numCani;
    }

    /**
     * Checks whether a telephone number is still available.
     *
     * @param numeroTelefono telephone number to test
     * @return {@code true} when the number is not already registered
     */
    public boolean checkNumTelefono(String numeroTelefono) {
        for (Cliente c : this.clienti.values()) {
            if (c.getNumTelefono().equals(numeroTelefono)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Creates and stores a new course.
     *
     * @param tipoCorso course type
     * @param capienza course capacity
     * @param costo course price
     * @return {@code true} when the course was added
     */
    public Boolean inserisciNuovoCorso(String tipoCorso, int capienza, float costo){
        int codiceCorso = this.generaCodiceCorso();
        Corso nuovCorso = new Corso(codiceCorso, capienza, costo, tipoCorso);
        return this.elencoCorsi.add(nuovCorso);
    }

    /**
     * Generates the next course code.
     *
     * @return new course code
     */
    public int generaCodiceCorso(){
        return (this.elencoCorsi.size() +1);
    }

    /**
     * Returns the courses whose information can be edited.
     *
     * @return course list
     */
    public LinkedList<Corso> modificaInformazioniCorso(){
        return this.elencoCorsi;
    }

    /**
     * Selects a course for administrative operations.
     *
     * @param cs course to select
     */
    public void selezionaCorso(Corso cs){
        this.setCorsoSelezionato(cs);
    }

    /**
     * Sets the selected course.
     *
     * @param cs course to select, or {@code null} to clear it
     * @return {@code true} when a non-null course was set
     */
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

    /**
     * Updates the selected course capacity and price.
     *
     * @param capienza new capacity
     * @param costo new price
     * @return {@code true} when the update was applied
     */
    public Boolean modificaCorso(int capienza, float costo){
            return this.corsoSelezionato.aggiornaInformazioni(capienza,costo);
    }

    /**
     * Returns the courses whose programme can be edited.
     *
     * @return course list
     */
    public LinkedList<Corso> modificaProgrammaCorso(){
        return this.elencoCorsi;
    }

    /**
     * Creates a new lesson in the selected course.
     *
     * @param nome lesson name
     */
    public void nuovaLezione(String nome){
        int codiceLezione = this.generaCodiceLezione();
        this.corsoSelezionato.nuovaLezione(codiceLezione, nome);
    }

    /**
     * Adds an exercise to the lesson being edited.
     *
     * @param nome exercise name
     * @param descrizione exercise description
     */
    public void inserisciEsercizio(String nome, String descrizione){
        Lezione lezioneCorrente = this.corsoSelezionato.getLezioneCorrente();
        lezioneCorrente.nuovoEsercizio(nome, descrizione);
    }

    /**
     * Confirms the lesson being edited and adds it to the course programme.
     *
     * @return {@code true} when the lesson was added
     */
    public Boolean confermaLezione(){
        return this.corsoSelezionato.confermaInserimentoLezione();
    }

    /**
     * Generates the next lesson code across all courses.
     *
     * @return new lesson code
     */
    public int generaCodiceLezione(){
        int codiceLezione=0;
        for(Corso c: this.elencoCorsi){
            codiceLezione += c.getLezioni().size();
        }
        return (codiceLezione+1);
    }

    /**
     * Returns the courses that have at least one enrolled dog.
     *
     * @return courses eligible for shift creation
     */
    public LinkedList<Corso> inserisciTurnoLezione(){
        return this.calcolaCorsiConCaniIscritti();
    }

    /**
     * Selects a course for shift management and returns its programme.
     *
     * @param cs course to manage
     * @return course lessons
     */
    public LinkedList<Lezione> selezionaCorsoModificaTurni(Corso cs){
        LinkedList<Lezione> programma = cs.getLezioni();
        this.setCorsoSelezionato(cs);
        return programma;
    }

    /**
     * Selects a lesson for shift creation.
     *
     * @param lz lesson to select
     */
    public void selezionaLezione(Lezione lz){
        this.corsoSelezionato.setLezioneSelezionata(lz);
    }

    /**
     * Creates a shift in the selected lesson after validating date and time.
     *
     * @param data shift date
     * @param oraInizio start time
     * @param oraFine end time
     * @return {@code true} when the shift was created
     */
    public Boolean nuovoTurno(LocalDate data, LocalTime oraInizio, LocalTime oraFine){
        Boolean esito = verificaDatiTurno(data, oraInizio, oraFine);
        if(esito){
            int codiceTurno = this.corsoSelezionato.generaCodiceTurno();
            Lezione lezioneSelezionata = this.corsoSelezionato.getLezioneSelezionata();
            lezioneSelezionata.nuovoTurno(codiceTurno, data, oraInizio, oraFine);
        }
        return esito;
    }

    /**
     * Calculates the courses with at least one enrolled dog.
     *
     * @return eligible courses
     */
    public LinkedList<Corso>  calcolaCorsiConCaniIscritti(){
        LinkedList<Corso> corsiConCani = new LinkedList<>();
        for(Corso c: elencoCorsi){
            if(c.getCaniIscritti().size()>0){
                corsiConCani.add(c);
            }
        }
        return corsiConCani;
    }

    /**
     * Returns the shifts available for exchanging the selected dog's booking.
     *
     * @return available shifts, or {@code null} when exchange is not allowed
     */
    public LinkedList<Turno> scambioTurno() {
        Boolean esito = this.caneSelezionato.verificaIdoneitaTurno();
        if (esito) {
            Lezione ultimaLezioneSeguita = this.caneSelezionato.getUltimaLezioneSeguita();
            return ultimaLezioneSeguita.getTurniDisponibili();
        } else {
            return null;
        }
    }

    /**
     * Exchanges the selected dog's current shift with a new one.
     *
     * @param ts replacement shift
     */
    public void selezionaTurnoScambio(Turno ts){
        Turno tc = this.caneSelezionato.getTurnoCorrente();
        Lezione ultimaLezioneSeguita = this.caneSelezionato.getUltimaLezioneSeguita();
        ultimaLezioneSeguita.effettuaScambioTurno(tc, ts);
        this.caneSelezionato.setTurnoCorrente(ts);
    }

    /**
     * Returns the courses whose programme can be viewed.
     *
     * @return course list
     */
    public LinkedList<Corso> visualizzaProgrammaCorso(){
        return this.elencoCorsi;
    }

    /**
     * Returns the programme of a course.
     *
     * @param cs course to inspect
     * @return course lessons
     */
    public LinkedList<Lezione> visualizzaProgramma(Corso cs){
        return cs.getLezioni();
    }

    /**
     * Returns the selected dog's progress in its current course.
     *
     * @return progress map, or {@code null} when the dog is not enrolled
     */
    public Map<String,LinkedList<Lezione>> mostraStatoAvanzamentoCorso(){
        Boolean esito = this.caneSelezionato.getAttualmenteIscritto();
        if(esito){
            return caneSelezionato.getAvanzamentoCorso();
        }
        else{
            return null;
        }
    }

    /**
     * Returns the foster periods with at least one dog in care.
     *
     * @return foster periods eligible for health-status updates
     */
    public LinkedList<PeriodoAffido> notificaStatoSalute(){
        return calcolaPeriodoCaneRegistrato();
    }

    /**
     * Returns the dogs in a foster period and selects that period.
     *
     * @param pa foster period to inspect
     * @return dogs in foster care for the period
     */
    public LinkedList<Cane> mostraCaniInAffido(PeriodoAffido pa){
        LinkedList<Cane> elencoCaniAffido = pa.getCaniAffido();
        setPeriodoSelezionato(pa);
        return elencoCaniAffido;
    }

    /**
     * Calculates the foster periods containing at least one dog.
     *
     * @return foster periods with registered dogs
     */
    public LinkedList<PeriodoAffido> calcolaPeriodoCaneRegistrato(){
        LinkedList<PeriodoAffido> elencoPeriodiAffidoCaniRegistrati= new LinkedList<>();
        for(PeriodoAffido pa: this.elencoPeriodiAffido){
            if(pa.getNumeroPosti()<pa.getCapienzaMassima()){
                elencoPeriodiAffidoCaniRegistrati.add(pa);
            }
        }
        return elencoPeriodiAffidoCaniRegistrati;
    }

    /**
     * Sends a health-status update for the selected foster period.
     *
     * @param mappaStatoSalute map of dog codes to health-status messages
     */
    public void notificaClienti(Map<Integer,String> mappaStatoSalute){
        this.periodoSelezionato.aggiornaStatoSalute(mappaStatoSalute);
    }

    /**
     * Returns the logged-in customer's health-status notifications.
     *
     * @return notification list
     */
    public LinkedList<Map<String,String>> leggiStatoSalute(){
        LinkedList<Map<String,String>> statoSaluteCani = this.clienteLoggato.getStatoSalute();
        return statoSaluteCani;
    }

    /**
     * Clears the logged-in customer's notifications.
     */
    public void cancellaNotifiche() {
        this.clienteLoggato.resettaStatoSaluteCani();
    }

    /**
     * Validates shift data.
     *
     * @param data shift date, which must be in the future
     * @param oraInizio start time
     * @param oraFine end time, which must be after the start time
     * @return {@code true} when the data are valid
     */
    public Boolean verificaDatiTurno(LocalDate data, LocalTime oraInizio, LocalTime oraFine){
        if((LocalDate.now().isBefore(data)) && (oraInizio.isBefore(oraFine))){
            return true;
        }
        else return false;
    }

    /**
     * Generates and assigns a delegation code to the logged-in customer.
     *
     * @return generated delegation code
     */
    public int delega(){
        int codiceDelega = this.generaCodiceDelega();
        this.clienteLoggato.setCodiceDelega(codiceDelega);
        return codiceDelega;
    }

    /**
     * Generates a random delegation code.
     *
     * @return delegation code
     */
    public int generaCodiceDelega(){
        Random random = new Random();
        return random.nextInt(999);
    }

    /**
     * Returns the selected course.
     *
     * @return selected course, or {@code null}
     */
    public Corso getCorsoSelezionato() {
        return this.corsoSelezionato;
    }

    /**
     * Returns all courses.
     *
     * @return course list
     */
    public LinkedList<Corso> getCorso(){
        return this.elencoCorsi;
    }

    /**
     * Returns every foster period.
     *
     * @return foster periods
     */
    public LinkedList<PeriodoAffido> getPeriodiAffido(){
        return this.elencoPeriodiAffido;
    }

    /**
     * Returns the foster periods that still have free places.
     *
     * @return available foster periods
     */
    public LinkedList<PeriodoAffido> getPeriodiAffidoDisponibili(){
        return this.elencoPeriodiDisponibili;
    }

    /**
     * Adds a foster period to the catalogue.
     *
     * @param pa period to add
     */
    public void addPeriodoAffido(PeriodoAffido pa) {
        this.elencoPeriodiAffido.add(pa);
    }

    /**
     * Finds the shift booked by a customer's dog for check-in.
     *
     * @param codiceCliente customer code
     * @param codiceCane dog code
     * @return booked shift, or {@code null} when no match exists
     */
    public Turno timbraPrenotazioneTurno(String codiceCliente, int codiceCane){
        Cliente cl = this.clienti.get(codiceCliente);
        if (cl != null) {
            Cane cn = cl.getCane(codiceCane);
            setCaneSelezionato(cn);
            setClienteSelezionato(cl);
            if (cn == null) {
                return null;
            } else {
                return cn.getTurnoCorrente();
            }
        } else {
            return null;
        }
    }

    /**
     * Confirms the selected dog's shift check-in and calculates the amount due.
     *
     * @return amount owed by the customer
     */
    public float confermaTimbroTurno() {
        float importoDovuto =0;
        Boolean primaLezione = this.caneSelezionato.checkPrimaLezione();
        Boolean recuperoLezione = this.caneSelezionato.getLezioneDaRecuperare();
        Corso corsoCorrente = this.caneSelezionato.getCorsoCorrente();
        int numCaniIscritti;
        Corso cs = null;
        if(corsoCorrente!=null){
            numCaniIscritti = this.clienteSelezionato.getNumCaniIscritti(corsoCorrente);
        }
        else{
            cs = this.caneSelezionato.getCorsiCompletati().getLast();
            numCaniIscritti = this.clienteSelezionato.getNumCaniIscritti(cs);
        }

        if((primaLezione)&&(recuperoLezione)){
            this.caneSelezionato.setLezioneDaRecuperare(false);
            importoDovuto = corsoCorrente.calcolaImportoDovuto(numCaniIscritti, "recuperoIscrizioneMultipla");
        }
        if((primaLezione)&&(recuperoLezione == false)){
            importoDovuto = corsoCorrente.calcolaImportoDovuto(numCaniIscritti, "iscrizioneMultipla");
        }
        if((primaLezione == false)&&(recuperoLezione)){
            this.caneSelezionato.setLezioneDaRecuperare(false);
            if(corsoCorrente != null){
                importoDovuto = corsoCorrente.calcolaImportoDovuto(numCaniIscritti,  "recuperoLezione");
            }
            else{
                importoDovuto = cs.calcolaImportoDovuto(numCaniIscritti,  "recuperoLezione");
            }
        }

        this.caneSelezionato.setTurnoCorrente(null);
        return importoDovuto;
    }

    /**
     * Cancels the selected dog's current booking when still allowed.
     *
     * @return {@code true} when the booking was cancelled
     */
    public boolean disdiciTurno() {
        boolean esito;
        esito = this.caneSelezionato.verificaIdoneitaTurno();
        if (esito) {
            Lezione ultimaLezioneSeguita = this.caneSelezionato.getUltimaLezioneSeguita();
            Corso corsoCorrente = this.caneSelezionato.getCorsoCorrente();
            Turno turnoCorrente = this.caneSelezionato.getTurnoCorrente();
            LinkedList<Turno> elencoTurniDisponibili = ultimaLezioneSeguita.getElencoTurniDisponibili();
            elencoTurniDisponibili.add(turnoCorrente);
            if(corsoCorrente == null){
                LinkedList<Corso> corsiCompletati = this.caneSelezionato.getCorsiCompletati();
                Corso cs = corsiCompletati.getLast();
                this.caneSelezionato.aggiornaAttualmenteIscritto(cs);
            }
            this.caneSelezionato.setTurnoCorrente(null);
            this.caneSelezionato.rimuoviUltimaLezioneSeguita();
        }
        return esito;
    }

    /**
     * Returns the shifts available for recovering a missed lesson.
     *
     * @return available shifts, or {@code null} when recovery is not allowed
     */
    public LinkedList<Turno> recuperaLezione() {
        boolean esito;
        Turno tc = this.caneSelezionato.getTurnoCorrente();
        if(tc!=null){
            esito = this.caneSelezionato.verificaIdoneitaTurno();
            if (!esito) {
                Lezione ultimaLezioneSeguita = this.caneSelezionato.getUltimaLezioneSeguita();
                return ultimaLezioneSeguita.getTurniDisponibili();
            } else {
                return null;
            }
        }
        else{
            return null;
        }
    }

    /**
     * Books a recovery shift for the selected dog.
     *
     * @param ts recovery shift
     * @return {@code true} when the booking succeeds
     */
    public boolean selezionaTurnoRecupero(Turno ts) {
        boolean esito;
        esito = this.caneSelezionato.checkSovrapposizioneDate(ts);
        if (esito) {
            this.caneSelezionato.setTurnoCorrente(ts);
            this.caneSelezionato.setLezioneDaRecuperare(true);
            Lezione ultimaLezioneSeguita = this.caneSelezionato.getUltimaLezioneSeguita();
            ultimaLezioneSeguita.aggiornaTurniDisponibili(ts);
        }
        return esito;
    }

    /**
     * Clears all selections made by the administrator.
     */
    public void logoutAdmin() {
        setCaneSelezionato(null);
        setClienteSelezionato(null);
        setPeriodoSelezionato(null);
        setCorsoSelezionato(null);
    }

}
