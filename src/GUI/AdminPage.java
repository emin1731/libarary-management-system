package GUI;
import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import utils.UpdateEvent;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.security.auth.RefreshFailedException;
import javax.security.auth.Refreshable;


public class AdminPage extends JFrame implements Refreshable {
    private GeneralDbPage generalDB;
    private AccountDbPage accountDbPage;
    private boolean isCurrentTabGeneralDB;

    public AdminPage(String username) {
        super("Welcome to Admin Panel");

        JTabbedPane tabbedPane = new JTabbedPane();
        ImageIcon icon = createImageIcon("tab-icon.png");

        // General Database Page
        this.generalDB = new GeneralDbPage(this, username);
        tabbedPane.addTab("General Database", icon, generalDB, "Browse and edit available books in the library");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        // Personal Database Page
        this.accountDbPage = new AccountDbPage(this);
        tabbedPane.addTab("Account Database", icon, accountDbPage, "Browse and edit user accounts");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        // Listen for tab selection change
        tabbedPane.addChangeListener(e -> {
            JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
            isCurrentTabGeneralDB = sourceTabbedPane.getSelectedIndex() == 0;
        });

        add(tabbedPane);

        // The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        URL imgURL = AdminPage.class.getResource("/images/" + path);

        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    public void onUpdateEvent(UpdateEvent event) {
        System.out.println("Parent frame updated.");
    }


    @Override
    public boolean isCurrent() {
        return isCurrentTabGeneralDB;
    }

    @Override
    public void refresh() throws RefreshFailedException {
        System.out.println("REFRESH");
        // personalDbPage.reloadPage();
    }

    public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            UIManager.put("swing.boldMetal", Boolean.FALSE);
            new AdminPage("emin").setVisible(true);
        }
    });
}
}