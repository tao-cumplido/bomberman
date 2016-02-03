package de.hsh.project.bomberman.game.battlemode.powerup;

import de.hsh.project.bomberman.game.Resource;
import de.hsh.project.bomberman.game.battlemode.board.GameBoard;
import de.hsh.project.bomberman.game.battlemode.board.Tile;
import de.hsh.project.bomberman.game.battlemode.gfx.AnimationID;
import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;

import java.awt.image.BufferedImage;

/**
 * Created by Tao on 18.12.2015.
 */
public class PowerUpExplosion extends Tile {

    private static BufferedImage spriteSheet = Resource.loadImage("powerup/dissolve.png");

    private enum Animation implements AnimationID {
        DEFAULT
    }

    public PowerUpExplosion() {
        super(false, GameBoard.TILE_SIZE);
        this.sprite = new Sprite(spriteSheet, GameBoard.TILE_SIZE, GameBoard.TILE_SIZE * 2);
        this.sprite.addAnimation(Animation.DEFAULT);
        this.sprite.playAnimation(Animation.DEFAULT, 3, this::removeFromBoard);
    }
}
