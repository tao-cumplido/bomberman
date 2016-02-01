package de.hsh.project.bomberman.game.battlemode.player;

import de.hsh.project.bomberman.game.battlemode.board.GameBoard;
import de.hsh.project.bomberman.game.settings.SettingsTyp;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by taocu on 26.10.2015.
 */
public class HumanPlayer extends Player {

    private Key[] directionKeys;
    private Key[] allKeys;

    private Direction intendedDirection = Direction.DOWN;

    public HumanPlayer(int playerNumber, InputMap inputMap, ActionMap actionMap, Map<SettingsTyp, Integer> settings) {
        super(playerNumber);
        directionKeys = new Key[] {
                new Key(settings.get(SettingsTyp.DIRECTION_LEFT), Direction.LEFT, this::pressDirectionAction, this::releaseDirectionAction),
                new Key(settings.get(SettingsTyp.DIRECTION_RIGHT), Direction.RIGHT, this::pressDirectionAction, this::releaseDirectionAction),
                new Key(settings.get(SettingsTyp.DIRECTION_UP), Direction.UP, this::pressDirectionAction, this::releaseDirectionAction),
                new Key(settings.get(SettingsTyp.DIRECTION_DOWN), Direction.DOWN, this::pressDirectionAction, this::releaseDirectionAction)
        };

        allKeys = new Key[] {
                directionKeys[0], directionKeys[1], directionKeys[2], directionKeys[3],
                new Key(settings.get(SettingsTyp.SETTINGS_BOMB), this::pressDropAction, this::releaseDropAction),
                new Key(settings.get(SettingsTyp.SETTING_REMOTECONTROL), this::pressRemoteAction, this::releaseRemoteAction)
        };

        for (Key key : allKeys) {
            inputMap.put(KeyStroke.getKeyStroke(key.getKeyCode(), 0), key.actionKeyPressed());
            actionMap.put(key.actionKeyPressed(), new KeyAction((event) -> key.press()));

            inputMap.put(KeyStroke.getKeyStroke(key.getKeyCode(), 0, true), key.actionKeyReleased());
            actionMap.put(key.actionKeyReleased(), new KeyAction((event) -> key.release()));
        }
    }

    private void pressDirectionAction(Key key) {
        if (isActive()) {
            target = null;
            intendedDirection = key.getDirection();
        }
    }

    private void releaseDirectionAction(Key key) {
        if (isActive()) {
            target = null;
            for (Key other : directionKeys) {
                if (other != key && other.isPressed()) {
                    intendedDirection = other.getDirection();
                }
            }
        }
    }

    private void pressDropAction(Key key) {
        if (!key.isPressed() && isActive()) {
            dropBomb();
        }
    }

    private void releaseDropAction(Key key) {}

    private void pressRemoteAction(Key key) {
        if (!key.isPressed() && isActive()) {
            remoteAction();
        }
    }

    private void releaseRemoteAction(Key key) {

    }

    private boolean anyDirectionPressed() {
        for (Key key : directionKeys) {
            if (key.isPressed()) return true;
        }

        return false;
    }

    private int getNextX() {
        switch (intendedDirection) {
            case LEFT:
                return (getLeft() - getSpeed()) / GameBoard.TILE_SIZE;
            case RIGHT:
                return (getRight() + getSpeed()) / GameBoard.TILE_SIZE;
            default:
                return getLeft() / GameBoard.TILE_SIZE;
        }
    }

    private int getNextY() {
        switch (intendedDirection) {
            case UP:
                return (getTop() - getSpeed()) / GameBoard.TILE_SIZE;
            case DOWN:
                return (getBottom() + getSpeed()) / GameBoard.TILE_SIZE;
            default:
                return getTop() / GameBoard.TILE_SIZE;
        }
    }

