import java.awt.*;
import java.awt.geom.*;

public class Line extends DrawingObject {
    double x2;
    double y2;
    int size;

    public Line(double x1, double y1, double x2, double y2, int s, Color c) {
        this.x = x1;
        this.y = y1;
        this.x2 = x2;
        this.y2 = y2;
        color = c;
        size = s;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(size));
        g2d.draw(new Line2D.Double(x, y, x2, y2));
    }

    public void setX2(double x2) { this.x2 = x2; }
    public void setY2(double y2) { this.y2 = y2; }
}
