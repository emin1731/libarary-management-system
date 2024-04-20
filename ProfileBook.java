import java.time.LocalDate;
import java.util.ArrayList;

enum Status{
    NOT_STARTED,
    ONGOING,
    COMPLETED
}

public class ProfileBook extends Book{
    private Status status;
    private Integer timeSpent;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer rating;
    private String review;

    public ProfileBook(Book book, Status status, Integer timeSpent, LocalDate startDate, LocalDate endDate, Integer rating, String review) {
        super(book.id, book.title, book.author, book.ratings, book.reviews);
        this.status = status;
        this.timeSpent = timeSpent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rating = rating;
        this.review = review;
    }

    public ProfileBook(String id, String title, String author, ArrayList<Integer> ratings, ArrayList<Review> reviews,
            Status status, Integer timeSpent, LocalDate startDate, LocalDate endDate, Integer rating, String review) {
        super(id, title, author, ratings, reviews);
        this.status = status;
        this.timeSpent = timeSpent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rating = rating;
        this.review = review;
    }

    

    @Override
    public String toString() {
        return "ProfileBook [id=" + id + ", title=" + title + ", status=" + status + ", author=" + author
                + ", timeSpent=" + timeSpent + ", startDate=" + startDate + ", endDate=" + endDate + ", rating="
                + rating + ", review=" + review + "]";
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTimeSpent(Integer timeSpent) {
        this.timeSpent = timeSpent;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Status getStatus() {
        return status;
    }

    public Integer getTimeSpent() {
        return timeSpent;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Integer getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }
    


    

    
}
