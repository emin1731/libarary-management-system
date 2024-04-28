package components;
// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.IOException;
// import java.util.HashMap;

// import javax.swing.JButton;
// import javax.swing.JComponent;
// import javax.swing.JFrame;
// import javax.swing.JLabel;
// import javax.swing.JPanel;
// import javax.swing.JPasswordField;
// import javax.swing.JTextField;

// import java.awt.*;
// import java.awt.event.*;
// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.IOException;
// import java.util.*;
// import javax.swing.*;


// public class RegisterPage implements ActionListener{
    
// 	JFrame frame = new JFrame();
// 	JButton loginButton = new JButton("Login");
// 	JButton resetButton = new JButton("Reset");
// 	JTextField userIDField = new JTextField();
// 	JPasswordField userPasswordField = new JPasswordField();
// 	JLabel userIDLabel = new JLabel("userID:");
// 	JLabel userPasswordLabel = new JLabel("password:");
// 	JLabel messageLabel = new JLabel();
// 	HashMap<String,String> accounts = this.getLoginData("src/data/Accounts.csv");
	
// 	RegisterPage() {
//         for (String usernameString : accounts.keySet()) {
//             System.out.println(usernameString);
//         }


//         frame.setPreferredSize(new Dimension(400, 400));
//         frame.pack();
//         frame.setLocationRelativeTo(null);; // Center the window on the screen
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
// 		userIDLabel.setBounds(50,100,75,25);
// 		userPasswordLabel.setBounds(50,150,75,25);
		
// 		messageLabel.setBounds(125,250,250,35);
// 		messageLabel.setFont(new Font(null,Font.ITALIC, 20));
		
// 		userIDField.setBounds(125,100,200,25);
// 		userPasswordField.setBounds(125,150,200,25);
		
// 		loginButton.setBounds(125,200,100,25);
// 		loginButton.setFocusable(false);
// 		loginButton.addActionListener(this);
		
// 		resetButton.setBounds(225,200,100,25);
// 		resetButton.setFocusable(false);
// 		resetButton.addActionListener(this);
//         JPanel panel = new JPanel();
		
// 		panel.add(userIDLabel);
// 		panel.add(userPasswordLabel);
// 		panel.add(messageLabel);
// 		panel.add(userIDField);
// 		panel.add(userPasswordField);
// 		panel.add(loginButton);
// 		panel.add(resetButton);

// 		panel.setLayout(null);
//         panel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        
		

//         frame.add(panel);
// 		frame.setVisible(true);
		
// 	}
    


// 	@Override
// 	public void actionPerformed(ActionEvent e) {
		
// 		if(e.getSource()==resetButton) {
// 			userIDField.setText("");
// 			userPasswordField.setText("");
// 		}
		
// 		if(e.getSource()==loginButton) {
			
// 			String userID = userIDField.getText();
// 			String password = String.valueOf(userPasswordField.getPassword());
			
// 			if(accounts.containsKey(userID)) {
// 				if(accounts.get(userID).equals(password)) {
// 					messageLabel.setForeground(Color.green);
// 					messageLabel.setText("Login successful");
// 					frame.dispose();
// 					// WelcomePage welcomePage = new WelcomePage(userID);
// 					TabbedPane mainPage = new TabbedPane();
//                     // System.out.println("WELCOME PAGE");
// 				}
// 				else {
// 					messageLabel.setForeground(Color.red);
// 					messageLabel.setText("Wrong password");
// 				}

// 			}
// 			else {
// 				messageLabel.setForeground(Color.red);
// 				messageLabel.setText("username not found");
// 			}
// 		}
// 	}
//     public HashMap<String,String> getLoginData(String fileName) {
//         HashMap<String,String> accounts = new HashMap<String,String>();
    
//       try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
//         reader.readLine(); // Skip header line
//         String line;
//         while ((line = reader.readLine()) != null) {
//             String[] data = line.split(",");
//             String username = data[0];
//             String password = data[1];

//             accounts.put(username, password);
        
//         }
//       } catch (IOException e) {
//         System.err.println("Error reading CSV file: " + e.getMessage());
//       }
    
//       return accounts;
//     }
    
//     public static void main(String[] args) {
//         new RegisterPage();
//         // for (String username : accounts.keySet()) {
//         //     System.out.println(username);
//         // }
//     }
// }




import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegisterPage {
	JFrame frame = new JFrame("Demo application");
	JLabel userLabel = new JLabel("User");
	JTextField userText = new JTextField(20);
	JLabel passwordLabel = new JLabel("Password");
	JPasswordField passwordText = new JPasswordField(20);
	JButton loginButton = new JButton("login");
	JButton registerButton = new JButton("register");

	public RegisterPage() {
		frame.setSize(300, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(null);

		
		userLabel.setBounds(10, 10, 80, 25);
		panel.add(userLabel);

		
		userText.setBounds(100, 10, 160, 25);
		panel.add(userText);

		
		passwordLabel.setBounds(10, 40, 80, 25);
		panel.add(passwordLabel);

		
		passwordText.setBounds(100, 40, 160, 25);
		panel.add(passwordText);

		
		loginButton.setBounds(10, 80, 80, 25);
		panel.add(loginButton);
		
		
		registerButton.setBounds(180, 80, 80, 25);
		panel.add(registerButton);

		frame.setVisible(true);
	}

}