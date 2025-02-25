package org.example.informeapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;

public class Controlador {


    @FXML
    private Button btnCerrar, btnInformeClientes, btnInformeArtistas;

    @FXML
    private void generarInformeClientes() {

        try {
            Connection conexion = Main.getConexion();
            JasperReport reporte = JasperCompileManager.compileReport(getClass().getResourceAsStream("/org/example/informeapp/informe_clientes.jrxml"));
            JasperPrint print = JasperFillManager.fillReport(reporte, new HashMap<>(), conexion);
            JasperViewer.viewReport(print, false);
        } catch (JRException e) {
            System.err.println("Error al generar el informe: " + e.getMessage());
        }
    }

    @FXML
    private void mostrarSelectorArtistas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/informeapp/selector_artistas.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Seleccionar Artista");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al abrir la ventana de selección de artistas: " + e.getMessage());
        }
    }

    @FXML
    private void salirAplicacion() {
        Main.cerrarConexion();
    }

}