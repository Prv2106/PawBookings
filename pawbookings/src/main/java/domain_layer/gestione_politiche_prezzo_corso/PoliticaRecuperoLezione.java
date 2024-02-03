package domain_layer.gestione_politiche_prezzo_corso;

import domain_layer.Corso;

public class PoliticaRecuperoLezione implements IpoliticaPrezzoCorso {

    @Override
    public float getPrezzo(Corso cs, int numCaniIscritti) {
    /* 
        Se il Cliente non presenta il proprio cane al turno prenotato (senza che ne abbia richiesto la disdetta) 
        allora per sostenere la stessa lezione in un’altra occasione sarà necessario 
        pagare il 50% del costo totale del corso diviso il numero di lezioni previste da questo
        */

       float costoCorso = cs.getCosto();
       int numLez = cs.getProgramma().size();

       return (float) 0.5*(costoCorso/numLez);
    }
    
}
