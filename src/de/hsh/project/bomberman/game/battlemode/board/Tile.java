package de.hsh.project.bomberman.game.battlemode.board;

import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by taocu on 08.11.2015.
 */
public abstract class Tile {

    protected static GameBoard BOARD;

    private Rectangle bounds;

    private boolean solid;

    protected Sprite sprite;

    public Tile() {}

    public Tile(int gridX, int gridY, boolean solid) {
        this.bounds = new Rectangle(GameBoard.TILE_SIZE, GameBoard.TILE_SIZE);
        this.solid = solid;
        setPosition(gridX, gridY);
    }

    public Rectangle collides(Tile tile) {
        return bounds.intersection(tile.bounds);
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public void setPosition(int gridX, int gridY) {
        bounds.x = gridX * GameBoard.TILE_SIZE;
        bounds.y = gridY * GameBoard.TILE_SIZE;
    }

    public int getLeft() {
        return bounds.x;
    }

    public int getTop() {
        return bounds.y;
    }

    public int getRight() {
        return bounds.x + bounds.width;
    }

    public int getBottom() {
        return bounds.y + bounds.height;
    }

    public void translateX(int delta) {
        bounds.x += delta;
    }

    public void translateY(int delta) {
        bounds.y += delta;
    }

    public void update() {
        sprite.update();
    }

    public BufferedImage getFrame() {
        return sprite.getCurrentFrame();
    }
}
