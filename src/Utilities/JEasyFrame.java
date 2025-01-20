package Utilities;

import javax.swing.*;
import java.awt.*;

public class JEasyFrame extends JFrame {
    public Component comp;

    public JEasyFrame(Component c, String title){
        super(title);
        this.comp = c;
        getContentPane().add(BorderLayout.CENTER,comp);
        pack();
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
    }
}
