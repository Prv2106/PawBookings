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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * Controller for the list of foster periods with free places. Routes to dog selection
 * for the chosen period.
 */
public class AvailablePeriodsController implements Initializable{

    @FXML
    private ListView<PeriodoAffido> list;

    ObservableList<PeriodoAffido> items = FXCollections.observableArrayList();

        /**
         * Populates the foster-period list and configures the cell renderer and selection handler.
         */
        @Override
        public void initialize(URL url, ResourceBundle rb) {

        PawBookings PB = PawBookings.getInstance();
        LinkedList<PeriodoAffido> periodiDisponibili = PB.affido();

        items.addAll(periodiDisponibili);
        this.list.setItems(items);

        this.list.setCellFactory(new Callback<ListView<PeriodoAffido>, ListCell<PeriodoAffido>>() {
            @Override
            public ListCell<PeriodoAffido> call(ListView<PeriodoAffido> listView) {
                return new ListCell<PeriodoAffido>() {
                    @Override
                    protected void updateItem(PeriodoAffido periodo, boolean empty) {

                        super.updateItem(periodo, empty);

                        if (periodo == null || empty) {
                            setText(null);
                        } else {
                            VBox vbox = new VBox();
                            vbox.getChildren().add(new Label("Data Inizio: " + periodo.getDataInizio().getDayOfMonth() + "/" + periodo.getDataInizio().getMonthValue() + "/" + periodo.getDataInizio().getYear()));
                            vbox.getChildren().add(new Label("Data Fine: " + periodo.getDataFine().getDayOfMonth() + "/" + periodo.getDataFine().getMonthValue() + "/" + periodo.getDataFine().getYear()));
                            vbox.getChildren().add(new Label("Posti Disponibili: " + periodo.getNumeroPosti()));
                            vbox.getChildren().add(new Label("Costo: " + String.format("%.2f", periodo.getCosto()) + "€"));

                            setGraphic(vbox);

                            setOnMouseClicked(event -> {
                                try {

                                    LinkedList<Cane> caniDaAffidare = PB.selezionaPeriodo(periodo);

                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("select_dog-view.fxml"));
                                    Parent secondRoot = loader.load();

                                    SelectDogController controller = loader.getController();
                                    controller.start(caniDaAffidare);

                                    MainApplication.goTo(secondRoot);
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
    void onHomeTabPressed(ActionEvent event) {

        try {
            MainApplication.setRoot("home_page-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onProfileTabPressed(ActionEvent event) {

        try {
            MainApplication.setRoot("profile-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
