package main.java.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import main.java.app.TrikiMasterApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Controlador para la pantalla de selección de dificultad y entrada de nombres.
 */
public class DifficultyController {

    @FXML private TextField txtNombreJ1;
    @FXML private TextField txtNombreJ2;
    @FXML private Button btnAprendiz;
    @FXML private Button btnExplorador;
    @FXML private Button btnEstratega;
    @FXML private Button btnIniciarPartida; // Asegúrate que este ID esté en tu FXML
    @FXML private Button btnAtras;

    private String nivelSeleccionado = "Aprendiz"; // Nivel por defecto (Asume Aprendiz si no se toca)

    /**
     * Inicializa el controlador. Se usa para preseleccionar la dificultad y enlazar listeners.
     */
    @FXML
    public void initialize() {
        // Inicialmente el botón de iniciar está deshabilitado. Se habilitará al seleccionar un nivel.
        // Se puede establecer el estilo del nivel por defecto (Aprendiz) si se desea.
        // También se pueden añadir listeners a los campos de texto para validación dinámica si se necesita.

        // CORRECCIÓN/AJUSTE: Establecer el nivel por defecto y habilitar el botón si los nombres son válidos
        // O dejarlo deshabilitado y habilitar con la selección de dificultad.

        // Asumiendo que el FXML ya tiene el nivel "Aprendiz" preseleccionado:
        // Se deshabilitará inicialmente y se habilitará al tocar cualquier botón de dificultad
        // y al escribir los nombres, para un flujo más limpio.
        btnIniciarPartida.setDisable(true);

        // Se pueden añadir listeners para habilitar el botón si ambos campos de texto no están vacíos
        txtNombreJ1.textProperty().addListener((obs, oldV, newV) -> validarNombresYNivel());
        txtNombreJ2.textProperty().addListener((obs, oldV, newV) -> validarNombresYNivel());

        // Al cargar, aseguramos que el estilo visual del nivel por defecto esté aplicado (opcional)
        // btnAprendiz.getStyleClass().add("nivel-seleccionado");
    }

    private void validarNombresYNivel() {
        String nombreJ1 = txtNombreJ1.getText().trim();
        String nombreJ2 = txtNombreJ2.getText().trim();

        // Habilita si hay nombres (no vacíos y diferentes) Y hay un nivel seleccionado.
        boolean nombresValidos = !nombreJ1.isEmpty() && !nombreJ2.isEmpty() && !nombreJ1.equals(nombreJ2);
        boolean nivelSeleccionadoValido = this.nivelSeleccionado != null && !this.nivelSeleccionado.isEmpty();

        btnIniciarPartida.setDisable(!(nombresValidos && nivelSeleccionadoValido));
    }


    /**
     * Maneja la selección de nivel.
     * @param event El evento que disparó el botón (Aprendiz, Explorador, Estratega).
     */
    @FXML
    private void handleNivelSeleccionado(ActionEvent event) {
        Button botonPresionado = (Button) event.getSource();
        // Obtiene solo la palabra del texto del botón (ej: "Aprendiz")
        this.nivelSeleccionado = botonPresionado.getText().split(" ")[0];

        // Lógica para deshabilitar/habilitar los estilos visuales de los botones de dificultad

        // Habilitar el botón de iniciar si los nombres ya son válidos
        validarNombresYNivel();
    }

    /**
     * Maneja el evento de click en el botón Iniciar Partida.
     * Valida los nombres, crea la instancia del modelo y carga la escena del juego.
     */
    @FXML
    private void handleIniciarPartida(ActionEvent event) {
        String nombreJ1 = txtNombreJ1.getText().trim();
        String nombreJ2 = txtNombreJ2.getText().trim();

        if (nombreJ1.isEmpty() || nombreJ2.isEmpty() || nombreJ1.equals(nombreJ2)) {
            mostrarMensaje("Error de Nombres", "Ambos jugadores deben tener un nombre único.", AlertType.WARNING);
            return;
        }

        if (nivelSeleccionado == null) {
            mostrarMensaje("Error", "Debes seleccionar un nivel de dificultad.", AlertType.WARNING);
            return;
        }

        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Cargar el FXML del juego
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/JuegoPrincipal.fxml"));
            Parent root = loader.load();

            // Obtener la instancia del GameController y pasar los datos
            GameController gameController = loader.getController();
            gameController.iniciarJuego(nombreJ1, nombreJ2, nivelSeleccionado);

            // Mostrar la escena del juego
            stage.setScene(new Scene(root));
            stage.sizeToScene();
        } catch (Exception e) {
            System.err.println("Error al iniciar la partida.");
            e.printStackTrace();
            mostrarMensaje("Error fatal", "No se pudo cargar la pantalla del juego.", AlertType.ERROR);
        }
    }

    /**
     * Regresa al menú principal.
     */
    @FXML
    private void handleAtras(ActionEvent event) {
        Stage stage = (Stage) btnAtras.getScene().getWindow();
        TrikiMasterApp.cambiarEscena("/main/resources/MenuPrincipal.fxml", stage);
    }

    private void mostrarMensaje(String titulo, String contenido, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}