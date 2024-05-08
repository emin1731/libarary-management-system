package GUI;

import javax.security.auth.RefreshFailedException;
import javax.security.auth.Refreshable;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import classes.Book;
import classes.ProfileBook;
import classes.ProfileBook.Status;
import database.GeneralDB;
import database.PersonalDB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EventObject;
import java.util.Locale;
import java.util.ResourceBundle;


public class PersonalDbPage extends JPanel {
    Locale locale = Locale.getDefault();
    ResourceBundle bundle = ResourceBundle.getBundle("Messages", locale);

    private JTable table;
    private DefaultTableModel model;
    private Refreshable parentFrame;
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
        bundle.getString("profilePage.userReview"),
        bundle.getString("profilePage.action")
    };
    private Object[][] data;

    private int sortColumn = -1;
    private boolean ascending = true;
    private int clickCount = 0;
    private String username;
    private Boolean isAdmin;

    public PersonalDbPage(Refreshable parentFrame, String username, Boolean isAdmin) {
        super(new BorderLayout());
        this.username = username;
        this.parentFrame = parentFrame;
        this.isAdmin = isAdmin;

        ArrayList<ProfileBook> users = PersonalDB.readPersonalBooksFromCSV("src/data/users/" + username + ".csv");
        data = toObjectArray(users);

        model = new DefaultTableModel(data, columns);
        table = new JTable(model);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        table.setDefaultRenderer(Object.class, renderer);

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

        Font tableFont = new Font("Arial", Font.PLAIN, 12);
        Color tableForeground = Color.BLACK;
        Color tableBackground = Color.WHITE;
        Color tableHeaderBackground = new Color(0, 102, 204); 
        Color tableHeaderForeground = Color.WHITE;
        table.setFont(tableFont);
        table.setForeground(tableForeground);
        table.setBackground(tableBackground);
        table.getTableHeader().setFont(tableFont);
        table.getTableHeader().setForeground(tableHeaderForeground);
        table.getTableHeader().setBackground(tableHeaderBackground);

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

        table.setColumnSelectionAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);

        table.getColumn(bundle.getString("profilePage.action")).setCellRenderer(new ButtonCellRendererEditor(users, username, parentFrame, this.isAdmin));
        table.getColumn(bundle.getString("profilePage.action")).setCellEditor(new ButtonCellRendererEditor(users, username, parentFrame, this.isAdmin));


        // Create a button
        JButton button = new JButton("Click me");
        button.addActionListener(e -> {
            // Handle button click event here
        });

        // Customizing button colors
        button.setBackground(Color.BLUE); 
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false); 
        button.setFont(new Font("Arial", Font.BOLD, 14));

        // Add the button to the panel at the bottom right
        add(button, BorderLayout.SOUTH);

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
        columns[10] = bundle.getString("profilePage.action");
    
        model.setDataVector(data, columns);

        table.getColumn(bundle.getString("profilePage.action")).setCellRenderer(new ButtonCellRendererEditor(users, username, parentFrame, this.isAdmin));
        table.getColumn(bundle.getString("profilePage.action")).setCellEditor(new ButtonCellRendererEditor(users, username, parentFrame, this.isAdmin));
    }



    private Object[][] toObjectArray(ArrayList<ProfileBook> users) {
        ResourceBundle bundle = ResourceBundle.getBundle("Messages", LocaleChanger.getCurrentLocale());

        Object[][] result = new Object[users.size()][11];
        for (int i = 0; i < users.size(); i++) {
            ProfileBook user = users.get(i);
            String userStatus = "Undefined";
            switch (user.getStatus()) {
                case NOT_STARTED:
                    userStatus = bundle.getString("profilePage.statusNotStarted");
                    break;
                case ONGOING:
                    userStatus = bundle.getString("profilePage.statusOngoing");
                    break;
                case COMPLETED:
                    userStatus = bundle.getString("profilePage.statusCompleted");
                    break;
                default:
                    System.out.println("Unknown status");
              }

            result[i][0] = user.getTitle();
            result[i][1] = user.getAuthor();
            result[i][2] = (user.getAverageRating() != -1) ? user.getAverageRating() : bundle.getString("profilePage.noRatings");
            result[i][3] = (!user.getReviewsUsersString().equals("No Reviews")) ? user.getAverageRating() : bundle.getString("profilePage.noReviews");
            result[i][4] = userStatus;
            result[i][5] = user.getTimeSpent();
            result[i][6] = user.getStartDate().format(DateTimeFormatter.ofPattern("dd LLLL yyyy"));
            result[i][7] = (user.getEndDate() == null) ? bundle.getString("profilePage.noEndDate") : user.getEndDate().format(DateTimeFormatter.ofPattern("dd LLLL yyyy"));
            result[i][8] = user.getRating();
            result[i][9] = user.getReview();
            result[i][10] = bundle.getString("profilePage.action");
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

    static class ButtonCellRendererEditor extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
        private JButton button;
        private Object[][] data;
        private int row;
        private ArrayList<ProfileBook> profileBooks;
        private String username;
        private Refreshable parentFrame;

        @Override
        public boolean shouldSelectCell(EventObject anEvent) {
            return true; // Allow cell selection for button interaction
        }

        public ButtonCellRendererEditor(ArrayList<ProfileBook> profileBooks, String username, Refreshable parentFrame, Boolean isAdmin) {
            this.profileBooks = profileBooks;
            this.username = username;
            this.parentFrame = parentFrame;

            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (!isAdmin) {
                        // Book book = books.get(row);
                        // System.out.println(book.toString());
    
                        // ProfileBook profileBook = new ProfileBook(book);
                        // new EditProfileBook(profileBook, username, parentFrame).setVisible(true);

                        System.out.println("USER PRESS DELETE");
                    }
                    else {
                        System.out.println("USER PRESS DELETE");
                        // Book book = books.get(row);
                        // System.out.println(book.toString());

                        // GeneralDB generalDB = new GeneralDB("src/data/GeneralDatabase.csv");

                        // try {
                        //     PersonalDB.deleteBookAcrossAllDatabases(book.getId());
                            
                            
                            
                            
                        // } catch (Exception er) {
                        //     System.err.println("Error in GeneralDbPage PersonalDB.deleteBookAcrossAllDatabases");
                        //     // er.printStackTrace();
                        //     // TODO: handle exception
                        // }
                        
                        // try {
                        //     generalDB.deleteBook(book.getId());
                        // } catch (IOException e1) {
                        //     // TODO Auto-generated catch block
                        //     e1.printStackTrace();
                        // }
                        
                        // try {
                        //     parentFrame.refresh();
                        // } catch (RefreshFailedException e1) {
                        //     // TODO Auto-generated catch block
                        //     e1.printStackTrace();
                        // }
                        // GeneralDB generalDB = new GeneralDB("emin");
                        // generalDB.deleteBook();
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
