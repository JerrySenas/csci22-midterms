import java.awt.*;
import java.awt.geom.*;

public abstract class Bullet {
    // game logic
    double x;
    double y;
    double angle;
    double speedX;
    double speedY;
    boolean isAlive = true;
    double hitboxSize;

    // graphics
    double size;
    double w;
    double h;
    Color color;

    // Graphics
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
    public void setSpeed(double spd) {
        speedX = Math.cos(Math.toRadians(angle))*spd;
        speedY = Math.sin(Math.toRadians(angle))*spd;
    }
    public void setAngle(double theta) { angle = theta; }
    // Graphics Setters
    public void setColor(Color c) { color = c; }

    public void update() {      
        x += speedX;
        y += speedY;
    }
}