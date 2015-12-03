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

    private static BufferedImage spriteSheet;

    public HardBlock() {
        super(true);

        if (spriteSheet == null) {
            try {
                spriteSheet = ImageIO.read(getClass().getResource("/res/images/hardblock.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.sprite = new Sprite(spriteSheet, GameBoard.TILE_SIZE, GameBoard.TILE_SIZE);
    }

    @Override
    public BufferedImage getFrame() {
        return sprite.getFrame(0);
    }
}
