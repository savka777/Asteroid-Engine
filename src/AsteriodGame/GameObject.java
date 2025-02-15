package AsteriodGame;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import Utilities.Vector2D;

public abstract class GameObject {

    public Vector2D position;
    public Vector2D velocity;
    public boolean dead;
    public double radius;
    public boolean isAlive = true;


    public GameObject(Vector2D position, Vector2D velocity, double radius) {
        this.position = position;
        this.velocity = velocity;
        this.radius = radius;
        this.dead = false;
        System.out.println("GameObject constructor: velocity = " + this.velocity);
    }

    public boolean overlap(GameObject other) { // using the distance between two centers compared to radii
        double dx = this.position.x - other.position.x;
        double dy = this.position.y - other.position.y;
        double distanceSquared = dx * dx + dy * dy;
        double radiusSum = this.radius + other.radius;
        return distanceSquared <= radiusSum * radiusSum;
    }

    public boolean overlapWithArea(GameObject other) { // using the area overlap between two objects, which is better?
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

        // create Areas from the shapes.
        Area area1 = new Area(circle1);
        Area area2 = new Area(circle2);

        // Intersect the two areas.
        area1.intersect(area2);

        // If the intersection is not empty, the areas overlap.
        return !area1.isEmpty();
    }


    public void collisionHandling(GameObject other) {
        if (this.getClass() != other.getClass() && this.overlapWithArea(other)) {
            if ((this instanceof Bullet && other instanceof Asteriod) ||
                    this instanceof Asteriod && other instanceof Bullet) {
                GameManager.incScore(100);
                this.setAlive();
                other.setAlive();
                GameManager.spawnExplosion(other.position);
            }

            if (this instanceof Ship && other instanceof Asteriod) {
                this.setAlive();
                // disable bullet
                ((Ship) this).canShoot = false;
                GameManager.loseLife();

            } else if (this instanceof Asteriod && other instanceof Ship) {
                other.setAlive();
                // disable bullet
                ((Ship) other).canShoot = false;
                GameManager.loseLife();

            }

            // add collision rules such as scores and other stuff i might need
        }
        // add what happens when asteroid collide
    }

    public abstract void update();

    public abstract boolean isAlive();

    public abstract void setAlive();

    public abstract void draw(Graphics2D g);
}
