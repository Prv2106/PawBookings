package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import domain_layer.Turno;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class AvailableShiftsController implements Initializable {

    @FXML
    private ListView<Turno> list;

    @FXML
    private Text textCorsoCorrente = new Text();

    @FXML
    private Text textProssimaLezione = new Text();


    // creiamo una lista osservabile per i corsi
    ObservableList<Turno> items = FXCollections.observableArrayList();

    // metodo che viene richiamato all'apertura della schermata
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // impostiamo turno e lezione
        textCorsoCorrente.setText("Corso base");
        textProssimaLezione.setText("Lezione B");

        // popoliamo la lista dinamica
        Turno turnoMattina = new Turno(LocalDate.now(), LocalTime.of(9, 0, 0), LocalTime.of(11, 0, 0));
        Turno turnoPomeriggio = new Turno(LocalDate.now(), LocalTime.of(15, 0, 0), LocalTime.of(17, 0, 0));
        Turno turnoSera = new Turno(LocalDate.now(), LocalTime.of(18, 30, 0), LocalTime.of(20, 30, 0));

        items.addAll(turnoMattina, turnoPomeriggio, turnoSera);
        this.list.setItems(items);

        // definiamo la grafica di ogni oggetto della lista
        this.list.setCellFactory(new Callback<ListView<Turno>, ListCell<Turno>>() {
            @Override
            public ListCell<Turno> call(ListView<Turno> listView) {
                return new ListCell<Turno>() {
                    @Override
                    protected void updateItem(Turno turno, boolean empty) {
                        // per ogni elemento (turno)
                        super.updateItem(turno, empty);

                        if (turno == null || empty) {
                            setText(null);
                        } else {
                            // data, ora inizio e ora fine
                            VBox vbox = new VBox();
                            Label dataLabel = new Label("Data: " + turno.getData());
                            Label oraInizioLabel = new Label("Ora Inizio: " + turno.getOraFine());
                            Label oraFineLabel = new Label("Ora fine: " + turno.getOraFine());

                            vbox.getChildren().addAll(dataLabel, oraInizioLabel, oraFineLabel);

                            // Imposta il contenuto della cella
                            setGraphic(vbox);

    // -------------------> Aggiungiamo il listener per l'evento di click
                            setOnMouseClicked(event -> {
                                // Azioni da eseguire quando un elemento viene cliccato

                                // passiamo alla schermata successiva
                                try {
                                    MainApplication.setRoot("ok-view.fxml");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    }
                };
            }
        });
    }



}
