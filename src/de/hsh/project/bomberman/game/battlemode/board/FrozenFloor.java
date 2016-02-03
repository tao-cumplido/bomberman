package de.hsh.project.bomberman.game.battlemode.board;

import de.hsh.project.bomberman.game.Resource;
import de.hsh.project.bomberman.game.battlemode.gfx.AnimationID;
import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;

import java.awt.image.BufferedImage;

/**
 * Created by taocu on 26.10.2015.
 */
public class FrozenFloor extends Tile {

    private static BufferedImage spriteSheet = Resource.loadImage("frozenfloor.png");

    private int tick;

    private enum Animation implements AnimationID {
        DEFAULT
    }

    public FrozenFloor() {
        super(false);

        this.sprite = new Sprite(spriteSheet);
        this.sprite.addAnimation(Animation.DEFAULT);
        this.sprite.playAnimation(Animation.DEFAULT, false);

        this.tick = 0;
    }

    @Override
    public void update() {
        super.update();
        tick++;
        if (tick == 240) {
            currentBoard.meltFloor(x, y);
        }
    }
}
