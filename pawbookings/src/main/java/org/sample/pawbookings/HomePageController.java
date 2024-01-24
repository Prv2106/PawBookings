package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.util.Callback;

import domain_layer.Cane;
import domain_layer.PawBookings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class HomePageController implements Initializable {
    @FXML
    ListView<Cane> list; // oggetto ListView recuperato dal file fxml (id=list)

    // creiamo una lista osservabile
    ObservableList<Cane> items = FXCollections.observableArrayList();

    // metodo che viene richiamato all'apertura della schermata
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // popoliamo la lista
        PawBookings PB = PawBookings.getInstance();  
        
        // Caricamento dei Cani del Cliente per l'avviamento
        LinkedList<Cane> listaCani =PB.getClienteLoggato().getCani();

        items.addAll(listaCani);
        this.list.setItems(items);

        // definiamo la grafica di ogni oggetto della lista
        this.list.setCellFactory(new Callback<ListView<Cane>, ListCell<Cane>>() {
            @Override
            public ListCell<Cane> call(ListView<Cane> listView) {
                return new ListCell<Cane>() {
                    @Override
                    protected void updateItem(Cane cane, boolean empty) {
                        // per ogni elemento (cane)
                        super.updateItem(cane, empty);

                        if (cane == null || empty) {
                            setText(null);
                        } else {
                            // nome e razza
                            VBox vbox = new VBox();
                            Label nomeLabel = new Label("Nome: " + cane.getNome());
                            Label razzaLabel = new Label("Razza: " + cane.getRazza());
                            vbox.getChildren().addAll(nomeLabel, razzaLabel);

                            // Imposta il contenuto della cella
                            setGraphic(vbox);

    // -------------------> Aggiungiamo il listener per l'evento di click
                            setOnMouseClicked(event -> {
                                // Azioni da eseguire quando un elemento viene cliccato
                                PB.selezionaCane(cane);;

                                // passiamo alla schermata successiva
                                try {
                                    MainApplication.setRoot("choose_activity-view.fxml");
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

     @FXML
    void onDogTabPressed(ActionEvent event) {

    }

    @FXML
    void onProfileTabPressed(ActionEvent event) {
        
    }
    

}