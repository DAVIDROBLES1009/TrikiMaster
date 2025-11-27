package main.java.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import java.util.List;
import main.java.modelo.Juego;
import main.java.modelo.Jugador;
import main.java.modelo.Pregunta;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.java.app.TrikiMasterApp; // <--- CORRECCIÓN CLAVE: Importación de la App
import javafx.scene.text.Font; // Importación necesaria para Font

/**
 * Controlador principal del juego TrikiMaster.
 * Gestiona la lógica de la pregunta, la respuesta y las jugadas en el tablero.
 */
public class GameController {

    // --- Elementos de la GUI (deben coincidir con el FXML) ---
    @FXML private Label lblPregunta;
    @FXML private Label lblTurno;
    @FXML private Label lblPuntuacionX;
    @FXML private Label lblPuntuacionO;
    @FXML private Button btnOpcion1;
    @FXML private Button btnOpcion2;
    @FXML private Button btnOpcion3;
    @FXML private Button btnOpcion4;
    @FXML private GridPane gridTablero;
    @FXML private Button btnMenuPrincipal;

    // Array para mapear los botones del tablero
    private Button[][] casillas = new Button[3][3];

    // --- Atributos del Modelo y Control ---
    private Juego juego;
    private String nivelActual;
    private boolean respuestaCorrectaRequerida = true; // Indica si se necesita una respuesta correcta para jugar

