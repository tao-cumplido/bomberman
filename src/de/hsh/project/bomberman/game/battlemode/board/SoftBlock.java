package de.hsh.project.bomberman.game.battlemode.board;


import de.hsh.project.bomberman.game.battlemode.gfx.AnimationID;
import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;

import java.awt.image.BufferedImage;

/**
 * Created by taocu on 26.10.2015.
 */
public class SoftBlock extends Block {

    private static BufferedImage spriteSheet = Sprite.loadSpriteSheet("/res/images/softblock.png");

    private enum Animation implements AnimationID {
        DEFAULT, DISSOLVE
    }

    public SoftBlock() {
        this.sprite = new Sprite(spriteSheet);
        this.sprite.addAnimation(Animation.DEFAULT, 0);
        this.sprite.addAnimation(Animation.DISSOLVE, 1, 2, 3, 4, 5, 6);
        this.sprite.playAnimation(Animation.DEFAULT, false);
    }

    @Override
    public void burn() {
        this.sprite.playAnimation(Animation.DISSOLVE, 5, this::remove);
    }
}
