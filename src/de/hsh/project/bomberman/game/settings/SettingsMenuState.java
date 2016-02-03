package de.hsh.project.bomberman.game.settings;

import de.hsh.project.bomberman.game.Resource;
import de.hsh.project.bomberman.game.menu.FontImage;
import de.hsh.project.bomberman.game.menu.MenuState;

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

    private BufferedImage background;

    private SettingsPlayer player1;
    private SettingsPlayer player2;
    private SettingsPlayer player3;
    private SettingsPlayer player4;


    private BufferedImage playerHead;


    private JComboBox<String> time;
    private JComboBox<Integer> life;
    private JComboBox<String> board;
    private JPanel levelPanel;


    private FontImage reToGame;


    public SettingsMenuState() {
        super();

        background = Resource.loadImage("0cover.png");

        tll = new JPanel();

        buildTll();
        buildPlayer();

        reToGame = new FontImage("BACK",4,true);
        setBackButton(reToGame);


        this.add(tll);
        this.add(reToGame);
        this.add(player1);
        this.add(player2);
        this.add(player3);
        this.add(player4);
        this.setLayout(null);
        tll.setBounds((int)(getPreferredSize().getWidth()*0.1),(int)(getPreferredSize().getHeight()*0.05),
                (int)(getPreferredSize().getWidth()*0.8),(int)(getPreferredSize().getHeight()*0.1));
        reToGame.setBounds((int) (getPreferredSize().getWidth()*0.07), (int) (getPreferredSize().getHeight()*0.95),4*8*4,4*8);
        player1.setBounds((int)(getPreferredSize().getWidth()/4*0.125),(int)(getPreferredSize().getHeight()*0.15),
                (int)(getPreferredSize().getWidth()/4*0.75),(int)(getPreferredSize().getHeight()*0.75));
        player2.setBounds((int)(getPreferredSize().getWidth()/4*1.125),(int)(getPreferredSize().getHeight()*0.15),
                (int)(getPreferredSize().getWidth()/4*0.75),(int)(getPreferredSize().getHeight()*0.75));
        player3.setBounds((int)(getPreferredSize().getWidth()/4*2.125),(int)(getPreferredSize().getHeight()*0.15),
                (int)(getPreferredSize().getWidth()/4*0.75),(int)(getPreferredSize().getHeight()*0.75));
        player4.setBounds((int)(getPreferredSize().getWidth()/4*3.125),(int)(getPreferredSize().getHeight()*0.15),
                (int)(getPreferredSize().getWidth()/4*0.75),(int)(getPreferredSize().getHeight()*0.75));

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, this);
        }
    }

    @Override
    protected void setPanelPosition(){
        this.reToGame.setPanelPoint();
    }

    @Override
    protected boolean setBackCondition(){

        if (player1.getPlayerTyp().getSelectedItem().equals("Computer") && player2.getPlayerTyp().getSelectedItem().equals("Computer") &&
                player3.getPlayerTyp().getSelectedItem().equals("Computer") && player4.getPlayerTyp().getSelectedItem().equals("Computer")) {
            JOptionPane.showMessageDialog(null, "At least one player!", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if(((Settings.getBasicSetting().get(SettingsTyp.PLAYER1)==0)&&Settings.getPlayer1().containsValue(-1))||
                ((Settings.getBasicSetting().get(SettingsTyp.PLAYER2)==0)&&Settings.getPlayer2().containsValue(-1))||
                ((Settings.getBasicSetting().get(SettingsTyp.PLAYER3)==0)&&Settings.getPlayer3().containsValue(-1))||(
                (Settings.getBasicSetting().get(SettingsTyp.PLAYER4)==0)&&Settings.getPlayer4().containsValue(-1))){
            JOptionPane.showMessageDialog(null, "Please fill in the complete form!", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        } else{
            int i = JOptionPane.showConfirmDialog(null, "Do you want to save the settings?", "Save", JOptionPane.YES_NO_OPTION);
            if (JOptionPane.YES_OPTION == i){
                Settings.write();
            }
        }
        return true;
    }


    private void buildTll() {

        setTime();
        setLife();
        setBoard();
        setLevel();

        tll.setLayout(new FlowLayout());
        tll.add(new FontImage("Time:",3,false));
        tll.add(time);
        tll.add(new FontImage("Lifes:",3,false));
        tll.add(life);
        tll.add(new FontImage("Board:",3,false));
        tll.add(board);
        tll.add(new FontImage("AI-Level:",3,false));
        tll.add(levelPanel);

        tll.setOpaque(false);
    }


    private void buildPlayer() {
        playerHead = Resource.loadImage("settings/bombers.png");

        player1 = new SettingsPlayer(SettingsTyp.PLAYER1, playerHead.getSubimage(0, 0, 500, 500), 1, Settings.getPlayer1());
        Settings.setPlayer1(player1.getPlayerMap());
        player2 = new SettingsPlayer(SettingsTyp.PLAYER2, playerHead.getSubimage(500, 0, 500, 500), 2, Settings.getPlayer2());
        Settings.setPlayer2(player2.getPlayerMap());
        player3 = new SettingsPlayer(SettingsTyp.PLAYER3, playerHead.getSubimage(1000, 0, 500, 500), 3, Settings.getPlayer3());
        Settings.setPlayer3(player3.getPlayerMap());
        player4 = new SettingsPlayer(SettingsTyp.PLAYER4, playerHead.getSubimage(1500, 0, 500, 500), 4, Settings.getPlayer4());
        Settings.setPlayer4(player4.getPlayerMap());

    }


    private void setLevel() {
        levelPanel = new JPanel();
        levelPanel.setOpaque(false);
        JRadioButton hard = new JRadioButton("2");
        hard.setOpaque(false);
        JRadioButton easy = new JRadioButton("1");
        easy.setOpaque(false);
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
        board.addItem("One");
        board.addItem("Two");
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