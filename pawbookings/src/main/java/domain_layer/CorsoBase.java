package domain_layer;

public class CorsoBase extends Corso{

    public CorsoBase(int codice, int capienza, float costo, String tipoCorso) {
        super(codice, capienza, costo, tipoCorso);   
        this.loadLezioni();
    }

    @Override
    public void loadLezioni() {
        Lezione lezione1 = new Lezione(1,"Comandi di Base");
        Lezione lezione2 = new Lezione(2,"Guinzaglio e Camminare al Guinzaglio");
        Lezione lezione3 = new Lezione(3,"Dai la Zampa e Seduto");
        Lezione lezione4 = new Lezione(4,"Controllo delle Impulsività");
        

        super.programma.add(lezione1);
        super.programma.add(lezione2);
        super.programma.add(lezione3);
        super.programma.add(lezione4);
        
    }


 


}
