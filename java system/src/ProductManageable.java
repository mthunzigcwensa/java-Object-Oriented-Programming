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
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ProductManageable extends Product implements ProductInterface {
    private String supplierName;

    // Constructor
    public ProductManageable(String productName, byte[] productImage, int productQuantity, double productPrice, String supplierName) {
        super(productName, productImage, productQuantity, productPrice);
        this.supplierName = supplierName;
    }

    // Getter and Setter for supplierName
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    
        // Function to retrieve product names from the database and load them into a combo box
    public void loadProductNamesIntoComboBox(JComboBox<String> comboBox) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/bbstore?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String dbUsername = "root";
        String dbPassword = "M2nzie@emkay";
        comboBox.removeAllItems(); // Clear the combo box

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            String sql = "SELECT product_name FROM products";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Get product name from the database
                        String productName = resultSet.getString("product_name");
                        
                        // Add the product name to the combo box
                        comboBox.addItem(productName);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addProduct(String productName, byte[] productImage, double price, int quantity) {
          // Connect to the database (You need to provide the correct database connection details).
    String jdbcUrl = "jdbc:mysql://localhost:3306/bbstore?zeroDateTimeBehavior=CONVERT_TO_NULL"; // Replace with your database URL
    String dbUsername = "root";
    String dbPassword = "M2nzie@emkay"; // Replace with your database password


        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            String sql = "INSERT INTO products (product_name, product_image, product_quantity, product_price, supplier_name) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, productName);
                preparedStatement.setBytes(2, productImage);
                preparedStatement.setInt(3, quantity);
                preparedStatement.setDouble(4, price);
                preparedStatement.setString(5, supplierName);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

@Override
public void  updateProduct(String productNameToUpdate, String newProductName, byte[] productImage, double price, int quantity){
    // Implement updating an existing product in the database by productName
    String jdbcUrl = "jdbc:mysql://localhost:3306/bbstore?zeroDateTimeBehavior=CONVERT_TO_NULL"; // Replace with your database URL
    String dbUsername = "root";
    String dbPassword = "M2nzie@emkay"; // Replace with your database password

    try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
        String sql = "UPDATE products SET product_name = ?, product_image = ?, product_quantity = ?, product_price = ?, supplier_name = ? WHERE product_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newProductName);
            preparedStatement.setBytes(2, productImage);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setDouble(4, price);
            preparedStatement.setString(5, supplierName);
            preparedStatement.setString(6, productNameToUpdate);
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Product updated successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Product not updated. Please try again.");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
    }
}

@Override
public void addProductToCart(String productName, int quantityBought, String userEmail) {
    String jdbcUrl = "jdbc:mysql://localhost:3306/bbstore?zeroDateTimeBehavior=CONVERT_TO_NULL"; // Replace with your database URL
    String dbUsername = "root";
    String dbPassword = "M2nzie@emkay"; // Replace with your database password

    // Check if the requested quantity is available in the products table
    try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
        String checkQuantitySql = "SELECT product_quantity FROM products WHERE product_name = ?";
        try (PreparedStatement checkQuantityStatement = connection.prepareStatement(checkQuantitySql)) {
            checkQuantityStatement.setString(1, productName);
            try (ResultSet resultSet = checkQuantityStatement.executeQuery()) {
                if (resultSet.next()) {
                    int availableQuantity = resultSet.getInt("product_quantity");
                    
if (availableQuantity >= quantityBought) {
    // Quantity is available, proceed to add to the cart

    // Fetch the price of the product from the products table
    String fetchPriceSql = "SELECT product_price FROM products WHERE product_name = ?";
    double productPrice = 0.0; // Initialize product price

    try (PreparedStatement fetchPriceStatement = connection.prepareStatement(fetchPriceSql)) {
        fetchPriceStatement.setString(1, productName);
        ResultSet resultSets = fetchPriceStatement.executeQuery();

        // Check if the product exists in the database
        if (resultSets.next()) {
            productPrice = resultSets.getDouble("product_price");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    // Calculate the total cost
    double totalCost = productPrice * quantityBought;

    // Insert the data into the cart table
    String addToCartSql = "INSERT INTO cart (product_name, quantity, totalCost, user_email) VALUES (?, ?, ?, ?)";
    try (PreparedStatement addToCartStatement = connection.prepareStatement(addToCartSql)) {
        addToCartStatement.setString(1, productName);
        addToCartStatement.setInt(2, quantityBought);
        addToCartStatement.setDouble(3, totalCost);
        addToCartStatement.setString(4, userEmail);

        int rowsInserted = addToCartStatement.executeUpdate();

        if (rowsInserted > 0) {
            // Product added to the cart successfully
            JOptionPane.showMessageDialog(null, "Product added to the cart.");
        } else {
            // Failed to add the product to the cart
            JOptionPane.showMessageDialog(null, "Failed to add product to the cart.");
        }
    }
} else {
    // Quantity not available, show a message or handle accordingly
    JOptionPane.showMessageDialog(null, " quantity exceeds available quantity");
}

                } else {
                    // Product not found, show a message or handle accordingly
                    JOptionPane.showMessageDialog(null, "Product not found.");
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
    }
}

    @Override
    public void removeProduct(String productName) {
        // Implement removing a product from the database by its name
    String jdbcUrl = "jdbc:mysql://localhost:3306/bbstore?zeroDateTimeBehavior=CONVERT_TO_NULL"; // Replace with your database URL
    String dbUsername = "root";
    String dbPassword = "M2nzie@emkay"; // Replace with your database password


        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            String sql = "DELETE FROM products WHERE product_name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, productName);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayProducts(JTable table) {
        // Implement displaying products in a JTable
    String jdbcUrl = "jdbc:mysql://localhost:3306/bbstore?zeroDateTimeBehavior=CONVERT_TO_NULL"; // Replace with your database URL
    String dbUsername = "root";
    String dbPassword = "M2nzie@emkay"; // Replace with your database password

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear the existing data in the table

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            String sql = "SELECT product_image, product_name, product_quantity, product_price, supplier_name FROM products";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Get product information from the database
                     byte[] productImage = resultSet.getBytes("product_image");
                        String productName = resultSet.getString("product_name");
                        int productQuantity = resultSet.getInt("product_quantity");
                        double productPrice = resultSet.getDouble("product_price");
                        

                        // Add a new row to the table model with product information
                          // Add a new row to the table model with product information
                          
                        model.addRow(new Object[]{productName, productImage, productQuantity, productPrice, supplierName});
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
@Override
public double displayProductsInCart(String userEmail, JTable table, JLabel totalCostLabel) {
    String jdbcUrl = "jdbc:mysql://localhost:3306/bbstore?zeroDateTimeBehavior=CONVERT_TO_NULL"; // Replace with your database URL
    String dbUsername = "root";
    String dbPassword = "M2nzie@emkay"; // Replace with your database password

    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0); // Clear the existing data in the table

    double totalCost = 0.0; // Initialize total cost to zero

    try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
        String sql = "SELECT product_name, quantity, totalCost FROM cart WHERE user_email = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userEmail);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String productName = resultSet.getString("product_name");
                    int quantity = resultSet.getInt("quantity");
                    double productTotalCost = resultSet.getDouble("totalCost");

                    // Add a new row to the table model with product information from the cart
                    model.addRow(new Object[]{productName, quantity, productTotalCost});

                    // Update the total cost
                    totalCost += productTotalCost;
                }
            }
        }

        // Update the total cost label with the calculated total cost
        totalCostLabel.setText("" + totalCost);
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return totalCost; // Return the total cost
}

@Override
public boolean payForProducts(String userEmail) {
    String jdbcUrl = "jdbc:mysql://localhost:3306/bbstore?zeroDateTimeBehavior=CONVERT_TO_NULL"; // Replace with your database URL
    String dbUsername = "root";
    String dbPassword = "M2nzie@emkay"; // Replace with your database password

    try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
        // Step 1: Retrieve products in the user's cart
        String retrieveCartItemsSql = "SELECT product_name, quantity FROM cart WHERE user_email = ?";
        try (PreparedStatement retrieveCartItemsStatement = connection.prepareStatement(retrieveCartItemsSql)) {
            retrieveCartItemsStatement.setString(1, userEmail);

            try (ResultSet cartItemsResultSet = retrieveCartItemsStatement.executeQuery()) {
                while (cartItemsResultSet.next()) {
                    String productName = cartItemsResultSet.getString("product_name");
                    int quantityBought = cartItemsResultSet.getInt("quantity");

                    // Step 2: Update product quantities in the products table
                    String updateProductQuantitySql = "UPDATE products SET product_quantity = product_quantity - ? WHERE product_name = ?";
                    try (PreparedStatement updateQuantityStatement = connection.prepareStatement(updateProductQuantitySql)) {
                        updateQuantityStatement.setInt(1, quantityBought);
                        updateQuantityStatement.setString(2, productName);
                        updateQuantityStatement.executeUpdate();
                    }
                }
            }
        }

        // Step 3: Remove products from the cart after successful purchase
        String removeFromCartSql = "DELETE FROM cart WHERE user_email = ?";
        try (PreparedStatement removeFromCartStatement = connection.prepareStatement(removeFromCartSql)) {
            removeFromCartStatement.setString(1, userEmail);
            removeFromCartStatement.executeUpdate();
        }

        // If everything is successful, return true
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        // If any error occurs, return false to indicate failure
        return false;
    }
}

   public void removeProductFromCart(String productName, String userEmail) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/bbstore?zeroDateTimeBehavior=CONVERT_TO_NULL"; // Replace with your database URL
        String dbUsername = "root";
        String dbPassword = "M2nzie@emkay"; // Replace with your database password

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            // Remove the specified product from the cart
            String removeFromCartSql = "DELETE FROM cart WHERE user_email = ? AND product_name = ?";
            try (PreparedStatement removeFromCartStatement = connection.prepareStatement(removeFromCartSql)) {
                removeFromCartStatement.setString(1, userEmail);
                removeFromCartStatement.setString(2, productName);
                int rowsDeleted = removeFromCartStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(null, "Product removed from the cart.");
                } else {
                    JOptionPane.showMessageDialog(null, "Product not found in the cart.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        }
    }
   
   
   public void clearCart(String userEmail) {
    String jdbcUrl = "jdbc:mysql://localhost:3306/bbstore?zeroDateTimeBehavior=CONVERT_TO_NULL"; // Replace with your database URL
    String dbUsername = "root";
    String dbPassword = "M2nzie@emkay"; // Replace with your database password

    try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
        // Delete all products from the user's cart
        String clearCartSql = "DELETE FROM cart WHERE user_email = ?";
        try (PreparedStatement clearCartStatement = connection.prepareStatement(clearCartSql)) {
            clearCartStatement.setString(1, userEmail);
            clearCartStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cart cleared successfully.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
    }
}

   
   public boolean updateQuantityInCart(String userEmail, String productName, int newQuantity) {
    String jdbcUrl = "jdbc:mysql://localhost:3306/bbstore?zeroDateTimeBehavior=CONVERT_TO_NULL"; // Replace with your database URL
    String dbUsername = "root";
    String dbPassword = "M2nzie@emkay"; // Replace with your database password

    try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
        // Check if the product is in the user's cart
        String checkCartSql = "SELECT quantity FROM cart WHERE user_email = ? AND product_name = ?";
        try (PreparedStatement checkCartStatement = connection.prepareStatement(checkCartSql)) {
            checkCartStatement.setString(1, userEmail);
            checkCartStatement.setString(2, productName);
            try (ResultSet resultSet = checkCartStatement.executeQuery()) {
                if (resultSet.next()) {
                    int currentQuantity = resultSet.getInt("quantity");
                    
                    // Update the quantity if the new quantity is valid
                    if (newQuantity >= 0) {
                        String updateQuantitySql = "UPDATE cart SET quantity = ? WHERE user_email = ? AND product_name = ?";
                        try (PreparedStatement updateQuantityStatement = connection.prepareStatement(updateQuantitySql)) {
                            updateQuantityStatement.setInt(1, newQuantity);
                            updateQuantityStatement.setString(2, userEmail);
                            updateQuantityStatement.setString(3, productName);
                            updateQuantityStatement.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Quantity updated successfully.");
                            return true;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid quantity. Please enter a valid quantity.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Product not found in the cart.");
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
    }
    
    return false;
}



}

        