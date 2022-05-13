module com.darkforest.darkforest {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.darkforest.darkforest to javafx.fxml;
    exports com.darkforest.darkforest;
    exports com.darkforest.darkforest.Controladores;
    opens com.darkforest.darkforest.Controladores to javafx.fxml;
}