package domain_layer.gestione_politiche_prezzo_corso;


public class PoliticaPrezzoCorsoFactory {
    private static PoliticaPrezzoCorsoFactory PCF;

    private PoliticaPrezzoCorsoFactory() {}

    public static PoliticaPrezzoCorsoFactory getInstance() {
        if (PCF == null) 
            PCF = new PoliticaPrezzoCorsoFactory();
        return PCF;
    }

    public IpoliticaPrezzoCorso getPoliticaPrezzo(int numCaniIscritti, String tipoPolitica) {
        switch (tipoPolitica) {
            case "recuperoIscrizioneMultipla":
                // composizione
                PoliticaRecuperoIscrizioneMultipla pc = new PoliticaRecuperoIscrizioneMultipla();

                PoliticaScontoIscrizioneMultipla p1 = new PoliticaScontoIscrizioneMultipla();
                PoliticaRecuperoLezione p2 = new PoliticaRecuperoLezione();

                pc.addPoliticaPrezzo(p1);
                pc.addPoliticaPrezzo(p2);
                return pc;

            case "recuperoLezione":
                return new PoliticaRecuperoLezione();
                
            case "iscrizioneMultipla":
                return new PoliticaScontoIscrizioneMultipla();

            default: return null;
        }
    }
}