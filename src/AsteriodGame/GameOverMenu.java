package AsteriodGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;

public class GameOverMenu extends JComponent {

    private JButton playAgainButton;
    private JButton quitButton;
    private Font retroFont;
    private int finalScore;

    public GameOverMenu(int finalScore) {
        this.finalScore = finalScore;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Load or default a retro font
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

        // Title label
        JLabel titleLabel = new JLabel("GAME OVER");
        titleLabel.setFont(retroFont);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Score label
        JLabel scoreLabel = new JLabel("Score: " + finalScore);
        scoreLabel.setFont(retroFont.deriveFont(24f));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create buttons
        playAgainButton = new JButton("Play Again");
        styleButton(playAgainButton);
        playAgainButton.addActionListener(e -> onPlayAgain());

        quitButton = new JButton("Quit");
        styleButton(quitButton);
        quitButton.addActionListener(e -> System.exit(0));

        // Add components to this panel
        add(Box.createVerticalGlue());
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(scoreLabel);
        add(Box.createRigidArea(new Dimension(0, 50)));
        add(playAgainButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(quitButton);
        add(Box.createVerticalGlue());
    }

    private void styleButton(JButton button) {
        button.setFont(retroFont.deriveFont(Font.PLAIN, 24f));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
//        button.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseEntered(MouseEvent evt) {
////                button.setContentAreaFilled(true);
//            }
//
//            @Override
//            public void mouseExited(MouseEvent evt) {
////                button.setContentAreaFilled(false);
//            }
//        });
    }

    private void onPlayAgain() {
        GameManager.resetGameState();

        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (topFrame != null) {
            topFrame.dispose();
        }

        JFrame frame = new JFrame("Asteroids Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GameMenu());
        frame.setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Retro black background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
