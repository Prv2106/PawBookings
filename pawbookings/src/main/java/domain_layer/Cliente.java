package domain_layer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Represents a customer of the dog training centre.
 * Stores personal data, owned dogs, health-status notifications, and the
 * delegation code. A customer observes foster periods to receive health
 * updates for the dogs in its care.
 */
public class Cliente implements Observer {
    private boolean notifica;
    private String codiceCliente;
    private String nome;
    private String cognome;
    private String password;
    private String numeroTelefono;
    private LinkedList<Cane> caniPosseduti;
    private LinkedList<Map<String, String>> statoSaluteCani;
    private LinkedList<PeriodoAffido> periodiAffido;
    private int codiceDelega;

    /**
     * Creates a customer with the supplied credentials and contact details.
     *
     * @param codiceCliente unique customer code
     * @param nome first name
     * @param cognome surname
     * @param password access password
     * @param numeroTelefono telephone number
     */
    public Cliente (String codiceCliente, String nome, String cognome,String password,String numeroTelefono) {
        this.codiceCliente = codiceCliente;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.numeroTelefono = numeroTelefono;
        this.notifica = false;
        this.caniPosseduti = new LinkedList<>();
        this.statoSaluteCani = new LinkedList<>();
        this.statoSaluteCani = new LinkedList<>();
        this.periodiAffido = new LinkedList<>();
        this.codiceDelega = 0;
    }

    /**
     * Counts the customer's dogs enrolled in the supplied foster period.
     *
     * @param affidoCorrente foster period to inspect
     * @return number of dogs from this customer in that period
     */
    public int getNumCaniAffido(PeriodoAffido affidoCorrente){
        int numCaniAffido =0;
        PeriodoAffido pa;
        for(Cane c: this.caniPosseduti){
            pa = c.getAffidoCorrente();
            if(affidoCorrente.equals(pa)){
                numCaniAffido++;
            }
        }
        return numCaniAffido;
    }

    /**
     * Counts the customer's dogs enrolled in the first lesson of a course.
     *
     * @param corsoCorrente course to inspect
     * @return number of dogs attending the first lesson
     */
    public int getNumCaniIscritti(Corso corsoCorrente){
        int numCaniIscritti =0;
        Corso c;
        for(Cane cn: this.caniPosseduti){
            c = cn.getCorsoCorrente();
            if(corsoCorrente.equals(c)
                &&(cn.getTurnoCorrente()!=null)
                &&(cn.getUltimaLezioneSeguita().equals(c.getLezioni().getFirst()))){
                numCaniIscritti++;
            }
        }
        return numCaniIscritti;
    }

    /**
     * Returns the customer's dogs that are not currently in foster care.
     *
     * @return dogs available for a new foster period
     */
    public LinkedList<Cane> getCaniNonInAffido() {
        return this.calcolaCaniNonInAffido();
    }

    /**
     * Calculates the customer's dogs that are not currently in foster care.
     *
     * @return dogs available for a new foster period
     */
    public LinkedList<Cane> calcolaCaniNonInAffido() {
        LinkedList<Cane> elencoCaniNonInAffido = new LinkedList<>();
        for (Cane cn : this.caniPosseduti) {
            if (!cn.getAttualmenteInAffido()) {
                elencoCaniNonInAffido.add(cn);
            }
        }
        return elencoCaniNonInAffido;
    }

    /**
     * Registers a new dog for the customer.
     *
     * @param nome dog name
     * @param razza dog breed
     * @param codiceCane unique dog code
     * @return {@code true} when the dog was added
     */
    public boolean registraCane(String nome, String razza, int codiceCane){
        Cane nuovoCane = new Cane(codiceCane, nome, razza);
        return caniPosseduti.add(nuovoCane);
    }

    /**
     * Removes a dog from the customer's owned dogs.
     *
     * @param cn dog to remove
     * @return {@code true} when the dog was removed
     */
    public Boolean rimuoviCane(Cane cn) {
        return this.caniPosseduti.remove(cn);
    }

    /**
     * Subscribes the customer to health-status notifications for a foster period.
     *
     * @param periodoSelezionato foster period to observe
     */
    public void iscrizioneNotificheStatoSalute(PeriodoAffido periodoSelezionato) {
        periodoSelezionato.addObserver(this);
    }

    /**
     * Cancels the customer's subscription to a foster period.
     *
     * @param periodoCorrente foster period to stop observing
     */
    public void annullamentoIscrizione(PeriodoAffido periodoCorrente) {
        periodoCorrente.deleteObserver(this);
    }

