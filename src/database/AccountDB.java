// AccountDB manages user account information, offering functionalities like user registration, login, and data retrieval from a CSV file.
package database;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import exceptions.EmptyUsernameOrPasswordException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserNotFoundException;

public class AccountDB {
    private String filepath;

    public AccountDB(String filepath) {
        this.filepath = filepath;
    }

    public void registerUser(String username, String password) throws EmptyUsernameOrPasswordException, UserAlreadyExistsException {
        try {
            if (loginUser(username, password)) {
                throw new UserAlreadyExistsException("User already exists");
            }
            
        } catch (UserNotFoundException e1) {
            try (FileOutputStream outputStream = new FileOutputStream(this.filepath, true);
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
                // Write header line on first write
                if (new File(this.filepath).length() == 0) {
                    writer.write("username,password\n");
                }
        
                StringBuilder csvLine = new StringBuilder();
                csvLine.append(username).append(",");
                csvLine.append(password);
    
                writer.write("\n" + csvLine.toString() );
                writer.flush();
            } catch (IOException e2) {
                System.err.println("Error writing to CSV file: " + e2.getMessage());
            }
            
        }

    }

    public boolean loginUser(String username, String password) throws UserNotFoundException, EmptyUsernameOrPasswordException {
        if (username.equals("") || password.equals("")) {
            throw new EmptyUsernameOrPasswordException("Username or password is empty");
        }
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
                System.out.println(data[0] + " " + data[1]);
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
