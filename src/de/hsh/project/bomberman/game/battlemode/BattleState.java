package de.hsh.project.bomberman.game.battlemode;

import de.hsh.project.bomberman.game.Game;
import de.hsh.project.bomberman.game.battlemode.board.BoardOne;
import de.hsh.project.bomberman.game.battlemode.board.GameBoard;
import de.hsh.project.bomberman.game.battlemode.player.AIPlayer;
import de.hsh.project.bomberman.game.battlemode.player.HumanPlayer;
import de.hsh.project.bomberman.game.battlemode.player.Player;
import de.hsh.project.bomberman.game.GameState;
import de.hsh.project.bomberman.game.settings.Settings;
import de.hsh.project.bomberman.game.settings.SettingsTyp;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by taocu on 26.10.2015.
 */
public class BattleState extends GameState implements Runnable {

    private BufferedImage dynamicBuffer = new BufferedImage(29*48, 17*48, BufferedImage.TYPE_INT_ARGB);

    private Player[] player = new Player[2];
    private GameBoard board;

    public BattleState() {
        Map<SettingsTyp, Integer> p1Keys = new HashMap<>();
        p1Keys.put(SettingsTyp.DIRECTION_LEFT, KeyEvent.VK_LEFT);
        p1Keys.put(SettingsTyp.DIRECTION_RIGHT, KeyEvent.VK_RIGHT);
        p1Keys.put(SettingsTyp.DIRECTION_UP, KeyEvent.VK_UP);
        p1Keys.put(SettingsTyp.DIRECTION_DOWN, KeyEvent.VK_DOWN);
        p1Keys.put(SettingsTyp.SETTINGS_BOMB, KeyEvent.VK_SPACE);
        p1Keys.put(SettingsTyp.SETTING_REMOTECONTROL, KeyEvent.VK_M);
        player[0] = new HumanPlayer(0, getInputMap(WHEN_IN_FOCUSED_WINDOW), getActionMap(), p1Keys);


        Map<SettingsTyp, Integer> p2Keys = new HashMap<>();
        p2Keys.put(SettingsTyp.DIRECTION_LEFT, KeyEvent.VK_A);
        p2Keys.put(SettingsTyp.DIRECTION_RIGHT, KeyEvent.VK_D);
        p2Keys.put(SettingsTyp.DIRECTION_UP, KeyEvent.VK_W);
        p2Keys.put(SettingsTyp.DIRECTION_DOWN, KeyEvent.VK_S);
        p2Keys.put(SettingsTyp.SETTINGS_BOMB, KeyEvent.VK_TAB);
        p2Keys.put(SettingsTyp.SETTING_REMOTECONTROL, KeyEvent.VK_Q);
        //player[1] = new HumanPlayer(1, getInputMap(WHEN_IN_FOCUSED_WINDOW), getActionMap(), p2Keys);
        player[1] = new AIPlayer(1);

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
