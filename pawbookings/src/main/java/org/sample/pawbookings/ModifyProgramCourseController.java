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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // andiamo a reperire il corso selezionato
        this.corsoSelezionato.setText(PawBookings.getInstance().getCorsoSelezionato().getTipoCorso());
    }

    @FXML
    void onAggiungiLezionePressed(ActionEvent event) throws IOException {
        // recuperiamo le informazione inserite dall'amministratore
        String nome = this.nome.getText();

        // validiamole
        if (!nome.isEmpty()) {
            try {
                PawBookings.getInstance().nuovaLezione(nome);
                MainApplication.setRoot("new_exercise-view.fxml");
            } catch (Exception e) {
                MainApplication.goAdminErrorPage(e.getMessage());
            }
        } else {
            // i campi sono vuoti...
            MainApplication.goAdminErrorPage("devi prima compilare il campo di testo");
        }
    }

    

    

}
