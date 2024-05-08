package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import database.AccountDB;
import database.PersonalDB;
import exceptions.UserNotFoundException;

public class RegisterPage implements ActionListener {
	JFrame frame = new JFrame("Register");
	/*JButton registerButton = new JButton("Register");
	JButton loginButton = new JButton("Login");*/
	RoundedButton loginButton = new RoundedButton("Login"); 
    RoundedButton registerButton = new RoundedButton("Register"); 
	JTextField userIDField = new JTextField(20);
	JPasswordField userPasswordField = new JPasswordField(20);
	JLabel userIDLabel = new JLabel("UserID:");
	JLabel userPasswordLabel = new JLabel("Password:");
	JLabel messageLabel = new JLabel();
	JLabel registerLabel = new JLabel("Register");
	JLabel welcomeLabel = new JLabel("Welcome!");
	AccountDB account;
	// HashMap<String,String> accounts = this.getLoginData("src/data/Accounts.csv");

	public RegisterPage() {
		account = new AccountDB("src/data/Accounts.csv");

		frame.setSize(700, 525);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		registerLabel.setBounds(470, 50, 140, 35); 
        registerLabel.setFont(new Font("Arial", Font.BOLD, 35)); 
		welcomeLabel.setBounds(100,50,200,35); 
		welcomeLabel.setFont(new Font("Georgia", Font.BOLD, 35));
        panel.add(registerLabel);
		panel.add(welcomeLabel);

		userIDLabel.setBounds(400, 140, 80, 25);
		panel.add(userIDLabel);


		messageLabel.setBounds(400, 247, 200, 25);
		messageLabel.setFont(new Font(null,Font.ITALIC, 14));
		panel.add(messageLabel);


		userIDField.setBounds(490, 140, 160, 25);
		panel.add(userIDField);

		userPasswordLabel.setBounds(400, 190, 80, 25);
		panel.add(userPasswordLabel);

		userPasswordField.setBounds(490, 190, 160, 25);
		panel.add(userPasswordField);

		registerButton.setBounds(420, 280, 100, 30);
		registerButton.setFocusable(false);
		registerButton.addActionListener(this);
		registerButton.setBackground(new Color(0x8fbc8f)); 
        registerButton.setForeground(Color.WHITE);
		panel.add(registerButton);
		
		loginButton.setBounds(570, 280, 80, 30);
		loginButton.setFocusable(false);
		loginButton.addActionListener(this);
		loginButton.setBackground(new Color(0x8fbc8f)); 
        loginButton.setForeground(Color.WHITE);
		panel.add(loginButton);

		panel.setBounds(0, 0, 500, 450);

		panel.setLayout(null);
        panel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		ImageIcon imageIcon = new ImageIcon("src/images/Image4.jpg");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(350, 525, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(scaledImageIcon);
        imageLabel.setBounds(0, 0, 350, 525);
        panel.add(imageLabel);

        frame.add(panel);
		frame.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		

		if(e.getSource()==registerButton) {
			System.out.println("REG");
			String userID = userIDField.getText();
			String password = String.valueOf(userPasswordField.getPassword());

			try {
				if (account.loginUser(userID, password)) {
					System.out.println("HELLO");
				}
				
			} 
			catch (UserNotFoundException er) {
				account.registerUser(userID, password);
				PersonalDB.createNewPersonalDB("src/data/users/" + userID + ".csv");

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						// Turn off metal's use of bold fonts
						UIManager.put("swing.boldMetal", Boolean.FALSE);
						new UserPage(userID).setVisible(true);
					}
				});

				
				messageLabel.setForeground(Color.green);
				messageLabel.setText("Registered");

				new UserPage(userID);
				frame.dispose();
			}
			
		}

		if(e.getSource()==loginButton) {
			// System.out.println("LOG");
			new LoginPage();
			frame.dispose();
		}
	}
	public static void main(String[] args) {
		new RegisterPage();
	}

}