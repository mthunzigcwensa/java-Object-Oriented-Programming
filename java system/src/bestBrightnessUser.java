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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class bestBrightnessUser extends User implements manageable {
    private String userRole;
    private List<User> userList = new ArrayList<>();

    public bestBrightnessUser(String email, String name, String password, String userRole) {
        super(email, name, password);
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    // Implement the methods from the Manageable interface
    @Override
    public void addUser(String email, String name, String password) {
        // Check if the email already exists in the userList.
        for (User user : userList) {
            if (user.getEmail().equals(email)) {
                System.out.println("Email provided already exists.");
                return;
            }
        }

        // Connect to the database (You need to provide the correct database connection details).
    String dbUrl = "jdbc:mysql://localhost:3306/bbstore?zeroDateTimeBehavior=CONVERT_TO_NULL"; // Replace with your database URL
    String dbUser = "root";
    String dbPassword = "M2nzie@emkay"; // Replace with your database password


        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            // Check if the email already exists in the database.
            String checkQuery = "SELECT email FROM users WHERE email = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setString(1, email);
                ResultSet resultSet = checkStmt.executeQuery();

                if (resultSet.next()) {
                    System.out.println("Email provided already exists in the database.");
                    return;
                }
            }

            // Insert the user data into the database.
// Modify the insertQuery to include the 'userRole' field.
String insertQuery = "INSERT INTO users (email, name, password, role) VALUES (?, ?, ?, ?)";
try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
    insertStmt.setString(1, email);
    insertStmt.setString(2, name);
    insertStmt.setString(3, password);
    insertStmt.setString(4, userRole); // Add the 'userRole' value to the query.
    insertStmt.executeUpdate();
}


            // Add the user to the userList.
            User user = new User(email, name, password);
            userList.add(user);

            System.out.println("User added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to add the user to the database.");
        }
    }

@Override
public void removeUser(String email) {
    // Remove the user from the userList (if it exists).
    userList.removeIf(user -> user.getEmail().equals(email));

    // Connect to the database (You need to provide the correct database connection details).
    String dbUrl = "jdbc:mysql://localhost:3306/bbstore?zeroDateTimeBehavior=CONVERT_TO_NULL"; // Replace with your database URL
    String dbUser = "root";
    String dbPassword = "M2nzie@emkay"; // Replace with your database password


    try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
        // Execute a SQL DELETE statement to remove the user by email.
        String deleteQuery = "DELETE FROM users WHERE email = ?";
        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
            deleteStmt.setString(1, email);
            int rowsAffected = deleteStmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("User with email " + email + " removed from the database.");
            } else {
                System.out.println("User with email " + email + " not found in the database.");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to remove the user from the database.");
    }
}

    @Override
public void displayAllUsers(JTable table) {
    // Connect to the database (You need to provide the correct database connection details).
    String dbUrl = "jdbc:mysql://localhost:3306/bbstore?zeroDateTimeBehavior=CONVERT_TO_NULL"; // Replace with your database URL
    String dbUser = "root";
    String dbPassword = "M2nzie@emkay"; // Replace with your database password

    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0); // Clear existing rows from the table.

    try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
        // Execute a SQL SELECT statement to retrieve all users from the database.
        String selectQuery = "SELECT email, name, role FROM users";
        try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
            ResultSet resultSet = selectStmt.executeQuery();

            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String name = resultSet.getString("name");
                String role = resultSet.getString("role");
                model.addRow(new Object[]{email, name, role});
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to retrieve users from the database.");
    }
}

@Override
public boolean loginUser(String email, String password) {
    // Connect to the database (You need to provide the correct database connection details).
    String dbUrl = "jdbc:mysql://localhost:3306/bbstore?zeroDateTimeBehavior=CONVERT_TO_NULL";
    String dbUser = "root";
    String dbPassword = "M2nzie@emkay";

    try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
        // Execute a SQL SELECT statement to retrieve user by email and password.
        String selectQuery = "SELECT email, password, role FROM users WHERE email = ? AND password = ?";
        try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
            selectStmt.setString(1, email);
            selectStmt.setString(2, password);
            ResultSet resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                String userRole = resultSet.getString("role");
                System.out.println("Login successful for user: " + email + " with role: " + userRole);

                // Redirect users based on their role
                if (userRole.equals("customer")) {
                    
                             customerHome l = new customerHome();
                             l.setVisible(true);
                } else if (userRole.equals("warehouse manager")) {
                             warehousemanager l = new warehousemanager();
                             l.setVisible(true);
                } else if (userRole.equals("sales manager")) {
                             salesmanagerhome l = new salesmanagerhome();
                             l.setVisible(true);
                } else if (userRole.equals("store manager")) {
                             storemanagerHome l = new storemanagerHome();
                             l.setVisible(true);                }

                return true; // Authentication successful
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Login failed due to a database error.");
    }

    System.out.println("Login failed. Invalid email or password.");
    return false; // Authentication failed
}
}
