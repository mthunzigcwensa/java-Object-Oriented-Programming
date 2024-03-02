/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nxuma
 */
public class User {
    
        private String email; // Private variable to store the user's email.
    private String name;  // Private variable to store the user's name.
    private String password; // Private variable to store the user's password.

    // Constructor for the User class.
    // It initializes the instance variables with the provided values.
    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    // Getters and setters for the private variables.

    // Getter method for retrieving the user's email.
    public String getEmail() {
        return email;
    }

    // Setter method for setting the user's email.
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter method for retrieving the user's name.
    public String getName() {
        return name;
    }

    // Setter method for setting the user's name.
    public void setName(String name) {
        this.name = name;
    }

    // Getter method for retrieving the user's password.
    public String getPassword() {
        return password;
    }

    // Setter method for setting the user's password.
    public void setPassword(String password) {
        this.password = password;
    }
    
}
