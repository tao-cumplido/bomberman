package de.hsh.project.bomberman.game.battlemode.board;

import de.hsh.project.bomberman.game.battlemode.player.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by taocu on 26.10.2015.
 */
public abstract class GameBoard {

    public static final int TILE_SIZE = 16;

    private static final int GRID_WIDTH = 19;
    private static final int GRID_HEIGHT = 15;

    private BufferedImage staticBuffer;
    private BufferedImage dynamicBuffer;

    private ArrayList<Rectangle> staticBounds;

    private Player[] player;

    public GameBoard(Player[] player) {
        this.player = player;

        this.staticBounds = new ArrayList<>();
        this.staticBuffer = new BufferedImage(GRID_WIDTH * TILE_SIZE, GRID_HEIGHT * TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
        this.dynamicBuffer = new BufferedImage(GRID_WIDTH * TILE_SIZE, GRID_HEIGHT * TILE_SIZE, BufferedImage.TYPE_INT_ARGB);

        Graphics g = staticBuffer.getGraphics();
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, staticBuffer.getWidth(), staticBuffer.getHeight());

        g.setColor(Color.RED);

        addStaticRect(0, 0, GRID_WIDTH * TILE_SIZE, TILE_SIZE, g);
        addStaticRect(0, (GRID_HEIGHT - 1) * TILE_SIZE, GRID_WIDTH * TILE_SIZE, TILE_SIZE, g);
        addStaticRect(0, TILE_SIZE, TILE_SIZE, (GRID_HEIGHT - 2) * TILE_SIZE, g);
        addStaticRect((GRID_WIDTH - 1) * TILE_SIZE, TILE_SIZE, TILE_SIZE, (GRID_HEIGHT - 2) * TILE_SIZE, g);

        for (int x = 2; x < GRID_WIDTH - 2; x += 2) {
            for (int y = 2; y < GRID_HEIGHT - 2; y += 2) {
                addStaticRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, g);
            }
        }
    }

    private void addStaticRect(int x, int y, int width, int height, Graphics g) {
        staticBounds.add(new Rectangle(x, y, width, height));
        g.fillRect(x, y, width, height);
    }

    public void update() {
        player[0].update();
        resolveCollisions();
    }

    public BufferedImage getBuffer() {
        Graphics g = dynamicBuffer.getGraphics();
        g.drawImage(staticBuffer, 0, 0, null);
        g.drawImage(player[0].getFrame(), player[0].getX(), player[0].getY()-16, null);
        return dynamicBuffer;
    }

    private void resolveCollisions() {
        for (Rectangle r : staticBounds) {
            for (int i = 0; i < 1; i++) {
                Rectangle bounds = player[i].getBounds();
                Rectangle collision = bounds.intersection(r);

                if (!collision.isEmpty()) {
                    int dx = bounds.x % 16;
                    int dy = bounds.y % 16;

                    if (collision.getWidth() < collision.getHeight()) {
                        // horizontal collision
                        if (dx > 8) {
                            // moving left
                            bounds.x -= dx - 16;
                        } else {
                            // moving right
                            bounds.x -= dx;
                        }

                        if (bounds.y < collision.y) {
                            bounds.y -= player[i].getSpeed();
                        } else if (bounds.getMaxY() > collision.getMaxY()) {
                            bounds.y += player[i].getSpeed();
                        }
                    } else {
                        // vertical collision
                        bounds.y -= (dy > 8) ? dy - 16 : dy;

                        if (bounds.x < collision.x) {
                            bounds.x -= player[i].getSpeed();
                        } else if (bounds.getMaxX() > collision.getMaxX()) {
                            bounds.x += player[i].getSpeed();
                        }
                    }
                }
            }
        }
    }
}
