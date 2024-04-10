package utils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.filechooser.FileSystemView;

public class GetData {
    String path;
    GetData(String filename) {
        this.path = filename;
    }

    public static Object[][] getDataFromCSV() {
        try {
            String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
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

}
