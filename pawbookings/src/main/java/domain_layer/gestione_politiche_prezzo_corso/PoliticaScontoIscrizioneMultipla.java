package domain_layer.gestione_politiche_prezzo_corso;

import domain_layer.Corso;

/**
 * Policy applying a 30% discount to every additional dog enrolled in the same
 * course by the same customer.
 */
public class PoliticaScontoIscrizioneMultipla implements IpoliticaPrezzoCorso {

    /**
     * Calculates the course price with the multiple-enrollment discount.
     *
     * @param cs course being priced
     * @param numCaniIscritti number of dogs enrolled by the same customer
     * @return discounted price for additional dogs, or the full price for one dog
     */
    @Override
    public float getPrezzo(Corso cs, int numCaniIscritti) {
            if (numCaniIscritti > 1)
            return cs.getCosto() * 0.7f;
        else
            return cs.getCosto();
    }

}
