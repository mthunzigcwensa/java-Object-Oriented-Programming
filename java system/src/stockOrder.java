/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nxuma
 */
public class stockOrder {
    private String stockOrdered;
    private int quantityOrdered;

    // Constructor with both parameters
    public stockOrder(String stockOrdered, int quantityOrdered) {
        this.stockOrdered = stockOrdered;
        this.quantityOrdered = quantityOrdered;
    }

    // Constructor with no parameters (default constructor)
    public stockOrder() {
        // You can initialize default values here if needed
        this.stockOrdered = "";
        this.quantityOrdered = 0;
    }

    // Getter method for stockOrdered
    public String getStockOrdered() {
        return stockOrdered;
    }

    // Setter method for stockOrdered
    public void setStockOrdered(String stockOrdered) {
        this.stockOrdered = stockOrdered;
    }

    // Getter method for quantityOrdered
    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    // Setter method for quantityOrdered
    public void setQuantityOrdered(int quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }
}

