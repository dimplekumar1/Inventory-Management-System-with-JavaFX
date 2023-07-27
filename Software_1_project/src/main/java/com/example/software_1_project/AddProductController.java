package com.example.software_1_project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;


/** This class adds Product data.
 * @author Dimple Kumar
 */
public class AddProductController implements Initializable {

    //creating stage and scene
    @FXML
    private Stage stage;
    private Scene scene;


    //table view and columns for parts
    public TableView<Part> tableParts;
    public TableColumn idColumnParts;
    public TableColumn nameColumnParts;
    public TableColumn priceColumnParts;
    public TableColumn stockColumnParts;


    //table view, columns, observable list for associated parts
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    public TableView<Part> tableAssociatedParts;
    public TableColumn idColumnAssociatedParts;
    public TableColumn nameColumnAssociatedParts;
    public TableColumn priceColumnAssociatedParts;
    public TableColumn stockColumnAssociatedParts;

    //creating text fields for part table
    @FXML
    public TextField productName;
    @FXML
    public TextField productStock;
    @FXML
    public TextField productPrice;
    @FXML
    public TextField productMaxQty;
    @FXML
    public TextField productMinQty;
    @FXML
    public TextField partNameOrId;


    /** This method returns the user back to the "Main Form" scene.
     * @param event is the action event which is executed when the user clicks the "Cancel" button in the "Add Product Form" scene.
     */
    public void backToMainForm(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main_form.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Inventory Management System");
        stage.show();
    }


    /** This method adds a new product based on the user added data.
     * @param event is the action event which is executed when the user clicks the "Save" button in the "Add Product Form" scene.
     * Once the user inputs the valid data and clicks the "Save" button, the scene will be switched back to the "Main Form" scene, and the Product table will be populated with the information of the added product.
     * A dialogue box will appear for all invalid data entries.
     */
    public void OnSaveAddProduct(ActionEvent event) throws IOException {

        //this if statement checks if the text fields are empty. If they are, displays an error dialogue box
        if ((productName.getText().isEmpty()) || (productPrice.getText().isEmpty()) || (productStock.getText().isEmpty()) || (productMinQty.getText().isEmpty()) || (productMaxQty.getText().isEmpty())) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Adding a Product");
            alert.setHeaderText("One or More Text Fields are Empty");
            alert.setContentText("Please complete all text fields to add a product!");
            alert.showAndWait();
        }
        else {
            try {
                //this if statement checks if the min qty is greater than equal to max qty. If it is, displays an error dialogue box
                if ((Integer.parseInt(productMinQty.getText())) >= (Integer.parseInt(productMaxQty.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Adding a Product");
                    alert.setHeaderText("Error in the min. and max. quantities");
                    alert.setContentText("Min. should be less than Max.!");
                    alert.showAndWait();
                    return;
                }
                //this if statement checks if the inventory is within the min and max. If not, displays an error dialogue box
                else if ((Integer.parseInt(productStock.getText())) < (Integer.parseInt(productMinQty.getText())) || (Integer.parseInt(productStock.getText())) > (Integer.parseInt(productMaxQty.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Adding a Product");
                    alert.setHeaderText("Error in the Inventory");
                    alert.setContentText("The Inventory should be between Min. and Max.!");
                    alert.showAndWait();
                    return;
                }
                //Adding product data
                Product product = new Product(Inventory.productIndex, productName.getText(), Double.parseDouble(productPrice.getText()), Integer.parseInt(productStock.getText()), Integer.parseInt(productMinQty.getText()), Integer.parseInt(productMaxQty.getText()));
                Inventory.addProduct(product);

                //this for loop copies the observable list to product class's observable list
                for (int i = 0; i < associatedParts.size(); i++) {
                    product.addAssociatedPart(associatedParts.get(i));
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
                alert.setTitle("Adding a Product");
                alert.setHeaderText("One or More Text Fields Contain an Invalid Input");
                alert.setContentText("Please input valid data in each text field to add a product!");
                alert.showAndWait();
            }
        }
    }


    /** This method assigns a part to a product.
     * @param event is the action event which is executed when the user clicks the "Add" button in the "Add Product Form" scene.
     * Once the user selects a respective part from the "Part" table and clicks the "Add" button, the selected part is added to "Associated Part" table.
     * If the "Add" button is clicked without selecting the part, Error dialogue box will appear.
     */
    public void OnAddAssociatedPart(ActionEvent event) {

        //selected part
        Part part = tableParts.getSelectionModel().getSelectedItem();

        //displays an error dialogue box if no part selected
        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Adding Associated Part");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please select a part and try again!");
            alert.showAndWait();
        }
        else {
            //adds associated part
            associatedParts.add(part);
            tableAssociatedParts.setItems(associatedParts);
        }
    }


    /** This method removes the selected part from the "Associated Part" table.
     * @param event is the action event which is executed when the user clicks the "Remove Associated Part" button in the "Add Product Form" scene.
     * Once the user selects a respective part from the "Associated Part" table and clicks the "Remove Associated Part" button, the selected part is removed.
     * If the "Remove Associated Part" button is clicked without selecting a part, Error dialogue box will appear.
     */
    public void OnRemoveAssociatedPart(ActionEvent event) {

        //selected part
        Part part = tableAssociatedParts.getSelectionModel().getSelectedItem();

        //displays an error dialogue box if no part selected
        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Removing Associated Part");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please select a part and try again!");
            alert.showAndWait();
        }
        else {
            //confirmation before deleting a part
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setTitle("Removing Associated Part");
            alert2.setHeaderText("Remove");
            alert2.setContentText("Do you want to remove this part?");

            Optional<ButtonType> result = alert2.showAndWait();
            if (result.get() == ButtonType.OK) {
                // ... user chose OK
                associatedParts.remove(part);
            }
            else {
                // ... user chose CANCEL or closed the dialog
                // do nothing
            }
        }
    }


