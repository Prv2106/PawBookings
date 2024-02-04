package domain_layer.gestione_politiche_sconto_affido;

import domain_layer.PeriodoAffido;

public class PoliticaRitiroAnticipato implements IpoliticaScontoAffido{

    // Regola di Dominio R2
    public float getPrezzo(PeriodoAffido pa, int numCaniAffido){
        float sconto = pa.getCosto() * 0.2f;
        return (pa.getCosto()-sconto);
    }
    
}
