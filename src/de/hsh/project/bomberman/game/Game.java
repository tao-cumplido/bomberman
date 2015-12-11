package de.hsh.project.bomberman.game;

import de.hsh.project.bomberman.game.menu.TitleState;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Tao on 24.10.2015.
 */
public class Game extends JFrame {

    public static int FPS = 30;

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
// center application window on screen
        setPreferredSize(new Dimension(912, 720));
        int x = (getToolkit().getScreenSize().width - getPreferredSize().width) / 2;
        int y = (getToolkit().getScreenSize().height - getPreferredSize().height) / 2;
        setLocation(x, y);


        setResizable(false);
        setVisible(true);
        switchState(new TitleState());
    }
}
