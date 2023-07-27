package com.example.software_1_project;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;


/** This class opens the "Main Form" scene, whenever the application is first started.
 * @author Dimple Kumar
 */
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main_form.fxml")));
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);

        stage.show();
    }


    /** This is the main method. */
    public static void main(String[] args) {
        launch();
    }
}