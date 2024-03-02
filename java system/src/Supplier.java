/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nxuma
 */
public class Supplier {

    private String supplierName; // Private variable to store the supplier's name.
    private String supplierEmail; // Private variable to store the supplier's email.

    // Constructor for the Supplier class.
    // It initializes the instance variables with the provided values.
    public Supplier(String supplierName, String supplierEmail) {
        this.supplierName = supplierName;
        this.supplierEmail = supplierEmail;
    }

    // Getters and setters for the private variables.

    // Getter method for retrieving the supplier's name.
    public String getSupplierName() {
        return supplierName;
    }

    // Setter method for setting the supplier's name.
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    // Getter method for retrieving the supplier's email.
    public String getSupplierEmail() {
        return supplierEmail;
    }

    // Setter method for setting the supplier's email.
    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }
}

