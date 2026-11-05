package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import domain_layer.PawBookings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.converter.LocalTimeStringConverter;

/**
 * Controller for creating a bookable shift in the selected lesson.
 */
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

    /**
     * Initializes the date picker and the time spinners.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.data.setValue(LocalDate.now().plusDays(1));

        // Configures the spinner to accept times in HH:mm format.
        SpinnerValueFactory<LocalTime> valueFactory1 = new SpinnerValueFactory<LocalTime>() {
            {
                setConverter(new LocalTimeStringConverter());
                setValue(LocalTime.of(8, 0)); // Initial time.
                setWrapAround(true); // Enables wrap-around at the limits.
            }

            @Override
            public void decrement(int steps) {
                setValue(getValue().minusMinutes(30*steps)); // Decrements by 30 minutes per step.
            }

            @Override
            public void increment(int steps) {
                setValue(getValue().plusMinutes(30*steps)); // Increments by 30 minutes per step.
            }
        };

        // Configures the spinner to accept times in HH:mm format.
        SpinnerValueFactory<LocalTime> valueFactory2 = new SpinnerValueFactory<LocalTime>() {
            {
                setConverter(new LocalTimeStringConverter());
                setValue(LocalTime.of(16, 0)); // Initial time.
                setWrapAround(true); // Enables wrap-around at the limits.
            }

            @Override
            public void decrement(int steps) {
                setValue(getValue().minusMinutes(30*steps)); // Decrements by 30 minutes per step.
            }

            @Override
            public void increment(int steps) {
                setValue(getValue().plusMinutes(30*steps)); // Increments by 30 minutes per step.
            }
        };

        oraInizio.setValueFactory(valueFactory1);
        oraFine.setValueFactory(valueFactory2);
    }

    @FXML
    void onInserisciNuovoCorsoPressed(ActionEvent event) throws IOException {

        LocalDate data = this.data.getValue();
        LocalTime oraInizio = this.oraInizio.getValue();
        LocalTime oraFine = this.oraFine.getValue();

        try {
            if (PawBookings.getInstance().nuovoTurno(data, oraInizio, oraFine)) {
                MainApplication.setRoot("admin_ok-view.fxml");
            } else {
               MainApplication.goAdminErrorPage("Qualcosa è andato storto durante l'inserimento del turno...");
            }
        } catch (Exception e) {
            MainApplication.goAdminErrorPage(e.getMessage());
        }

    }

}
