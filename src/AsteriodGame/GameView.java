package AsteriodGame;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.List;
import AsteriodGame.*;

import static AsteriodGame.Constants.FRAME_HEIGHT;
import static AsteriodGame.Constants.FRAME_WIDTH;

// Graphics Renderer
public class GameView extends JComponent {
    private static final Color BG_COLOR = Color.BLACK;
    private GameManager game; // Instance to our Game Manager
    private Font retroFont;

    public GameView(GameManager game) {
        this.game = game;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());

        synchronized (GameManager.class) {
            for (GameObject gameObject : game.gameObjects) {
                if(gameObject.isAlive()){
                    gameObject.draw(g);
                }
            }
            try {
                InputStream is = getClass().getResourceAsStream("/AsteriodGame/Static/PressStart2P-Regular.ttf");
                retroFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(18f);
            } catch (Exception e) {
                System.out.println("Custom font not found, using default.");
                retroFont = new Font("Arial", Font.BOLD, 8);
            }
            g.setColor(Color.WHITE);
            g.setFont(retroFont);
            g.drawString("Level: " + GameManager.getLevel(), 20, FRAME_HEIGHT - 20);
            g.drawString("Score: " + GameManager.getScore(), FRAME_WIDTH / 3 + 20, FRAME_HEIGHT - 20);
            g.drawString("Lives: " + GameManager.getLives(), 2 * FRAME_WIDTH / 3 + 20, FRAME_HEIGHT - 20);
            if (GameManager.getLives() == 0)
                g.drawString("GAME OVER Score " + GameManager.getScore(), FRAME_WIDTH / 2 - 100, FRAME_HEIGHT / 2 - 20);
        }


        }


    @Override
    public Dimension getPreferredSize() {
        return Constants.FRAME_SIZE;
    }
}
