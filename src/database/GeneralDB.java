package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.UUID;

import classes.Book;
import classes.Review;
import utils.SerializationUtils;


public class GeneralDB {
  private String filename;

    public GeneralDB(String filename) {
      this.filename = filename;
    }

    public static void main(String[] args) {
      // initialCreation("data/brodsky.csv", "data/GeneralDatabase.csv");
    }
    public void initialCreation(String source) {
      ArrayList<Book> init = new ArrayList<>();

      try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
        reader.readLine(); // Skip header line
        String line;
        String title = "NOT_INIT";
        String author;
        while ((line = reader.readLine()) != null) {
          String[] data = line.split(",");

          author = data[data.length - 1].trim();
          if (data.length <= 1 || data[data.length - 1].trim() == "") {
            author = "Unknown";  
          }

          if (data.length == 1) {
            title = data[0];
            init.add(new Book(UUID.randomUUID().toString(), title, author, new ArrayList<Integer>(), new ArrayList<Review>()));
          }
          else {
            for (int i = 0; i < data.length - 1; i++) {
              String[] titles = data[i].split("\\s*,\\s*");
                for (String titleItem : titles) {
                  titleItem = titleItem.trim().replaceAll("^\"|\"$|['\"]", "");
                  if (titleItem == "") {
                    titleItem = "Unknown";
                  }
                  ArrayList<String> rowData = new ArrayList<>();
                  rowData.add(titleItem.trim());
                  title = titleItem.trim();
                  rowData.add(author);
                  init.add(new Book(UUID.randomUUID().toString(), title, author, new ArrayList<Integer>(), new ArrayList<Review>()));
              }
            }
          }
        }
      } catch (IOException e) {
        System.err.println("Error reading CSV file: " + e.getMessage());
      }

      for (Book book : init) {
        writeBookToCSV(book);
      }
    } 
    
    public ArrayList<Book> readBooksFromCSV() {
      ArrayList<Book> books = new ArrayList<>();
    
      try (BufferedReader reader = new BufferedReader(new FileReader(this.filename))) {
        reader.readLine(); // Skip header line
        String line;
        while ((line = reader.readLine()) != null) {
          String[] data = line.split(",");
          String id = data[0];
          String title = data[1];
          String author = data[2];
          ArrayList<Integer> ratings = SerializationUtils.deserializeObjectFromString(data[3]);

          ArrayList<Review> reviews = SerializationUtils.deserializeObjectFromString(data[4]);
    
          Book book = new Book(id, title, author, ratings, reviews);
          books.add(book);
        }
      } catch (IOException e) {
        System.err.println("Error reading CSV file: " + e.getMessage());
      }
    
      return books;
    }

    public void writeBookToCSV(Book book) {
        try (FileOutputStream outputStream = new FileOutputStream(this.filename, true);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            // Write header line on first write
            if (new File(this.filename).length() == 0) {
                writer.write("Id,Title,Author,Ratings,Reviews\n");
            }
    
            StringBuilder csvLine = new StringBuilder();
            csvLine.append(book.getId()).append(",");
            csvLine.append(book.getTitle()).append(",");
            csvLine.append(book.getAuthor()).append(",");
            csvLine.append(SerializationUtils.serializeObjectToString(book.getRatings())).append(",");
            csvLine.append(SerializationUtils.serializeObjectToString(book.getReviews()));

            writer.write(csvLine.toString() + "\n");
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }
    
    

    public void updateBook(Book updatedBook) throws IOException {
        ArrayList<Book> books = readBooksFromCSV();
      
        int index = -1;
        for (int i = 0; i < books.size(); i++) {
          if (books.get(i).getId().equals(updatedBook.getId())) {
            index = i;
            break;
          }
        }
      
        if (index != -1) {
          books.set(index, updatedBook); // Update the book object in the list
        } else {
          // Handle case where book to update is not found (optional)
          System.err.println("Book was not found: " + updatedBook.getClass());
        }
        try (FileOutputStream outputStream = new FileOutputStream(this.filename);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
        writer.write("Id,Title,Author,Ratings,Reviews\n");
        for (Book book : books) {
          writeBookToCSV(book); // Assuming writeBookToCSV handles writing a book object to a line
        }
        writer.flush();
        }
      }

      public void deleteBook(String bookId) throws IOException {
        ArrayList<Book> books = readBooksFromCSV();
        ArrayList<Book> newBooks = new ArrayList<>();
      
        for (Book book : books) {
          System.out.println(book.toString());
          if (!book.getId().equals(bookId)) {
            newBooks.add(book);
          }
          else {
            System.out.println("FOUND -- " + book.toString());
          }
        }
        // Rewrite the CSV file excluding the deleted book
        try (FileOutputStream outputStream = new FileOutputStream(this.filename);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
        writer.write("Id,Title,Author,Ratings,Reviews\n");
        for (Book book : newBooks) {
          writeBookToCSV(book); // Assuming writeBookToCSV handles writing a book object to a line
        }
        writer.flush();
        }
      }



      
}
    


