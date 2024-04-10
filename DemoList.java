import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.*;

public class DemoList extends JFrame {

    private JButton browseButton;
    private JTextField filePathField;
    private JPanel labelPanel;

    public DemoList() {
        super("CSV Label Generator");
        // Set layout
        setLayout(new BorderLayout());

        // Top panel for file selection
        JPanel topPanel = new JPanel();
        filePathField = new JTextField(20);
        browseButton = new JButton("Browse");
        browseButton.addActionListener(new BrowseButtonListener());
        topPanel.add(new JLabel("CSV File:"));
        topPanel.add(filePathField);
        topPanel.add(browseButton);
        add(topPanel, BorderLayout.NORTH);

        // Panel to hold generated labels
        labelPanel = new JPanel();
        labelPanel.setLayout(new FlowLayout());
        add(labelPanel, BorderLayout.CENTER);

        // Set size and make visible
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private class BrowseButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int selection = fileChooser.showOpenDialog(DemoList.this);

            if (selection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getPath();
                filePathField.setText(filePath);
                generateLabels(filePath);
            }
        }
    }

    private void generateLabels(String filePath) {
        labelPanel.removeAll(); // Clear existing labels

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip the first line (assuming it contains headers)
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                for (String value : values) {
                    JLabel label = new JLabel(value);
                    labelPanel.add(label);
                }
            }
            // Revalidate and repaint to show changes
            labelPanel.revalidate();
            labelPanel.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reading CSV file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DemoList());
    }
}