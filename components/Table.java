package components;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class Table extends JFrame {
    JScrollPane scrollPane;
    JTable table;
    String[] columns;
    String[][] data;

 
    
    public Table() { 
        columns = new String[]{"Title", "Author"};
        data = getData("C:\\Users\\emin0\\Desktop\\team-project-team-54\\data\\brodsky.csv");
        table = new JTable(data, columns);
        scrollPane = new JScrollPane(table);
        setBounds(10, 10, 500, 140);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("General Database");
        this.add(scrollPane);
        this.pack();

        table.setRowHeight(50);
        TableColumnModel colmod = table.getColumnModel();
        colmod.setColumnMargin(10);

        this.setVisible(true);
    }
    public String[][] getData(String filename) {

        BufferedReader reader;
        ArrayList<String> list = new ArrayList<>();
        String str = "";

        try {
            reader = new BufferedReader(new FileReader(filename));
            str = reader.readLine(); // to remove first row Title,Author
            while ((str = reader.readLine()) != null) {
                if (str == "") {
                    list.add("Unknown");
                }
                else {
                    list.add(str);
                }
                System.out.println("LINE: " +  str);
            }
            int n = list.get(0).split(",").length;
            String[][] data = new String[list.size()][n];
            for (int i = 0; i < list.size(); i++) {
                data[i] = list.get(i).split(",", 2);
                if (data[i][1] == "") {
                    data[i][1] = "Unknown";
                }
                else {
                    data[i][1] = list.get(i).split(",")[1];

                }
                System.out.println("DATA: " + data[i][1].toString() + ", " + list.get(i).split(","));
            }
            reader.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        
    }
    // public static void main(String[] args) {
    //     new Table();
    //     // new Demo("klsfmglksmg").setVisible(true);
    // }
}