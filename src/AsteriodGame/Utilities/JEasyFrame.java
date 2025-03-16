package AsteriodGame.Utilities;

import javax.swing.*;
import java.awt.*;

public class JEasyFrame extends JFrame {
    public Component comp;

    public JEasyFrame(Component c, String title, boolean fullscreenExclusive) {
        super(title);
        this.comp = c;
        getContentPane().add(BorderLayout.CENTER, comp);
        if (!fullscreenExclusive) {
            pack();
            setLocationRelativeTo(null);
            setVisible(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            return;
        }
        setUndecorated(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(this);
        setVisible(true);
        repaint();
    }

    public JEasyFrame(Component c, String title) {
        this(c, title, true);
    }
}
