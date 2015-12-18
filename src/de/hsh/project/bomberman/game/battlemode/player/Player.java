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
import java.awt.image.RescaleOp;
import java.util.ArrayList;

/**
 * Created by taocu on 26.10.2015.
 */
public abstract class Player extends Tile {

    public static int MAX_SPEED = 16;
    public static int MIN_SPEED = 4;

    protected enum Animation implements AnimationID {
        STAND_DOWN,
        STAND_UP,
        STAND_LEFT,
        STAND_RIGHT,

        WALK_DOWN,
        WALK_UP,
        WALK_LEFT,
        WALK_RIGHT,

        DEATH
    }

    private int bombs = 5;
    private int bombRange = 5;
    private int speed = 8;
    private boolean kickAbility;
    private boolean remoteControl;
    private int lifes = 3;
    private ArrayList<PowerUp> powerUps;
    private Surprise temporaryAbility;
    private ArrayList<Bomb> bombQueue = new ArrayList<>();

    private Direction facingDirection;
    private boolean moving;

    private boolean alive = true;

    private int invincible = 0;

    private RescaleOp flickerOp = new RescaleOp(0, 0, null);
    private BufferedImage flicker = new BufferedImage(GameBoard.TILE_SIZE, GameBoard.TILE_SIZE * 2, BufferedImage.TYPE_4BYTE_ABGR);

    protected Point target;

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

        sprite.addAnimation(Animation.DEATH,
                12, 13, 14, 15,
                12, 12, 13, 13, 14, 14, 15, 15,
                12, 12, 12, 13, 13, 13, 14, 14, 14, 15, 15, 15,
                12, 12, 12, 12, 13, 13, 13, 13, 14, 14, 14, 14, 15, 15, 15, 15,
                12, 12, 12, 12, 12, 13, 13, 13, 13, 13, 14, 14, 14, 14, 14, 15, 15, 15, 15, 15,
                15, 15, 15, 15, 15, 15, 15, 15, 15, 15,
                16, 16, 16, 17, 17, 17, 18, 18, 18, 19, 19, 19,
                20, 20, 20, 19, 19, 19, 21, 21, 21, 19, 19, 19, 20, 20, 20, 19, 19, 19, 21, 21, 21,
                19, 19, 19, 19, 19, 19, 19, 19, 19, 19);

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

    public int getLifes() {
        return lifes;
    }

    public boolean isAlive() {
        return alive;
    }

    private void kill() {
        alive = false;
    }

    @Override
    public void update() {
        super.update();
        if (invincible > 0) {
            invincible--;
        }
    }

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
        target = new Point(x, y);
    }

    @Override
    public BufferedImage getFrame() {
        BufferedImage frame = sprite.getCurrentFrame();

        if (invincible % 3 == 1) {
            flickerOp.filter(frame, flicker);
            return flicker;
        }

        return frame;
    }

    @Override
    public void burn() {
        if (invincible == 0 && lifes > 0) {
            lifes--;

            if (lifes == 0) {
                facingDirection = Direction.NONE;
                sprite.playAnimation(Animation.DEATH, 0, this::kill);
            } else {
                invincible = 60;
            }
        }
    }

    protected void dropBomb() {
        if (lifes > 0) {
            int x = getX();
            int y = getY();

            if (currentBoard.getTile(x, y).isEmpty() && bombQueue.size() < bombs) {
                currentBoard.put(new FireBomb(bombRange, bombQueue), x, y);
            }
        }
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isXAligned() {
        return bounds.x % GameBoard.TILE_SIZE == 0;
    }

    public boolean isYAligned() {
        return bounds.y % GameBoard.TILE_SIZE == 0;
    }

    protected void move(Direction direction) {
        facingDirection = direction;
        moving = true;
        switch (direction) {
            case LEFT:
                sprite.playAnimation(Animation.WALK_LEFT, 48 / speed, true);
                break;
            case RIGHT:
                sprite.playAnimation(Animation.WALK_RIGHT, 48 / speed, true);
                break;
            case UP:
                sprite.playAnimation(Animation.WALK_UP, 48 / speed, true);
                break;
            case DOWN:
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
