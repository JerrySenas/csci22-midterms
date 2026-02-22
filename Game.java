/**
This class contains the game logic of the program. This includes player movement, knife speed, spellcard timing, etc.

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
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game {
    int canvasWidth, canvasHeight;
    Square bg;
    Circle aura;
    int frameNumber;
    int spellcardTimer;
    boolean timeStopped;

    double spellcardRadius;
    Line spellcardTimerLine;

    ArrayList<Knife> knives;
    Character reimu;
    Boss sakuya;
    boolean leftPressed, upPressed, rightPressed, downPressed, shiftPressed;
    
    int spellcardStartFrame;
    boolean isPaused;
    String pauseMsg;
    
    /**
     * Constructs a new Game instance.
     *
     * @param w width of the game canvas
     * @param h height of the game canvas
     */

    public Game(int w, int h) {
        canvasWidth = w;
        canvasHeight = h;
        bg = new Square(0, 0, canvasWidth, new Color(0, 0, 0));

        sakuya = new Boss(canvasWidth / 2, canvasHeight / 4, 25);
        aura = new Circle(sakuya.getCenterX(), sakuya.getCenterY(), 100, new Color(255, 255, 255, 0));

        spellcardTimerLine = new Line(100, 25, 700, 25, 3, Color.YELLOW);
        spellcardTimer = 0;
        timeStopped = false;
        spellcardRadius = 200;
        
        frameNumber = 0;
        spellcardStartFrame = 0;
        isPaused = true;
        pauseMsg = "Arrow keys to move, Shift to show hitbox. \n Try to avoid the knives :3\n\n\nPress any key to continue.";
        reimu = new Character(25, 400, 200);
        knives = new ArrayList<>();
    }

    /**
     * Updates the game state for a single frame.
     *
     * This includes:
     * 
     *   Advancing the frame counter
     *   Updating the spellcard timer
     *   Processing player input and movement
     *   Updating boss and projectile logic
     *   Checking collisions
     *   Handling spellcard completion
     * 
     * If the game is paused, this method exits early without updating state.
     */

    public void update() {
        frameNumber ++;

        if (isPaused) {
            return;
        }
        if (!timeStopped) {
            spellcardTimer ++;
        }

        checkBullets(frameNumber);
        
        // Evaluate Reimu's direction
        reimu.setDirectionX(0);
        reimu.setDirectionY(0);
        if ( leftPressed && (reimu.getCenterX() - reimu.getWidth()*0.5) > 0 ) reimu.setDirectionX(-1);
        if ( rightPressed && (reimu.getCenterX() + reimu.getWidth()*0.5) < canvasWidth ) reimu.setDirectionX(1);
        if ( upPressed && (reimu.getCenterY() - reimu.getHeight()*0.5) > 0 ) reimu.setDirectionY(-1);
        if ( downPressed && (reimu.getCenterY() + reimu.getHeight()*0.5) < canvasHeight ) reimu.setDirectionY(1);

        reimu.setIsFocused(shiftPressed);
        reimu.update(frameNumber);
        sakuya.update();

        runSpellcard(frameNumber - spellcardStartFrame);

        if (spellcardTimer < 1700) {
            spellcardTimerLine.setX2(100 + (600 - 600 * spellcardTimer / 1700));
        } else {
            knives = new ArrayList<>();
            frameNumber = 0;
            isPaused = true;
            pauseMsg = String.format(
                "You have been hit %d times. %s\n\n\nPress any key to retry.",
                reimu.getHitCount(),
                (reimu.getHitCount() == 0)? "Good job!" : "Better luck next time :("
            );
        }
    }

    /**
     * Detects which key was pressed.
     *  @param keyCode The keycode of the pressed key.
     */

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

    /**
     * Detects which key was released.
     *  @param keyCode The keycode of the released key.
     */
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

    /**
     * Handles collision and cleans up any bullets out of canvas.
     *  @param frameNumber The current frame. Used by Reimu's invulnerability flickering.
     */
    public void checkBullets(int frameNumber) {
        // Destroys bullets outside of canvas
        double reimuX = reimu.getCenterX();
        double reimuY = reimu.getCenterY();
        if (
            !reimu.isInvuln &&
            Math.hypot(sakuya.getCenterX() - reimuX, sakuya.getCenterY() - reimuY) < reimu.getHitboxSize() + sakuya.getHitboxSize()
        ) {
            reimu.takeDamage(frameNumber);
            return;
        }
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
    }

    /**
     * Spawns knives, stops time, changes bg color and aura, and moves Sakuya to a random position.
     * Sakuya's penultimate spellcard in Lunatic difficulty. This attack cycles.
     * Timing is based on elapsedFrames
     * 
     *  @param elapsedFrames How many frames is has been since the end of the last cycle.
     */
    public void runSpellcard(int elapsedFrames) {
        // 5 seconds between cycle
        if (elapsedFrames < 300) {
            // First attack warning
            if (elapsedFrames == 274) {
                aura.setOpacity(0);
                aura.setColor(new Color(255, 255, 255));
                aura.setSize(1350);
            } else if (elapsedFrames > 274) {
                aura.addSize(-50);
                aura.addOpacity(5);
            }
        } else if (elapsedFrames < 360) {
            // Send 4 waves of 15 knives every 6 frames (0.1 s)
            if (elapsedFrames == 300) {
                aura.setOpacity(0);
            }
            if (elapsedFrames < 319 && elapsedFrames % 6 == 0) {
                for (int i = 0; i < 16; i++) {
                    knives.add(new Knife(sakuya.getCenterX(), sakuya.getCenterY(), i*24, Color.RED));
                }
            }
        } else if (elapsedFrames < 480) {
            // Stop time for 120 frames (2 s)
            if (elapsedFrames == 360) {
                timeStopped = true;
                aura.setOpacity(100);
                aura.setColor(new Color(255, 255, 255));
                bg.setColor(Color.decode("#370603"));
                for (Knife knife : knives) {
                    knife.setSpeed(0);
                }
                reimu.setSpeed(0);
                sakuya.setDest(
                    canvasWidth*0.25 + Math.random()*canvasWidth*0.5,
                    canvasHeight*0.125 + Math.random()*canvasHeight*0.25,
                    40
                );
            }
            if (elapsedFrames < 384) {
                // Spawn 4 waves of knives every 6 frames (0.1 s)
                aura.addSize(50);
                aura.addOpacity(-5);

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
            } else if (elapsedFrames < 437 && elapsedFrames > 400) {
                // Randomly rotate knives
                if (elapsedFrames % 6 == 0) {              
                    for (Knife knife : knives) {
                        if (knife.turned) {
                            continue;
                        }

                        if (Math.random() < 0.25) {
                            knife.setAngle(Math.random()*360);
                            knife.setColor(Color.green);
                            knife.setTurned();
                        }
                    }
                }
            } else if (elapsedFrames >= 454) {
                // Warn time stop end
                if (elapsedFrames == 454) {
                    aura.setX(sakuya.getCenterX() - aura.getSize()*0.5);
                    aura.setY(sakuya.getCenterY() - aura.getSize()*0.5);
                    aura.setOpacity(100);
                    aura.setSize(100);
                } else {
                    aura.addSize(50);
                    aura.addOpacity(-5);
                }
            }
        } else {
            timeStopped = false;
            bg.setColor(Color.decode("#801700"));
            aura.setOpacity(0);
            for (Knife knife : knives) {
                knife.setSpeed();
            }
            reimu.setSpeed();
            spellcardStartFrame = frameNumber;
            spellcardRadius = 200;
        }
    }

    /**
     * Unpauses the game and resets the game state to the starting position.
     */
    public void unpause() {
        spellcardTimer = 0;
        timeStopped = false;
        spellcardRadius = 200;
        
        frameNumber = 0;
        spellcardStartFrame = 0;
        reimu.reset();
        sakuya.reset();
        bg.setColor(Color.BLACK);
        aura.setOpacity(0);
        aura.setSize(100);
        aura.setX(sakuya.getCenterX() - aura.getSize()*0.5);
        aura.setY(sakuya.getCenterY() - aura.getSize()*0.5);
        isPaused = false;
    }

    public DrawingObject getBG() { return bg; }
    public DrawingObject getAura() { return aura; }
    public DrawingObject getTimer() { return spellcardTimerLine; }
    public DrawingObject getReimu() { return reimu; }
    public DrawingObject getSakuya() { return sakuya; }
    public ArrayList<Knife> getKnives() { return knives; }
    public int getFrameNumber() { return frameNumber; }
    public String getPauseMsg() { return pauseMsg; }
    public boolean isPaused() { return isPaused; }

    /**
     * Returns the angle between the horizontal and the line between Sakuya and Reimu's centers.
     * Return value is in degrees, within the range -180 to 180, and relative to Reimu.
     */
    public double getBossAngle() {
        double diffX = sakuya.getCenterX() - reimu.getCenterX();
        double diffY = sakuya.getCenterY() - reimu.getCenterY();
        return Math.toDegrees(Math.atan2(diffY, diffX));
    }
}
