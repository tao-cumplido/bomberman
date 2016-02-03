package de.hsh.project.bomberman.game.battlemode.board;

import de.hsh.project.bomberman.game.Resource;
import de.hsh.project.bomberman.game.battlemode.player.Player;
import de.hsh.project.bomberman.game.battlemode.powerup.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.TreeMap;

/**
 * Created by taocu on 26.10.2015.
 */
public abstract class GameBoard {

    public static final int TILE_SIZE = 64;

    public static final int GRID_WIDTH = 19;
    public static final int GRID_HEIGHT = 13;

    public static int pixel2Grid(int p) {
        return p / TILE_SIZE;
    }

    private BufferedImage staticBuffer;
    private BufferedImage dynamicBuffer;

    private Tile[] grid;
    private Tile[] floor;
    private int frozenFloorCounter;

    private Player[] player;

    public GameBoard(Player[] player) {
        Tile.currentBoard = this;

        this.player = player;

        this.grid = new Tile[GRID_WIDTH * GRID_HEIGHT];
        Arrays.fill(grid, Tile.EMPTY);

        this.floor = new Tile[GRID_WIDTH * GRID_HEIGHT];
        Arrays.fill(floor, Tile.EMPTY);
        this.frozenFloorCounter = 0;

        this.staticBuffer = new BufferedImage(GRID_WIDTH * TILE_SIZE, GRID_HEIGHT * TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
        this.dynamicBuffer = new BufferedImage(GRID_WIDTH * TILE_SIZE, GRID_HEIGHT * TILE_SIZE, BufferedImage.TYPE_INT_ARGB);

        BufferedImage floor = Resource.loadImage("floor.png");

        Graphics g = staticBuffer.getGraphics();

        for (int x = 1; x < GRID_WIDTH - 1; x++) {
            for (int y = 1; y < GRID_HEIGHT - 1; y++) {
                g.drawImage(floor, x * TILE_SIZE, y * TILE_SIZE, null);
            }
        }

        for (int x = 1; x < GRID_WIDTH - 1; x++) {
            put(new HardBlock(3), x, 0);
            put(new HardBlock(0), x, GRID_HEIGHT - 1);
        }

        for (int y = 0; y < GRID_HEIGHT - 1; y++) {
            put(new HardBlock(4), 0, y);
            put(new HardBlock(4), GRID_WIDTH - 1, y);
        }

        for (int x = 2; x < GRID_WIDTH - 2; x += 2) {
            for (int y = 2; y < GRID_HEIGHT - 2; y += 2) {
                put(new HardBlock(1), x, y);
            }
        }
    }

    public void update() {
        player[0].update();
        player[1].update();
        player[2].update();
        player[3].update();

        if (frozenFloorCounter > 0) {
            for (Tile tile : floor) if (tile != Tile.EMPTY) {
                tile.update();
            }
        }
        for (Tile tile : grid) if (tile != Tile.EMPTY) {
            tile.update();
        }
        applyCollisions();
    }

    private int index2D(int x, int y) {
        return (x % GRID_WIDTH) + (y * GRID_WIDTH);
    }

    public void put(Tile tile, int x, int y) {
        int index = index2D(x, y);
        if (grid[index] != Tile.EMPTY) {
            System.out.println("Warning: grid[" + x + "][" + y + "] is not empty!");
        }

        grid[index] = tile;
        tile.setPosition(x, y);
    }

    public void remove(int x, int y) {
        grid[index2D(x, y)] = Tile.EMPTY;
    }

    public Tile getTile(int x, int y) {
        return grid[index2D(x, y)];
    }

    public boolean fieldIsBlocked(int x, int y) {
        return grid[index2D(x, y)].isSolid();
    }

    public void freezeFloor(int x, int y) {
        int xy = index2D(x, y);

        if (floor[xy] == Tile.EMPTY) {
            floor[xy] = new FrozenFloor();
            floor[xy].setPosition(x, y);
            frozenFloorCounter++;
        }
    }

    public void meltFloor(int x, int y) {
        int xy = index2D(x, y);

        if (floor[xy] != Tile.EMPTY) {
            floor[xy] = Tile.EMPTY;
            frozenFloorCounter--;
        }
    }

    public boolean isFrozenFloor(int x, int y) {
        return floor[index2D(x, y)] != Tile.EMPTY;
    }

    public BufferedImage getBuffer() {
        Player[] undrawnPlayers;
        TreeMap<Integer, Player> playersToDraw;

        Graphics g = dynamicBuffer.getGraphics();
        g.drawImage(staticBuffer, 0, 0, null);

        if (frozenFloorCounter > 0) {
            for (Tile tile : floor) if (tile != Tile.EMPTY) {
                g.drawImage(tile.getFrame(), tile.getLeft(), tile.getTop(), null);
            }
        }

        undrawnPlayers = this.player.clone();

        for (Tile tile : grid) {
            if (tile != Tile.EMPTY) {
                g.drawImage(tile.getFrame(), tile.getLeft(), tile.getTop() - tile.getVOffset(), null);
            }

            if (tile.getX() == GRID_WIDTH - 1) {
                playersToDraw = new TreeMap<>();
                for (int i = 0; i < 4; i++) {
                    Player p = undrawnPlayers[i];
                    if (p != null && p.isAlive() && p.getY() == tile.getY()) {
                        if (playersToDraw.containsKey(p.getTop())) {
                            playersToDraw.put(p.getTop() + p.getPlayerNumber(), p);
                        } else {
                            playersToDraw.put(p.getTop(), p);
                        }
                        undrawnPlayers[i] = null;
                    }
                }

                for (Player p : playersToDraw.values()) {
                    g.drawImage(p.getFrame(), p.getLeft(), p.getTop() - p.getVOffset(), null);
                }
            }
        }

        return dynamicBuffer;
    }

    private void applyCollisions() {
        for (Tile tile : grid) if (tile != Tile.EMPTY) {
            for (int i = 0; i < 4; i++) {
                tile.onCollision(player[i]);
            }
        }
    }

    public void sprayPowerUps(ArrayList<PowerUp> powerUps) {
        Random r = new Random();
        for (PowerUp p : powerUps) {
            int x, y;

            do {
                x = r.nextInt(GRID_WIDTH - 2) + 1;
                y = r.nextInt(GRID_HEIGHT - 2) + 1;
            } while (fieldIsBlocked(x, y));

            put(p, x, y);
        }
    }

    protected void fillRandomSoftBlocks() {
        Random generate = new Random();

        for (int x = 1; x < GRID_WIDTH - 1; x++) {
            fill: for (int y = 1; y < GRID_HEIGHT - 1; y += (x % 2 == 0) ? 2 : 1) {
                for (int i = 0; i < 4; i++) {
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
                    PowerUp powerUp = null;

                    double spf = generate.nextDouble();
                    if ((x > 4 && x < GRID_WIDTH - 4) && (y > 3 && y < GRID_HEIGHT - 3)) {
                        if (spf > 0.3) {
                            double type = generate.nextDouble();
                            if (type < 0.2) {
                                powerUp = new BombUp();
                            } else if (type < 0.4) {
                                powerUp = new RangeUp();
                            } else if (type < 0.6) {
                                powerUp = new SpeedUp();
                            } else if (type < 0.9) {
                                powerUp = new Surprise();
                            } else {
                                powerUp = new RemoteControl();
                            }
                        }
                    } else if (spf > 0.6) {
                        double type = generate.nextDouble();
                        if (type < 0.5) {
                            powerUp = new BombUp();
                        } else if (type < 0.8) {
                            powerUp = new RangeUp();
                        } else {
                            powerUp = new SpeedUp();
                        }
                    }

                    put(new SoftBlock(powerUp), x, y);
                }
            }
        }
    }
}
