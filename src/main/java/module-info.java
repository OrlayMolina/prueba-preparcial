module puntouno.preparcial {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens puntouno.preparcial to javafx.fxml;
    exports puntouno.preparcial;
    opens puntouno.preparcial.controller to javafx.fxml;
    exports puntouno.preparcial.controller;
    opens puntouno.preparcial.model to javafx.fxml;
    exports puntouno.preparcial.model;
    opens puntouno.preparcial.util to javafx.fxml;
    exports puntouno.preparcial.util;
}