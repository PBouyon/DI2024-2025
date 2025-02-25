module org.example.informeapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires jasperreports;
    requires java.sql;


    opens org.example.informeapp to javafx.fxml;
    exports org.example.informeapp;
}