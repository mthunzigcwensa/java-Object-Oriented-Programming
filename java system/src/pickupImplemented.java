/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nxuma
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;


public class pickupImplemented implements pickupInterface {
    private String dbUrl = "jdbc:mysql://localhost:3306/bbstore?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private String dbUser = "root";
    private String dbPassword = "M2nzie@emkay";

    @Override
public void addPickUp(String productName, int quantityPickedUp, String pickedUpBy) {
    // Check if the requested quantity is greater than the available quantity
    if (!isQuantityAvailable(productName, quantityPickedUp)) {
         JOptionPane.showMessageDialog(null, "Failed to add pickup. Insufficient quantity available.");
        System.out.println("Failed to add pickup. Insufficient quantity available.");
        return;
    }

    String sql = "INSERT INTO pickedUpProducts (productName, quantityPickedUp, pickedUpBy) VALUES (?, ?, ?)";

    try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setString(1, productName);
        preparedStatement.setInt(2, quantityPickedUp);
        preparedStatement.setString(3, pickedUpBy);

        int rowsInserted = preparedStatement.executeUpdate();

        if (rowsInserted > 0) {
             JOptionPane.showMessageDialog(null, "Product picked up successfully.");
            System.out.println("Pickup added successfully.");
            updateProductQuantity(productName, quantityPickedUp);
        } else {
            System.out.println("Failed to add pickup.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

// Helper method to check if the requested quantity is available
private boolean isQuantityAvailable(String productName, int requestedQuantity) {
    String sql = "SELECT product_quantity FROM products WHERE product_name = ?";

    try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setString(1, productName);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int availableQuantity = resultSet.getInt("product_quantity");
                return requestedQuantity <= availableQuantity;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    // In case of an error or if the product is not found, assume quantity is not available
    return false;
}

    @Override
   public void updateProductQuantity(String productName, int newQuantity) {
    // SQL statement to update the product quantity in the products table
    String updateProductSql = "UPDATE products SET product_quantity = product_quantity - ? WHERE product_name = ?";

    try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
         PreparedStatement preparedStatement = connection.prepareStatement(updateProductSql)) {
        preparedStatement.setInt(1, newQuantity);
        preparedStatement.setString(2, productName);

        int rowsUpdated = preparedStatement.executeUpdate();

        if (rowsUpdated > 0) {
            System.out.println("Product quantity updated successfully.");
        } else {
            System.out.println("Failed to update product quantity.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}


