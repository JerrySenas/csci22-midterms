import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game {
    int canvasWidth, canvasHeight;
    long timestamp;

    ArrayList<Knife> knives;
    Character reimu;
    boolean leftPressed, upPressed, rightPressed, downPressed, shiftPressed;

    public Game(int w, int h) {
        canvasWidth = w;
        canvasHeight = h;

        reimu = new Character(25, 200, 200);

        knives = new ArrayList<>();
    }

    public void draw(Graphics2D g2d) {
        reimu.draw(g2d);
        
        for (Knife knife : knives) {
            knife.draw(g2d);
        }

    }

    public void update(long currentTime) {
        checkBullets();
        
        // Eval Reimu direction
        reimu.setDirectionX(0);
        reimu.setDirectionY(0);
        if ( leftPressed && (reimu.getCenterX() - reimu.getWidth()*0.5) > 0 ) reimu.setDirectionX(-1);
        if ( rightPressed && (reimu.getCenterX() + reimu.getWidth()*0.5) < canvasWidth ) reimu.setDirectionX(1);
        if ( upPressed && (reimu.getCenterY() - reimu.getHeight()*0.5) > 0 ) reimu.setDirectionY(-1);
        if ( downPressed && (reimu.getCenterY() + reimu.getHeight()*0.5) < canvasHeight ) reimu.setDirectionY(1);

        reimu.setIsFocused(shiftPressed);
        reimu.update();

        if (currentTime - timestamp > 5000) {
            for (int i = 0; i < 16; i++) {
                knives.add(new Knife(100 + i*40, 200, 90, Color.BLUE));
            }
            timestamp = currentTime;
        }
    }

    public void handlePress(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                leftPressed = true;
                break;

            case KeyEvent.VK_UP:
                upPressed = true;
                break;

            case KeyEvent.VK_RIGHT:
                rightPressed = true;
                break;

            case KeyEvent.VK_DOWN:
                downPressed = true;
                break;

            case KeyEvent.VK_SHIFT:
                shiftPressed = true;
                break;
        
            default:
                break;
        }
    }

    public void handleRelease(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                leftPressed = false;
                break;

            case KeyEvent.VK_UP:
                upPressed = false;
                break;

            case KeyEvent.VK_RIGHT:
                rightPressed = false;
                break;

            case KeyEvent.VK_DOWN:
                downPressed = false;
                break;

            case KeyEvent.VK_SHIFT:
                shiftPressed = false;
                break;
        
            default:
                break;
        }

    }

    public void checkBullets() {
        // Destroys bullets outside of canvas
        for (int i=knives.size() - 1; i >= 0; i--) {
            Knife knife = knives.get(i);
            knife.update();
            if (!knife.isAlive())
                knives.remove(i);
        }

        // 
    }

    public ArrayList<Knife> getKnives() { return knives; }
    public Character getReimu() { return reimu; }

    public void setTime(long millis) { timestamp = millis; }
}
