package org.sample.pawbookings;

import java.io.IOException;

import domain_layer.PawBookings;
import domain_layer.PeriodoAffido;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ConcludiAffido {

    protected void goNext(String str1, String str2, boolean delega) throws IOException {  
    // validiamole
    if (!str1.isEmpty() && !str2.isEmpty()) {
        // operazione di sistema
        PawBookings PB = PawBookings.getInstance();  
        
        try {
            PeriodoAffido pa = delega 
            ? PB.concludiAffidoDelega(Integer.parseInt(str1), Integer.parseInt(str2)) 
            : PB.concludiAffido(str1, Integer.parseInt(str2));

            if (pa != null) {
                // recuperiamo il loader relativa alla schermata di dettagli affido (fxml)
                FXMLLoader ld = new FXMLLoader(getClass().getResource("dettagli_affido-view.fxml"));
                Parent root = ld.load(); // qui viene chiamato l'eventuale INITIALIZE
                // ne recuperiamo il relativo controller
                DettagliAffidoController controller = ld.getController();
                controller.start(pa);
                // andiamo nella schermata
                MainApplication.goTo(root);
            } else {
                // nessun affido trovato sulla base dei dati inseriti:
               MainApplication.goAdminErrorPage("Nessuna corrispondenza trovata");
            }
        } catch (Exception e) {
                MainApplication.goAdminErrorPage(e.getMessage());
        }
    } else {
        // i campi sono vuoti...
        MainApplication.goAdminErrorPage("Devi prima compilare tutti i campi!");
    }
}    
}
