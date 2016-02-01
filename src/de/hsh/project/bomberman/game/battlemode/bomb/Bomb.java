package de.hsh.project.bomberman.game.battlemode.bomb;

import de.hsh.project.bomberman.game.Game;
import de.hsh.project.bomberman.game.battlemode.board.GameBoard;
import de.hsh.project.bomberman.game.battlemode.board.Tile;
import de.hsh.project.bomberman.game.battlemode.gfx.AnimationID;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * Created by taocu on 26.10.2015.
 */
public abstract class Bomb extends Tile {

    protected enum Animation implements AnimationID {
        DEFAULT
    }

    private int tick;

    protected int range;

    private ArrayList<Bomb> queue;

    private Trigger trigger;

    public Bomb(int range, ArrayList<Bomb> queue, Trigger trigger) {
        super(true, GameBoard.TILE_SIZE);
        this.tick = 0;
        this.range = range;
        this.queue = queue;
        this.trigger = trigger;

        queue.add(this);
    }

    @Override
    public void update() {
        super.update();
        if (trigger == Trigger.TIME) {
            if (tick++ == 3 * Game.FPS) {
                detonate();
            }
        }
    }

    @Override
    public boolean isBomb() {
        return true;
    }

    @Override
    public void burn() {
        detonate();
    }

    public void detonate() {
        currentBoard.remove(getX(), getY());
        queue.remove(this);
    }

    protected Tile[] extend(Function<Integer, Blast> blastConstructor) {
        currentBoard.put(blastConstructor.apply(0), getX(), getY());

        Tile[] tiles = new Tile[4];

        tiles[0] = extend(4, 5, -1, 0, blastConstructor);    // LEFT
        tiles[1] = extend(4, 6, 1, 0, blastConstructor);     // RIGHT
        tiles[2] = extend(1, 3, 0, -1, blastConstructor);    // UP
        tiles[3] = extend(1, 2, 0, 1, blastConstructor);     // DOWN

        return tiles;
    }

    protected Tile extend(int regular, int tip, int dx, int dy, Function<Integer, Blast> blastConstructor) {
        int x = getX();
        int y = getY();

        for (int i = 1; i <= range; i++) {
            Tile tile = currentBoard.getTile(x + dx * i, y + dy * i);
            if (tile != Tile.EMPTY) {
                if (tile.isBlast()) {
                    tile.burn();
                } else {
                    return tile;
                }
            }
            currentBoard.put(blastConstructor.apply((i == range) ? tip : regular), x + dx * i, y + dy * i);
        }

        return Tile.EMPTY;
    }
}
