package SpaceSurvivorGame.Enemy;

import SpaceSurvivorGame.Controllers.Action;
import SpaceSurvivorGame.Controllers.Controller;
import SpaceSurvivorGame.GameObjects.Bullet;
import SpaceSurvivorGame.GameObjects.GameObject;
import SpaceSurvivorGame.Managers.SoundManager;
import SpaceSurvivorGame.Utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static SpaceSurvivorGame.Config.Configurations.*;

public class EnemyShip extends GameObject {
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


    public EnemyShip(Controller controller, double startX, double startY) {
        super(new Vector2D(startX, startY), new Vector2D(0, 0), RADIUS);
        this.controller = controller;
        this.direction = new Vector2D(0, -1);
    }

    public void makeBullet() {
        Vector2D shipTipLocal = new Vector2D(0, -RADIUS);

        AffineTransform transform = new AffineTransform();
        transform.translate(position.x, position.y);
        transform.rotate(direction.angle() + Math.PI / 2);
        transform.scale(DRAWING_SCALE, DRAWING_SCALE);

        double[] tipWorld = new double[2];
        transform.transform(shipTipLocal.toArray(), 0, tipWorld, 0, 1);

        Vector2D bulletVelocity = new Vector2D(direction).mult(BULLET_SPEED);


        bullet = new Bullet(this, tipWorld[0], tipWorld[1], bulletVelocity.x, bulletVelocity.y);
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

        if (bullet != null && bullet.isAlive()) {
            bullet.update();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        int[] XP = new int[3];
        int[] YP = new int[3];

        XP[0] = 0;
        YP[0] = -RADIUS;


        XP[1] = -RADIUS;
        YP[1] = RADIUS;

        XP[2] = RADIUS;
        YP[2] = RADIUS;

        XP[0] = 0;
        YP[0] = -RADIUS;
        XP[1] = -RADIUS;
        YP[1] = RADIUS;
        XP[2] = RADIUS;
        YP[2] = RADIUS;

        int[] XPTHRUST = new int[3];
        int[] YPTHRUST = new int[3];

        XPTHRUST[0] = 0;
        YPTHRUST[0] = RADIUS + 5;

        XPTHRUST[1] = -RADIUS / 2;
        YPTHRUST[1] = RADIUS;

        XPTHRUST[2] = RADIUS / 2;
        YPTHRUST[2] = RADIUS;


        AffineTransform at = g.getTransform();
        g.translate(super.position.x, super.position.y);
        double rotation = direction.angle() + Math.PI / 2;
        g.rotate(rotation);
        g.scale(DRAWING_SCALE, DRAWING_SCALE);
        g.setColor(COLOR);


        g.drawPolygon(XP, YP, XP.length);
        g.setTransform(at);

        if (bullet != null && bullet.isAlive()) {
            bullet.draw(g);
        }
    }
}
