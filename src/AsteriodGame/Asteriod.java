package AsteriodGame;

import static AsteriodGame.Constants.*;

import java.awt.*;
import Utilities.Vector2D;

// Game Object
public class Asteriod extends GameObject {
    // coordinate vector
    private static final int RADIUS = 10;
    private static final double MAX_SPEED = 500;

    public Asteriod(double x, double y, double vx, double vy) {
        super(new Vector2D(x,y),new Vector2D(vx,vy),RADIUS);
        position.add(super.velocity);
    }

    public static Asteriod MakeRandomAsteroid() {
        // Generate random angle in radians (0 to 2π)
        double angle = Math.random() * 2 * Math.PI;

        // Generate random speed (magnitude of velocity)
        double speed = Math.random() * MAX_SPEED;

        // Convert polar coordinates (angle & speed) to cartesian (x & y velocities)
        double vx = speed * Math.cos(angle); // horizontal component
        double vy = speed * Math.sin(angle); // vertical component

        return new Asteriod(
                Math.random() * FRAME_WIDTH,
                Math.random() * FRAME_HEIGHT,
                vx,
                vy);
    }

    // Update the physics to our game objects for animation, this is called by our
    // game manager
    @Override
    public void update() {

        position.addScaled(velocity, DT);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT); // Wraps around
    }

    // Render a game object
    @Override
    public void draw(Graphics2D g) {

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g.setColor(Color.RED);
        g.fillOval((int) position.x - RADIUS, (int) position.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
