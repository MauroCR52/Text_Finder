package com.example.text_finder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        Tree<Integer> bst = new AVLTree<>();
        bst.insert(30).insert(25).insert(60).insert(55).insert(50).insert(70);
        bst.delete(60);
        bst.traverse();
    }

    public static void main(String[] args) {
        launch();
    }
}