package com.example.kurs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 600);
        stage.setTitle("Heating System");
        stage.setScene(scene);
        stage.setMinWidth(750);
        stage.setMaxWidth(750);
        stage.setMinHeight(600);
        stage.setMaxHeight(600);

        HelloController controller = fxmlLoader.getController();
        Modeling modeling = new Modeling();
        controller.setModeling(modeling);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}