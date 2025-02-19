package GUI;

import javax.security.auth.RefreshFailedException;
import javax.security.auth.Refreshable;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EventObject;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.MouseEvent;
import java.io.IOException;

import classes.Book;
import classes.ProfileBook;
import classes.Review;
import database.GeneralDB;
import database.PersonalDB;
import utils.TableCellListener;


public class GeneralDbPage extends JPanel implements ActionListener {
    Locale locale = Locale.getDefault();
    ResourceBundle bundle = ResourceBundle.getBundle("Messages", locale);
    private JTable table;
    private DefaultTableModel model;
    private String[] columns = {
        bundle.getString("generalDb.title"), 
        bundle.getString("generalDb.author"), 
        bundle.getString("generalDb.ratings"), 
        bundle.getString("generalDb.reviews"), 
        bundle.getString("generalDb.actions")
    }; // Updated columns
    private int sortColumn = -1;
    private boolean ascending = true;
    private String username;
    private Refreshable parentFrame;
    private Object[][] data;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField searchField;
    private JButton searchButton;
    private int sortCount = 0;

    private Boolean isAdmin;
    


    public GeneralDbPage(Refreshable parentFrame, String username, Boolean isAdmin) {
        super(new BorderLayout());
        this.username = username;
        this.parentFrame = parentFrame;
        this.isAdmin = isAdmin;
        GeneralDB generalDB = new GeneralDB("src/data/GeneralDatabase.csv");
        ArrayList<Book> books = generalDB.readBooksFromCSV();
        data = toObjectArray(books);

        model = new DefaultTableModel(data, columns);
        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (!isAdmin) {
                    return column == 4;
                }
                else {
                    return isAdmin;
                }
            }

        };
        if (isAdmin) {
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
            
        }
        if (isAdmin) {
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
        }

        table.setColumnSelectionAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);

        // table.getColumn(bundle.getString("generalDb.actions")).setCellRenderer(new TableToggleButton(books, this.username, this.parentFrame));
        // table.getColumn(bundle.getString("generalDb.actions")).setCellEditor(new TableToggleButton(books, this.username, this.parentFrame));
        table.getColumn(bundle.getString("generalDb.actions")).setCellRenderer(new ButtonCellRendererEditor(books, username, parentFrame, this.isAdmin));
        table.getColumn(bundle.getString("generalDb.actions")).setCellEditor(new ButtonCellRendererEditor(books, username, parentFrame, this.isAdmin));

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
        searchButton = new JButton(bundle.getString("generalDb.search"));
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

        table.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int column = table.columnAtPoint(e.getPoint());
                sortCount++;
                switch (sortCount % 3) {
                    case 0: // keeps original order
                        sorter = null;
                        table.setRowSorter(null);
                        break;
                    case 1: // ascending order
                        sorter = new TableRowSorter<>(model);
                        table.setRowSorter(sorter);
                        sorter.toggleSortOrder(column);
                        ascending = true;
                        break;
                    case 2: // descending order
                        sorter = new TableRowSorter<>(model);
                        table.setRowSorter(sorter);
                        sorter.toggleSortOrder(column);
                        sorter.toggleSortOrder(column);
                        ascending = false;
                        break;
                }
                System.out.println(ascending);
            }
        });


        setSize(700, 400);
    }

    public void reloadPage() {
        // System.out.println("REFRESH GENERAL PAGE");
        GeneralDB generalDB = new GeneralDB("src/data/GeneralDatabase.csv");
        ArrayList<Book> books = generalDB.readBooksFromCSV();
        data = toObjectArray(books);
    
        // Retrieve the resource bundle based on the current locale
        ResourceBundle bundle = ResourceBundle.getBundle("Messages", LocaleChanger.getCurrentLocale());
    
        // Update column titles with translated values
        columns[0] = bundle.getString("generalDb.title");
        columns[1] = bundle.getString("generalDb.author");
        columns[2] = bundle.getString("generalDb.ratings");
        columns[3] = bundle.getString("generalDb.reviews");
        columns[4] = bundle.getString("generalDb.actions");
    
        model.setDataVector(data, columns);
        searchButton.setText(bundle.getString("generalDb.search"));

        table.getColumn(bundle.getString("generalDb.actions")).setCellRenderer(new ButtonCellRendererEditor(books, username, parentFrame, this.isAdmin));
        table.getColumn(bundle.getString("generalDb.actions")).setCellEditor(new ButtonCellRendererEditor(books, username, parentFrame, this.isAdmin));
        this.repaint();
    }

    public static void openNewWindow(Book book) {
        new ReviewsPage(book);
    }

    public Object[][] toObjectArray(ArrayList<Book> books) {
        ResourceBundle bundle = ResourceBundle.getBundle("Messages", LocaleChanger.getCurrentLocale());
        Object[][] result = new Object[books.size()][5]; // Updated array size
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            result[i][0] = book.getTitle();
            result[i][1] = (!book.getAuthor().equals("Unknown")) ? book.getAuthor() : bundle.getString("generalDb.unknown");
            result[i][2] = (book.getAverageRating() != -1) ? book.getAverageRating() : bundle.getString("generalDb.noRatings");
            result[i][3] = (!book.getReviewsUsersString().equals("No Reviews")) ? book.getReviewsUsersString() : bundle.getString("generalDb.noReviews");
            result[i][4] = this.isAdmin ? bundle.getString("generalDb.delete") : bundle.getString("generalDb.addToFavoruite");
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
            @SuppressWarnings({ "rawtypes", "unchecked" })
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

        // Convert the filtered data to an array
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
            JButton button = (JButton) e.getSource();
            if (button.getText().equals(bundle.getString("generalDb.search"))) {
                performSearch(searchField.getText());
            }
        }
    }

    static class ButtonCellRendererEditor extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
        private JButton button;
        private Object[][] data;
        private int row;
        private ArrayList<Book> books;
        private String username;
        private Refreshable parentFrame;

        @Override
        public boolean shouldSelectCell(EventObject anEvent) {
            return true; // Allow cell selection for button interaction
        }

        public ButtonCellRendererEditor(ArrayList<Book> books, String username, Refreshable parentFrame, Boolean isAdmin) {
            this.books = books;
            this.username = username;
            this.parentFrame = parentFrame;

            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (!isAdmin) {
                        Book book = books.get(row);
                        System.out.println(book.toString());
                        
                        ProfileBook profileBook = new ProfileBook(book);
                        EditProfileBook editProfileBook = new EditProfileBook(profileBook, username, parentFrame);
                        editProfileBook.setVisible(true);

                        if (editProfileBook.isActive()) {
                            System.out.println("editProfileBook is Active ");
                        }
                    }
                    else {
                        Book book = books.get(row);
                        System.out.println(book.toString());

                        GeneralDB generalDB = new GeneralDB("src/data/GeneralDatabase.csv");

                        try {
                            PersonalDB.deleteBookAcrossAllDatabases(book.getId());
                        } catch (Exception er) {
                            System.err.println("Error in GeneralDbPage PersonalDB.deleteBookAcrossAllDatabases");
                        }
                        
                        try {
                            generalDB.deleteBook(book.getId());

                            parentFrame.refresh();
                        } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
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