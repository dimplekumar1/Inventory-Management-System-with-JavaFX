package com.example.software_1_project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


/** This class adds Part data.
 * @author Dimple Kumar
 */
public class AddPartsController implements Initializable {

    //creating stage and scene
    @FXML
    private Stage stage;
    private Scene scene;

    //creating text fields for the part table
    @FXML
    public TextField partName;
    @FXML
    public TextField partStock;
    @FXML
    public TextField partPrice;
    @FXML
    public TextField partMaxQty;
    @FXML
    public TextField partMinQty;
    @FXML
    public TextField partMachineOrCompany;

    //this text below will be changed based on which radiobutton is clicked
    @FXML
    public Text machineIdOrCompanyName;

    //creating radio buttons
    @FXML
    public RadioButton partInHouseRadio;
    @FXML
    public RadioButton partOutSourcedRadio;


    /** This method returns the user back to the "Main Form" scene.
     * @param event is the action event which is executed when the user clicks the "Cancel" button in the "Add Part Form" scene.
     */
    public void backToMainForm(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main_form.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Inventory Management System");
        stage.show();
    }


    /** This method sets the title of the Text Field if "Out Sourced" radiobutton is clicked.
     * @param event is the action event which is executed when the user selects the "Out Sourced" radiobutton in the "Add Part Form" scene.
     */
    public void OnOutsourced(ActionEvent event) {
        machineIdOrCompanyName.setText("Company Name");
    }


    /** This method sets the title of the Text Field if "In House" radiobutton is clicked.
     * @param event is the action event which is executed when the user selects the "In House" radiobutton in the "Add Part Form" scene.
     */
    public void OnInHouse(ActionEvent event) {
        machineIdOrCompanyName.setText("Machine Id");
    }


    /** This method adds a new part based on the user added data.
     * @param event is the action event which is executed when the user clicks the "Save" button in the "Add Part Form" scene.
     * Once the user inputs the valid data and clicks the "Save" button, the scene will be switched back to the "Main Form" scene, and the Part table will be populated with the added part information.
     * Error dialogue box will appear for all invalid data entries.
     */
    public void OnSaveAddPart(ActionEvent event) throws IOException {

        //this if statement checks if the text fields are empty. If they are, displays an error dialogue box
        if ((partName.getText().isEmpty()) || (partPrice.getText().isEmpty()) || (partStock.getText().isEmpty()) || (partMinQty.getText().isEmpty()) || (partMaxQty.getText().isEmpty()) || (partMachineOrCompany.getText().isEmpty())) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Adding a Part");
            alert.setHeaderText("One or More Text Fields are Empty");
            alert.setContentText("Please complete all text fields to add a part!");
            alert.showAndWait();
        }
        else {
            try {
                //this if statement checks if the min qty is greater than equal to max qty. If it is, displays an error dialogue box
                if ((Integer.parseInt(partMinQty.getText())) >= (Integer.parseInt(partMaxQty.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Adding a Part");
                    alert.setHeaderText("Error in the min. and max. quantities");
                    alert.setContentText("Min. should be less than Max.!");
                    alert.showAndWait();
                    return;
                }
                //this if statement checks if the inventory is within the min and max. If not, displays an error dialogue box
                else if ((Integer.parseInt(partStock.getText())) < (Integer.parseInt(partMinQty.getText())) || (Integer.parseInt(partStock.getText())) > (Integer.parseInt(partMaxQty.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Adding a Part");
                    alert.setHeaderText("Error in the Inventory");
                    alert.setContentText("The Inventory should be between Min. and Max.!");
                    alert.showAndWait();
                    return;
                }
                //this if statement adds a part data if InHouse radio button is selected
                else if (partInHouseRadio.isSelected()) {
                    InHouse inHousePart = new InHouse(Inventory.partIndex, partName.getText(), Double.parseDouble(partPrice.getText()), Integer.parseInt(partStock.getText()), Integer.parseInt(partMinQty.getText()), Integer.parseInt(partMaxQty.getText()), Integer.parseInt(partMachineOrCompany.getText()));
                    Inventory.addPart(inHousePart);
                }
                //this if statement adds a part data if OutSourced radio button is selected
                else if (partOutSourcedRadio.isSelected()) {
                    Outsourced outSourcedPart = new Outsourced(Inventory.partIndex, partName.getText(), Double.parseDouble(partPrice.getText()), Integer.parseInt(partStock.getText()), Integer.parseInt(partMinQty.getText()), Integer.parseInt(partMaxQty.getText()), partMachineOrCompany.getText());
                    Inventory.addPart(outSourcedPart);
                }

                //returns back to main form scene
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main_form.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Inventory Management System");
                stage.show();

            }
            catch (NumberFormatException e) {
                //displays error dialogue box if the user typed unexpected data format
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Adding a Part");
                alert.setHeaderText("One or More Text Fields Contain an Invalid Input");
                alert.setContentText("Please input valid data in each text field to add a part!");
                alert.showAndWait();
            }
        }
    }


    /** This method initializes the "AddPartsController" class. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}



