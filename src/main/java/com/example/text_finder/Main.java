package com.example.text_finder;

import com.spire.doc.Document;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.spire.doc.*;

import java.io.*;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.util.logging.Level;
import java.util.logging.Logger;

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

/**
 * Clase main que permite iniciar la aplicacion
 */

public class Main extends Application {
    /**
     * Metodo que carga el archivo fxml y lo muestra
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("server.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Server");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método que incia la aplicacion
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        LectorDocs();
        launch();
    }

    /**
     * Metodo que lee la carpeta de documentos y los inserta en la biblioteca
     * @throws IOException
     */
    public static void LectorDocs() throws IOException {
        List<Integer> lista = new ArrayList<Integer>();
        File folder = new File("src/main/java/Documentos");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles)
            if (file.isFile()) {
                int cont = 0;
                String filename = file.toString();
                int index = filename.lastIndexOf(".");
                if (index > 0) {
                    String extension = filename.substring(index + 1);
                    if (extension.equals("txt")) {
                        String line;
                        FileReader fileReader = new FileReader(file.getPath());
                        BufferedReader br = new BufferedReader(fileReader);
                        while ((line = br.readLine()) != null) {
                                String words[] = line.split(" ");
                                cont += words.length;
                        }
                        br.close();
                    } else if (extension.equals("docx")) {
                        Document document = new Document();
                        document.loadFromFile(file.getPath());
                        cont = document.getBuiltinDocumentProperties().getWordCount();
                    }
                    else {
                        PDDocument document = PDDocument.load(file);
                        //Instantiate PDFTextStripper class
                        PDFTextStripper pdfStripper = new PDFTextStripper();
                        //Retrieving text from PDF document
                        String s = pdfStripper.getText(document);
                        String cad[] = s.split("\r\n", -1);
                        int i = 0;
                        int z = 0;
                        StringBuilder textoT = new StringBuilder();
                        while (i != cad.length) {
                            String cad2[] = cad[i].split(" ", -1);
                            int j = 0;
                            while (j != cad2.length) {
                                if (cad2[j] != "") {
                                    textoT.append(cad2[j]);
                                    textoT.append(" ");
                                }
                                j += 1;
                            }
                            i += 1;
                        }
                        String[] cad3 = textoT.toString().split(" ", -1);
                        while (z != cad3.length) {
                            if (cad3[z] != "") {
                                String posicion = String.valueOf(textoT.indexOf(cad3[z]));
                            }
                            z += 1;
                            cont += 1;
                        }
                        document.close();

                    }
                }
                lista.add(cont);
                SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHH");
                Documento documento = new Documento(file.getName(), file.getPath(), cont, Integer.parseInt(sdf.format(file.lastModified())), filename.substring(index + 1));
                Biblioteca.biblioteca.InsertarDocumento(documento);
            }
    }
}