package de.hsh.project.bomberman.game.battlemode.player;

import de.hsh.project.bomberman.game.battlemode.board.GameBoard;
import de.hsh.project.bomberman.game.battlemode.board.Tile;
import de.hsh.project.bomberman.game.settings.SettingsTyp;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Created by taocu on 26.10.2015.
 */
public class HumanPlayer extends Player {

    private Key keyLeft;
    private Key keyRight;
    private Key keyUp;
    private Key keyDown;
    private Key keyDropBomb;
    private Key keyTriggerBomb;

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
                new Key(settings.get(SettingsTyp.SETTINGS_BOMB), this::pressDropAction, this::releaseDropAction)
        };

        for (Key key : allKeys) {
            inputMap.put(KeyStroke.getKeyStroke(key.getKeyCode(), 0), key.actionKeyPressed());
            actionMap.put(key.actionKeyPressed(), new KeyAction((event) -> key.press()));

            inputMap.put(KeyStroke.getKeyStroke(key.getKeyCode(), 0, true), key.actionKeyReleased());
            actionMap.put(key.actionKeyReleased(), new KeyAction((event) -> key.release()));
        }
    }

    private void pressDirectionAction(Key key) {
        target = null;
        intendedDirection = key.getDirection();
    }

    private void releaseDirectionAction(Key key) {
        target = null;
        for (Key other : directionKeys) {
            if (other != key && other.isPressed()) {
                intendedDirection = other.getDirection();
            }
        }
    }

    private void pressDropAction(Key key) {
        if (!key.isPressed()) {
            dropBomb();
        }
    }

    private void releaseDropAction(Key key) {}

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
        if (getLifes() > 0) {
            if (anyDirectionPressed()) {
                move(intendedDirection);
            } else {
                stop(intendedDirection);
            }
        }
        super.update();
    }

    // TODO: try to remove redundancy
    @Override
    protected void move(Direction direction) {
        super.move(direction);
        int x = getX();
        int y = getY();
        boolean onBomb = currentBoard.getTile(x, y).isBomb();
        switch (direction) {
            case LEFT:
                if (isYAligned()) {
                    if (!currentBoard.fieldIsBlocked(getNextX(), y) || (onBomb && !isXAligned())) {
                        translateX(-getSpeed());
                    }
                } else {
                    if (target == null) {
                        if (getTop() < y * GameBoard.TILE_SIZE) {
                            if (!currentBoard.fieldIsBlocked(x - 1, y - 1)) {
                                setTarget(x - 1, y - 1);
                            } else if (!currentBoard.fieldIsBlocked(x - 1, y)) {
                                setTarget(x - 1, y);
                            }
                        } else {
                            if (!currentBoard.fieldIsBlocked(x - 1, y + 1)) {
                                setTarget(x - 1, y + 1);
                            } else if (!currentBoard.fieldIsBlocked(x - 1, y)) {
                                setTarget(x - 1, y);
                            }
                        }
                    } else if (!currentBoard.fieldIsBlocked(target.x, target.y)) {
                        if (getTop() > target.y * GameBoard.TILE_SIZE) {
                            translateY(-getSpeed());
                        } else {
                            translateY(getSpeed());
                        }
                    }
                }
                break;
            case RIGHT:
                if (isYAligned()) {
                    if (!currentBoard.fieldIsBlocked(getNextX(), y) || (onBomb && !isXAligned())) {
                        translateX(getSpeed());
                    }
                } else {
                    if (target == null) {
                        if (getTop() < y * GameBoard.TILE_SIZE) {
                            if (!currentBoard.fieldIsBlocked(x + 1, y - 1)) {
                                setTarget(x + 1, y - 1);
                            } else if (!currentBoard.fieldIsBlocked(x + 1, y)) {
                                setTarget(x + 1, y);
                            }
                        } else {
                            if (!currentBoard.fieldIsBlocked(x + 1, y + 1)) {
                                setTarget(x + 1, y + 1);
                            } else if (!currentBoard.fieldIsBlocked(x + 1, y)) {
                                setTarget(x + 1, y);
                            }
                        }
                    } else if (!currentBoard.fieldIsBlocked(target.x, target.y)) {
                        if (getTop() > target.y * GameBoard.TILE_SIZE) {
                            translateY(-getSpeed());
                        } else {
                            translateY(getSpeed());
                        }
                    }
                }
                break;
            case UP:
                if (isXAligned()) {
                    if (!currentBoard.fieldIsBlocked(x, getNextY()) || (onBomb && !isYAligned())) {
                        translateY(-getSpeed());
                    }
                } else {
                    if (target == null) {
                        if (getLeft() < x * GameBoard.TILE_SIZE) {
                            if (!currentBoard.fieldIsBlocked(x - 1, y - 1)) {
                                setTarget(x - 1, y - 1);
                            } else if (!currentBoard.fieldIsBlocked(x, y - 1)) {
                                setTarget(x, y - 1);
                            }
                        } else {
                            if (!currentBoard.fieldIsBlocked(x + 1, y - 1)) {
                                setTarget(x + 1, y - 1);
                            } else if (!currentBoard.fieldIsBlocked(x, y - 1)) {
                                setTarget(x, y - 1);
                            }
                        }
                    } else if (!currentBoard.fieldIsBlocked(target.x, target.y)) {
                        if (getLeft() > target.x * GameBoard.TILE_SIZE) {
                            translateX(-getSpeed());
                        } else {
                            translateX(getSpeed());
                        }
                    }
                }
                break;
            case DOWN:
                if (isXAligned()) {
                    if (!currentBoard.fieldIsBlocked(x, getNextY()) || (onBomb && !isYAligned())) {
                        translateY(getSpeed());
                    }
                } else {
                    if (target == null) {
                        if (getLeft() < x * GameBoard.TILE_SIZE) {
                            if (!currentBoard.fieldIsBlocked(x - 1, y + 1)) {
                                setTarget(x - 1, y + 1);
                            } else if (!currentBoard.fieldIsBlocked(x, y + 1)) {
                                setTarget(x, y + 1);
                            }
                        } else {
                            if (!currentBoard.fieldIsBlocked(x + 1, y + 1)) {
                                setTarget(x + 1, y + 1);
                            } else if (!currentBoard.fieldIsBlocked(x, y + 1)) {
                                setTarget(x, y + 1);
                            }
                        }
                    } else if (!currentBoard.fieldIsBlocked(target.x, target.y)) {
                        if (getLeft() > target.x * GameBoard.TILE_SIZE) {
                            translateX(-getSpeed());
                        } else {
                            translateX(getSpeed());
                        }
                    }
                }
                break;
        }
    }

    private void setTarget(int x, int y) {
        target = new Point(x, y);
    }
}
