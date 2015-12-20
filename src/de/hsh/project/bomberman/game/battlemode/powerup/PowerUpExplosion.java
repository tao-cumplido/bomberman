package de.hsh.project.bomberman.game.battlemode.powerup;

import de.hsh.project.bomberman.game.battlemode.board.Tile;
import de.hsh.project.bomberman.game.battlemode.gfx.AnimationID;
import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;

import java.awt.image.BufferedImage;

/**
 * Created by Tao on 18.12.2015.
 */
public class PowerUpExplosion extends Tile {

    private static BufferedImage spriteSheet = Sprite.loadSpriteSheet("/res/images/powerup/dissolve.png");

    private enum Animation implements AnimationID {
        DEFAULT
    }

    public PowerUpExplosion() {
        super(false);
        this.sprite = new Sprite(spriteSheet);
        this.sprite.addAnimation(Animation.DEFAULT);
        this.sprite.playAnimation(Animation.DEFAULT, 3, this::removeFromBoard);
    }
}
