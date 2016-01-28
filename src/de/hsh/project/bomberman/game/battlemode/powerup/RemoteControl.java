package de.hsh.project.bomberman.game.battlemode.powerup;

import de.hsh.project.bomberman.game.battlemode.bomb.Trigger;
import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;
import de.hsh.project.bomberman.game.battlemode.player.Player;

import java.awt.image.BufferedImage;

/**
 * Created by taocu on 26.10.2015.
 */
public class RemoteControl extends PowerUp {

    private static BufferedImage spriteSheet = Sprite.loadSpriteSheet("/res/images/powerup/remote.png");

    public RemoteControl() {
        super(spriteSheet);
    }

    @Override
    public void affect(Player player) {
        player.setBombTrigger(Trigger.REMOTE);
    }
}
