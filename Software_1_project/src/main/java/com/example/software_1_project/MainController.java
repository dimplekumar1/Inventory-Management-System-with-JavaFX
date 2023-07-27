package com.example.software_1_project;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;


/** This class manages functionality and features on the "Main Form" scene.
 *
 * RUNTIME ERROR: a runtime error was found when I left one of the text fields blank in the "Add Part Form". This error was corrected by adding "if" statement. The if statement now checks if any text field is empty and displays an error dialogue box if true.
 *
 * FUTURE ENHANCEMENT: This Inventory Management System can include a feature to create and download reports. Such as a summary of stock. It could also include alerts if the inventory is too low and reminds to re-stock.
 *
 * JAVADOC FILES LOCATION: the javadoc files can be found at /Software_1_Project/JavaDoc
 *
 * @author Dimple Kumar
 *
 */
public class MainController implements Initializable {

    //creating stage and scene
    @FXML
    private Stage stage;
    private Scene scene;

    //table view and columns for Parts
    public TableView<Part> tableParts;
    public TableColumn idColumnParts;
    public TableColumn nameColumnParts;
    public TableColumn priceColumnParts;
    public TableColumn stockColumnParts;

    //table view and columns for Products
    public TableView<Product> tableProducts;
    public TableColumn idColumnProducts;
    public TableColumn nameColumnProducts;
    public TableColumn priceColumnProducts;
    public TableColumn stockColumnProducts;

    //search text fields for part and product
    public TextField partNameOrId;
    public TextField productNameOrId;


    /** This method opens the "Add Part Form" scene.
     * @param event is the action event which is executed when the user clicks the "Add" button below the part table in the "Main Form" scene.
     */
   public void openAddParts(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Add_part_form.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Add Part Form");
        stage.show();
    }


