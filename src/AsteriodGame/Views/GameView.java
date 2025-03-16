package AsteriodGame.Views;

import AsteriodGame.GameObjects.GameObject;
import AsteriodGame.Managers.GameManager;
import AsteriodGame.Config.Configurations;
import AsteriodGame.Views.SettingMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;

public class GameView extends JComponent {

    private static final Color BG_COLOR = Color.BLACK;
    private GameManager game;
    private Font retroFont;
    private JButton pauseButton;
    private boolean paused = false;
    private SettingMenu settingMenu;

    public GameView(GameManager game) {
        this.game = game;
        setLayout(new BorderLayout()); // Use BorderLayout for better layout management

        try {
            InputStream is = getClass().getResourceAsStream("/AsteriodGame/Static/PressStart2P-Regular.ttf");
            retroFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(18f);
        } catch (Exception e) {
            retroFont = new Font("Arial", Font.BOLD, 18);
        }

        pauseButton = new JButton("Pause");
        pauseButton.setFont(retroFont);
        pauseButton.setForeground(Color.YELLOW);
        pauseButton.setBackground(Color.BLACK);
        pauseButton.setFocusPainted(false);
        pauseButton.setContentAreaFilled(false);
        pauseButton.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        pauseButton.addActionListener(e -> togglePause());

        // Add pause button to the top-right corner
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        topPanel.add(pauseButton);
        add(topPanel, BorderLayout.NORTH);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                if (settingMenu != null) {
                    settingMenu.setLocation(
                            (getWidth() - settingMenu.getWidth()) / 2,
                            (getHeight() - settingMenu.getHeight()) / 2
                    );
                }
            }
        });
    }

    public void togglePause() {
        paused = !paused;
        GameManager.isPaused = paused;
        if (paused) {
            showSettingsPanel();
            pauseButton.setText("Resume");
        } else {
            hideSettingsPanel();
            pauseButton.setText("Pause");
        }
    }

    private void showSettingsPanel() {
        if (settingMenu == null) {
            settingMenu = new SettingMenu(this);
        }
        // Set the size of the settings panel
        settingMenu.setPreferredSize(new Dimension(350, 350)); // Ensure enough space for all components
        settingMenu.setSize(settingMenu.getPreferredSize());
        // Center the settings panel
        settingMenu.setLocation(
                (getWidth() - settingMenu.getWidth()) / 2,
                (getHeight() - settingMenu.getHeight()) / 2
        );
        add(settingMenu, BorderLayout.CENTER); // Add settings panel to the center
        settingMenu.setVisible(true);
        revalidate();
        repaint();
    }

    private void hideSettingsPanel() {
        if (settingMenu != null) {
            remove(settingMenu);
            settingMenu.setVisible(false);
            revalidate();
            repaint();
        }
    }

    public boolean isPaused() {
        return paused;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());

        synchronized (GameManager.class) {
            // Draw game objects only if game is not paused.
            if (!paused) {
                for (GameObject gameObject : game.gameObjects) {
                    if (gameObject.isAlive()) {
                        gameObject.draw(g);
                    }
                }
            }

            try {
                InputStream is = getClass().getResourceAsStream("/AsteriodGame/Static/PressStart2P-Regular.ttf");
                retroFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(18f);
            } catch (Exception e) {
                retroFont = new Font("Arial", Font.BOLD, 18);
            }
            g.setFont(retroFont);
            FontMetrics fm = g.getFontMetrics();
            int screenWidth = getWidth();
            int screenHeight = getHeight();

            // Draw Score in the top center.
            String scoreStr = "Score: " + GameManager.getScore();
            int scoreWidth = fm.stringWidth(scoreStr);
            g.setColor(Color.YELLOW);
            g.drawString(scoreStr, (screenWidth - scoreWidth) / 2, 40);

            // Bottom left: Level.
            String levelStr = "Level: " + GameManager.getLevel();
            g.drawString(levelStr, 20, screenHeight - 20);

            // Bottom right: Lives.
            String livesStr = "Lives: " + GameManager.getLives();
            int livesWidth = fm.stringWidth(livesStr);
            g.drawString(livesStr, screenWidth - livesWidth - 20, screenHeight - 20);

            // Bottom center: Progress Bar.
            int barWidth = 200;
            int barHeight = 20;
            int barX = (screenWidth - barWidth) / 2;
            int barY = screenHeight - 40;
            int totalAsteroids = GameManager.getTotalAsteroidsThisLevel();
            int remainingAsteroids = GameManager.getRemainingAsteroids();
            float progress = totalAsteroids > 0 ? ((float) remainingAsteroids / totalAsteroids) : 0;

            g.setColor(Color.YELLOW);
            g.fillRect(barX, barY, barWidth, barHeight);
            g.setColor(Color.BLACK);
            g.fillRect(barX, barY, (int) (barWidth * progress), barHeight);
            g.setColor(Color.YELLOW);
            g.drawRect(barX, barY, barWidth, barHeight);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return Configurations.FRAME_SIZE;
    }

    public Font getRetroFont() {
        return this.retroFont;
    }
}