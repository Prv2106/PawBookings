package domain_layer;

public class CorsoAvanzato extends Corso{
    public CorsoAvanzato(int codice, int capienza, float costo, String tipoCorso) {
        super(codice, capienza, costo, tipoCorso);
    }

    @Override
    public void loadLezioni() {
        // TODO Auto-generated method stub
        
    }
}
