package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import domain_layer.Corso;
import domain_layer.Lezione;
import domain_layer.PawBookings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ShowCourseStateController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private ListView<Lezione> list1;
    // creiamo una lista osservabile per le lezioni seguite
    ObservableList<Lezione> lezioniSeguite = FXCollections.observableArrayList();

    @FXML
    private ListView<Lezione> list2;
    // creiamo una lista osservabile per le lezioni mancanti
    ObservableList<Lezione> lezioniMancanti = FXCollections.observableArrayList();

    @FXML
    void onBackPressed(ActionEvent event) {
        try {
            MainApplication.simpleBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PawBookings PB = PawBookings.getInstance();  
        Map<String, List<Lezione>> lista = PB.mostraStatoAvanzamentoCorso();
        
        // recuperiamo le lezioni seguite e mettiamole nella UI
        lezioniSeguite.addAll(lista.get("lezioni seguite"));
        this.list1.setItems(lezioniSeguite);

        // recuperiamo le lezioni mancanti e mettiamole nella UI
        lezioniMancanti.addAll(lista.get("lezioni mancanti"));
        this.list2.setItems(lezioniMancanti);


        // definiamo la grafica di ogni oggetto di entrambe le liste..
    
           // list1 = lista lezioni seguite
            this.list1.setCellFactory(new Callback<ListView<Lezione>, ListCell<Lezione>>() {
                @Override
                public ListCell<Lezione> call(ListView<Lezione> listView) {
                    return new ListCell<Lezione>() {
                        @Override
                        protected void updateItem(Lezione lezione, boolean empty) {
                            // per ogni elemento (lezione)
                            super.updateItem(lezione, empty);

                            if (lezione == null || empty) {
                                setText(null);
                            } else {
                                // nome lezione
                                VBox vbox = new VBox();
                                vbox.getChildren().add(new Label("Lezione: " + lezione.getNome()));
                                setGraphic(vbox);
                            }
                        }
                    };
                }
            });

            // list2 = lista lezioni mancanti
            this.list2.setCellFactory(new Callback<ListView<Lezione>, ListCell<Lezione>>() {
                @Override
                public ListCell<Lezione> call(ListView<Lezione> listView) {
                    return new ListCell<Lezione>() {
                        @Override
                        protected void updateItem(Lezione lezione, boolean empty) {
                            // per ogni elemento (lezione)
                            super.updateItem(lezione, empty);

                            if (lezione == null || empty) {
                                setText(null);
                            } else {
                                // nome lezione
                                VBox vbox = new VBox();
                                vbox.getChildren().add(new Label("Lezione: " + lezione.getNome()));
                                setGraphic(vbox);
                            }
                        }
                    };
                }
            });

    }

}