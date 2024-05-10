package GUI;

import javax.security.auth.RefreshFailedException;
import javax.security.auth.Refreshable;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import classes.Book;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import database.AccountDB;
import database.GeneralDB;


public class AccountDbPage extends JPanel {
    Locale locale = Locale.getDefault();
    ResourceBundle bundle = ResourceBundle.getBundle("Messages", locale);
    private JTable table;
    private DefaultTableModel model;
    // private String[] columns = {"Username", "Password", "View", "Action"};
    private String[] columns = {
        bundle.getString("profilePage.author"), 
        bundle.getString("accountDb.password"), 
        bundle.getString("accountDb.view"), 
        bundle.getString("accountDb.action")
    }; // Updated columns
    Object[][] data;
    private Refreshable parentFrame;


    public AccountDbPage(Refreshable parentFrame) {
        setLayout(new BorderLayout());
        AccountDB accountDB = new AccountDB("src/data/Accounts.csv");
        HashMap<String, String> hashMap = accountDB.getAllUsers();
        
        this.parentFrame = parentFrame;

        data = convertHashMapToArray(hashMap);
        model = new DefaultTableModel(data, columns);

        table = new JTable(model);
        table.setSurrendersFocusOnKeystroke(true);


        // Custom cell renderer for Action column
        table.getColumn(bundle.getString("accountDb.view")).setCellRenderer(new ButtonCellRendererEditor(table, data, 2, this.parentFrame));
        // Custom cell editor for Action column
        table.getColumn(bundle.getString("accountDb.view")).setCellEditor(new ButtonCellRendererEditor(table, data, 2, this.parentFrame));

        // Custom cell renderer for View column
        table.getColumn(bundle.getString("accountDb.action")).setCellRenderer(new ButtonCellRendererEditor(table, data, 3, this.parentFrame));
        // Custom cell editor for View column
        table.getColumn(bundle.getString("accountDb.action")).setCellEditor(new ButtonCellRendererEditor(table, data, 3, this.parentFrame));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public static Object[][] convertHashMapToArray(HashMap<String, String> hashMap) {
        ResourceBundle bundle = ResourceBundle.getBundle("Messages", LocaleChanger.getCurrentLocale());
        Object[][] array = new Object[hashMap.size()][4]; // 4 columns: username, password, action, view
        int rowIndex = 0;
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            array[rowIndex][0] = entry.getKey(); // Username
            array[rowIndex][1] = entry.getValue(); // Password
            array[rowIndex][2] = bundle.getString("accountDb.view");
            array[rowIndex][3] = bundle.getString("accountDb.delete");
            rowIndex++;
        }
        return array;
    }

    public void reloadPage() {
        // System.out.println("RELOAD IN ACC PAGE");
        AccountDB accountDB = new AccountDB("src/data/Accounts.csv");
        HashMap<String, String> hashMap = accountDB.getAllUsers();
        data = convertHashMapToArray(hashMap);
        ResourceBundle bundle = ResourceBundle.getBundle("Messages", LocaleChanger.getCurrentLocale());

        columns[0] = bundle.getString("profilePage.author");
        columns[1] = bundle.getString("accountDb.password");
        columns[2] = bundle.getString("accountDb.view");
        columns[3] = bundle.getString("accountDb.action");

        model.setDataVector(data, columns);
        table.getColumn(bundle.getString("accountDb.view")).setCellRenderer(new ButtonCellRendererEditor(table, data, 2, this.parentFrame));
        table.getColumn(bundle.getString("accountDb.view")).setCellEditor(new ButtonCellRendererEditor(table, data, 2, this.parentFrame));

        table.getColumn(bundle.getString("accountDb.action")).setCellRenderer(new ButtonCellRendererEditor(table, data, 3, this.parentFrame));
        table.getColumn(bundle.getString("accountDb.action")).setCellEditor(new ButtonCellRendererEditor(table, data, 3, this.parentFrame));
        this.repaint();
    }

    // public void updateTableData() {
    //     AccountDB accountDB = new AccountDB("src/data/Accounts.csv");
    //     HashMap<String, String> hashMap = accountDB.getAllUsers();
    //     data = convertHashMapToArray(hashMap);
    //     model.setDataVector(data, columns);

    //     table.getColumn(bundle.getString("accountDb.view")).setCellRenderer(new ButtonCellRendererEditor(table, data, 2, this.parentFrame));
    //     table.getColumn(bundle.getString("accountDb.view")).setCellEditor(new ButtonCellRendererEditor(table, data, 2, this.parentFrame));
        
    //     table.getColumn(bundle.getString("accountDb.action")).setCellRenderer(new ButtonCellRendererEditor(table, data, 3, this.parentFrame));
    //     table.getColumn(bundle.getString("accountDb.action")).setCellEditor(new ButtonCellRendererEditor(table, data, 3, this.parentFrame));
    //     this.repaint();
    //   }

    static class ButtonCellRendererEditor extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
        private JButton button;
        private Object[][] data;
        private int columnIndex;
        private int row;

        public ButtonCellRendererEditor(JTable table, Object[][] data, int columnIndex, Refreshable parentFrame) {
            this.data = data;
            this.columnIndex = columnIndex;

            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    // System.out.println("Row index: " + row);
                    if (columnIndex == 2) {
                        // System.out.println("View " + data[row][0]);
                        ;

                        JFrame frame = new JFrame((String) data[row][0]);
                        frame.setPreferredSize(new Dimension(700, 400));
                        frame.setLocationRelativeTo(null); // Center the window on the screen
                        frame.getContentPane().add(new PersonalDbPage(parentFrame, (String) data[row][0], true));
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only frame window, not the entire application    
                        frame.pack();
                        frame.setVisible(true);


                    } else if (columnIndex == 3) {
                        System.out.println("View button clicked in row " + row);
                    }
                }
            });
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            this.row = row;
            button.setText((value == null) ? "" : value.toString());
            return button;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            button.setText((value == null) ? "" : value.toString());
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return button.getText();
        }

        @Override
        public boolean stopCellEditing() {
            return super.stopCellEditing();
        }
    }

}
