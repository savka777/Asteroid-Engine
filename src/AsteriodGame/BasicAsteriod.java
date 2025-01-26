package AsteriodGame;
import java.awt.*;

import static AsteriodGame.Constants.*;

// Game Object
public class BasicAsteriod {
    private double x;
    private double y;
    private double vx;
    private double vy;
    private static final int RADIUS = 10;
    private static final double MAX_SPEED = 500;

    public BasicAsteriod(double x, double y, double vx, double vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    // Making a new Game Object with random physics properties added to them
    public static BasicAsteriod MakeRandomAsteroid(){
        // Generate random angle in radians (0 to 2π)
        double angle = Math.random() * 2 * Math.PI;

        // Generate random speed (magnitude of velocity)
        double speed = Math.random() * MAX_SPEED;

        // Convert polar coordinates (angle & speed) to cartesian (x & y velocities)
        double vx = speed * Math.cos(angle);  // horizontal component
        double vy = speed * Math.sin(angle);  // vertical component

        return new BasicAsteriod(
                Math.random() * FRAME_WIDTH,
                Math.random() * FRAME_HEIGHT,
                vx,
                vy
        );
    }

    // Update the physics to our game objects for animation, this is called by our game manager
    public void update(){
        x += vx * DT;
        y += vy * DT;
        x = (x + FRAME_WIDTH) % FRAME_WIDTH;
        y = (y + FRAME_HEIGHT) % FRAME_HEIGHT;
    }

    // Render a game object
    public void draw(Graphics2D g){
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));

        g.setColor(Color.RED);
        g.fillOval((int) x - RADIUS, (int) y - RADIUS, 2 * RADIUS, 2*RADIUS);

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
    }
}
