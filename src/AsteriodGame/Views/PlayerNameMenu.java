package AsteriodGame.Views;

import AsteriodGame.Managers.GameManager;
import AsteriodGame.Player.LimitedDocument;
import AsteriodGame.Managers.HighScoreManager;
import AsteriodGame.Config.Configurations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.io.InputStream;

public class PlayerNameMenu extends JPanel {
    private JTextField nameField;
    private JButton okButton;
    private HighScoreManager highScoreManager;
    private Font retroFont;

    public PlayerNameMenu(HighScoreManager highScoreManager) {
        this.highScoreManager = highScoreManager;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);

        // Load retro font.
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

        JLabel prompt = new JLabel("Enter your 3-letter name:");
        prompt.setFont(retroFont.deriveFont(Font.PLAIN, 36f));
        prompt.setForeground(Color.YELLOW);
        prompt.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameField = new JTextField(3);
        nameField.setMaximumSize(new Dimension(200, 50));
        nameField.setFont(retroFont.deriveFont(Font.BOLD, 36f));
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setForeground(Color.YELLOW);
        nameField.setBackground(Color.BLACK);
        nameField.setCaretColor(Color.YELLOW);
        nameField.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        nameField.setDocument(new LimitedDocument(3));

        okButton = new JButton("OK");
        okButton.setFont(retroFont.deriveFont(Font.BOLD, 24f));
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        styleButton(okButton);
        okButton.addActionListener(e -> {
            String name = nameField.getText().toUpperCase();
            if (name.length() != 3) {
                JOptionPane.showMessageDialog(this, "Name must be 3 letters");
                return;
            }
            GameManager.playerName = name;

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

            // Start the game loop in a new thread.
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

                                // Create a new full-screen frame for the game over menu
                                GraphicsDevice gd = GraphicsEnvironment
                                        .getLocalGraphicsEnvironment()
                                        .getDefaultScreenDevice();

                                JFrame gameOverFrame = new JFrame("Game Over");
                                gameOverFrame.setUndecorated(true); // Remove window decorations
                                gameOverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                gameOverFrame.setResizable(false);
                                gameOverFrame.add(new GameOverMenu(finalScore));
                                gd.setFullScreenWindow(gameOverFrame); // Set to full screen
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
        add(nameField);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(okButton);
        add(Box.createVerticalGlue());
    }

    private void styleButton(JButton button) {
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