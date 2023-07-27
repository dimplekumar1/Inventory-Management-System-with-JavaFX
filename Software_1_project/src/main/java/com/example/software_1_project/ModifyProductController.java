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


/** This class modifies Product data.
 * @author Dimple Kumar
 */
public class ModifyProductController implements Initializable {

    //creating stage and scene
    @FXML
    private Stage stage;
    private Scene scene;

    //text field for product data
    @FXML
    private TextField modifyProductId;
    @FXML
    private TextField modifyProductName;
    @FXML
    private TextField modifyProductStock;
    @FXML
    private TextField modifyProductPrice;
    @FXML
    private TextField modifyProductMinQty;
    @FXML
    private TextField modifyProductMaxQty;

    //search field
    @FXML
    public TextField partNameOrId;

    //table view and columns for parts
    public TableView<Part> tableParts;
    public TableColumn idColumnParts;
    public TableColumn nameColumnParts;
    public TableColumn priceColumnParts;
    public TableColumn stockColumnParts;

    //table view, column, observable list for associated parts
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    public TableView<Part> tableAssociatedParts;
    public TableColumn idColumnAssociatedParts;
    public TableColumn nameColumnAssociatedParts;
    public TableColumn priceColumnAssociatedParts;
    public TableColumn stockColumnAssociatedParts;



    /** This method passes the selected part product from MainController to the ModifyProductsController.
     * @param product is the selected product from MainController.
     * This method also passes the associated part table data from AddProductsController.
     */
    public void productData(Product product) {
        //sets data passed from MainController
        modifyProductId.setText(String.valueOf(product.getId()));
        modifyProductName.setText(product.getName());
        modifyProductStock.setText(String.valueOf(product.getStock()));
        modifyProductPrice.setText(String.valueOf(product.getPrice()));
        modifyProductMinQty.setText(String.valueOf(product.getMin()));
        modifyProductMaxQty.setText(String.valueOf(product.getMax()));

        //initializes associated table
        tableAssociatedParts.setItems(product.getAllAssociatedParts());

        //this for loop copies the observable list from product class to this controller's observable list
        for(int i = 0; i < product.getAllAssociatedParts().size(); i++) {
            associatedParts.add(product.getAllAssociatedParts().get(i));
        }
    }


    /** This method returns the user back to the "Main Form" scene.
     * @param event is the action event which is executed when the user clicks the "Cancel" button in the "Modify Product Form" scene.
     */
    public void backToMainForm(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main_form.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Inventory Management System");
        stage.show();
    }


    /** This method modifies a product based on the user added data.
     * @param event is the action event which is executed when the user clicks the "Save" button in the "Modify Product Form" scene.
     * Once the user inputs the valid data and clicks the "Save" button, the scene will be switched back to the "Main Form" scene, and the Product table will be populated with the information of the modified product.
     * A dialogue box will appear for all invalid data entries.
     */
    public void OnSaveModifyProduct(ActionEvent event) throws IOException {

        //this if statement checks if the text fields are empty. If they are, displays an error dialogue box
        if ((modifyProductName.getText().isEmpty()) || (modifyProductPrice.getText().isEmpty()) || (modifyProductStock.getText().isEmpty()) || (modifyProductMinQty.getText().isEmpty()) || (modifyProductMaxQty.getText().isEmpty())) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modifying a Product");
            alert.setHeaderText("One or More Text Fields are Empty");
            alert.setContentText("Please complete all text fields!");
            alert.showAndWait();
        }
        else {
            try {
                //this if statement checks if the min qty is greater than equal to max qty. If it is, displays an error dialogue box
                if ((Integer.parseInt(modifyProductMinQty.getText())) >= (Integer.parseInt(modifyProductMaxQty.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Modifying a Product");
                    alert.setHeaderText("Error in the min. and max. quantities");
                    alert.setContentText("Min. should be less than Max.!");
                    alert.showAndWait();
                    return;
                }
                //this if statement checks if the inventory is within the min and max. If not, displays an error dialogue box
                else if ((Integer.parseInt(modifyProductStock.getText())) < (Integer.parseInt(modifyProductMinQty.getText())) || (Integer.parseInt(modifyProductStock.getText())) > (Integer.parseInt(modifyProductMaxQty.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Modifying a Product");
                    alert.setHeaderText("Error in the Inventory");
                    alert.setContentText("The Inventory should be between Min. and Max.!");
                    alert.showAndWait();
                    return;
                }

                //Adding product data
                Product product = new Product(Integer.parseInt(modifyProductId.getText()), modifyProductName.getText(), Double.parseDouble(modifyProductPrice.getText()), Integer.parseInt(modifyProductStock.getText()), Integer.parseInt(modifyProductMinQty.getText()), Integer.parseInt(modifyProductMaxQty.getText()));
                Inventory.updateProduct(Integer.parseInt(modifyProductId.getText()), product);

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
                alert.setTitle("Modifying a Product");
                alert.setHeaderText("One or More Text Fields Contain an Invalid Input");
                alert.setContentText("Please input valid data in each text field!");
                alert.showAndWait();
            }
        }
    }


    /** This method assigns a part to a product.
     * @param event is the action event which is executed when the user clicks the "Add" button in the "Modify Product Form" scene.
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
            //adds associated parts
            associatedParts.add(part);
            tableAssociatedParts.setItems(associatedParts);
        }
    }


    /** This method removes the selected part from the "Associated Part" table.
     * @param event is the action event which is executed when the user clicks the "Remove Associated Part" button in the "Modify Product Form" scene.
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
                tableAssociatedParts.setItems(associatedParts);
            }
            else {
                // ... user chose CANCEL or closed the dialog
                // do nothing
            }
        }
    }


    /** This method searches the data from the Part table based on the "Part ID" or partial/full "Part Name" entered by the user, and provides the search results.
     * @param event is the action event which is executed when the user types keyword in the search "Text Field" in the "Modify Product Form" and presses the "Enter" button on the keyboard.
     * Matching search results appear
     * If search founds no results, a Warning dialogue box appears.
     */
    public void searchPartByNameOrId(ActionEvent event) {

        //gets a string and sets it to lower case
        String s = partNameOrId.getText().toLowerCase();

        ObservableList<Part> part = Inventory.lookupPart(s);

        //search by part name
        //passes the above string to lookupPart method in Inventory class
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



    /** This method initializes the "ModifyProductController" class.
     * Whenever user accesses the "Modify Product Form" scene, the "Part Table" will be pre-populated with the part data.
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
