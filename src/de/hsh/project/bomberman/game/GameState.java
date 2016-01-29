package de.hsh.project.bomberman.game;

import de.hsh.project.bomberman.game.battlemode.board.GameBoard;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Tao on 24.10.2015.
 */
public abstract class GameState extends JPanel {

    public GameState() {
        int width = GameBoard.GRID_WIDTH;
        int height = GameBoard.GRID_HEIGHT;
        int tile = GameBoard.TILE_SIZE;
        setPreferredSize(new Dimension((width - 2) * tile + tile / 2, (height - 1) * tile + 100));
        setFocusable(true);
    }
}
