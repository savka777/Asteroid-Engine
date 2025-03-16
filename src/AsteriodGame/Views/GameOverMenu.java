package AsteriodGame.Views;

import AsteriodGame.Managers.GameManager;
import AsteriodGame.Managers.HighScoreManager;
import AsteriodGame.HighScore.HighScore;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class GameOverMenu extends JComponent {

    private JButton playAgainButton;
    private JButton quitButton;
    private static Font retroFont;
    private int finalScore;

    public GameOverMenu(int finalScore) {
        this.finalScore = finalScore;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);

        try {
            InputStream is = getClass().getResourceAsStream("/AsteriodGame/Static/PressStart2P-Regular.ttf");
            if (is == null) {
                retroFont = new Font("Arial", Font.BOLD, 36);
            } else {
                retroFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(36f);
            }
        } catch (Exception e) {
            retroFont = new Font("Arial", Font.BOLD, 36);
        }

        // Title label with neon-style text.
        JLabel titleLabel = new JLabel("GAME OVER");
        titleLabel.setFont(retroFont);
        titleLabel.setForeground(Color.RED);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Score label.
        JLabel scoreLabel = new JLabel("Score: " + finalScore);
        scoreLabel.setFont(retroFont.deriveFont(Font.PLAIN, 24f));
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Process high scores.
        HighScoreManager scoreManager = new HighScoreManager();
        if (scoreManager.isNewHighScore(finalScore)) {
            scoreManager.addHighScore(new HighScore(GameManager.playerName, finalScore));
        }
        JPanel highScoresPanel = new JPanel();
        highScoresPanel.setLayout(new BoxLayout(highScoresPanel, BoxLayout.Y_AXIS));
        highScoresPanel.setOpaque(false);
        highScoresPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        for (HighScore hs : scoreManager.getHighScores()) {
            JLabel hsLabel = new JLabel(hs.toString());
            hsLabel.setFont(retroFont.deriveFont(Font.PLAIN, 24f));
            hsLabel.setForeground(Color.YELLOW);
            hsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            highScoresPanel.add(hsLabel);
        }


        playAgainButton = new JButton("Play Again");
        styleButton(playAgainButton);
        playAgainButton.addActionListener(e -> onPlayAgain());

        quitButton = new JButton("Quit");
        styleButton(quitButton);
        quitButton.addActionListener(e -> System.exit(0));

        add(Box.createVerticalGlue());
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(scoreLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(highScoresPanel);
        add(Box.createRigidArea(new Dimension(0, 50)));
        add(playAgainButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(quitButton);
        add(Box.createVerticalGlue());
    }

    public static void styleButton(JButton button) {
        button.setFont(retroFont.deriveFont(Font.BOLD, 24f));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setForeground(Color.YELLOW);
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.YELLOW, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    }

    private void onPlayAgain() {
        GameManager.resetGameState();
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (topFrame != null) {
            topFrame.dispose();
        }
        GraphicsDevice gd = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();

        JFrame frame = new JFrame("Asteroids Menu");
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new GameMenu());
        gd.setFullScreenWindow(frame);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
