import java.awt.*;
import java.awt.geom.*;

public class Bow extends DrawingObject {
    public Bow(double x, double y, double s, Color c) {
        this.x = x;
        this.y = y;
        size = s;
        w = size;
        h = w*0.5;
        color = c;
    }

    @Override
    public void draw(Graphics2D g2d) {
        new Triangle(x, y, h, h, 90, color).draw(g2d);
        new Triangle(x + h, y, h, h, -90, color).draw(g2d);
    }
}
