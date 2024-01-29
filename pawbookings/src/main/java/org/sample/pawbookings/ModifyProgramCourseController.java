package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import domain_layer.PawBookings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
        // recuperiamo le informazione inserite dall'utente
        String nome = this.nome.getText();

        // recuperiamo il loader relativa alla schermata di errore (fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_error-view.fxml"));
        Parent secondRoot = loader.load();

        // ne recuperiamo il relativo controller
        AdminErrorController errorController = loader.getController();

        // validiamole
        if (!nome.isEmpty()) {
            try {
                PawBookings.getInstance().nuovaLezione(nome);
                MainApplication.setRoot("new_exercise-view.fxml");
            } catch (Exception e) {
                errorController.setTextError(e.getMessage());
                // andiamo nella schermatav di errore
                MainApplication.goTo(secondRoot);
            }
        } else {
            // i campi sono vuoti...

            // messaggio errore
            errorController.setTextError("devi prima compilare il campo di testo");
            // andiamo nella schermatav di errore
            MainApplication.goTo(secondRoot);
        }
    }

    

    

}
