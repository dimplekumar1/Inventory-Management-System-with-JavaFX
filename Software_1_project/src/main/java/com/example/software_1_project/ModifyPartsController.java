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


/** This class modifies part data.
 * @author Dimple Kumar
 */
public class ModifyPartsController implements Initializable {

    //creating stage and scene
    @FXML
    private Stage stage;
    private Scene scene;

    //creating text fields for the part table
    @FXML
    private TextField modifyPartId;
    @FXML
    private TextField modifyPartName;
    @FXML
    private TextField modifyPartStock;
    @FXML
    private TextField modifyPartPrice;
    @FXML
    private TextField modifyPartMinQty;
    @FXML
    private TextField modifyPartMaxQty;
    @FXML
    private TextField modifyPartMachineOrCompany;

    //this text below will be changed based on which radiobutton is clicked
    public Text machineIdOrCompanyName;

    //creating radio buttons
    @FXML
    private RadioButton partInHouseRadio;

    @FXML
    private RadioButton partOutsourceRadio;


    /** This method passes the selected part data from MainController to the ModifyPartsController.
     * @param part is the selected part from MainController.
     */
    public void partData(Part part) {
        //sets part data passed from MainController
        modifyPartId.setText(String.valueOf(part.getId()));
        modifyPartName.setText(part.getName());
        modifyPartStock.setText(String.valueOf(part.getStock()));
        modifyPartPrice.setText(String.valueOf(part.getPrice()));
        modifyPartMinQty.setText(String.valueOf(part.getMin()));
        modifyPartMaxQty.setText(String.valueOf(part.getMax()));

        //sets machine id or company name based on the radio button selected
        if(part instanceof InHouse) {
            partInHouseRadio.setSelected(true);
            modifyPartMachineOrCompany.setText(String.valueOf(((InHouse) part).getMachineId()));
            machineIdOrCompanyName.setText("Machine Id");
        }
        else {
            partOutsourceRadio.setSelected(true);
            modifyPartMachineOrCompany.setText(String.valueOf(((Outsourced) part).getCompanyName()));
            machineIdOrCompanyName.setText("Company Name");
        }
    }


    /** This method returns the user back to the "Main Form" scene.
     * @param event is the action event which is executed when the user clicks the "Cancel" button in the "Modify Part Form" scene.
     */
    public void backToMainForm(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main_form.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Inventory Management System");
        stage.show();
    }


    /** This method sets the title of the Text Field if "OutSourced" radiobutton is clicked.
     * @param event is the action event which is executed when the user selects the "Out Sourced" radiobutton in the "Modify Part Form" scene.
     */
    public void OnOutsourced(ActionEvent event) {
        machineIdOrCompanyName.setText("Company Name");
    }


    /** This method sets the title of the Text Field if "In House" radiobutton is clicked.
     * @param event is the action event which is executed when the user selects the "In House" radiobutton in the "Modify Part Form" scene.
     */
    public void OnInHouse(ActionEvent event) {
        machineIdOrCompanyName.setText("Machine Id");
    }


    /** This method saves the modified part based on the user added data.
     * @param event is the action event which is executed when the user clicks the "Save" button in the "Modify Part Form" scene.
     * Once the user inputs the valid data and clicks the "Save" button, the scene will be switched back to the "Main Form" scene, and the Part table will be populated with the modified part information.
     * Error dialogue box will appear for all invalid data entries.
     */
    public void OnSaveModifyPart(ActionEvent event) throws IOException {

        //this if statement checks if the text fields are empty. If they are, displays an error dialogue box
        if ((modifyPartName.getText().isEmpty()) || (modifyPartPrice.getText().isEmpty()) || (modifyPartStock.getText().isEmpty()) || (modifyPartMinQty.getText().isEmpty()) || (modifyPartMaxQty.getText().isEmpty()) || (modifyPartMachineOrCompany.getText().isEmpty())) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modifying a Part");
            alert.setHeaderText("One or More Text Fields are Empty");
            alert.setContentText("Please complete all text fields!");
            alert.showAndWait();
        }
        else {
            try {
                //this if statement checks if the min qty is greater than equal to max qty. If it is, displays an error dialogue box
                if ((Integer.parseInt(modifyPartMinQty.getText())) >= (Integer.parseInt(modifyPartMaxQty.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Modifying a Part");
                    alert.setHeaderText("Error in the min. and max. quantities");
                    alert.setContentText("Min. should be less than Max.!");
                    alert.showAndWait();
                    return;
                }
                //this if statement checks if the inventory is within the min and max. If not, displays an error dialogue box
                else if ((Integer.parseInt(modifyPartStock.getText())) < (Integer.parseInt(modifyPartMinQty.getText())) || (Integer.parseInt(modifyPartStock.getText())) > (Integer.parseInt(modifyPartMaxQty.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Modifying a Part");
                    alert.setHeaderText("Error in the Inventory");
                    alert.setContentText("The Inventory should be between Min. and Max.!");
                    alert.showAndWait();
                    return;
                }
                //this if statement adds a part data if InHouse radio button is selected
                else if (partInHouseRadio.isSelected()) {
                    InHouse inHousePart = new InHouse(Integer.parseInt(modifyPartId.getText()), modifyPartName.getText(), Double.parseDouble(modifyPartPrice.getText()), Integer.parseInt(modifyPartStock.getText()), Integer.parseInt(modifyPartMinQty.getText()), Integer.parseInt(modifyPartMaxQty.getText()), Integer.parseInt(modifyPartMachineOrCompany.getText()));
                    Inventory.updatePart(Integer.parseInt(modifyPartId.getText()), inHousePart);

                //this if statement adds a part data if OutSourced radio button is selected
                } else if (partOutsourceRadio.isSelected()) {
                    Outsourced outSourcedPart = new Outsourced(Integer.parseInt(modifyPartId.getText()), modifyPartName.getText(), Double.parseDouble(modifyPartPrice.getText()), Integer.parseInt(modifyPartStock.getText()), Integer.parseInt(modifyPartMinQty.getText()), Integer.parseInt(modifyPartMaxQty.getText()), modifyPartMachineOrCompany.getText());
                    Inventory.updatePart(Integer.parseInt(modifyPartId.getText()), outSourcedPart);
                }

                //returns back to main form scene
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main_form.fxml")));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Inventory Management System");
                stage.show();
            }
            catch (NumberFormatException e) {
                //displays error dialogue box if the user typed unexpected data format
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Modifying a Part");
                alert.setHeaderText("One or More Text Fields Contain an Invalid Input");
                alert.setContentText("Please input valid data in each text field!");
                alert.showAndWait();
            }
        }

    }


    /** This method initializes the "AddPartsController" class. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