    /** This method opens the "Modify Part Form" scene.
     * @param event is the action event which is executed when the user selects a part and clicks the "Modify" button below the part table in the "Main Form" scene.
     * Error dialogue box appears if no part is selected.
     */
    public void openModifyParts(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Modify_part_form.fxml"));
        loader.load();

        //this is done to access partData function from the ModifyPartsController
        ModifyPartsController modifyPartsController = loader.getController();

        //selected part
        Part part = tableParts.getSelectionModel().getSelectedItem();

        //displays an error dialogue box if no part selected
        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modifying a Part");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please select a part and try again!");
            alert.showAndWait();
        }
        else {
            //passing selected part to ModifyPartsController
            modifyPartsController.partData(part);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent root = loader.getRoot();
            stage.setScene(new Scene(root));
            stage.setTitle("Modify Part Form");
            stage.show();
        }
    }


    /** This method opens the "Add Product Form" scene.
     * @param event is the action event which is executed when the user clicks the "Add" button below the product table in the "Main Form" scene.
     */
    public void openAddProducts(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Add_product_form.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Add Product Form");
        stage.show();
    }


    /** This method opens the "Modify Product Form" scene.
     * @param event is the action event which is executed when the user selects a product and clicks the "Modify" button below the product table in the "Main Form" scene.
     * Error dialogue box appears if no product is selected.
     */
    public void openModifyProducts(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Modify_product_form.fxml"));
        loader.load();

        //this is done to access productData function in the ModifyProductsController
        ModifyProductController modifyProductController = loader.getController();

        //selected product
        Product product = tableProducts.getSelectionModel().getSelectedItem();

        //displays an error dialogue box if no product selected
        if (product == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modifying a Product");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("Please select a product and try again!");
            alert.showAndWait();
        }
        else {
            //passing selected product to ModifyProductController
            modifyProductController.productData(product);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent root = loader.getRoot();
            stage.setScene(new Scene(root));
            stage.setTitle("Modify Product Form");
            stage.show();
        }
    }


    /** This method exits is application.
     * @param event is the action event which is executed when the user clicks the "Exit" button in the "Main Form" scene.
     */
    public void exit(ActionEvent event) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }


    /** This method deletes the selected part from the "Part" table.
     * @param event is the action event which is executed when the user clicks the "Delete" button below the part table in the "Main Form" scene.
     * Once the user selects a respective part from the "Part" table and clicks the "delete" button, the selected part is deleted.
     * If the "Delete" button is clicked without selecting a part, Error dialogue box will appear.
     */
    public void deletePart(ActionEvent event) throws IOException {

        //selected part
        Part part = tableParts.getSelectionModel().getSelectedItem();

        //displays an error dialogue box if no part selected
        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Deleting a Part");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please select a part and try again!");
            alert.showAndWait();
        }

        //confirmation before deleting a part
        else {
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setTitle("Deleting a Part");
            alert2.setHeaderText("Delete");
            alert2.setContentText("Do you want to delete this part?");

            Optional<ButtonType> result = alert2.showAndWait();
            if (result.get() == ButtonType.OK) {
                // ... user chose OK
                Inventory.deletePart(part);
            } else {
                // ... user chose CANCEL or closed the dialog
                // do nothing
            }
        }
    }


    /** This method deletes the selected product from the "Product" table.
     * @param event is the action event which is executed when the user clicks the "Delete" button below the product table in the "Main Form" scene.
     * Once the user selects a respective product from the "Product" table and clicks the "delete" button, the selected product is deleted.
     * If the "Delete" button is clicked without selecting a product, Error dialogue box will appear.
     */
    public void deleteProduct(ActionEvent event) throws IOException {

        //selected product
        Product product = tableProducts.getSelectionModel().getSelectedItem();

        //displays an error dialogue box if no product selected
        if (product == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Deleting a Product");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("Please select a product and try again!");
            alert.showAndWait();
        }
        //confirmation before deleting product
        else {
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setTitle("Deleting a Product");
            alert2.setHeaderText("Delete");
            alert2.setContentText("Do you want to delete this product?");

            Optional<ButtonType> result = alert2.showAndWait();
            if (result.get() == ButtonType.OK) {
                // ... user chose OK
                if (product.getAllAssociatedParts().size() == 0) {
                    Inventory.deleteProduct(product);
                }
                //can't delete product if it contains associated parts
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Deleting a Product");
                    alert.setHeaderText("Can't Delete a Product That Has Parts Associated With It.");
                    alert.setContentText("To delete this product, remove all associated parts!");
                    alert.showAndWait();
                }
            }
            else {
                // ... user chose CANCEL or closed the dialog
                //do nothing
            }
        }
    }

    //to add test data only if it is the first time accessing Main Form scene
    private static boolean firstTime = true;


    /** This method adds that test data in the Part and Product table in the "Main Form" scene.  */
    private void addTestData() {
        if(!firstTime) {
            return;
        }
        firstTime = false;

        //Test Data Parts
        InHouse inHousePart1 = new InHouse(Inventory.partIndex, "Brakes", 12.99, 15, 1, 20, 111);
        Inventory.addPart(inHousePart1);

        Outsourced outsourcedPart1 = new Outsourced(Inventory.partIndex, "Rim", 56.99, 15, 1, 20, "Super Bikes");
        Inventory.addPart(outsourcedPart1);

        Outsourced outsourcedPart2 = new Outsourced(Inventory.partIndex, "Seat", 120.58, 8, 1, 10, "Custom Cars");
        Inventory.addPart(outsourcedPart2);

        //Test Data Products
        Product product1 = new Product(Inventory.productIndex, "Brakes", 12.99, 15, 1, 20);
        Inventory.addProduct(product1);

        Product product2 = new Product(Inventory.productIndex, "Tire", 149.99, 25, 1, 30);
        Inventory.addProduct(product2);

        Product product3 = new Product(Inventory.productIndex, "Engine", 1149.99, 10, 1, 15);
        Inventory.addProduct(product3);
    }


    /** This method searches the data from the Part table based on the "Part ID" or partial/full "Part Name" entered by the user, and provides the search results.
     * @param event is the action event which is executed when the user types keyword in the search "Text Field" above the part table in the "Main Form" and presses the "Enter" button on the keyboard.
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
                    //displays error dialogue box if the user typed unexpected data format
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Searching a Part");
                    alert.setHeaderText("No results found!");
                    alert.setContentText("Please try again with a valid Part ID or Name.");
                    alert.showAndWait();
                }
            }
            catch(NumberFormatException e) {
                //displays error dialogue box if the user typed unexpected data format
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


    /** This method searches the data from the Product table based on the "Product ID" or partial/full "Product Name" entered by the user, and provides the search results.
     * @param event is the action event which is executed when the user types keyword in the search "Text Field" above the product table in the "Main Form" and presses the "Enter" button on the keyboard.
     * Matching search results appear
     * If search founds no results, a Warning dialogue box appears.
     */
    public void searchProductByNameOrId(ActionEvent event){

        //gets a string and sets it to lower case
        String s = productNameOrId.getText().toLowerCase();

        //search by product name
        //passes the above string to lookupProduct method in Inventory class
        ObservableList<Product> product = Inventory.lookupProduct(s);

        //search by product id (if the above search returned 0)
        if (product.size() == 0) {
            try {
                int index = Integer.parseInt(s);
                Product product2 = Inventory.lookupProduct(index);
                if (product2 != null) {
                    product.add(product2);
                }
                else {
                    //displays error dialogue box if the user typed unexpected data format
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Searching a Product");
                    alert.setHeaderText("No results found!");
                    alert.setContentText("Please try again with a valid Product ID or Name.");
                    alert.showAndWait();
                }
            }
            catch (NumberFormatException e)
                {
                    //displays error dialogue box if the user typed unexpected data format
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Searching a Product");
                    alert.setHeaderText("No results found!");
                    alert.setContentText("Please try again with a valid Product ID or Name.");
                    alert.showAndWait();
                }
            }
        //sets the data in products table
        tableProducts.setItems(product);
    }


    /** This method initializes the "MainController" class.
     * Whenever user accesses the "Main Form" scene, the part and product tables will be pre-populated with the part and product data.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //add test data
        addTestData();

        //sets the data in parts and products table
        tableParts.setItems(Inventory.getAllParts());
        tableProducts.setItems(Inventory.getAllProducts());

        //columns for Parts table
        idColumnParts.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumnParts.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockColumnParts.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumnParts.setCellValueFactory(new PropertyValueFactory<>("price"));

        //columns for Products table
        idColumnProducts.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumnProducts.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockColumnProducts.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumnProducts.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}


