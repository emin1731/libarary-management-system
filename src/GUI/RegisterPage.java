package GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javax.swing.*;


public class RegisterPage{
	JFrame frame = new JFrame("Register");
	JButton loginButton = new JButton("Login");
	JButton registerButton = new JButton("Reset");
	JTextField userIDField = new JTextField(20);
	JPasswordField userPasswordField = new JPasswordField(20);
	JLabel userIDLabel = new JLabel("userID:");
	JLabel userPasswordLabel = new JLabel("password:");
	JLabel messageLabel = new JLabel();
	// HashMap<String,String> accounts = this.getLoginData("src/data/Accounts.csv");

	public RegisterPage() {
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
		// loginButton.addActionListener(this);
		panel.add(loginButton);
		
		registerButton.setBounds(180, 100, 80, 25);
		registerButton.setFocusable(false);
		// registerButton.addActionListener(this);
		panel.add(registerButton);

		panel.setLayout(null);
        panel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        frame.add(panel);
		frame.setVisible(true);
	}

}