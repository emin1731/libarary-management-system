package utils;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Book
 */
public class Book implements Serializable{
    String title;
    String author;
    // int[] ratings;
    Review[] reviews;
    public Book(String title, String author, Review[] reviews) {
        this.title = title;
        this.author = author;
        // this.ratings = ratings;
        this.reviews = reviews;
    }
    
    @Override
    public String toString() {
        return "Book [title=" + title + ", author=" + author + ", reviews="
                + Arrays.toString(reviews) + "]";
    }

    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    // public int[] getRatings() {
    //     return ratings;
    // }
    public Review[] getReviews() {
        return reviews;
    }
    
    
}

