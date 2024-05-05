package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import classes.ProfileBook;
import classes.ProfileBook.Status;
import classes.Review;
import utils.SerializationUtils;




public class PersonalDB {

  public static void createNewPersonalDB(String filename) {
    try (FileWriter writer = new FileWriter(filename)) {
        // Write header row
        writer.append("Id,Title,Author,Ratings,Reviews,Status,Time Spent,Start Date,End Date,User Rating,User Review");
        writer.append("\n");


        writer.flush();
    } catch (IOException e) {
        e.printStackTrace();
    }
  }
  public static void writePersonalBookToCSV(ProfileBook profileBook, String fileName) {
    try (FileOutputStream outputStream = new FileOutputStream(fileName, true);
          BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
        // Write header line on first write
        if (new File(fileName).length() == 0) {
            writer.write("Id,Title,Author,Ratings,Reviews,Status,Time Spent,Start Date, End Date, User Rating, User Review\n");
        }

        StringBuilder csvLine = new StringBuilder();
        csvLine.append(profileBook.getId()).append(",");
        csvLine.append(profileBook.getTitle()).append(",");
        csvLine.append(profileBook.getAuthor()).append(",");
        csvLine.append(SerializationUtils.serializeObjectToString(profileBook.getRatings())).append(",");
        csvLine.append(SerializationUtils.serializeObjectToString(profileBook.getReviews())).append(",");
        csvLine.append(profileBook.getStatus()).append(",");
        csvLine.append(profileBook.getTimeSpent()).append(",");
        csvLine.append(SerializationUtils.serializeObjectToString(profileBook.getStartDate())).append(",");
        csvLine.append(SerializationUtils.serializeObjectToString(profileBook.getEndDate())).append(",");
        csvLine.append(profileBook.getRating()).append(",");
        csvLine.append(profileBook.getReview());

        writer.write(csvLine.toString() + "\n");
        writer.flush();
    } catch (IOException e) {
        System.err.println("Error writing to CSV file: " + e.getMessage());
    }
  }
  
  public static ArrayList<ProfileBook> readPersonalBooksFromCSV(String fileName) {
    ArrayList<ProfileBook> profileBooks = new ArrayList<>();
  
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      reader.readLine(); // Skip header line
      String line;
      while ((line = reader.readLine()) != null) {
        String[] data = line.split(",");
        String id = data[0];
        String title = data[1];
        String author = data[2];
        ArrayList<Integer> ratings = SerializationUtils.deserializeArrayListFromString(data[3]);

        ArrayList<Review> reviews = SerializationUtils.deserializeArrayListFromString(data[4]);

        Status status = Status.valueOf(data[5]);
        Integer timeSpent = Integer.parseInt(data[6]);
        LocalDate startDate = SerializationUtils.deserializeObjectFromString(data[7]);
        LocalDate endDate = SerializationUtils.deserializeObjectFromString(data[8]);
        Integer userRating = Integer.parseInt(data[9]);
        String userReview = data[10];
    
        ProfileBook profileBook = new ProfileBook(id, title, author, ratings, reviews, status, timeSpent, startDate, endDate, userRating, userReview);
        profileBooks.add(profileBook);
      }
    } catch (IOException e) {
      System.err.println("Error reading CSV file: " + e.getMessage());
    }
  
    return profileBooks;
  }
  public static void updateBook(String fileName, ProfileBook updatedProfileBook) throws IOException {
      ArrayList<ProfileBook> profileBooks = readPersonalBooksFromCSV(fileName);
    
      int index = -1;
      for (int i = 0; i < profileBooks.size(); i++) {
        if (profileBooks.get(i).getId().equals(updatedProfileBook.getId())) {
          index = i;
          break;
        }
      }
    
      if (index != -1) {
        profileBooks.set(index, updatedProfileBook); // Update the book object in the list
      } else {
        // Handle case where book to update is not found (optional)
        System.err.println("Profile Book was not found: " + updatedProfileBook.getClass());
      }
      try (FileOutputStream outputStream = new FileOutputStream(fileName);
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
      writer.write("Id,Title,Author,Ratings,Reviews,Status,Time Spent,Start Date, End Date, User Rating, User Review\n");
      for (ProfileBook profileBook : profileBooks) {
        writePersonalBookToCSV(profileBook, fileName); // Assuming writeBookToCSV handles writing a book object to a line
      }
      writer.flush();
      }
    }

    public static void deleteBook(String fileName, String profileBookId) throws IOException {
      ArrayList<ProfileBook> profileBooks = readPersonalBooksFromCSV(fileName);
      ArrayList<ProfileBook> newProfileBooks = new ArrayList<>();
    
      for (ProfileBook profileBook : profileBooks) {
      //   System.out.println(book.toString());
        if (!profileBook.getId().equals(profileBookId)) {
          newProfileBooks.add(profileBook);
        }
        else {
          System.out.println("DELETE FOUND -- " + profileBook.toString());
        }
      }
      // Rewrite the CSV file excluding the deleted book
      try (FileOutputStream outputStream = new FileOutputStream(fileName);
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
      writer.write("Id,Title,Author,Ratings,Reviews\n");
      for (ProfileBook profileBook : newProfileBooks) {
        writePersonalBookToCSV(profileBook, fileName); // Assuming writeBookToCSV handles writing a book object to a line
      }
      writer.flush();
      }
    }

    public static void main(String[] args) {
      ArrayList<ProfileBook> profileBooks = readPersonalBooksFromCSV("src\\data\\personal_books.csv");
      for (ProfileBook profileBook : profileBooks) {
          System.out.println(profileBook.toString());
      }
  }
}
    


