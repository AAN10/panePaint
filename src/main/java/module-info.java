module com.example.objoblig {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.objoblig to javafx.fxml;
    exports com.example.objoblig;
}