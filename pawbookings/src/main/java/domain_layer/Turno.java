package domain_layer;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a bookable time slot for a lesson.
 */
public class Turno {

    private LocalDate data;
    private LocalTime oraInizio;
    private LocalTime oraFine;
    private int codiceTurno;

    /**
     * Creates a shift.
     *
     * @param codiceTurno unique shift code
     * @param data shift date
     * @param oraInizio start time
     * @param oraFine end time
     */
    public Turno(int codiceTurno,LocalDate data, LocalTime oraInizio, LocalTime oraFine){
        this.codiceTurno = codiceTurno;
        this.data=data;
        this.oraInizio=oraInizio;
        this.oraFine=oraFine;
    }

    /**
     * Returns the shift date.
     *
     * @return shift date
     */
    public LocalDate getData(){
        return this.data;
    }

    /**
     * Returns the start time.
     *
     * @return start time
     */
    public LocalTime getOraInizio(){
        return this.oraInizio;
    }

    /**
     * Returns the end time.
     *
     * @return end time
     */
    public LocalTime getOraFine(){
        return this.oraFine;
    }

    /**
     * Returns the shift code.
     *
     * @return shift code
     */
    public int getCodice() {
        return this.codiceTurno;
    }

}
