/**
This class contains the game logic of the program. This includes player movement, knife speed and rotation, etc.
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
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game {
    int canvasWidth, canvasHeight;
    Square bg;
    Circle aura;
    int auraSize;
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

    public Game(int w, int h) {
        canvasWidth = w;
        canvasHeight = h;
        bg = new Square(0, 0, canvasWidth, Color.decode("#801700"));

        sakuya = new Boss(canvasWidth / 2, canvasHeight / 4, 25);
        aura = new Circle(sakuya.getCenterX(), sakuya.getCenterY(), 100, new Color(255, 255, 255, 0));

        spellcardTimerLine = new Line(100, 25, 700, 25, 3, Color.YELLOW);
        spellcardTimer = 0;
        timeStopped = false;
        spellcardRadius = 200;

        
        frameNumber = 0;
        reimu = new Character(25, 400, 200);
        knives = new ArrayList<>();
    }

    public void drawKnives(Graphics2D g2d) {
        for (Knife knife : knives) {
            knife.draw(g2d);
        }
    }

    public void update() {
        frameNumber ++;
        if (!timeStopped) {
            spellcardTimer ++;
        }

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
        sakuya.update();

        runSpellcard(frameNumber - spellcardStartFrame);

        if (spellcardTimer < 1800) {
            spellcardTimerLine.setX2(100 + (600 - (spellcardTimer / 3)));
        } else {
            System.out.println(reimu.getHitCount());
            spellcardTimer = 0;
            frameNumber = 0;
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
        // 5 s between cycle
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
            // Send 4 waves of 14 knives every 6 frames (0.1 s)
            if (elapsedFrames == 300) {
                aura.setOpacity(0);
            }
            if (elapsedFrames < 319 && elapsedFrames % 6 == 0) {
                for (int i = 0; i < 15; i++) {
                    knives.add(new Knife(sakuya.getCenterX(), sakuya.getCenterY(), i*25, Color.RED));
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
        }
        
        else {
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

    public DrawingObject getBG() { return bg; }
    public DrawingObject getAura() { return aura; }
    public DrawingObject getTimer() { return spellcardTimerLine; }
    public DrawingObject getReimu() { return reimu; }
    public DrawingObject getSakuya() { return sakuya; }

    public double getBossAngle() {
        double diffX = sakuya.getCenterX() - reimu.getCenterX();
        double diffY = sakuya.getCenterY() - reimu.getCenterY();
        return Math.toDegrees(Math.atan2(diffY, diffX));
    }
}
