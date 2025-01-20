package DrawingBall;
import javax.swing.JFrame;
import java.awt.*;

public class JEasyFrame extends JFrame{
    public Component comp;
    public JEasyFrame(Component c, String title){
        super(title);
        setSize(1000,1000);
        this.comp = c;
        getContentPane().add(BorderLayout.CENTER,comp);  // returns a Container to place the Components that you want to display in the JFrame
        //pack(); // re size's based on the components in the Frame
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
    }
}
