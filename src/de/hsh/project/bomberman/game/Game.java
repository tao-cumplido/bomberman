package de.hsh.project.bomberman.game;

import de.hsh.project.bomberman.game.state.GameState;
import de.hsh.project.bomberman.game.state.TitleState;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Tao on 24.10.2015.
 */
public class Game extends JFrame {

    private static Game THIS; // lazy singleton

    public static void switchState(GameState state) {
        THIS.getContentPane().removeAll();
        THIS.add(state, BorderLayout.CENTER);
        THIS.pack();
    }

    public Game() {
        super("Bomberman");
        THIS = this;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        setVisible(true);
        switchState(new TitleState());
    }
}
