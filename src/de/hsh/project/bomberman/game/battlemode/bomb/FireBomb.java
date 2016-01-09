package de.hsh.project.bomberman.game.battlemode.bomb;

import de.hsh.project.bomberman.game.battlemode.board.Tile;
import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by taocu on 26.10.2015.
 */
public class FireBomb extends Bomb {

    private static BufferedImage spriteSheet = Sprite.loadSpriteSheet("/res/images/firebomb.png");

    public FireBomb(int range, ArrayList<Bomb> queue, Trigger trigger) {
        super(range, queue, trigger);

        this.sprite = new Sprite(spriteSheet);
        this.sprite.addAnimation(Animation.DEFAULT, 0, 1, 2, 1);
        this.sprite.playAnimation(Animation.DEFAULT, 20, true);
    }

    @Override
    public void detonate() {
        super.detonate();
        for (Tile tile : extend(FireBlast::new)) {
            tile.burn();
        }
    }
}