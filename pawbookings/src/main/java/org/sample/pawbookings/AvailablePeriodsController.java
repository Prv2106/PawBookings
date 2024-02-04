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

public class AvailablePeriodsController implements Initializable{

    @FXML
    private ListView<PeriodoAffido> list;

     // creiamo una lista osservabile per i periodi di affido
    ObservableList<PeriodoAffido> items = FXCollections.observableArrayList();

        @Override
        public void initialize(URL url, ResourceBundle rb) {
        // popoliamo la lista
        PawBookings PB = PawBookings.getInstance();  
        LinkedList<PeriodoAffido> periodiDisponibili = PB.affido();
        
        items.addAll(periodiDisponibili);
        this.list.setItems(items);


        // definiamo la grafica di ogni oggetto della lista
        this.list.setCellFactory(new Callback<ListView<PeriodoAffido>, ListCell<PeriodoAffido>>() {
            @Override
            public ListCell<PeriodoAffido> call(ListView<PeriodoAffido> listView) {
                return new ListCell<PeriodoAffido>() {
                    @Override
                    protected void updateItem(PeriodoAffido periodo, boolean empty) {
                        // per ogni elemento (corso)
                        super.updateItem(periodo, empty);

                        if (periodo == null || empty) {
                            setText(null);
                        } else {
                            VBox vbox = new VBox();
                            vbox.getChildren().add(new Label("Data Inizio: " + periodo.getDataInizio().getDayOfMonth() + "/" + periodo.getDataInizio().getMonthValue() + "/" + periodo.getDataInizio().getYear()));
                            vbox.getChildren().add(new Label("Data Fine: " + periodo.getDataFine().getDayOfMonth() + "/" + periodo.getDataFine().getMonthValue() + "/" + periodo.getDataFine().getYear()));
                            vbox.getChildren().add(new Label("Posti Disponibili: " + periodo.getNumeroPosti()));
                            vbox.getChildren().add(new Label("Costo: " + String.format("%.2f", periodo.getCosto()) + "€"));
                                                       
                            // Imposta il contenuto della cella
                            setGraphic(vbox);

    // -------------------> Aggiungiamo il listener per l'evento di click
                            setOnMouseClicked(event -> {
                                try {
                                    // Azioni da eseguire quando un elemento viene cliccato
                                    LinkedList<Cane> caniDaAffidare = PB.selezionaPeriodo(periodo);

                                    // recuperiamo il loader relativa alla schermata di seleziona cane (fxml)
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("select_dog-view.fxml"));
                                    Parent secondRoot = loader.load(); // qui viene chiamato l'eventuale INITIALIZE
                                    // ne recuperiamo il relativo controller
                                    SelectDogController controller = loader.getController();
                                    controller.start(caniDaAffidare);
                                    // andiamo nella schermata
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

    // TASTI NAVBAR
    @FXML
    void onHomeTabPressed(ActionEvent event) {
        // andiamo nella home page
        try {
            MainApplication.setRoot("home_page-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onProfileTabPressed(ActionEvent event) {
        // andiamo nella sezione relativa al profilo
        try {
            MainApplication.setRoot("profile-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}