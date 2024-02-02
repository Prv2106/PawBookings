package domain_layer.gestione_politiche_sconto_affido;

import java.util.ArrayList;
import java.util.List;

import domain_layer.PeriodoAffido;

public abstract class PoliticaScontoAffidoComposta implements IpoliticaScontoAffido{
    
    protected List<IpoliticaScontoAffido> politicaScontoAffido = new ArrayList<>();

    public void addPoliticaSconto(IpoliticaScontoAffido politica){
        this.politicaScontoAffido.add(politica);
    }

    public abstract float getPrezzo(PeriodoAffido pa, int numCaniAffido);

}
