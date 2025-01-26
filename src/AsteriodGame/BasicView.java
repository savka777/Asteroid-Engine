package AsteriodGame;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

// Graphics Renderer
public class BasicView extends JComponent {
    private static final Color BG_COLOR = Color.BLACK;
    private BasicGame game; // Instance to our Game Manager

    public BasicView(BasicGame game){
        this.game = game;
    }

    @Override
    public void paintComponent(Graphics graphics){
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(BG_COLOR);
        g.fillRect(0,0,getWidth(),getHeight());

        // Get the astroids from the game manager, and render them on the view
        List<BasicAsteriod> gameAsteroids = game.asteroids;
        for(BasicAsteriod a : gameAsteroids){
            a.draw(g);
        }
    }

    @Override
    public Dimension getPreferredSize(){
        return Constants.FRAME_SIZE;
    }
}
