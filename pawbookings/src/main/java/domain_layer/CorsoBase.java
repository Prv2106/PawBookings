package domain_layer;

import java.util.LinkedList;

public class CorsoBase extends Corso{

    private LinkedList<Lezione> programma;


    public CorsoBase(int codice, int capienza, float costo, String tipoCorso) {
        super(codice, capienza, costo, tipoCorso);   
        this.programma = new LinkedList<>();
        this.loadLezioni();
    }

    @Override
    public void loadLezioni() {
        Lezione lezione1 = new Lezione(1,"Comandi di Base");
        Lezione lezione2 = new Lezione(2,"Guinzaglio e Camminare al Guinzaglio");
        Lezione lezione3 = new Lezione(3,"Dai la Zampa e Seduto");
        Lezione lezione4 = new Lezione(4,"Controllo delle Impulsività");
        

        this.programma.add(lezione1);
        this.programma.add(lezione2);
        this.programma.add(lezione3);
        this.programma.add(lezione4);
        
    }


    @Override
    public LinkedList<Lezione> getLezioni(){
        return this.programma;
    }



}
