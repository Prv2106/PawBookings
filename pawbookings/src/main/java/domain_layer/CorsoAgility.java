package domain_layer;

import java.util.LinkedList;
public class CorsoAgility extends Corso{
    private LinkedList<Lezione> programma;
    public CorsoAgility(int codice, int capienza, float costo, String tipoCorso) {
        super(codice, capienza, costo, tipoCorso);
        this.programma = new LinkedList<>();
        this.loadLezioni();
    }

    @Override
    public void loadLezioni() {
        Lezione l1 = new Lezione(1, "Introduzione all'Agility");
        Lezione l2 = new Lezione(2, "Comandi di Base‌");
        Lezione l3 = new Lezione(3, "Abilità di Controllo e Fiducia");
        Lezione l4 = new Lezione(4, "Sequenze di Ostacoli");
        Lezione l5 = new Lezione(5, "Gare Simulate");

        this.programma.add(l1);
        this.programma.add(l2);
        this.programma.add(l3);
        this.programma.add(l4);
        this.programma.add(l5);
    }

    @Override
    public LinkedList<Lezione> getLezioni() {
        return this.programma;
    }
}
