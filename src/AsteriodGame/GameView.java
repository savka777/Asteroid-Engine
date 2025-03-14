package AsteriodGame;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

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
            // Draw game objects
            for (GameObject gameObject : game.gameObjects) {
                if (gameObject.isAlive()) {
                    gameObject.draw(g);
                }
            }

            // Load retro font
            try {
                InputStream is = getClass().getResourceAsStream("/AsteriodGame/Static/PressStart2P-Regular.ttf");
                retroFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(18f);
            } catch (Exception e) {
                System.out.println("Custom font not found, using default.");
                retroFont = new Font("Arial", Font.BOLD, 18);
            }
            g.setFont(retroFont);
            FontMetrics fm = g.getFontMetrics();

            int screenWidth = getWidth();
            int screenHeight = getHeight();

            // Draw Score in the top center
            String scoreStr = "Score: " + GameManager.getScore();
            int scoreWidth = fm.stringWidth(scoreStr);
            g.setColor(Color.WHITE);
            g.drawString(scoreStr, 0, 40);

            // Bottom left: Level text
            String levelStr = "Level: " + GameManager.getLevel();
            g.drawString(levelStr, 20, screenHeight- 20);

            // Bottom right: Lives text
            String livesStr = "Lives: " + GameManager.getLives();
            int livesWidth = fm.stringWidth(livesStr);
            g.drawString(livesStr, screenWidth - livesWidth - 20, screenHeight - 20);

            // Bottom center: Progress Bar
            int barWidth = 200;
            int barHeight = 20;
            int barX = (screenWidth - barWidth) / 2;
            int barY = screenHeight - 40;

            int totalAsteroids = GameManager.getTotalAsteroidsThisLevel();
            int remainingAsteroids = GameManager.getRemainingAsteroids();
            float progress = totalAsteroids > 0 ? ((float) remainingAsteroids / totalAsteroids) : 0;

            g.setColor(Color.WHITE);
            g.fillRect(barX, barY, barWidth, barHeight);

            g.setColor(Color.BLACK);
            g.fillRect(barX, barY, (int) (barWidth * progress), barHeight);

            g.setColor(Color.WHITE);
            g.drawRect(barX, barY, barWidth, barHeight);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return Settings.FRAME_SIZE;
    }
}
