/**
This class contains the GUI for the program.
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
import javax.swing.*;

public class SceneFrame {
    JFrame frame;
    SceneCanvas canvas;
    int width;
    int height;

    public SceneFrame() {
        frame = new JFrame();
        canvas = new SceneCanvas(800, 600);
        canvas.setPreferredSize(new Dimension(800, 600));
        width = 1600;
        height = 900;
    }

    public void setupGUI() {
        Container cp = frame.getContentPane();
        cp.add(canvas, BorderLayout.CENTER);
        frame.setSize(width, height);
        frame.setTitle("Midterm Project - Senas - Soriano");
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
