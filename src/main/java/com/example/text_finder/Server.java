package com.example.text_finder;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private static Socket socket;
    private static BufferedReader bufferedReader;
    private static BufferedWriter bufferedWriter;

    public Server(ServerSocket serverSocket) {
        try {
            this.serverSocket = serverSocket;
            this.socket = serverSocket.accept();
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            System.out.println("Error creando servidor");
            e.printStackTrace();
        }
    }
    public static void sendMessageToClient(String messageToClient){
        try {
            bufferedWriter.write(messageToClient);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error enviando mensaje al cliente");
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void receiveMessageFromClient(VBox vBox){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()){
                    try {
                        String messageFromClient = bufferedReader.readLine();
                        Controller.addLabel(messageFromClient, vBox);

                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error recibiendo mensaje del cliente");
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }
                }
            }
        }).start();
    }

    public static void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try {
            if (bufferedReader != null){
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
            if (socket != null){
                socket.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
