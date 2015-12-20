package de.hsh.project.bomberman.game.battlemode.powerup;

import de.hsh.project.bomberman.game.battlemode.board.Tile;
import de.hsh.project.bomberman.game.battlemode.gfx.AnimationID;
import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;
import de.hsh.project.bomberman.game.battlemode.player.Player;

import java.awt.image.BufferedImage;

/**
 * Created by taocu on 26.10.2015.
 */
public abstract class PowerUp extends Tile {

    protected enum Animation implements AnimationID {
        DEFAULT, FROZEN
    }

    public PowerUp(BufferedImage spriteSheet) {
        super(false);
        this.sprite = new Sprite(spriteSheet);
        this.sprite.addAnimation(Animation.DEFAULT, 0, 1);
        this.sprite.addAnimation(Animation.FROZEN, 2);
    }

    @Override
    public boolean onCollision(Player player) {
        if (super.onCollision(player)) {
            removeFromBoard();
            player.addPowerUp(this);
            return true;
        }

        return false;
    }

    @Override
    public void burn() {
        removeFromBoard();
        currentBoard.put(new PowerUpExplosion(), getX(), getY());
    }

    public void affect(Player player) {}; // TODO: make abstract
}
