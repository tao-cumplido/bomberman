package de.hsh.project.bomberman.game.battlemode.board;

import de.hsh.project.bomberman.game.battlemode.player.Player;

/**
 * Created by taocu on 26.10.2015.
 */
public class BoardTwo extends GameBoard {

    public BoardTwo(Player[] player) {
        super(player);
        player[0].setPosition(GRID_WIDTH / 2 - 1, GRID_HEIGHT / 2 - 1);
        player[1].setPosition(GRID_WIDTH / 2 + 1, GRID_HEIGHT / 2 - 1);
        player[2].setPosition(GRID_WIDTH / 2 - 1, GRID_HEIGHT / 2 + 1);
        player[3].setPosition(GRID_WIDTH / 2 + 1, GRID_HEIGHT / 2 + 1);
        fillRandomSoftBlocks();
    }

}
