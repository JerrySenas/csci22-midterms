import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.*;

public class SceneCanvas extends JComponent {
    int width;
    int height;

    Game game;
    Timer animTimer;
    Rectangle2D.Double bg;

    public SceneCanvas(int w, int h) {
        width = w;
        height = h;

        game = new Game(width, height);
        setupControls();

        bg = new Rectangle2D.Double(0, 0, width, height);

        animTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                game.update(System.currentTimeMillis());
                repaint();
            }
        });
        game.setTime(System.currentTimeMillis());
        animTimer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.GRAY);
        g2d.fill(bg);

        game.draw(g2d);
    }

    public void setupControls() {
        setFocusable(true);
        requestFocusInWindow();
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                game.handlePress(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                game.handleRelease(e.getKeyCode());
            }
        });
    }
}
