package AsteriodGame;

import AsteriodGame.Views.GameMenu;

import javax.swing.*;
import java.awt.*;

public class StartGame {
    public static void main(String[] args) {
        GraphicsDevice gd = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();

        JFrame frame = new JFrame("Game");
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new GameMenu());
        gd.setFullScreenWindow(frame);
    }
}