/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nxuma
 */
public class Product {
    private String productName;  // Private variable to store the product's name.
    private byte[] productImage; // Private variable to store the product's image.
    private int productQuantity; // Private variable to store the product's quantity.
    private double productPrice; // Private variable to store the product's price.

    // Constructor for the Product class.
    public Product(String productName, byte[] productImage, int productQuantity, double productPrice) {
        this.productName = productName;
        this.productImage = productImage;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
    }

    // Getters and setters for the private variables.
    
    // Getter method for retrieving the product's name.
    public String getProductName() {
        return productName;
    }

    // Setter method for setting the product's name.
    public void setProductName(String productName) {
        this.productName = productName;
    }

    // Getter method for retrieving the product's image.
    public byte[] getProductImage() {
        return productImage;
    }

    // Setter method for setting the product's image.
    public void setProductImage(byte[] productImage) {
        this.productImage = productImage;
    }

    // Getter method for retrieving the product's quantity.
    public int getProductQuantity() {
        return productQuantity;
    }

    // Setter method for setting the product's quantity.
    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    // Getter method for retrieving the product's price.
    public double getProductPrice() {
        return productPrice;
    }

    // Setter method for setting the product's price.
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}

