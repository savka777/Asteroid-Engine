package SpaceSurvivorGame.GameObjects;

import SpaceSurvivorGame.Managers.SoundManager;
import SpaceSurvivorGame.Utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static SpaceSurvivorGame.Config.Configurations.*;

/**
 * Asteroid class represents the asteroid game objects in the game.
 */
public class Asteroid extends GameObject {
    private static final int BASE_RADIUS = 20;
    private static final double MAX_SPEED = 200;
    private Polygon shape;
    private int radius;

    /**
     * Constructs an Asteroid with start position and velocity.
     *
     * @param x  start X position.
     * @param y  start Y position.
     * @param vx start velocity in the X axis.
     * @param vy start velocity in the Y axis.
     */
    public Asteroid(double x, double y, double vx, double vy) {
        super(new Vector2D(x, y), new Vector2D(vx, vy), BASE_RADIUS);
        this.radius = BASE_RADIUS + (int) (Math.random() * 11);
        generateRandomShape();
        position.add(super.velocity);
    }

    /**
     * Generates random polygon shapes.
     */
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

    /**
     * Generates a random asteroid at a random position and speed.
     *
     * @return a new random Asteroid.
     */
    public static Asteroid MakeRandomAsteroid() {
        double angle = Math.random() * 2 * Math.PI;
        double speed = Math.random() * MAX_SPEED;
        double vx = speed * Math.cos(angle);
        double vy = speed * Math.sin(angle);

        return new Asteroid(
                Math.random() * FRAME_WIDTH,
                Math.random() * FRAME_HEIGHT,
                vx,
                vy);
    }

    /**
     * Checks if the asteroid is alive.
     *
     * @return true if alive, false otherwise.
     */
    @Override
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Sets the asteroid to dead and triggers sound effect.
     */
    @Override
    public void setAlive() {
        isAlive = false;
        SoundManager.playAsteroidExplosionSound();
    }

    /**
     * Updates the asteroids position based on velocity and wrap around the game frame.
     */
    @Override
    public void update() {
        position.addScaled(velocity, DT);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
    }

    /**
     * Draws the asteroid.
     *
     * @param g Graphics2D used for drawing.
     */
    @Override
    public void draw(Graphics2D g) {
        AffineTransform oldTransform = g.getTransform();
        g.translate(position.x, position.y);
        g.setColor(Color.WHITE);
        g.drawPolygon(shape);
        g.setTransform(oldTransform);
    }
}
