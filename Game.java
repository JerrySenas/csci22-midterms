import java.util.ArrayList;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Game {
    int canvasWidth, canvasHeight;
    int frameNumber;

    double bossX;
    double bossY;
    double spellcardRadius;

    ArrayList<Knife> knives;
    Character reimu;
    boolean leftPressed, upPressed, rightPressed, downPressed, shiftPressed;
    
    int spellcardStartFrame;

    public Game(int w, int h) {
        canvasWidth = w;
        canvasHeight = h;

        bossX = canvasWidth / 2;
        bossY = canvasHeight / 4;
        spellcardRadius = 200;

        frameNumber = 0;
        reimu = new Character(25, 400, 200);
        knives = new ArrayList<>();
    }

    public void draw(Graphics2D g2d) {
        reimu.draw(g2d);

        for (Knife knife : knives) {
            knife.draw(g2d);
        }

    }

    public void update() {
        frameNumber += 1;
        checkBullets(frameNumber);
        
        // Eval Reimu direction
        reimu.setDirectionX(0);
        reimu.setDirectionY(0);
        if ( leftPressed && (reimu.getCenterX() - reimu.getWidth()*0.5) > 0 ) reimu.setDirectionX(-1);
        if ( rightPressed && (reimu.getCenterX() + reimu.getWidth()*0.5) < canvasWidth ) reimu.setDirectionX(1);
        if ( upPressed && (reimu.getCenterY() - reimu.getHeight()*0.5) > 0 ) reimu.setDirectionY(-1);
        if ( downPressed && (reimu.getCenterY() + reimu.getHeight()*0.5) < canvasHeight ) reimu.setDirectionY(1);

        reimu.setIsFocused(shiftPressed);
        reimu.update(frameNumber);

        runSpellcard(frameNumber - spellcardStartFrame);
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

    public void checkBullets(int frameNumber) {
        // Destroys bullets outside of canvas
        double reimuX = reimu.getCenterX();
        double reimuY = reimu.getCenterY();
        for (int i=knives.size() - 1; i >= 0; i--) {
            Knife knife = knives.get(i);
            knife.update();

            if (
                knife.getCenterX() < 0 ||
                knife.getCenterX() > canvasWidth ||
                knife.getCenterY() < 0 ||
                knife.getCenterY() > canvasHeight
            ) {
                knives.remove(i);
                continue;
            }

            if (
                !reimu.isInvuln &&
                Math.hypot(knife.getCenterX() - reimuX, knife.getCenterY() - reimuY) <= (knife.getHitboxSize() + reimu.getHitboxSize())*0.5
            ) {
                reimu.takeDamage(frameNumber);
                knives.remove(i);
            }
        }

        // 
    }

    public void runSpellcard(int elapsedFrames) {
        if (elapsedFrames < 300) {
            // Wait 5 secs
        } else if (elapsedFrames < 360) {
            // Send 4 waves of 14 knives every 6 frames (0.1 s)
            if (elapsedFrames < 319 && elapsedFrames % 6 == 0) {
                for (int i = 0; i < 15; i++) {
                    knives.add(new Knife(bossX, bossY, i*25, Color.RED));
                }
            }
        } else if (elapsedFrames < 480) {
            // Stop time for 120 frames (2 s)
            if (elapsedFrames == 360) {
                for (Knife knife : knives) {
                    knife.setSpeed(0);
                }
                reimu.setSpeed(0);
            }
            if (elapsedFrames < 385) {
                // Spawn 4 waves of knives every 6 frames (0.1 s)
                if (elapsedFrames % 6 == 0) {
                    double angle = getBossAngle() - 90;
                    
                    for (int j = 0; j < 10; j++) {
                        double knifeAngle = Math.toRadians(angle + j*20);
                        double knifeX = reimu.getCenterX() + spellcardRadius*Math.cos(knifeAngle);
                        double knifeY = reimu.getCenterY() + spellcardRadius*Math.sin(knifeAngle);

                        Knife knife1 = new Knife(
                            knifeX,
                            knifeY,
                            angle + j*20 + 135,
                            Color.BLUE
                        );
                        Knife knife2 = new Knife(
                            knifeX,
                            knifeY,
                            angle + j*20 + 180,
                            Color.BLUE
                        );
                        Knife knife3 = new Knife(
                            knifeX,
                            knifeY,
                            angle + j*20 + 225,
                            Color.BLUE
                        );
                        knife1.setSpeed(0);
                        knife2.setSpeed(0);
                        knife3.setSpeed(0);
                        knives.add(knife1);
                        knives.add(knife2);
                        knives.add(knife3);
                    }
                    spellcardRadius += 40;
                }
            }

            else if (elapsedFrames < 425) {
                // Randomly rotate knives
                if (elapsedFrames % 6 == 0) {              
                    for (Knife knife : knives) {
                        if (knife.turned) {
                            continue;
                        }

                        if (Math.random() < 0.2) {
                            knife.setAngle(Math.random()*360);
                            knife.setColor(Color.green);
                            knife.setTurned();
                        }
                    }
                }
            }
        }
        
        else {
            for (Knife knife : knives) {
                knife.setSpeed();
            }
            reimu.setSpeed();
            spellcardStartFrame = frameNumber;
            spellcardRadius = 200;
        }
    }

    public ArrayList<Knife> getKnives() { return knives; }
    public Character getReimu() { return reimu; }

    public double getBossDistance() {
        return Math.hypot(bossX - reimu.getCenterX(), bossY - reimu.getCenterY());
    }
    public double getBossAngle() {
        double diffX = bossX - reimu.getCenterX();
        double diffY = bossY - reimu.getCenterY();
        return Math.toDegrees(Math.atan2(diffY, diffX));
    }
}
