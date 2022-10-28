module com.example.text_finder {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires Spire.Doc;
    requires org.apache.poi.ooxml;
    requires java.logging;
    requires org.apache.pdfbox;


    opens com.example.text_finder to javafx.fxml;
    exports com.example.text_finder;
}