package AsteriodGame;

import Utilities.Vector2D;

public class AIController implements Controller {

    private Action action;
    private PLayerShip playerRef;
    private EnemyShip enemyShip;


    // Example: store a reference to the player ship so we can aim at it
    public AIController(PLayerShip player,EnemyShip enemyShip) {
        this.playerRef = player;
        this.enemyShip = enemyShip;
        this.action = new Action();
    }

    @Override
    public Action action() {
        // Each frame, figure out how to adjust turn/thrust to move toward the player
        // Then decide if we want to shoot.

        // Reset each frame
        action.turn = 0;
        action.thrust = 0;
        action.shoot = false;

        if (playerRef == null || !playerRef.isAlive()) {
            // If there’s no valid player, do nothing
            return action;
        }

        // 1) Find direction from enemy to player
        Vector2D enemyPos = getEnemyPosition();
        Vector2D playerPos = playerRef.position;
        Vector2D toPlayer = new Vector2D(playerPos).subtract(enemyPos);

        // 2) Compute the angle difference between your heading and the player
        //    For a simpler approach, we can just “turn” left or right to reduce angle to zero
        double desiredAngle = toPlayer.angle();
        double currentAngle = getEnemyDirectionAngle().angle();
        // angleDiff in range (-PI, +PI)
        double angleDiff = normalRelativeAngle(desiredAngle - currentAngle);

        // Decide whether to turn left or right
        if (angleDiff > 0.05) {
            // turn clockwise
            action.turn = +1;
        } else if (angleDiff < -0.05) {
            // turn counterclockwise
            action.turn = -1;
        }

        // 3) Thrust if we want to close in on the player
        double distance = toPlayer.mag();
        if (distance > 100) {
          // if we’re far from the player, thrust
            action.thrust = 1;
        } else if (distance < 80) {
            // maybe back away
             action.thrust = -1;  // optional
        }

        // 4) Decide when to shoot
        //    Here we just shoot if we’re roughly pointed at the player
        //    (the smaller the angleDiff, the more “on target” we are).
        if (Math.abs(angleDiff) < 0.15 && distance < 300) {
            action.shoot = true;
        }

        return action;
    }

    /**
     * Helper method to get the actual EnemyShip position,
     * if you store the reference differently or want to do something else.
     */
    private Vector2D getEnemyPosition() {

        return enemyShip.position;
    }

    private Vector2D getEnemyDirectionAngle() {

        return enemyShip.direction;
    }

    /**
     * Normalizes an angle to the range (-π, π).
     */
    private double normalRelativeAngle(double angle) {
        double twoPi = 2.0 * Math.PI;
        while (angle > Math.PI)  angle -= twoPi;
        while (angle <= -Math.PI) angle += twoPi;
        return angle;
    }
}
