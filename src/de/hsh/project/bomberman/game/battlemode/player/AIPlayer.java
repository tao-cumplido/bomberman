package de.hsh.project.bomberman.game.battlemode.player;

import de.hsh.project.bomberman.game.battlemode.board.GameBoard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by taocu on 26.10.2015.
 */
public class AIPlayer extends Player {

    private Point lastStepped;

    public AIPlayer(int playerNumber) {
        super(playerNumber);
    }

    @Override
    public void update() {
        if (isActive()) {
            checkTarget();

            if (target != null) {
                moveToTarget();
            }
        }

        super.update();
    }

    private void checkTarget() {
        if (target == null) {
            Random r = new Random();

            int dx = r.nextInt(7) - 3;
            int dy = r.nextInt(7) - 3;

            int x = (getX() + dx + GameBoard.GRID_WIDTH) % GameBoard.GRID_WIDTH;
            int y = (getY() + dy + GameBoard.GRID_HEIGHT) % GameBoard.GRID_HEIGHT;

            if (!currentBoard.fieldIsBlocked(x, y)) {
                target = new Point(x, y);
            }
        } else if (target.x * GameBoard.TILE_SIZE == getLeft() && target.y * GameBoard.TILE_SIZE == getTop()) {
            stop(getFacingDirection());
            dropBomb();
            target = null;
        }
    }

    private void moveToTarget() {
        move(findPath());
    }

    Direction findPath() {
        return findPath(getX(), getY());
    }

    Direction findPath(int x, int y) {
        if (currentBoard.fieldIsBlocked(x, y) && !currentBoard.getTile(getX(), getY()).isBomb()) return Direction.NONE;

        if (target.x == x - 1 && target.y == y) return Direction.LEFT;
        if (target.x == x + 1 && target.y == y) return Direction.RIGHT;
        if (target.x == x && target.y == y - 1) return Direction.UP;
        if (target.x == x && target.y == y + 1) return Direction.DOWN;
        if (target.x == x && target.y == y) return getFacingDirection();

        if (target.x < x && !(new Point(x - 1, y).equals(lastStepped)) && findPath(x - 1, y) != Direction.NONE) {
            return Direction.LEFT;
        }
        if (target.x > x && !(new Point(x + 1, y).equals(lastStepped)) && findPath(x + 1, y) != Direction.NONE) {
            return Direction.RIGHT;
        }
        if (target.x == x) {
            int dy = (target.y < y) ? -1 : 1;
            if (!currentBoard.fieldIsBlocked(x - 1, y) && !currentBoard.fieldIsBlocked(x - 1, y + dy) && findPath(x - 1, y + dy) != Direction.NONE) {
                lastStepped = new Point(x, y);
                return Direction.LEFT;
            }
            if (!currentBoard.fieldIsBlocked(x + 1, y) && !currentBoard.fieldIsBlocked(x + 1, y + dy) && findPath(x + 1, y + dy) != Direction.NONE) {
                lastStepped = new Point(x, y);
                return Direction.RIGHT;
            }
        }

        if (target.y < y && findPath(x, y - 1) != Direction.NONE) {
            return Direction.UP;
        }
        if (target.y > y && findPath(x, y + 1) != Direction.NONE) {
            return Direction.DOWN;
        }
        if (target.y == y) {
            int dx = (target.x < x) ? -1 : 1;
            if (!currentBoard.fieldIsBlocked(x, y - 1) && !currentBoard.fieldIsBlocked(x + dx, y - 1) && findPath(x + dx, y - 1) != Direction.NONE) {
                lastStepped = new Point(x, y);
                return Direction.UP;
            }
            if (!currentBoard.fieldIsBlocked(x, y + 1) && !currentBoard.fieldIsBlocked(x + dx, y + 1) && findPath(x + dx, y + 1) != Direction.NONE) {
                lastStepped = new Point(x, y);
                return Direction.DOWN;
            }
        }

        return Direction.NONE;
    }

    @Override
    protected void move(Direction direction) {
        int speed, delta, x, y;
        boolean canMove, align;
        switch (direction) {
            case NONE:
                target = null;
                return;
            case LEFT:
                if (isYAligned()) {
                    x = getLeft();
                    y = getY();
                    speed = getSpeed();
                    canMove = !currentBoard.fieldIsBlocked(GameBoard.pixel2Grid(x - speed), y);
                    //delta = canMove ? speed : x % GameBoard.TILE_SIZE;
                    align = GameBoard.pixel2Grid(x - speed) < GameBoard.pixel2Grid(x) && !isXAligned();
                    delta = (align || !canMove) ? x % GameBoard.TILE_SIZE : speed;
                    translateX(-delta);
                } else {
                    move(getFacingDirection());
                    return;
                }
                break;
            case RIGHT:
                if (isYAligned()) {
                    x = getRight() + 1;
                    y = getY();
                    speed = getSpeed();
                    canMove = !currentBoard.fieldIsBlocked(GameBoard.pixel2Grid(x + speed), y);
                    //delta = canMove ? speed : Math.floorMod(-x, GameBoard.TILE_SIZE);
                    align = GameBoard.pixel2Grid(x + speed) > GameBoard.pixel2Grid(x) && !isXAligned();
                    delta = (align || !canMove) ? Math.floorMod(-x, GameBoard.TILE_SIZE) : speed;
                    translateX(delta);
                } else {
                    move(getFacingDirection());
                    return;
                }
                break;
            case UP:
                if (isXAligned()) {
                    x = getX();
                    y = getTop();
                    speed = getSpeed();
                    canMove = !currentBoard.fieldIsBlocked(x, GameBoard.pixel2Grid(y - speed));
                    //delta = canMove ? speed : y % GameBoard.TILE_SIZE;
                    align = GameBoard.pixel2Grid(y - speed) < GameBoard.pixel2Grid(y) && !isYAligned();
                    delta = (align || !canMove) ? y % GameBoard.TILE_SIZE : speed;
                    translateY(-delta);
                } else {
                    move(getFacingDirection());
                    return;
                }
                break;
            case DOWN:
                if (isXAligned()) {
                    x = getX();
                    y = getBottom() + 1;
                    speed = getSpeed();
                    canMove = !currentBoard.fieldIsBlocked(x, GameBoard.pixel2Grid(y + speed));
                    //delta = canMove ? speed : Math.floorMod(-y, GameBoard.TILE_SIZE);
                    align = GameBoard.pixel2Grid(y + speed) > GameBoard.pixel2Grid(y) && !isYAligned();
                    delta = (align || !canMove) ? Math.floorMod(-y, GameBoard.TILE_SIZE) : speed;
                    translateY(delta);
                } else {
                    move(getFacingDirection());
                    return;
                }
                break;
        }

        super.move(direction);
    }
}
