package de.hsh.project.bomberman.game.battlemode.player;

import de.hsh.project.bomberman.game.battlemode.board.GameBoard;
import de.hsh.project.bomberman.game.battlemode.board.Tile;

import java.awt.*;
import java.util.Random;

/**
 * Created by taocu on 26.10.2015.
 */
public class AIPlayer extends Player {

    private enum Intent {
        EXPAND, HIDE, WAIT
    }

    private Point lastStepped;
    private int level;

    private int wait;

    private Intent intent;

    public AIPlayer(int playerNumber, int level) {
        super(playerNumber);
        this.level = level;
        this.intent = Intent.EXPAND;
        this.wait = 0;
    }

    @Override
    public void update() {
        if (isActive()) {
            checkTarget();

            if (target != null && currentBoard.fieldIsBlocked(target.x, target.y)) target = null;

            if (target != null) {
                moveToTarget();

                if (atTarget()) {
                    target = null;
                    stop(getFacingDirection());
                    switch (intent) {
                        case EXPAND:
                            dropBomb();
                            if (level > 0) intent = Intent.HIDE;
                            break;
                        case HIDE:
                            if (hasRemote()) {
                                remoteAction();
                                intent = Intent.EXPAND;
                            } else {
                                intent = Intent.WAIT;
                            }
                            break;
                    }
                }
            }

            if (intent == Intent.WAIT) {
                if (wait > 0) {
                    wait--;
                    if (wait == 0) {
                        intent = Intent.EXPAND;
                        return;
                    }
                } else if (queueIsEmpty()) {
                    wait = 60;
                }
            }

            if (level > 0) {
                //detectBomb();
            }
        }

        super.update();
    }

    private void checkTarget() {
        switch (level) {
            case 0: determineTargetLevel0(); break;
            case 1: determineTargetLevel1(); break;

        }
    }

    private void determineTargetLevel0() {
        if (target == null) {
            Random r = new Random();

            int dx = r.nextInt(7) - 3;
            int dy = r.nextInt(7) - 3;

            int x = (getX() + dx + GameBoard.GRID_WIDTH) % GameBoard.GRID_WIDTH;
            int y = (getY() + dy + GameBoard.GRID_HEIGHT) % GameBoard.GRID_HEIGHT;

            if (!currentBoard.fieldIsBlocked(x, y)) {
                if (level == 0 || isSafe(x, y, 0)) {
                    target = new Point(x, y);
                }
            }
        } else if (target.x * GameBoard.TILE_SIZE == getLeft() && target.y * GameBoard.TILE_SIZE == getTop()) {
            stop(getFacingDirection());
            dropBomb();
            target = null;
        }
    }

    private void determineTargetLevel1() {
        if (target == null) {
            switch (intent) {
                case EXPAND:
                    target = findNearestChest(getX(), getY(), 0);
                    break;
                case HIDE:
                    target = findNearestHarbor(getX(), getY(), 0);
                    break;
                case WAIT:
                    return;
            }

            if (target == null) determineTargetLevel0();
        }
    }

    private boolean atTarget() {
        return (target != null && getLeft() == target.x * GameBoard.TILE_SIZE && getTop() == target.y * GameBoard.TILE_SIZE);
    }

    private Point findNearestChest(int x, int y, int i) {
        if (outOfBounds(x, y) || i > 4 || currentBoard.fieldIsBlocked(x, y)) return null;

        if (isChest(x - 1, y) || isChest(x + 1, y) || isChest(x, y - 1) || isChest(x, y + 1)) {
            return new Point(x, y);
        }

        Point p;

        p = findNearestChest(x - 1, y, i + 1);
        if (p != null) return p;

        p = findNearestChest(x + 1, y, i + 1);
        if (p != null) return p;

        p = findNearestChest(x, y - 1, i + 1);
        if (p != null) return p;

        p = findNearestChest(x, y + 1, i + 1);
        if (p != null) return p;

        return null;
    }

    private boolean isChest(int x, int y) {
        return currentBoard.getTile(x, y).isBlock();
    }

    private Point findNearestHarbor(int x, int y, int i) {
        if (outOfBounds(x, y) || i > 5 || isIgnorable(x, y)) return null;

        if (isSafe(x, y, 0)) return new Point(x, y);

        Point p;

        p = findNearestHarbor(x - 1, y, i + 1);
        if (p != null) return p;

        p = findNearestHarbor(x + 1, y, i + 1);
        if (p != null) return p;

        p = findNearestHarbor(x, y - 1, i + 1);
        if (p != null) return p;

        p = findNearestHarbor(x, y + 1, i + 1);
        if (p != null) return p;

        return null;
    }

    private void detectBomb() {
        int x = getX();
        int y = getY();

        for (int i = 1; i <= 3; i++) {
            if (isDangerous(x - i, y) || isDangerous(x + i, y) || isDangerous(x, y - i) || isDangerous(x, y + i)) {
                target = null;
                return;
            }
        }
    }

    private boolean isIgnorable(int x, int y) {
        return currentBoard.fieldIsBlocked(x, y) && !currentBoard.getTile(x, y).isBomb();
    }

    private boolean isDangerous(int x, int y) {
        if (!outOfBounds(x, y)) {
            Tile here = currentBoard.getTile(x, y);
            return here.isBomb() || here.isBlast();
        }

        return false;
    }

    private boolean isSafe(int x, int y, int i) {
        if (i == 4 || outOfBounds(x, y)) return true;

        if (isDangerous(x, y)) return false;

        boolean safe = true;

        if (!isIgnorable(x - 1, y)) safe = isSafe(x - 1, y, i + 1);
        if (!safe) return false;

        if (!isIgnorable(x + 1, y)) safe = isSafe(x + 1, y, i + 1);
        if (!safe) return false;

        if (!isIgnorable(x, y - 1)) safe = isSafe(x, y - 1, i + 1);
        if (!safe) return false;

        if (!isIgnorable(x, y + 1)) safe = isSafe(x, y + 1, i + 1);
        return safe;
    }

    private boolean outOfBounds(int x, int y) {
        return x < 1 || x > GameBoard.GRID_WIDTH - 2 || y < 1 || y > GameBoard.GRID_HEIGHT - 2;
    }

    private void moveToTarget() {
        move(findPath());
    }

    Direction findPath() {
        return findPath(getX(), getY());
    }

    Direction findPath(int x, int y) {
        if (isIgnorable(x, y)) return Direction.NONE;

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
