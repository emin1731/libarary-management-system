package GUI;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import classes.Book;
import GUI.ReviewsView;


public class ReviewsView extends JFrame {
    public ReviewsView(Book book ) {
        // this.setBounds(100, 100, 700, 400);
        this.setPreferredSize(new Dimension(700, 400));
        this.pack();
        this.setLocationRelativeTo(null);; // Center the window on the screen
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window, not the entire application
        // this.getContentPane().setBorder(BorderFactory.createEmptyBorder(10,10,10,10))
        // for (Review review : book ) {
        //     JLabel label = new JLabel(review.getName());
        //     this.getContentPane().add(label);
            
        // }
        JPanel header = new JPanel();
        header.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));




        JLabel title = new JLabel(book.getTitle());
        header.add(title);

        JLabel author = new JLabel(book.getAuthor());
        header.add(author);

        this.getContentPane().add(header);


        
        this.setVisible(true);
    }
}