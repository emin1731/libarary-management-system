// Represents a form for editing profile book details including time spent, start and end dates, rating, review, and status.
package GUI;

import javax.security.auth.Refreshable;
import javax.swing.*;
import javax.swing.border.Border;

import classes.Book;
import classes.ProfileBook;
import classes.Review;
import database.GeneralDB;
import database.PersonalDB;
import exceptions.InvalidRatingException;

import java.awt.*;
import java.time.LocalDate;
import java.awt.event.*;


public class EditProfileBook extends JFrame {
    private ProfileBook profileBook;
    private Book book;
    private JComboBox<ProfileBook.Status> statusComboBox;
    private JLabel titleLabel, authorLabel, timeSpentLabel, startDateLabel, endDateLabel, ratingLabel, reviewLabel;
    private JTextField timeSpentField, ratingField, reviewField;
    private JPanel startDatePicker, endDatePicker;
    private JButton saveButton;
    private String username;
    Refreshable parentFrame;

    public EditProfileBook(Book book, String username, Refreshable parentFrame) {
        ProfileBook profileBook = new ProfileBook(book);


        this.username = username;
        this.profileBook = profileBook;
        this.book = book;
        this.parentFrame = parentFrame;

        setTitle("Profile Book Form");
        setLayout(new GridLayout(9, 2, 10, 10)); // Adjust grid layout based on your needs
        setPreferredSize(new Dimension(600, 400)); // Set preferred size

        // Labels
        titleLabel = new JLabel("Title: " + profileBook.getTitle());
        authorLabel = new JLabel("Author: " + profileBook.getAuthor());
        timeSpentLabel = new JLabel("Time Spent:");
        startDateLabel = new JLabel("Start Date:");
        endDateLabel = new JLabel("End Date:");
        ratingLabel = new JLabel("Rating:");
        reviewLabel = new JLabel("Review:");

        // Text Fields
        timeSpentField = new JTextField();
        ratingField = new JTextField();
        reviewField = new JTextField();

        // Set background color and border for text fields
        Color textFieldColor = new Color(204, 204, 204); 
        Border textFieldBorder = BorderFactory.createLineBorder(Color.GRAY);
        timeSpentField.setBackground(textFieldColor);
        timeSpentField.setBorder(textFieldBorder);
        ratingField.setBackground(textFieldColor);
        ratingField.setBorder(textFieldBorder);
        reviewField.setBackground(textFieldColor);
        reviewField.setBorder(textFieldBorder);

        // Status Combo Box
        statusComboBox = new JComboBox<>(ProfileBook.Status.values());

        // Date Pickers
        startDatePicker = createDatePicker();
        endDatePicker = createDatePicker();

        // Add labels and fields to the frame
        add(titleLabel);
        add(authorLabel);
        add(timeSpentLabel);
        add(timeSpentField);
        add(startDateLabel);
        add(startDatePicker);
        add(endDateLabel);
        add(endDatePicker);
        add(ratingLabel);
        add(ratingField);
        add(reviewLabel);
        add(reviewField);
        add(new JLabel("Status:"));
        add(statusComboBox);

        // Save Button
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle save action
                saveProfileBook();
            }
        });


        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setBackground(new Color(34, 139, 34)); // Forest green
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorder(BorderFactory.createRaisedBevelBorder());
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                saveButton.setBackground(new Color(30, 130, 76));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                saveButton.setBackground(new Color(34, 139, 34));
            }
        });

        Font labelFont = new Font("Arial", Font.BOLD, 12);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 12);

        titleLabel.setFont(labelFont);
        authorLabel.setFont(labelFont);
        timeSpentLabel.setFont(labelFont);
        startDateLabel.setFont(labelFont);
        endDateLabel.setFont(labelFont);
        ratingLabel.setFont(labelFont);
        reviewLabel.setFont(labelFont);
        timeSpentField.setFont(textFieldFont);
        ratingField.setFont(textFieldFont);
        reviewField.setFont(textFieldFont);

        // Add Save button to the frame
        add(saveButton);

        // Disable save button initially
        saveButton.setEnabled(false);

        // Add focus listeners to text fields
        addTextFieldFocusListeners();

        pack();
        setLocationRelativeTo(null);
    }

    // Add focus listeners to text fields to enable/disable save button based on field completion
    private void addTextFieldFocusListeners() {
        FocusListener focusListener = new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                checkFieldsCompletion();
            }
        };

        timeSpentField.addFocusListener(focusListener);
        ratingField.addFocusListener(focusListener);
        reviewField.addFocusListener(focusListener);
    }

    // Check if all text fields are filled to enable/disable the save button
    private void checkFieldsCompletion() {
        boolean allFilled = !timeSpentField.getText().isEmpty() &&
                            !ratingField.getText().isEmpty() &&
                            !reviewField.getText().isEmpty();

        saveButton.setEnabled(allFilled);
    }

    // Method to save the profile book
    private void saveProfileBook() {
        profileBook.setTimeSpent(Integer.parseInt(timeSpentField.getText()));
        profileBook.setStartDate(getDateFromPicker(startDatePicker));
        profileBook.setEndDate(getDateFromPicker(endDatePicker));
        try {

            profileBook.setRating(Integer.parseInt(ratingField.getText()));
            
        } catch (InvalidRatingException e) {
            System.out.println("Rating in EditProfileBook is zero");
        }

        profileBook.setReview(reviewField.getText());
        profileBook.setStatus((ProfileBook.Status) statusComboBox.getSelectedItem());

        PersonalDB.writePersonalBookToCSV(profileBook, "src/data/users/" + this.username + ".csv");
        

        GeneralDB generalDB = new GeneralDB("src/data/GeneralDatabase.csv");
        
        Review review = new Review(profileBook.getRating(), username, profileBook.getReview());
        Book newBook = this.book.clone();
        newBook.addReview(review);
        try {
            generalDB.updateBook(newBook);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            this.parentFrame.refresh();
            
        } catch (Exception e) {
        }

        dispose();
    }

    // Create date picker panel
    private JPanel createDatePicker() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> dayCombo = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"});
        JComboBox<String> monthCombo = new JComboBox<>(new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
        JTextField yearField = new JTextField(4);
        yearField.setPreferredSize(new Dimension(yearField.getPreferredSize().width, 30));

        panel.add(dayCombo);
        panel.add(monthCombo);
        panel.add(yearField);
        return panel;
    }

    // Get LocalDate from date picker panel
    @SuppressWarnings("unchecked")
    private LocalDate getDateFromPicker(JPanel datePickerPanel) {
        JComboBox<String> dayCombo = (JComboBox<String>) datePickerPanel.getComponent(0);
        JComboBox<String> monthCombo = (JComboBox<String>) datePickerPanel.getComponent(1);
        JTextField yearField = (JTextField) datePickerPanel.getComponent(2);

        int day = Integer.parseInt((String) dayCombo.getSelectedItem());
        int month = monthCombo.getSelectedIndex() + 1;
        int year = Integer.parseInt(yearField.getText());

        return LocalDate.of(year, month, day);
    }

}
