package AsteriodGame;

import Utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static AsteriodGame.Settings.*;

public class EnemyShip extends GameObject {

    // Constants can be updated in Constants.java
    public static final int RADIUS = ENEMY_RADIUS;
    public static final double STEER_RATE = ENEMY_STEER_RATE;
    public static final double MAG_ACCELERATION = ENEMY_MAG_ACCELERATION;
    public static final double DRAG = ENEMY_DRAG;
    public static final double SHOOT_DELAY = ENEMY_SHOOT_DELAY;
    public static final Color COLOR = ENEMY_COLOR;
    public Vector2D direction;
    public Controller controller;
    public Bullet bullet = null;
    private boolean isAlive = true;

    private double lastTimeBulletFired = 0;


    public EnemyShip(Controller controller , double startX, double startY) {
        super(new Vector2D(startX,startY), new Vector2D(0, 0), RADIUS);
        this.controller = controller;
        this.direction = new Vector2D(0, -1);
    }

    public void makeBullet() {
        // Calculate the tip of the ship in the ship's local coordinate system
        Vector2D shipTipLocal = new Vector2D(0, -RADIUS); // Nose of the ship in local coordinates

        // Transform the tip's position to the world coordinate system
        AffineTransform transform = new AffineTransform();
        transform.translate(position.x, position.y); // Translate to ship's position
        transform.rotate(direction.angle() + Math.PI / 2); // Rotate by ship's direction
        transform.scale(DRAWING_SCALE, DRAWING_SCALE); // Apply scaling

        // Apply the transformation to the ship's tip
        double[] tipWorld = new double[2];
        transform.transform(shipTipLocal.toArray(), 0, tipWorld, 0, 1);

        // Calculate bullet velocity based on the ship's direction
        Vector2D bulletVelocity = new Vector2D(direction).mult(BULLET_SPEED);

        // Create the bullet at the transformed tip position with the calculated velocity

        bullet = new Bullet(this,tipWorld[0], tipWorld[1], bulletVelocity.x, bulletVelocity.y);
        System.out.println("Bullet created at: (" + tipWorld[0] + ", " + tipWorld[1] + ")");
        System.out.println("Bullet velocity: (" + bulletVelocity.x + ", " + bulletVelocity.y + ")");
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive() {
        SoundManager.playPlayerDeadSound();
        isAlive = false;
    }

    @Override
    public void update() {
        // System.out.println("Ship update: velocity = " + velocity);

        Action action = controller.action();
        direction.rotate(action.turn * STEER_RATE * DT);
        velocity.addScaled(direction, action.thrust * MAG_ACCELERATION * DT);
        velocity.mult(1 - DRAG * DT);
        super.position.addScaled(velocity, DT);
        super.position.wrap(FRAME_WIDTH, FRAME_HEIGHT);

        lastTimeBulletFired += DT;
        if (action.shoot && lastTimeBulletFired > SHOOT_DELAY) {
            makeBullet();
            SoundManager.playShootingSound();
            lastTimeBulletFired = 0;
        }

        if(bullet != null && bullet.isAlive()){
            bullet.update();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        int[] XP = new int[3];
        int[] YP = new int[3];

        // Nose of the ship
        XP[0] = 0;
        YP[0] = -RADIUS;


        // Left wing
        XP[1] = -RADIUS;
        YP[1] = RADIUS;

        // Right wing
        XP[2] = RADIUS;
        YP[2] = RADIUS;

        // Ship coordinates remain the same
        XP[0] = 0;
        YP[0] = -RADIUS;
        XP[1] = -RADIUS;
        YP[1] = RADIUS;
        XP[2] = RADIUS;
        YP[2] = RADIUS;

        // Thrust coordinates
        int[] XPTHRUST = new int[3];
        int[] YPTHRUST = new int[3];

        // Center point of thrust
        XPTHRUST[0] = 0;
        YPTHRUST[0] = RADIUS + 5;

        // Left point of thrust
        XPTHRUST[1] = -RADIUS / 2;
        YPTHRUST[1] = RADIUS;

        // Right point of thrust
        XPTHRUST[2] = RADIUS / 2;
        YPTHRUST[2] = RADIUS;


        AffineTransform at = g.getTransform(); // save current state of transformation matrix
        g.translate(super.position.x, super.position.y); // translate coordinate system to ship's position (centerr of ship))
        double rotation = direction.angle() + Math.PI / 2;
        g.rotate(rotation);
        g.scale(DRAWING_SCALE, DRAWING_SCALE);
        g.setColor(COLOR);
//        g.drawLine(0,-RADIUS, 5,-RADIUS *2);

        g.drawPolygon(XP, YP, XP.length);
        g.setTransform(at); // restore transformation matrix to original state

        if (bullet != null && bullet.isAlive()) {
            bullet.draw(g);
        }
    }
}
