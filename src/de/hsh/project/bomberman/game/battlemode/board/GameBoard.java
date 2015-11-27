package de.hsh.project.bomberman.game.battlemode.board;

import de.hsh.project.bomberman.game.battlemode.player.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by taocu on 26.10.2015.
 */
public abstract class GameBoard {

    public static final int TILE_SIZE = 48;

    private static final int GRID_WIDTH = 19;
    private static final int GRID_HEIGHT = 15;

    private BufferedImage staticBuffer;
    private BufferedImage dynamicBuffer;

    private ArrayList<Tile> staticBounds;
    private CopyOnWriteArrayList<Tile> tiles;

    private Player[] player;

    public GameBoard(Player[] player) {
        Tile.BOARD = this;

        this.player = player;

        this.tiles = new CopyOnWriteArrayList<>();

        this.staticBounds = new ArrayList<>();
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
            addStaticBlock(new HardBlock(x, 0), g);
            addStaticBlock(new HardBlock(x, GRID_HEIGHT - 1), g);
        }

        for (int y = 1; y < GRID_HEIGHT - 1; y++) {
            addStaticBlock(new HardBlock(0, y), g);
            addStaticBlock(new HardBlock(GRID_WIDTH - 1, y), g);
        }

        for (int x = 2; x < GRID_WIDTH - 2; x += 2) {
            for (int y = 2; y < GRID_HEIGHT - 2; y += 2) {
                addStaticBlock(new HardBlock(x, y), g);
            }
        }
    }

    private void addStaticBlock(HardBlock block, Graphics g) {
        staticBounds.add(block);
        g.drawImage(block.getFrame(), block.getLeft(), block.getTop(), null);
    }

    public void update() {
        player[0].update();
        tiles.forEach(Tile::update);
        resolveCollisions();
    }

    public void put(Tile tile) {
        tiles.add(tile);
}

    public BufferedImage getBuffer() {
        Graphics g = dynamicBuffer.getGraphics();
        g.drawImage(staticBuffer, 0, 0, null);
        tiles.forEach(tile -> g.drawImage(tile.getFrame(), tile.getLeft(), tile.getTop(), null));
        g.drawImage(player[0].getFrame(), player[0].getLeft(), player[0].getTop() - TILE_SIZE, null);
        return dynamicBuffer;
    }

    private void resolveCollisions() {
        for (Tile t : staticBounds) {
            for (int i = 0; i < 1; i++) {
                Rectangle collision = player[i].collides(t);

                if (!collision.isEmpty()) {
                    int dx = player[i].getLeft() % TILE_SIZE;
                    int dy = player[i].getTop() % TILE_SIZE;

                    if (collision.getWidth() < collision.getHeight()) {
                        // horizontal collision
                        if (dx > TILE_SIZE / 2) {
                            // moving left
                            player[i].translateX(TILE_SIZE - dx);
                        } else {
                            // moving right
                            player[i].translateX(-dx);
                        }

                        if (player[i].getTop() < collision.y) {
                            player[i].translateY(-player[i].getSpeed());
                        } else if (player[i].getBottom() > collision.getMaxY()) {
                            player[i].translateY(player[i].getSpeed());
                        }
                    } else {
                        // vertical collision
                        if (dy > TILE_SIZE / 2) {
                            // moving up
                            player[i].translateY(TILE_SIZE - dy);
                        } else {
                            // moving down
                            player[i].translateY(-dy);
                        }

                        if (player[i].getLeft() < collision.x) {
                            player[i].translateX(-player[i].getSpeed());
                        } else if (player[i].getRight() > collision.getMaxX()) {
                            player[i].translateX(player[i].getSpeed());
                        }
                    }
                }
            }
        }
    }
}
