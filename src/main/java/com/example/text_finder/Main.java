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
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
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
        this.leerDocx();
    }
    public static void leerTxt() {
        String ArchivoDoc = "C:\\Users\\Alvaro Duarte\\Documents\\GitHub\\Text_Finder\\Prueba.txt";
        String FieldDelimiter = " ";
        BufferedReader lector;
        Tree<String> bst = new BinaryTree<>();
        int indicador =0;
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
                    bst.insert(celdas[i]+"¬"+posicion+"¬"+String.valueOf(indicador));
                    indicador+=1;
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
            Tree<String> bst = new AVLTree<>();
            String textoT="";
            String FieldDelimiter = " ";
            int i =0;
            int indicador =0;
            for (XWPFParagraph paragraph : lParrafos) {
                String temp = (paragraph.getText());
                textoT +=temp;
                textoT+=" ";
            }
            String[] celdas = textoT.split(FieldDelimiter, -1);
            while (i != celdas.length) {
                if(celdas[i]!="") {
                    String posicion = String.valueOf(textoT.indexOf(celdas[i]));
                    bst.insert(celdas[i]+"¬"+posicion+"¬"+String.valueOf(indicador));
                    indicador+=1;
                }
                i += 1;
            }
            bst.Search("documento");
            System.out.println(bst.getComparaciones());
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
        int indicador=0;
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
                bst.insert(cad3[z]+"¬"+posicion+"¬"+String.valueOf(indicador));
                indicador+=1;
            }
            z += 1;
        }
        System.out.println(textoT);
        bst.traverse();
    }
    //Créditos para https://www.tutorialspoint.com/how-to-read-data-from-pdf-file-and-display-on-console-in-java#:~:text=Load%20an%20existing%20PDF%20document,method%20of%20the%20PDFTextStripper%20class.
    public void modDocx(String oldWord, String newWord) throws IOException {
    int count = 0;
    XWPFDocument document = new XWPFDocument();
    XWPFDocument docx = new XWPFDocument(new FileInputStream("Prueba.docx"));
    XWPFWordExtractor we = new XWPFWordExtractor(docx);
    String text = we.getText() ;
        if(text.contains(oldWord)){
        text = text.replace(oldWord, newWord);
        System.out.println(text);
    }
    char[] c = text.toCharArray();
        for(int i= 0; i < c.length;i++){

        if(c[i] == '\n'){
            count ++;
        }
    }
        System.out.println(c[0]);
    StringTokenizer st = new StringTokenizer(text,"\n");

    XWPFParagraph para = document.createParagraph();
        para.setAlignment(ParagraphAlignment.CENTER);
    XWPFRun run = para.createRun();
        run.setBold(true);
        run.setFontSize(36);
        run.setText("Apache POI works well!");

    List<XWPFParagraph>paragraphs = new ArrayList<XWPFParagraph>();
    List<XWPFRun>runs = new ArrayList<XWPFRun>();
    int k = 0;
        for(k=0;k<count+1;k++){
        paragraphs.add(document.createParagraph());
    }
    k=0;
        while(st.hasMoreElements()){
        paragraphs.get(k).setAlignment(ParagraphAlignment.LEFT);
        paragraphs.get(k).setSpacingAfter(0);
        paragraphs.get(k).setSpacingBefore(0);
        run = paragraphs.get(k).createRun();
        run.setText(st.nextElement().toString());
        k++;
    }

        document.write(new FileOutputStream("Prueba.docx"));
}

    public static void main(String[] args) {
        launch();
    }
}