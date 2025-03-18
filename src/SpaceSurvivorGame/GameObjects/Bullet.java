package SpaceSurvivorGame.GameObjects;

import static SpaceSurvivorGame.Config.Configurations.*;


import java.awt.Color;
import java.awt.Graphics2D;

import SpaceSurvivorGame.Utilities.Vector2D;

/**
 * Bullet class represents the bullet game object in game that is fired by enemy and ship
 */
public class Bullet extends GameObject {
    private double BulletLifeTime;
    public static final double RADIUS = BULLET_RADIUS;
    private boolean isAlive = true;
    public static final int LIFE = BULLET_LIFE;
    public GameObject whoIsFiring; // check's who is firing


    /**
     * Constructs a bullet with position, velocity, and the game object that fired it.
     *
     * @param whoIsFiring The game object that fired this bullet.
     * @param x           start X coordinate.
     * @param y           start Y coordinate.
     * @param vx          Velocity in the X axis.
     * @param vy          Velocity in the Y axis.
     */
    public Bullet(GameObject whoIsFiring, double x, double y, double vx, double vy) {
        super(new Vector2D(x, y), new Vector2D(vx, vy), RADIUS);
        this.whoIsFiring = whoIsFiring;
        this.BulletLifeTime = LIFE;
    }

    /**
     * Checks if bullet is still active.
     *
     * @return true if the bullet is active, false otherwise.
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Mark bullet as inactive.
     */
    public void setAlive() {
        isAlive = false;
    }

    /**
     * Updates bullet position, lifetime, and checks boundaries to see if bullet should expire.
     */
    @Override
    public void update() {
        super.position.addScaled(super.velocity, 0.1);
        BulletLifeTime -= DT;

        if (BulletLifeTime <= 0 || super.position.x < 0 || super.position.x > FRAME_WIDTH || super.position.y < 0 || super.position.y > FRAME_HEIGHT) {
            setAlive();
        }
    }

    /**
     * Renders the bullet.
     *
     * @param g Graphics2D used for drawing.
     */
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillOval((int) position.x, (int) position.y, 5, 5);
    }
}
