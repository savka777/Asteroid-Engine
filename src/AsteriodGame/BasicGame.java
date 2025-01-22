package AsteriodGame;
import Utilities.JEasyFrame;

import javax.swing.*;

import static AsteriodGame.Constants.DELAY;

import java.util.ArrayList;
import java.util.List;

// Game Manager
public class BasicGame {
    public List<BasicAsteriod> asteroids; // Asteroids that will spawn on screen
    public static final int N_INIT_ASTEROIDS = 15;

    // Init of Game
    public BasicGame(){
        asteroids = new ArrayList<BasicAsteriod>();
        for(int i = 0; i < N_INIT_ASTEROIDS; i++){
            asteroids.add(BasicAsteriod.MakeRandomAsteroid());
        }
    }
//    public static void main(String[] args) throws InterruptedException {
//        BasicGame game = new BasicGame();
//        BasicView view = new BasicView(game);
//        new JEasyFrame(view, "Basic Game");
//
//        while(true){
//            game.update(); // Update the asteroids
//            view.repaint(); // Render the screen
//            Thread.sleep(DELAY);
//        }
//    }

    // This one has the Game Menu
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        JFrame frame = new JFrame("Asteroids Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new BasicGameMenu());
        frame.setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    });
}

    public void update(){
        for(BasicAsteriod a : asteroids){
            a.update();
        }
    }
}
