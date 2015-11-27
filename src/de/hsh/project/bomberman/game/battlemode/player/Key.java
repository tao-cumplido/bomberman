package de.hsh.project.bomberman.game.battlemode.player;

import java.util.function.Consumer;

/**
 * Created by taocu on 27.11.2015.
 */
public class Key {

    private int keyCode;
    private Direction direction;
    private boolean pressed;

    private Consumer<Key> pressAction;
    private Consumer<Key> releaseAction;

    public Key(int keyCode, Direction direction, Consumer<Key> pressAction, Consumer<Key> releaseAction) {
        this.pressed = false;
        this.keyCode = keyCode;
        this.direction = direction;

        this.pressAction = pressAction;
        this.releaseAction = releaseAction;
    }

    public Key(int keyCode, Consumer<Key> pressAction, Consumer<Key> releaseAction) {
        this(keyCode, Direction.NONE, pressAction, releaseAction);
    }

    public void press() {
        pressAction.accept(this);
        pressed = true;
    }

    public void release() {
        releaseAction.accept(this);
        pressed = false;
    }

    public boolean isPressed() {
        return pressed;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public String actionKeyPressed() {
        return keyCode + "p";
    }

    public String actionKeyReleased() {
        return keyCode + "r";
    }
}
