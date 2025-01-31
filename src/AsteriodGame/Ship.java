package AsteriodGame;

import Utilities.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import static AsteriodGame.Constants.*;

public class Ship extends GameObject {
    public static final int RADIUS = 8;
    public static final double STEER_RATE = 2 * Math.PI;
    public static final double MAG_ACCELERATION = 100;
    public static final double DRAG = 0.005;
    public static final Color COLOR = Color.BLUE;
    public Vector2D direction;
    public Controller controller;
    private boolean isThrusting = false;

    public Ship(Controller controller) {
        super(new Vector2D(FRAME_WIDTH / 2, FRAME_HEIGHT / 2),new Vector2D(0,0),RADIUS);
        this.controller = controller;
        this.direction = new Vector2D(0, -1);
    }

    @Override
    public void update() {
        System.out.println("Ship update: velocity = " + velocity);
        // invoke basic controller
        Action action = controller.action();
        isThrusting = action.thrust > 0;
        direction.rotate(action.turn * STEER_RATE * DT);
        velocity.addScaled(direction, action.thrust * MAG_ACCELERATION * DT);
        velocity.mult(1 - DRAG * DT);

        super.position.addScaled(velocity, DT);
        super.position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
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

        // Right point of thrust

        AffineTransform at = g.getTransform(); // save current state of transformation matrix
        g.translate(super.position.x, super.position.y); // translate coordinate system to ship's position (centerr of ship))
        double rotation = direction.angle() + Math.PI / 2;
        g.rotate(rotation);
        g.scale(DRAWING_SCALE, DRAWING_SCALE);
        g.setColor(Color.yellow);

        g.fillPolygon(XP, YP, XP.length);

        if (isThrusting) {
            g.setColor(Color.WHITE);
            g.fillPolygon(XPTHRUST, YPTHRUST, XPTHRUST.length);
        }
        g.setTransform(at); // restore transformation matrix to original state
    }
}
