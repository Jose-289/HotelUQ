module edu.hoteluq {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens edu.hoteluq.app to javafx.fxml;
    exports edu.hoteluq.app;
    exports edu.hoteluq.controlador;
    opens edu.hoteluq.controlador to javafx.fxml;

}