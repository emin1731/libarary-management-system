package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TableDemo extends JFrame {

    private DefaultTableModel model;
    private JTable table;
    private int sortColumn = -1;
    private boolean ascending = true;
    private int clickCount = 0;

    public TableDemo(Object[][] data, Object[] columns) {
        model = new DefaultTableModel(data, columns);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = table.columnAtPoint(e.getPoint());
                if (col == sortColumn) {
                    if (clickCount == 0) {
                        ascending = true;
                        clickCount = 1;
                    } else if (clickCount == 1) {
                        ascending = false;
                        clickCount = 2;
                    } else {
                        sortColumn = -1;
                        ascending = true;
                        clickCount = 0;
                    }
                } else {
                    sortColumn = col;
                    ascending = true;
                    clickCount = 1;
                }
                sortTable();
            }
        });

        // Create a JPanel to contain the scroll pane and the button
        JPanel panel = new JPanel(new BorderLayout());

        // Add the scroll pane to the panel
        panel.add(scrollPane, BorderLayout.CENTER);

        // Create a button
        JButton button = new JButton("Click me");
        button.setBounds(0, 0, 100, 20);
        button.addActionListener(e -> {
            // Handle button click event here
        });

        // Add the button to the panel at the bottom right
        panel.add(button, BorderLayout.SOUTH);

        // Add the panel to the frame
        getContentPane().add(panel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
    }

    private void sortTable() {
        // Implement sorting logic here
    }

    public static void main(String[] args) {
        Object[][] data = {
            {"1", "John Doe", "johndoe@example.com"}, 
            {"1", "John Doe", "johndoe@example.com"}, 

            {"2", "Jane Smith", "janesmith@example.com"}
        
        };
        Object[] columns = {"Id", "Name", "Email"};
        SwingUtilities.invokeLater(() -> {
            TableDemo frame = new TableDemo(data, columns);
            frame.setVisible(true);
        });
    }
}
