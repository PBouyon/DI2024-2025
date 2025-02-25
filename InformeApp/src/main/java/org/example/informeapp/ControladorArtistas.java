package org.example.informeapp;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ControladorArtistas {


    @FXML
    private ListView<String> listaArtistas;

    @FXML
    private void initialize() {
        cargarArtistas();
    }

    private void cargarArtistas() {
        try {
            Connection conexion = Main.getConexion();
            PreparedStatement consulta = conexion.prepareStatement("SELECT Name FROM artists");
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                listaArtistas.getItems().add(resultado.getString("Name"));
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar artistas: " + e.getMessage());
        }
    }

    @FXML
    private void generarInformeArtista() {
        String artistaSeleccionado = listaArtistas.getSelectionModel().getSelectedItem();
        if (artistaSeleccionado == null) {
            System.err.println("Debe seleccionar un artista.");
            return;
        }

        try {
            Connection conexion = Main.getConexion();
            JasperReport reporte = JasperCompileManager.compileReport(getClass().getResourceAsStream("/org/example/informeapp/informe_artistas.jrxml"));

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ARTIST_NAME", artistaSeleccionado);

            JasperPrint print = JasperFillManager.fillReport(reporte, parametros, conexion);
            JasperViewer.viewReport(print, false);
        } catch (JRException e) {
            System.err.println("Error al generar el informe de artista: " + e.getMessage());
        }
    }

}
