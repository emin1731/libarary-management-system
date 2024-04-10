
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JFrame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.filechooser.FileSystemView;

public class Swing {
    JFrame frame;
    String[] columns;
    Object[][] data;
    JTable table;
    JScrollPane scrollPane;

    Swing() {
        frame = new JFrame();
        columns = new String[]{"name", "author"};
        data = getData();
        table = new JTable(data, columns);
        scrollPane = new JScrollPane();
        frame.add(scrollPane);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Swing Demo");
        frame.setSize(700, 400);
        frame.setResizable(false);

    }
    public Object[][] getData() {
        try {
            // String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
            // BufferedReader br = new BufferedReader(new FileReader(path + "/brodsky.csv"));
            BufferedReader br = new BufferedReader(new FileReader("/Applications/MAMP/htdocs/PP2 Team project/brodsky.csv"));
            ArrayList<String> list = new ArrayList<>();
            String str = "";
            while ((str = br.readLine()) != null) {
                list.add(str);
            }
            int n = list.get(0).split(",").length;
            Object[][] data = new Object[list.size()][n];
            for (int i = 0; i < list.size(); i++) {
                data[i] = list.get(i).split(",");
            }
            br.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        
    }
    public static void main(String[] args) {
        new Swing();
    }
}