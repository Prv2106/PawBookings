package org.sample.pawbookings;

import java.io.IOException;

import domain_layer.PawBookings;
import domain_layer.Turno;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class DettagliTurnoController {

    @FXML
    private Button backButton;

    @FXML
    private Text codice;

    @FXML
    private Text data;

    @FXML
    private Text oraFine;

    @FXML
    private Text oraInizio;

    @FXML
    void onBackPressed(ActionEvent event) {
        try {
            MainApplication.simpleBack();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public void start(Turno t) {
        codice.setText(Integer.toString(t.getCodice()));
        data.setText(t.getData().getDayOfMonth() + "/" + t.getData().getMonthValue() + "/" + t.getData().getYear());
        oraInizio.setText(t.getOraInizio().toString());
        oraFine.setText(t.getOraFine().toString());
    }

    @FXML
    void onConcludiAffidoPressed(ActionEvent event) throws IOException {
        try {
            PawBookings.getInstance().confermaTimbroTurno();
            MainApplication.setRoot("admin_ok-view.fxml");
        } catch (IOException e) {
            MainApplication.goAdminErrorPage("Qualcosa è andato storto..");
        }
    }

}

