package domain_layer.gestione_politiche_prezzo_corso;

/**
 * Singleton factory that creates course price policies by name.
 */
public class PoliticaPrezzoCorsoFactory {
    private static PoliticaPrezzoCorsoFactory PCF;

    private PoliticaPrezzoCorsoFactory() {}

    /**
     * Returns the singleton factory instance.
     *
     * @return factory instance
     */
    public static PoliticaPrezzoCorsoFactory getInstance() {
        if (PCF == null)
            PCF = new PoliticaPrezzoCorsoFactory();
        return PCF;
    }

    /**
     * Creates the course price policy identified by name.
     *
     * @param numCaniIscritti number of dogs enrolled by the same customer
     * @param tipoPolitica policy name
     * @return requested policy, or {@code null} for an unknown name
     */
    public IpoliticaPrezzoCorso getPoliticaPrezzo(int numCaniIscritti, String tipoPolitica) {
        switch (tipoPolitica) {
            case "recuperoIscrizioneMultipla":

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
