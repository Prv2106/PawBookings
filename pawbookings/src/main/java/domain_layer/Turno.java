package domain_layer;


import java.time.LocalDate;
import java.time.LocalTime;

public class Turno {

    private LocalDate data;
    private LocalTime oraInizio;
    private LocalTime oraFine;
    private int codiceTurno;
    


    public Turno(int codiceTurno,LocalDate data, LocalTime oraInizio, LocalTime oraFine){
        this.codiceTurno = codiceTurno;
        this.data=data;
        this.oraInizio=oraInizio;
        this.oraFine=oraFine;
    }


    public LocalDate getData(){
        return this.data;
    }

    public LocalTime getOraInizio(){
        return this.oraInizio;
    }

    public LocalTime getOraFine(){
        return this.oraFine;
    }

}
