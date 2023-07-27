package com.example.software_1_project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/** This class defines and manages Products.
 * @author Dimple Kumar
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    //constructor
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


    /** This method sets the product id.
     * @param id is the id that this method sets.
     */
    public void setId(int id) {
        this.id = id;
    }


    /** This method sets the product name.
     * @param name is the name that this method sets.
     */
    public void setName(String name) {
        this.name = name;
    }


    /** This method sets the product price.
     * @param price is the price that this method sets.
     */
    public void setPrice(double price) {
        this.price = price;
    }


    /** This method sets the product stock.
     * @param stock is the stock that this method sets.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }


    /** This method sets the product min qty.
     * @param min is the min that this method sets.
     */
    public void setMin(int min){
        this.min = min;
    }


    /** This method sets the product max qty.
     * @param max is the max that this method sets.
     */
    public void setMax(int max) {
        this.max = max;
    }


    /** This method returns the product id.
     * @return the id.
     */
    public int getId() {
        return id;
    }


    /** This method returns the product name.
     * @return the name.
     */
    public String getName() {
        return name;
    }


    /** This method returns the product price.
     * @return the price.
     */
    public double getPrice() {
        return price;
    }


    /** This method returns the product stock.
     * @return the stock.
     */
    public int getStock() {
        return stock;
    }


    /** This method returns the product min qty.
     * @return the min.
     */
    public int getMin() {
        return min;
    }


    /** This method returns the product max qty.
     * @return the max.
     */
    public int getMax() {
        return max;
    }


    /** This method adds a part to an observable list.
     * @param part is the part which will be added to the observable list.
     */
    public void addAssociatedPart(Part part) {
       associatedParts.add(part);
    }


    /** This method removes a selected associated part from an observable list.
     * @param selectedAssociatedPart is the part which will be removed from the observable list.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }


    /** This method returns an observable list containing all associated parts.
     * @return the associatedParts.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
