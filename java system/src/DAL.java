/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ACER I5 11G
 */
import java.sql.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DAL {
    private Connection connection;
    private String jdbcUrl = "jdbc:mysql://localhost:3306/bbstore?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private String username = "root";
    private String password = "M2nzie@emkay";

    // Constructor to establish the database connection
    public DAL() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (ClassNotFoundException | SQLException e) {
        }
    }

    // Method to insert a new movie record into the database
    public void insertMovie(String title, String director, int year) {
        try {
            String query = "INSERT INTO movies (title, director, year) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, director);
                preparedStatement.setInt(3, year);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
        }
    }

    // Method to update an existing movie record in the database
    public void updateMovie(int movieId, String title, String director, int year) {
        try {
            String query = "UPDATE movies SET title=?, director=?, year=? WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, director);
                preparedStatement.setInt(3, year);
                preparedStatement.setInt(4, movieId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
        }
    }

    // Method to delete an existing movie record from the database
    public void deleteMovie(int movieId) {
        try {
            String query = "DELETE FROM movies WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, movieId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
        }
    }

    // Method to display all movies stored in the database
    public void displayMoviesInTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear the existing data in the table

        try {
            String query = "SELECT * FROM movies";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String director = resultSet.getString("director");
                    int year = resultSet.getInt("year");

                    // Add a new row to the table model with movie information
                    model.addRow(new Object[]{id, title, director, year});
                }
            }
        } catch (SQLException e) {
           
        }
    }


    // Method to search for a movie by title
    public void searchMovieByTitle(String title, JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear the existing data in the table

        try {
            String query = "SELECT * FROM movies WHERE title=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, title);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String director = resultSet.getString("director");
                        int year = resultSet.getInt("year");

                        // Add a new row to the table model with movie information
                        model.addRow(new Object[]{id, title, director, year});
                    }
                }
            }
        } catch (SQLException e) {
            
        }
    }

    // Close the database connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
        }
    }
}

