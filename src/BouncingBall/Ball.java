package BouncingBall;
import java.awt.*;

public class Ball {

    public double x,y,vx,vy;
    public double radius;
    public Color color;

    public static final int BOX_X = 100;
    public static final int BOX_Y = 100;
    public static final int BOX_WIDTH = 300;
    public static final int BOX_HEIGHT = 300;

    public static final int BALL_MAX_SPEED = 20;

    public Ball( double radius,Color color){
        this.x = BOX_X + radius +  Math.random() * (BOX_WIDTH - 2 * radius);
        this.y = BOX_Y + radius +  Math.random() * (BOX_HEIGHT - 2 * radius);
        this.vx = Math.random() * BALL_MAX_SPEED;
        this.vy = Math.random() * BALL_MAX_SPEED;
        this.radius = radius;
        this.color = color;
    }

    // Collision handling
    public void update(){
        // update the x and y position with the velocity
        x += vx;
        y += vy;
        if(x - radius < BOX_X || x + radius > BOX_X + BOX_WIDTH){
            vx *= -1;
            radius = reduceSize(radius);
        }
        if(y - radius < BOX_Y
                || y + radius > BOX_Y + BOX_HEIGHT){
            vy *= -1;
            radius = reduceSize(radius);
        }
    }
    public double reduceSize(Double radius){
        return radius -2;
    }
}
