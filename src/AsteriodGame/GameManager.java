package AsteriodGame;

import Utilities.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static AsteriodGame.Settings.*;

public class GameManager {
    public static GameManager instance;
    public List<GameObject> gameObjects;
    public Controller controller;
    public PLayerShip ship;
    private static int score = PLAYER_SCORE;
    private static int lives = N_PLAYER_LIFES;
    private static int level = LEVEL_START;
    public static boolean isGameOver = false;
    public int totalAsteroidsInLevel;
    public static boolean isPaused = false;

    public static String playerName = "XXX";

    public GameManager() {
        instance = this;
        gameObjects = new ArrayList<>();
        for (int i = 0; i < N_INIT_ASTEROIDS; i++) {
            gameObjects.add(Asteriod.MakeRandomAsteroid());
        }

        controller = new InputManager();
        ship = new PLayerShip(controller);
        gameObjects.add(ship);

        EnemyShip enemy = new EnemyShip(null, 100, 100);
        enemy.controller = new AIController(ship, enemy);
        gameObjects.add(enemy);

        totalAsteroidsInLevel = N_INIT_ASTEROIDS;
    }

    public static void addGameObject(GameObject obj) {
        instance.gameObjects.add(obj);
    }

    public void newLevel() {
        level++;
        score += SCORE_FOR_LEVEL_COMPLETION;
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e);
        }
        synchronized (GameManager.class) {
            gameObjects.clear();
            int numberOfAsteroid = N_INIT_ASTEROIDS + 2 * (level - 1);
            for (int i = 0; i < numberOfAsteroid + 2 * (level - 1); i++) {
                gameObjects.add(Asteriod.MakeRandomAsteroid());
            }
            ship = new PLayerShip(controller);
            gameObjects.add(ship);

            int numberOfEnemyShips = N_ENEMY_SHIPS + (level - 1);
            for (int i = 0; i < numberOfEnemyShips; i++) {
                EnemyShip enemy = new EnemyShip(null, 100, 100);
                enemy.controller = new AIController(ship, enemy);
                gameObjects.add(enemy);
            }
            totalAsteroidsInLevel = numberOfAsteroid;
        }
    }

    public void newLife() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e);
        }
        synchronized (GameManager.class) {
            gameObjects.clear();
            int numberOfAsteroid = N_INIT_ASTEROIDS + 2 * (level - 1);
            for (int i = 0; i < N_INIT_ASTEROIDS + 2 * (level - 1); i++) {
                gameObjects.add(Asteriod.MakeRandomAsteroid());
            }
            ship = new PLayerShip(controller);
            gameObjects.add(ship);

            int numberOfEnemyShips = N_ENEMY_SHIPS + (level - 1);
            for (int i = 0; i < numberOfEnemyShips; i++) {
                EnemyShip enemy = new EnemyShip(null, 100, 100);
                enemy.controller = new AIController(ship, enemy);
                gameObjects.add(enemy);
            }
            totalAsteroidsInLevel = numberOfAsteroid;
        }
    }

    public void update() {

        if(isPaused)return;

        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject o1 = gameObjects.get(i);
            for (int j = i + 1; j < gameObjects.size(); j++) {
                GameObject o2 = gameObjects.get(j);
                o1.collisionHandling(o2);
            }
        }

        List<GameObject> aliveObjects = new ArrayList<>();
        boolean noAsteroids = true;
        boolean noShip = true;

        for (GameObject o : gameObjects) {
            o.update();

            if (o instanceof Asteriod) {
                noAsteroids = false;
            } else if (o instanceof PLayerShip) {
                noShip = false;
            }

            if (o.isAlive()) {
                aliveObjects.add(o);
            }

            if (ship.bullet != null && ship.bullet.isAlive()) {
                aliveObjects.add(ship.bullet);
                ship.bullet = null;
            }

            if (o instanceof EnemyShip) {
                EnemyShip enemy = (EnemyShip) o;
                if (enemy.bullet != null && enemy.bullet.isAlive()) {
                    aliveObjects.add(enemy.bullet);
                    enemy.bullet = null;
                }
            }
        }

        synchronized (GameManager.class) {
            gameObjects.clear();
            gameObjects.addAll(aliveObjects);
        }

        if (noAsteroids) {
            newLevel();
            addLife();
        } else if (noShip) {
            newLife();
        }
    }

    public static void incScore(int inc) {
        int oldScore = score;
        score += inc;
        System.out.println("Score " + score);
        if (score / 5000 > oldScore / 5000) {
            System.out.println("Adding life");
            lives++;
        }
    }

    public static void loseLife() {
        lives--;
        if (lives == 0)
            isGameOver = true;
    }

    public static void addLife() {
        if (lives != 0) {
            lives++;
        }
    }

    public static int getScore() {
        return score;
    }

    public static int getLevel() {
        return level;
    }

    public static int getLives() {
        return lives;
    }

    public static void spawnExplosion(Vector2D position) {
        // Spawn 20 particles with random velocities.
        for (int i = 0; i < 20; i++) {
            double angle = Math.random() * 2 * Math.PI;
            double speed = 50 + Math.random() * 50;
            double vx = speed * Math.cos(angle);
            double vy = speed * Math.sin(angle);
            Particle p = new Particle(position.x, position.y, vx, vy, 1.0, Color.ORANGE);
            addGameObject(p);
        }
    }

    public static int getRemainingAsteroids() {
        int count = 0;
        for (GameObject obj : instance.gameObjects) {
            if (obj instanceof Asteriod && obj.isAlive()) {
                count++;
            }
        }
        return count;
    }

    public static void resetGameState() {
        isGameOver = false;
        lives = N_PLAYER_LIFES;
        score = PLAYER_SCORE;
        level = LEVEL_START;
    }

    public static int getTotalAsteroidsThisLevel() {
        return instance.totalAsteroidsInLevel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Asteroids Menu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new GameMenu());
            frame.setSize(Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
