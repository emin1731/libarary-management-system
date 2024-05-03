package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.awt.event.MouseEvent;

import classes.Book;
import database.GeneralDB;
import utils.TableCellListener;


import java.util.function.BiConsumer;

public class GeneralDbPage extends JPanel implements ActionListener {

    private JTable table;
    private DefaultTableModel model;
    private String[] columns = {"Title", "Author", "Ratings", "Reviews", "Actions"}; // Updated columns
    private Object[][] data;
    private int sortColumn = -1;
    private boolean ascending = true;
    private int clickCount = 0;
    private JTextField searchField;

    public GeneralDbPage() {
        super(new BorderLayout());
        GeneralDB generalDB = new GeneralDB("src/data/GeneralDatabase.csv");

        ArrayList<Book> books = generalDB.readBooksFromCSV();
        data = toObjectArray(books);

        model = new DefaultTableModel(data, columns);
        table = new JTable(model) {
            // @Override
            // public boolean isCellEditable(int row, int column) {
            //     return false;
            // }
        };

        new TableCellListener(table, 0, (row, newValue) -> {
            System.out.println(books.get(row).toString());
        
            Book newBook = books.get(row).clone();
            newBook.setTitle(newValue);
            try {
                generalDB.updateBook(newBook);
                
            } catch (Exception e) {
                e.printStackTrace();
                // TODO: handle exception
            }
        });

        new TableCellListener(table, 1, (row, newValue) -> {
            System.out.println(books.get(row).toString());
        
            Book newBook = books.get(row).clone();
            newBook.setAuthor(newValue);
            try {
                generalDB.updateBook(newBook);
                
            } catch (Exception e) {
                e.printStackTrace();
                // TODO: handle exception
            }
        });


        table.setColumnSelectionAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);


        table.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        // table.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox()));


        table.getColumnModel().getColumn(3).setCellRenderer(new ClickableCellRenderer());
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = table.getColumnModel().getColumnIndexAtX(e.getX());
                int row = e.getY() / table.getRowHeight();
                if (column == 3 && row < table.getRowCount()) {
                    System.out.println("Clicked on: " + table.getValueAt(row, column) + " -> " + row + " " + column);
                    System.out.println(generalDB.readBooksFromCSV().get(row).getTitle());
                    Book book = generalDB.readBooksFromCSV().get(row);
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
        Object[][] result = new Object[books.size()][5]; // Updated array size
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            result[i][0] = book.getTitle();
            result[i][1] = book.getAuthor();
            result[i][2] = (book.getAverageRating() != -1) ? book.getAverageRating() : "No ratings";
            result[i][3] = book.getReviewsUsersString();
            // Creating JButton for each row


            // JButton button = new JButton("Action");
            // button.addActionListener(this);
            result[i][4] = "Edit";
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
                    Comparable<String> c2 = (Comparable) o2;
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
            // Handle button clicks here
            JButton button = (JButton) e.getSource();
            // Find the row of the clicked button
            int row = table.rowAtPoint(button.getLocation());
            // Perform action based on the row
            // For example, print the title and author of the book
            System.out.println("Button clicked in row: " + row);
            System.out.println("Title: " + data[row][0] + ", Author: " + data[row][1]);
        } else if (e.getSource() instanceof JButton) {
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


class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
      setOpaque(true);
    }
  
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
      if (isSelected) {
        setForeground(table.getSelectionForeground());
        setBackground(table.getSelectionBackground());
      } else {
        setForeground(table.getForeground());
        setBackground(UIManager.getColor("Button.background"));
      }
      setText((value == null) ? "" : value.toString());
      return this;
    }
  }