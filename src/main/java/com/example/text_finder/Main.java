package com.example.text_finder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        Tree<Integer> bst = new AVLTree<>();
        bst.insert(30).insert(25).insert(60).insert(55).insert(50).insert(70).delete(60);
        bst.traverse();
    }

    public static void main(String[] args) throws IOException {
        LectorDocs();
        launch();
    }

    public static void LectorDocs() throws IOException {
        File folder = new File("src/main/java/Documentos");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles)
            if (file.isFile()) {
                String line;
                int cont = 0;
                FileReader fileReader = new FileReader(file.getPath());
                BufferedReader br = new BufferedReader(fileReader);
                while ((line = br.readLine()) != null) {                    /*Splits each line into words*/
                    String words[] = line.split(" ");                    /*Counts each word*/
                    cont += words.length;
                }
                br.close();
                SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHH");
                Documento documento = new Documento(file.getName(), file.getPath(), cont, Integer.parseInt(sdf.format(file.lastModified())));
                Biblioteca.biblioteca.InsertarDocumento(documento);
            }
    }
}