package de.hsh.project.bomberman.game.battlemode.board;

/**
 * Created by taocu on 11.12.2015.
 */
public abstract class Block extends Tile {

    public Block() {
        super(true);
    }

    @Override
    public boolean isBlock() {
        return true;
    }
}
