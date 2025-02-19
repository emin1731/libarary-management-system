// LoginPage class represents a graphical user interface for user authentication, providing options for login, registration, and password visibility toggling.
package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ResourceBundle;
import javax.security.auth.Refreshable;
import javax.swing.ImageIcon;
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
import exceptions.EmptyUsernameOrPasswordException;
import exceptions.UserNotFoundException;

public class LoginPage implements ActionListener, ItemListener, Refreshable {
    
    JFrame frame = new JFrame("Login");
    RoundedButton loginButton = new RoundedButton("Login"); 
    RoundedButton registerButton = new RoundedButton("Register"); 
    JTextField userIDField = new JTextField(20);
    JPasswordField userPasswordField = new JPasswordField(20);
    JLabel userIDLabel = new JLabel("UserID:");
    JLabel userPasswordLabel = new JLabel("Password:");
    JLabel messageLabel = new JLabel();
    JLabel loginLabel = new JLabel("Log In");
    JLabel welcomeLabel = new JLabel("Welcome!");
    AccountDB account;
    JCheckBox showPasswordCheckbox = new JCheckBox("Show Password");

    public LoginPage() {
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
        loginLabel.setBounds(100, 50, 200, 35);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 35));
		    welcomeLabel.setBounds(425,50,250,35); 
    		welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 35));
        panel.add(loginLabel);
        panel.add(welcomeLabel);

        userIDLabel.setBounds(10, 140, 80, 25);
        panel.add(userIDLabel);

        messageLabel.setBounds(10, 247, 200, 25);
        messageLabel.setFont(new Font(null, Font.ITALIC, 14));
        panel.add(messageLabel);

        userIDField.setBounds(100, 140, 160, 25);
        panel.add(userIDField);

        userPasswordLabel.setBounds(10, 190, 80, 25);
        panel.add(userPasswordLabel);

        userPasswordField.setBounds(100, 190, 160, 25);
        panel.add(userPasswordField);

        showPasswordCheckbox.setBounds(120, 230, 160, 25);
        showPasswordCheckbox.addItemListener(this);
        panel.add(showPasswordCheckbox);

        loginButton.setBounds(30, 280, 80, 30);
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);
        loginButton.setBackground(new Color(0x8fbc8f)); 
        loginButton.setForeground(Color.WHITE);
        panel.add(loginButton);
        
        registerButton.setBounds(180, 280, 100, 30);
        registerButton.setFocusable(false);
        registerButton.addActionListener(this);
        registerButton.setBackground(new Color(0x8fbc8f)); 
        registerButton.setForeground(Color.WHITE);
        panel.add(registerButton);

        panel.setBounds(0, 0, 500, 450);

        panel.setLayout(null);
        panel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        ImageIcon imageIcon = new ImageIcon("src/images/Image4.jpg");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(350, 525, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(scaledImageIcon);
        imageLabel.setBounds(350, 0, 350, 525);
        panel.add(imageLabel);

        contentPanel.add(panel);

        frame.add(contentPanel);
        frame.setVisible(true);
    }

    @Override
    public void refresh() {
        ResourceBundle bundle = ResourceBundle.getBundle("Messages", LocaleChanger.getCurrentLocale());

        // Update all labels with new localized strings
        loginLabel.setText(bundle.getString("loginPage.login"));
        welcomeLabel.setText(bundle.getString("loginPage.welcome"));
        userIDLabel.setText(bundle.getString("loginPage.username"));
        userPasswordLabel.setText(bundle.getString("loginPage.password"));
        loginButton.setText(bundle.getString("loginPage.login"));
        registerButton.setText(bundle.getString("loginPage.register"));
        showPasswordCheckbox.setText(bundle.getString("loginPage.showPassword"));
        // Update any other message labels as needed
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ResourceBundle bundle = ResourceBundle.getBundle("Messages", LocaleChanger.getCurrentLocale());
        if(e.getSource()==registerButton) {
            new RegisterPage();
            frame.dispose();
        }

        if(e.getSource()==loginButton) {
            String userID = userIDField.getText();
            String password = String.valueOf(userPasswordField.getPassword());

            try {
                if (account.loginUser(userID, password)) {
                    // System.out.println("LOGIN" + userID + password);
                    if (userID.equals("admin") && password.equals("admin")) {
                        // System.out.println("ADMIN");
                        new AdminPage("emin").setVisible(true);;
                        
                    } else if (userID != "admin") {
                        // TabbedPane mainPage = new TabbedPane(userID);
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                // Turn off metal's use of bold fonts
                                UIManager.put("swing.boldMetal", Boolean.FALSE);
                                UserPage userPage =  new UserPage(userID);
                                userPage.setVisible(true);
                            }
                        });
                    }

                    frame.dispose();
                } else {
                    messageLabel.setForeground(Color.red);
                    messageLabel.setText(bundle.getString("loginPage.wrongPassword"));
                }
            } catch (UserNotFoundException e1) {
                messageLabel.setForeground(Color.red);
                messageLabel.setText(bundle.getString("loginPage.usernameNotFound"));
            } catch (EmptyUsernameOrPasswordException e2) {
                messageLabel.setForeground(Color.red);
                messageLabel.setText(bundle.getString("loginPage.emptyUsernameOrPassword"));
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

    @Override
    public boolean isCurrent() {
        throw new UnsupportedOperationException("Unimplemented method 'isCurrent'");
    }
}
