package de.hsh.project.bomberman.game.battlemode.bomb;

import de.hsh.project.bomberman.game.Resource;
import de.hsh.project.bomberman.game.battlemode.board.GameBoard;
import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;
import de.hsh.project.bomberman.game.battlemode.player.Player;

import java.awt.image.BufferedImage;

/**
 * Created by taocu on 26.10.2015.
 */
public class FireBlast extends Blast {

    private static BufferedImage spriteSheet = Resource.loadImage("fireblast.png");

    public FireBlast(int row) {
        super(row);
        Integer frames[] = {0, 1, 2, 3, 4, 3, 4, 3, 4, 3, 2, 1, 0};
        if (row > 0) {
            for (int i = 0; i < frames.length; i++) {
                frames[i] += row * 5;
            }
        }

        this.sprite = new Sprite(spriteSheet);
        this.sprite.addAnimation(Animation.DEFAULT, frames);
        this.sprite.playAnimation(Animation.DEFAULT, 0, this::removeFromBoard);
    }

    @Override
    public int onCollision(Player player) {
        if (super.onCollision(player) > 8 * GameBoard.TILE_SIZE) {
            player.burn();
            return 1;
        }

        return 0;
    }

    @Override
    public void removeFromBoard() {
        super.removeFromBoard();
        currentBoard.meltFloor(getX(), getY());
    }
}
