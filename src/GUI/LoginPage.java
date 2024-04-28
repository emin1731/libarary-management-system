package GUI;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

public class LoginPage implements ActionListener{
	
	JFrame frame = new JFrame("Login");
	JButton loginButton = new JButton("Login");
	JButton registerButton = new JButton("Reset");
	JTextField userIDField = new JTextField(20);
	JPasswordField userPasswordField = new JPasswordField(20);
	JLabel userIDLabel = new JLabel("userID:");
	JLabel userPasswordLabel = new JLabel("password:");
	JLabel messageLabel = new JLabel();
	HashMap<String,String> accounts = this.getLoginData("src/data/Accounts.csv");
	
	public LoginPage() {

		frame.setSize(300, 175);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		userIDLabel.setBounds(10, 10, 80, 25);
		panel.add(userIDLabel);


		messageLabel.setBounds(10, 70, 200, 25);
		messageLabel.setFont(new Font(null,Font.ITALIC, 14));
		panel.add(messageLabel);


		userIDField.setBounds(100, 10, 160, 25);
		panel.add(userIDField);

		userPasswordLabel.setBounds(10, 40, 80, 25);
		panel.add(userPasswordLabel);

		userPasswordField.setBounds(100, 40, 160, 25);
		panel.add(userPasswordField);

		loginButton.setBounds(10, 100, 80, 25);
		loginButton.setFocusable(false);
		loginButton.addActionListener(this);
		panel.add(loginButton);
		
		registerButton.setBounds(180, 100, 80, 25);
		registerButton.setFocusable(false);
		registerButton.addActionListener(this);
		panel.add(registerButton);

		panel.setLayout(null);
        panel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        frame.add(panel);
		frame.setVisible(true);
		
	}
    


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==registerButton) {
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
					// WelcomePage welcomePage = new WelcomePage(userID);
					TabbedPane mainPage = new TabbedPane();

					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							//Turn off metal's use of bold fonts
					UIManager.put("swing.boldMetal", Boolean.FALSE);
					TabbedPane.createAndShowGUI();
						}
					});


					frame.dispose();
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
