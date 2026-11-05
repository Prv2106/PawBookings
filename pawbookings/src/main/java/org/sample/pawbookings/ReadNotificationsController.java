package org.sample.pawbookings;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
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

/**
 * Controller for reading and clearing customer health-status notifications.
 */
public class ReadNotificationsController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private ListView<String> list;

    ObservableList<String> items = FXCollections.observableArrayList();

    @FXML
    void onBackPressed(ActionEvent event) {
        try {
            MainApplication.simpleBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Combines and displays the logged-in customer's health-status notifications.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PawBookings PB = PawBookings.getInstance();
        // One map is stored for each foster period the customer subscribed to.
        LinkedList<Map<String, String>> listaMappa = PB.leggiStatoSalute();

        // Collects dog names.
        List<String> nomiCani = new ArrayList<>();
        // Collects health-status messages.
        List<String> messaggi = new ArrayList<>();

        for (Map<String, String> m: listaMappa) {

            messaggi.addAll(m.values());

            for (String k: m.keySet()) {
                nomiCani.add(k);
            }
        }

        // Combines each dog name with its health-status message.
        List<String> notifiche = new ArrayList<>();

        for (int i = 0; i < messaggi.size(); i++)
            notifiche.add(nomiCani.get(i) + ": " + messaggi.get(i));

        items.addAll(notifiche);
        this.list.setItems(items);

        this.list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String notifica, boolean empty) {

                        super.updateItem(notifica, empty);

                        if (notifica == null || empty) {
                            setText(null);
                        } else {

                            VBox vbox = new VBox();
                            vbox.getChildren().add(new Label(notifica));
                            setGraphic(vbox);
                        }
                    }
                };
            }
        });
    }

    @FXML
    void onDeletePressed(ActionEvent event) {
        // Clears the current customer's notifications.
        PawBookings.getInstance().cancellaNotifiche();
        try {
            MainApplication.setRoot("home_page-view.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
