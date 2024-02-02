package domain_layer.gestione_politiche_sconto_affido;


import domain_layer.PeriodoAffido;

public class PoliticaAnticipoAffidoMultiplo extends PoliticaScontoAffidoComposta{
    
    public float getPrezzo(PeriodoAffido pa, int numCaniAffido){
        float importoDovuto=0;
        for(IpoliticaScontoAffido p: politicaScontoAffido){
            importoDovuto += p.getPrezzo(pa, numCaniAffido);
        }

        return importoDovuto;
    }

}
