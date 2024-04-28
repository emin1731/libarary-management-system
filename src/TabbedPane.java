/*
 * TabbedPane.java requires one additional file:
 *   images/middle.gif.
 */

 import javax.swing.JTabbedPane;
 import javax.swing.ImageIcon;
 import javax.swing.JLabel;
 import javax.swing.JPanel;
 import javax.swing.JFrame;
 import javax.swing.JComponent;
 import javax.swing.SwingUtilities;
 import javax.swing.UIManager;
 import java.awt.BorderLayout;
 import java.awt.Dimension;
 import java.awt.GridLayout;
 import java.awt.event.KeyEvent;
 
 public class TabbedPane extends JPanel {
     public TabbedPane() {
         super(new GridLayout(1, 1));
         
         JTabbedPane tabbedPane = new JTabbedPane();
         ImageIcon icon = createImageIcon("images/tab-icon.png");
         
        //  JComponent panel1 = makeTextPanel("Panel #1");
         Sorting generalDB = new Sorting();
         tabbedPane.addTab("Tab 1", icon, generalDB.getContentPane(),
                 "Does nothing");
         tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
         
         JComponent panel2 = makeTextPanel("Panel #2");
         tabbedPane.addTab("Tab 2", icon, panel2,
                 "Does twice as much nothing");
         tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
         
  
         add(tabbedPane);
         
         //The following line enables to use scrolling tabs.
         tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

     }
     
     protected JComponent makeTextPanel(String text) {
         JPanel panel = new JPanel(false);
         JLabel filler = new JLabel(text);
         filler.setHorizontalAlignment(JLabel.CENTER);
         panel.setLayout(new GridLayout(1, 1));
         panel.add(filler);
         return panel;
     }
     
     /** Returns an ImageIcon, or null if the path was invalid. */
     protected static ImageIcon createImageIcon(String path) {
         java.net.URL imgURL = TabbedPane.class.getResource(path);
         if (imgURL != null) {
             return new ImageIcon(imgURL);
         } else {
             System.err.println("Couldn't find file: " + path);
             return null;
         }
     }
     
     /**
      * Create the GUI and show it.  For thread safety,
      * this method should be invoked from
      * the event dispatch thread.
      */
     public static void createAndShowGUI() {
         //Create and set up the window.
         JFrame frame = new JFrame("TabbedPane");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
         //Add content to the window.
         frame.add(new TabbedPane(), BorderLayout.CENTER);
         
         //Display the window.
         frame.pack();
         frame.setSize(700, 400);
         frame.setVisible(true);
     }
     
     public static void main(String[] args) {
         //Schedule a job for the event dispatch thread:
         //creating and showing this application's GUI.
         SwingUtilities.invokeLater(new Runnable() {
             public void run() {
                 //Turn off metal's use of bold fonts
         UIManager.put("swing.boldMetal", Boolean.FALSE);
         createAndShowGUI();
             }
         });
     }
 }