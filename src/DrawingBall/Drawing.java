package DrawingBall;

import javax.swing.*;
import java.awt.*;

public class Drawing extends JComponent {
    public static void main(String[] a){
        new JEasyFrame(new Drawing(), "Ball Exercise"); // passes the constructor
    }

    @Override
    public void paintComponent(Graphics graphics){
        Graphics2D g = (Graphics2D) graphics; // cast graphics to a Graphics2D object for more features
        circles(g);
    }

    public void circles(Graphics2D g){
        for(int i = 0; i < 50; i++){
            g.setColor(new Color(rnd(256),rnd(256),rnd(256)));
            int r = 5 * rnd(60);
            g.fillOval(rnd(600),rnd(600),r,r);
        }
    }

    public static int rnd(int n ){
        return (int)(Math.random() * n);
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(Frame.WIDTH,Frame.HEIGHT);
    }
}
