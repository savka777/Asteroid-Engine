package AsteriodGame;

import javax.swing.*;
import Utilities.JEasyFrame;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GameMenu extends JComponent {

    private JButton startButton;
    private JButton exitButton;
    Image img = Toolkit.getDefaultToolkit().createImage("./src/AsteriodGame/Static/menuPhoto.jpg");

    // Custom retro font variable
    private Font retroFont;

    // List of asteroids for background animation
    private List<Asteriod> backgroundAsteroids;
    // Timer to update the background animation
    private Timer backgroundTimer;

    public GameMenu() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Load the custom retro font
        try {
            // Use an absolute path starting with a slash (assuming the font file is in the classpath)
            InputStream is = getClass().getResourceAsStream("/AsteriodGame/Static/PressStart2P-Regular.ttf");
            if (is == null) {
                System.out.println("Custom font not found, using default.");
                retroFont = new Font("Arial", Font.BOLD, 36);
            } else {
                retroFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(36f);
            }
        } catch (Exception e) {
            System.out.println("Error loading custom font, using default.");
            retroFont = new Font("Arial", Font.BOLD, 36);
        }

        // Create title label with retro style
        JLabel titleLabel = new JLabel("ASTEROIDS");
        titleLabel.setFont(retroFont);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create buttons and style them
        startButton = new JButton("Start Game");
        styleButton(startButton);
        startButton.addActionListener(e -> startGame());

        exitButton = new JButton("Exit");
        styleButton(exitButton);
        exitButton.addActionListener(e -> System.exit(0));

        // Arrange components with spacing
        add(Box.createVerticalGlue());
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 50)));
        add(startButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(exitButton);
        add(Box.createVerticalGlue());

        // Initialize background asteroids
        backgroundAsteroids = new ArrayList<>();
        // Create a few asteroids for the background (adjust quantity as needed)
        for (int i = 0; i < 15; i++) {
            backgroundAsteroids.add(Asteriod.MakeRandomAsteroid());
        }

        // Start a Swing Timer to update and repaint the background asteroids
        backgroundTimer = new Timer(33, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBackgroundAsteroids();
                repaint();
            }
        });
        backgroundTimer.start();
    }

    // Update positions of background asteroids
    private void updateBackgroundAsteroids() {
        for (Asteriod a : backgroundAsteroids) {
            a.update();
            a.position.wrap(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        }
    }

    // Style a button with the retro look
    private void styleButton(JButton button) {
        button.setFont(retroFont.deriveFont(Font.PLAIN, 24f));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setContentAreaFilled(true);
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                button.setContentAreaFilled(false);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw black background for the retro look
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw background asteroids
        Graphics2D g2d = (Graphics2D) g;
        for (Asteriod a : backgroundAsteroids) {
            a.draw(g2d);
        }
    }

    // Start the game, stopping the background animation first
    private void startGame() {
        backgroundTimer.stop();  // Stop background animation
        try {
            GameManager game = new GameManager();
            GameView view = new GameView(game);
            new JEasyFrame(view, "game").addKeyListener((KeyListener) game.controller);

            // Start game loop in a separate thread
            new Thread(() -> {
                try {
                    while (true) {
                        if (!GameManager.isGameOver) {
                            game.update();
                        }
                        view.repaint();
                        Thread.sleep(Constants.DELAY);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            // Close the menu window
            SwingUtilities.getWindowAncestor(this).dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Asteroids Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GameMenu());
        frame.setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
