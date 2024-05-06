package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.awt.event.MouseEvent;

import classes.Book;
import database.GeneralDB;
import GUI.ReviewsView;


public class GeneralDbPage extends JPanel implements ActionListener {

    private JTable table;
    private DefaultTableModel model;
    private String[] columns = {"Title", "Author", "Ratings", "Reviews"};
    private Object[][] data;
    private int sortColumn = -1;
    private boolean ascending = true;
    private int clickCount = 0;
    private JTextField searchField;

    public GeneralDbPage() {
        super(new BorderLayout());

        ArrayList<Book> books = GeneralDB.readBooksFromCSV("src/data/GeneralDatabase.csv");
        data = toObjectArray(books);

        model = new DefaultTableModel(data, columns);
        table = new JTable(model);

        table.getColumnModel().getColumn(3).setCellRenderer(new ClickableCellRenderer());
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = table.getColumnModel().getColumnIndexAtX(e.getX());
                int row = e.getY() / table.getRowHeight();
                if (column == 3 && row < table.getRowCount()) {
                    System.out.println("Clicked on: " + table.getValueAt(row, column) + " -> " + row + " " + column);
                    System.out.println(GeneralDB.readBooksFromCSV("src/data/GeneralDatabase.csv").get(row).getTitle());
                    Book book = GeneralDB.readBooksFromCSV("src/data/GeneralDatabase.csv").get(row);
                    openNewWindow(book);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search Here");
        searchButton.addActionListener(this);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);

        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonForeground = Color.WHITE;
        Color buttonBackground = new Color(0, 102, 204); // Dark blue
        searchButton.setFont(buttonFont);
        searchButton.setForeground(buttonForeground);
        searchButton.setBackground(buttonBackground);
        
        Font searchFieldFont = new Font("Arial", Font.PLAIN, 14);
        Color searchFieldForeground = Color.BLACK;
        Color searchFieldBackground = Color.WHITE;
        searchField.setFont(searchFieldFont);
        searchField.setForeground(searchFieldForeground);
        searchField.setBackground(searchFieldBackground);

        table.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);
                int column = table.columnAtPoint(point);

                if (row != -1 && column != -1) {
                    table.clearSelection();
                    table.setRowSelectionInterval(row, row);
                    table.setColumnSelectionInterval(column, column);
                }
            }
        });

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        table.setDefaultRenderer(Object.class, renderer);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (table.isCellSelected(row, column)) {
                    c.setBackground(Color.BLUE);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        });

        Font tableFont = new Font("Arial", Font.PLAIN, 12);
        Color tableForeground = Color.BLACK;
        Color tableBackground = Color.WHITE;
        Color tableHeaderBackground = new Color(0, 102, 204); 
        Color tableHeaderForeground = Color.WHITE;
        table.setFont(tableFont);
        table.setForeground(tableForeground);
        table.setBackground(tableBackground);
        table.getTableHeader().setFont(buttonFont);
        table.getTableHeader().setForeground(tableHeaderForeground);
        table.getTableHeader().setBackground(tableHeaderBackground);

        table.getTableHeader().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int col = table.columnAtPoint(evt.getPoint());
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

        setSize(700, 400);
    }

    public static void openNewWindow(Book book) {
        JFrame frame = new JFrame("New Window");
        frame.setSize(500, 700);
        JLabel label = new JLabel("Title: " + book.getTitle() + ", Author: " + book.getAuthor());
        frame.add(label);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public Object[][] toObjectArray(ArrayList<Book> books) {
        Object[][] result = new Object[books.size()][4];
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            result[i][0] = book.getTitle();
            result[i][1] = book.getAuthor();
            result[i][2] = (book.getAverageRating() != -1) ? book.getAverageRating() : "No ratings";
            result[i][3] = book.getReviewsUsersString();
        }
        return result;
    }

    private void sortTable() {
        if (sortColumn == -1) {
            model.setDataVector(data, columns);
            table.setModel(model);
            return;
        }

        Arrays.sort(data, new Comparator<Object[]>() {
            @Override
            public int compare(Object[] row1, Object[] row2) {
                Object o1 = row1[sortColumn];
                Object o2 = row2[sortColumn];
                if (o1 instanceof Comparable && o2 instanceof Comparable) {
                    Comparable c1 = (Comparable) o1;
                    Comparable c2 = (Comparable) o2;
                    int result = c1.compareTo(c2);
                    return ascending ? result : -result;
                }
                return 0;
            }
        });

        model.setDataVector(data, columns);
        table.setModel(model);
    }

    private void performSearch(String searchText) {
        List<Object[]> filteredData = new ArrayList<>();
        for (Object[] row : data) {
            String title = (String) row[0];
            String author = (String) row[1];
            if (title.toLowerCase().contains(searchText.toLowerCase()) || author.toLowerCase().contains(searchText.toLowerCase())) {
                filteredData.add(row);
            }
        }

        Object[][] filteredArray = new Object[filteredData.size()][];
        for (int i = 0; i < filteredData.size(); i++) {
            filteredArray[i] = filteredData.get(i);
        }

        model.setDataVector(filteredArray, columns);
        table.setModel(model);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            performSearch(searchField.getText());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("General Database");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new GeneralDbPage());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}

class ClickableCellRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (column == 1) {
            setForeground(Color.BLUE);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        return this;
    }
}
