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

    public HumanPlayer() {

    }

    @Override
    public void update() {
        super.update();
        int dx = keyLeftPressed ? - 5 : keyRightPressed ? 5 : 0;
        int dy = keyUpPressed ? - 5 : keyDownPressed ? 5 : 0;
        move(dx, dy);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();

        if (k == keyLeft) {
            keyLeftPressed = true;
            sprite.playAnimation(PlayerAnimation.WALK_LEFT, true);
        }

        if (k == keyRight) {
            keyRightPressed = true;
            sprite.playAnimation(PlayerAnimation.WALK_RIGHT, true);
        }

        if (k == keyUp) {
            keyUpPressed = true;
            sprite.playAnimation(PlayerAnimation.WALK_UP, true);
        }

        if (k == keyDown) {
            keyDownPressed = true;
            sprite.playAnimation(PlayerAnimation.WALK_DOWN, true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int k = e.getKeyCode();

        if (k == keyLeft) {
            keyLeftPressed = false;
        }

        if (k == keyRight) {
            keyRightPressed = false;
        }

        if (k == keyUp) {
            keyUpPressed = false;
        }

        if (k == keyDown) {
            keyDownPressed = false;
        }
    }
}
