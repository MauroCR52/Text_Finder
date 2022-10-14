module com.example.text_finder {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.text_finder to javafx.fxml;
    exports com.example.text_finder;
}