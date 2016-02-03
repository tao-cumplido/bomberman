package de.hsh.project.bomberman.game.battlemode.hud;

import de.hsh.project.bomberman.game.battlemode.BattleState;
import de.hsh.project.bomberman.game.battlemode.board.GameBoard;
import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;
import de.hsh.project.bomberman.game.battlemode.player.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by taocu on 28.01.2016.
 */
public class LifeDisplay {

    private static Sprite HEARTS = new Sprite("hud/hearts.png", 96, 96);

    private Player player;
    private Sprite digits;

    private BufferedImage buffer;

    private int lastLife;

    public LifeDisplay(Player player) {
        this.player = player;
        this.digits = new Sprite(BattleState.DIGITS, GameBoard.TILE_SIZE * 3/8, GameBoard.TILE_SIZE);
        this.buffer = new BufferedImage(96, 96, BufferedImage.TYPE_INT_ARGB);
        this.lastLife = -1;
    }

    public BufferedImage getBuffer() {
        int lifes = player.getLifes();

        if (lastLife != lifes) {
            lastLife = lifes;

            Graphics g = buffer.getGraphics();

            g.drawImage(HEARTS.getFrame(player.getPlayerNumber() - 1), 0, 0, null);

            if (lifes < 10) {
                g.drawImage(digits.getFrame(lifes), 36, 16, null);
            } else {
                g.drawImage(digits.getFrame(lifes / 10), 26, 16, null);
                g.drawImage(digits.getFrame(lifes % 10), 46, 16, null);
            }
        }

        return buffer;
    }
}
