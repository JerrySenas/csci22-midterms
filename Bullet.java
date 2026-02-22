/**
___
@author Jerry Senas (255351) and Angelico Soriano (255468)
@version February 22, 2026
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

public abstract class Bullet extends DrawingObject {
    // game logic
    double angle;
    double normalSpeed;
    double speedX;
    double speedY;
    boolean isAlive = true;
    double hitboxSize;

    // Graphics
    @Override
    public void draw(Graphics2D g2d) {
        Ellipse2D.Double bullet = new Ellipse2D.Double(x, y, size, size);
        g2d.setColor(Color.BLUE);
        g2d.fill(bullet);
    }

    // Getters
    public double getWidth() { return w; }
    public double getHeight() { return h; }
    public double getCenterX() { return x + w*0.5; }
    public double getCenterY() { return y + h*0.5; }
    public double getHitboxSize() { return hitboxSize; }
    public double getAngle() { return angle; }
    public boolean isAlive() { return isAlive; }

    // Setters
    @Override
    public void setColor(Color c) { color = c; }

    public void setSpeed(double spd) {
        speedX = Math.cos(Math.toRadians(angle))*spd;
        speedY = Math.sin(Math.toRadians(angle))*spd;
    }
    public void setSpeed() { setSpeed(normalSpeed); }
    public void setAngle(double theta) { angle = theta; }

    public void update() {      
        x += speedX;
        y += speedY;
    }
}