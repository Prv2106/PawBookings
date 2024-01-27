package org.sample.pawbookings;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

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

public class ReadNotificationsController implements Initializable {
    private Map<String, String> mappa;
    private Set<String> keys; // tutte le chiavi della mappa

    @FXML
    private Button backButton;

    @FXML
    private ListView<String> list;
    // creiamo una lista osservabile per le notifiche (stringhe)
    ObservableList<String> notifiche = FXCollections.observableArrayList();


    @FXML
    void onBackPressed(ActionEvent event) {
        try {
            MainApplication.simpleBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getKeyByIndex(int index) {
        String key = "";

        int counter = 0;
        for (String k : keys) {
            if (index == counter) {
                key = k;
            }
            counter++;
        }
         return key;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PawBookings PB = PawBookings.getInstance();  
        mappa = PB.leggiStatoSalute();
        keys = mappa.keySet();
        
        // recuperiamo le notifiche mettiamole nella UI
        notifiche.addAll(mappa.values());
        this.list.setItems(notifiche);

    
        // definiamo la grafica 
        int counter = 0;
    
        this.list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String notifica, boolean empty) {
                        // per ogni elemento (notifica)
                        super.updateItem(notifica, empty);

                        if (notifica == null || empty) {
                            setText(null);
                        } else {
                            // a sx il nome del cane, a dx il messaggio
                            VBox vbox = new VBox();
                            vbox.getChildren().add(new Label(getKeyByIndex(counter) + ": " + notifica));
                            setGraphic(vbox);
                        }
                         counter++;
                    }
                };
            }
        });
    }

}
