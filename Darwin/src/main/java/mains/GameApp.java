package mains;

import visualization.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Objects;
public class GameApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("menu.fxml"));
        GridPane viewRoot = loader.load();
        MenuVisualizator presenter = loader.getController();

        stage.setTitle("Game Menu");
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.png")));
        stage.getIcons().add(icon);
        stage.setMaximized(true);
        stage.setScene(new Scene(viewRoot));
        stage.show();
    }
}