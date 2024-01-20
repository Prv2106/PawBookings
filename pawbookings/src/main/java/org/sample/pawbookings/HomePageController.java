package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.util.Callback;

import domain_layer.Cane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class HomePageController implements Initializable{
    @FXML
    ListView<Cane> list; // oggetto ListView recuperato dal file fxml (id=list)

    // creiamo una lista osservabile
    ObservableList<Cane> items = FXCollections.observableArrayList();


    // metodo che viene richiamato all'apertura della schermata
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // popoliamo la lista
        this.list.setItems(items);
        // List<String> cani = PawBookings.getInstance().getCaniCliente();
        // items.addAll(cani);
        items.add(new Cane(0, "Rex", "Pastore Tedesco", false));
        items.add(new Cane(1, "Luna", "Barboncino", false));

        // definiamo la grafica di ogni oggetto della lista
        this.list.setCellFactory(new Callback<ListView<Cane>, ListCell<Cane>>() {
            @Override
            public ListCell<Cane> call(ListView<Cane> listView) {
                return new ListCell<Cane>() {
                    @Override
                    protected void updateItem(Cane cane, boolean empty) {
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
                                System.out.println("Elemento cliccato: " + cane.getNome());
                                // PawBookings.getInstance().setCaneSelezionato(cane);

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
    

}