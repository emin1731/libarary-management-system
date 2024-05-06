package GUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import database.AccountDB;
import exceptions.UserNotFoundException;

public class LoginPage implements ActionListener, ItemListener {
	
	JFrame frame = new JFrame("Login");
	JButton loginButton = new JButton("Login");
	JButton registerButton = new JButton("Register");
	JTextField userIDField = new JTextField(20);
	JPasswordField userPasswordField = new JPasswordField(20);
	JLabel userIDLabel = new JLabel("userID:");
	JLabel userPasswordLabel = new JLabel("password:");
	JLabel messageLabel = new JLabel();
	AccountDB account;
	JCheckBox showPasswordCheckbox = new JCheckBox("Show Password");

	public LoginPage() {
		account = new AccountDB("C://Users//ismai//OneDrive//Рабочий стол//ayla pp2 project//team-project-team-54//src//data//Accounts.csv");

		frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		userIDLabel.setBounds(10, 10, 80, 25);
		panel.add(userIDLabel);

		messageLabel.setBounds(10, 100, 200, 25);
		messageLabel.setFont(new Font(null, Font.ITALIC, 14));
		panel.add(messageLabel);

		userIDField.setBounds(100, 10, 160, 25);
		panel.add(userIDField);

		userPasswordLabel.setBounds(10, 40, 80, 25);
		panel.add(userPasswordLabel);

		userPasswordField.setBounds(100, 40, 160, 25);
		panel.add(userPasswordField);

		showPasswordCheckbox.setBounds(100, 70, 160, 25);
		showPasswordCheckbox.addItemListener(this);
		panel.add(showPasswordCheckbox);

		loginButton.setBounds(10, 130, 80, 25);
		loginButton.setFocusable(false);
		loginButton.addActionListener(this);
		panel.add(loginButton);
		
		registerButton.setBounds(180, 130, 80, 25);
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
			new RegisterPage();
			frame.dispose();
		}

		if(e.getSource()==loginButton) {
			String userID = userIDField.getText();
			String password = String.valueOf(userPasswordField.getPassword());

			try {
				if (account.loginUser(userID, password)) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							UIManager.put("swing.boldMetal", Boolean.FALSE);
							new TabbedPane(userID).setVisible(true);
						}
					});
					frame.dispose();
				} else {
					messageLabel.setForeground(Color.red);
					messageLabel.setText("Wrong password");
				}
			} catch (UserNotFoundException er) {
				messageLabel.setForeground(Color.red);
				messageLabel.setText("Username not found");
			}
			
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == showPasswordCheckbox) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				userPasswordField.setEchoChar((char) 0); // Show password
			} else {
				userPasswordField.setEchoChar('\u2022'); // Hide password
			}
		}
	}

	public static void main(String[] args) {
        new LoginPage();
    }
}
