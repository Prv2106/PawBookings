package domain_layer.gestione_politiche_sconto_affido;

import java.util.ArrayList;
import java.util.List;

import domain_layer.PeriodoAffido;

/**
 * Composite foster-care policy that aggregates multiple discount policies.
 */
public abstract class PoliticaScontoAffidoComposta implements IpoliticaScontoAffido{

    protected List<IpoliticaScontoAffido> politicaScontoAffido = new ArrayList<>();

    /**
     * Adds a discount policy to the composite.
     *
     * @param politica policy to add
     */
    public void addPoliticaSconto(IpoliticaScontoAffido politica){
        this.politicaScontoAffido.add(politica);
    }

    /**
     * Calculates the aggregate amount due.
     *
     * @param pa foster period being priced
     * @param numCaniAffido number of dogs in foster care for the customer
     * @return aggregated amount due
     */
    public abstract float getPrezzo(PeriodoAffido pa, int numCaniAffido);

}
