/**
This is a class that sets the properties of a Circle.
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