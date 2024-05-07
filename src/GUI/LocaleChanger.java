package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleChanger extends JComponent {
    private JComboBox<String> languageDropdown;
    private JLabel greetingLabel;
    private ResourceBundle bundle;

    public LocaleChanger() {
        // Dropdown to select language
        languageDropdown = new JComboBox<>(new String[]{"EN - English", "AZ - Azerbaijani"});
        languageDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = languageDropdown.getSelectedIndex();
                switch (selectedIndex) {
                    case 0:
                        setLocale(new Locale.Builder().setLanguage("en").setRegion("US").build()); // English, United States
                        break;
                    case 1:
                        setLocale(new Locale.Builder().setLanguage("az").setRegion("AZ").build()); // Azerbaijani, Azerbaijan
                        break;
                }
            }
        });


        // Label to display greeting
        // greetingLabel = new JLabel();

        // Add components to this component
        setLayout(new GridLayout(1, 2));
        add(languageDropdown);
        // add(greetingLabel);

        // Set default locale
        setLocale(new Locale.Builder().setLanguage("en").setRegion("US").build());
    }

    public void setLocale(Locale locale) {
        Locale.setDefault(locale);
        bundle = ResourceBundle.getBundle("Messages", locale);
        updateUI();
    }

    // public void updateUI() {
    //     if (bundle != null) {
    //         String greeting = bundle.getString("greeting");
    //         greetingLabel.setText(greeting);
    //     }
    // }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create a JFrame and add LocaleChanger component to it
            JFrame frame = new JFrame("Locale Changer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new LocaleChanger());
            // frame.setSize(100, 50);
            frame.setVisible(true);
        });
    }
}
