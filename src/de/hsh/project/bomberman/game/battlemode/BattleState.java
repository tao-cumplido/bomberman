package de.hsh.project.bomberman.game.battlemode;

import de.hsh.project.bomberman.game.Game;
import de.hsh.project.bomberman.game.Resource;
import de.hsh.project.bomberman.game.battlemode.board.BoardOne;
import de.hsh.project.bomberman.game.battlemode.board.GameBoard;
import de.hsh.project.bomberman.game.battlemode.hud.LifeDisplay;
import de.hsh.project.bomberman.game.battlemode.hud.TimeDisplay;
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

    public static final BufferedImage DIGITS = Resource.loadImage("hud/digits.png");

    private BufferedImage dynamicBuffer = new BufferedImage(1120, 868, BufferedImage.TYPE_INT_ARGB);

    private Player[] player = new Player[4];
    private GameBoard board;

    private LifeDisplay[] lifeDisplays = new LifeDisplay[4];

    private TimeDisplay timeDisplay;

    public BattleState() {
        Map<SettingsTyp, Integer> p1Keys = new HashMap<>();
        p1Keys.put(SettingsTyp.DIRECTION_LEFT, KeyEvent.VK_LEFT);
        p1Keys.put(SettingsTyp.DIRECTION_RIGHT, KeyEvent.VK_RIGHT);
        p1Keys.put(SettingsTyp.DIRECTION_UP, KeyEvent.VK_UP);
        p1Keys.put(SettingsTyp.DIRECTION_DOWN, KeyEvent.VK_DOWN);
        p1Keys.put(SettingsTyp.SETTINGS_BOMB, KeyEvent.VK_SPACE);
        p1Keys.put(SettingsTyp.SETTING_REMOTECONTROL, KeyEvent.VK_M);
        player[0] = new HumanPlayer(0, getInputMap(WHEN_IN_FOCUSED_WINDOW), getActionMap(), p1Keys);
        //player[0] = new AIPlayer(0, 0);

        Map<SettingsTyp, Integer> p2Keys = new HashMap<>();
        p2Keys.put(SettingsTyp.DIRECTION_LEFT, KeyEvent.VK_A);
        p2Keys.put(SettingsTyp.DIRECTION_RIGHT, KeyEvent.VK_D);
        p2Keys.put(SettingsTyp.DIRECTION_UP, KeyEvent.VK_W);
        p2Keys.put(SettingsTyp.DIRECTION_DOWN, KeyEvent.VK_S);
        p2Keys.put(SettingsTyp.SETTINGS_BOMB, KeyEvent.VK_TAB);
        p2Keys.put(SettingsTyp.SETTING_REMOTECONTROL, KeyEvent.VK_Q);
        //player[1] = new HumanPlayer(1, getInputMap(WHEN_IN_FOCUSED_WINDOW), getActionMap(), p2Keys);
        player[1] = new AIPlayer(1, 1);

        player[2] = new AIPlayer(2, 1);
        player[3] = new AIPlayer(3, 1);

        this.board = new BoardOne(player);

        for (int i = 0; i < 4; i++) {
            lifeDisplays[i] = new LifeDisplay(player[i]);
        }

        timeDisplay = new TimeDisplay(Settings.getBasicSetting().get(SettingsTyp.TIME));

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

        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, 1120, 120);

        g.drawImage(lifeDisplays[0].getBuffer(), 96, 2, null);
        g.drawImage(lifeDisplays[1].getBuffer(), 288, 2, null);
        g.drawImage(lifeDisplays[2].getBuffer(), 736, 2, null);
        g.drawImage(lifeDisplays[3].getBuffer(), 928, 2, null);

        g.drawImage(timeDisplay.getBuffer(), 464, 18, null);

        g.drawImage(board.getBuffer(), -48, 100, this);
    }

    private void render() {
        Graphics g = getGraphics();
        g.drawImage(dynamicBuffer, 0, 0, this);
    }

}
