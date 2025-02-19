/**
 * The ⁠ Book ⁠ class represents individual book in general database
 */
package classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Book
 */
public class Book implements Serializable, Cloneable {
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
        double sum = 0;

        for (Review rating : reviews) {
            sum += rating.getRating();
        }

        if (sum == 0) {
            return -1.0;
        }
        return (Double) sum / this.reviews.size();
    }
    

    public String getReviewsUsersString() {
        if (reviews.isEmpty()) {
            return "No Reviews";
        }
    
        String res = "";
        int count = 0;
        for (Review review : reviews) {
            if (count < 3) {
                res += review.getName() + " ";
                count++;
            } else {
                res += "...";
                break;
            }
        }
        return res.trim(); // Remove trailing space if any
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
    public Book clone() {
        try {
            Book clonedBook = (Book) super.clone();
            // Make a deep copy of ratings
            clonedBook.ratings = new ArrayList<>(this.ratings);
            // Make a deep copy of reviews
            clonedBook.reviews = new ArrayList<>();
            for (Review review : this.reviews) {
                clonedBook.reviews.add(new Review(review));
            }
            return clonedBook;
        } catch (CloneNotSupportedException e) {
            // This should never happen since Book implements Cloneable
            throw new InternalError(e);
        }
    }


    public void setId(String id) {
        this.id = id;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setRatings(ArrayList<Integer> ratings) {
        this.ratings = ratings;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }
    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public Review deleteReviewByUsername(String username) {
        Iterator<Review> reviewIterator = reviews.iterator();
        while (reviewIterator.hasNext()) {
            Review review = reviewIterator.next();
            if (review.getName().equals(username)) {
                reviewIterator.remove(); // Use iterator's remove method
                return review; // Return the removed review (optional)
            }
        }
        return null; // Indicate review not found
    }
}
