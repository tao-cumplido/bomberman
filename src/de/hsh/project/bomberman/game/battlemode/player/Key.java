package de.hsh.project.bomberman.game.battlemode.player;

/**
 * Created by taocu on 27.11.2015.
 */
public class Key {

    private int keyCode;
    private Player.Direction direction;
    private boolean pressed;

    public Key(int keyCode, Player.Direction direction) {
        this.pressed = false;
        this.keyCode = keyCode;
        this.direction = direction;
    }

    public Key(int keyCode) {
        this(keyCode, Player.Direction.NONE);
    }

    public void press() {
        pressed = true;
    }

    public void release() {
        pressed = false;
    }

    public boolean isPressed() {
        return pressed;
    }

    public Player.Direction getDirection() {
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
