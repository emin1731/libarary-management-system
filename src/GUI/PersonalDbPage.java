package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;

import classes.ProfileBook;
import database.PersonalDB;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.ResourceBundle;


public class PersonalDbPage extends JPanel {
    Locale locale = Locale.getDefault();
    ResourceBundle bundle = ResourceBundle.getBundle("Messages", locale);

    private JTable table;
    private DefaultTableModel model;
    // private String[] columns = {"Title", "Author", "Ratings", "Reviews", "Status", "Time Spent", "Start Date", "End Date", "User Rating", "User Review"};
    private String[] columns = {
        bundle.getString("profilePage.title"), 
        bundle.getString("profilePage.author"), 
        bundle.getString("profilePage.ratings"), 
        bundle.getString("profilePage.reviews"), 
        bundle.getString("profilePage.status"),
        bundle.getString("profilePage.timeSpent"),
        bundle.getString("profilePage.startDate"),
        bundle.getString("profilePage.endDate"),
        bundle.getString("profilePage.userRating"),
        bundle.getString("profilePage.userReview")
    };
    private Object[][] data;

    private int sortColumn = -1;
    private boolean ascending = true;
    private int clickCount = 0;
    private String username;

    public PersonalDbPage(String username) {
        super(new BorderLayout());
        this.username = username;

        ArrayList<ProfileBook> users = PersonalDB.readPersonalBooksFromCSV("src/data/users/" + username + ".csv");
        data = toObjectArray(users);

        model = new DefaultTableModel(data, columns);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

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

        setSize(700, 400);
    }

    public void reloadPage() {
        System.out.println("REFRESH GENERAL PAGE");
        ArrayList<ProfileBook> users = PersonalDB.readPersonalBooksFromCSV("src/data/users/" + username + ".csv");
        data = toObjectArray(users);
        model.setDataVector(data, columns);
    
        ResourceBundle bundle = ResourceBundle.getBundle("Messages", LocaleChanger.getCurrentLocale());

        columns[0] = bundle.getString("profilePage.title"); 
        columns[1] = bundle.getString("profilePage.author"); 
        columns[2] = bundle.getString("profilePage.ratings"); 
        columns[3] = bundle.getString("profilePage.reviews"); 
        columns[4] = bundle.getString("profilePage.status");
        columns[5] = bundle.getString("profilePage.timeSpent");
        columns[6] = bundle.getString("profilePage.startDate");
        columns[7] = bundle.getString("profilePage.endDate");
        columns[8] = bundle.getString("profilePage.userRating");
        columns[9] = bundle.getString("profilePage.userReview");
    
        model.setDataVector(data, columns);
    }



    private Object[][] toObjectArray(ArrayList<ProfileBook> users) {
        Object[][] result = new Object[users.size()][11];
        for (int i = 0; i < users.size(); i++) {
            ProfileBook user = users.get(i);
            result[i][0] = user.getTitle();
            result[i][1] = user.getAuthor();
            result[i][2] = (user.getAverageRating() != -1) ? user.getAverageRating() : "No ratings";
            result[i][3] = user.getReviewsUsersString();
            result[i][4] = user.getStatus().name();
            result[i][5] = user.getTimeSpent();
            result[i][6] = user.getStartDate().format(DateTimeFormatter.ofPattern("dd LLLL yyyy"));
            result[i][7] = (user.getEndDate() == null) ? "End date: null" : user.getEndDate().format(DateTimeFormatter.ofPattern("dd LLLL yyyy"));
            result[i][8] = user.getRating();
            result[i][9] = user.getReview();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Personal Database");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new PersonalDbPage("emin"));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
