package org.example.informeapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {
    private static Connection conexion;

    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/informeapp/main.fxml"));
        Parent raiz = loader.load();

        Scene escena = new Scene(raiz, 800, 600);
        escena.getStylesheets().add(getClass().getResource("/org/example/informeapp/estilos.css").toExternalForm());

        escenarioPrincipal.setTitle("Generador de Informes");
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.setOnCloseRequest(event -> cerrarConexion());
        escenarioPrincipal.show();
    }

    public static void main(String[] args) {
        conectarBaseDatos();
        launch(args);
    }

    private static void conectarBaseDatos() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection("jdbc:sqlite:chinook.db");
                System.out.println("Conexión establecida con SQLite.");
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        System.exit(0);
    }

    public static Connection getConexion() {
        conectarBaseDatos();
        return conexion;
    }
}