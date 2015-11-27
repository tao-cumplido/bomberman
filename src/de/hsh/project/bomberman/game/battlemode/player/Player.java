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

    public enum Direction {
        NONE, LEFT, RIGHT, UP, DOWN;

        public Direction[] allWithout(Direction direction) {
            switch (direction) {
                case LEFT:  return new Direction[] {RIGHT, UP, DOWN};
                case RIGHT: return new Direction[] {LEFT, UP, DOWN};
                case UP:    return new Direction[] {LEFT, RIGHT, DOWN};
                case DOWN:  return new Direction[] {LEFT, RIGHT, UP};
                default:    return new Direction[] {LEFT, RIGHT, UP, DOWN};
            }
        }
    }

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
    private int bombRange;
    private int speed = 8;
    private boolean kickAbility;
    private boolean remoteControl;
    private int lifes;
    private ArrayList<PowerUp> powerUps;
    private Surprise temporaryAbility;
    private ArrayList<Bomb> bombQueue;

    private BufferedImage frame;

    public Player() {
        super(1, 1, false);

        this.sprite = new Sprite("/res/images/bmw-dummy.png", GameBoard.TILE_SIZE, GameBoard.TILE_SIZE * 2, 6);

        sprite.addAnimation(Animation.STAND_DOWN, 0);
        sprite.addAnimation(Animation.STAND_UP, 3);
        sprite.addAnimation(Animation.STAND_LEFT, 6);
        sprite.addAnimation(Animation.STAND_RIGHT, 9);

        sprite.addAnimation(Animation.WALK_DOWN, 0, 1, 0, 2);
        sprite.addAnimation(Animation.WALK_UP, 3, 4, 3, 5);
        sprite.addAnimation(Animation.WALK_LEFT, 6, 7, 6, 8);
        sprite.addAnimation(Animation.WALK_RIGHT, 9, 10, 9, 11);

        sprite.playAnimation(Animation.STAND_DOWN, true);
    }

    protected void dropBomb() {
        int gridX = (getLeft() + GameBoard.TILE_SIZE / 2) / GameBoard.TILE_SIZE;
        int gridY = (getTop() + GameBoard.TILE_SIZE / 2) / GameBoard.TILE_SIZE;
        BOARD.put(new FireBomb(gridX, gridY, bombRange));
    }

    public int getSpeed() {
        return speed;
    }

    protected void move(Direction d) {
        switch (d) {
            case LEFT:
                translateX(-speed);
                sprite.playAnimation(Animation.WALK_LEFT, true);
                break;
            case RIGHT:
                translateX(speed);
                sprite.playAnimation(Animation.WALK_RIGHT, true);
                break;
            case UP:
                translateY(-speed);
                sprite.playAnimation(Animation.WALK_UP, true);
                break;
            case DOWN:
                translateY(speed);
                sprite.playAnimation(Animation.WALK_DOWN, true);
                break;
        }
    }

    protected void stop(Direction d) {
        switch (d) {
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
