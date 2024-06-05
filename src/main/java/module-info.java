module com.example.analizadorlexico {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.analizadorlexico to javafx.fxml;
    exports com.example.analizadorlexico;
}