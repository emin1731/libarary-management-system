// import java.awt.Dimension;

// import javax.swing.BorderFactory;
// import javax.swing.JFrame;
// import javax.swing.JPanel;

// public class Login extends JFrame {
//     public Login() {
//         this.setPreferredSize(new Dimension(700, 400));
//         this.pack();
//         this.setLocationRelativeTo(null);; // Center the window on the screen
//         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

//         JPanel container = new JPanel();
//         container.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));





//         this.setVisible(true);
//     }
//     public static void main(String[] args) {
//         new Login();
//     }
// }


// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.*;

// public class LoginPage extends JFrame {
//     private JTextField usernameField;
//     private JPasswordField passwordField;

//     public LoginPage() {
//         setTitle("Login Page");
//         this.setPreferredSize(new Dimension(700, 400));
//         this.pack();
//         this.setLocationRelativeTo(null);; // Center the window on the screen
//         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//         // setLayout(new GridLayout(4, 2));
//         setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

//         JLabel usernameLabel = new JLabel("Username:");
//         JLabel passwordLabel = new JLabel("Password:");
//         JLabel promptLabel = new JLabel("Please enter your username and password:", SwingConstants.CENTER);

//         usernameField = new JTextField();
//         passwordField = new JPasswordField();

//         JButton loginButton = new JButton("Login");
//         JButton registerButton = new JButton("Register");

//         // Adding action listeners
//         loginButton.addActionListener(new ActionListener() {
//             public void actionPerformed(ActionEvent e) {
//                 String username = usernameField.getText();
//                 String password = new String(passwordField.getPassword());
//                 // Perform login authentication here
//                 JOptionPane.showMessageDialog(null, "Login button clicked with username: " + username + " and password: " + password);
//             }
//         });

//         registerButton.addActionListener(new ActionListener() {
//             public void actionPerformed(ActionEvent e) {
//                 // Perform registration action here
//                 JOptionPane.showMessageDialog(null, "Register button clicked");
//             }
//         });

//         // Adding components to the frame
//         add(promptLabel);
//         add(new JLabel()); // Empty cell for spacing
//         add(usernameLabel);
//         add(usernameField);
//         add(passwordLabel);
//         add(passwordField);
//         add(loginButton);
//         add(registerButton);

//         setVisible(true);
//     }

//     public static void main(String[] args) {
//         new LoginPage();
//     }
// }




// import javax.swing.*;
// import java.awt.*;

// public class LoginPage extends JFrame {

//     public LoginPage() {
//         super("Login");
//         // setSize(400, 300);
//         this.setPreferredSize(new Dimension(700, 400));
//         this.pack();
//         this.setLocationRelativeTo(null);; // Center the window on the screen
//         setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

//         JPanel panel = new JPanel(new GridBagLayout());
//         GridBagConstraints gbc = new GridBagConstraints();

//         // Login Title
//         JLabel loginLabel = new JLabel("Login");
//         gbc.gridx = 0;
//         gbc.gridy = 0;
//         gbc.gridwidth = 2;
//         gbc.insets = new Insets(20, 0, 20, 0);
//         panel.add(loginLabel, gbc);

//         // Username Label
//         JLabel usernameLabel = new JLabel("Username");
//         gbc.gridy = 1;
//         gbc.gridwidth = 1;
//         gbc.insets = new Insets(20, 20, 5, 0);
//         panel.add(usernameLabel, gbc);

//         // Username Text Field
//         JTextField usernameField = new JTextField(20);
//         gbc.gridx = 1;
//         gbc.insets = new Insets(20, 0, 5, 20);
//         panel.add(usernameField, gbc);

//         // Password Label
//         JLabel passwordLabel = new JLabel("Password");
//         gbc.gridy = 2;
//         gbc.insets = new Insets(5, 20, 5, 0);
//         panel.add(passwordLabel, gbc);

//         // Password Text Field (password field hides the characters as user types)
//         JPasswordField passwordField = new JPasswordField(20);
//         gbc.gridx = 1;
//         gbc.insets = new Insets(5, 0, 5, 20);
//         panel.add(passwordField, gbc);

