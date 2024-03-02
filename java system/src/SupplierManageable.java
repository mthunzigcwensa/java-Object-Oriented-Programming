/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author nxuma
 */
import javax.swing.JTable;

public interface SupplierManageable {
    void addSupplier(String supplierName, String supplierEmail);
    void removeSupplier(String supplierEmail);
    void displayAllSuppliers(JTable table);
    void updateSupplier(String supplierEmail, String newSupplierName, String newSupplierEmail);
}


