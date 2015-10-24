package de.hsh.project.bomberman.game.state;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Tao on 24.10.2015.
 */
public abstract class MenuState extends GameState {

    private static BufferedImage BACKGROUND;

    public MenuState() {
        try {
            BACKGROUND = ImageIO.read(getClass().getResource("/res/images/menu-bg.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (BACKGROUND != null) {
            g.drawImage(BACKGROUND, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
