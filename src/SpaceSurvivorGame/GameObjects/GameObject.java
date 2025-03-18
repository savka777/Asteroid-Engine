package SpaceSurvivorGame.GameObjects;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import SpaceSurvivorGame.Enemy.EnemyShip;
import SpaceSurvivorGame.Managers.GameManager;
import SpaceSurvivorGame.Player.PLayerShip;
import SpaceSurvivorGame.Utilities.Vector2D;

/**
 * Game object class is abstract and represents the general game objects in the game.
 */
public abstract class GameObject {

    public Vector2D position;
    public Vector2D velocity;
    public boolean dead;
    public double radius;
    public boolean isAlive = true;

    /**
     * Constructs a GameObject with position, velocity, and radius.
     *
     * @param position start position vector.
     * @param velocity start velocity vector.
     * @param radius   collision radius.
     */
    public GameObject(Vector2D position, Vector2D velocity, double radius) {
        this.position = position;
        this.velocity = velocity;
        this.radius = radius;
        this.dead = false;
    }

    /**
     * Checks for overlap using distance between two game objects.
     *
     * @param other The other GameObject to check.
     * @return true if objects overlap, false otherwise.
     */
    public boolean overlap(GameObject other) {
        double dx = this.position.x - other.position.x;
        double dy = this.position.y - other.position.y;
        double distanceSquared = dx * dx + dy * dy;
        double radiusSum = this.radius + other.radius;
        return distanceSquared <= radiusSum * radiusSum;
    }

    /**
     * Checks for overlap using area between two game objects.
     * (In game, this method is not used, overlap method is used instead)
     *
     * @param other The other GameObject to check.
     * @return true if areas overlap, false otherwise.
     */
    public boolean overlapWithArea(GameObject other) {
        Ellipse2D.Double circle1 = new Ellipse2D.Double(
                this.position.x - this.radius,
                this.position.y - this.radius,
                this.radius * 2,
                this.radius * 2
        );
        Ellipse2D.Double circle2 = new Ellipse2D.Double(
                other.position.x - other.radius,
                other.position.y - other.radius,
                other.radius * 2,
                other.radius * 2
        );

        Area area1 = new Area(circle1);
        Area area2 = new Area(circle2);
        area1.intersect(area2);
        return !area1.isEmpty();
    }


    /**
     * Handles the collision between this object and another.
     *
     * @param other The other GameObject involved in collision.
     */
    public void collisionHandling(GameObject other) {
        // bullet from same object firing does not kill itself, only bullets from opposing ships
        if (this == other) return;
        if (this instanceof Bullet bullet) {
            if (bullet.whoIsFiring == other) return;
        } else if (other instanceof Bullet bullet) {
            if (bullet.whoIsFiring == this) return;
        }

        if (this.getClass() != other.getClass() && this.overlap(other)) {
            if ((this instanceof Bullet && other instanceof Asteroid) ||
                    this instanceof Asteroid && other instanceof Bullet) {
                GameManager.incScore(100);
                this.setAlive();
                other.setAlive();
                GameManager.spawnExplosion(other.position);
            }

            if (this instanceof PLayerShip && other instanceof Asteroid) {
                this.setAlive();
                GameManager.spawnExplosion(this.position);
                ((PLayerShip) this).canShoot = false;
                GameManager.loseLife();

            } else if (this instanceof Asteroid && other instanceof PLayerShip) {
                other.setAlive();
                ((PLayerShip) other).canShoot = false;
                GameManager.loseLife();

            } else if (this instanceof Bullet && other instanceof PLayerShip) {
                other.setAlive();
                ((PLayerShip) other).canShoot = false;
                GameManager.loseLife();

            } else if (this instanceof PLayerShip && other instanceof Bullet) {
                this.setAlive();
                ((PLayerShip) this).canShoot = false;
                GameManager.loseLife();

            } else if (this instanceof Bullet && other instanceof EnemyShip) {
                GameManager.incScore(500);
                other.setAlive();
                this.setAlive();
                GameManager.spawnExplosion(other.position);

            } else if (this instanceof EnemyShip && other instanceof Bullet) {
                GameManager.incScore(200);
                this.setAlive();
                other.setAlive();
                GameManager.spawnExplosion(this.position);
            }
        }
    }

    /**
     * Abstract method to update object state.
     */
    public abstract void update();

    /**
     * Abstract method to check object life status.
     */
    public abstract boolean isAlive();

    /**
     * Abstract method to mark object as inactive.
     */
    public abstract void setAlive();

    /**
     * Abstract method for rendering object.
     */
    public abstract void draw(Graphics2D g);
}
