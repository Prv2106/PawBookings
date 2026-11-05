package org.sample.pawbookings;

import java.io.IOException;

import domain_layer.PawBookings;
import domain_layer.PeriodoAffido;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Base controller for the foster-care pickup flow. Resolves a foster period from the
 * data entered by the administrator.
 */
public class ConcludiAffido {

    /**
     * Resolves a foster period from the administrator input and opens its details.
     *
     * @param str1 customer code or delegation code
     * @param str2 dog code
     * @param delega {@code true} when {@code str1} is a delegation code
     * @throws IOException if the details view cannot be loaded
     */
    protected void goNext(String str1, String str2, boolean delega) throws IOException {

    if (!str1.isEmpty() && !str2.isEmpty()) {

        PawBookings PB = PawBookings.getInstance();

        try {
            PeriodoAffido pa = delega
            ? PB.concludiAffidoDelega(Integer.parseInt(str1), Integer.parseInt(str2))
            : PB.concludiAffido(str1, Integer.parseInt(str2));

            if (pa != null) {

                FXMLLoader ld = new FXMLLoader(getClass().getResource("dettagli_affido-view.fxml"));
                Parent root = ld.load();

                DettagliAffidoController controller = ld.getController();
                controller.start(pa);

                MainApplication.goTo(root);
            } else {

               MainApplication.goAdminErrorPage("Nessuna corrispondenza trovata");
            }
        } catch (Exception e) {
                MainApplication.goAdminErrorPage(e.getMessage());
        }
    } else {

        MainApplication.goAdminErrorPage("Devi prima compilare tutti i campi!");
    }
}
}
