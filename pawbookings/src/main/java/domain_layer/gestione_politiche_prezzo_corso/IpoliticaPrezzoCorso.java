package domain_layer.gestione_politiche_prezzo_corso;

import domain_layer.Corso;

/**
 * Strategy contract for calculating the price of a course.
 */
public interface IpoliticaPrezzoCorso {
    /**
     * Calculates the amount due for a course.
     *
     * @param cs course being priced
     * @param numCaniIscritti number of dogs enrolled by the same customer
     * @return amount due
     */
    public float getPrezzo(Corso cs, int numCaniIscritti);
}
