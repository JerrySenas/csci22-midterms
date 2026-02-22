/**
This is a class that sets the properties of a Triangle.
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

public class Triangle extends DrawingObject {
    double angle;

    public Triangle(double x, double y, double w, double h, double a, Color c) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        angle = a;
        color = c;
    }

    public Triangle(double x, double y, double w, double h, Color c) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        angle = 0;
        color = c;
    }

    @Override
    public void draw(Graphics2D g2d) {
        AffineTransform baseTransform = g2d.getTransform();
        g2d.rotate(Math.toRadians(angle), x + w*0.5, y + h*0.5);

        Path2D.Double perimeter = new Path2D.Double();
        perimeter.moveTo(x, y + h);
        perimeter.lineTo(x + w*0.5, y);
        perimeter.lineTo(x + w, y + h);
        perimeter.closePath();
        g2d.setColor(color);
        g2d.fill(perimeter);

        g2d.setTransform(baseTransform);
    }
}
