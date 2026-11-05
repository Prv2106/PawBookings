package domain_layer.gestione_politiche_sconto_affido;

import domain_layer.PeriodoAffido;

/**
 * Policy applying a 20% discount for an early pickup (rule R2).
 */
public class PoliticaRitiroAnticipato implements IpoliticaScontoAffido{

    /**
     * Calculates the price with the early-pickup discount.
     *
     * @param pa foster period being priced
     * @param numCaniAffido number of dogs in foster care; unused by this policy
     * @return discounted amount due
     */
    public float getPrezzo(PeriodoAffido pa, int numCaniAffido){
        float sconto = pa.getCosto() * 0.2f;
        return (pa.getCosto()-sconto);
    }

}
