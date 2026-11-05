package domain_layer.gestione_politiche_prezzo_corso;

import java.util.ArrayList;
import java.util.List;

import domain_layer.Corso;

/**
 * Composite price policy that aggregates multiple course price policies.
 */
public abstract class PoliticaComposta implements IpoliticaPrezzoCorso {
    protected List<IpoliticaPrezzoCorso> politicaPrezzoCorso = new ArrayList<>();;

    /**
     * Adds a price policy to the composite.
     *
     * @param politica policy to add
     */
    public void addPoliticaPrezzo(IpoliticaPrezzoCorso politica) {
        this.politicaPrezzoCorso.add(politica);
    }

    /**
     * Calculates the aggregate amount due.
     *
     * @param cs course being priced
     * @param numCaniIscritti number of dogs enrolled by the same customer
     * @return aggregated amount due
     */
    @Override
    public abstract float getPrezzo(Corso cs, int numCaniIscritti);

}
