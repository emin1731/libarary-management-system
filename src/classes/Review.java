// Represents a review with attributes including rating, reviewer name, and comment.
package classes;

import java.io.Serializable;

/**
 * Review
 */
public class Review implements Serializable{
    private int rating;
    private String name;
    private String comment;
    
    public Review(int rating, String name, String comment) {
        this.rating = rating;
        this.name = name;
        this.comment = comment;
    }
    // Deep copy constructor
    public Review(Review other) {
        this.rating = other.rating;
        this.name = other.name != null ? new String(other.name) : null; // Create a new String object
        this.comment = other.comment != null ? new String(other.comment) : null; // Create a new String object
    }


    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    
}