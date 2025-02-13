package AsteriodGame;

import static AsteriodGame.Constants.*;

import java.awt.*;
import java.awt.geom.AffineTransform;

import Utilities.Vector2D;

// Game Object
public class Asteriod extends GameObject {
    // coordinate vector
    private static final int BASE_RADIUS = 10;
    private static final double MAX_SPEED = 200;
    private Polygon shape;
    private int radius;

    public Asteriod(double x, double y, double vx, double vy) {
        super(new Vector2D(x, y), new Vector2D(vx, vy), BASE_RADIUS);
        this.radius = BASE_RADIUS + (int) (Math.random() * 11);
        generateRandomShape();
        position.add(super.velocity);
    }

    private void generateRandomShape() {
        int numVertices = 8 + (int) (Math.random() * 5);
        int[] yPoints = new int[numVertices];
        int[] xPoints = new int[numVertices];
        double angleStep = 2 * Math.PI / numVertices;

        for (int i = 0; i < numVertices; i++) {
            double angle = i * angleStep;

            double randomRadius = radius * (0.8 + Math.random() * 0.4);
            xPoints[i] = (int) (randomRadius * Math.cos(angle));
            yPoints[i] = (int) (randomRadius * Math.sin(angle));
        }
        shape = new Polygon(xPoints, yPoints, numVertices);
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


    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void setAlive() {
        isAlive = false;
    }

    @Override
    public void update() {

        position.addScaled(velocity, DT);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT); // Wraps around
    }

    // Render a game object
    @Override
    public void draw(Graphics2D g) {
        AffineTransform oldTransform = g.getTransform();

        g.translate(position.x, position.y);
        g.setColor(Color.WHITE);
        g.drawPolygon(shape);
        g.setTransform(oldTransform);
    }
}
