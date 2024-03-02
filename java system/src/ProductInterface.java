/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author nxuma
 */
import javax.swing.JLabel;
import javax.swing.JTable;

public interface ProductInterface {

    // Add a new product
    void addProduct(String productName, byte[] productImage, double price, int quantity);

    // Update an existing product
   void updateProduct(String productNameToUpdate, String newProductName, byte[] productImage, double price, int quantity);
    // Remove a product by its name
    void removeProduct(String productName);

    // Display products in a table
    void displayProducts(JTable table);
    
    void addProductToCart(String productName, int quantityBought, String userEmail);

    // Display products in the cart for a specific user
    double displayProductsInCart(String userEmail, JTable table, JLabel totalCostLabel);
    
        boolean payForProducts(String userEmail);



}