    /** This method searches the data from the Part table based on the "Part ID" or partial/full "Part Name" entered by the user, and provides the search results.
     * @param event is the action event which is executed when the user types keyword in the search "Text Field" in the "Add Product Form" and presses the "Enter" button on the keyboard.
     * Matching search results appear
     * If search founds no results, a Warning dialogue box appears.
     */
    public void searchPartByNameOrId(ActionEvent event) {

        //gets a string and sets it to lower case
        String s = partNameOrId.getText().toLowerCase();

        //search by part name
        //passes the above string to lookupPart method in Inventory class
        ObservableList<Part> part = Inventory.lookupPart(s);

        //search by part id (if the above search returned 0)
        if (part.size() == 0) {
            try {
                int index = Integer.parseInt(s);
                Part part2 = Inventory.lookupPart(index);
                if (part2 != null) {
                    part.add(part2);
                }
                else {
                    //displays warning dialogue box if the user typed unexpected data format
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Searching a Part");
                    alert.setHeaderText("No results found!");
                    alert.setContentText("Please try again with a valid Part ID or Name.");
                    alert.showAndWait();
                }
            }
            catch(NumberFormatException e) {
                //displays warning dialogue box if the user typed unexpected data format
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Searching a Part");
                alert.setHeaderText("No results found!");
                alert.setContentText("Please try again with a valid Part ID or Name.");
                alert.showAndWait();
            }
        }
        //sets the data in parts table
        tableParts.setItems(part);
    }


    /** This method initializes the "AddProductController" class.
     * Whenever user accesses the "Add Product Form" scene, the "Part Table" will be pre-populated with the part data.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //sets the data in parts table
        tableParts.setItems(Inventory.getAllParts());

        //columns for Parts table
        idColumnParts.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumnParts.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockColumnParts.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumnParts.setCellValueFactory(new PropertyValueFactory<>("price"));

        //columns for associated Parts table
        idColumnAssociatedParts.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumnAssociatedParts.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockColumnAssociatedParts.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumnAssociatedParts.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
