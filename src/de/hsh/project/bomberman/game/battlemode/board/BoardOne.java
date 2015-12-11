package de.hsh.project.bomberman.game.battlemode.board;

import de.hsh.project.bomberman.game.battlemode.player.Player;

/**
 * Created by taocu on 26.10.2015.
 */
public class BoardOne extends GameBoard {

    public BoardOne(Player[] player) {
        super(player);
        player[0].setPosition(1, 1);
        player[1].setPosition(GRID_WIDTH - 2, GRID_HEIGHT - 2);
        fillRandomSoftBlocks();
    }
}
