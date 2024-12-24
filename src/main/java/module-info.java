module com.example.xogame {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.logging;
    
    
    exports com.example.xogame;
    opens com.example.xogame to javafx.fxml;
    exports com.example.shared;
    opens com.example.shared to javafx.fxml;
    exports com.example.xogame.controller;
    opens com.example.xogame.controller to javafx.fxml;
    exports com.example.xogame.containers;
    opens com.example.xogame.containers to javafx.fxml;
    exports com.example.xogame.controls;
    opens com.example.xogame.controls to javafx.fxml;
    exports com.example.xogame.data.match;
    opens com.example.xogame.data.match to javafx.fxml, com.google.gson;
    exports com.example.xogame.data.account;
    opens com.example.xogame.data.account to javafx.fxml, com.google.gson;
    exports com.example.xogame.notification;
    opens com.example.xogame.notification to javafx.fxml;
    exports com.example.xogame.computer;
    opens com.example.xogame.computer to javafx.fxml;
}