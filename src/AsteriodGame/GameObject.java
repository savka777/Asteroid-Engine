package AsteriodGame;

import java.awt.*;
import java.awt.geom.Area;

import Utilities.Vector2D;

public abstract class GameObject {
    
    public Vector2D position; 
    public Vector2D velocity;
    public boolean dead; 
    public double radius;
    public boolean isAlive = true;


    public GameObject(Vector2D position, Vector2D velocity, double radius){
        this.position = position;
        this.velocity = velocity;
        this.radius = radius;
        this.dead = false;
        System.out.println("GameObject constructor: velocity = " + this.velocity);
    }

//    public boolean overlap(GameObject other){
//        Area area = new Area((Shape) this);
//        area.intersect(new Area((Shape) other));
//        return !area.isEmpty();
//    }

    public boolean overlap(GameObject other) {
        double dx = this.position.x - other.position.x;
        double dy = this.position.y - other.position.y;
        double distanceSquared = dx * dx + dy * dy;
        double radiusSum = this.radius + other.radius;
        return distanceSquared <= radiusSum * radiusSum;
    }
    public void collisionHandling(GameObject other){
        if(this.getClass() != other.getClass() && this.overlap(other)){
            if((this instanceof Bullet && other instanceof Asteriod) ||
            this instanceof Asteriod && other instanceof Bullet){
                this.setAlive();
                other.setAlive();
            }

            if(this instanceof Ship && other instanceof Asteriod){
                this.setAlive();
                // disable bullet
                ((Ship) this).canShoot = false;

            }else if(this instanceof Asteriod && other instanceof Ship){
                other.setAlive();
                // disable bullet
                ((Ship) other).canShoot = false;
            }

            // add collision rules such as scores and other stuff i might need
        }
    }

    public abstract void update();
    public abstract boolean isAlive();
    public abstract void setAlive();

    public abstract void draw(Graphics2D g);
}
