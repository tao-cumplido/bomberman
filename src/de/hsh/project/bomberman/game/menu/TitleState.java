package de.hsh.project.bomberman.game.menu;

import de.hsh.project.bomberman.game.Game;
import de.hsh.project.bomberman.game.Resource;
import de.hsh.project.bomberman.game.battlemode.BattleState;
import de.hsh.project.bomberman.game.credits.CreditsState;
import de.hsh.project.bomberman.game.help.HelpMenuState;
import de.hsh.project.bomberman.game.highscore.HighScoreMenuState;
import de.hsh.project.bomberman.game.settings.SettingsMenuState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * Created by Tao on 24.10.2015.
 */
public class TitleState extends MenuState {

    private FontImage titleLabel;
    private FontImage settingButton;
    private FontImage creditsButton;
    private FontImage helpButton;
    private FontImage highScoreButton;
    private FontImage newGameButton;
    private FontImage exitGameButton;

    private BufferedImage cover;



    public TitleState() {

        cover = Resource.loadImage("1cover.png");

        GridBagLayout layout;
        layout = new GridBagLayout();
        this.setLayout(layout);


        titleLabel = new FontImage("Bomberman",10,false);
        settingButton = new FontImage("Setting",5,true);
        creditsButton = new FontImage("Credit",5,true);
        helpButton = new FontImage("Help",5,true);
        highScoreButton = new FontImage("HighScore",5,true);
        newGameButton = new FontImage("start",5,true);
        exitGameButton = new FontImage("exit",5,true);

        setExitGameButton(exitGameButton);
        setNewGameButton(newGameButton);
        setSettingButton(settingButton);
        setHelpButton(helpButton);
        setHighScoreButton(highScoreButton);
        setCreditsButton(creditsButton);


        this.add(titleLabel);
        this.add(creditsButton);
        this.add(settingButton);
        this.add(helpButton);
        this.add(highScoreButton);
        this.add(newGameButton);
        this.add(exitGameButton);

        GridBagConstraints bagConstraints = new GridBagConstraints();

        addGridBag(layout, titleLabel, bagConstraints, 0, 0, 2, 1, 0, 4);
        addGridBag(layout, newGameButton, bagConstraints, 1, 5, 1, 1, 0, 1);
        addGridBag(layout, settingButton, bagConstraints, 1, 6, 1, 1, 0, 1);
        addGridBag(layout, highScoreButton, bagConstraints, 1, 7, 1, 1, 0, 1);
        addGridBag(layout, helpButton, bagConstraints, 1, 8, 1, 1, 0, 1);
        addGridBag(layout, creditsButton, bagConstraints, 1, 9, 1, 1, 0, 1);
        addGridBag(layout, exitGameButton, bagConstraints, 1, 10, 1, 1, 0, 1);

    }


    public void addGridBag(GridBagLayout layout, Component c, GridBagConstraints constraints, int x, int y, int w, int h, int wx, int wy) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = w;
        constraints.gridheight = h;
        constraints.weightx = wx;
        constraints.weighty = wy;
        layout.setConstraints(c, constraints);
    }

    protected void setExitGameButton(FontImage exitGameButton){
        this.exitGameButton = exitGameButton;
        this.exitGameButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (cover!= null) {
            g.drawImage(cover, 0, 0, this);
        }
    }

    @Override
    protected void setPanelPosition(){
        newGameButton.setPanelPoint();
        creditsButton.setPanelPoint();
        exitGameButton.setPanelPoint();
        settingButton.setPanelPoint();
        highScoreButton.setPanelPoint();
        helpButton.setPanelPoint();

    }

    protected void setNewGameButton(FontImage newGameButton) {
        this.newGameButton = newGameButton;
        this.newGameButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Game.switchState(new BattleState());
            }
        });
    }
    public void setHighScoreButton(FontImage highScoreButton) {
        this.highScoreButton = highScoreButton;
        this.highScoreButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Game.switchState(new HighScoreMenuState());
            }
        });
    }

    public void setHelpButton(FontImage helpButton) {
        this.helpButton = helpButton;
        this.helpButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Game.switchState(new HelpMenuState());
            }
        });
    }

    public void setCreditsButton(FontImage creditsButton) {
        this.creditsButton = creditsButton;
        this.creditsButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Game.switchState(new CreditsState());
            }
        });
    }

    public void setSettingButton(FontImage settingButton) {
        this.settingButton = settingButton;
        this.settingButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Game.switchState(new SettingsMenuState());
            }
        });
    }

}
