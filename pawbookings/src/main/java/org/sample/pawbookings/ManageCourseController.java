package org.sample.pawbookings;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ManageCourseController {

    @FXML
    private Button backButton;

    private void showAllCourses() {
        // andiamo nella pagina contenente l'elenco di tutti i corsi esistenti
        try {
            MainApplication.setRoot("admin_all_courses-view");;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onBackPressed(ActionEvent event) {
        try {
            MainApplication.simpleBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onInserisciCorsoPressed(ActionEvent event) {
        // andiamo nella pagina dove l'amministratore può inserire un corso
        try {
            MainApplication.simpleBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onModificaInformazioniCorsoPressed(ActionEvent event) {
        showAllCourses();
    }

    @FXML
    void onModificaProgrammaCorsoPressed(ActionEvent event) {
        showAllCourses();
    }

}

