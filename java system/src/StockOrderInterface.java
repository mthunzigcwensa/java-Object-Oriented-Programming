/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author nxuma
 */
import javax.swing.JTable;

public interface StockOrderInterface {
    void addStockOrder(String stockOrdered, int quantityOrdered);
    void removeStockOrder(int stockIds);
    void displayStockOrders(JTable table);
}

