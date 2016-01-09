package de.hsh.project.bomberman.game.menu;

import de.hsh.project.bomberman.game.Game;
import de.hsh.project.bomberman.game.GameState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Tao on 24.10.2015.
 */
public abstract class MenuState extends GameState {

    private static BufferedImage BACKGROUND;

    public MenuState() {
        if (BACKGROUND == null) {
            try {
                BACKGROUND = ImageIO.read(getClass().getResource("/res/images/cover8.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setListener();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (BACKGROUND != null) {
            g.drawImage(BACKGROUND, 0, 0, this);
        }
    }

    protected void setListener(){
        MouseAdapter mo = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                    setPanelPosition();
            }
        };
        this.addMouseListener(mo);
        this.addMouseMotionListener(mo);
    }

    protected void setPanelPosition(){}

    protected void setBackButton(FontImage image) {
        image.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(setBackCondition())
                    Game.switchState(new TitleState());
            }
        });
    }

    protected boolean setBackCondition(){
        return true;
    }
}
