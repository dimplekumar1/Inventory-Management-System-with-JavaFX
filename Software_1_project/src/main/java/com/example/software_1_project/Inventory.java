package com.example.software_1_project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/** This class manages the Inventory of Parts and Products.
 * @author Dimple Kumar
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static int partIndex = 1;
    public static int productIndex = 1000;


    /** This method adds a part in allParts observable list.
     * @param  newPart is the part which is added in the observable list.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);

        //this is to auto generate part id
        partIndex++;
    }


    /** This method adds a product in allProducts observable list.
     * @param  newProduct is the product which is added in the observable list.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);

        //this is to auto generate product id
        productIndex++;
    }


    /** This method searches a part using the entered part id.
     * @param partId is entered by the user and used by this method to look up a part.
     * @return if a part id matches with a part in the observable list, the matched part is returned.
     * null is returned if there is no match.
     */
    public static Part lookupPart(int partId) {
        ObservableList<Part> allParts = getAllParts();

        //for loop to check if provided part id matches with any part in the observable list
        for (int i = 0; i < allParts.size(); i++) {
            Part part = allParts.get(i);
            if(part.getId() == partId) {
                return part;
            }
        }
        return null;
    }


    /** This method searches a product using the entered product id.
     * @param productId is entered by the user and used by this method to look up a product.
     * @return if a product id matches with a product in the observable list, the matched product is returned.
     * null is returned if there is no match.
     */
    public static Product lookupProduct(int productId) {
        ObservableList<Product> allProducts = getAllProducts();

        //for loop to check if provided product id matches with any product in the observable list
        for (int i = 0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);
            if(product.getId() == productId) {
                return product;
            }
        }
        return null;
    }


    /** This method searches a part using the entered full or partial part name.
     * @param partName is entered by the user and used by this method to look up a part.
     * @return if a part name matches with a part in the observable list, the matched part is added to a new observable list.
     * The new observable list is returned with the matched part.
     * empty observable list is returned if there is no match.
     */
    public static ObservableList<Part> lookupPart(String partName) {

        //creating new observable list
        ObservableList<Part> searchResults = FXCollections.observableArrayList();

        ObservableList<Part> allParts = getAllParts();

        //for loop to check if provided part name matches with any part in the "allParts" observable list, if it is then adds the part to the new observable list created above
        for(Part parts : allParts) {
            if (parts.getName().toLowerCase().contains(partName)) {
                searchResults.add(parts);
            }
        }
        return searchResults;

    }


    /** This method searches a product using the entered full or partial product name.
     * @param productName is entered by the user and used by this method to look up a product.
     * @return if a product name matches with a product in the observable list, the matched product is added to a new observable list.
     * The new observable list is returned with the matched product.
     * empty observable list is returned if there is no match.
     */
    public static ObservableList<Product> lookupProduct(String productName) {

        //creating new observable list
        ObservableList<Product> searchResults = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = getAllProducts();

        //for loop to check if provided product name matches with any product in the "allProducts" observable list, if it is then adds the part to the new observable list created above
        for(Product products : allProducts) {
            if (products.getName().toLowerCase().contains(productName)) {
                searchResults.add(products);
            }
        }

        return searchResults;

    }


    /** This method modifies the selected part.
     * @param index is the index of a part in the observable list which is being modified.
     * @param selectedPart is the modified part that is being placed in the observable list at the above-mentioned index.
     */
    public static void updatePart(int index, Part selectedPart) {
        for (int i = 0; i < allParts.size(); i++) {
            Part part = allParts.get(i);
            if (part.getId() == index) {
                getAllParts().set(i, selectedPart);
                return;
            }
        }
    }


    /** This method modifies the selected product.
     * @param index is the index of a product in the observable list which is being modified.
     * @param newProduct is the modified product that is being placed in the observable list at the above-mentioned index.
     */
    public static void updateProduct(int index, Product newProduct) {
        for (int i = 0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);
            if (product.getId() == index) {
                getAllProducts().set(i, newProduct);
                return;
            }
        }
    }


    /** This method removes a part.
     * @param selectedPart is the part selected by the user which will be deleted.
     * @return the selected part is deleted.
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }


    /** This method removes a product.
     * @param selectedProduct is the product selected by the user which will be deleted.
     * @return the selected product is deleted.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }


    /** This method returns observable list containing all parts.
     * @return allParts is returned.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }


    /** This method returns observable list containing all products.
     * @return allProducts is returned.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
