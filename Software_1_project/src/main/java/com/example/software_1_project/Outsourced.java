package com.example.software_1_project;


/** This class extends Part class.
 * There are two types of Parts and OutSourced is one of them.
 * This class contains a constructor which defines the attributes of the OutSourced part.
 * @author Dimple Kumar
 */
public class Outsourced extends Part{
    private String companyName;

    //constructor
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }


    /** This method sets the companyName.
     * @param companyName is the name that this method sets.
     */
   public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    /** This method returns the companyName.
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

}
