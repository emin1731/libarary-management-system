package utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Base64;



public class GeneralDB {
    public static void main(String[] args) {
        Review review1 = new Review(4, "User123", "fiftyfifty");
        Review review2 = new Review(4, "User331", "fiftyfif- - - - ");
        Review review3 = new Review(1, "User223", "dkjfbnkjfnbenf");
        Review[] reviews1 = {review1, review2, review3};
        Review[] reviews2 = {review3, review1};
        Book book1 = new Book("Zolushka", "Alex Nevskiy", reviews1);
        Book book2 = new Book("Kristina", "Playboi carti", reviews2);

        writeBookToCSV(book1, "data/new_sample.csv");
        writeBookToCSV(book2, "data/new_sample.csv");
        
        ArrayList<Book> res = readBooksFromCSV("data/new_sample.csv");
        for (Book book : res) {
            System.out.println(book.toString());
        }


    }

    private static void writeBookToCSV(Book book, String fileName) {
        try (FileOutputStream outputStream = new FileOutputStream(fileName, true);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            // Write header line on first write
            if (new File(fileName).length() == 0) {
                writer.write("Title,Author,Reviews\n");
            }
    
            StringBuilder csvLine = new StringBuilder();
            csvLine.append(book.getTitle()).append(",");
            csvLine.append(book.getAuthor()).append(",");

            String reviewsString = serializeObjectToString(book.getReviews());
    
            csvLine.append(reviewsString);
            writer.write(csvLine.toString() + "\n");
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }
    
    private static ArrayList<Book> readBooksFromCSV(String fileName) {
        ArrayList<Book> books = new ArrayList<>();
      
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
          reader.readLine(); // Skip header line
          String line;
          while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
      
            String title = data[0];
            String author = data[1];
            Review[] reviews = (Review[]) deserializeObjectFromString(data[2]);
      
            Book book = new Book(title, author, reviews);
            books.add(book);
          }
        } catch (IOException e) {
          System.err.println("Error reading CSV file: " + e.getMessage());
        }
      
        return books;
      }
      
    
    
    
    // Helper method to serialize an object to a String
    private static String serializeObjectToString(Object obj) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Helper method to deserialize an object to a String
    private static Object deserializeObjectFromString(String serializedString) {
        byte[] bytes = Base64.getDecoder().decode(serializedString);
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
    


