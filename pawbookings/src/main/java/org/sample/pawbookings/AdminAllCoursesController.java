package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import domain_layer.Corso;
import domain_layer.Esercizio;
import domain_layer.Lezione;
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

public class AdminAllCoursesController {

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

    // invocato dal metodo chiamante
    public void initialize(String nextDestination) throws IOException {
        items.addAll(PawBookings.getInstance().getCorsi());
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

                            if (nextDestination.equals("modify_info_course-view.fxml")) {
                                // se devo modificare le info (capienza e costo), mostro solo le info del corso
                                vbox.getChildren().add(new Label("Capienza: " + corso.getCapienza()));
                                vbox.getChildren().add(new Label("Costo: " + corso.getCosto() + "€"));
                            } else {
                                // devo modificare il programma, quindi mostro il programma
                                vbox.getChildren().add(new Label("Programma (num. lezioni: " + corso.getLezioni().size() +")"));
                                for (int i = 0; i < corso.getLezioni().size(); i++) {
                                Lezione lezione = corso.getLezioni().get(i);
                                vbox.getChildren().add(new Label("   - " + (i+1) +") " + lezione.getNome()));
                                    // sotto ogni lezione, mettiamone gli esercizi
                                    for (int j = 0; j < lezione.getEsercizi().size(); j++) {
                                        Esercizio esercizio = lezione.getEsercizi().get(j);
                                        vbox.getChildren().add(new Label("        - " + (j+1) +") " + esercizio.getNome()));
                                    }
                                }
                            }
                            
                            // Imposta il contenuto della cella
                            setGraphic(vbox);

    // -------------------> Aggiungiamo il listener per l'evento di click
                            setOnMouseClicked(event -> {
                                // Azioni da eseguire quando un elemento viene cliccato
                                PawBookings.getInstance().selezionaCorso(corso);

                                // passiamo alla schermata successiva
                                try {
                                    MainApplication.setRoot(nextDestination);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

}

