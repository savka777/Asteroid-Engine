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
    private Font retroFont;
    private List<Asteriod> backgroundAsteroids;
    private Timer backgroundTimer;

    public GameMenu() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

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

        JLabel titleLabel = new JLabel("ASTEROIDS");
        titleLabel.setFont(retroFont);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton = new JButton("Start");
        styleButton(startButton);
        startButton.addActionListener(e -> startGame());

        exitButton = new JButton("Exit");
        styleButton(exitButton);
        exitButton.addActionListener(e -> System.exit(0));

        add(Box.createVerticalGlue());
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 50)));
        add(startButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(exitButton);
        add(Box.createVerticalGlue());

        // Init background asteroids
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
            a.position.wrap(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        }
    }

    private void styleButton(JButton button) {
        button.setFont(retroFont.deriveFont(Font.PLAIN, 24f));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
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

        Graphics2D g2d = (Graphics2D) g;
        for (Asteriod a : backgroundAsteroids) {
            a.draw(g2d);
        }
    }

    private void startGame() {
        backgroundTimer.stop();
        try {
            GameManager game = new GameManager();
            GameView view = new GameView(game);
            new JEasyFrame(view, "game").addKeyListener((KeyListener) game.controller);

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
