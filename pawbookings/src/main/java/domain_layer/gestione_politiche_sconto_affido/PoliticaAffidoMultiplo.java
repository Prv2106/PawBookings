package domain_layer.gestione_politiche_sconto_affido;

import domain_layer.PeriodoAffido;

/**
 * Policy applying a 20% discount when more than one dog from the same customer
 * is in foster care for the same period (rule R4).
 */
public class PoliticaAffidoMultiplo implements IpoliticaScontoAffido{

    /**
     * Calculates the price for a multiple foster registration.
     *
     * @param pa foster period being priced
     * @param numCaniAffido number of dogs in foster care for the customer
     * @return discounted price when more than one dog is registered
     */
    public float getPrezzo(PeriodoAffido pa, int numCaniAffido){
        if(numCaniAffido > 1){
            float sconto = pa.getCosto() * 0.2f;
            return (pa.getCosto()-sconto);
        }
        else{
            return pa.getCosto();
        }

    }
}
