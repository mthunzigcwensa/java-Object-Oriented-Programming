
import javax.swing.JTable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author nxuma
 */

public interface manageable {
    void addUser(String email, String name, String password);
    void removeUser(String email);
    void displayAllUsers(JTable table);
    
    // New method to authenticate a user by email and password
    boolean loginUser(String email, String password);
}
