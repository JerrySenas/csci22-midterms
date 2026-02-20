
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Circle extends DrawingObject {
    int opacity;

    public Circle(double x, double y, double s, Color c) {
        size = s;
        this.x = x - s*0.5;
        this.y = y - s*0.5;
        color = c;
        opacity = c.getAlpha();
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fill(new Ellipse2D.Double(x, y, size, size));
    }

    @Override
    public void setColor(Color c) {
        color = new Color(c.getRed(), c.getGreen(), c.getBlue(), opacity);
    }

    public void addSize(double add) {
        size = Math.max(size + add, 0);
        x -= add*0.5;
        y -= add*0.5;
    }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setOpacity(int o) { opacity = o; setColor(color); }
    public void addOpacity(int o) {
        opacity = Math.max(opacity + o, 0);
        setColor(color);
    }
}