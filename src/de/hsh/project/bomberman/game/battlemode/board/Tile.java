package de.hsh.project.bomberman.game.battlemode.board;

import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;
import de.hsh.project.bomberman.game.battlemode.player.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by taocu on 08.11.2015.
 */
public abstract class Tile {

    public final static Tile EMPTY = new Tile() {
        @Override
        public boolean isEmpty() {
            return true;
        }
    };

    protected static GameBoard currentBoard;

    protected Rectangle bounds;

    int x, y;

    private boolean solid, active;

    private int vOffset;
    protected Sprite sprite;

    public Tile() {} // TODO: make private

    public Tile(boolean solid, int vOffset) {
        this.bounds = new Rectangle(GameBoard.TILE_SIZE, GameBoard.TILE_SIZE);
        this.solid = solid;
        this.vOffset = vOffset;
        setActive(true);
    }

    public Tile(boolean solid) {
        this(solid, 0);
    }

    public int onCollision(Player player) {
        Rectangle r = bounds.intersection(player.bounds);
        return r.isEmpty() ? 0 : r.width * r.height;
    }

    public int getVOffset() {
        return vOffset;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setPosition(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        bounds.x = x * GameBoard.TILE_SIZE;
        this.x = x;
    }

    public void setY(int y) {
        bounds.y = y * GameBoard.TILE_SIZE;
        this.y = y;
    }

    public int getLeft() {
        return bounds.x;
    }

    public int getTop() {
        return bounds.y;
    }

    public int getRight() {
        return bounds.x + bounds.width - 1;
    }

    public int getBottom() {
        return bounds.y + bounds.height - 1;
    }

    public void update() {
        sprite.update();
    }

    public BufferedImage getFrame() {
        return sprite.getCurrentFrame();
    }

    // TODO: make abstract
    public void burn() {}

    public void freeze() {}

    public void removeFromBoard() {
        currentBoard.remove(getX(), getY());
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean isBlock() {
        return false;
    }

    public boolean isBomb() {
        return false;
    }

    public boolean isBlast() {
        return false;
    }

    public boolean isPowerUp() {
        return false;
    }
}
