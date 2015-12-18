package de.hsh.project.bomberman.game.battlemode.bomb;

import de.hsh.project.bomberman.game.battlemode.board.Tile;
import de.hsh.project.bomberman.game.battlemode.gfx.AnimationID;
import de.hsh.project.bomberman.game.battlemode.player.Player;

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

    @Override
    public boolean onCollision(Player player) {
        boolean collides = super.onCollision(player);

        if (collides) {
            player.burn();
        }

        return collides;
    }
}
