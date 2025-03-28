package SpaceSurvivorGame.Config;

import java.awt.*;

/**
 * Configurations class is used to create consitant environment variables that can be changed in one location,
 * instead of having to dig through classes and adjust game play. Also, useful for the settings menu, having all the
 * variables in one place that can be easily adjusted.
 */
public class Configurations {

    ////// PLAYER SETTINGS
    public static final int PLAYER_RADIUS = 12;
    public static double PLAYER_SENSITIVITY = 3;
    public static double PLAYER_STEER_RATE = PLAYER_SENSITIVITY * Math.PI;
    public static final double PLAYER_MAG_ACCELERATION = 400;
    public static final double PLAYER_DRAG = 0.005;
    public static final Color PLAYER_COLOR = Color.white;
    public static final int PLAYER_SCORE = 0;
    public static final int N_PLAYER_LIFES = 3;


    ////// ENEMY SETTINGS
    public static final int ENEMY_RADIUS = 12;
    public static final double ENEMY_STEER_RATE = 3.5 * Math.PI;
    public static final double ENEMY_MAG_ACCELERATION = 200;
    public static final double ENEMY_DRAG = 0.0005;
    public static final double ENEMY_SHOOT_DELAY = 2.5;
    public static final Color ENEMY_COLOR = Color.RED;
    public static int N_ENEMY_SHIPS = 1;


    ////// DISPLAY SETTINGS
    public static Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int FRAME_HEIGHT = (int) SCREEN_SIZE.getHeight();
    public static final int FRAME_WIDTH = (int) SCREEN_SIZE.getWidth();
    public static final Dimension FRAME_SIZE = new Dimension(Configurations.FRAME_WIDTH, Configurations.FRAME_HEIGHT);


    ///// RENDERING SETTINGS
    public static final int DELAY = 10;
    public static final double DT = DELAY / 1000.0;
    public static final double DRAWING_SCALE = 1.0;

    ///// BULLET SETTINGS
    public static final int BULLET_SPEED = 100;
    public static final double BULLET_RADIUS = 3;
    public static final int BULLET_LIFE = 2;

    ///// ASTEROID SETTINGS
    public static final int N_INIT_ASTEROIDS = 10;

    ///// LEVEL SETTINGS
    public static final int LEVEL_START = 1;
    public static final int SCORE_FOR_LEVEL_COMPLETION = 200;


}
