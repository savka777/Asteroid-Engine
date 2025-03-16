package AsteriodGame.Views;

import AsteriodGame.GameObjects.Asteriod;
import AsteriodGame.Managers.HighScoreManager;
import AsteriodGame.Managers.SoundManager;
import AsteriodGame.HighScore.HighScore;
import AsteriodGame.Config.Configurations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GameMenu extends JComponent {

    private JButton startButton;
    private JButton exitButton;
    private Font retroFont;
    private List<Asteriod> backgroundAsteroids;
    private Timer backgroundTimer;

    private HighScoreManager highScoreManager;

    public GameMenu() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Load the retro font.
        try {
            InputStream is = getClass().getResourceAsStream("/AsteriodGame/Static/PressStart2P-Regular.ttf");
            if (is == null) {
                retroFont = new Font("Arial", Font.BOLD, 54);
            } else {
                retroFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(54f);
            }
        } catch (Exception e) {
            retroFont = new Font("Arial", Font.BOLD, 54);
        }

        // Title label.
        JLabel titleLabel = new JLabel("SPACE SURVIVOR");
        titleLabel.setFont(retroFont);
        titleLabel.setForeground(Color.RED);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

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

        // Start button.
        startButton = new JButton("PLAY GAME");
        styleButton(startButton);
        startButton.addActionListener(e -> startGame());

        // Exit button.
        exitButton = new JButton("Exit");
        styleButton(exitButton);
        exitButton.addActionListener(e -> System.exit(0));

        // Build the layout.
        add(Box.createVerticalGlue());
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(highScoresPanel);
        add(Box.createRigidArea(new Dimension(0, 50)));
        add(startButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(exitButton);
        add(Box.createVerticalGlue());

        // Initialize background asteroids.
        backgroundAsteroids = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            backgroundAsteroids.add(Asteriod.MakeRandomAsteroid());
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

    private void updateBackgroundAsteroids() {
        for (Asteriod a : backgroundAsteroids) {
            a.update();
            a.position.wrap(Configurations.FRAME_WIDTH, Configurations.FRAME_HEIGHT);
        }
    }

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
        for (Asteriod a : backgroundAsteroids) {
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