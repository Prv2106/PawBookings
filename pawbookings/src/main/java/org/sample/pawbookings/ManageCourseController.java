package org.sample.pawbookings;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class ManageCourseController {

    @FXML
    private Button backButton;

    private void showAllCourses(String nextDestination) {
        // andiamo nella pagina contenente l'elenco di tutti i corsi esistenti
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_all_courses-view.fxml"));
            Parent root = loader.load();
            AdminAllCoursesController controller = loader.getController();
            controller.initialize(nextDestination);
            MainApplication.goTo(root);
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
            MainApplication.setRoot("new_course-view.fxml");;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onModificaInformazioniCorsoPressed(ActionEvent event) {
        showAllCourses("modify_info_course-view.fxml");
    }

    @FXML
    void onModificaProgrammaCorsoPressed(ActionEvent event) {
        showAllCourses("modify_program_course-view.fxml");
    }

}

