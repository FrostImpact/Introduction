module com.example.tech_assignment {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tech_assignment to javafx.fxml;
    exports com.example.tech_assignment;
}