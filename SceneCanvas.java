/**
This class includes the background of the program and will be the place where the objects will be drawn.
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
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class SceneCanvas extends JComponent {
    int width;
    int height;

    Game game;
    Timer animTimer;
    
    ArrayList<DrawingObject> sprites;

    public SceneCanvas(int w, int h) {
        width = w;
        height = h;

        game = new Game(width, height);
        setupControls();

        sprites = new ArrayList<>();
            
        sprites.add(game.getBG());
        sprites.add(game.getTimer());
        sprites.add(game.getAura());
        sprites.add(game.getSakuya());
        sprites.add(game.getReimu());

        animTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                game.update();
                repaint();
            }
        });
        animTimer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (DrawingObject sprite : sprites) {
            sprite.draw(g2d);
        }
        game.drawKnives(g2d);
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
