package AsteriodGame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// Graphics Renderer
public class GameView extends JComponent {
    private static final Color BG_COLOR = Color.BLACK;
    private GameManager game; // Instance to our Game Manager

    public GameView(GameManager game) {
        this.game = game;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());

        game.ship.draw(g);
        // Get the astroids from the game manager, and render them on the view
        List<Asteriod> gameAsteroids = game.asteroids;
        for (GameObject asteroid : gameAsteroids) {
            asteroid.draw(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return Constants.FRAME_SIZE;
    }
}
