import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Character extends Bullet {
    int hp;
    double speed;
    int directionX;
    int directionY;
    boolean isFocused;

    static final double NORMALIZE = 1 / Math.sqrt(2);

    public Character(double s, double x, double y) {
        size = s;
        w = size;
        h = w*2;

        this.x = x;
        this.y = y;
        speed = 7;
        directionX = 0;
        directionY = 0;
        hp = 5;
        isFocused = false;
    }

    @Override
    public void draw(Graphics2D g2d) {
        Rectangle2D.Double temp = new Rectangle2D.Double(x, y, w, h);
        g2d.setColor(Color.RED);
        g2d.fill(temp);

        if (isFocused) {
            Ellipse2D.Double hitbox = new Ellipse2D.Double(getCenterX() - w*0.1, getCenterY() - w*0.1, w*0.2, w*0.2);
            g2d.setColor(Color.BLUE);
            g2d.fill(hitbox);
        }
    }

    @Override
    public void update() {
        // Normalize speed
        double currentSpeed = speed;
        if (isFocused) { currentSpeed /= 2; }
        if (directionX != 0 && directionY != 0) {
            currentSpeed *= NORMALIZE;
        }

        x += currentSpeed * directionX;
        y += currentSpeed * directionY;
    }

    public void setDirectionX(int dir) { directionX = dir; }
    public void setDirectionY(int dir) { directionY = dir; }
    public void setIsFocused(boolean focus) { isFocused = focus; }
}
