package AsteriodGame;
import Utilities.*;

import java.awt.Color;

public class BasicShip {
    public static final int RADIUS = 8;
    public static final double STEER_RATE = 2 * Math.PI;
    public static final double MAG_ACCELERATION = 200;
    public static final double DRAG = 0.01;
    public static final Color COLOR = Color.BLUE;
    
    public Vector2D position;
    public Vector2D velocity;
    public Vector2D direction;


}
