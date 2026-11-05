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

/**
 * Controller for the dog activity dashboard. Exposes enrollment, attendance, booking,
 * exchange, cancellation, and recovery actions.
 */
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
    private Button recover;

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
            recover.setVisible(true);
            recover.setManaged(true);
            data.setText(t.getData().getDayOfMonth() + "/" + t.getData().getMonthValue() + "/" + t.getData().getYear());
            oraInizio.setText(t.getOraInizio().toString());
            oraFine.setText(t.getOraFine().toString());
            codice.setText("" + t.getCodice());
        } else {
            boxDettagliTurno.setVisible(false);
            boxDettagliTurno.setManaged(false);
            recover.setVisible(false);
            recover.setManaged(false);
        }
    }

    @FXML
    void onDisdiciPressed(ActionEvent event) throws IOException {
                if (PawBookings.getInstance().disdiciTurno()) {
            MainApplication.setRoot("ok-view.fxml");
        } else {
            MainApplication.goClientErrorPage("Non è più possibile disdire il turno in quanto è scaduto");
        }
    }

    @FXML
    void onNuovaIscrizioneCorsoClicked(ActionEvent event) throws IOException {

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

        try {
            if (PawBookings.getInstance().prenotaTurnoLezione() == null) {
                MainApplication.goClientErrorPage("il cane non è iscritto ad un corso oppure hai già prenotato un turno");
            } else {
                MainApplication.setRoot("available_shifts-view.fxml");
            }
        } catch (IOException e) {
            // The operation fails when the dog has no active enrollment or booking.
            MainApplication.goClientErrorPage(e.getMessage());
        }
    }

    @FXML
    void onScambiaTurnoClicked(ActionEvent event) throws IOException {
        try {
            PawBookings PB = PawBookings.getInstance();
            LinkedList<Turno> elencoTurni = PB.scambioTurno();
            if (elencoTurni == null) {
                // No shift is currently booked.
                MainApplication.goClientErrorPage("Non hai ancora prenotato nessun turno, oppure non è troppo tardi per scambiare il tuo turno...");
            } else if (elencoTurni.isEmpty()) {
                // No alternative shift is available.
                MainApplication.goClientErrorPage("Nessun turno disponibile!");
            } else {

                MainApplication.setRoot("available_shifts_to_switch-view.fxml");
            }
        } catch (IOException e) {
            MainApplication.goClientErrorPage(e.getMessage());
        }
    }

    @FXML
    void onRecuperaLezioneClicked(ActionEvent event) throws IOException {
            LinkedList<Turno> turni = PawBookings.getInstance().recuperaLezione();
        if (turni != null) {
            MainApplication.setRoot("available_shifts_recover_lesson-view.fxml");
        } else {
            MainApplication.goClientErrorPage("Sei ancora in tempo per disdire il turno!");
        }

    }

}
