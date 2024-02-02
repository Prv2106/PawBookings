package domain_layer.gestione_politiche_prezzo_corso;

import domain_layer.Corso;

public interface IpoliticaPrezzoCorso {
    public float getPrezzo(Corso cs, int numCaniIscritti);
}
