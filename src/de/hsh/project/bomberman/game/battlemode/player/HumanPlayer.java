package de.hsh.project.bomberman.game.battlemode.player;

import javax.swing.*;

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

    private Direction intendedDirection = Direction.DOWN;

    public HumanPlayer(int playerNumber, InputMap inputMap, ActionMap actionMap, int[] keyCodes) {
        super(playerNumber);
        directionKeys = new Key[] {
                new Key(keyCodes[0], Direction.LEFT, this::pressDirectionAction, this::releaseDirectionAction),
                new Key(keyCodes[1], Direction.RIGHT, this::pressDirectionAction, this::releaseDirectionAction),
                new Key(keyCodes[2], Direction.UP, this::pressDirectionAction, this::releaseDirectionAction),
                new Key(keyCodes[3], Direction.DOWN, this::pressDirectionAction, this::releaseDirectionAction)
        };

        allKeys = new Key[] {
                directionKeys[0], directionKeys[1], directionKeys[2], directionKeys[3],
                new Key(keyCodes[4], this::pressDropAction, this::releaseDropAction)
        };

        for (Key key : allKeys) {
            inputMap.put(KeyStroke.getKeyStroke(key.getKeyCode(), 0), key.actionKeyPressed());
            actionMap.put(key.actionKeyPressed(), new KeyAction((event) -> key.press()));

            inputMap.put(KeyStroke.getKeyStroke(key.getKeyCode(), 0, true), key.actionKeyReleased());
            actionMap.put(key.actionKeyReleased(), new KeyAction((event) -> key.release()));
        }
    }

    private void pressDirectionAction(Key key) {
        intendedDirection = key.getDirection();
    }

    private void releaseDirectionAction(Key key) {
        for (Key other : directionKeys) {
            if (other != key && other.isPressed()) {
                intendedDirection = other.getDirection();
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
        if (getLifes() > 0) {
            if (anyDirectionPressed()) {
                move(intendedDirection);
            } else {
                stop(intendedDirection);
            }
        }
        super.update();
    }
}
