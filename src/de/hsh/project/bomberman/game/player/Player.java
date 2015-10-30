package de.hsh.project.bomberman.game.player;

import de.hsh.project.bomberman.game.bomb.Bomb;
import de.hsh.project.bomberman.game.gfx.Sprite;
import de.hsh.project.bomberman.game.powerup.PowerUp;
import de.hsh.project.bomberman.game.powerup.Surprise;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by taocu on 26.10.2015.
 */
public abstract class Player {

    private Point position = new Point(0, 0);
    private int bombs;
    private int bombRange;
    private int speed;
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

    public BufferedImage getFrame() {
        return sprite.getCurrentFrame();
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    protected void move(int dx, int dy) {
        position.x += dx;
        position.y += dy;
    }
}
