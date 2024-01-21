package domain_layer;

public class CorsoAgility extends Corso{

    
    public CorsoAgility(int codice, int capienza, float costo, String tipoCorso) {
        super(codice, capienza, costo, tipoCorso);
        this.loadLezioni();
    }

    @Override
    public void loadLezioni() {
        Lezione l1 = new Lezione(1, "Introduzione all'Agility");
        Lezione l2 = new Lezione(2, "Comandi di Base‌");
        Lezione l3 = new Lezione(3, "Abilità di Controllo e Fiducia");
        Lezione l4 = new Lezione(4, "Sequenze di Ostacoli");
        Lezione l5 = new Lezione(5, "Gare Simulate");

        super.programma.add(l1);
        super.programma.add(l2);
        super.programma.add(l3);
        super.programma.add(l4);
        super.programma.add(l5);
    }


}
