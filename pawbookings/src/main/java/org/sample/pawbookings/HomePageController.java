package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.util.Callback;

import domain_layer.Cane;
import domain_layer.PawBookings;
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

/**
 * Controller for the customer home page. Lists the customer's dogs and provides
 * navigation to notifications, foster care, profile, and course programmes.
 */
public class HomePageController implements Initializable {
    @FXML
    ListView<Cane> list;

    ObservableList<Cane> items = FXCollections.observableArrayList();

    @FXML
    private Text text;

    @FXML
    private ImageView noNotificationIcon;

    @FXML
    private ImageView notificationIcon;

    private BooleanProperty myBooleanProperty;

    /**
     * Loads the customer's dogs, binds the notification icon, and configures dog selection.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PawBookings PB = PawBookings.getInstance();

        myBooleanProperty = new SimpleBooleanProperty(PB.getClienteLoggato().getNotifica());

        myBooleanProperty.addListener((observable, oldValue, newValue) -> {});

        noNotificationIcon.visibleProperty().bind(myBooleanProperty.not());
        notificationIcon.visibleProperty().bind(myBooleanProperty);

        LinkedList<Cane> listaCani = PB.getClienteLoggato().getCani();

        if (listaCani.isEmpty()) {
            text.setText("Non hai ancora aggiunto un cane");
            text.setFont(new Font(18));
        } else {
            items.addAll(listaCani);
            this.list.setItems(items);

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

                                VBox vbox = new VBox();
                                vbox.getChildren().add(new Label("Nome: " + cane.getNome()));
                                vbox.getChildren().add(new Label("Razza: " + cane.getRazza()));
                                vbox.getChildren().add(new Label("Codice: " + cane.getCodiceCane()));

                                setGraphic(vbox);

                                setOnMouseClicked(event -> {
                                    try {

                                        PB.selezionaCane(cane);;

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

       try {
        MainApplication.setRoot("read_notifications-view.fxml");
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

     @FXML
    void onDogTabPressed(ActionEvent event) {

        try {
            MainApplication.setRoot("available_periods-view.fxml");
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

    @FXML
    void onVisualizzaProgrammaPressed(ActionEvent event) {

        try {
            MainApplication.setRoot("show_all_courses-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
