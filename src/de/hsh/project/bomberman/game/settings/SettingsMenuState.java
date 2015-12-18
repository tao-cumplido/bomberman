package de.hsh.project.bomberman.game.settings;

import de.hsh.project.bomberman.game.Game;
import de.hsh.project.bomberman.game.menu.MenuState;
import de.hsh.project.bomberman.game.menu.TitleState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * Created by taocu on 26.10.2015.
 */
public class SettingsMenuState extends MenuState {

    private JPanel tll;
    private JPanel s_player;

    private SettingsPlayer player1;
    private SettingsPlayer player2;
    private SettingsPlayer player3;
    private SettingsPlayer player4;


    private BufferedImage playerHead;


    private JComboBox<String> time;
    private JComboBox<Integer> life;
    private JComboBox<String> board;
    private JPanel levelPanel;


    private JButton reToGame;


    public SettingsMenuState() {
        super();

        tll = new JPanel();
        s_player = new JPanel();


        buildTll();
        buildPlayer();

        reToGame = new JButton("Start new Game");
        setReToGame(reToGame);


        this.setLayout(new BorderLayout());
        this.add(tll, BorderLayout.NORTH);
        this.add(s_player, BorderLayout.CENTER);
        this.add(reToGame, BorderLayout.SOUTH);
    }

    private void setReToGame(JButton reToGame) {
        this.reToGame = reToGame;
        this.reToGame.addActionListener((event) -> {
            if (player1.getPlayerTyp().getSelectedItem().equals("Computer") && player2.getPlayerTyp().getSelectedItem().equals("Computer") &&
                    player3.getPlayerTyp().getSelectedItem().equals("Computer") && player4.getPlayerTyp().getSelectedItem().equals("Computer")) {
                JOptionPane.showMessageDialog(null, "At least one player", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                int i = JOptionPane.showConfirmDialog(null, "Do you want to save the settings?", "Save", JOptionPane.YES_NO_OPTION);
                if (JOptionPane.YES_OPTION == i){
                    Settings.write();
                }

                Game.switchState(new TitleState());
            }
        });
    }


    private void buildTll() {

        setTime();
        setLife();
        setBoard();
        setLevel();

        tll.setLayout(new FlowLayout());
        tll.add(new JLabel("Time: "));
        tll.add(time);
        tll.add(new JLabel("Life: "));
        tll.add(life);
        tll.add(new JLabel("Board: "));
        tll.add(board);
        tll.add(new JLabel("Difficulty: "));
        tll.add(levelPanel);
    }


    private void buildPlayer() {
        try {
            playerHead = ImageIO.read(getClass().getResource("/res/images/settings/4Player_Head.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        player1 = new SettingsPlayer(SettingsTyp.PLAYER1, playerHead.getSubimage(0, 0, 17, 18), 1, Settings.getPlayer1());
        Settings.setPlayer1(player1.getPlayerMap());
        player2 = new SettingsPlayer(SettingsTyp.PLAYER2, playerHead.getSubimage(17, 0, 17, 18), 2, Settings.getPlayer2());
        Settings.setPlayer2(player2.getPlayerMap());
        player3 = new SettingsPlayer(SettingsTyp.PLAYER3, playerHead.getSubimage(34, 0, 17, 18), 3, Settings.getPlayer3());
        Settings.setPlayer3(player3.getPlayerMap());
        player4 = new SettingsPlayer(SettingsTyp.PLAYER4, playerHead.getSubimage(51, 0, 17, 18), 4, Settings.getPlayer4());
        Settings.setPlayer4(player4.getPlayerMap());

        s_player.setLayout(new GridLayout(1, 4));
        s_player.add(player1);
        s_player.add(player2);
        s_player.add(player3);
        s_player.add(player4);
    }


    private void setLevel() {
        levelPanel = new JPanel();
        JRadioButton hard = new JRadioButton("HARD");
        JRadioButton easy = new JRadioButton("EASY");
        ButtonGroup level = new ButtonGroup();
        level.add(hard);
        level.add(easy);
        levelPanel.setLayout(new GridLayout(2, 1));
        levelPanel.add(easy);
        levelPanel.add(hard);
        if (Settings.getBasicSetting().get(SettingsTyp.LEVEL) == 1) {
            hard.setSelected(true);
        } else {
            easy.setSelected(true);
        }
        easy.addActionListener((event) -> {
            Map<SettingsTyp, Integer> map = Settings.getBasicSetting();
            map.put(SettingsTyp.LEVEL, 0);
            Settings.setBasicSetting(map);
        });
        hard.addActionListener(event -> {
            Map<SettingsTyp, Integer> map = Settings.getBasicSetting();
            map.put(SettingsTyp.LEVEL, 1);
            Settings.setBasicSetting(map);

        });
    }


    private void setBoard() {
        board = new JComboBox<>();
        board.addItem("BoardOne");
        board.addItem("BoardTwo");
        board.setSelectedIndex(Settings.getBasicSetting().get(SettingsTyp.BOARD));
        board.addItemListener(e -> {
            Map<SettingsTyp, Integer> map = Settings.getBasicSetting();
            map.put(SettingsTyp.BOARD, board.getSelectedIndex());
            Settings.setBasicSetting(map);
        });
    }

    private void setLife() {
        life = new JComboBox<>();
        for (int i = 1; i <= 10; i++) {
            life.addItem(i);
            life.setSelectedItem(Settings.getBasicSetting().get(SettingsTyp.LIFE));
            life.addItemListener(e -> {
                Map<SettingsTyp, Integer> map = Settings.getBasicSetting();
                map.put(SettingsTyp.LIFE, (Integer) life.getSelectedItem());
                Settings.setBasicSetting(map);
            });
        }
    }


    private void setTime() {
        time = new JComboBox<>();
        for (int i = 2; i <= 5; i++) {
            time.addItem(String.valueOf(i) + "min");
        }
        time.setSelectedIndex(Settings.getBasicSetting().get(SettingsTyp.TIME) - 2);
        time.addItemListener(e -> {
            Map<SettingsTyp, Integer> map = Settings.getBasicSetting();
            map.put(SettingsTyp.TIME, time.getSelectedIndex() + 2);
            Settings.setBasicSetting(map);
        });
    }
}