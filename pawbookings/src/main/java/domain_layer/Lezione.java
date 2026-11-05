package domain_layer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

/**
 * Represents a lesson in a course programme.
 * Maintains the exercise list and the shifts that can be booked for the
 * lesson.
 */
public class Lezione {

    private int codiceLezione;
    private String nome;
    private LinkedList<Turno> elencoTurniDisponibili;
    private LinkedList<Esercizio> esercizi;

    /**
     * Creates a lesson.
     *
     * @param codiceLezione unique lesson code
     * @param nome lesson name
     */
    public Lezione(int codiceLezione, String nome) {

        this.codiceLezione = codiceLezione;
        this.nome = nome;
        this.elencoTurniDisponibili = new LinkedList<>();
        this.esercizi = new LinkedList<>();
    }

    /**
     * Removes a shift from the list of available shifts.
     *
     * @param ts shift to remove
     */
    public void aggiornaTurniDisponibili(Turno ts) {

        this.elencoTurniDisponibili.remove(ts);
    }

    /**
     * Returns the shifts available for this lesson.
     *
     * @return available shifts
     */
    public LinkedList<Turno> getTurniDisponibili() {

        return this.elencoTurniDisponibili;
    }

    /**
     * Returns the lesson name.
     *
     * @return lesson name
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Adds an exercise to the lesson.
     *
     * @param nome exercise name
     * @param descrizione exercise description
     */
    public void nuovoEsercizio(String nome, String descrizione) {
        Esercizio nuovEsercizio = new Esercizio(nome, descrizione);
        this.esercizi.add(nuovEsercizio);
    }

    /**
     * Adds a bookable shift to the lesson.
     *
     * @param codiceTurno shift code
     * @param data shift date
     * @param oraInizio start time
     * @param oraFine end time
     */
    public void nuovoTurno(int codiceTurno,LocalDate data, LocalTime oraInizio, LocalTime oraFine) {
        Turno nuovoTurno = new Turno(codiceTurno, data, oraInizio, oraFine);
        this.elencoTurniDisponibili.add(nuovoTurno);
    }

    /**
     * Exchanges the current shift with a newly selected shift.
     *
     * @param tc shift being released
     * @param ts shift being booked
     */
    public void effettuaScambioTurno(Turno tc, Turno ts){
        this.elencoTurniDisponibili.add(tc);
        this.elencoTurniDisponibili.remove(ts);
    }

    /**
     * Returns the exercises in the lesson.
     *
     * @return exercises
     */
    public LinkedList<Esercizio> getEsercizi() {
        return this.esercizi;
    }

    /**
     * Returns the mutable list of available shifts.
     *
     * @return available shifts
     */
    public LinkedList<Turno> getElencoTurniDisponibili() {
        return this.elencoTurniDisponibili;
    }
}

