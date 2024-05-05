package GUI;


import javax.security.auth.Refreshable;
import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;


import classes.Book;
import classes.ProfileBook;

public class TableToggleButton extends JButton implements TableCellRenderer, TableCellEditor {

    private boolean state;
    private int row;
    private static final String ACTION_ONE_TEXT = "Action One";
    private static final String ACTION_TWO_TEXT = "Action Two";
    ArrayList<Book> books;
    String username;
    Refreshable parentFrame;

    public TableToggleButton(ArrayList<Book> books, String username, Refreshable parentFrame) {
        this.books = books;
        this.username = username;
        this.parentFrame = parentFrame;

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = !state;
                if (state) {
                    
                    performActionOne();
                    setText(ACTION_TWO_TEXT);
                } else {
                    performActionTwo();
                    setText(ACTION_ONE_TEXT);
                }
                System.out.println("Button state: " + state + ", Row: " + row);
            }
        });
        setOpaque(true);
    }

    private void performActionOne() {
        Book book = books.get(row);
        System.out.println(book.toString());

        ProfileBook profileBook = new ProfileBook(book);
        new EditProfileBook(profileBook, this.username, parentFrame).setVisible(true);;

        // PersonalDB.writePersonalBookToCSV(profileBook, "src/data/users/" + this.username + ".csv");
        // try {
        //     // SwingUtilities.updateComponentTreeUI((Component) this.parentFrame);
        //     parentFrame.refresh();
            
        // } catch (Exception e) {
        //     System.out.println("ERROR");
        //     // TODO: handle exception
        // }


        // Code for Action One
        System.out.println("Action One performed");
    }

    private void performActionTwo() {
        // Code for Action Two
        System.out.println("Action Two performed");
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }
        setText((value == null) ? ACTION_ONE_TEXT : value.toString());
        this.row = row; // Set the row
        return this;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.row = row; // Set the row
        return this;
    }

    @Override
    public Object getCellEditorValue() {
        return state;
    }

    @Override
    public boolean isCellEditable(EventObject e) { return true; }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) { return true; }

    @Override
    public boolean stopCellEditing() { return true; }

    @Override
    public void cancelCellEditing() {
        state = !state; // revert state if cancelled
    }

    @Override
    public void addCellEditorListener(CellEditorListener l) { }

    @Override
    public void removeCellEditorListener(CellEditorListener l) { }
}