package de.hsh.project.bomberman.game.credits;

import de.hsh.project.bomberman.game.menu.FontImage;
import de.hsh.project.bomberman.game.menu.MenuState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by taocu on 26.10.2015.
 */
public class CreditsState extends MenuState {

    private FontImage back;

    private BufferedImage credits;

    public CreditsState(){
        if (credits == null) {
            try {
                credits = ImageIO.read(getClass().getResource("/res/images/credit3.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FontImage title = new FontImage("bomberman",10,false);
        back = new FontImage("back",5,true);
        this.add(back);
        this.add(title);
        this.setLayout(null);
        title.setBounds((int) (getPreferredSize().getWidth()*0.5-5*9*8), (int) (getPreferredSize().getHeight()*0.05),10*8*9,10*8);
        back.setBounds((int) (getPreferredSize().getWidth()*0.05), (int) (getPreferredSize().getHeight()*0.92),5*8*4,5*8);

        setBackButton(back);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (credits != null) {
            g.drawImage(credits, 0, 0, this);
        }
    }
    @Override
    protected void setPanelPosition(){
        back.setPanelPoint();
    }
}
