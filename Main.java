import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Main {
    private static JLabel userLabel;
    private static JTextField userText;
    private static JLabel passwordLabel;
    private static JPasswordField passwordText;
    private static JButton loginbutton;
    private static JButton registerButton;
    private static JLabel success;

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setTitle("Login/Registration");
        frame.setResizable(false);
        frame.setSize(420,500);
        frame.add(panel);
        panel.setLayout(null);

        userLabel = new JLabel("Username: ");
        userLabel.setBounds(100, 150, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(50);
        userText.setBounds(170,150,165,25);
        panel.add(userText);

        passwordLabel = new JLabel("Password: ");
        passwordLabel.setBounds(100, 190, 80, 25);
        panel.add(passwordLabel);
        
        passwordText = new JPasswordField();
        passwordText.setBounds(170, 190, 165, 25);
        panel.add(passwordText);

        loginbutton = new JButton("Login");
        loginbutton.setBounds(120,230,80,25);
        panel.add(loginbutton);

        registerButton = new JButton("Register");
        registerButton.setBounds(215,230,100,25);
        panel.add(registerButton);

        success = new JLabel("");
        success.setBounds(10,110,300,25);
        panel.add(success);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
} 