    /**
     * Método de inicialización llamado por JavaFX después de que se cargan los FXML.
     */
    @FXML
    public void initialize() {
        // Inicializa los 9 botones del tablero programáticamente
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = new Button(" ");
                btn.setMinSize(100, 100);
                btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                btn.setFont(new Font(36));

                // Asigna la acción de jugada a cada botón
                final int fila = i;
                final int col = j;
                btn.setOnAction(event -> handleCasillaClick(fila, col));

                gridTablero.add(btn, j, i); // Columna, Fila
                casillas[i][j] = btn;

                // Asegurar que crezcan para llenar el espacio
                GridPane.setHgrow(btn, javafx.scene.layout.Priority.ALWAYS);
                GridPane.setVgrow(btn, javafx.scene.layout.Priority.ALWAYS);
            }
        }

        // Desactivar botones de jugada hasta que la pregunta sea respondida
        habilitarCasillas(false);
        habilitarBotonesOpcion(false);
    }

    /**
     * Inicializa el juego con los nombres de los jugadores y el nivel.
     * Este método es llamado desde DifficultyController.
     * @param nombreJ1 Nombre del Jugador X.
     * @param nombreJ2 Nombre del Jugador O.
     * @param nivel El nivel de dificultad seleccionado.
     */
    public void iniciarJuego(String nombreJ1, String nombreJ2, String nivel) {
        this.nivelActual = nivel;
        this.juego = new Juego(nombreJ1, nombreJ2);
        this.juego.setNivelDificultad(nivel); // Establecer el nivel en el modelo

        actualizarPuntuacionGUI();
        actualizarTurnoGUI();
        iniciarRonda();
    }

    /**
     * Inicia una nueva ronda de juego (resetea el tablero y genera una nueva pregunta).
     */
    private void iniciarRonda() {
        // CORRECCIÓN CLAVE: El método correcto es reiniciarJuego()
        juego.reiniciarJuego();

        reiniciarTableroGUI();
        actualizarTurnoGUI();

        // Generar la primera pregunta de la ronda
        generarNuevaPregunta();

        // Desactivar el tablero y activar opciones
        habilitarCasillas(false);
        habilitarBotonesOpcion(true);
    }

    /**
     * Genera una nueva pregunta en el modelo y actualiza la GUI.
     */
    private void generarNuevaPregunta() {
        juego.generarNuevaPregunta();
        Pregunta p = juego.getPreguntaActual();

        // Actualizar Label de la pregunta
        lblPregunta.setText(p.getOperando1() + " " + p.getOperador() + " " + p.getOperando2() + " = ?");

        // Actualizar botones de opción
        List<Integer> opciones = p.getOpcionesDeRespuesta();
        btnOpcion1.setText(String.valueOf(opciones.get(0)));
        btnOpcion2.setText(String.valueOf(opciones.get(1)));
        btnOpcion3.setText(String.valueOf(opciones.get(2)));
        btnOpcion4.setText(String.valueOf(opciones.get(3)));

        respuestaCorrectaRequerida = true; // Restaurar el requisito
    }

    /**
     * Maneja el evento de click en los botones de opción de respuesta.
     */
    @FXML
    private void handleRespuesta(ActionEvent event) {
        if (!respuestaCorrectaRequerida) return;

        Button botonPresionado = (Button) event.getSource();
        int respuestaUsuario = Integer.parseInt(botonPresionado.getText());

        if (juego.getPreguntaActual().verificarRespuesta(respuestaUsuario)) {
            // Respuesta Correcta
            mostrarMensaje("¡Correcto!", "Tienes permiso para hacer tu jugada.", Alert.AlertType.INFORMATION);
            respuestaCorrectaRequerida = false; // Desbloquea la jugada en el tablero
            habilitarCasillas(true);
            habilitarBotonesOpcion(false);
        } else {
            // Respuesta Incorrecta: Ceder el turno
            mostrarMensaje("Incorrecto", "Respuesta errónea. Pierdes tu turno.", Alert.AlertType.ERROR);
            juego.cambiarTurno(); // Cambiar el turno
            actualizarTurnoGUI();

            // Iniciar una nueva pregunta inmediatamente
            generarNuevaPregunta();
            // Las casillas siguen deshabilitadas hasta que el nuevo jugador acierte
            habilitarBotonesOpcion(true);
        }
    }

    /**
     * Maneja el evento de click en las casillas del tablero.
     */
    private void handleCasillaClick(int fila, int col) {
        if (respuestaCorrectaRequerida) {
            mostrarMensaje("Espera", "Primero debes responder la pregunta correctamente.", Alert.AlertType.WARNING);
            return;
        }

        if (juego.hacerJugada(fila, col)) {
            // Jugada exitosa
            casillas[fila][col].setText(String.valueOf(juego.getTurnoActual().getSimbolo()));
            casillas[fila][col].setDisable(true);

            if (juego.verificarVictoria()) {
                // Victoria
                actualizarPuntuacionGUI();
                mostrarMensaje("¡Victoria!", juego.getTurnoActual().getNombre() + " ha ganado la ronda.", Alert.AlertType.CONFIRMATION);
                habilitarCasillas(false); // Deshabilitar tablero

                // Iniciar nueva ronda después de la victoria
                iniciarRonda();
            } else if (juego.verificarEmpate()) {
                // Empate
                mostrarMensaje("Empate", "El tablero está lleno. Es un empate.", Alert.AlertType.INFORMATION);
                habilitarCasillas(false); // Deshabilitar tablero

                // Iniciar nueva ronda después del empate
                iniciarRonda();
            } else {
                // Continuar juego
                juego.cambiarTurno();
                actualizarTurnoGUI();

                // Generar nueva pregunta para el siguiente jugador
                generarNuevaPregunta();
                habilitarCasillas(false); // Bloquear jugada hasta nueva respuesta correcta
                habilitarBotonesOpcion(true);
            }
        } else {
            // Jugada inválida (casilla ya ocupada)
            mostrarMensaje("Inválido", "Esa casilla ya está ocupada. Selecciona otra.", Alert.AlertType.WARNING);
        }
    }

    // --- Métodos de Utilidad ---

    /**
     * Habilita o deshabilita todos los botones de casillas.
     */
    private void habilitarCasillas(boolean habilitar) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Si se va a habilitar, solo habilitar las casillas vacías
                if (habilitar) {
                    if (juego.getTablero()[i][j] == ' ') {
                        casillas[i][j].setDisable(false);
                    }
                } else {
                    casillas[i][j].setDisable(true);
                }
            }
        }
    }

    /**
     * Habilita o deshabilita los botones de opción de respuesta.
     */
    private void habilitarBotonesOpcion(boolean habilitar) {
        btnOpcion1.setDisable(!habilitar);
        btnOpcion2.setDisable(!habilitar);
        btnOpcion3.setDisable(!habilitar);
        btnOpcion4.setDisable(!habilitar);
    }

    /**
     * Actualiza el marcador en la GUI.
     */
    private void actualizarPuntuacionGUI() {
        Jugador j1 = juego.getJugadores()[0];
        Jugador j2 = juego.getJugadores()[1];

        lblPuntuacionX.setText(j1.getNombre() + " (X): " + j1.getPuntuacion());
        lblPuntuacionO.setText(j2.getNombre() + " (O): " + j2.getPuntuacion());
    }

    /**
     * Actualiza el indicador de turno en la GUI.
     */
    private void actualizarTurnoGUI() {
        lblTurno.setText(juego.getTurnoActual().getNombre() + " (" + juego.getTurnoActual().getSimbolo() + ")");
    }

    /**
     * Limpia la visualización del tablero en la GUI.
     */
    private void reiniciarTableroGUI() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                casillas[i][j].setText(" ");
                // No se necesita deshabilitar aquí, ya que iniciarRonda llama a habilitarCasillas(false)
            }
        }
    }

    /**
     * Vuelve a la pantalla principal.
     */
    @FXML
    private void handleVolverMenuPrincipal(ActionEvent event) {
        // Se asume que btnMenuPrincipal es el botón que dispara la acción
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Carga la escena de menú principal
        TrikiMasterApp.cambiarEscena("/main/resources/MenuPrincipal.fxml", stage);
    }

    private void mostrarMensaje(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}