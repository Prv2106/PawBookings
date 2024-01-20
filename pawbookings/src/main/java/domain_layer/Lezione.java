package domain_layer;

import java.util.ArrayList;
import java.util.List;

public class Lezione {
    private int codiceLezione;
    private String nome;
    private List<Turno> elencoTurniDisponibili;

    public Lezione() {
        this.codiceLezione = codiceLezione;
        this.nome = nome;
        this.elencoTurniDisponibili = new ArrayList<>();
    }

    public void aggiornaelencoTurniDisponibili(Turno ts) {
        this.elencoTurniDisponibili.remove(ts);
    }

    public List<Turno> getelencoTurniDisponibili() {
        return this.elencoTurniDisponibili;
    }

}
