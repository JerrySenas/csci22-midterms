/**
This is where the boss, Sakuya, is drawn. This is also where the instructions for her location and destination are coded.
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

public class Boss extends Bullet {
    double destX;
    double destY;
    boolean isMoving;
    int frames;

    public Boss(double x, double y, double s) {
        this.x = x;
        this.y = y;
        
        size = s;
        w = size;
        h = w*2;

        destX = getCenterX();
        destY = getCenterY();
        speedX = 0;
        speedY = 0;
        isMoving = false;
        frames = 0;
    }

    @Override
    public void draw(Graphics2D g2d) {
        Circle head = new Circle(getCenterX(), y + w*0.25, w*0.5, Color.WHITE);
        head.draw(g2d);

        Circle face = new Circle(getCenterX(), y + w*0.33, w*0.325, Color.decode("#F9EFD3"));
        face.draw(g2d);

        Rectangle2D.Double torso = new Rectangle2D.Double(x + w*0.375, y + w*0.5, w*0.25, w*0.5);
        Triangle skirt = new Triangle(x, y + w*0.5, w, h*0.75, Color.BLUE);
        Triangle apron = new Triangle(x, y + w*0.5, w, h*0.625, Color.WHITE);
        skirt.draw(g2d);
        apron.draw(g2d);
        g2d.setColor(Color.BLUE);
        g2d.fill(torso);

        // Shoulders
        new Square(x + w*0.125, y + w*0.5, w*0.25, Color.WHITE).draw(g2d);
        new Square(x + w*0.625, y + w*0.5, w*0.25, Color.WHITE).draw(g2d);
    }

    @Override
    public void update() {
        if (frames > 0) {
            x += speedX;
            y += speedY;
            frames--;
        }
        if (isMoving && frames == 0) {
            x = destX - w*0.5;
            y = destY - h*0.5;
            speedX = 0;
            speedY = 0;
            isMoving = false;
        }
    }

    public void setDest(double x, double y, int f) {
        destX = x;
        destY = y;
        isMoving = true;
        frames = f;
        speedX = (destX - getCenterX()) / frames;
        speedY = (destY - getCenterY()) / frames;
    }
}
