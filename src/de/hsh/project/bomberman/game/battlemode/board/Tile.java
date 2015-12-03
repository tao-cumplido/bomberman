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

    public boolean onCollision(Player player) {
        boolean collides = !bounds.intersection(player.bounds).isEmpty();
        // TODO: wall collision doesn't work if player.speed > 16
        if (collides && this.isSolid() && player.isMoving()) {
            int delta = player.getSpeed();
            int x = player.getX();
            int y = player.getY();
            int m = GameBoard.TILE_SIZE / 2;
            switch (player.getFacingDirection()) {
                case LEFT:
                    if (this.getLeft() < player.getLeft() && BOARD.fieldIsBlocked(x - 1, y)) {
                        player.translateX(delta);
                    }
                    if (player.getTop() < this.getTop()) {
                        if (player.getBottom() >= this.getTop() + m) y -= 1;
                        if (!BOARD.fieldIsBlocked(x - 1, y)) player.translateY(-delta);
                    }
                    if (player.getBottom() > this.getBottom()) {
                        if (player.getTop() < this.getBottom() - m) y += 1;
                        if (!BOARD.fieldIsBlocked(x - 1, y)) player.translateY(delta);
                    }
                    break;
                case RIGHT:
                    if (this.getRight() > player.getRight() && BOARD.fieldIsBlocked(x + 1, y)) {
                        player.translateX(-delta);
                    }
                    if (player.getTop() < this.getTop()) {
                        if (player.getBottom() >= this.getTop() + m) y -= 1;
                        if (!BOARD.fieldIsBlocked(x + 1, y)) player.translateY(-delta);
                    }
                    if (player.getBottom() > this.getBottom()) {
                        if (player.getTop() < this.getBottom() - m) y += 1;
                        if (!BOARD.fieldIsBlocked(x + 1, y)) player.translateY(delta);
                    }
                    break;
                case UP:
                    if (this.getTop() < player.getTop() && BOARD.fieldIsBlocked(x, y - 1)) {
                        player.translateY(delta);
                    }
                    if (player.getLeft() < this.getLeft()) {
                        if (player.getRight() >= this.getLeft() + m) x -= 1;
                        if (!BOARD.fieldIsBlocked(x, y - 1)) player.translateX(-delta);
                    }
                    if (player.getRight() > this.getRight()) {
                        if (player.getLeft() < this.getRight() - m) x += 1;
                        if (!BOARD.fieldIsBlocked(x, y - 1)) player.translateX(delta);
                    }
                    break;
                case DOWN:
                    if (this.getBottom() > player.getBottom() && BOARD.fieldIsBlocked(x, y + 1)) {
                        player.translateY(-delta);
                    }
                    if (player.getLeft() < this.getLeft()) {
                        if (player.getRight() >= this.getLeft() + m) x -= 1;
                        if (!BOARD.fieldIsBlocked(x, y + 1)) player.translateX(-delta);
                    }
                    if (player.getRight() > this.getRight()) {
                        if (player.getLeft() < this.getRight() - m) x += 1;
                        if (!BOARD.fieldIsBlocked(x, y + 1)) player.translateX(delta);
                    }
                    break;
            }
        }

        return collides;
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

    // TODO: make abstract
    public void burn() {}

    public void freeze() {}
}
