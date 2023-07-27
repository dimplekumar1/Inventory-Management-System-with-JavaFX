module com.example.software_1_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.software_1_project to javafx.fxml;
    exports com.example.software_1_project;
}