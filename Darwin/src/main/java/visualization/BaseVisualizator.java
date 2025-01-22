package visualization;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

import java.util.Set;

abstract public class BaseVisualizator {

    @FXML
    protected Pane rootPane;

    protected void showAlert(String header, String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    protected Set<Node> getClassElements(String className) {
        return rootPane.lookupAll("." + className);
    }
}