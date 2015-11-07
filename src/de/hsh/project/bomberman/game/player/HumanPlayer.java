package de.hsh.project.bomberman.game.player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by taocu on 26.10.2015.
 */
public class HumanPlayer extends Player implements KeyListener {

    private int keyLeft = KeyEvent.VK_LEFT;
    private int keyRight = KeyEvent.VK_RIGHT;
    private int keyUp = KeyEvent.VK_UP;
    private int keyDown = KeyEvent.VK_DOWN;
    private int keyDropBomb = KeyEvent.VK_SPACE;
    private int keyTriggerBomb = KeyEvent.VK_SHIFT;

    private boolean keyLeftPressed = false;
    private boolean keyRightPressed = false;
    private boolean keyUpPressed = false;
    private boolean keyDownPressed = false;

    private Direction currentDirection = Direction.DOWN;

    public HumanPlayer() {

    }

    @Override
    public void update() {
        if (keyLeftPressed || keyRightPressed || keyDownPressed || keyUpPressed) {
            move(currentDirection);
        } else {
            stop(currentDirection);
        }
        super.update();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();

        if (k == keyLeft) {
            keyLeftPressed = true;
            currentDirection = Direction.LEFT;
        }

        if (k == keyRight) {
            keyRightPressed = true;
            currentDirection = Direction.RIGHT;
        }

        if (k == keyUp) {
            keyUpPressed = true;
            currentDirection = Direction.UP;
        }

        if (k == keyDown) {
            keyDownPressed = true;
            currentDirection = Direction.DOWN;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int k = e.getKeyCode();

        if (k == keyLeft) {
            keyLeftPressed = false;
            if (keyRightPressed) {
                currentDirection = Direction.RIGHT;
            }
            if (keyDownPressed) {
                currentDirection = Direction.DOWN;
            }
            if (keyUpPressed) {
                currentDirection = Direction.UP;
            }
        }

        if (k == keyRight) {
            keyRightPressed = false;
            if (keyLeftPressed) {
                currentDirection = Direction.LEFT;
            }
            if (keyDownPressed) {
                currentDirection = Direction.DOWN;
            }
            if (keyUpPressed) {
                currentDirection = Direction.UP;
            }
        }

        if (k == keyUp) {
            keyUpPressed = false;
            if (keyDownPressed) {
                currentDirection = Direction.DOWN;
            }
            if (keyLeftPressed) {
                currentDirection = Direction.LEFT;
            }
            if (keyRightPressed) {
                currentDirection = Direction.RIGHT;
            }
        }

        if (k == keyDown) {
            keyDownPressed = false;
            if (keyUpPressed) {
                currentDirection = Direction.UP;
            }
            if (keyLeftPressed) {
                currentDirection = Direction.LEFT;
            }
            if (keyRightPressed) {
                currentDirection = Direction.RIGHT;
            }
        }
    }
}
