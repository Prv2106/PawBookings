package org.sample.pawbookings;

import java.io.IOException;
import java.util.LinkedList;

import domain_layer.Cane;
import domain_layer.PawBookings;
import domain_layer.PeriodoAffido;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class DettagliAffidoController {

    @FXML
    private Text codice;

    @FXML
    private Text dataFine;

    @FXML
    private Text dataInizio;

    @FXML
    private Button backButton;
    @FXML
    void onBackPressed(ActionEvent event) throws IOException {
        try {
            Stage finestraCorrente = (Stage) backButton.getScene().getWindow();
            finestraCorrente.close();
            MainApplication.goBackRoot(true);;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(PeriodoAffido pa) {
        codice.setText(Integer.toString(pa.getCodice()));
        dataInizio.setText(pa.getDataInizio().getDayOfMonth() + "/" + pa.getDataInizio().getMonthValue() + "/" + pa.getDataInizio().getYear());
        dataFine.setText(pa.getDataFine().getDayOfMonth() + "/" + pa.getDataFine().getMonthValue() + "/" + pa.getDataFine().getYear());
    }

    @FXML
    void onConcludiAffidoPressed(ActionEvent event) {
        // passiamo alla schermata che mostra l'elenco dei corsi disponibili
        try {
            PawBookings.getInstance().confermaConclusioneAffido();
            MainApplication.setRoot("admin_ok-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
