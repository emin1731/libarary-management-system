package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import classes.Book;

public class ReviewsView extends JFrame {
    public ReviewsView(Book book) {
        this.setPreferredSize(new Dimension(700, 100));
        this.setLocationRelativeTo(null); // Center the window on the screen
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window, not the entire application

        Color bgColor = new Color(0x1A4D2E);
        Color textColor = new Color(0xF5EFE6);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(bgColor);

        JLabel titleLabel = new JLabel("Title: " + book.getTitle());
        titleLabel.setForeground(textColor);
        JLabel authorLabel = new JLabel("Author: " + book.getAuthor());
        authorLabel.setForeground(textColor);

        panel.add(titleLabel);
        panel.add(authorLabel);

        this.add(panel);

        this.pack();
        this.setVisible(true);
    }
}