package org.sample.pawbookings;

import java.io.IOException;

import domain_layer.PawBookings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Controller for the administrator dashboard. Provides navigation to course, shift,
 * foster-care, and health-status management.
 */
public class AdminHomeController {

    @FXML
    void onConcludiAffidoPressed(ActionEvent event) {

        try {
            MainApplication.setRoot("admin_choose_concludi_affido-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onGestisciCorsoPressed(ActionEvent event) {

        try {
            MainApplication.setRoot("manage_course-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onInserisciTurnoLezionePressed(ActionEvent event) {

        try {
            MainApplication.setRoot("courses_with_dogs-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onTimbraPrenotazioneTurnoPressed(ActionEvent event) throws IOException {
        try {
            MainApplication.setRoot("timbra_turno-view.fxml");
        } catch (Exception e) {
            MainApplication.goAdminErrorPage("Qualcosa è andato storto");
        }
    }

    @FXML
    void onNotificaStatoSalutePressed(ActionEvent event) {

        try {
            MainApplication.setRoot("periods_with_dogs-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onLogoutPressed(ActionEvent event) {
        try {
            PawBookings.getInstance().logoutAdmin();
            MainApplication.setRoot("fork-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
