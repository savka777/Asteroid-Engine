package AsteriodGame;

import Utilities.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


// Game Manager
public class GameManager {
    public static GameManager instance;
    public static final int N_INIT_ASTEROIDS = 10;
    public List<GameObject> gameObjects;
    public Controller controller;
    public Ship ship;
    private static int score = 0;
    private static int lives = 3;
    private static int level = 1;
    public static boolean isGameOver = false;
    public int totalAsteroidsInLevel;


    // Init of Game AND manage state of Game
    public GameManager() {
        instance = this;
        gameObjects = new ArrayList<GameObject>();
        for (int i = 0; i < N_INIT_ASTEROIDS; i++) {
            gameObjects.add(Asteriod.MakeRandomAsteroid());
        }

        controller = new InputManager();
        ship = new Ship(controller);
        gameObjects.add(ship);
        totalAsteroidsInLevel = N_INIT_ASTEROIDS;
    }

    public static void addGameObject(GameObject obj) {
        instance.gameObjects.add(obj);
    }

    public void newLevel() {
        level++;
        score +=200; // extra points when player finished the level
        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }
        synchronized (GameManager.class) {
            gameObjects.clear();
            int numberOfAsteroid = N_INIT_ASTEROIDS + 2 * (level -1);
            for (int i = 0; i < numberOfAsteroid + 2 * (level - 1); i++) {
                gameObjects.add(Asteriod.MakeRandomAsteroid());
            }

            totalAsteroidsInLevel = numberOfAsteroid;
            ship = new Ship(controller);
            gameObjects.add(ship);
        }
    }

    public void newLife() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }
        synchronized (GameManager.class) {
            gameObjects.clear();
            int numberOfAsteroid = N_INIT_ASTEROIDS + 2 * (level -1);
            for (int i = 0; i < N_INIT_ASTEROIDS + 2 * (level - 1); i++) {
                gameObjects.add(Asteriod.MakeRandomAsteroid());

            }
            totalAsteroidsInLevel = numberOfAsteroid;
            ship = new Ship(controller);
            gameObjects.add(ship);
        }
    }

    public void update() {
        // Process collisions between all game objects
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject o1 = gameObjects.get(i);
            for (int j = i + 1; j < gameObjects.size(); j++) {
                GameObject o2 = gameObjects.get(j);
                o1.collisionHandling(o2);
            }
        }

        // Create a list for game objects that are still alive after updates
        List<GameObject> aliveObjects = new ArrayList<>();
        boolean noAsteroids = true;
        boolean noShip = true;

        // Update each object and check its type
        for (GameObject o : gameObjects) {
            o.update();

            if (o instanceof Asteriod) {
                noAsteroids = false;
                // Asteroid class implements splitting
                // Asteriod a = (Asteriod) o;
                // if (!a.spawnedAsteroids.isEmpty()) {
                //     aliveObjects.addAll(a.spawnedAsteroids);
                //     a.spawnedAsteroids.clear();
                // }
            } else if (o instanceof Ship) {
                noShip = false;
            }

            // Add the object if it is still alive
            if (o.isAlive()) {
                aliveObjects.add(o);
            }

            // If the ship fired a bullet that is still active, add it to the list.
            // (Assumes ship.bullet is set to non-null when fired and nullified after adding.)
            if (ship.bullet != null && ship.bullet.isAlive()) {
                aliveObjects.add(ship.bullet);
                ship.bullet = null;
            }
        }

        // Synchronize updates to the shared game objects list
        synchronized (GameManager.class) {
            gameObjects.clear();
            gameObjects.addAll(aliveObjects);
        }

        // If there are no asteroids remaining, move to the next level
        if (noAsteroids) {
            newLevel();
            addLife();
        }
        // If the ship has been destroyed, remove a life and restart the level
        else if (noShip) {
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

    public static void addLife(){
        if(lives != 0){
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
        // Spawn 20 particles with random velocities and a 1-second lifetime.
        for (int i = 0; i < 20; i++) {
            double angle = Math.random() * 2 * Math.PI;
            double speed = 50 + Math.random() * 50;
            double vx = speed * Math.cos(angle);
            double vy = speed * Math.sin(angle);
            Particle p = new Particle(position.x, position.y, vx, vy, 1.0, Color.ORANGE);
            addGameObject(p);
        }
    }

    public static int getRemainingAsteroids(){
        int count = 0;
        for(GameObject obj : instance.gameObjects){
            if(obj instanceof Asteriod && obj.isAlive()){
                count++;
            }
        }
        return count;
    }

    public static int getTotalAsteroidsThisLevel() {
        return instance.totalAsteroidsInLevel;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Asteroids Menu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new GameMenu());
            frame.setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
