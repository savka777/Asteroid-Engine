package AsteriodGame;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


// Game Manager
public class GameManager {
    public static final int N_INIT_ASTEROIDS = 10;
    public List<GameObject> gameObjects;
    public Controller controller;
    public Ship ship;
    private static int score = 0;
    private static int lives = 10;
    private static int level = 1;
    public static boolean isGameOver = false;


    // Init of Game AND manage state of Game
    public GameManager() {
        gameObjects = new ArrayList<GameObject>();
        for (int i = 0; i < N_INIT_ASTEROIDS; i++) {
            gameObjects.add(Asteriod.MakeRandomAsteroid());
        }

        controller = new InputManager();
        ship = new Ship(controller);
        gameObjects.add(ship);
    }

    public void newLevel() {
        level++;
        try {
            Thread.sleep(1000);
        }
        catch (Exception e) {

        }
        synchronized(GameManager.class) {
            gameObjects.clear();
            for (int i = 0; i < N_INIT_ASTEROIDS + 2 * (level-1); i++) {
                gameObjects.add(Asteriod.MakeRandomAsteroid());

            }
            ship = new Ship(controller);
            gameObjects.add(ship);
        }
    }

    public void newLife() {
        try {
            Thread.sleep(1000);
        }
        catch (Exception e) {

        }
        synchronized(GameManager.class) {
            gameObjects.clear();
            for (int i = 0; i < N_INIT_ASTEROIDS + 2 * (level-1); i++) {
                gameObjects.add(Asteriod.MakeRandomAsteroid());

            }
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

    public static void loseLife()  {
        lives--;
        if (lives==0)
            isGameOver = true;
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
