package main.java.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal que inicializa la aplicación JavaFX TrikiMaster.
 * Carga la escena inicial (Menu Principal).
 */
public class TrikiMasterApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carga el archivo FXML del Menu Principal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/MenuPrincipal.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("TrikiMaster: Triki Matemático");
        primaryStage.setScene(new Scene(root, 800, 600)); // Tamaño recomendado
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Utilidad para cambiar de escena desde cualquier controlador.
     * @param scenePath Ruta del archivo FXML de la nueva escena.
     * @param stage El Stage (ventana) actual.
     */
    public static void cambiarEscena(String scenePath, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(TrikiMasterApp.class.getResource(scenePath));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.sizeToScene(); // Ajusta el tamaño al FXML cargado
        } catch (Exception e) {
            System.err.println("Error al cargar la escena: " + scenePath);
            e.printStackTrace();
        }
    }
}