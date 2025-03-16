package AsteriodGame.GameObjects;

import java.awt.*;

import AsteriodGame.Utilities.Vector2D;
import static AsteriodGame.Config.Configurations.*;

public class Particle extends GameObject {
    private double lifetime;

    public Particle(double x, double y, double vx, double vy, double lifetime, Color color) {
        super(new Vector2D(x, y), new Vector2D(vx, vy), 12);
        this.lifetime = lifetime;
    }

    @Override
    public boolean isAlive() {
        return lifetime > 0;
    }

    @Override
    public void setAlive() {
        lifetime = 0;
    }

    @Override
    public void update() {
        position.addScaled(velocity, DT);
        lifetime -= DT;
    }

    @Override
    public void draw(Graphics2D g) {
        // Calculate alpha based on remaining lifetime (assumes initial lifetime of 1 second)
        float alpha = (float) Math.max(lifetime / 1.0, 0);
        Color c = new Color(255, 255, 255, (int)(alpha * 255));
        g.setColor(c);
        g.fillOval((int) position.x, (int) position.y, 4, 4);
    }
}
