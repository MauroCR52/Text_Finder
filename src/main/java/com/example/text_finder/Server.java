package com.example.text_finder;

import com.spire.doc.Document;
import javafx.scene.layout.VBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private ServerSocket serverSocket;
    private static Socket socket;
    private static BufferedReader bufferedReader;
    private static BufferedWriter bufferedWriter;

    public static boolean encontrado = false;

    private static String messageFromClient;

    public static String mensaje;

    //Tree<String> bst = new AVLTree<>();

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

    public static void sendMessageToClient(String messageToClient) {
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

    public void receiveMessageFromClient(VBox vBox) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected())
                    try {
                        messageFromClient = bufferedReader.readLine();
                        System.out.println(messageFromClient);
                        Nodo_Biblioteca actual = Biblioteca.biblioteca.head;
                        while (actual != null){
                            if (actual.getData().getTipo().equals("docx")){
                                leerDocx(actual.getData().getDireccion());
                                if(encontrado){
                                    sendMessageToClient(actual.getData().getNombre()+ "  "+ "AVL: "+AVLTree.comparaciones +"  " + "Bin: "+ BinaryTree.comparaciones + "  "+ AVLTree.textResult);
                                }
                            }
                            else if (actual.getData().getTipo().equals("txt")){
                                leerTxt(actual.getData().getDireccion());
                                if(encontrado){
                                    sendMessageToClient(actual.getData().getNombre()+ "  "+ "AVL: "+AVLTree.comparaciones +"  " + "Bin: "+ BinaryTree.comparaciones + "  "+ AVLTree.textResult);
                                }
                            }
                            else {
                                leerPDF(actual.getData().getDireccion());
                                if(encontrado){
                                    sendMessageToClient(actual.getData().getNombre()+ "  "+ "AVL: "+AVLTree.comparaciones +"  " + "Bin: "+ BinaryTree.comparaciones + "  "+ AVLTree.textResult);
                                }
                            }
                            actual = actual.next;
                        }

                        Controller.addLabel(messageFromClient, vBox);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error recibiendo mensaje del cliente");
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }
            }
        }).start();
    }

    public static void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null)
                bufferedReader.close();
            if (bufferedWriter != null) bufferedWriter.close();
            if (socket != null)
                socket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void leerTxt(String archivo) {
        String ArchivoDoc = archivo;
        String FieldDelimiter = " ";
        BufferedReader lector;
        Tree<String> bst = new AVLTree<>();
        Tree<String> bin = new BinaryTree<>();
        int indicador = 0;
        try {
            lector = new BufferedReader(new FileReader(ArchivoDoc));
            String linea;
            String textoT = "";
            int i = 0;
            while ((linea = lector.readLine()) != null) { //Sirve para que la lectura se haga hasta que la línea sea nula.
                textoT += linea;
                textoT += " ";
            }
            String[] palabras = textoT.split(FieldDelimiter, -1);
            while (i != palabras.length) {
                if (palabras[i] != "") {
                    String posicion = String.valueOf(textoT.indexOf(palabras[i]));
                    if (palabras.length - i >=4) {
                        bst.insert(palabras[i] + "~" + palabras[i + 1] + "~" + palabras[i + 2] + "~" + palabras[i + 3] + "¬" + posicion + "¬" + String.valueOf(indicador));
                        bin.insert(palabras[i] + "~" + palabras[i + 1] + "~" + palabras[i + 2] + "~" + palabras[i + 3] + "¬" + posicion + "¬" + String.valueOf(indicador));
                        indicador += 1;
                    } else if (palabras.length - i == 3) {
                        bst.insert(palabras[i] + "~" + palabras[i + 1] + "~" + palabras[i + 2] + "¬" + posicion + "¬" + indicador);
                        bin.insert(palabras[i] + "~" + palabras[i + 1] + "~" + palabras[i + 2] + "¬" + posicion + "¬" + indicador);
                    } else if (palabras.length - i == 2) {
                        bst.insert(palabras[i] + "~" + palabras[i + 1] + "¬" + posicion + "¬" + indicador);
                        bin.insert(palabras[i] + "~" + palabras[i + 1] + "¬" + posicion + "¬" + indicador);
                    } else {
                        bst.insert(palabras[i] + "¬" + posicion + "¬" + indicador);
                        bin.insert(palabras[i] + "¬" + posicion + "¬" + indicador);
                    }

                }
                i += 1;
                indicador += 1;
            }
            try {
                bin.Search(messageFromClient);
                bst.Search(messageFromClient);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void leerDocx(String archivo) {
        try {
            FileInputStream file = new FileInputStream(archivo);
            XWPFDocument docx = new XWPFDocument(file);
            List<XWPFParagraph> lParrafos = docx.getParagraphs();
            Tree<String> bst = new AVLTree<>();
            Tree<String> bin = new BinaryTree<>();
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
                if (celdas[i] != "") {
                    String posicion = String.valueOf(textoT.indexOf(celdas[i]));
                    if (celdas.length - i >= 4) {
                        bst.insert(celdas[i] + "~" + celdas[i + 1] + "~" + celdas[i + 2] + "~" + celdas[i + 3] + "¬" + posicion + "¬" + String.valueOf(indicador));
                        bin.insert(celdas[i] + "~" + celdas[i + 1] + "~" + celdas[i + 2] + "~" + celdas[i + 3] + "¬" + posicion + "¬" + String.valueOf(indicador));
                        indicador += 1;
                    } else if (celdas.length - i == 3) {
                        bst.insert(celdas[i] + "~" + celdas[i + 1] + "~" + celdas[i + 2] + "¬" + posicion + "¬" + indicador);
                        bin.insert(celdas[i] + "~" + celdas[i + 1] + "~" + celdas[i + 2] + "¬" + posicion + "¬" + indicador);
                    } else if (celdas.length - i == 2) {
                        bst.insert(celdas[i] + "~" + celdas[i + 1] + "¬" + posicion + "¬" + indicador);
                        bin.insert(celdas[i] + "~" + celdas[i + 1] + "¬" + posicion + "¬" + indicador);
                    } else {
                        bst.insert(celdas[i] + "¬" + posicion + "¬" + indicador);
                        bin.insert(celdas[i] + "¬" + posicion + "¬" + indicador);
                    }
                }
                i += 1;
            }
            try {
                bin.Search(messageFromClient);
                System.out.println(bin.getComparaciones());
                bst.Search(messageFromClient);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            //    bin.Search(messageFromClient);
            System.out.println("AVL: "+bst.getComparaciones() + " "+ "Bin: "+ bin.getComparaciones());
        } catch (IOException e) {
            System.setProperty("log4j.configurationFile", "./path_to_the_log4j2_config_file/log4j2.xml");
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    //Créditos para https://www.youtube.com/watch?v=CCgcMGdKurw

    public static void leerPDF(String archivo) throws IOException {
        File file = new File(archivo);
        PDDocument document = PDDocument.load(file);
        //Instantiate PDFTextStripper class
        PDFTextStripper pdfStripper = new PDFTextStripper();
        //Retrieving text from PDF document
        String s = pdfStripper.getText(document);
        String cad[] = s.split("\\r\\n", -1);
        int indicador=0;
        int i = 0;
        int z = 0;
        Tree<String> bst = new AVLTree<>();
        Tree<String> bin = new BinaryTree<>();
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
                if (cad3.length - z >=4) {
                    bst.insert(cad3[z] + "~" + cad3[z+ 1] + "~" + cad3[z + 2] + "~" + cad3[z + 3] + "¬" + posicion + "¬" + String.valueOf(indicador));
                    bin.insert(cad3[z] + "~" + cad3[z+ 1] + "~" + cad3[z + 2] + "~" + cad3[z + 3] + "¬" + posicion + "¬" + String.valueOf(indicador));
                } else if (cad3.length - z == 3) {
                    bst.insert(cad3[z] + "~" + cad3[z + 1] + "~" + cad3[z + 2] + "¬" + posicion + "¬" + indicador);
                    bin.insert(cad3[z] + "~" + cad3[z + 1] + "~" + cad3[z + 2] + "¬" + posicion + "¬" + indicador);
                } else if (cad3.length - z == 2) {
                    bst.insert(cad3[z] + "~" + cad3[z + 1] + "¬" + posicion + "¬" + indicador);
                    bin.insert(cad3[z] + "~" + cad3[z + 1] + "¬" + posicion + "¬" + indicador);
                } else {
                    bst.insert(cad3[z] + "¬" + posicion + "¬" + indicador);
                    bin.insert(cad3[z] + "¬" + posicion + "¬" + indicador);
                }
            }
            z += 1;
            indicador += 1;
        }
        try {
            bin.Search(messageFromClient);
            bst.Search(messageFromClient);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        document.close();
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
        lista_radix = new Integer[lista.size()];
        lista_radix = lista.toArray(lista_radix);

        System.out.println(Arrays.toString(lista_radix));
    }
}
