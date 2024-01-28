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
        this.lezioneCorrente.setText(PawBookings.getInstance().getCorsoSelezionato().getLezioneCorrente().getNome());
    }

    @FXML
    void onAggiungiEsercizioPressed(ActionEvent event) throws IOException {
        // recuperiamo le informazione inserite dall'utente
        String nome = this.nome.getText();
        String descrizione = this.descrizione.getText();

        // recuperiamo il loader relativa alla schermata di errore (fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_error-view.fxml"));
        Parent secondRoot = loader.load();

        // ne recuperiamo il relativo controller
        AdminErrorController errorController = loader.getController();

        // validiamole
        if (!nome.isEmpty() && !descrizione.isEmpty()) {
            try {
                PawBookings.getInstance().inserisciEsercizio(nome, descrizione);
                MainApplication.setRoot("exercise_added-view");
            } catch (IOException e) {
                errorController.setTextError("qualcosa è andato storto :(");
                MainApplication.goTo(secondRoot);
            }
        } else {
            // uno dei campi è vuoto
            errorController.setTextError("devi compilare tutti i campi!");
            MainApplication.goTo(secondRoot);
        }
       
    }
}

