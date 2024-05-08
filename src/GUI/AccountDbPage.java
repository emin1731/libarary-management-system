package GUI;

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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import database.AccountDB;


public class AccountDbPage extends JPanel {
    private DefaultTableModel model;
    private String[] columns = {"Username", "Password", "Action", "View"};
    Object[][] data;
    private Refreshable parentFrame;


    public AccountDbPage(Refreshable parentFrame) {
        setLayout(new BorderLayout());
        AccountDB accountDB = new AccountDB("src/data/Accounts.csv");
        HashMap<String, String> hashMap = accountDB.getAllUsers();
        
        this.parentFrame = parentFrame;

        data = convertHashMapToArray(hashMap);
        model = new DefaultTableModel(data, columns);

        JTable table = new JTable(model);
        table.setSurrendersFocusOnKeystroke(true);


        // Custom cell renderer for Action column
        table.getColumnModel().getColumn(2).setCellRenderer(new ButtonCellRendererEditor(table, data, 2, this.parentFrame));
        // Custom cell editor for Action column
        table.getColumnModel().getColumn(2).setCellEditor(new ButtonCellRendererEditor(table, data, 2, this.parentFrame));

        // Custom cell renderer for View column
        table.getColumnModel().getColumn(3).setCellRenderer(new ButtonCellRendererEditor(table, data, 3, this.parentFrame));
        // Custom cell editor for View column
        table.getColumnModel().getColumn(3).setCellEditor(new ButtonCellRendererEditor(table, data, 3, this.parentFrame));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    // public static void main(String[] args) {
    //     JFrame frame = new JFrame("Table Example");
    //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     frame.getContentPane().add(new AccountDbPage());
    //     frame.pack();
    //     frame.setVisible(true);
    // }

    public static Object[][] convertHashMapToArray(HashMap<String, String> hashMap) {
        Object[][] array = new Object[hashMap.size()][4]; // 4 columns: username, password, action, view
        int rowIndex = 0;
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            array[rowIndex][0] = entry.getKey(); // Username
            array[rowIndex][1] = entry.getValue(); // Password
            array[rowIndex][2] = "View"; // Action
            array[rowIndex][3] = "Delete"; // View
            rowIndex++;
        }
        return array;
    }

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
                    System.out.println("Row index: " + row);
                    if (columnIndex == 2) {
                        System.out.println("View " + data[row][0]);
                        ;

                        JFrame frame = new JFrame((String) data[row][0]);
                        frame.setPreferredSize(new Dimension(700, 400));
                        frame.setLocationRelativeTo(null); // Center the window on the screen
                        frame.getContentPane().add(new PersonalDbPage((String) data[row][0]));
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
