package de.hsh.project.bomberman.game.state;

import de.hsh.project.bomberman.game.Game;
import de.hsh.project.bomberman.game.board.BoardOne;
import de.hsh.project.bomberman.game.board.GameBoard;
import de.hsh.project.bomberman.game.player.HumanPlayer;
import de.hsh.project.bomberman.game.player.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by taocu on 26.10.2015.
 */
public class BattleState extends GameState implements Runnable {

    private BufferedImage dynamicBuffer = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);
    private BufferedImage staticBuffer = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);

    private Player[] player = new Player[4];
    private GameBoard board;

    public BattleState() {
        player[0] = new HumanPlayer();

        this.board = new BoardOne(player);

        addKeyListener((HumanPlayer)player[0]);
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
        g.drawImage(board.getBuffer(), 100, 100, this);
    }

    private void render() {
        getGraphics().drawImage(dynamicBuffer, 0, 0, this);
    }

}
