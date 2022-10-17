module com.example.text_finder {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;


    opens com.example.text_finder to javafx.fxml;
    exports com.example.text_finder;
}