package de.hsh.project.bomberman.game.battlemode.board;


import de.hsh.project.bomberman.game.Resource;
import de.hsh.project.bomberman.game.battlemode.gfx.AnimationID;
import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;
import de.hsh.project.bomberman.game.battlemode.powerup.PowerUp;

import java.awt.image.BufferedImage;

/**
 * Created by taocu on 26.10.2015.
 */
public class SoftBlock extends Block {

    private static BufferedImage spriteSheet = Resource.loadImage("softblock.png");

    private enum Animation implements AnimationID {
        DEFAULT, DISSOLVE
    }

    private PowerUp powerUp;

    public SoftBlock(PowerUp powerUp) {
        this.sprite = new Sprite(spriteSheet);
        this.sprite.addAnimation(Animation.DEFAULT, 0);
        this.sprite.addAnimation(Animation.DISSOLVE, 2, 3, 4);
        this.sprite.playAnimation(Animation.DEFAULT, false);

        this.powerUp = powerUp;
    }

    @Override
    public void burn() {
        super.burn();
        this.sprite.playAnimation(Animation.DISSOLVE, 4, this::removeFromBoard);
    }

    @Override
    public void removeFromBoard() {
        super.removeFromBoard();
        if (powerUp != null) {
            currentBoard.put(powerUp, getX(), getY());
        }
    }

    @Override
    public boolean isBlock() {
        return true;
    }

    @Override
    public BufferedImage getFrame() {
        return (frozen > 0) ? sprite.getFrame(1) : sprite.getCurrentFrame();
    }
}
