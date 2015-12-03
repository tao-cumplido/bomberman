package de.hsh.project.bomberman.game.battlemode.player;

import de.hsh.project.bomberman.game.battlemode.board.GameBoard;
import de.hsh.project.bomberman.game.battlemode.board.Tile;
import de.hsh.project.bomberman.game.battlemode.bomb.Bomb;
import de.hsh.project.bomberman.game.battlemode.bomb.FireBomb;
import de.hsh.project.bomberman.game.battlemode.gfx.AnimationID;
import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;
import de.hsh.project.bomberman.game.battlemode.powerup.PowerUp;
import de.hsh.project.bomberman.game.battlemode.powerup.Surprise;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by taocu on 26.10.2015.
 */
public abstract class Player extends Tile {

    public static int MAX_SPEED = 16;
    public static int MIN_SPEED = 4;

    private enum Animation implements AnimationID {
        STAND_DOWN,
        STAND_UP,
        STAND_LEFT,
        STAND_RIGHT,

        WALK_DOWN,
        WALK_UP,
        WALK_LEFT,
        WALK_RIGHT;
    }

    private int bombs;
    private int bombRange = 3;
    private int speed = 8;
    private boolean kickAbility;
    private boolean remoteControl;
    private int lifes;
    private ArrayList<PowerUp> powerUps;
    private Surprise temporaryAbility;
    private ArrayList<Bomb> bombQueue;

    private BufferedImage frame;

    private Direction facingDirection;
    private boolean moving;

    public Player(int playerNumber) {
        super(false);

        this.sprite = new Sprite("/res/images/bomberman/bomber" + playerNumber + ".png", GameBoard.TILE_SIZE, GameBoard.TILE_SIZE * 2);

        sprite.addAnimation(Animation.STAND_DOWN, 0);
        sprite.addAnimation(Animation.STAND_UP, 3);
        sprite.addAnimation(Animation.STAND_LEFT, 6);
        sprite.addAnimation(Animation.STAND_RIGHT, 9);

        sprite.addAnimation(Animation.WALK_DOWN, 0, 1, 0, 2);
        sprite.addAnimation(Animation.WALK_UP, 3, 4, 3, 5);
        sprite.addAnimation(Animation.WALK_LEFT, 6, 7, 6, 8);
        sprite.addAnimation(Animation.WALK_RIGHT, 9, 10, 9, 11);

        stop(Direction.DOWN);
    }

    public Direction getFacingDirection() {
        return facingDirection;
    }

    public boolean isMoving() {
        return moving;
    }

    public void translateX(int delta) {
        bounds.x += delta;
    }

    public void translateY(int delta) {
        bounds.y += delta;
    }

    @Override
    public int getX() {
        return (getLeft() + GameBoard.TILE_SIZE / 2) / GameBoard.TILE_SIZE;
    }

    @Override
    public int getY() {
        return (getTop() + GameBoard.TILE_SIZE / 2) / GameBoard.TILE_SIZE;
    }

    protected void dropBomb() {
        BOARD.put(new FireBomb(bombRange), getX(), getY());
    }

    public int getSpeed() {
        return speed;
    }

    protected void move(Direction direction) {
        facingDirection = direction;
        moving = true;
        switch (direction) {
            case LEFT:
                translateX(-speed);
                sprite.playAnimation(Animation.WALK_LEFT, 48 / speed, true);
                break;
            case RIGHT:
                translateX(speed);
                sprite.playAnimation(Animation.WALK_RIGHT, 48 / speed, true);
                break;
            case UP:
                translateY(-speed);
                sprite.playAnimation(Animation.WALK_UP, 48 / speed, true);
                break;
            case DOWN:
                translateY(speed);
                sprite.playAnimation(Animation.WALK_DOWN, 48 / speed, true);
                break;
        }
    }

    protected void stop(Direction direction) {
        facingDirection = direction;
        moving = false;
        switch (direction) {
            case LEFT:
                sprite.playAnimation(Animation.STAND_LEFT, false);
                break;
            case RIGHT:
                sprite.playAnimation(Animation.STAND_RIGHT, false);
                break;
            case UP:
                sprite.playAnimation(Animation.STAND_UP, false);
                break;
            case DOWN:
                sprite.playAnimation(Animation.STAND_DOWN, false);
                break;
        }
    }
}
