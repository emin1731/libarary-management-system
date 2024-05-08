package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.RoundRectangle2D;

public class RoundedButton extends JButton {

    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false); 
        setFocusPainted(false); 
        setBorderPainted(false); 
    }

    // Override the paintComponent method to customize the button's appearance
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        Shape shape = new RoundRectangle2D.Float(0, 0, width, height, 20, 20);

        g2.setColor(getBackground());

        g2.fill(shape);

        g2.setColor(getForeground());
        g2.drawString(getText(), (width - g2.getFontMetrics().stringWidth(getText())) / 2,
                (height + g2.getFontMetrics().getAscent()) / 2);

        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        size.width += 20; 
        return size;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rounded Button Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        RoundedButton loginButton = new RoundedButton("Login");
        loginButton.setBounds(30, 280, 80, 25);
        loginButton.setFocusable(false);
        loginButton.setBackground(new Color(0x8fbc8f));
        loginButton.setForeground(Color.WHITE);
        frame.add(loginButton);

        RoundedButton registerButton = new RoundedButton("Register");
        registerButton.setBounds(180, 280, 100, 25);
        registerButton.setFocusable(false);
        registerButton.setBackground(new Color(0x8fbc8f));
        registerButton.setForeground(Color.WHITE);
        frame.add(registerButton);

        frame.setSize(700, 525);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
