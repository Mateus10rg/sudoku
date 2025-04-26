module com.example.jogosudoku {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.jogosudoku to javafx.fxml;
    exports com.example.jogosudoku;
}