package de.hsh.project.bomberman.game.battlemode.player;

import de.hsh.project.bomberman.game.Game;
import de.hsh.project.bomberman.game.battlemode.board.GameBoard;
import de.hsh.project.bomberman.game.battlemode.board.Tile;
import de.hsh.project.bomberman.game.battlemode.bomb.Bomb;
import de.hsh.project.bomberman.game.battlemode.bomb.FireBomb;
import de.hsh.project.bomberman.game.battlemode.bomb.IceBomb;
import de.hsh.project.bomberman.game.battlemode.bomb.Trigger;
import de.hsh.project.bomberman.game.battlemode.gfx.AnimationID;
import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;
import de.hsh.project.bomberman.game.battlemode.powerup.PowerUp;
import de.hsh.project.bomberman.game.settings.Settings;
import de.hsh.project.bomberman.game.settings.SettingsTyp;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by taocu on 26.10.2015.
 */
public abstract class Player extends Tile {

    public static int MAX_SPEED = 20;
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

    private int bombs;
    private int bombRange;
    private int speed;
    //private boolean kickAbility; // won't implement
    private Trigger bombTrigger;
    private boolean dropsIceBombs;
    private boolean dropsBombs;
    private boolean dropsRandomBombs;
    private int lifes;
    private ArrayList<PowerUp> powerUps;
    private ArrayList<Bomb> bombQueue;

    private int resetCounter;

    private Direction facingDirection;
    private boolean moving;

    private boolean alive = true;
    private int frozen = 0;
    private BufferedImage frozenFrame = new BufferedImage(GameBoard.TILE_SIZE, GameBoard.TILE_SIZE * 2, BufferedImage.TYPE_4BYTE_ABGR);
    private RescaleOp frozenOp = new RescaleOp(1, 0, null);

    private int invincible = 0;

    private RescaleOp flickerOp = new RescaleOp(0, 0, null);
    private BufferedImage flicker = new BufferedImage(GameBoard.TILE_SIZE, GameBoard.TILE_SIZE * 2, BufferedImage.TYPE_4BYTE_ABGR);

    protected Point target;

    private int playerNumber;

    public Player(int playerNumber) {
        super(false, GameBoard.TILE_SIZE);

        this.sprite = new Sprite("/res/images/bomberman/bomber" + playerNumber + ".png", GameBoard.TILE_SIZE, GameBoard.TILE_SIZE * 2);

        sprite.addAnimation(Animation.STAND_DOWN, 0);
        sprite.addAnimation(Animation.STAND_UP, 1);
        sprite.addAnimation(Animation.STAND_LEFT, 2);
        sprite.addAnimation(Animation.STAND_RIGHT, 3);

        sprite.addAnimation(Animation.WALK_DOWN, 0);
        sprite.addAnimation(Animation.WALK_UP, 1);
        sprite.addAnimation(Animation.WALK_LEFT, 2);
        sprite.addAnimation(Animation.WALK_RIGHT, 3);

        sprite.addAnimation(Animation.DEATH, 4, 4, 5, 4, 4, 5, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 7, 8, 8, 8, 8, 8);

        stop(Direction.DOWN);

        this.playerNumber = playerNumber + 1;

        this.bombQueue = new ArrayList<>();
        this.powerUps = new ArrayList<>();
        this.lifes = Settings.getBasicSetting().get(SettingsTyp.LIFE);
        resetStats();
    }

    public void resetStats() {
        bombs = 1;
        bombRange = 1;
        speed = 8;
        bombTrigger = Trigger.TIME;

        dropsBombs = true;
        dropsIceBombs = false;
        dropsRandomBombs = false;
        resetCounter = -1;

        for (PowerUp p : powerUps) {
            p.affect(this);
        }
    }

