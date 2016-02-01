package de.hsh.project.bomberman.game.battlemode.board;


import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;

import java.awt.image.BufferedImage;

/**
 * Created by taocu on 26.10.2015.
 */
public class HardBlock extends Block {

    private static BufferedImage spriteSheet = Sprite.loadSpriteSheet("/res/images/hardblock.png");

    private int type;

    public HardBlock(int type) {
        this.type = type;
        this.sprite = new Sprite(spriteSheet);
    }

    @Override
    public BufferedImage getFrame() {
        return (frozen > 0 && type == 1) ? sprite.getFrame(2) : sprite.getFrame(type);
    }
}
