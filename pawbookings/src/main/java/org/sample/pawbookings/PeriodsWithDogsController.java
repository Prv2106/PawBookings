package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import domain_layer.Cane;
import domain_layer.PawBookings;
import domain_layer.PeriodoAffido;
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

public class PeriodsWithDogsController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private ListView<PeriodoAffido> list;
    // creiamo una lista osservabile per i periodi
    ObservableList<PeriodoAffido> items = FXCollections.observableArrayList();

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
        items.addAll(PawBookings.getInstance().notificaStatoSalute());
        this.list.setItems(items);

        // definiamo la grafica di ogni oggetto della lista (periodo)
        this.list.setCellFactory(new Callback<ListView<PeriodoAffido>, ListCell<PeriodoAffido>>() {
            @Override
            public ListCell<PeriodoAffido> call(ListView<PeriodoAffido> listView) {
                return new ListCell<PeriodoAffido>() {
                    @Override
                    protected void updateItem(PeriodoAffido pa, boolean empty) {
                        // per ogni elemento
                        super.updateItem(pa, empty);

                        if (pa == null || empty) {
                            setText(null);
                        } else {
                            // data inizio e data fine
                            VBox vbox = new VBox();
                            vbox.getChildren().add(new Label("Data inizio: " + pa.getDataInizio()));
                            vbox.getChildren().add(new Label("Data fine: " + pa.getDataFine()));

                            // Imposta il contenuto della cella
                            setGraphic(vbox);

    // -------------------> Aggiungiamo il listener per l'evento di click
                            setOnMouseClicked(event -> {
                                try {
                                    // Azioni da eseguire quando un elemento viene cliccato
                                    LinkedList<Cane> caniInAffido = PawBookings.getInstance().mostraCaniInAffido(pa);

                                    // recuperiamo il loader della pagina "DogsToRate"
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("show_dogs_to_rate-view.fxml"));
                                    // reperiamone la root
                                    Parent root = loader.load();
                                    // recuperiamone il controller
                                    ShowDogsToRateController controller = loader.getController();

                                    // passiamogli la lista
                                    controller.initialize(caniInAffido);
                                    
                                    // settiamo la root
                                    MainApplication.goTo(root);
                                    
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
