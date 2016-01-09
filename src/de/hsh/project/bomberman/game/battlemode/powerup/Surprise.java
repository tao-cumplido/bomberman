package de.hsh.project.bomberman.game.battlemode.powerup;

import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;
import de.hsh.project.bomberman.game.battlemode.player.Player;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by taocu on 26.10.2015.
 */
public class Surprise extends PowerUp {

    private static BufferedImage spriteSheet = Sprite.loadSpriteSheet("/res/images/powerup/surprise.png");

    public Surprise() {
        super(spriteSheet);
    }

    @Override
    public boolean isTemporary() {
        return true;
    }

    @Override
    public void affect(Player player) {
        double r = new Random().nextDouble();

        player.resetStats();

        if (r < 0.2) {
            player.iceBombs();
        } else if (r < 0.325) {
            player.minimizeSpeed();
        } else if (r < 0.45) {
            player.maximizeSpeed();
        } else if (r < 0.575) {
            player.maximizeRange();
        } else if (r < 0.7) {
            player.noBombs();
        } else if (r < 0.825) {
            player.randomBombs();
        } else if (r < 0.95) {
            player.makeInvincible();
        } else {
            player.increaseLifes();
        }
    }
}
