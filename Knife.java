import java.awt.*;
import java.awt.geom.*;

public class Knife extends Bullet {
    boolean turned;

    public Knife(double s, double x, double y, double angle, Color c) {
        size = s;
        color = c;
        w = size*2.5;
        h = size;

        this.x = x;
        this.y = y;
        this.angle = angle;
        turned = false;

        setSpeed(5);
    }

    public Knife(double x, double y, double angle, Color c) {
        this(15, x, y, angle, c);
    }

    @Override
    public void draw(Graphics2D g) {
        AffineTransform baseTransform = g.getTransform();
        g.rotate(Math.toRadians(angle), x + w*0.5, y + h*0.5);

        Ellipse2D.Double pommel = new Ellipse2D.Double(x, y + h*0.25, h*0.5, h*0.5);
        g.setColor(color);
        g.fill(pommel);

        Rectangle2D.Double hilt = new Rectangle2D.Double(x + h*0.25, y + h*0.25, h*0.5, h*0.5);
        g.fill(hilt);

        Rectangle2D.Double guard = new Rectangle2D.Double(x + h*0.75, y, h*0.167, h);
        g.fill(guard);

        Path2D.Double blade = new Path2D.Double();
        blade.moveTo(x + h*0.75 + h*0.167, y + h*0.25);
        blade.lineTo(x + w - h*0.5, y + h*0.25);
        blade.lineTo(x + w, y + h*0.5);
        blade.lineTo(x + w - h*0.5, y + h*0.75);
        blade.lineTo(x + h*0.75 + h*0.167, y + h*0.75);
        blade.closePath();

        g.setStroke(new BasicStroke(2));
        g.draw(blade);
        g.setColor(Color.WHITE);
        g.fill(blade);

        g.setTransform(baseTransform);
    }


    public boolean isTurned() { return turned; }
    public void setTurned() { turned = false; }
}
