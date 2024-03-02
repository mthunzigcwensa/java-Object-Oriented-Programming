/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nxuma
 */
public class pickUp {
    private String productName; // Name of the picked-up product.
    private int quantityPickedUp; // Amount of quantity picked up.
    private String pickedUpBy; // Name of the person who picked it up.

    // Constructor for the PickUp class.
    // It initializes the instance variables with the provided values.
    public pickUp(String productName, int quantityPickedUp, String pickedUpBy) {
        this.productName = productName;
        this.quantityPickedUp = quantityPickedUp;
        this.pickedUpBy = pickedUpBy;
    }

    // Getters and setters for the private variables.

    // Getter method for retrieving the product name.
    public String getProductName() {
        return productName;
    }

    // Setter method for setting the product name.
    public void setProductName(String productName) {
        this.productName = productName;
    }

    // Getter method for retrieving the quantity picked up.
    public int getQuantityPickedUp() {
        return quantityPickedUp;
    }

    // Setter method for setting the quantity picked up.
    public void setQuantityPickedUp(int quantityPickedUp) {
        this.quantityPickedUp = quantityPickedUp;
    }

    // Getter method for retrieving the person who picked it up.
    public String getPickedUpBy() {
        return pickedUpBy;
    }

    // Setter method for setting the person who picked it up.
    public void setPickedUpBy(String pickedUpBy) {
        this.pickedUpBy = pickedUpBy;
    }
}

