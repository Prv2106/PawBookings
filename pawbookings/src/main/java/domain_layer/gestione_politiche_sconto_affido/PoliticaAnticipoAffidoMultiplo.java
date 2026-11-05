package domain_layer.gestione_politiche_sconto_affido;

import domain_layer.PeriodoAffido;

/**
 * Composite policy for an early pickup with multiple dogs in foster care.
 * Combines the early-pickup discount with the multiple-dog discount.
 */
public class PoliticaAnticipoAffidoMultiplo extends PoliticaScontoAffidoComposta{

    /**
     * Sums the configured discounts and removes the duplicated base price.
     *
     * @param pa foster period being priced
     * @param numCaniAffido number of dogs in foster care for the customer
     * @return discounted amount due
     */
    public float getPrezzo(PeriodoAffido pa, int numCaniAffido){
        float importoDovuto=0;
        for(IpoliticaScontoAffido p: politicaScontoAffido){
            importoDovuto += p.getPrezzo(pa, numCaniAffido);
        }

        return (importoDovuto - pa.getCosto());
    }

}
