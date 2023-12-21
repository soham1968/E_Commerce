module com.example.major_pro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.E_commerce to javafx.fxml;
    exports com.example.E_commerce;
}