package de.hsh.project.bomberman.game;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Tao on 24.10.2015.
 */
public abstract class GameState extends JPanel {

    public GameState() {
//<<<<<<< HEAD
        setPreferredSize(new Dimension(1280, 720));
//=======
      // setPreferredSize(new Dimension(29*48, 17*48));//1392*816
//>>>>>>> 3008903d7bc0f60e59c21e05f29b56fded825cdb
        setFocusable(true);
    }
}
