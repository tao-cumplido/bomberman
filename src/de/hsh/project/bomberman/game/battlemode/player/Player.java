package de.hsh.project.bomberman.game.battlemode.player;

import de.hsh.project.bomberman.game.battlemode.bomb.Bomb;
import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;
import de.hsh.project.bomberman.game.battlemode.powerup.PowerUp;
import de.hsh.project.bomberman.game.battlemode.powerup.Surprise;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by taocu on 26.10.2015.
 */
public abstract class Player {

    private Rectangle bounds = new Rectangle(16, 16, 16, 16);
    private int bombs;
    private int bombRange;
    private int speed = 2;
    private boolean kickAbility;
    private boolean remoteControl;
    private int lifes;
    private ArrayList<PowerUp> powerUps;
    private Surprise temporaryAbility;
    private ArrayList<Bomb> bombQueue;

    private BufferedImage frame;

    protected Sprite sprite;

    public Player() {
        this.sprite = new Sprite("/res/images/bmw-dummy.png", 16, 32, 10);

        sprite.addAnimation(PlayerAnimation.STAND_DOWN, 0);
        sprite.addAnimation(PlayerAnimation.STAND_UP, 3);
        sprite.addAnimation(PlayerAnimation.STAND_LEFT, 6);
        sprite.addAnimation(PlayerAnimation.STAND_RIGHT, 9);

        sprite.addAnimation(PlayerAnimation.WALK_DOWN, 1, 0, 2, 0);
        sprite.addAnimation(PlayerAnimation.WALK_UP, 4, 3, 5, 3);
        sprite.addAnimation(PlayerAnimation.WALK_LEFT, 7, 6, 8, 6);
        sprite.addAnimation(PlayerAnimation.WALK_RIGHT, 10, 9, 11, 9);

        sprite.playAnimation(PlayerAnimation.STAND_DOWN, true);
    }

    public void update() {
        sprite.update();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getSpeed() {
        return speed;
    }

    public BufferedImage getFrame() {
        return sprite.getCurrentFrame();
    }

    public int getX() {
        return bounds.x;
    }

    public int getY() {
        return bounds.y;
    }

    protected void move(int dx, int dy) {
        bounds.x += dx;
        bounds.y += dy;
    }

    protected void move(Direction d) {
        switch (d) {
            case LEFT:
                bounds.x -= speed;
                sprite.playAnimation(PlayerAnimation.WALK_LEFT, true);
                break;
            case RIGHT:
                bounds.x += speed;
                sprite.playAnimation(PlayerAnimation.WALK_RIGHT, true);
                break;
            case UP:
                bounds.y -= speed;
                sprite.playAnimation(PlayerAnimation.WALK_UP, true);
                break;
            case DOWN:
                bounds.y += speed;
                sprite.playAnimation(PlayerAnimation.WALK_DOWN, true);
                break;
        }
    }

    protected void stop(Direction d) {
        switch (d) {
            case LEFT:
                sprite.playAnimation(PlayerAnimation.STAND_LEFT, false);
                break;
            case RIGHT:
                sprite.playAnimation(PlayerAnimation.STAND_RIGHT, false);
                break;
            case UP:
                sprite.playAnimation(PlayerAnimation.STAND_UP, false);
                break;
            case DOWN:
                sprite.playAnimation(PlayerAnimation.STAND_DOWN, false);
                break;
        }
    }
}
