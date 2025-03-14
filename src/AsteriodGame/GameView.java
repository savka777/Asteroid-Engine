package AsteriodGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import static AsteriodGame.Settings.*;

public class GameView extends JComponent {

    private static final Color BG_COLOR = Color.BLACK;
    private GameManager game;
    private Font retroFont;

    private JButton pauseButton;
    private boolean paused = false;
    private SettingsPanel settingsPanel;

    public GameView(GameManager game) {
        this.game = game;
        setLayout(null);

        try {
            InputStream is = getClass().getResourceAsStream("/AsteriodGame/Static/PressStart2P-Regular.ttf");
            retroFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(18f);
        } catch(Exception e) {
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
        add(pauseButton);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                positionPauseButton();
                if (settingsPanel != null) {
                    settingsPanel.setBounds(getWidth()/2 - 150, getHeight()/2 - 100, 300, 200);
                }
            }
        });
    }

    private void positionPauseButton() {
        int btnWidth = 100;
        int btnHeight = 40;
        int margin = 20;
        pauseButton.setBounds(getWidth() - btnWidth - margin, margin, btnWidth, btnHeight);
    }

    private void togglePause() {
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
        if (settingsPanel == null) {
            settingsPanel = new SettingsPanel();
        }
        settingsPanel.setBounds(getWidth()/2 - 150, getHeight()/2 - 100, 300, 200);
        add(settingsPanel);
        settingsPanel.setVisible(true);
        repaint();
    }

    private void hideSettingsPanel() {
        if (settingsPanel != null) {
            remove(settingsPanel);
            settingsPanel.setVisible(false);
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

        synchronized(GameManager.class) {
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
            g.drawString(scoreStr, (screenWidth - scoreWidth)/2, 40);

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
            int barX = (screenWidth - barWidth)/2;
            int barY = screenHeight - 40;
            int totalAsteroids = GameManager.getTotalAsteroidsThisLevel();
            int remainingAsteroids = GameManager.getRemainingAsteroids();
            float progress = totalAsteroids > 0 ? ((float) remainingAsteroids/ totalAsteroids) : 0;

            g.setColor(Color.YELLOW);
            g.fillRect(barX, barY, barWidth, barHeight);
            g.setColor(Color.BLACK);
            g.fillRect(barX, barY, (int)(barWidth * progress), barHeight);
            g.setColor(Color.YELLOW);
            g.drawRect(barX, barY, barWidth, barHeight);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return Settings.FRAME_SIZE;
    }

    // Inner class for the settings (pause) panel.
    private class SettingsPanel extends JPanel {
        public SettingsPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(new Color(0,0,0,200)); // semi-transparent black.

            JLabel settingsLabel = new JLabel("SETTINGS");
            settingsLabel.setFont(retroFont.deriveFont(Font.BOLD, 32f));
            settingsLabel.setForeground(Color.YELLOW);
            settingsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton resumeButton = new JButton("Resume");
            resumeButton.setFont(retroFont.deriveFont(Font.BOLD, 24f));
            resumeButton.setForeground(Color.YELLOW);
            resumeButton.setBackground(Color.BLACK);
            resumeButton.setFocusPainted(false);
            resumeButton.setContentAreaFilled(false);
            resumeButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.YELLOW, 2),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            resumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            resumeButton.addActionListener(e -> togglePause());

            add(Box.createVerticalGlue());
            add(settingsLabel);
            add(Box.createRigidArea(new Dimension(0,20)));
            add(resumeButton);
            add(Box.createVerticalGlue());
        }
    }
}
