package SpaceSurvivorGame.Controllers;

import SpaceSurvivorGame.Enemy.EnemyShip;
import SpaceSurvivorGame.Player.PLayerShip;
import SpaceSurvivorGame.Utilities.Vector2D;

public class AIController implements Controller {

    private Action action;
    private PLayerShip playerRef;
    private EnemyShip enemyShip;


    public AIController(PLayerShip player, EnemyShip enemyShip) {
        this.playerRef = player;
        this.enemyShip = enemyShip;
        this.action = new Action();
    }

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
            // turn clockwise
            action.turn = +1;
        } else if (angleDiff < -0.05) {
            action.turn = -1;
        }

        double distance = toPlayer.mag();
        if (distance > 300) {
            action.thrust = 1;
        } else if (distance < 100) {
            action.thrust = -1;
        }

        if (Math.abs(angleDiff) < 0.15 && distance < 500) {
            action.shoot = true;
        }

        return action;
    }

    private Vector2D getEnemyPosition() {

        return enemyShip.position;
    }

    private Vector2D getEnemyDirectionAngle() {

        return enemyShip.direction;
    }

    private double normalRelativeAngle(double angle) {
        double twoPi = 2.0 * Math.PI;
        while (angle > Math.PI) angle -= twoPi;
        while (angle <= -Math.PI) angle += twoPi;
        return angle;
    }
}
