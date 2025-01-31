package AsteriodGame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// Game Manager
public class GameManager {
    public static final int N_INIT_ASTEROIDS = 10;
    public List<Asteriod> asteroids; // Asteroids that will spawn on screen
    public Controller controller;
    public Ship ship;

    // Init of Game
    public GameManager() {
        asteroids = new ArrayList<Asteriod>();
        for (int i = 0; i < N_INIT_ASTEROIDS; i++) {
            asteroids.add(Asteriod.MakeRandomAsteroid());
        }
        controller = new InputManager();
        ship = new Ship(controller);
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
        for (Asteriod asteroid: asteroids) {
            asteroid.update();
        }
        ship.update();
    }
}
