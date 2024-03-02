/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nxuma
 */
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class bestBrightnessSuppliers implements SupplierManageable {
    private List<Supplier> supplierList = new ArrayList<>();

    // Define your database connection details here
    private String dbUrl = "jdbc:mysql://localhost:3306/bbstore?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private String dbUser = "root";
    private String dbPassword = "M2nzie@emkay";

   @Override
public void addSupplier(String supplierName, String supplierEmail) {
    try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
        // Check if the supplier email already exists in the database.
        String checkQuery = "SELECT supplierEmail FROM suppliers WHERE supplierEmail = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setString(1, supplierEmail);
            ResultSet resultSet = checkStmt.executeQuery();

            if (resultSet.next()) {
                // Show a JOptionPane message to notify the user.
                JOptionPane.showMessageDialog(null, "Supplier with this email already exists.");
            } else {
                String insertQuery = "INSERT INTO suppliers (supplierName, supplierEmail) VALUES (?, ?)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                    insertStmt.setString(1, supplierName);
                    insertStmt.setString(2, supplierEmail);
                    insertStmt.executeUpdate();

                    // Add the new supplier to the list only if it's successfully added to the database.
                    Supplier supplier = new Supplier(supplierName, supplierEmail);
                    supplierList.add(supplier);

                    // Show a success message using JOptionPane.
                    JOptionPane.showMessageDialog(null, "Supplier added successfully.");
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to add the supplier to the database.");
    }
}

// This function retrieves supplier emails from the database
public List<String> getSupplierEmails() {
    List<String> emails = new ArrayList<>();

    try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
        String selectQuery = "SELECT supplierEmail FROM suppliers";
        try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
            ResultSet resultSet = selectStmt.executeQuery();

            while (resultSet.next()) {
                String email = resultSet.getString("supplierEmail");
                emails.add(email);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to retrieve supplier emails from the database.");
    }

    return emails;
}

// This function populates the specified JComboBox with supplier emails
public void populateSupplierEmailComboBox(JComboBox<String> comboBox) {
    List<String> emails = getSupplierEmails();

    comboBox.removeAllItems(); // Clear the existing items

    for (String email : emails) {
        comboBox.addItem(email);
    }
}



@Override
public void updateSupplier(String supplierEmail, String newSupplierName, String newSupplierEmail) {
    // Update the supplier information in the database and in the supplierList.
    try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
        // Update the supplier information in the database.
        String updateQuery = "UPDATE suppliers SET supplierName = ?, supplierEmail = ? WHERE supplierEmail = ?";
        try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
            updateStmt.setString(1, newSupplierName);
            updateStmt.setString(2, newSupplierEmail);
            updateStmt.setString(3, supplierEmail);
            updateStmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Supplier added successfully.");

        }

        // Update the supplier information in the supplierList.
        for (Supplier supplier : supplierList) {
            if (supplier.getSupplierEmail().equals(supplierEmail)) {
                supplier.setSupplierName(newSupplierName);
                supplier.setSupplierEmail(newSupplierEmail);
                break; // No need to continue looping after the update.
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to update the supplier in the database.");
        JOptionPane.showMessageDialog(null, "Failed to add the supplier to the database.");

    }
}



    @Override
    public void removeSupplier(String supplierEmail) {
        supplierList.removeIf(supplier -> supplier.getSupplierEmail().equals(supplierEmail));
        
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String deleteQuery = "DELETE FROM suppliers WHERE supplierEmail = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                deleteStmt.setString(1, supplierEmail);
                deleteStmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Supplier removeed successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to remove the supplier from the database.");
            JOptionPane.showMessageDialog(null, "Failed to remove the supplier from the database.");
        }
    }

    @Override
    public void displayAllSuppliers(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String selectQuery = "SELECT supplierName, supplierEmail FROM suppliers";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                ResultSet resultSet = selectStmt.executeQuery();

                while (resultSet.next()) {
                    String supplierName = resultSet.getString("supplierName");
                    String supplierEmail = resultSet.getString("supplierEmail");
                    model.addRow(new Object[]{supplierName, supplierEmail});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to retrieve suppliers from the database.");
            JOptionPane.showMessageDialog(null, "Failed to retrieve suppliers from the database.");
        }
    }
}


