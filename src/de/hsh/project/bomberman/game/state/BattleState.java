package de.hsh.project.bomberman.game.state;

import de.hsh.project.bomberman.game.Game;
import de.hsh.project.bomberman.game.player.HumanPlayer;
import de.hsh.project.bomberman.game.player.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by taocu on 26.10.2015.
 */
public class BattleState extends GameState implements Runnable {

    private boolean gameIsActive = true;
    private BufferedImage buffer = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);

    private Player[] player = new Player[4];

    public BattleState() {
        player[0] = new HumanPlayer();
        addKeyListener((HumanPlayer)player[0]);
        new Thread(this).start();
    }

    @Override
    public void run() {
        long start, elapsed, wait;

        while (gameIsActive) {
            start = System.nanoTime();

            //processInput();
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

    private void processInput() {

    }

    private void update() {
        player[0].update();
    }

    private void draw() {
        Graphics g = buffer.getGraphics();
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, 640, 480);
        g.drawImage(player[0].getFrame(), player[0].getX(), player[0].getY(), this);
    }

    private void render() {
        getGraphics().drawImage(buffer, 0, 0, this);
    }

}
