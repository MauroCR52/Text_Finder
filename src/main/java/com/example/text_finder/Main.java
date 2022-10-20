package com.example.text_finder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        /*
        Tree<Integer> bst = new AVLTree<>();
        bst.insert(30).insert(25).insert(60).insert(55).insert(50).insert(70);
        bst.delete(60);
        bst.traverse();

         */
        this.leerPDF();

    }

    public static void leerTxt() {
        String ArchivoDoc = "C:\\Users\\Alvaro Duarte\\Documents\\GitHub\\Text_Finder\\Prueba.docx";
        String FieldDelimiter = " ";
        BufferedReader lector;
        Tree<String> bst = new BinaryTree<>();
        try {
            lector = new BufferedReader(new FileReader(ArchivoDoc));
            String linea;
            while ((linea = lector.readLine()) != null) { //Sirve para que la lectura se haga hasta que la línea sea nula.
                String[] celdas = linea.split(FieldDelimiter, -1);
                int i = 0;
                System.out.println(celdas[0]);
                while (i != celdas.length) {
                    bst.insert(celdas[i]);
                    System.out.println(i);
                    System.out.println(celdas.length);
                    i += 1;
                }
                i = 0;
            }
            bst.traverse();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void leerDocx() {
        try {
            FileInputStream file = new FileInputStream("C:\\Users\\Alvaro Duarte\\Documents\\GitHub\\Text_Finder\\Prueba.docx");
            XWPFDocument docx = new XWPFDocument(file);
            List<XWPFParagraph> lParrafos = docx.getParagraphs();
            Tree<String> bst = new BinaryTree<>();
            for (XWPFParagraph paragraph : lParrafos) {
                String temp = (paragraph.getText());
                String[] palabras = temp.split(" ", -1);
                int i = 0;
                while (i != palabras.length) {
                    bst.insert(palabras[i]);
                    i += 1;
                }
                i = 0;
            }
            bst.traverse();
        } catch (IOException e) {
            System.setProperty("log4j.configurationFile", "./path_to_the_log4j2_config_file/log4j2.xml");
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
            ;
        }

    }

    public static void leerPDF() throws IOException {
        File file = new File("C:\\Users\\Alvaro Duarte\\Documents\\GitHub\\Text_Finder\\Prueba.pdf");
        PDDocument document = PDDocument.load(file);
        //Instantiate PDFTextStripper class
        PDFTextStripper pdfStripper = new PDFTextStripper();
        //Retrieving text from PDF document
        String s = pdfStripper.getText(document);
        String cad[] = s.split("\\r\\n", -1);
        int i = 0;
        int z = 0;
        Tree<String> bst = new BinaryTree<>();
        StringBuilder text = new StringBuilder();
        while (i != cad.length) {
            String cad2[] = cad[i].split(" ", -1);
            int j = 0;
            while (j != cad2.length) {
                if (cad2[j] != "") {
                    text.append(cad2[j]);
                    text.append("$#$");
                }
                j += 1;
            }
            i += 1;
        }
        String[] cad3 = text.toString().split("[$#$]", -1);
        while (z != cad3.length) {
            if (cad3[z] != "") {
                bst.insert(cad3[z]);
            }
            z += 1;
        }
        System.out.println(text);
        bst.traverse();
    }


    public static void main(String[] args) {
        launch();
    }
}