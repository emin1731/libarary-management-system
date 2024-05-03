
package classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Book
 */
public class Book implements Serializable, Cloneable{
    protected String id;
    protected String title;
    protected String author;
    protected ArrayList<Integer> ratings;
    protected ArrayList<Review> reviews;

    public Book(String id, String title, String author, ArrayList<Integer> ratings, ArrayList<Review> reviews) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.ratings = ratings;
        this.reviews = reviews;
    }
    public double getAverageRating() {
        if (this.ratings.isEmpty()) {
            return -1.0;
        }
        double sum = 0;
        for (Integer integer : this.ratings) {
            sum += integer;
        }
        return (Double) sum / this.ratings.size();
    }
    public String getReviewsUsersString() {
        if (reviews.isEmpty()) {
            return "No Reviews";
        }
        String res = "";
        for (Review review : reviews) {
            res += review + " ";
        }
        return res;
    }

    


    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author=" + author + ", ratings=" + ratings + ", reviews="
                + reviews + "]";
    }

    public void setTitle(String title) {
        this.title = title;
    }


   public String getId() {
       return id;
   }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public ArrayList<Integer> getRatings() {
        return ratings;
    }
    public ArrayList<Review> getReviews() {
        return reviews;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    
}

