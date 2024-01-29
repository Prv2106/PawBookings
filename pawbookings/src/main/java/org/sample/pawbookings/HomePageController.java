package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.util.Callback;

import domain_layer.Cane;
import domain_layer.PawBookings;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HomePageController implements Initializable {
    @FXML
    ListView<Cane> list; // oggetto ListView recuperato dal file fxml (id=list)

    // creiamo una lista osservabile
    ObservableList<Cane> items = FXCollections.observableArrayList();

    @FXML
    private Text text;

    @FXML
    private ImageView noNotificationIcon;

    @FXML
    private ImageView notificationIcon;


    private BooleanProperty myBooleanProperty;


    // metodo che viene richiamato all'apertura della schermata
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PawBookings PB = PawBookings.getInstance();  

        // preoccupiamoci dell'icona delle "notifiche"
        myBooleanProperty = new SimpleBooleanProperty(PB.getClienteLoggato().getNotifica());

        // Aggiunge un listener per rilevare i cambiamenti nella proprietà booleana "notitifca" di Cliente
        myBooleanProperty.addListener((observable, oldValue, newValue) -> {});

        noNotificationIcon.visibleProperty().bind(myBooleanProperty.not());
        notificationIcon.visibleProperty().bind(myBooleanProperty);
           

        // Caricamento dei Cani del Cliente per l'avviamento
        LinkedList<Cane> listaCani = PB.getClienteLoggato().getCani();

        if (listaCani.isEmpty()) {
            text.setText("Non hai ancora aggiunto un cane");
            text.setFont(new Font(16));
        } else {
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
                                vbox.getChildren().add(new Label("Nome: " + cane.getNome()));
                                vbox.getChildren().add(new Label("Razza: " + cane.getRazza()));
                                vbox.getChildren().add(new Label("Codice: " + cane.getCodiceCane()));

                                // Impostiiamo il contenuto della cella
                                setGraphic(vbox);

        // -------------------> Aggiungiamo il listener per l'evento di click
                                setOnMouseClicked(event -> {
                                    try {
                                        // Azioni da eseguire quando un elemento viene cliccato
                                        PB.selezionaCane(cane);;
                                        // passiamo alla schermata successiva 
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

    @FXML
    void onNotificationIconPressed(ActionEvent event) {
       // andiamo nella schermata relativa alla letture delle notifiche
       try {
        MainApplication.setRoot("read_notifications-view.fxml");
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

     @FXML
    void onDogTabPressed(ActionEvent event) {
        // andiamo nella schermata relativa all'affido
        try {
            MainApplication.setRoot("available_periods-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onProfileTabPressed(ActionEvent event) {
        // andiamo nella schermata relativa al profilo dell'utente
        try {
            MainApplication.setRoot("profile-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onVisualizzaProgrammaPressed(ActionEvent event) {
        // andiamo nella schermata relativa alla visualizzazione dei programmi
        try {
            MainApplication.setRoot("show_all_courses-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

}