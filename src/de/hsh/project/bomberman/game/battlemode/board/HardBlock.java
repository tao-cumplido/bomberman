package de.hsh.project.bomberman.game.battlemode.board;


import de.hsh.project.bomberman.game.battlemode.gfx.AnimationID;
import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by taocu on 26.10.2015.
 */
public class HardBlock extends Tile {

    private static BufferedImage spriteSheet = Sprite.loadSpriteSheet("/res/images/hardblock.png");

    public HardBlock() {
        super(true);
        this.sprite = new Sprite(spriteSheet);
    }

    @Override
    public BufferedImage getFrame() {
        return sprite.getFrame(0);
    }
}
