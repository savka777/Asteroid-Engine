package SpaceSurvivorGame.Views;

import SpaceSurvivorGame.Config.Configurations;
import SpaceSurvivorGame.GameObjects.Asteroid;
import SpaceSurvivorGame.HighScore.HighScore;
import SpaceSurvivorGame.Managers.HighScoreManager;
import SpaceSurvivorGame.Managers.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Game Menu class represents the intro game menu that user sees.
 * Display's title, high scores, start, exit buttons and animated background.
 */
public class GameMenu extends JComponent {
    private JButton startButton;
    private JButton exitButton;
    private Font retroFont;
    private List<Asteroid> backgroundAsteroids;
    private Timer backgroundTimer;
    private HighScoreManager highScoreManager;

    /**
     * Constructs the game menu, initializing UI and background animation.
     */
    public GameMenu() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        try {
            InputStream is = getClass().getResourceAsStream("/SpaceSurvivorGame/Static/PressStart2P-Regular.ttf");
            if (is == null) {
                retroFont = new Font("Arial", Font.BOLD, 54);
            } else {
                retroFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(54f);
            }
        } catch (Exception e) {
            retroFont = new Font("Arial", Font.BOLD, 54);
        }

        // title
        JLabel titleLabel = new JLabel("SPACE SURVIVOR");
        titleLabel.setFont(retroFont);
        titleLabel.setForeground(Color.RED);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // high score
        highScoreManager = new HighScoreManager();
        JPanel highScoresPanel = new JPanel();
        highScoresPanel.setLayout(new BoxLayout(highScoresPanel, BoxLayout.Y_AXIS));
        highScoresPanel.setOpaque(false);
        for (HighScore hs : highScoreManager.getHighScores()) {
            JLabel hsLabel = new JLabel(hs.toString());
            hsLabel.setFont(retroFont.deriveFont(Font.PLAIN, 32f));
            hsLabel.setForeground(Color.YELLOW);
            hsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            highScoresPanel.add(hsLabel);
        }

        // buttons
        startButton = new JButton("PLAY GAME");
        styleButton(startButton);
        startButton.addActionListener(e -> startGame());
        exitButton = new JButton("Exit");
        styleButton(exitButton);
        exitButton.addActionListener(e -> System.exit(0));

        add(Box.createVerticalGlue());
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(highScoresPanel);
        add(Box.createRigidArea(new Dimension(0, 50)));
        add(startButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(exitButton);
        add(Box.createVerticalGlue());

        // background animations
        backgroundAsteroids = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            backgroundAsteroids.add(Asteroid.MakeRandomAsteroid());
        }
        backgroundTimer = new Timer(33, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBackgroundAsteroids();
                repaint();
            }
        });
        backgroundTimer.start();
    }

    /**
     * Updates the background asteroids.
     */
    private void updateBackgroundAsteroids() {
        for (Asteroid a : backgroundAsteroids) {
            a.update();
            a.position.wrap(Configurations.FRAME_WIDTH, Configurations.FRAME_HEIGHT);
        }
    }

    /**
     * Styles a button with custom font and color settings.
     *
     * @param button The button to style.
     */
    private void styleButton(JButton button) {
        button.setFont(retroFont.deriveFont(Font.PLAIN, 32f));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setForeground(Color.YELLOW);
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        Graphics2D g2d = (Graphics2D) g;
        for (Asteroid a : backgroundAsteroids) {
            a.draw(g2d);
        }
    }

    private void startGame() {
        SoundManager.startMainMusic();
        backgroundTimer.stop();
        PlayerNameMenu nameEntry = new PlayerNameMenu(highScoreManager);
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().removeAll();
        frame.add(nameEntry);
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return Configurations.FRAME_SIZE;
    }
}