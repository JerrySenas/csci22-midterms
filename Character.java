/**
This is the code for the moveable character, Reimu.
@author Jerry Senas (255351) and Angelico Soriano (255468)
@version February __, 2026
I have not discussed the Java language code in my program
with anyone other than my instructor or the teaching assistants
assigned to this course.
I have not used Java language code obtained from another student,
or any other unauthorized source, either modified or unmodified.
If any Java language code or documentation used in my program
was obtained from another source, such as a textbook or website,
that has been clearly noted with a proper citation in the comments
of my program.
*/

import java.awt.*;
import java.awt.geom.*;

public class Character extends Bullet {
    int hp;
    double speed;
    int directionX;
    int directionY;
    boolean isFocused;
    boolean isInvuln;

    int invulnStartFrame;

    static final double NORMALIZE = 1 / Math.sqrt(2);

    public Character(double s, double x, double y) {
        size = s;
        w = size;
        h = w*2;
        color = Color.RED;

        this.x = x;
        this.y = y;
        hitboxSize = w*0.2;
        normalSpeed = 7;
        speed = normalSpeed;
        directionX = 0;
        directionY = 0;
        hp = 5;
        isFocused = false;
        isInvuln = false;
        invulnStartFrame = 0;
    }

    @Override
    public void draw(Graphics2D g2d) {
        Rectangle2D.Double temp = new Rectangle2D.Double(x, y, w, h);
        g2d.setColor(color);
        g2d.fill(temp);

        if (isFocused) {
            Ellipse2D.Double hitbox = new Ellipse2D.Double(getCenterX() - hitboxSize*0.5, getCenterY() - hitboxSize*0.5, hitboxSize, hitboxSize);
            g2d.setColor(Color.BLUE);
            g2d.fill(hitbox);
        }
    }

    public void update(int frameNumber) {
        if (isInvuln && frameNumber - invulnStartFrame > 120) {
            isInvuln = false;
            color = Color.RED;
        }
        
        // Normalize speed
        double currentSpeed = speed;
        if (isFocused) { currentSpeed /= 2; }
        if (directionX != 0 && directionY != 0) {
            currentSpeed *= NORMALIZE;
        }

        x += currentSpeed * directionX;
        y += currentSpeed * directionY;
    }

    public void takeDamage(int frameNumber) {
        isInvuln = true;
        color = Color.BLUE;
        invulnStartFrame = frameNumber;
    }

    @Override
    public void setSpeed(double spd) { speed = spd; }
    public void setDirectionX(int dir) { directionX = dir; }
    public void setDirectionY(int dir) { directionY = dir; }
    public void setIsFocused(boolean focus) { isFocused = focus; }
}
