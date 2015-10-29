package de.hsh.project.bomberman.game.player;

import de.hsh.project.bomberman.game.bomb.Bomb;
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

    public Player() {
        try {
            frame = ImageIO.read(getClass().getResource("/res/images/bm-head-dummy.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {

    }

    public BufferedImage getFrame() {
        return frame;
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
