package de.hsh.project.bomberman.game.battlemode.powerup;

import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;
import de.hsh.project.bomberman.game.battlemode.player.Player;

import java.awt.image.BufferedImage;

/**
 * Created by taocu on 26.10.2015.
 */
public class SpeedUp extends PowerUp {

    private static BufferedImage spriteSheet = Sprite.loadSpriteSheet("/res/images/powerup/speed.png");

    public SpeedUp() {
        super(spriteSheet);
        this.sprite.playAnimation(Animation.DEFAULT, 4, true);
    }

    @Override
    public void affect(Player player) {
        player.raiseSpeed();
    }
}
