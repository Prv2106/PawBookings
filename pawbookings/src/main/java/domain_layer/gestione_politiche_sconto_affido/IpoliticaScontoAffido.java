package domain_layer.gestione_politiche_sconto_affido;

import domain_layer.PeriodoAffido;

public interface IpoliticaScontoAffido {
    public float getPrezzo(PeriodoAffido pa, int numCaniAffido);    
}
