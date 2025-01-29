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

        game.ship.draw(g);
        // Get the astroids from the game manager, and render them on the view
        List<BasicAsteriod> gameAsteroids = game.asteroids;
        for(BasicAsteriod a : gameAsteroids){
            a.draw(g);
            int xA = (int) a.position.x; // maybe add explosive effect here? 
            int yA = (int) a.position.y;

            int xB = (int) game.ship.position.x;
            int yB = (int) game.ship.position.y;

            g.drawLine((int)xA, (int)yA, (int)xB, (int)yB); 
        }
    }

    @Override
    public Dimension getPreferredSize(){
        return Constants.FRAME_SIZE;
    }
}
