package GUI;


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