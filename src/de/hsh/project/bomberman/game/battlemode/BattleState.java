package de.hsh.project.bomberman.game.battlemode;

import de.hsh.project.bomberman.game.Game;
import de.hsh.project.bomberman.game.battlemode.board.BoardOne;
import de.hsh.project.bomberman.game.battlemode.board.GameBoard;
import de.hsh.project.bomberman.game.battlemode.player.HumanPlayer;
import de.hsh.project.bomberman.game.battlemode.player.Player;
import de.hsh.project.bomberman.game.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by taocu on 26.10.2015.
 */
public class BattleState extends GameState implements Runnable {

    private BufferedImage dynamicBuffer = new BufferedImage(1216, 960, BufferedImage.TYPE_INT_ARGB);
    private BufferedImage staticBuffer = new BufferedImage(1216, 960, BufferedImage.TYPE_INT_ARGB);

    private Player[] player = new Player[4];
    private GameBoard board;

    public BattleState() {
        player[0] = new HumanPlayer(0, getInputMap(WHEN_IN_FOCUSED_WINDOW), getActionMap());

        this.board = new BoardOne(player);

        new Thread(this).start();
    }

    @Override
    public void run() {
        long start, elapsed, wait;

        while (true) {
            start = System.nanoTime();

            update();
            draw();
            render();

            elapsed = System.nanoTime() - start;
            wait = 1000 / Game.FPS - elapsed / 1000_000;

            try {
                //if (wait < 0) System.out.println("Warning: loop too slow!");
                Thread.sleep(wait < 0 ? 5 : wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        board.update();
    }

    private void draw() {
        Graphics g = dynamicBuffer.getGraphics();
        g.drawImage(board.getBuffer(), 0, 0, this);
    }

    private void render() {
        getGraphics().drawImage(dynamicBuffer, 0, 0, this);
    }

}
