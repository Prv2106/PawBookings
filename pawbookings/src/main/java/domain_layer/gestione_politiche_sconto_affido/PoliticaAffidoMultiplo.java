package domain_layer.gestione_politiche_sconto_affido;

import domain_layer.PeriodoAffido;

public class PoliticaAffidoMultiplo implements IpoliticaScontoAffido{

    // Regola di dominio R4
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
