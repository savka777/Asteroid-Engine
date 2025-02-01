package AsteriodGame;

import java.awt.Graphics2D;

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

    public abstract boolean isAlive();
    public abstract void setAlive();
    public abstract void update();
    public abstract void draw(Graphics2D g);
}
