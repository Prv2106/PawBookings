package domain_layer.gestione_politiche_prezzo_corso;

import domain_layer.Corso;

/**
 * Policy for the fee charged when a lesson must be made up.
 * The fee is 50% of the course price divided by the number of lessons.
 */
public class PoliticaRecuperoLezione implements IpoliticaPrezzoCorso {

    /**
     * Calculates the make-up lesson fee.
     *
     * @param cs course being priced
     * @param numCaniIscritti number of dogs enrolled by the same customer; unused
     *        by this policy
     * @return make-up lesson fee
     */
    @Override
    public float getPrezzo(Corso cs, int numCaniIscritti) {
               float costoCorso = cs.getCosto();
       int numLez = cs.getProgramma().size();

       return (float) 0.5*(costoCorso/numLez);
    }

}
