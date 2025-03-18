package SpaceSurvivorGame.Controllers;

import SpaceSurvivorGame.Enemy.EnemyShip;
import SpaceSurvivorGame.Player.PLayerShip;
import SpaceSurvivorGame.Utilities.Vector2D;

/**
 * Control the enemy ship behavior through simple AI logic that engages with a player ship.
 */
public class AIController implements Controller {
    private Action action;
    private PLayerShip playerRef;
    private EnemyShip enemyShip;

    public AIController(PLayerShip player, EnemyShip enemyShip) {
        this.playerRef = player;
        this.enemyShip = enemyShip;
        this.action = new Action();
    }

    /**
     * Calculate and return the next action based on the AI logic.
     * AI decides thrust, turn or shoot based on the position and angel between the player ship.
     */
    @Override
    public Action action() {

        action.turn = 0;
        action.thrust = 0;
        action.shoot = false;

        if (playerRef == null || !playerRef.isAlive()) {
            return action;
        }

        Vector2D enemyPos = getEnemyPosition();
        Vector2D playerPos = playerRef.position;
        Vector2D toPlayer = new Vector2D(playerPos).subtract(enemyPos);

        double desiredAngle = toPlayer.angle();
        double currentAngle = getEnemyDirectionAngle().angle();
        double angleDiff = normalRelativeAngle(desiredAngle - currentAngle);

        if (angleDiff > 0.05) {
            action.turn = +1; // turn clockwise
        } else if (angleDiff < -0.05) {
            action.turn = -1; // turn counterclockwise
        }

        double distance = toPlayer.mag();
        if (distance > 300) {
            action.thrust = 1; // move forward
        } else if (distance < 100) {
            action.thrust = -1; // move backward when close to player ship
        }

        if (Math.abs(angleDiff) < 0.15 && distance < 500) {
            action.shoot = true; // shoot at player when they are close with a specific accuracy
        }
        return action;
    }

    /**
     * Get the enemy position.
     *
     * @return Vector2D representing the enemy position
     */
    private Vector2D getEnemyPosition() {
        return enemyShip.position;
    }

    /**
     * Get the enemy direction angle.
     *
     * @return Vector2D representing the enemy direction angle
     */
    private Vector2D getEnemyDirectionAngle() {
        return enemyShip.direction;
    }

    /**
     * Normalize an angle to a value between -PI and PI
     *
     * @param angle in radians to normalize.
     * @return normalized angle
     */
    private double normalRelativeAngle(double angle) {
        double twoPI = 2.0 * Math.PI;
        while (angle > Math.PI) angle -= twoPI;
        while (angle <= -Math.PI) angle += twoPI;
        return angle;
    }
}
