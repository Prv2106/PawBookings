package org.sample.pawbookings;
import java.io.IOException;
import java.time.LocalDate;

import domain_layer.PawBookings;
import domain_layer.PeriodoAffido;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.text.Text;


public class DettagliAffidoController {

    @FXML
    private Text codice;

    @FXML
    private Text dataInizio;

    @FXML
    private Text dataFine;

    @FXML
    private Text dataRitiro;


    public void start(PeriodoAffido pa) {
        codice.setText(Integer.toString(pa.getCodice()));
        dataInizio.setText(pa.getDataInizio().getDayOfMonth() + "/" + pa.getDataInizio().getMonthValue() + "/" + pa.getDataInizio().getYear());
        dataFine.setText(pa.getDataFine().getDayOfMonth() + "/" + pa.getDataFine().getMonthValue() + "/" + pa.getDataFine().getYear());
        dataRitiro.setText(LocalDate.now().getDayOfMonth() + "/" + LocalDate.now().getMonthValue() + "/" + LocalDate.now().getYear());
    }

    @FXML
    void onConcludiAffidoPressed(ActionEvent event) throws IOException {
        try {
            float importo = PawBookings.getInstance().confermaConclusioneAffido();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_cash-view.fxml"));
            Parent root = loader.load();
            AdminCashController controller = loader.getController();
            controller.setAmount(importo);
            MainApplication.goTo(root);            
        } catch (IOException e) {
            MainApplication.goAdminErrorPage("Qualcosa è andato storto...");
        }
    }

}
