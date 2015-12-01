package de.hsh.project.bomberman.game;

import de.hsh.project.bomberman.game.battlemode.BattleState;
import de.hsh.project.bomberman.game.menu.TitleState;
import de.hsh.project.bomberman.game.settings.SettingsMenuState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
//        addWindowListener(new WindowAdapter() {
//
//            @Override
//            public void windowClosing(WindowEvent e) {
//                int i =JOptionPane.showConfirmDialog(null, "Do you want to exit the game?", "Exit?", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
//                if(i == JOptionPane.YES_OPTION){
//
//                    System.exit(0);
//                }
//
//                return;
//            }
//
//        });

        //Dialog displays in the middle
        setPreferredSize(new Dimension(920,540));
        int w=(getToolkit().getScreenSize().width -getPreferredSize().width)/2;
        int h=(getToolkit().getScreenSize().height-getPreferredSize().height)/2;
        setLocation(w, h);

        setResizable(false);
        setLayout(new BorderLayout());
        setVisible(true);
        //switchState(new TitleState());
        switchState(new de.hsh.project.bomberman.game.battlemode.BattleState());
    }
}
