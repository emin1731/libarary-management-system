// UserPage class extends JFrame to create a user interface for displaying general and personal database tabs, allowing users to browse and manage books.
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
        setTitleWithLocale(username);

        LocaleChanger localeChanger = new LocaleChanger(this);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        JPanel localePanel = new JPanel(new FlowLayout(FlowLayout.LEADING)); 
        localePanel.add(localeChanger);

        contentPanel.add(localePanel, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();
        ImageIcon icon = createImageIcon("tab-icon.png");

        ResourceBundle bundle = ResourceBundle.getBundle("Messages", LocaleChanger.getCurrentLocale());
        String generalDatabase = bundle.getString("userPage.generalDb.title");
        String personalDatabase = bundle.getString("userPage.personalDb.title");


        this.generalDB = new GeneralDbPage(this, username, false);
        tabbedPane.addTab(generalDatabase, icon, generalDB, "Browse the available book library");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        this.personalDbPage = new PersonalDbPage(this, username, false);
        tabbedPane.addTab(personalDatabase, icon, personalDbPage, "Browse and add books to your personal database");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        tabbedPane.addChangeListener(e -> {
            JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
            isCurrentTabGeneralDB = sourceTabbedPane.getSelectedIndex() == 0;
        });

        contentPanel.add(tabbedPane, BorderLayout.CENTER);

        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        getContentPane().add(contentPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
        notifyLocaleChange();
    }

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
        System.out.println("REFRESH");
        personalDbPage.reloadPage();
        generalDB.reloadPage();
        generalDB.setLocale(LocaleChanger.getCurrentLocale());
        setTitleWithLocale(this.username); 
        repaint();
    }

    private void setTitleWithLocale(String username) {
        ResourceBundle bundle = ResourceBundle.getBundle("Messages", LocaleChanger.getCurrentLocale());
        String greeting = bundle.getString("greeting");
        setTitle(greeting + " " + username);
    
        String generalDatabase = bundle.getString("userPage.generalDb.title");
        String personalDatabase = bundle.getString("userPage.personalDb.title");
    
        if (tabbedPane != null) {
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
