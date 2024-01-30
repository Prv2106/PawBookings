package org.sample.pawbookings;

import java.io.IOException;
import java.util.LinkedList;

import domain_layer.PawBookings;
import domain_layer.Turno;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class ChooseActivityController {

    @FXML
    private Button backButton;

    @FXML
    void onBackPressed(ActionEvent event) throws IOException {
        try {
            MainApplication.simpleBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // PULSANTI SCHERMATA

    @FXML
    void onNuovaIscrizioneCorsoClicked(ActionEvent event) throws IOException {
        // passiamo alla schermata che mostra l'elenco dei corsi disponibili
        try {
            if (PawBookings.getInstance().nuovaIscrizioneCorso() != null) {
                MainApplication.setRoot("available_courses-view.fxml");
            } else {
                MainApplication.goClientErrorPage("probabilmente il tuo cane risulta già essere iscritto ad un corso, oppure ha già completato tutti i corsi disponibili");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            MainApplication.goClientErrorPage("errore generico");
        }
    }

    
    @FXML
    void onMostraStatoAvanzamentoCorsoClicked(ActionEvent event) throws IOException {
        // passiamo alla schermata che mostra lo stato di avanzamento del cane 'ShowCourseState'
        try {
            MainApplication.setRoot("show_course_state-view.fxml");
        } catch (IOException e) {
            MainApplication.goClientErrorPage("probabilmente il cane non è iscritto ad un corso");
        }
    }

    @FXML
    void onPrenotaTurnoLezioneClicked(ActionEvent event) throws IOException {
        // passiamo alla schermata che mostra l'elenco dei turni disponibili
        try {
            MainApplication.setRoot("available_shifts-view.fxml"); 
        } catch (IOException e) {
            // probabilmente il cane non è iscritto al turno.... quindi:
            MainApplication.goClientErrorPage("probabilmente il cane non è iscritto ad un corso");
        }
    }

    @FXML
    void onScambiaTurnoClicked(ActionEvent event) throws IOException {
        try {
            PawBookings PB = PawBookings.getInstance();
            LinkedList<Turno> elencoTurni = PB.scambioTurno();
            if (elencoTurni == null) {
                // nessun turno prenotato
                MainApplication.goClientErrorPage("Nessun turno ancora prenotato!");
            } else if (elencoTurni.isEmpty()) {
                // nessun turno disponibile
                MainApplication.goClientErrorPage("Nessun turno disponibile!");
            } else {
                // andiamo nella pagina coi turni disponibili
                MainApplication.setRoot("available_shifts_to_switch-view.fxml");
            }
        } catch (IOException e) {
            MainApplication.goClientErrorPage(e.getMessage());
        }
    }
}