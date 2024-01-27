package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import domain_layer.PawBookings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class NewExerciseController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private Text lezioneCorrente;

    @FXML
    private TextArea descrizione;

    @FXML
    private TextField nome;


    @FXML
    void onBackPressed(ActionEvent event) {
        try {
            MainApplication.simpleBack();;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.lezioneCorrente.setText(PawBookings.getInstance().getCorsoSelezionato().getLezioneCorrente());
    }

    @FXML
    void onAggiungiEsercizioPressed(ActionEvent event) {
        try {
            PawBookings.getInstance().inserisciEsercizio(nome, descrizione);
            MainApplication.setRoot("exercise_added-view");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

