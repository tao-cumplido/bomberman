package de.hsh.project.bomberman.game.battlemode.player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

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
    private Key[] allKeys;

    private Direction currentDirection = Direction.DOWN;

    public HumanPlayer(int playerNumber, InputMap inputMap, ActionMap actionMap) {
        super(playerNumber);
        directionKeys = new Key[] {
                new Key(KeyEvent.VK_LEFT, Direction.LEFT, this::pressDirectionAction, this::releaseDirectionAction),
                new Key(KeyEvent.VK_RIGHT, Direction.RIGHT, this::pressDirectionAction, this::releaseDirectionAction),
                new Key(KeyEvent.VK_UP, Direction.UP, this::pressDirectionAction, this::releaseDirectionAction),
                new Key(KeyEvent.VK_DOWN, Direction.DOWN, this::pressDirectionAction, this::releaseDirectionAction)
        };

        allKeys = new Key[] {
                directionKeys[0], directionKeys[1], directionKeys[2], directionKeys[3],
                new Key(KeyEvent.VK_SPACE, this::pressDropAction, this::releaseDropAction)
        };

        for (Key key : allKeys) {
            inputMap.put(KeyStroke.getKeyStroke(key.getKeyCode(), 0), key.actionKeyPressed());
            actionMap.put(key.actionKeyPressed(), new KeyAction((event) -> key.press()));

            inputMap.put(KeyStroke.getKeyStroke(key.getKeyCode(), 0, true), key.actionKeyReleased());
            actionMap.put(key.actionKeyReleased(), new KeyAction((event) -> key.release()));
        }
    }

    private void pressDirectionAction(Key key) {
        currentDirection = key.getDirection();
    }

    private void releaseDirectionAction(Key key) {
        for (Key other : directionKeys) {
            if (other != key && other.isPressed()) {
                currentDirection = other.getDirection();
            }
        }
    }

    private void pressDropAction(Key key) {
        if (!key.isPressed()) {
            dropBomb();
        }
    }

    private void releaseDropAction(Key key) {}

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
