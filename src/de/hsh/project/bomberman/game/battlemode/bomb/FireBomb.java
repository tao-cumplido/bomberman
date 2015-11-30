package de.hsh.project.bomberman.game.battlemode.bomb;

import de.hsh.project.bomberman.game.battlemode.board.GameBoard;
import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;

/**
 * Created by taocu on 26.10.2015.
 */
public class FireBomb extends Bomb {

    public FireBomb(int range) {
        super(range);

        this.sprite = new Sprite("/res/images/firebomb.png", GameBoard.TILE_SIZE, GameBoard.TILE_SIZE, 20);
        this.sprite.addAnimation(Animation.DEFAULT, 0, 1, 2, 1);
        this.sprite.playAnimation(Animation.DEFAULT, true);
    }
}
