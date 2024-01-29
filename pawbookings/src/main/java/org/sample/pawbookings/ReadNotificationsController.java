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

public class ReadNotificationsController implements Initializable {   

    @FXML
    private Button backButton;

    @FXML
    private ListView<String> list;
    // creiamo una lista osservabile per le notifiche (stringhe)
    ObservableList<String> items = FXCollections.observableArrayList();


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
        // avremo una mappa per ogni periodo affido al quale siamo "iscritti"
        LinkedList<Map<String, String>> listaMappa = PB.leggiStatoSalute();
        
        // prendiamo tutti i nomi cani e li mettiamo in un ArrayList
        List<String> nomiCani = new ArrayList<>();
        // prendiamo tutti i messaggi e li mettiamo in un ArrayList
        List<String> messaggi = new ArrayList<>();
        

        for (Map<String, String> m: listaMappa) {
            // per ogni mappa della lista
            messaggi.addAll(m.values());

            for (String k: m.keySet()) {
                nomiCani.add(k);
            }
        }

        // unifichiamo nome e messaggio in un'unica Stringa
        List<String> notifiche = new ArrayList<>();

        for (int i = 0; i < messaggi.size(); i++) 
            notifiche.add(nomiCani.get(i) + ": " + messaggi.get(i));
        
                
        items.addAll(notifiche);
        this.list.setItems(items);

        
        // definiamo la grafica 
        this.list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String notifica, boolean empty) {
                        // per ogni elemento (nome del cane)
                        super.updateItem(notifica, empty);

                        if (notifica == null || empty) {
                            setText(null);
                        } else {
                            // a sx il nome del cane, a dx il messaggio
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
        // dobbiamo cancellare le notifiche del cliente
        PawBookings.getInstance().cancellaNotifiche();
        try {
            MainApplication.setRoot("home_page-view.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
