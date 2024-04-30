package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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

public class LoginPage implements ActionListener{
	
	JFrame frame = new JFrame("Login");
	JButton loginButton = new JButton("Login");
	JButton registerButton = new JButton("Register");
	JTextField userIDField = new JTextField(20);
	JPasswordField userPasswordField = new JPasswordField(20);
	JLabel userIDLabel = new JLabel("userID:");
	JLabel userPasswordLabel = new JLabel("password:");
	JLabel messageLabel = new JLabel();
	AccountDB account;
	
	public LoginPage() {
		account = new AccountDB("src/database/AccountDB.java");

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
			new RegisterPage();
			frame.dispose();
		}

		if(e.getSource()==loginButton) {
			String userID = userIDField.getText();
			String password = String.valueOf(userPasswordField.getPassword());

			try {
				if (account.loginUser(userID, password)) {
					TabbedPane mainPage = new TabbedPane();
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							UIManager.put("swing.boldMetal", Boolean.FALSE);
							TabbedPane.createAndShowGUI();
						}
					});
					frame.dispose();
				} else {
					messageLabel.setForeground(Color.red);
					messageLabel.setText("Wrong password");
				}
			} catch (UserNotFoundException er) {
				messageLabel.setForeground(Color.red);
				messageLabel.setText("username not found");
			}
			
		}
	}
 
    public static void main(String[] args) {
        new LoginPage();

    }
}
