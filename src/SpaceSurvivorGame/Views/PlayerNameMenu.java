package SpaceSurvivorGame.Views;

import SpaceSurvivorGame.Config.Configurations;
import SpaceSurvivorGame.HighScore.FormatDocument;
import SpaceSurvivorGame.Managers.GameManager;
import SpaceSurvivorGame.Managers.HighScoreManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.io.InputStream;

/**
 * Player menu class represents the player name entry menu before the game starts.
 * Allows the player to enter their name and select a difficulty mode before starting the game.
 */
public class PlayerNameMenu extends JPanel {
    private JTextField nameField;
    private JButton okButton;
    private HighScoreManager highScoreManager;
    private Font retroFont;
    private JLabel errorLabel;
    private JButton easyModeButton;
    private JButton hardModeButton;
    private String selectedDifficulty = "Easy";

    /**
     * Constructs the PlayerNameMenu, initializing UI components and difficulty selection.
     *
     * @param highScoreManager The manager for handling high scores.
     */
    public PlayerNameMenu(HighScoreManager highScoreManager) {
        this.highScoreManager = highScoreManager;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);

        try {
            InputStream is = getClass().getResourceAsStream("/SpaceSurvivorGame/Static/PressStart2P-Regular.ttf");
            if (is == null) {
                retroFont = new Font("Arial", Font.BOLD, 36);
            } else {
                retroFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(36f);
            }
        } catch (Exception e) {
            retroFont = new Font("Arial", Font.BOLD, 36);
        }

        // name prompt
        JLabel prompt = new JLabel("Enter your 3 - letter name:");
        prompt.setFont(retroFont.deriveFont(Font.PLAIN, 42f));
        prompt.setForeground(Color.YELLOW);
        prompt.setAlignmentX(Component.CENTER_ALIGNMENT);

        // name field
        nameField = new JTextField(3);
        nameField.setMaximumSize(new Dimension(200, 50));
        nameField.setFont(retroFont.deriveFont(Font.BOLD, 36f));
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setForeground(Color.YELLOW);
        nameField.setBackground(Color.BLACK);
        nameField.setCaretColor(Color.YELLOW);
        nameField.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        nameField.setDocument(new FormatDocument(3));

        // error label
        errorLabel = new JLabel("");
        errorLabel.setFont(retroFont.deriveFont(Font.PLAIN, 24f));
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setVisible(false);

        // buttons
        easyModeButton = createDifficultyBtn("EASY MODE");
        hardModeButton = createDifficultyBtn("HARD MODE");

        // button descriptions
        JLabel easyModeDescription = createDescriptionLabel("Extra lives on level completion");
        JLabel hardModeDescription = createDescriptionLabel("Only 3 lives, no extra lives");

        easyModeButton.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        hardModeButton.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 1));

        // manage button events
        easyModeButton.addActionListener(e -> {
            selectedDifficulty = "Easy";
            easyModeButton.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
            hardModeButton.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 1));
        });

        hardModeButton.addActionListener(e -> {
            selectedDifficulty = "Hard";
            hardModeButton.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
            easyModeButton.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 1));
        });

        okButton = new JButton("OK");
        okButton.setFont(retroFont.deriveFont(Font.BOLD, 24f));
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        styleButton(okButton);
        okButton.addActionListener(e -> {
            String name = nameField.getText().toUpperCase();
            if (name.length() != 3) {
                errorLabel.setText("Name must be 3 letters!");
                errorLabel.setVisible(true);
                new javax.swing.Timer(3000, evt -> errorLabel.setVisible(false)).start();
                return;
            }
            GameManager.playerName = name;
            GameManager.difficultyMode = selectedDifficulty;

            GameManager game = new GameManager();
            GameView gameView = new GameView(game);
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.getContentPane().removeAll();
            frame.add(gameView);
            frame.revalidate();
            frame.repaint();

            gameView.addKeyListener((KeyListener) game.controller);
            gameView.setFocusable(true);
            gameView.requestFocusInWindow();
            new Thread(() -> {
                try {
                    while (true) {
                        if (!GameManager.isGameOver) {
                            game.update();
                        } else {
                            int finalScore = GameManager.getScore();
                            SwingUtilities.invokeLater(() -> {
                                JFrame oldFrame = (JFrame) SwingUtilities.getWindowAncestor(gameView);
                                if (oldFrame != null) {
                                    oldFrame.dispose();
                                }

                                GraphicsDevice gd = GraphicsEnvironment
                                        .getLocalGraphicsEnvironment()
                                        .getDefaultScreenDevice();

                                JFrame gameOverFrame = new JFrame("Game Over");
                                gameOverFrame.setUndecorated(true);
                                gameOverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                gameOverFrame.setResizable(false);
                                gameOverFrame.add(new GameOverMenu(finalScore));
                                gd.setFullScreenWindow(gameOverFrame);
                            });
                            break;
                        }
                        gameView.repaint();
                        Thread.sleep(Configurations.DELAY);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();
        });

        add(Box.createVerticalGlue());
        add(prompt);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(errorLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(nameField);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(easyModeButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(easyModeDescription);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(hardModeButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(hardModeDescription);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(okButton);
        add(Box.createVerticalGlue());
    }

    /**
     * Helper, creates a button for difficulty
     *
     * @return a button for difficulty
     */
    private JButton createDifficultyBtn(String buttonText) {
        JButton button = new JButton(buttonText);
        styleButton(button);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    /**
     * Helper, creates a label for difficulty
     *
     * @return a label for difficulty
     */
    private JLabel createDescriptionLabel(String descriptionText) {
        JLabel description = new JLabel(descriptionText);
        description.setFont(retroFont.deriveFont(Font.PLAIN, 16f));
        description.setForeground(Color.YELLOW);
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        return description;
    }

    /**
     * Style a given button.
     *
     * @param button to be styled.
     */
    private void styleButton(JButton button) {
        button.setFont(retroFont.deriveFont(Font.BOLD, 24f));
        button.setForeground(Color.YELLOW);
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.YELLOW, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    }
}