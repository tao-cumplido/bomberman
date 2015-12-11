package de.hsh.project.bomberman.game;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Tao on 24.10.2015.
 */
public abstract class GameState extends JPanel {

    public GameState() {
        //setPreferredSize(new Dimension(912, 720));
        setFocusable(true);
    }
}
