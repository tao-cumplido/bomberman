package de.hsh.project.bomberman.game.battlemode.board;

import java.awt.*;

/**
 * Created by taocu on 08.11.2015.
 */
public abstract class Tile {

    private Rectangle bounds;

    private boolean solid;

    public Tile() {}

    public Tile(boolean solid) {
        this.bounds = new Rectangle(GameBoard.TILE_SIZE, GameBoard.TILE_SIZE);
        this.solid = solid;
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
}
