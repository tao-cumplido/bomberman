package de.hsh.project.bomberman.game.menu;

import de.hsh.project.bomberman.game.Game;
import de.hsh.project.bomberman.game.battlemode.BattleState;
import de.hsh.project.bomberman.game.credits.CreditsState;
import de.hsh.project.bomberman.game.help.HelpMenuState;
import de.hsh.project.bomberman.game.highscore.HighScoreMenuState;
import de.hsh.project.bomberman.game.settings.SettingsMenuState;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Tao on 24.10.2015.
 */
public class TitleState extends MenuState {

    private JLabel titleLabel;
    private JButton settingButton;
    private JButton creditsButton;
    private JButton helpButton;
    private JButton highScoreButton;
    private JButton newGameButton;

    public TitleState() {
        GridBagLayout layout;
        layout = new GridBagLayout();
        this.setLayout(layout);


        titleLabel = new JLabel();
        settingButton = new JButton("Setting");
        creditsButton = new JButton("Credit");
        helpButton = new JButton("Help");
        highScoreButton = new JButton("HighScore");
        newGameButton = new JButton("New Game");

        setTitleLabel(titleLabel);
        setSettingButton(settingButton);
        setCreditsButton(creditsButton);
        setHelpButton(helpButton);
        setHighScoreButton(highScoreButton);
        setNewGameButton(newGameButton);

        this.add(titleLabel);
        this.add(creditsButton);
        this.add(settingButton);
        this.add(helpButton);
        this.add(highScoreButton);
        this.add(newGameButton);

        GridBagConstraints bagConstraints = new GridBagConstraints();
        bagConstraints.fill = GridBagConstraints.NORTH;
        bagConstraints.anchor = GridBagConstraints.CENTER;

        addGridBag(layout, titleLabel, bagConstraints, 0, 0, 2, 1, 0, 1);
        addGridBag(layout, newGameButton, bagConstraints, 1, 5, 1, 1, 0, 1);
        addGridBag(layout, settingButton, bagConstraints, 1, 6, 1, 1, 0, 1);
        addGridBag(layout, highScoreButton, bagConstraints, 1, 8, 1, 1, 0, 1);
        addGridBag(layout, helpButton, bagConstraints, 1, 10, 1, 1, 0, 1);
        addGridBag(layout, creditsButton, bagConstraints, 1, 12, 1, 1, 0, 1);
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


    protected void setTitleLabel(JLabel titleLabel) {
        this.titleLabel = titleLabel;
        String title = "Bomberman";
        this.titleLabel.setText(title);
        this.titleLabel.setFont(new Font(" Arial", Font.BOLD, 60));
    }

    protected void setCreditsButton(JButton creditsButton) {
        this.creditsButton = creditsButton;
        this.creditsButton.setPreferredSize(new Dimension(100, 30));
        this.creditsButton.addActionListener((event) -> Game.switchState(new CreditsState()));
    }

    protected void setHelpButton(JButton helpButton) {
        this.helpButton = helpButton;
        this.helpButton.setPreferredSize(new Dimension(100, 30));
        this.helpButton.addActionListener((event) -> Game.switchState(new HelpMenuState()));
    }

    protected void setHighScoreButton(JButton highScoreButton) {
        this.highScoreButton = highScoreButton;
        this.highScoreButton.setPreferredSize(new Dimension(100, 30));
        this.highScoreButton.addActionListener((event) -> Game.switchState(new HighScoreMenuState()));
    }

    protected void setNewGameButton(JButton newGameButton) {
        this.newGameButton = newGameButton;
        this.newGameButton.setPreferredSize(new Dimension(100, 30));
        this.newGameButton.addActionListener((event) -> Game.switchState(new BattleState()));
    }


    protected void setSettingButton(JButton settingButton) {
        this.settingButton = settingButton;
        this.settingButton.setPreferredSize(new Dimension(100, 30));
        this.settingButton.addActionListener((event) -> Game.switchState(new SettingsMenuState()));
    }
}
