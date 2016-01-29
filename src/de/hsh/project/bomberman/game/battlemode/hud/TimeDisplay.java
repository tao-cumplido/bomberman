package de.hsh.project.bomberman.game.battlemode.hud;

import de.hsh.project.bomberman.game.Game;
import de.hsh.project.bomberman.game.Resource;
import de.hsh.project.bomberman.game.battlemode.BattleState;
import de.hsh.project.bomberman.game.battlemode.board.GameBoard;
import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by taocu on 28.01.2016.
 */
public class TimeDisplay {

    private static BufferedImage clock = Resource.loadImage("hud/clock.png");

    private Sprite digits;
    private BufferedImage buffer;

    private int counter;

    private final int DIGIT_OFFSET = 70;

    public TimeDisplay(int minutes) {
        this.digits = new Sprite(BattleState.DIGITS, GameBoard.TILE_SIZE * 3/8, GameBoard.TILE_SIZE);
        this.buffer = new BufferedImage(256, 64, BufferedImage.TYPE_INT_ARGB);
        this.counter = minutes * 60 * Game.FPS;
    }

    public BufferedImage getBuffer() {
        if (timeIsUp()) {

        } else if (counter % Game.FPS == 0) {
            Graphics g = buffer.getGraphics();

            g.drawImage(clock, 0, 0, null);

            int seconds = counter / Game.FPS;

            g.drawImage(digits.getFrame(seconds / 60 / 10), DIGIT_OFFSET, 0, null);
            g.drawImage(digits.getFrame((seconds / 60) % 10), DIGIT_OFFSET + 20, 0, null);
            g.drawImage(digits.getFrame((seconds % 60) / 10), DIGIT_OFFSET + 55, 0, null);
            g.drawImage(digits.getFrame((seconds % 60) % 10), DIGIT_OFFSET + 75, 0, null);
        }

        counter--;

        return buffer;
    }

    public int remainingSeconds() {
        return counter / 30;
    }

    public boolean timeIsUp() {
        return counter < 0;
    }
}
