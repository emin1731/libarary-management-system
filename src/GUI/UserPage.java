package GUI;

import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javax.security.auth.Refreshable;

import javax.swing.*;
import java.awt.*;


public class UserPage extends JFrame implements Refreshable {
    private GeneralDbPage generalDB;
    private PersonalDbPage personalDbPage;
    private boolean isCurrentTabGeneralDB;
    private String username;
    private JTabbedPane tabbedPane;

    public UserPage(String username) {
        this.username = username;
        // Set title with default locale
        setTitleWithLocale(username);

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

        tabbedPane = new JTabbedPane();
        ImageIcon icon = createImageIcon("tab-icon.png");

        ResourceBundle bundle = ResourceBundle.getBundle("Messages", LocaleChanger.getCurrentLocale());
        String generalDatabase = bundle.getString("userPage.generalDb.title");
        String personalDatabase = bundle.getString("userPage.personalDb.title");


        // General Database Page
        this.generalDB = new GeneralDbPage(this, username);
        tabbedPane.addTab(generalDatabase, icon, generalDB, "Browse the available book library");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        // Personal Database Page
        this.personalDbPage = new PersonalDbPage(username);
        tabbedPane.addTab(personalDatabase, icon, personalDbPage, "Browse and add books to your personal database");
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
        notifyLocaleChange();
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

    private void notifyLocaleChange() {
        // Notify other frames to update their UI
        SwingUtilities.invokeLater(() -> {
            for (java.awt.Window window : java.awt.Window.getWindows()) {
                if (window instanceof JFrame) {
                    refresh();
                }
            }
        });
    }

    @Override
    public void refresh() {
        // System.out.println(this);
        System.out.println("REFRESH");
        personalDbPage.reloadPage();
        generalDB.reloadPage();
        generalDB.setLocale(LocaleChanger.getCurrentLocale());
        setTitleWithLocale(this.username); // Update title with current locale
        repaint();
    }

    private void setTitleWithLocale(String username) {
        ResourceBundle bundle = ResourceBundle.getBundle("Messages", LocaleChanger.getCurrentLocale());
        String greeting = bundle.getString("greeting");
        setTitle(greeting + " " + username);
    
        // Retrieve translated tab titles
        String generalDatabase = bundle.getString("userPage.generalDb.title");
        String personalDatabase = bundle.getString("userPage.personalDb.title");
    
        // Ensure tabbedPane is initialized
        if (tabbedPane != null) {
            // Update tab titles
            tabbedPane.setTitleAt(0, generalDatabase);
            tabbedPane.setTitleAt(1, personalDatabase);
        } else {
            System.err.println("TabbedPane is not initialized.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UIManager.put("swing.boldMetal", Boolean.FALSE);
            new UserPage("emin").setVisible(true);
        });
    }
}
