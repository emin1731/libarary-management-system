package GUI;


import javax.swing.*;

import classes.Book;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditBook extends JFrame {
    private Book book;

    private JTextField titleField;
    private JTextField authorField;
    // Add more fields for other variables if needed

    public EditBook(Book book) {
        this.book = book;
        setTitle("Update Book");
        setSize(400, 200);
        setLayout(new GridLayout(0, 2));

        // Initialize JTextFields with book data
        titleField = new JTextField(this.book.getTitle());
        authorField = new JTextField(book.getAuthor());
        // Initialize more fields for other variables if needed

        // Add JTextFields to the frame
        add(new JLabel("Title:"));
        add(titleField);
        add(new JLabel("Author:"));
        add(authorField);
        // Add more labels and fields for other variables if needed

        // Add Edit and Delete buttons
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        add(editButton);
        add(deleteButton);

        // Add action listeners for the buttons
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the book object with new data from JTextFields
                book.setTitle(titleField.getText());
                book.setAuthor(authorField.getText());
                // Update other variables if needed

                // Perform any other actions you want when editing a book
                // For example, you can update the book in the database or table
                // You may also want to close this JFrame after editing
                // For now, let's just print the updated book data
                System.out.println("Book updated: " + book);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform delete operation here
                // For now, let's just print a message
                System.out.println("Book deleted: " + book);
            }
        });
    }

    // Main method for testing
    public static void main(String[] args) {
        // Create a sample book
        Book sampleBook = new Book("123", "Sample Title", "Aouthor", null, null);

        // Create and display the update frame
        EditBook updateFrame = new EditBook(sampleBook);
        updateFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        updateFrame.setVisible(true);
    }
}