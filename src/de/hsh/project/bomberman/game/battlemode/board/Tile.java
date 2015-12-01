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

    protected Rectangle bounds;

    int x, y;

    private boolean solid;

    protected Sprite sprite;

    public Tile() {} // TODO: remove eventually

    public Tile(boolean solid) {
        this.bounds = new Rectangle(GameBoard.TILE_SIZE, GameBoard.TILE_SIZE);
        this.solid = solid;
    }

    public Rectangle onCollision(Player player) {
        Rectangle intersection = bounds.intersection(player.bounds);

        if(!intersection.isEmpty() && this.isSolid()) {
            /*int x = player.getX();
            int y = player.getY();
            switch (player.getFacingDirection()) {
                case LEFT:
                    if (this.getLeft() < player.getLeft()) {
                        player.translateX(player.getSpeed());
                    }
                    break;
                case RIGHT:
                    if (this.getRight() > player.getRight()) {
                        player.translateX(-player.getSpeed());
                    }
                    break;
                case UP:
                    if (this.getTop() < player.getTop()) {
                        player.translateY(player.getSpeed());
                    }
                    break;
                case DOWN:
                    if (this.getBottom() > player.getBottom()) {
                        player.translateY(-player.getSpeed());
                    }
                    break;
            }*/

            int dx = player.getLeft() % GameBoard.TILE_SIZE;
            int dy = player.getTop() % GameBoard.TILE_SIZE;

            if (intersection.getWidth() < intersection.getHeight()) {
                // horizontal collision
                /*if (dx > GameBoard.TILE_SIZE / 2) {
                    // moving left
                    player.translateX(GameBoard.TILE_SIZE - dx);
                } else {
                    // moving right
                    player.translateX(-dx);
                }*/
                player.alignX();

                if (player.getTop() < intersection.y) {
                    player.translateY(-player.getSpeed());
                } else if (player.getBottom() > intersection.getMaxY()) {
                    player.translateY(player.getSpeed());
                }
            } else {
                // vertical collision
                /*if (dy > GameBoard.TILE_SIZE / 2) {
                    // moving up
                    player.translateY(GameBoard.TILE_SIZE - dy);
                } else {
                    // moving down
                    player.translateY(-dy);
                }*/
                player.alignY();

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
        return bounds.x + bounds.width;
    }

    public int getBottom() {
        return bounds.y + bounds.height;
    }

    public void update() {
        sprite.update();
    }

    public BufferedImage getFrame() {
        return sprite.getCurrentFrame();
    }
}
