package de.hsh.project.bomberman.game.battlemode.bomb;

import de.hsh.project.bomberman.game.battlemode.board.Tile;
import de.hsh.project.bomberman.game.battlemode.gfx.AnimationID;

/**
 * Created by taocu on 26.10.2015.
 */
public abstract class Blast extends Tile {

    protected enum Animation implements AnimationID {
        DEFAULT
    }

    public Blast() {
        super(false);
    }

    protected void removeCallback() {
        BOARD.remove(getX(), getY());
    }
}
