package SpaceSurvivorGame.GameObjects;

import java.awt.*;

import SpaceSurvivorGame.Utilities.Vector2D;

import static SpaceSurvivorGame.Config.Configurations.*;

/**
 * Particle effect class used for visual effects in the Game.
 */
public class Particle extends GameObject {
    private double lifetime;

    /**
     * Constructs a Particle with position, velocity, lifetime, and color.
     *
     * @param x        start X position.
     * @param y        start Y position.
     * @param vx       Velocity in the X axis.
     * @param vy       Velocity in the Y axis.
     * @param lifetime Duration the particle remains active.
     * @param color    Color of the particle.
     */
    public Particle(double x, double y, double vx, double vy, double lifetime, Color color) {
        super(new Vector2D(x, y), new Vector2D(vx, vy), 12);
        this.lifetime = lifetime;
    }

    /**
     * Checks if the particle is active based on its remaining lifetime.
     *
     * @return true if lifetime is greater than 0, false otherwise.
     */
    @Override
    public boolean isAlive() {
        return lifetime > 0;
    }

    /**
     * Marks the particle as inactive.
     */
    @Override
    public void setAlive() {
        lifetime = 0;
    }

    /**
     * Updates particle position and decreases its lifetime.
     */
    @Override
    public void update() {
        position.addScaled(velocity, DT);
        lifetime -= DT;
    }

    /**
     * Draws the particle and adjusting transparency based on remaining lifetime.
     *
     * @param g Graphics2D  used for rendering.
     */
    @Override
    public void draw(Graphics2D g) {
        float alpha = (float) Math.max(lifetime, 0);
        Color c = new Color(255, 255, 255, (int) (alpha * 255));
        g.setColor(c);
        g.fillOval((int) position.x, (int) position.y, 4, 4);
    }
}
