package de.hsh.project.bomberman.game.battlemode.bomb;

import de.hsh.project.bomberman.game.battlemode.board.Tile;
import de.hsh.project.bomberman.game.battlemode.gfx.Sprite;
import de.hsh.project.bomberman.game.battlemode.player.Direction;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by taocu on 26.10.2015.
 */
public class FireBomb extends Bomb {

    private static BufferedImage spriteSheet = Sprite.loadSpriteSheet("/res/images/firebomb.png");

    public FireBomb(int range, ArrayList<Bomb> queue) {
        super(range, queue);

        this.sprite = new Sprite(spriteSheet);
        this.sprite.addAnimation(Animation.DEFAULT, 0, 1, 2, 1);
        this.sprite.playAnimation(Animation.DEFAULT, 20, true);
    }

    @Override
    public void detonate() {
        super.detonate();
        currentBoard.put(new FireBlast(0), getX(), getY());
        extend(Direction.LEFT);
        extend(Direction.RIGHT);
        extend(Direction.UP);
        extend(Direction.DOWN);
    }

    private void extend(Direction direction) {
        switch (direction) {
            case LEFT: extend(4, 5, -1, 0); break;
            case RIGHT: extend(4, 6, 1, 0); break;
            case UP: extend(1, 3, 0, -1); break;
            case DOWN: extend(1, 2, 0, 1); break;
        }
    }

    private void extend(int regular, int tip, int dx, int dy) {
        int x = getX();
        int y = getY();

        for (int i = 1; i <= range; i++) {
            Tile tile = currentBoard.getTile(x + dx * i, y + dy * i);
            if (tile != Tile.EMPTY) {
                tile.burn();
                if (!tile.isBlast()) return;
            }
            currentBoard.put(new FireBlast((i == range) ? tip : regular), x + dx * i, y + dy * i);
        }
    }
}