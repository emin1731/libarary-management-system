// This class represents a component to change the locale of the application, allowing users to select between English and Azerbaijani languages.
package GUI;

import javax.security.auth.Refreshable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class LocaleChanger extends JComponent {
    private static Locale currentLocale = Locale.getDefault();
    private JComboBox<String> languageDropdown;
    private Refreshable parentFrame;

    public LocaleChanger(Refreshable parentFrame) {
        this.parentFrame = parentFrame;
        // Dropdown to select language
        languageDropdown = new JComboBox<>(new String[]{"EN - English", "AZ - Azerbaijani"});
        languageDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = languageDropdown.getSelectedIndex();
                switch (selectedIndex) {
                    case 0:
                        setLocale(new Locale("en", "US")); // English, United States
                        break;
                    case 1:
                        setLocale(new Locale("az", "AZ")); // Azerbaijani, Azerbaijan
                        break;
                }
            }
        });

        setLayout(new GridLayout(1, 2));
        add(languageDropdown);

        // Set default locale
        setLocale(new Locale("en", "US"));
    }

    public void setLocale(Locale locale) {
        Locale.setDefault(locale);
        currentLocale = locale;
        updateUI();
    }

    public void updateUI() {
        try {
            parentFrame.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Locale getCurrentLocale() {
        return currentLocale;
    }
}
