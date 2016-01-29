package de.hsh.project.bomberman.game.battlemode.bomb;

import de.hsh.project.bomberman.game.Resource;
import de.hsh.project.bomberman.game.battlemode.board.GameBoard;
import de.hsh.project.bomberman.game.battlemode.board.Tile;
import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by taocu on 26.10.2015.
 */
public class IceBomb extends Bomb {

    private static BufferedImage spriteSheet = Resource.loadImage("icebomb.png");

    public IceBomb(int range, ArrayList<Bomb> queue, Trigger trigger) {
        super(range, queue, trigger);

        this.sprite = new Sprite(spriteSheet, GameBoard.TILE_SIZE, GameBoard.TILE_SIZE * 2);
        this.sprite.addAnimation(Animation.DEFAULT, 0, 1, 2, 1);
        this.sprite.playAnimation(Animation.DEFAULT, 2, true);
    }

    @Override
    public void detonate() {
        playSound(Resource.loadAudio("freeze.wav"));
        super.detonate();
        for (Tile tile : extend(IceBlast::new)) {
            tile.freeze();
        }
    }
}
