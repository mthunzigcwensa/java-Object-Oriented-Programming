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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class StockOrderManager implements StockOrderInterface {
    public String dbUrl = "jdbc:mysql://localhost:3306/bbstore?zeroDateTimeBehavior=CONVERT_TO_NULL";
    public String dbUser = "root";
    public String dbPassword = "M2nzie@emkay";

 
    @Override
    public void addStockOrder(String stockOrdered, int quantityOrdered) {
        String sql = "INSERT INTO WareHouseOrders (stock_ordered, quantity_ordered) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, stockOrdered);
            preparedStatement.setInt(2, quantityOrdered);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Stock order added successfully.");
            } else {
                System.out.println("Failed to add stock order.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeStockOrder(int orderId) {
        String sql = "DELETE FROM WareHouseOrders WHERE order_id = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderId);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Stock order removed successfully.");
            } else {
                System.out.println("Stock order not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayStockOrders(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear the existing data in the table

        String sql = "SELECT order_id, stock_ordered, quantity_ordered, order_date FROM WareHouseOrders";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                String stockOrdered = resultSet.getString("stock_ordered");
                int quantityOrdered = resultSet.getInt("quantity_ordered");
                String orderDate = resultSet.getString("order_date");
// order_id   stock_ordered   quantity_ordered  order_date
                model.addRow(new Object[]{orderId, stockOrdered, quantityOrdered, orderDate});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

