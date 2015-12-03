package de.hsh.project.bomberman.game.battlemode.bomb;

import de.hsh.project.bomberman.game.Game;
import de.hsh.project.bomberman.game.battlemode.board.Tile;
import de.hsh.project.bomberman.game.battlemode.gfx.AnimationID;

/**
 * Created by taocu on 26.10.2015.
 */
public abstract class Bomb extends Tile {

    protected enum Animation implements AnimationID {
        DEFAULT
    }

    private int tick;

    protected int range;

    public Bomb(int range) {
        super(true);
        this.tick = 0;
        this.range = range;
    }

    @Override
    public void update() {
        super.update();
        tick++;
        if (tick == 4 * Game.FPS) {
            detonate();
        }
    }

    @Override
    public void burn() {
        detonate();
    }

    public void detonate() {
        BOARD.remove(getX(), getY());
    }
}
