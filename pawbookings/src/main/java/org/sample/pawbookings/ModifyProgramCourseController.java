package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import domain_layer.PawBookings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Controller for adding a lesson to the selected course.
 */
public class ModifyProgramCourseController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private Text corsoSelezionato;

    @FXML
    private TextField nome;

    @FXML
    void onBackPressed(ActionEvent event) {
        try {
            MainApplication.simpleBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the name of the course being edited.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.corsoSelezionato.setText(PawBookings.getInstance().getCorsoSelezionato().getTipoCorso());
    }

    @FXML
    void onAggiungiLezionePressed(ActionEvent event) throws IOException {

        String nome = this.nome.getText();

        if (!nome.isEmpty()) {
            try {
                PawBookings.getInstance().nuovaLezione(nome);
                MainApplication.setRoot("new_exercise-view.fxml");
            } catch (Exception e) {
                MainApplication.goAdminErrorPage(e.getMessage());
            }
        } else {

            MainApplication.goAdminErrorPage("devi prima compilare il campo di testo");
        }
    }

}
