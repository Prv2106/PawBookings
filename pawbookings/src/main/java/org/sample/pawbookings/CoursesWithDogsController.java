package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import domain_layer.Corso;
import domain_layer.PawBookings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class CoursesWithDogsController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private ListView<Corso> list;
    // creiamo una lista osservabile per i corsi
    ObservableList<Corso> items = FXCollections.observableArrayList();

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
        items.addAll(PawBookings.getInstance().inserisciTurnoLezione());
        this.list.setItems(items);

        // definiamo la grafica di ogni oggetto della lista (corso)
        this.list.setCellFactory(new Callback<ListView<Corso>, ListCell<Corso>>() {
            @Override
            public ListCell<Corso> call(ListView<Corso> listView) {
                return new ListCell<Corso>() {
                    @Override
                    protected void updateItem(Corso corso, boolean empty) {
                        // per ogni elemento (corso)
                        super.updateItem(corso, empty);

                        if (corso == null || empty) {
                            setText(null);
                        } else {
                            // tipo corso e costo
                            VBox vbox = new VBox();
                            vbox.getChildren().add(new Label("Tipo Corso: " + corso.getTipoCorso()));
                            vbox.getChildren().add(new Label("Costo: " + corso.getCosto() + "€"));

                            // Imposta il contenuto della cella
                            setGraphic(vbox);

    // -------------------> Aggiungiamo il listener per l'evento di click
                            setOnMouseClicked(event -> {
                                // Azioni da eseguire quando un elemento viene cliccato
                                PawBookings.getInstance().selezionaCorsoModificaTurni(corso);

                                // passiamo alla schermata successiva
                                try {
                                    MainApplication.setRoot("admin_show_course_program-view.fxml");
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
