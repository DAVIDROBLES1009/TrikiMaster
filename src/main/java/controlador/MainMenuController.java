package main.java.controlador;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import main.java.app.TrikiMasterApp;

/**
 * Controlador para la pantalla del Menú Principal.
 */
public class MainMenuController {

    @FXML private Button btnJugar;
    @FXML private Button btnModoPractica; // No implementaremos la lógica específica de 'Práctica' aquí, solo el flujo.
    @FXML private Button btnSalir;

    /**
     * Maneja el evento de click en el botón Jugar (o Modo Práctica).
     * Navega a la pantalla de selección de dificultad y entrada de nombres.
     */
    @FXML
    private void handleJugar(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Carga la escena de selección de dificultad (que también pedirá nombres)
        TrikiMasterApp.cambiarEscena("/main/resources/SeleccionDificultad.fxml", stage);
    }

    /**
     * Maneja el evento de click en el botón Salir.
     */
    @FXML
    private void handleSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    // El método initialize puede usarse para configuraciones iniciales si es necesario
    @FXML
    public void initialize() {
        // Configuración inicial...
    }
}