    @Override
    public void update() {
        if (isActive()) {
            if (anyDirectionPressed()) {
                move(intendedDirection);
            } else {
                stop(intendedDirection);
                if (currentBoard.isFrozenFloor(getX(), getY())) {
                    moveTo(getFacingDirection());
                }
            }
        }
        super.update();
    }

    @Override
    protected void move(Direction direction) {
        super.move(direction);
        moveTo(direction);
    }

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
        target = new Point(x, y);
    }

    public void moveTo(Direction direction) {
        int x = getX();
        int y = getY();
        switch (direction) {
            case LEFT:
                if (isYAligned()) {
                    if (!currentBoard.fieldIsBlocked(getNextX(), y)) {
                        translateX(-getSpeed());
                    } else {
                        alignX(Direction.LEFT);
                    }
                } else if (target == null) {
                    if (getTop() < y * GameBoard.TILE_SIZE) {
                        setTarget(x - 1, y, -1, true);
                    } else {
                        setTarget(x - 1, y, 1, true);
                    }
                } else if (!currentBoard.fieldIsBlocked(target.x, target.y)) {
                    moveAroundCorner(getTop(), target.y, this::alignY, Direction.UP, Direction.DOWN);
                } break;

            case RIGHT:
                if (isYAligned()) {
                    if (!currentBoard.fieldIsBlocked(getNextX(), y)) {
                        translateX(getSpeed());
                    } else {
                        alignX(Direction.RIGHT);
                    }
                } else if (target == null) {
                    if (getTop() < y * GameBoard.TILE_SIZE) {
                        setTarget(x + 1, y, -1, true);
                    } else {
                        setTarget(x + 1, y, 1, true);
                    }
                } else if (!currentBoard.fieldIsBlocked(target.x, target.y)) {
                    moveAroundCorner(getTop(), target.y, this::alignY, Direction.UP, Direction.DOWN);
                } break;

            case UP:
                if (isXAligned()) {
                    if (!currentBoard.fieldIsBlocked(x, getNextY())) {
                        translateY(-getSpeed());
                    } else {
                        alignY(Direction.UP);
                    }
                } else if (target == null) {
                    if (getLeft() < x * GameBoard.TILE_SIZE) {
                        setTarget(x, y - 1, -1, false);
                    } else {
                        setTarget(x, y - 1, 1, false);
                    }
                } else if (!currentBoard.fieldIsBlocked(target.x, target.y)) {
                    moveAroundCorner(getLeft(), target.x, this::alignX, Direction.LEFT, Direction.RIGHT);
                } break;

            case DOWN:
                if (isXAligned()) {
                    if (!currentBoard.fieldIsBlocked(x, getNextY())) {
                        translateY(getSpeed());
                    } else {
                        alignY(Direction.DOWN);
                    }
                } else if (target == null) {
                    if (getLeft() < x * GameBoard.TILE_SIZE) {
                        setTarget(x, y + 1, -1, false);
                    } else {
                        setTarget(x, y + 1, 1, false);
                    }
                } else if (!currentBoard.fieldIsBlocked(target.x, target.y)) {
                    moveAroundCorner(getLeft(), target.x, this::alignX, Direction.LEFT, Direction.RIGHT);
                } break;
        }
    }

    private void setTarget(int x, int y, int d, boolean horizontal) {
        int xx = horizontal ? x : x + d;
        int yy = horizontal ? y + d : y;
        if (!currentBoard.fieldIsBlocked(xx, yy)) {
            target = new Point(xx, yy);
        } else if (!currentBoard.fieldIsBlocked(x, y)) {
            target = new Point(x, y);
        }
    }

    private void moveAroundCorner(int playerCoordinate, int targetCoordinate, Consumer<Direction> align, Direction neg, Direction pos) {
        if (playerCoordinate > targetCoordinate * GameBoard.TILE_SIZE) {
            align.accept(neg);
        } else {
            align.accept(pos);
        }
    }

    @Override
    public boolean isHuman() {
        return true;
    }
}
