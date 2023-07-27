package com.example.software_1_project;


/** This class extends Part class.
 * There are two types of Parts and InHouse is one of them.
 * This class contains a constructor which defines the attributes of the InHouse part.
 * @author Dimple Kumar
 */
public class InHouse extends Part{
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
       super(id, name, price, stock, min, max);
       this.machineId = machineId;
    }


    /** This method sets the machineID.
     * @param machineId is the id that this method sets.
     */
   public void setMachineId(int machineId) {

        this.machineId = machineId;
    }


    /** This method returns the machineID.
     * @return the machineID
     */
    public int getMachineId() {
        return machineId;
    }
}