    public int getPlayerNumber() {
        return playerNumber;
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

    public void alignX(Direction direction) {
        int speed = getSpeed();
        int delta = bounds.x % GameBoard.TILE_SIZE;
        if (delta != 0) {
            switch (direction) {
                case LEFT:
                    if (delta > speed) {
                        translateX(-speed);
                        return;
                    }
                    break;
                case RIGHT:
                    if (GameBoard.TILE_SIZE - delta > speed) {
                        translateX(speed);
                        return;
                    }
            }
        }
        bounds.x = getX() * GameBoard.TILE_SIZE;
    }

    public void alignY(Direction direction) {
        int speed = getSpeed();
        int delta = bounds.y % GameBoard.TILE_SIZE;
        if (delta != 0) {
            switch (direction) {
                case UP:
                    if (delta > speed) {
                        translateY(-speed);
                        return;
                    }
                    break;
                case DOWN:
                    if (GameBoard.TILE_SIZE - delta > speed) {
                        translateY(speed);
                        return;
                    }
            }
        }
        bounds.y = getY() * GameBoard.TILE_SIZE;
    }

    @Override
    public int getX() {
        //return (getLeft() + GameBoard.TILE_SIZE / 2) / GameBoard.TILE_SIZE;
        return GameBoard.pixel2Grid(getLeft() + GameBoard.TILE_SIZE / 2);
    }

    @Override
    public int getY() {
        //return (getTop() + GameBoard.TILE_SIZE / 2) / GameBoard.TILE_SIZE;
        return GameBoard.pixel2Grid(getTop() + GameBoard.TILE_SIZE / 2);
    }

    public int getLifes() {
        return lifes;
    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isFrozen() {
        return frozen > 0;
    }

    private void kill() {
        alive = false;
        currentBoard.sprayPowerUps(powerUps);
    }

    public int score() {
        int score = getLifes() * 1000;

        for (PowerUp p : powerUps) {
            score += p.score();
        }

        return score;
    }

    public boolean isHuman() {
        return false;
    }

    @Override
    public void update() {
        super.update();

        if (isActive()) {
            if (invincible > 0) {
                invincible--;
            }

            if (resetCounter > 0) {
                resetCounter--;
                if (dropsRandomBombs && resetCounter % Game.FPS == 0) {
                    if (new Random().nextDouble() > 0.75) {
                        dropBomb(getX(), getY());
                    }
                }
            } else if (resetCounter == 0) {
                resetStats();
            }
        }

        if (frozen > 0) {
            frozen -= 2;
        } else if (frozen < 0) {
            setActive(true);
            setSolid(false);
            stop(facingDirection);
            frozen = 0;
            currentBoard.remove(getX(), getY());
        }
    }

    @Override
    public BufferedImage getFrame() {
        if (frozen > 0) return null; //frozenFrame;

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
            if (frozen > 0) frozen = 1;

            lifes--;

            if (lifes == 0) {
                facingDirection = Direction.NONE;
                sprite.playAnimation(Animation.DEATH, 2, this::kill);
                setActive(false);
                //alive = false;
            } else {
                invincible = 2 * Game.FPS;
            }
        }
    }

    @Override
    public void freeze() {
        if (invincible == 0 && lifes > 0) {
            int x = getX();
            int y = getY();

            setX(x);
            setY(y);

            currentBoard.remove(x, y);
            currentBoard.put(new IceBlock(this), getX(), getY());

            frozen = 479;
            //frozenFrame = sprite.getCurrentFrame();
            setActive(false);
            setSolid(true);
        }
    }

    protected void dropBomb() {
        if (dropsBombs && !dropsRandomBombs) {
            dropBomb(getX(), getY());
        }
    }

    private void dropBomb(int x, int y) {
        if (currentBoard.getTile(x, y).isEmpty() && bombQueue.size() < bombs) {
            Bomb bomb = dropsIceBombs ? new IceBomb(bombRange, bombQueue, bombTrigger) : new FireBomb(bombRange, bombQueue, bombTrigger);
            //Bomb bomb = (playerNumber == 1) ? new IceBomb(bombRange, bombQueue, bombTrigger) : new FireBomb(bombRange, bombQueue, bombTrigger);
            currentBoard.put(bomb, x, y);
        }
    }

    protected boolean hasRemote() {
        return bombTrigger == Trigger.REMOTE;
    }

    protected void remoteAction() {
        if (bombTrigger == Trigger.REMOTE && !bombQueue.isEmpty()) {
            bombQueue.get(0).detonate();
        }
    }

    protected boolean queueIsEmpty() {
        return bombQueue.size() == 0;
    }

    public void setBombTrigger(Trigger trigger) {
        bombTrigger = trigger;
    }

    public void raiseBombCount() {
        bombs++;
    }

    public void raiseBombRange() {
        bombRange++;
    }

    public void raiseSpeed() {
        if (speed < MAX_SPEED) {
            speed += 2;
        }
    }

    public void maximizeSpeed() {
        speed = MAX_SPEED + 4;
        resetCounter = 15 * Game.FPS;
    }

    public void minimizeSpeed() {
        speed = MIN_SPEED;
        resetCounter = 15 * Game.FPS;
    }

    public void noBombs() {
        dropsBombs = false;
        resetCounter = 15 * Game.FPS;
    }

    public void iceBombs() {
        dropsIceBombs = true;
        resetCounter = 20 * Game.FPS;
    }

    public void randomBombs() {
        dropsRandomBombs = true;
        resetCounter = 15 * Game.FPS;
    }

    public void makeInvincible() {
        invincible = 5 * Game.FPS;
    }

    public void maximizeRange() {
        bombRange = 100;
        resetCounter = 15 * Game.FPS;
    }

    public void increaseLifes() {
        lifes += 1;
    }

    public void addPowerUp(PowerUp powerUp) {
        if (!powerUp.isTemporary()) {
            powerUps.add(powerUp);
        }
        powerUp.affect(this);
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
