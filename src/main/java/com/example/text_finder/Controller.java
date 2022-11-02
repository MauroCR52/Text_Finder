package com.example.text_finder;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import lombok.Value;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase controller que lleva los controles necesarios para la ventana del server
 */
public class Controller implements Initializable {
    @FXML
    private ScrollPane ScrollMain;
    @FXML
    private VBox mensajeVBOX;
    private Server server;

    /**
     * metodo initialize que conecta el server a un puerto local
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            server = new Server(new ServerSocket(3421));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al crear servidor");
        }
        mensajeVBOX.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                ScrollMain.setVvalue((Double) newValue);
            }
        });
        server.receiveMessageFromClient(mensajeVBOX);
    }
}
