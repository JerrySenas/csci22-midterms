
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Square extends DrawingObject {
    public Square(double x, double y, double s, Color c) {
        this.x = x;
        this.y = y;
        size = s;
        color = c;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fill(new Rectangle2D.Double(x, y, size, size));
    }
}