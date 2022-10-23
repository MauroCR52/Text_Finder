package com.example.text_finder;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.spire.doc.*;
import java.io.*;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;


import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        //this.leerDocx();
    }
    public static void leerTxt() {
        String ArchivoDoc = "C:\\Users\\Alvaro Duarte\\Documents\\GitHub\\Text_Finder\\Prueba.txt";
        String FieldDelimiter = " ";
        BufferedReader lector;
        Tree<String> bst = new BinaryTree<>();
        try {
            lector = new BufferedReader(new FileReader(ArchivoDoc));
            String linea;
            String textoT="";
            int i = 0;
            while ((linea = lector.readLine()) != null) { //Sirve para que la lectura se haga hasta que la línea sea nula.
                textoT+=linea;
                textoT+=" ";
            }
            String[] celdas = textoT.split(FieldDelimiter, -1);
            while (i != celdas.length) {
                if(celdas[i]!="") {
                    String posicion = String.valueOf(textoT.indexOf(celdas[i]));
                    bst.insert(celdas[i]+"¬"+posicion);
                }
                i += 1;
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
            String textoT="";
            String FieldDelimiter = " ";
            int i =0;
            for (XWPFParagraph paragraph : lParrafos) {
                String temp = (paragraph.getText());
                textoT +=temp;
                textoT+=" ";
            }
            String[] celdas = textoT.split(FieldDelimiter, -1);
            while (i != celdas.length) {
                if(celdas[i]!="") {
                    String posicion = String.valueOf(textoT.indexOf(celdas[i]));
                    bst.insert(celdas[i]+"¬"+posicion);
                }
                i += 1;
            }
            bst.traverse();
            bst.Search("Hola");
        } catch (IOException e) {
            System.setProperty("log4j.configurationFile", "./path_to_the_log4j2_config_file/log4j2.xml");
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    //Créditos para https://www.youtube.com/watch?v=CCgcMGdKurw

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
                bst.insert(cad3[z]+"¬"+posicion);
            }
            z += 1;
        }
        System.out.println(textoT);
        bst.traverse();
    }
    //Créditos para https://www.tutorialspoint.com/how-to-read-data-from-pdf-file-and-display-on-console-in-java#:~:text=Load%20an%20existing%20PDF%20document,method%20of%20the%20PDFTextStripper%20class.
    public static void main(String[] args) throws IOException {
        LectorDocs();
        System.out.println("Lista sin ordenar");
        leer_biblio();
        System.out.println("\n");
        Biblioteca.biblioteca.ordenar_fecha();
        System.out.println("Ordenar por fecha");
        leer_biblio();
        System.out.println("\n");
        Nodo_Biblioteca n = Biblioteca.biblioteca.head;
        while (n.next != null)
            n = n.next;
        Biblioteca.biblioteca.ordenar_nombre(Biblioteca.biblioteca.head, n);
        System.out.println("Ordenar por nombre");
        leer_biblio();

        launch();
    }

    static Integer[] lista_radix;
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
                        while ((line = br.readLine()) != null) {                    /*Splits each line into words*/
                            String words[] = line.split(" ");                    /*Counts each word*/
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
                        String cad[] = s.split("\\r\\n", -1);
                        int i = 0;
                        int z = 0;
                        Tree<String> bst = new BinaryTree<>();
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
                                bst.insert(cad3[z]+"¬"+posicion);
                            }
                            z += 1;
                            cont += 1;
                        }
                        document.close();

                    }
                }
                lista.add(cont);
                SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHH");
                Documento documento = new Documento(file.getName(), file.getPath(), cont, Integer.parseInt(sdf.format(file.lastModified())));
                Biblioteca.biblioteca.InsertarDocumento(documento);
            }
        lista_radix = new Integer[lista.size()];
        lista_radix = lista.toArray(lista_radix);

        System.out.println(Arrays.toString(lista_radix));
    }
    public static void leer_biblio(){
        Nodo_Biblioteca actual = Biblioteca.biblioteca.head;
        while (actual != null){
            System.out.println(actual.getData().getNombre() + " "+ actual.getData().getFecha() + " " +actual.getData().getTamano());
            actual = actual.next;
        }
    }
}