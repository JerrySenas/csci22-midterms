/**
_______
@author Jerry Senas (255351) and Angelico Soriano (255468)
@version February __, 2026
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

public abstract class DrawingObject {
    double x;
    double y;

    double w;
    double h;
    double size;
    Color color;

    public abstract void draw(Graphics2D g2d);

    public double getX() { return x; }
    public double getY() { return y; }
    public double getCenterX() { return x + w*0.5; }
    public double getCenterY() { return y + h*0.5; }
    public double getW() { return w; }
    public double getH() { return h; }
    public double getSize() { return size; }

    public void setColor(Color c) { color = c; }
    public void setSize(double s) { size = s; }
}