package de.hsh.project.bomberman.game.battlemode.player;

import de.hsh.project.bomberman.game.Resource;
import de.hsh.project.bomberman.game.battlemode.board.Block;
import de.hsh.project.bomberman.game.battlemode.board.Tile;
import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;

import java.awt.image.BufferedImage;

/**
 * Created by taocu on 27.01.2016.
 */
public class IceBlock extends Block {

    private static BufferedImage spriteSheet = Resource.loadImage("bomberman/frozen.png");

    private Player player;

    public IceBlock(Player player) {
        this.player = player;
        this.sprite = new Sprite(spriteSheet);
    }

    @Override
    public BufferedImage getFrame() {
        return sprite.getFrame(0);
    }

    @Override
    public void burn() {
        super.burn();
        player.burn();
    }
}
