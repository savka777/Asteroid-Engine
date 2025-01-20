package BouncingBall;

import DrawingBall.JEasyFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import static BouncingBall.Ball.*;

public class BouncingBall extends JComponent {

    public Ball ball;
    public Rectangle2D.Double rect;
    public Ellipse2D.Double circle;

    public static void main(String[] args) throws InterruptedException{
            BouncingBall bb = new BouncingBall();
            JEasyFrame frame = new JEasyFrame(bb,"Bouncing Ball");
            while(true){
                bb.ball.update();
                frame.repaint();
                Thread.sleep(100);
        }
    }
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(JEasyFrame.WIDTH,JEasyFrame.HEIGHT);
    }

    public BouncingBall(){
        ball = new Ball(50,Color.BLACK);
        rect = new Rectangle2D.Double(BOX_X, BOX_Y, BOX_WIDTH,BOX_HEIGHT);
        circle = new Ellipse2D.Double();
    }

    @Override
    public void paintComponent(Graphics graphics){
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.BLUE);
        g.draw(rect);
        g.setColor(ball.color);
        circle.setFrame(ball.x-ball.radius, ball.y-ball.radius, 2*ball.radius,2*ball.radius);
        g.fill(circle);
    }
}
