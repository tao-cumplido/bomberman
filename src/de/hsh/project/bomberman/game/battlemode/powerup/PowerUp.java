package de.hsh.project.bomberman.game.battlemode.powerup;

import de.hsh.project.bomberman.game.Game;
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

    private int tick;

    public PowerUp(BufferedImage spriteSheet) {
        super(false);
        this.sprite = new Sprite(spriteSheet);
        this.sprite.addAnimation(Animation.DEFAULT, 0, 1);
        this.sprite.addAnimation(Animation.FROZEN, 2);
        this.sprite.playAnimation(Animation.DEFAULT, 4, true);

        this.tick = -1;
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
    public void update() {
        super.update();

        if (tick >= 0) {
            tick--;
        }

        if (tick == 0) {
            sprite.playAnimation(Animation.DEFAULT, 4, true);
            setSolid(false);
        }
    }

    @Override
    public void burn() {
        removeFromBoard();
        currentBoard.put(new PowerUpExplosion(), getX(), getY());
    }

    @Override
    public void freeze() {
        sprite.playAnimation(Animation.FROZEN, true);
        setSolid(true);
        this.tick = 8 * Game.FPS;
    }

    public boolean isTemporary() {
        return false;
    }

    public void affect(Player player) {}; // TODO: make abstract
}
