package domain_layer.gestione_politiche_sconto_affido;

import domain_layer.PeriodoAffido;

/**
 * Strategy contract for calculating the price of a foster period.
 */
public interface IpoliticaScontoAffido {
    /**
     * Calculates the amount due for a foster period.
     *
     * @param pa foster period being priced
     * @param numCaniAffido number of dogs in foster care for the customer
     * @return amount due
     */
    public float getPrezzo(PeriodoAffido pa, int numCaniAffido);
}
