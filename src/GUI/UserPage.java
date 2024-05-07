package GUI;
import utils.UpdateEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.security.auth.RefreshFailedException;
import javax.security.auth.Refreshable;


// public class UserPage extends JFrame implements Refreshable {
//     private GeneralDbPage generalDB;
//     private PersonalDbPage personalDbPage;
//     private boolean isCurrentTabGeneralDB;
//     // private String username;

//     public UserPage(String username) {
//         super("Hello " + username + "!");

//         JTabbedPane tabbedPane = new JTabbedPane();
//         ImageIcon icon = createImageIcon("tab-icon.png");

//         // General Database Page
//         this.generalDB = new GeneralDbPage(this, username);
//         tabbedPane.addTab("General Database", icon, generalDB, "Browse the available book library");
//         tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

//         // Personal Database Page
//         this.personalDbPage = new PersonalDbPage(username);
//         tabbedPane.addTab("Personal Database", icon, personalDbPage, "Browse and add books to your personal database");
//         tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

//         // Listen for tab selection change
//         tabbedPane.addChangeListener(e -> {
//             JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
//             isCurrentTabGeneralDB = sourceTabbedPane.getSelectedIndex() == 0;
//         });

//         add(tabbedPane);

//         // The following line enables to use scrolling tabs.
//         tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setSize(700, 400);
//         setLocationRelativeTo(null);
//     }

//     /** Returns an ImageIcon, or null if the path was invalid. */
//     protected static ImageIcon createImageIcon(String path) {
//         URL imgURL = UserPage.class.getResource("/images/" + path);

//         if (imgURL != null) {
//             return new ImageIcon(imgURL);
//         } else {
//             System.err.println("Couldn't find file: " + path);
//             return null;
//         }
//     }
//     public void onUpdateEvent(UpdateEvent event) {
//         // Handle the update event
//         System.out.println("Parent frame updated.");
//         personalDbPage.reloadPage();
//     }


//     @Override
//     public boolean isCurrent() {
//         return isCurrentTabGeneralDB;
//     }

//     @Override
//     public void refresh() throws RefreshFailedException {
//         System.out.println("REFRESH");
//         personalDbPage.reloadPage();
//     }

//     public static void main(String[] args) {
//     // Schedule a job for the event dispatch thread:
//     // creating and showing this application's GUI.
//     SwingUtilities.invokeLater(new Runnable() {
//         public void run() {
//             // Turn off metal's use of bold fonts
//             UIManager.put("swing.boldMetal", Boolean.FALSE);
//             new UserPage("emin").setVisible(true);
//         }
//     });
// }
// }


import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class UserPage extends JFrame implements Refreshable {
    private GeneralDbPage generalDB;
    private PersonalDbPage personalDbPage;
    private boolean isCurrentTabGeneralDB;

    public UserPage(String username) {
        Locale locale = new Locale.Builder().setLanguage("en").setRegion("US").build();
        ResourceBundle bundle = ResourceBundle.getBundle("Messages", locale);
        // super("Hello " + username + "!" + bundle.getString("greeting"));
        setTitle(bundle.getString("greeting"));

        // Create a LocaleChanger component
        LocaleChanger localeChanger = new LocaleChanger();

        // Create a panel for the tabbed pane and locale changer
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        // Add the LocaleChanger to a panel with FlowLayout
        JPanel localePanel = new JPanel(new FlowLayout(FlowLayout.LEADING)); // Align components to the left
        localePanel.add(localeChanger);
        
        // Add the localePanel and tabbed pane to the content panel
        contentPanel.add(localePanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        ImageIcon icon = createImageIcon("tab-icon.png");

        // General Database Page
        this.generalDB = new GeneralDbPage(this, username);
        tabbedPane.addTab("General Database", icon, generalDB, "Browse the available book library");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        // Personal Database Page
        this.personalDbPage = new PersonalDbPage(username);
        tabbedPane.addTab("Personal Database", icon, personalDbPage, "Browse and add books to your personal database");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        // Listen for tab selection change
        tabbedPane.addChangeListener(e -> {
            JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
            isCurrentTabGeneralDB = sourceTabbedPane.getSelectedIndex() == 0;
        });

        contentPanel.add(tabbedPane, BorderLayout.CENTER);

        // The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        getContentPane().add(contentPanel);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        URL imgURL = UserPage.class.getResource("/images/" + path);

        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    @Override
    public boolean isCurrent() {
        return isCurrentTabGeneralDB;
    }

    @Override
    public void refresh() throws RefreshFailedException {
        System.out.println("REFRESH");
        personalDbPage.reloadPage();
    }

    public static void main(String[] args) {
        // Schedule a job for the event dispatch thread:
        // creating and showing this application's GUI.
        SwingUtilities.invokeLater(() -> {
            // Turn off metal's use of bold fonts
            UIManager.put("swing.boldMetal", Boolean.FALSE);
            new UserPage("emin").setVisible(true);
        });
    }
}
