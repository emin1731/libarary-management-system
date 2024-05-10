package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import classes.Book;
import classes.Review;

public class ReviewsPage extends JFrame {
    public ReviewsPage(Book book) {
        ResourceBundle bundle = ResourceBundle.getBundle("Messages", LocaleChanger.getCurrentLocale());
        this.setTitle(bundle.getString("reviewsPage.title"));
        this.setPreferredSize(new Dimension(500, 400));

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(0xE8DFCA));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets.top = 5;
        gbc.insets.bottom = 5;

        JLabel titleLabel = new JLabel(bundle.getString("reviewsPage.bookTitle") + ": " + book.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, gbc);

        gbc.gridy++;
        JLabel authorLabel = new JLabel(bundle.getString("reviewsPage.bookAuthor") + ": " + ((!book.getAuthor().equals("Unknown")) ? book.getAuthor() : bundle.getString("generalDb.unknown")));
        authorLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(authorLabel, gbc);

        gbc.gridy++;
        JLabel ratingsLabel = new JLabel(bundle.getString("reviewsPage.averageRating") + ": " + ((book.getAverageRating() != -1) ? book.getAverageRating() : bundle.getString("generalDb.noRatings")));
        ratingsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(ratingsLabel, gbc);

        gbc.gridy++;
        JLabel reviewsLabel = new JLabel(bundle.getString("reviewsPage.Reviews") + ": ");
        reviewsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(reviewsLabel, gbc);

        ArrayList<Review> reviews = book.getReviews();
        gbc.gridy++;
        for (Review review : reviews) {
            JLabel reviewLabel = new JLabel(bundle.getString("reviewsPage.reviewUsername") + ": " + review.getName() + ", " + bundle.getString("reviewsPage.reviewRating") + ": " + review.getRating() + ", " + bundle.getString("reviewsPage.reviewDescription") + ": " + review.getComment());
            reviewLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            panel.add(reviewLabel, gbc);
            gbc.gridy++;
        }


        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
