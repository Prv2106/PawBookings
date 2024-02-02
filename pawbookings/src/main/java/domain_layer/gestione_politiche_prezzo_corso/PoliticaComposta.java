package domain_layer.gestione_politiche_prezzo_corso;

import java.util.List;

import domain_layer.Corso;

public abstract class PoliticaComposta implements IpoliticaPrezzoCorso {
    protected List<IpoliticaPrezzoCorso> politicaPrezzoCorso;

    public void addPoliticaPrezzo(IpoliticaPrezzoCorso politica) {
        this.politicaPrezzoCorso.add(politica);
    }

    @Override
    public abstract float getPrezzo(Corso cs, int numCaniIscritti);
    


}
