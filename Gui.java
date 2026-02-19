import java.awt.*;
import javax.swing.*;

public class Gui {
    JFrame frame;
    Canvas canvas;
    int width;
    int height;

    public Gui() {
        frame = new JFrame();
        canvas = new Canvas(800, 900);
        canvas.setPreferredSize(new Dimension(800, 900));
        width = 1600;
        height = 900;
    }

    public void setupGUI() {
        Container cp = frame.getContentPane();
        cp.add(canvas, BorderLayout.CENTER);
        frame.setSize(width, height);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
