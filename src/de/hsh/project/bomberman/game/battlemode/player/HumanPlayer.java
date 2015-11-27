package de.hsh.project.bomberman.game.battlemode.player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by taocu on 26.10.2015.
 */
public class HumanPlayer extends Player {

    private Key keyLeft;
    private Key keyRight;
    private Key keyUp;
    private Key keyDown;
    private Key keyDropBomb;
    private Key keyTriggerBomb;

    private Key[] directionKeys;

    private Direction currentDirection = Direction.DOWN;

    public HumanPlayer(InputMap inputMap, ActionMap actionMap) {

        this.keyLeft = new Key(KeyEvent.VK_LEFT, Direction.LEFT);
        this.keyRight = new Key(KeyEvent.VK_RIGHT, Direction.RIGHT);
        this.keyUp = new Key(KeyEvent.VK_UP, Direction.UP);
        this.keyDown = new Key(KeyEvent.VK_DOWN, Direction.DOWN);

        directionKeys = new Key[] {keyLeft, keyRight, keyUp, keyDown};

        for (Key key : directionKeys) {
            inputMap.put(KeyStroke.getKeyStroke(key.getKeyCode(), 0), key.actionKeyPressed());
            actionMap.put(key.actionKeyPressed(), new KeyAction((event) -> pressDirectionKey(key)));

            inputMap.put(KeyStroke.getKeyStroke(key.getKeyCode(), 0, true), key.actionKeyReleased());
            actionMap.put(key.actionKeyReleased(), new KeyAction((event) -> releaseDirectionKey(key)));
        }
    }

    private void pressDirectionKey(Key key) {
        key.press();
        currentDirection = key.getDirection();
    }

    private void releaseDirectionKey(Key key) {
        key.release();
        for (Key other : directionKeys) {
            if (other != key && other.isPressed()) {
                currentDirection = other.getDirection();
            }
        }
    }

    private boolean anyDirectionPressed() {
        for (Key key : directionKeys) {
            if (key.isPressed()) return true;
        }

        return false;
    }

    @Override
    public void update() {
        if (anyDirectionPressed()) {
            move(currentDirection);
        } else {
            stop(currentDirection);
        }
        super.update();
    }
}
