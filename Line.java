/**
This is a class that sets the properties of a Line.
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
