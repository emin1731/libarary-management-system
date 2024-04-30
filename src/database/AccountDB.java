package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import exceptions.UserNotFoundException;

public class AccountDB {
    private String filepath;


    public AccountDB(String filepath) {
        this.filepath = filepath;
    }

    public void registerUser(String username, String password) {
        try (FileOutputStream outputStream = new FileOutputStream(this.filepath, true);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            // Write header line on first write
            if (new File(this.filepath).length() == 0) {
                writer.write("username,password\n");
            }
    
            StringBuilder csvLine = new StringBuilder();
            csvLine.append(username).append(",");
            csvLine.append(password);

            writer.write(csvLine.toString() + "\n");
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    public boolean loginUser(String username, String password) throws UserNotFoundException{
        HashMap<String,String> users = getAllUsers();

        if(users.containsKey(username)) {
				if(users.get(username).equals(password)) {
					return true;
				}
				else {
					return false;
				}

			}
        else {
            throw new UserNotFoundException("Username was not found");
        }
    }

    public HashMap<String, String> getAllUsers() {
        HashMap<String,String> users = new HashMap<String,String>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            reader.readLine(); // Skip header line
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String username = data[0];
                String password = data[1];
    
                users.put(username, password);
            
            }
          } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
          }
          return users;
    }

}
