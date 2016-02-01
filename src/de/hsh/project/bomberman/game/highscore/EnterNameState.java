package de.hsh.project.bomberman.game.highscore;


import de.hsh.project.bomberman.game.Game;
import de.hsh.project.bomberman.game.GameState;
import de.hsh.project.bomberman.game.menu.MenuState;

import javax.swing.*;

/**
 * Created by taocu on 26.10.2015.
 */

//  new EnterNameState((new HighScore(null,12,"easy",3,1.2,"one")));
public class EnterNameState  {

    public EnterNameState(HighScore highScore) {
        String name = JOptionPane.showInputDialog("Name: ");
        if(name==null||name.equals(""))
            name ="NN";
        if(name.length()>8)
            name = name.substring(0,8);
        highScore.setName(name);
        HighScoreFile.addScore(highScore);
    }

}