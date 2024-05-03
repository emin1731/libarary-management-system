package GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

import database.AccountDB;
import database.PersonalDB;
import exceptions.UserNotFoundException;

public class RegisterPage implements ActionListener {
	JFrame frame = new JFrame("Register");
	JButton registerButton = new JButton("Register");
	JButton loginButton = new JButton("Login");
	JTextField userIDField = new JTextField(20);
	JPasswordField userPasswordField = new JPasswordField(20);
	JLabel userIDLabel = new JLabel("userID:");
	JLabel userPasswordLabel = new JLabel("password:");
	JLabel messageLabel = new JLabel();
	AccountDB account;
	// HashMap<String,String> accounts = this.getLoginData("src/data/Accounts.csv");

	public RegisterPage() {
		account = new AccountDB("src/data/Accounts.csv");

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

		registerButton.setBounds(10, 100, 80, 25);
		registerButton.setFocusable(false);
		registerButton.addActionListener(this);
		panel.add(registerButton);
		
		loginButton.setBounds(180, 100, 80, 25);
		loginButton.setFocusable(false);
		loginButton.addActionListener(this);
		panel.add(loginButton);

		panel.setLayout(null);
        panel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

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
						new TabbedPane(userID).setVisible(true);
					}
				});

				
				messageLabel.setForeground(Color.green);
				messageLabel.setText("Registered");

				new TabbedPane(userID);
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