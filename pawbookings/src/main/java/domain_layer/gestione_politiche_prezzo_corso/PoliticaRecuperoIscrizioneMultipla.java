package domain_layer.gestione_politiche_prezzo_corso;

import domain_layer.Corso;

/**
 * Composite policy applied when a make-up lesson is combined with a multiple
 * enrollment. Rules R1 and R3 may overlap because the course is paid at the
 * check-in of the first lesson, so a missed first lesson can require both the
 * course fee and the recovery fee.
 */
public class PoliticaRecuperoIscrizioneMultipla extends PoliticaComposta {

    /**
     * Sums the prices produced by the configured policies.
     *
     * @param cs course being priced
     * @param numCaniIscritti number of dogs enrolled by the same customer
     * @return aggregate amount due
     */
    @Override
    public float getPrezzo(Corso cs, int numCaniIscritti) {
            float importoDovuto = 0;

        for (IpoliticaPrezzoCorso p: politicaPrezzoCorso) {
            importoDovuto += p.getPrezzo(cs, numCaniIscritti);
        }

        return importoDovuto;
    }

}
