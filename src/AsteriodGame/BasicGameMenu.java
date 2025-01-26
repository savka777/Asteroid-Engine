package AsteriodGame;
import javax.swing.*;
import Utilities.JEasyFrame;
import java.awt.*;
import java.awt.event.*;
public class BasicGameMenu extends JComponent {
    private BasicGame game;
    private boolean isMenuActive = true;
    private JButton startButton;
    private JButton exitButton;

    public BasicGameMenu() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Create title label
        JLabel titleLabel = new JLabel("Asteroids");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create buttons
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.PLAIN, 24));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(e -> startGame());

        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 24));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(e -> System.exit(0));

        // Add components with spacing
        add(Box.createVerticalGlue());
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 50)));
        add(startButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(exitButton);
        add(Box.createVerticalGlue());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void startGame() {
        try {
            // Create and start game
            BasicGame game = new BasicGame();
            BasicView view = new BasicView(game);
            new JEasyFrame(view, "game").addKeyListener( (KeyListener)game.controller);
        
            // Start game loop in separate thread
            new Thread(() -> {
                try {
                    while(true) {
                        game.update();
                        view.repaint();
                        Thread.sleep(Constants.DELAY);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            // Close menu window
            SwingUtilities.getWindowAncestor(this).dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Asteroids Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new BasicGameMenu());
        frame.setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
