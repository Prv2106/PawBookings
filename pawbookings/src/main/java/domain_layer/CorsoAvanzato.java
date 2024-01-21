package domain_layer;


public class CorsoAvanzato extends Corso{
    
    public CorsoAvanzato(int codice, int capienza, float costo, String tipoCorso) {
        super(codice, capienza, costo, tipoCorso);
        this.loadLezioni();
    }

    @Override
    public void loadLezioni() {
        Lezione l1 = new Lezione(1, "Messa al piede del cane‌");
        Lezione l2 = new Lezione(2, "Condotta senza guinzaglio‌");
        Lezione l3 = new Lezione(3, "Svolte, dietro front e variazioni delle andature");
        Lezione l4 = new Lezione(4, "Posizioni di seduto e terra in condotta”");
        Lezione l5 = new Lezione(5, "Riporto in piano di un oggetto");

        super.programma.add(l1);
        super.programma.add(l2);
        super.programma.add(l3);
        super.programma.add(l4);
        super.programma.add(l5);
    }





}
