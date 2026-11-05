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

/**
 * Controller for the list of foster periods containing dogs. Routes to the
 * health-status rating screen.
 */
public class PeriodsWithDogsController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private ListView<PeriodoAffido> list;

    ObservableList<PeriodoAffido> items = FXCollections.observableArrayList();

    @FXML
    void onBackPressed(ActionEvent event) {
        try {
            MainApplication.simpleBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates the foster-period list and configures the health-status navigation handler.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        items.addAll(PawBookings.getInstance().notificaStatoSalute());
        this.list.setItems(items);

        this.list.setCellFactory(new Callback<ListView<PeriodoAffido>, ListCell<PeriodoAffido>>() {
            @Override
            public ListCell<PeriodoAffido> call(ListView<PeriodoAffido> listView) {
                return new ListCell<PeriodoAffido>() {
                    @Override
                    protected void updateItem(PeriodoAffido pa, boolean empty) {

                        super.updateItem(pa, empty);

                        if (pa == null || empty) {
                            setText(null);
                        } else {

                            VBox vbox = new VBox();
                            vbox.getChildren().add(new Label("Data inizio: " + pa.getDataInizio()));
                            vbox.getChildren().add(new Label("Data fine: " + pa.getDataFine()));

                            setGraphic(vbox);

                            setOnMouseClicked(event -> {
                                try {

                                    LinkedList<Cane> caniInAffido = PawBookings.getInstance().mostraCaniInAffido(pa);

                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("show_dogs_to_rate-view.fxml"));

                                    Parent root = loader.load();

                                    ShowDogsToRateController controller = loader.getController();

                                    controller.initialize(caniInAffido);

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
