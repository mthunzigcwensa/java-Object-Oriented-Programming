/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author nxuma
 */
public interface pickupInterface {
    // Method to add a pickup, which affects the quantity of the product.
    void addPickUp(String productName, int quantityPickedUp, String pickedUpBy);

    // Method to update the product quantity after a pickup.
    void updateProductQuantity(String productName, int newQuantity);
}

