package domain_layer.gestione_politiche_prezzo_corso;

import domain_layer.Corso;

public class PoliticaScontoIscrizioneMultipla implements IpoliticaPrezzoCorso {

    @Override
    public float getPrezzo(Corso cs, int numCaniIscritti) {
    /* 
       Se il Cliente iscrive ad un Corso più di un Cane riceverà uno sconto del 30% 
       sull’importo dovuto relativamente all’iscrizione degli altri Cani dopo il primo 
    */

        if (numCaniIscritti > 1) 
            return cs.getCosto() * 0.7f;
        else 
            return cs.getCosto();
    }
    
}
