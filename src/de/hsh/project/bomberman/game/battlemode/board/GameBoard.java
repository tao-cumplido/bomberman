package de.hsh.project.bomberman.game.battlemode.board;

import de.hsh.project.bomberman.game.battlemode.player.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * Created by taocu on 26.10.2015.
 */
public abstract class GameBoard {

    public static final int TILE_SIZE = 48;

    protected static final int GRID_WIDTH = 19;
    protected static final int GRID_HEIGHT = 15;

    private BufferedImage staticBuffer;
    private BufferedImage dynamicBuffer;

    private Tile[][] grid;

    private Player[] player;

    public GameBoard(Player[] player) {
        Tile.BOARD = this;

        this.player = player;

        this.grid = new Tile[GRID_WIDTH][GRID_HEIGHT];

        this.staticBuffer = new BufferedImage(GRID_WIDTH * TILE_SIZE, GRID_HEIGHT * TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
        this.dynamicBuffer = new BufferedImage(GRID_WIDTH * TILE_SIZE, GRID_HEIGHT * TILE_SIZE, BufferedImage.TYPE_INT_ARGB);

        BufferedImage floor = null;

        try {
            floor = ImageIO.read(getClass().getResource("/res/images/floor.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Graphics g = staticBuffer.getGraphics();

        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                g.drawImage(floor, x * TILE_SIZE, y * TILE_SIZE, null);
            }
        }

        for (int x = 0; x < GRID_WIDTH; x++) {
            put(new HardBlock(), x, 0);
            put(new HardBlock(), x, GRID_HEIGHT - 1);
        }

        for (int y = 1; y < GRID_HEIGHT - 1; y++) {
            put(new HardBlock(), 0, y);
            put(new HardBlock(), GRID_WIDTH - 1, y);
        }

        for (int x = 2; x < GRID_WIDTH - 2; x += 2) {
            for (int y = 2; y < GRID_HEIGHT - 2; y += 2) {
                put(new HardBlock(), x, y);
            }
        }
    }

    public void update() {
        player[0].update();
        player[1].update();
        for (Tile[] row : grid) for (Tile tile : row) if (tile != null) {
            tile.update();
        }
        resolveCollisions();
    }

    public void put(Tile tile, int x, int y) {
        //tiles.add(tile);
        if (grid[x][y] != null) {
            System.out.println("Warning: grid[" + x + "][" + y + "] is not empty!");
        }

        grid[x][y] = tile;
        tile.setPosition(x, y);
    }

    public void remove(int x, int y) {
        grid[x][y] = null;
    }

    public Tile getTile(int x, int y) {
        return grid[x][y];
    }

    public boolean fieldIsBlocked(int x, int y) {
        return grid[x][y] != null && grid[x][y].isSolid();
    }

    public BufferedImage getBuffer() {
        Graphics g = dynamicBuffer.getGraphics();
        g.drawImage(staticBuffer, 0, 0, null);

        for (Tile[] row : grid) for (Tile tile : row) if (tile != null) {
            g.drawImage(tile.getFrame(), tile.getLeft(), tile.getTop(), null);
        }

        for (int i = 0; i < 2; i++) {
            if (player[i].isAlive()) {
                g.drawImage(player[i].getFrame(), player[i].getLeft(), player[i].getTop() - TILE_SIZE, null);
            }
        }
        return dynamicBuffer;
    }

    private void resolveCollisions() {
        for (Tile[] row : grid) for (Tile tile : row) if (tile != null) {
            for (int i = 0; i < 2; i++) {
                tile.onCollision(player[i]);
            }
        }
    }

    protected void fillRandomSoftBlocks() {
        int p1x = player[0].getX();
        int p1y = player[0].getY();

        Random generate = new Random();

        for (int x = 1; x < GRID_WIDTH - 1; x++) {
            fill: for (int y = 1; y < GRID_HEIGHT - 1; y += (x % 2 == 0) ? 2 : 1) {
                for (int i = 0; i < 2; i++) {
                    int px = player[i].getX();
                    int py = player[i].getY();
                    if (x == px && y == py) continue fill;
                    if (x == px + 1 && y == py) continue fill;
                    if (x == px - 1 && y == py) continue fill;
                    if (x == px && y == py + 1) continue fill;
                    if (x == px && y == py - 1) continue fill;
                    if (x == px + 1 && y == py + 1) continue fill;
                    if (x == px + 1 && y == py - 1) continue fill;
                    if (x == px - 1 && y == py + 1) continue fill;
                    if (x == px - 1 && y == py - 1) continue fill;
                }

                if (generate.nextDouble() > 0.3) {
                    put(new SoftBlock(), x, y);
                }
            }
        }
    }
}
