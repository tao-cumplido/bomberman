package de.hsh.project.bomberman.game.battlemode.board;

import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;
import de.hsh.project.bomberman.game.battlemode.player.Player;

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

    public Tile(boolean solid) {
        this.bounds = new Rectangle(GameBoard.TILE_SIZE, GameBoard.TILE_SIZE);
        this.solid = solid;
    }

    public Rectangle onCollision(Player player) {
        Rectangle intersection = bounds.intersection(((Tile)player).bounds);

        if(!intersection.isEmpty() && this.isSolid()) {
            int dx = player.getLeft() % GameBoard.TILE_SIZE;
            int dy = player.getTop() % GameBoard.TILE_SIZE;

            if (intersection.getWidth() < intersection.getHeight()) {
                // horizontal collision
                if (dx > GameBoard.TILE_SIZE / 2) {
                    // moving left
                    player.translateX(GameBoard.TILE_SIZE - dx);
                } else {
                    // moving right
                    player.translateX(-dx);
                }

                if (player.getTop() < intersection.y) {
                    player.translateY(-player.getSpeed());
                } else if (getBottom() > intersection.getMaxY()) {
                    player.translateY(player.getSpeed());
                }
            } else {
                // vertical collision
                if (dy > GameBoard.TILE_SIZE / 2) {
                    // moving up
                    player.translateY(GameBoard.TILE_SIZE - dy);
                } else {
                    // moving down
                    player.translateY(-dy);
                }

                if (player.getLeft() < intersection.x) {
                    player.translateX(-player.getSpeed());
                } else if (player.getRight() > intersection.getMaxX()) {
                    player.translateX(player.getSpeed());
                }
            }
        }

        return intersection;
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
