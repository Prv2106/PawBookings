package domain_layer.gestione_politiche_prezzo_corso;

import domain_layer.Corso;

public class PoliticaRecuperoIscrizioneMultipla extends PoliticaComposta {

    @Override
    public float getPrezzo(Corso cs, int numCaniIscritti) {
    /*
        Le regole R1 ed R3 si possono sovrapporre in quanto, siccome il pagamento di un corso
        si effettua al timbro della prenotazione del turno relativo alla prima lezione di un corso,
        è possibile che già dalla prima lezione il cliente non presenta il cane e dovrà quindi, oltre
        che saldare il conto del corso, anche sostenere la "mora" per recuperare la lezione.
     */
    
        float importoDovuto = 0;

        for (IpoliticaPrezzoCorso p: politicaPrezzoCorso) {
            importoDovuto += p.getPrezzo(cs, numCaniIscritti);
        }

        return importoDovuto;
    }
    
}
