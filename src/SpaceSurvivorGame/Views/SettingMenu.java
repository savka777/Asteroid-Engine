package SpaceSurvivorGame.Views;

import SpaceSurvivorGame.Managers.SoundManager;
import SpaceSurvivorGame.Config.Configurations;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SettingMenu extends JPanel {

    private GameView gameView;
    private JButton quitButton;
    private JButton resumeButton;
    private JCheckBox gamePlayAudioCheckBox;
    private JSlider sensitivitySlider;

    public SettingMenu(GameView gameView) {
        this.gameView = gameView;

        setPreferredSize(new Dimension(350, 500));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(0, 0, 0, 200));

        JLabel settingsLabel = new JLabel("SETTINGS");
        settingsLabel.setFont(gameView.getRetroFont().deriveFont(Font.BOLD, 54f));
        settingsLabel.setForeground(Color.RED);
        settingsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sensitivityLabel = new JLabel("Sensitivity");
        sensitivityLabel.setFont(gameView.getRetroFont().deriveFont(Font.BOLD, 24f));
        sensitivityLabel.setForeground(Color.YELLOW);
        sensitivityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        sensitivitySlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);
        sensitivitySlider.setMaximumSize(new Dimension(300, 50));
        sensitivitySlider.setBackground(Color.BLACK);
        sensitivitySlider.setForeground(Color.YELLOW);
        sensitivitySlider.setMajorTickSpacing(1);
        sensitivitySlider.setPaintTicks(true);
        sensitivitySlider.setPaintLabels(true);
        sensitivitySlider.setFont(gameView.getRetroFont().deriveFont(Font.PLAIN, 18f));
        sensitivitySlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        sensitivitySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Configurations.PLAYER_SENSITIVITY = sensitivitySlider.getValue();
                Configurations.PLAYER_STEER_RATE = Configurations.PLAYER_SENSITIVITY * Math.PI;
            }
        });

        gamePlayAudioCheckBox = new JCheckBox("Audio");
        styleCheckBox(gamePlayAudioCheckBox);
        gamePlayAudioCheckBox.setSelected(true);
        gamePlayAudioCheckBox.addActionListener(e -> {
            if (gamePlayAudioCheckBox.isSelected()) {
                SoundManager.startMainMusic();
            } else {
                SoundManager.stopMainMusic();
            }
        });

        resumeButton = new JButton("Resume");
        styleButton(resumeButton);
        resumeButton.addActionListener(e -> gameView.togglePause());

        quitButton = new JButton("Quit");
        styleButton(quitButton);
        quitButton.addActionListener(e -> System.exit(0));

        add(Box.createVerticalGlue());
        add(settingsLabel);
        add(Box.createRigidArea(new Dimension(0, 20))); // Reduced spacing
        add(sensitivityLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));  // Reduced spacing
        add(sensitivitySlider);
        add(Box.createRigidArea(new Dimension(0, 20))); // Reduced spacing
        add(gamePlayAudioCheckBox);
        add(Box.createRigidArea(new Dimension(0, 20))); // Reduced spacing
        add(resumeButton);
        add(Box.createRigidArea(new Dimension(0, 20))); // Reduced spacing
        add(quitButton);
        add(Box.createVerticalGlue());

        setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        resumeButton.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        quitButton.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        gamePlayAudioCheckBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.YELLOW, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    }

    private void styleButton(JButton button) {
        button.setFont(gameView.getRetroFont().deriveFont(Font.BOLD, 24f));
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

    private void styleCheckBox(JCheckBox checkBox) {
        checkBox.setOpaque(false);
        checkBox.setBackground(new Color(0, 0, 0, 0));
        checkBox.setForeground(Color.YELLOW);
        checkBox.setFont(gameView.getRetroFont().deriveFont(Font.BOLD, 21f));
        checkBox.setFocusPainted(false);
        checkBox.setContentAreaFilled(false);
        checkBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.YELLOW, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        int iconSize = 20;
        checkBox.setIcon(new ArcadeCheckBoxIcon(iconSize, Color.YELLOW));
        checkBox.setSelectedIcon(new CheckBoxIcon(iconSize, Color.YELLOW, Color.YELLOW));
    }

    private static class ArcadeCheckBoxIcon implements Icon {
        private int size;
        private Color borderColor;
        public ArcadeCheckBoxIcon(int size, Color borderColor) {
            this.size = size;
            this.borderColor = borderColor;
        }
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(borderColor);
            g.drawRect(x, y, size - 1, size - 1);
        }
        @Override
        public int getIconWidth() {
            return size;
        }
        @Override
        public int getIconHeight() {
            return size;
        }
    }

    private static class CheckBoxIcon implements Icon {
        private int size;
        private Color borderColor;
        private Color fillColor;
        public CheckBoxIcon(int size, Color borderColor, Color fillColor) {
            this.size = size;
            this.borderColor = borderColor;
            this.fillColor = fillColor;
        }
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(borderColor);
            g.drawRect(x, y, size - 1, size - 1);
            g.setColor(fillColor);
            g.fillRect(x + 3, y + 3, size - 6, size - 6);
        }
        @Override
        public int getIconWidth() {
            return size;
        }
        @Override
        public int getIconHeight() {
            return size;
        }
    }
}