//         // Forgot Password Link (can be a button or label)
//         JLabel forgotPasswordLabel = new JLabel("Forgot Password?");
//         gbc.gridy = 3;
//         gbc.gridwidth = 2;
//         gbc.insets = new Insets(5, 20, 10, 20);
//         panel.add(forgotPasswordLabel, gbc);

//         // Login Button
//         JButton loginButton = new JButton("Login");
//         gbc.gridy = 4;
//         gbc.insets = new Insets(10, 20, 0, 20);
//         panel.add(loginButton, gbc);

//         // Not a Member Label
//         JLabel notMemberLabel = new JLabel("Not a Member?");
//         gbc.gridy = 5;
//         gbc.insets = new Insets(10, 20, 10, 0);
//         panel.add(notMemberLabel, gbc);

//         // Sign Up Button
//         JButton signupButton = new JButton("Signup");
//         gbc.gridx = 1;
//         gbc.insets = new Insets(10, 0, 10, 20);
//         panel.add(signupButton, gbc);

//         add(panel);
//         setVisible(true);
//     }

//     public static void main(String[] args) {
//         new LoginPage();
//     }
// }



import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

public class LoginPage implements ActionListener{
	
	JFrame frame = new JFrame();
	JButton loginButton = new JButton("Login");
	JButton resetButton = new JButton("Reset");
	JTextField userIDField = new JTextField();
	JPasswordField userPasswordField = new JPasswordField();
	JLabel userIDLabel = new JLabel("userID:");
	JLabel userPasswordLabel = new JLabel("password:");
	JLabel messageLabel = new JLabel();
	HashMap<String,String> accounts = this.getLoginData("src/data/Accounts.csv");
	
	LoginPage() {
        for (String usernameString : accounts.keySet()) {
            System.out.println(usernameString);
        }


        frame.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setLocationRelativeTo(null);; // Center the window on the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		userIDLabel.setBounds(50,100,75,25);
		userPasswordLabel.setBounds(50,150,75,25);
		
		messageLabel.setBounds(125,250,250,35);
		messageLabel.setFont(new Font(null,Font.ITALIC,25));
		
		userIDField.setBounds(125,100,200,25);
		userPasswordField.setBounds(125,150,200,25);
		
		loginButton.setBounds(125,200,100,25);
		loginButton.setFocusable(false);
		loginButton.addActionListener(this);
		
		resetButton.setBounds(225,200,100,25);
		resetButton.setFocusable(false);
		resetButton.addActionListener(this);
        JPanel panel = new JPanel();
		
		panel.add(userIDLabel);
		panel.add(userPasswordLabel);
		panel.add(messageLabel);
		panel.add(userIDField);
		panel.add(userPasswordField);
		panel.add(loginButton);
		panel.add(resetButton);

		panel.setLayout(null);
        panel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        
		

        frame.add(panel);
		frame.setVisible(true);
		
	}
    


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==resetButton) {
			userIDField.setText("");
			userPasswordField.setText("");
		}
		
		if(e.getSource()==loginButton) {
			
			String userID = userIDField.getText();
			String password = String.valueOf(userPasswordField.getPassword());
			
			if(accounts.containsKey(userID)) {
				if(accounts.get(userID).equals(password)) {
					messageLabel.setForeground(Color.green);
					messageLabel.setText("Login successful");
					frame.dispose();
					// WelcomePage welcomePage = new WelcomePage(userID);
                    System.out.println("WELCOME PAGE");
				}
				else {
					messageLabel.setForeground(Color.red);
					messageLabel.setText("Wrong password");
				}

			}
			else {
				messageLabel.setForeground(Color.red);
				messageLabel.setText("username not found");
			}
		}
	}
    public HashMap<String,String> getLoginData(String fileName) {
        HashMap<String,String> accounts = new HashMap<String,String>();
    
      try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        reader.readLine(); // Skip header line
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            String username = data[0];
            String password = data[1];

            accounts.put(username, password);
        
        }
      } catch (IOException e) {
        System.err.println("Error reading CSV file: " + e.getMessage());
      }
    
      return accounts;
    }
    
    public static void main(String[] args) {
        new LoginPage();
        // for (String username : accounts.keySet()) {
        //     System.out.println(username);
        // }
    }
}
