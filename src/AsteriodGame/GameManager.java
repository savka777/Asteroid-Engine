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

    // This one has the Game Menu
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

    public void update() {
        List<GameObject> alivGameObjects = new ArrayList<>();

        for(GameObject obj : gameObjects) {
            obj.update();
            if(obj.isAlive()) {
                alivGameObjects.add(obj);
            }
        }

        if(ship.bullet != null && ship.bullet.isAlive()) {
            System.out.println("Bullet added");
            alivGameObjects.add(ship.bullet);
            ship.bullet = null;
        }

        gameObjects = alivGameObjects;
        // Update the game objects
        ship.update();
    }
}
