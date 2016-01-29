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
import de.hsh.project.bomberman.game.highscore.EnterNameState;
import de.hsh.project.bomberman.game.highscore.HighScore;
import de.hsh.project.bomberman.game.menu.TitleState;
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

    private boolean timeIsUp = false;

    private boolean gameIsRunning = true;

    public BattleState() {
        Map<SettingsTyp, Integer> settings = Settings.getBasicSetting();

        if (settings.get(SettingsTyp.PLAYER1) == 0) {
            player[0] = new HumanPlayer(0, getInputMap(WHEN_IN_FOCUSED_WINDOW), getActionMap(), Settings.getPlayer1());
        } else {
            player[0] = new AIPlayer(0, settings.get(SettingsTyp.LEVEL));
        }
        if (settings.get(SettingsTyp.PLAYER2) == 0) {
            player[1] = new HumanPlayer(1, getInputMap(WHEN_IN_FOCUSED_WINDOW), getActionMap(), Settings.getPlayer2());
        } else {
            player[1] = new AIPlayer(1, settings.get(SettingsTyp.LEVEL));
        }
        if (settings.get(SettingsTyp.PLAYER3) == 0) {
            player[2] = new HumanPlayer(2, getInputMap(WHEN_IN_FOCUSED_WINDOW), getActionMap(), Settings.getPlayer3());
        } else {
            player[2] = new AIPlayer(2, settings.get(SettingsTyp.LEVEL));
        }
        if (settings.get(SettingsTyp.PLAYER4) == 0) {
            player[3] = new HumanPlayer(3, getInputMap(WHEN_IN_FOCUSED_WINDOW), getActionMap(), Settings.getPlayer4());
        } else {
            player[3] = new AIPlayer(3, settings.get(SettingsTyp.LEVEL));
        }

        this.board = new BoardOne(player);

        for (int i = 0; i < 4; i++) {
            lifeDisplays[i] = new LifeDisplay(player[i]);
        }

        timeDisplay = new TimeDisplay(settings.get(SettingsTyp.TIME));

        new Thread(this).start();
    }

    @Override
    public void run() {
        long start, elapsed, wait;

        while (gameIsRunning) {
            start = System.nanoTime();

            update(); if (!gameIsRunning) return;
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

        if (timeDisplay.timeIsUp() && !timeIsUp) {
            timeIsUp = true;
            for (int i = 0; i < 4; i++) {
                if (player[i].getLifes() > 1) player[i].setLifes(1);
            }
        }

        int ap = activePlayers();

        if (ap == 1) {
            Player p = null;

            for (int i = 0; i < 4; i++) {
                if (player[i].isAlive()) {
                    p = player[i];
                    break;
                }
            }

            gameIsRunning = false;

            if (p.isHuman()) {
                Game.switchState(new EnterNameState(new HighScore(p.score() + timeDisplay.remainingSeconds() * 100)));
            } else {
                Game.switchState(new TitleState());
            }
        }

        if (ap == 0) {
            Game.switchState(new TitleState());
        }
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
        getGraphics().drawImage(dynamicBuffer, 0, 0, this);
    }

    private int activePlayers() {
        int n = 0;

        for (int i = 0; i < 4; i++) {
            if (player[i].isAlive()) n++;
        }

        return n;
    }

}
