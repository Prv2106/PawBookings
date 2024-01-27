package org.sample.pawbookings;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AdminHomeController {

    @FXML
    void onConcludiAffidoPressed(ActionEvent event) {
        // passiamo alla schermata dove inserire le info date dal cliente in struttura
        try {
            MainApplication.setRoot("concludi_affido-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onGestisciCorsoPressed(ActionEvent event) {
        // passiamo alla schermata dove inserire l'amministratore può gestire uno specifico corso
        try {
            MainApplication.setRoot("manage_course-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onInserisciTurnoLezionePressed(ActionEvent event) {
        // passiamo alla pagina contenenti l'elenco dei corsi con almeno un cane iscritto
        try {
            MainApplication.setRoot("courses_with_dogs-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onNotificaStatoSalutePressed(ActionEvent event) {
        // passiamo alla pagina contenenti l'elenco dei periodi di affido con almeno un cane, appunto, in affido
        try {
            MainApplication.setRoot("periods_with_dogs-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void onLogoutPressed(ActionEvent event) {
        try {
            MainApplication.setRoot("fork-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
