module com.example.text_finder {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires Spire.Doc;
    requires java.logging;
    requires org.apache.pdfbox;
    requires org.apache.poi.ooxml;
    requires java.desktop;

    opens com.example.text_finder to javafx.fxml;
    exports com.example.text_finder;
}