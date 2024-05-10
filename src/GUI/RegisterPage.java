// RegisterPage class represents a graphical user interface for user registration, allowing new users to create accounts by providing a username and password, with localization support.
package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;

import javax.security.auth.Refreshable;
import javax.swing.*;

import database.AccountDB;
import database.PersonalDB;
import exceptions.EmptyUsernameOrPasswordException;
import exceptions.UserAlreadyExistsException;

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

	public RegisterPage() {
		account = new AccountDB("src/data/Accounts.csv");

		frame.setSize(700, 525);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LocaleChanger localeChanger = new LocaleChanger(this);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        JPanel localePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        localePanel.add(localeChanger);

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


		messageLabel.setBounds(400, 247, 250, 25);
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
		registerLabel.setText(bundle.getString("registerPage.register"));
		welcomeLabel.setText(bundle.getString("registerPage.welcome"));
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		ResourceBundle bundle = ResourceBundle.getBundle("Messages", LocaleChanger.getCurrentLocale());

		if(e.getSource()==registerButton) {
			System.out.println("REG");
			String userID = userIDField.getText();
			String password = String.valueOf(userPasswordField.getPassword());

			try {
				account.registerUser(userID, password);

				PersonalDB.createNewPersonalDB("src/data/users/" + userID + ".csv");

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						UIManager.put("swing.boldMetal", Boolean.FALSE);
						new UserPage(userID).setVisible(true);
					}
				});

				
				messageLabel.setForeground(Color.green);
				messageLabel.setText("Registered");

				new UserPage(userID);
				frame.dispose();

			} catch (UserAlreadyExistsException e1) {
				messageLabel.setForeground(Color.red);
				messageLabel.setText("Registered");
				messageLabel.setText(bundle.getString("registerPage.userAlreadyExists"));
			} catch (EmptyUsernameOrPasswordException e2) {
				messageLabel.setForeground(Color.red);
				messageLabel.setText(bundle.getString("loginPage.emptyUsernameOrPassword"));
			}

		}

		if(e.getSource()==loginButton) {
			new LoginPage();
			frame.dispose();
		}
	}
	public static void main(String[] args) {
		new RegisterPage();
	}

	@Override
	public boolean isCurrent() {
		throw new UnsupportedOperationException("Unimplemented method 'isCurrent'");
	}

}  
