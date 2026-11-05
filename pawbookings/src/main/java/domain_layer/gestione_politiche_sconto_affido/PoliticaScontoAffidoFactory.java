package domain_layer.gestione_politiche_sconto_affido;

/**
 * Singleton factory that creates foster-care price policies by name.
 */
public class PoliticaScontoAffidoFactory {
    private static PoliticaScontoAffidoFactory PAF;

    /**
     * Returns the singleton factory instance.
     *
     * @return factory instance
     */
    public static PoliticaScontoAffidoFactory getInstance(){
        if(PAF == null)
             PAF = new PoliticaScontoAffidoFactory();
        return PAF;
    }

    /**
     * Creates the foster-care price policy identified by name.
     *
     * @param numCani number of dogs in foster care for the customer
     * @param tipoPolitica policy name
     * @return requested policy, or {@code null} for an unknown name
     */
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
