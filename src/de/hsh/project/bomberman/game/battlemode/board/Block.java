package de.hsh.project.bomberman.game.battlemode.board;

/**
 * Created by taocu on 11.12.2015.
 */
public abstract class Block extends Tile {

    protected int frozen;

    public Block() {
        super(true);
        this.frozen = 0;
    }

    @Override
    public void freeze() {
        this.frozen = 240;
        currentBoard.freezeFloor(getX(), getY());
    }

    @Override
    public void burn() {
        frozen = 0;
        currentBoard.meltFloor(getX(), getY());
    }

    @Override
    public void update() {
        super.update();
        if (frozen > 0) {
            frozen--;
        }
    }
}
