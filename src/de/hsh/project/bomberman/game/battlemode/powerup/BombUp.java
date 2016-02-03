package de.hsh.project.bomberman.game.battlemode.powerup;

import de.hsh.project.bomberman.game.Resource;
import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;
import de.hsh.project.bomberman.game.battlemode.player.Player;

import java.awt.image.BufferedImage;

/**
 * Created by taocu on 26.10.2015.
 */
public class BombUp extends PowerUp {

    private static BufferedImage spriteSheet = Resource.loadImage("powerup/bomb.png");

    public BombUp() {
        super(spriteSheet);
    }

    @Override
    public void affect(Player player) {
        player.raiseBombCount();
    }
}
