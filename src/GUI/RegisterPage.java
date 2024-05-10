package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;

import javax.security.auth.Refreshable;
import javax.swing.*;

import database.AccountDB;
import database.PersonalDB;
import exceptions.UserNotFoundException;

public class RegisterPage implements ActionListener, Refreshable {
	JFrame frame = new JFrame("Register");
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
		// Create a LocaleChanger component
        LocaleChanger localeChanger = new LocaleChanger(this);

		// Create a panel for the tabbed pane and locale changer
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        // Add the LocaleChanger to a panel with FlowLayout
        JPanel localePanel = new JPanel(new FlowLayout(FlowLayout.LEADING)); // Align components to the left
        localePanel.add(localeChanger);

        // Add the localePanel and tabbed pane to the content panel
        contentPanel.add(localePanel, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		registerLabel.setBounds(470, 50, 200, 35); 
        registerLabel.setFont(new Font("Arial", Font.BOLD, 35)); 
		welcomeLabel.setBounds(100,50,250,35); 
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

		contentPanel.add(panel);

        frame.add(contentPanel);
		frame.setVisible(true);
	}

	@Override
    public void refresh() {
		ResourceBundle bundle = ResourceBundle.getBundle("Messages", LocaleChanger.getCurrentLocale());



		frame.setTitle(bundle.getString("registerPage.register"));
		loginButton.setText(bundle.getString("registerPage.login"));
		registerButton.setText(bundle.getString("registerPage.register"));
		userIDLabel.setText(bundle.getString("registerPage.username"));
		userPasswordLabel.setText(bundle.getString("registerPage.password"));
		// messageLabel.setText(bundle.getString(""));
		registerLabel.setText(bundle.getString("registerPage.register"));
		welcomeLabel.setText(bundle.getString("registerPage.welcome"));
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

	@Override
	public boolean isCurrent() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'isCurrent'");
	}

}