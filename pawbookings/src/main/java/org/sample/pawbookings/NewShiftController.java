package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import domain_layer.PawBookings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.converter.LocalTimeStringConverter;

public class NewShiftController implements Initializable{

    @FXML
    private Button backButton;

    @FXML
    private DatePicker data;

    @FXML
    private Spinner<LocalTime> oraFine;

    @FXML
    private Spinner<LocalTime> oraInizio;


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
        this.data.setValue(LocalDate.now().plusDays(1));
        // Configura il Spinner per accettare orari nel formato HH:mm
        SpinnerValueFactory<LocalTime> valueFactory = new SpinnerValueFactory<LocalTime>() {
            {
                setConverter(new LocalTimeStringConverter());
                setValue(LocalTime.now()); // Imposta l'orario iniziale
                setWrapAround(true); // Per consentire il wrap-around (riavvolgimento) se si raggiunge il limite
            }

            @Override
            public void decrement(int steps) {
                setValue(getValue().minusMinutes(30*steps)); // Decrementa l'orario
            }

            @Override
            public void increment(int steps) {
                setValue(getValue().plusMinutes(30*steps)); // Incrementa l'orario
            }
        };

        oraInizio.setValueFactory(valueFactory);
        oraFine.setValueFactory(valueFactory);
    }

    @FXML
    void onInserisciNuovoCorsoPressed(ActionEvent event) {
        // recuperiamo le informazione inserite dall'utente
        LocalDate data = this.data.getValue();
        LocalTime oraInizio = this.oraInizio.getValue();
        LocalTime oraFine = this.oraFine.getValue();

        // recuperiamo il loader relativa alla schermata di errore (fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_error-view.fxml"));
        Parent secondRoot = loader.load();

        // ne recuperiamo il relativo controller
        AdminErrorController errorController = loader.getController();
        
        try {
            if (PawBookings.getInstance().nuovoTurno(data, oraInizio, oraFine)) {
                MainApplication.setRoot("admin_ok-view.fxml");
            } else {
                // messaggio errore
                errorController.setTextError("Qualcosa è andato storto durante l'inserimento del turno...");
                // andiamo nella schermata di errore
                MainApplication.goTo(secondRoot);
            }
        } catch (Exception e) {
            errorController.setTextError(e.getMessage());
            // andiamo nella schermatav di errore
            MainApplication.goTo(secondRoot);
        }
        
    }

    

}