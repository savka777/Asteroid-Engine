package AsteriodGame;
import Utilities.*;
import java.awt.*;
import static AsteriodGame.Constants.*;

public class BasicShip {
    public static final int RADIUS = 8;
    public static final double STEER_RATE = 2 * Math.PI;
    public static final double MAG_ACCELERATION = 100;
    public static final double DRAG = 0.005;
    public static final Color COLOR = Color.BLUE;
    
    public Vector2D position;
    public Vector2D velocity;
    public Vector2D direction;

    public BasicController controller; 

    public BasicShip(BasicController controller) {
        this.controller = controller;
        this.position = new Vector2D(FRAME_WIDTH/2,FRAME_HEIGHT/2);
        this.velocity = new Vector2D(0,0);
        this.direction = new Vector2D(0,-1);
    }

    public void update() {
        // invoke basic controller
        Action action = controller.action();
        direction.rotate(action.turn * STEER_RATE * DT);
        velocity.addScaled(direction, action.thrust * MAG_ACCELERATION * DT);
        velocity.mult(1 - DRAG * DT);

        position.addScaled(velocity, DT);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
    }

public void draw(Graphics2D g) {
    g.setColor(COLOR);  
    
    // Calculate the points of the triangle based on position and direction
    int[] xPoints = new int[3];
    int[] yPoints = new int[3];
    
    // Nose of the ship
    xPoints[0] = (int) (position.x + direction.x * RADIUS);
    yPoints[0] = (int) (position.y + direction.y * RADIUS);
    
    // Left wing
    Vector2D left = new Vector2D(direction).rotate(2.5);
    xPoints[1] = (int) (position.x + left.x * RADIUS);
    yPoints[1] = (int) (position.y + left.y * RADIUS);
    
    // Right wing
    Vector2D right = new Vector2D(direction).rotate(-2.5);
    xPoints[2] = (int) (position.x + right.x * RADIUS);
    yPoints[2] = (int) (position.y + right.y * RADIUS);
    
    g.fillPolygon(xPoints, yPoints, 3);
}
}
