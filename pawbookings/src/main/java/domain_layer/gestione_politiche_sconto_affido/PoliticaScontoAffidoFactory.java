package domain_layer.gestione_politiche_sconto_affido;

public class PoliticaScontoAffidoFactory {
    private static PoliticaScontoAffidoFactory PAF;
    
    
    public static PoliticaScontoAffidoFactory getInstance(){
        if(PAF == null)
             PAF = new PoliticaScontoAffidoFactory();
        return PAF;
    }


    public IpoliticaScontoAffido getPoliticaScontoAffido(int numCani, String tipoPolitica){

        switch(tipoPolitica){
            case "anticipoAffidoMultiplo": {
                PoliticaAnticipoAffidoMultiplo pc = new PoliticaAnticipoAffidoMultiplo();
                PoliticaRitiroAnticipato p1 = new PoliticaRitiroAnticipato();
                PoliticaAffidoMultiplo p2 = new PoliticaAffidoMultiplo();
                pc.addPoliticaSconto(p1);
                pc.addPoliticaSconto(p2);
                return pc;
            }
            case "anticipoAffido": {
                return new PoliticaRitiroAnticipato();
            }           
            case "affidoMultiplo": {
                return new PoliticaAffidoMultiplo();
            }   
            default: return null; 
        }

    }



    
}
