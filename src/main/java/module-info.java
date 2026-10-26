module edu.hoteluq {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.hoteluq.app to javafx.fxml;
    exports edu.hoteluq.app;
    exports edu.hoteluq.controller;
    opens edu.hoteluq.controller to javafx.fxml;

}