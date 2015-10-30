package de.hsh.project.bomberman.game;

import de.hsh.project.bomberman.game.state.BattleState;
import de.hsh.project.bomberman.game.state.GameState;
import de.hsh.project.bomberman.game.state.TitleState;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Tao on 24.10.2015.
 */
public class Game extends JFrame {

    public static int FPS = 60;

    private static Game THIS; // lazy singleton

    public static void switchState(GameState state) {
        THIS.getContentPane().removeAll();
        THIS.add(state, BorderLayout.CENTER);
        THIS.pack(); // TODO: possibly move to constructor after first switchState
    }

    public Game() {
        super("Bomberman");
        THIS = this;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        setVisible(true);
        // switchState(new TitleState());
        switchState(new BattleState());
    }
}