    /**
     * Clears health-status notifications and resets the notification flag.
     */
    public void  resettaStatoSaluteCani() {
        this.setNotifica(false);
        this.statoSaluteCani.clear();
    }

    /**
     * Receives a health-status update from an observed foster period.
     *
     * @param o observed foster period
     */
    @Override
    public void update(Observable o) {
        PeriodoAffido pa = (PeriodoAffido) o;
        Map<Integer,String> mappaStatoSalute = pa.getState();
        int codiceCane;
        String nomeCane;
        String stato;

        // Example map entry: {1: "In very good condition"}.
        Map<String,String> mappaStatoSaluteCani = new HashMap<>();

        for (Cane c: this.caniPosseduti) {
            codiceCane = c.getCodiceCane();
            nomeCane = c.getNome();
            for (Integer codiceCaneMappa: mappaStatoSalute.keySet()) {
                // Copies the health status of each dog owned by the customer.

                if (codiceCane==codiceCaneMappa) {
                    stato = mappaStatoSalute.get(codiceCane);
                    mappaStatoSaluteCani.put(nomeCane,stato);
                }
            }
        }

        this.statoSaluteCani.add(mappaStatoSaluteCani);
        this.setNotifica(true);
    }

    /**
     * Sets the notification flag.
     *
     * @param val new flag value
     */
    public void setNotifica(Boolean val){
        this.notifica = val;
    }

    /**
     * Returns the customer code.
     *
     * @return customer code
     */
    public String getCodiceCliente() {
        return this.codiceCliente;
    }

    /**
     * Returns the customer first name.
     *
     * @return first name
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Returns the customer surname.
     *
     * @return surname
     */
    public String getCognome() {
        return this.cognome;
    }

    /**
     * Returns the customer's dogs.
     *
     * @return owned dogs
     */
    public LinkedList<Cane> getCani() {
        return this.caniPosseduti;
    }

    /**
     * Returns the access password.
     *
     * @return password
     */
    public String getPassword(){
        return this.password;
    }

    /**
     * Finds one of the customer's dogs by code.
     *
     * @param codiceCane dog code
     * @return matching dog, or {@code null} when not found
     */
    public Cane getCane(int codiceCane) {
        for (Cane cn : this.caniPosseduti) {
            if (cn.getCodiceCane() == codiceCane) {
                return cn;
            }
        }
        return null;
    }

    /**
     * Returns the telephone number.
     *
     * @return telephone number
     */
    public String getNumTelefono() {
        return this.numeroTelefono;
    }

    /**
     * Returns whether a notification is pending.
     *
     * @return notification flag
     */
    public boolean getNotifica() {
        return this.notifica;
    }

    /**
     * Returns the stored health-status notifications.
     *
     * @return list of dog-name to health-status maps
     */
    public LinkedList<Map<String,String>> getStatoSalute(){
        return this.statoSaluteCani;
    }

    /**
     * Replaces the stored health-status notifications.
     *
     * @param mappaStatoSalute new notification list
     */
    public void setStatoSaluteCani(LinkedList<Map<String, String>> mappaStatoSalute){
        this.statoSaluteCani = mappaStatoSalute;
    }
    /**
     * Adds an existing dog to the customer.
     *
     * @param cn dog to add
     */
    public void addCane(Cane cn) {
        this.caniPosseduti.add(cn);
    }

    /**
     * Sets the delegation code.
     *
     * @param codiceDelega delegation code
     */
    public void setCodiceDelega(int codiceDelega) {
        this.codiceDelega = codiceDelega;
    }

    /**
     * Checks whether a dog's current shift overlaps a foster period.
     *
     * @param cn dog to inspect
     * @param pa foster period to test
     * @return {@code false} when the shift overlaps the period
     */
    public boolean checkSovrapposizioneDate(Cane cn, PeriodoAffido pa){
        if (cn.getTurnoCorrente() == null) {
            return true;
        } else {
            if((cn.getTurnoCorrente().getData().isAfter(pa.getDataInizio())) && (cn.getTurnoCorrente().getData().isBefore(pa.getDataFine()))
                || (cn.getTurnoCorrente().getData().equals(pa.getDataInizio()))
                || (cn.getTurnoCorrente().getData().equals(pa.getDataFine()))){
                return false;
            }
            else{
                return true;
            }
        }
    }

    /**
     * Returns the delegation code.
     *
     * @return delegation code
     */
    public int getCodiceDelega(){
        return this.codiceDelega;
    }

}
