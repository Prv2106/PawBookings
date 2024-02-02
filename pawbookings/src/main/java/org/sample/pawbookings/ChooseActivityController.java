package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import domain_layer.PawBookings;
import domain_layer.Turno;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


public class ChooseActivityController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private HBox boxDettagliTurno;

    @FXML
    private Text codice;

    @FXML
    private Text data;

    @FXML
    private Text oraFine;

    @FXML
    private Text oraInizio;

    @FXML
    private Button disdiciButton;


    @FXML
    void onBackPressed(ActionEvent event) throws IOException {
        try {
            MainApplication.simpleBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Turno t = PawBookings.getInstance().getCaneSelezionato().getTurnoCorrente();
        if (t != null) {
            boxDettagliTurno.setVisible(true);
            boxDettagliTurno.setManaged(true);
            data.setText(t.getData().getDayOfMonth() + "/" + t.getData().getMonthValue() + "/" + t.getData().getYear());
            oraInizio.setText(t.getOraInizio().toString());
            oraFine.setText(t.getOraFine().toString());
            codice.setText("" + t.getCodice());
        } else {
            boxDettagliTurno.setVisible(false);
            boxDettagliTurno.setManaged(false);
        }
    }

    // PULSANTI SCHERMATA

    @FXML
    void onDisdiciPressed(ActionEvent event) {
        /* l'operazione di sistema restituisce un booleano, se è true restituisco l'ok-view, 
        se è false metto la schermata di errore con il messaggio che il turno è scaduto */

    }

    @FXML
    void onNuovaIscrizioneCorsoClicked(ActionEvent event) throws IOException {
        // passiamo alla schermata che mostra l'elenco dei corsi disponibili
        try {
            if (PawBookings.getInstance().nuovaIscrizioneCorso() != null) {
                MainApplication.setRoot("available_courses-view.fxml");
            } else {
                MainApplication.goClientErrorPage("probabilmente il tuo cane risulta già essere iscritto ad un corso, oppure ha già completato tutti i corsi disponibili");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            MainApplication.goClientErrorPage("errore generico");
        }
    }

    
    @FXML
    void onMostraStatoAvanzamentoCorsoClicked(ActionEvent event) throws IOException {
        // passiamo alla schermata che mostra lo stato di avanzamento del cane 'ShowCourseState'
        try {
            if (PawBookings.getInstance().mostraStatoAvanzamentoCorso() == null) {
                MainApplication.goClientErrorPage("Il cane non è iscirtto ad alcun corso!");
            } else {
                MainApplication.setRoot("show_course_state-view.fxml");
            }
        } catch (IOException e) {
            MainApplication.goClientErrorPage(e.getMessage());
        }
    }

    @FXML
    void onPrenotaTurnoLezioneClicked(ActionEvent event) throws IOException {
        // passiamo alla schermata che mostra l'elenco dei turni disponibili
        try {
            if (PawBookings.getInstance().prenotaTurnoLezione() == null) {
                MainApplication.goClientErrorPage("il cane non è iscritto ad un corso oppure hai già prenotato un turno");
            } else {
                MainApplication.setRoot("available_shifts-view.fxml"); 
            }
        } catch (IOException e) {
            // probabilmente il cane non è iscritto al turno.... quindi:
            MainApplication.goClientErrorPage(e.getMessage());
        }
    }

    @FXML
    void onScambiaTurnoClicked(ActionEvent event) throws IOException {
        try {
            PawBookings PB = PawBookings.getInstance();
            LinkedList<Turno> elencoTurni = PB.scambioTurno();
            if (elencoTurni == null) {
                // nessun turno prenotato
                MainApplication.goClientErrorPage("Non hai ancora prenotato nessun turno, oppure non è troppo tardi per scambiare il tuo turno...");
            } else if (elencoTurni.isEmpty()) {
                // nessun turno disponibile
                MainApplication.goClientErrorPage("Nessun turno disponibile!");
            } else {
                // andiamo nella pagina coi turni disponibili
                MainApplication.setRoot("available_shifts_to_switch-view.fxml");
            }
        } catch (IOException e) {
            MainApplication.goClientErrorPage(e.getMessage());
        }
    }

    @FXML
    void onRecuperaLezioneClicked(ActionEvent event) {
                                                                                                /* 
     -> al click, il sistema fa il check con l'operazione di sistema.
     - se restituisce null, "il tuo turno corrente è ancora attivo, quindi puoi ancora disdirlo"
     - se diverso da null, viene restituito l'elenco dei turni disponibili
     -> al click su un turno, si chiama un'altra operazione simile al "selezionaTurno(ts)" 
                                                                                                */
    


    }

    
}