package com.example.text_finder;




import com.spire.doc.Document;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.Desktop;
import com.spire.doc.*;
import java.io.*;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;



import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("server.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Server");
        stage.setScene(scene);
        stage.show();

        /*
        String a= "soy,";
        int b= a.length()-1;
        //String s = "a,bdfd.gfg;djfda:dgfs?dkfjsd¿djfkldjs¡kj!-jfd_fjdjd,dfcb*fd";
        String s="a,";
        String[]str=s.split("[,.;:¿?¡!*]");
        for (int i=0;i<str.length;i++){
            System.out.println("Str["+i+"]:"+str[i]);
        }
         */
       // this.leerDocx();
        Server.sendMessageToClient("Bienvenido a Text Finder, escribe la palabra o frase que deseas buscar.");

    }

    public static void main(String[] args) throws IOException {
        Server.LectorDocs();
        System.out.println("Lista sin ordenar");
        Server.leer_biblio();
        System.out.println("\n");
        Biblioteca.biblioteca.ordenar_fecha();
        System.out.println("Ordenar por fecha");
        Server.leer_biblio();
        System.out.println("\n");
        Nodo_Biblioteca n = Biblioteca.biblioteca.head;
        while (n.next != null)
            n = n.next;
        Biblioteca.biblioteca.ordenar_nombre(Biblioteca.biblioteca.head, n);
        System.out.println("Ordenar por nombre");
        Server.leer_biblio();
        launch();
    }

}