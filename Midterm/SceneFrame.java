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
