import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Sorting extends JFrame implements ActionListener {

    private JTable table;
    private DefaultTableModel model;
    private String[] columns = {"Title", "Author", "Ratings", "Reviews"};
    private Object[][] data;
    private int sortColumn = -1;
    private boolean ascending = true;
    private int clickCount = 0;
    private JTextField searchField;

    public Sorting(String csvFilePath) {
        ArrayList<Book> books = GeneralDB.readBooksFromCSV("Chapter1/src/data/GeneralDatabase.csv");
        data = toObjectArray(books);

        model = new DefaultTableModel(data, columns);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Create a panel for the search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search Here");
        searchButton.addActionListener(this); // Register the button for ActionListener
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        getContentPane().add(searchPanel, BorderLayout.NORTH);

        table.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
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

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
    }

    public Object[][] toObjectArray(ArrayList<Book> books) {
        Object[][] result = new Object[books.size()][4];
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            result[i][0] = book.getTitle();
            result[i][1] = book.getAuthor();
            result[i][2] = "Ratings"; // Placeholder for ratings
            result[i][3] = "Reviews"; // Placeholder for reviews
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Sorting("C:/Users/ismai/OneDrive/Рабочий стол/ayla pp2 project/team-project-team-54/brodsky.csv").setVisible(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            performSearch(searchField.getText());
        }
    }
}
