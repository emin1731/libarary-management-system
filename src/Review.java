

import java.io.Serializable;

/**
 * Review
 */
public class Review implements Serializable{
    int rating;
    String name;
    String comment;
    
    public Review(int rating, String name, String comment) {
        this.rating = rating;
        this.name = name;
        this.comment = comment;
    }
    
}