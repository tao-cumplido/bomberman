package de.hsh.project.bomberman.game.settings;

import de.hsh.project.bomberman.game.Game;
import de.hsh.project.bomberman.game.menu.MenuState;
import de.hsh.project.bomberman.game.menu.TitleState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    private JRadioButton hard;
    private JRadioButton easy;
    private ButtonGroup level;

    private JButton reToGame;

    private Settings sFile;



    public SettingsMenuState(){
        super();

        sFile = new Settings(){};

        tll = new JPanel();
        s_player = new JPanel();
        s_player.setBackground(Color.YELLOW);

        buildtll();
        buildsplayer();

        reToGame = new JButton("Start new Game");
        setReToGame(reToGame);


        this.setLayout(new BorderLayout());
        this.add(tll,BorderLayout.NORTH);
        this.add(s_player,BorderLayout.CENTER);
        this.add(reToGame,BorderLayout.SOUTH);
    }

    private void setReToGame(JButton reToGame){
        this.reToGame = reToGame;
        this.reToGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(player1.getPlayerTyp().getSelectedItem().equals("Computer")&&player2.getPlayerTyp().getSelectedItem().equals("Computer")&&
                        player3.getPlayerTyp().getSelectedItem().equals("Computer")&&player4.getPlayerTyp().getSelectedItem().equals("Computer")){
                    JOptionPane.showMessageDialog(null,"At least one player","Warning",JOptionPane.WARNING_MESSAGE);
                }else{
                    int i =JOptionPane.showConfirmDialog(null, "Do you want to save the settings?", "Save", JOptionPane.YES_NO_OPTION);
                        if(JOptionPane.YES_OPTION == i)
                            sFile.write();
                    Game.switchState(new TitleState());

                }

            }
        });
    }


    private void buildtll(){

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

    private void buildsplayer(){
        try {
            playerHead = ImageIO.read(getClass().getResource("/res/images/settings/4Player_Head.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setPlayer1();
        setPlayer2();
        setPlayer3();
        setPlayer4();

        s_player.setLayout(new GridLayout(1,4));
        s_player.add(player1);
        s_player.add(player2);
        s_player.add(player3);
        s_player.add(player4);
    }


    private void setLevel() {
        levelPanel = new JPanel();
        hard = new JRadioButton("HARD");
        easy = new JRadioButton("EASY");
        level = new ButtonGroup();
        level.add(hard);
        level.add(easy);
        levelPanel.setLayout(new GridLayout(2,1));
        levelPanel.add(easy);
        levelPanel.add(hard);
        if(sFile.getBasicSetting().get(SettingsTyp.LEVEL).intValue()==1){
            hard.setSelected(true);
        }else{
            easy.setSelected(true);
        }
        easy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<SettingsTyp,Integer> map =sFile.getBasicSetting();
                map.put(SettingsTyp.LEVEL, Integer.valueOf(0));
                sFile.setBasicSetting(map);
            }
        });
        hard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<SettingsTyp,Integer> map =sFile.getBasicSetting();
                map.put(SettingsTyp.LEVEL, Integer.valueOf(1));
                sFile.setBasicSetting(map);
            }
        });
    }

    private void setBoard(){
        board = new JComboBox<>();
        board.addItem("BoardOne");
        board.addItem("BoardTwo");
        board.setSelectedIndex(sFile.getBasicSetting().get(SettingsTyp.BOARD).intValue());
        board.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Map<SettingsTyp,Integer> map =sFile.getBasicSetting();
                map.put(SettingsTyp.BOARD, Integer.valueOf(board.getSelectedIndex()));
                sFile.setBasicSetting(map);
            }
        });
    }

    private void setLife() {
        life = new JComboBox<>();
        for(int i=1;i<=10;i++){
            life.addItem(Integer.valueOf(i));
        }
        life.setSelectedItem(sFile.getBasicSetting().get(SettingsTyp.LIFE));
        life.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Map<SettingsTyp,Integer> map =sFile.getBasicSetting();
                map.put(SettingsTyp.LIFE, (Integer) life.getSelectedItem());
                sFile.setBasicSetting(map);
            }
        });
    }

    private void setTime() {
        time = new JComboBox<>();
        for(int i=2;i<=5;i++){
            time.addItem(String.valueOf(i) + "min");
        }
        time.setSelectedIndex(sFile.getBasicSetting().get(SettingsTyp.TIME).intValue() - 2);
        time.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Map<SettingsTyp,Integer> map =sFile.getBasicSetting();
                map.put(SettingsTyp.TIME, Integer.valueOf(time.getSelectedIndex()+2));
                sFile.setBasicSetting(map);
            }
        });
    }


    protected void setPlayer1(){
        player1 = new SettingsPlayer(playerHead.getSubimage(0,0,17,18)){
            @Override
            protected void setPlayerTyp(JComboBox<String> playerTyp) {
                playerTyp = getPlayerTyp();
                playerTyp.setSelectedIndex(sFile.getBasicSetting().get(SettingsTyp.PLAYER1).intValue());
                checkEnabled();
                final JComboBox<String> finalPlayerTyp = playerTyp;
                playerTyp.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        Map<SettingsTyp, Integer> map = sFile.getBasicSetting();
                        map.put(SettingsTyp.PLAYER1, Integer.valueOf(finalPlayerTyp.getSelectedIndex()));
                        sFile.setBasicSetting(map);
                    }
                });
               super.setPlayerTyp(playerTyp);
            }

            @Override
            protected void setBombField(JTextField bombField,boolean b) {
                int keycode= sFile.getPlayer1().get(SettingsTyp.SETTINGS_BOMB).intValue();
                if(keycode==-1)
                    bombField.setText(null);
                else
                    bombField.setText(KeyEvent.getKeyText(keycode));
                if(b){
                    keyControl = new KeyControl(bombField){
                        @Override
                        protected boolean setCode(int code) {
                            code = getCode();
                            if(code != -1&& code != 8&&code !=sFile.getPlayer1().get(SettingsTyp.SETTINGS_BOMB).intValue()){
                                return checkKey(code);
                            }
                            return true;
                        }

                        @Override
                        protected void setPlayerWert(int code) {
                            if(code==KeyEvent.VK_BACK_SPACE){
                                code = -1;
                            }
                            Map<SettingsTyp,Integer> map =sFile.getPlayer1();
                            map.put(SettingsTyp.SETTINGS_BOMB,Integer.valueOf(code));
                            sFile.setPlayer1(map);
                        }
                    };
                    bombField.addKeyListener(keyControl);


                }
            }

            @Override
            protected void setReContField(JTextField reContField,boolean b) {
                int keycode= sFile.getPlayer1().get(SettingsTyp.SETTING_REMOTECONTROL).intValue();
                if(keycode==-1)
                    reContField.setText(null);
                else
                    reContField.setText(KeyEvent.getKeyText(keycode));
                if(b){
                    keyControl = new KeyControl(reContField){
                        @Override
                        protected boolean setCode(int code) {
                            code = getCode();
                            if(code != -1&& code != 8&&code !=sFile.getPlayer1().get(SettingsTyp.SETTING_REMOTECONTROL).intValue()){
                                return checkKey(code);
                            }
                            return true;
                        }

                        @Override
                        protected void setPlayerWert(int code) {
                            if(code==KeyEvent.VK_BACK_SPACE){
                                code = -1;
                            }
                            Map<SettingsTyp,Integer> map =sFile.getPlayer1();
                            map.put(SettingsTyp.SETTING_REMOTECONTROL,Integer.valueOf(code));
                            sFile.setPlayer1(map);
                        }
                    };
                    reContField.addKeyListener(keyControl);


                }
            }

            @Override
            protected void setRightField(JTextField rightField,boolean b) {
                int keycode= sFile.getPlayer1().get(SettingsTyp.DIRECTION_RIGHT).intValue();
                if(keycode==-1)
                    rightField.setText(null);
                else
                    rightField.setText(KeyEvent.getKeyText(keycode));
                if(b){
                    keyControl = new KeyControl(rightField){
                        @Override
                        protected boolean setCode(int code) {
                            code = getCode();
                            if(code != -1&& code != 8&&code !=sFile.getPlayer1().get(SettingsTyp.DIRECTION_RIGHT).intValue()){
                                return checkKey(code);
                            }
                            return true;
                        }

                        @Override
                        protected void setPlayerWert(int code) {
                            if(code==KeyEvent.VK_BACK_SPACE){
                                code = -1;
                            }
                            Map<SettingsTyp,Integer> map =sFile.getPlayer1();
                            map.put(SettingsTyp.DIRECTION_RIGHT,Integer.valueOf(code));
                            sFile.setPlayer1(map);
                        }
                    };
                    rightField.addKeyListener(keyControl);


                }
            }

            @Override
            protected void setLeftField(JTextField leftField,boolean b) {
                int keycode= sFile.getPlayer1().get(SettingsTyp.DIRECTION_LEFT).intValue();
                if(keycode==-1)
                    leftField.setText(null);
                else
                    leftField.setText(KeyEvent.getKeyText(keycode));
                if(b){
                    keyControl = new KeyControl(leftField){
                        @Override
                        protected boolean setCode(int code) {
                            code = getCode();
                            if(code != -1&& code != 8&&code !=sFile.getPlayer1().get(SettingsTyp.DIRECTION_LEFT).intValue()){
                                return checkKey(code);
                            }
                            return true;
                        }

                        @Override
                        protected void setPlayerWert(int code) {
                            if(code==KeyEvent.VK_BACK_SPACE){
                                code = -1;
                            }
                            Map<SettingsTyp,Integer> map =sFile.getPlayer1();
                            map.put(SettingsTyp.DIRECTION_LEFT,Integer.valueOf(code));
                            sFile.setPlayer1(map);
                        }
                    };
                    leftField.addKeyListener(keyControl);


                }
            }

            @Override
            protected void setDownField(JTextField downField,boolean b) {
                int keycode= sFile.getPlayer1().get(SettingsTyp.DIRECTION_DOWN).intValue();
                if(keycode==-1)
                    downField.setText(null);
                else
                    downField.setText(KeyEvent.getKeyText(keycode));
                if(b){
                    keyControl = new KeyControl(downField){
                        @Override
                        protected boolean setCode(int code) {
                            code = getCode();
                            if(code != -1&& code != 8&&code !=sFile.getPlayer1().get(SettingsTyp.DIRECTION_DOWN).intValue()){
                                return checkKey(code);
                            }
                            return true;
                        }

                        @Override
                        protected void setPlayerWert(int code) {
                            if(code==KeyEvent.VK_BACK_SPACE){
                                code = -1;
                            }
                            Map<SettingsTyp,Integer> map =sFile.getPlayer1();
                            map.put(SettingsTyp.DIRECTION_DOWN,Integer.valueOf(code));
                            sFile.setPlayer1(map);
                        }
                    };
                    downField.addKeyListener(keyControl);


                }
            }

            @Override
            protected void setUpField(JTextField upField,boolean b) {
                int keycode= sFile.getPlayer1().get(SettingsTyp.DIRECTION_UP).intValue();
                if(keycode==-1)
                    upField.setText(null);
                else
                    upField.setText(KeyEvent.getKeyText(keycode));
                if(b){
                    keyControl = new KeyControl(upField){
                        @Override
                        protected boolean setCode(int code) {
                            code = getCode();
                            if(code != -1&& code != 8&&code !=sFile.getPlayer1().get(SettingsTyp.DIRECTION_UP).intValue()){
                                return checkKey(code);
                            }
                            return true;
                        }

                        @Override
                        protected void setPlayerWert(int code) {
                            if(code==KeyEvent.VK_BACK_SPACE){
                                code = -1;
                            }
                            Map<SettingsTyp,Integer> map =sFile.getPlayer1();
                            map.put(SettingsTyp.DIRECTION_UP,Integer.valueOf(code));
                            sFile.setPlayer1(map);
                        }
                    };
                    upField.addKeyListener(keyControl);


                }
            }
        };
        player1.setBorder(BorderFactory.createTitledBorder("Player1"));
    }

    protected void setPlayer2(){
        player2 = new SettingsPlayer(playerHead.getSubimage(17,0,17,18)) {
            @Override
            protected void setPlayerTyp(JComboBox<String> playerTyp) {
                playerTyp = getPlayerTyp();
                playerTyp.setSelectedIndex(sFile.getBasicSetting().get(SettingsTyp.PLAYER2).intValue());
                checkEnabled();
                final JComboBox<String> finalPlayerTyp = playerTyp;
                playerTyp.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        Map<SettingsTyp, Integer> map = sFile.getBasicSetting();
                        map.put(SettingsTyp.PLAYER2, Integer.valueOf(finalPlayerTyp.getSelectedIndex()));
                        sFile.setBasicSetting(map);
                    }
                });
                super.setPlayerTyp(playerTyp);
            }

            @Override
            protected void setBombField(JTextField bombField,boolean b) {
                int keycode= sFile.getPlayer2().get(SettingsTyp.SETTINGS_BOMB).intValue();
                if(keycode==-1)
                    bombField.setText(null);
                else
                    bombField.setText(KeyEvent.getKeyText(keycode));
                if(b){
                    keyControl = new KeyControl(bombField){
                        @Override
                        protected boolean setCode(int code) {
                            code = getCode();
                            if(code != -1&& code != 8&&code !=sFile.getPlayer2().get(SettingsTyp.SETTINGS_BOMB).intValue()){
                                return checkKey(code);
                            }
                            return true;
                        }

                        @Override
                        protected void setPlayerWert(int code) {
                            if(code==KeyEvent.VK_BACK_SPACE){
                                code = -1;
                            }
                            Map<SettingsTyp,Integer> map =sFile.getPlayer2();
                            map.put(SettingsTyp.SETTINGS_BOMB,Integer.valueOf(code));
                            sFile.setPlayer2(map);
                        }
                    };
                    bombField.addKeyListener(keyControl);


                }
            }

            @Override
            protected void setReContField(JTextField reContField,boolean b) {
                int keycode= sFile.getPlayer2().get(SettingsTyp.SETTING_REMOTECONTROL).intValue();
                if(keycode==-1)
                    reContField.setText(null);
                else
                    reContField.setText(KeyEvent.getKeyText(keycode));
                if(b){
                    keyControl = new KeyControl(reContField){
                        @Override
                        protected boolean setCode(int code) {
                            code = getCode();
                            if(code != -1&& code != 8&&code !=sFile.getPlayer2().get(SettingsTyp.SETTING_REMOTECONTROL).intValue()){
                                return checkKey(code);
                            }
                            return true;
                        }

                        @Override
                        protected void setPlayerWert(int code) {
                            if(code==KeyEvent.VK_BACK_SPACE){
                                code = -1;
                            }
                            Map<SettingsTyp,Integer> map =sFile.getPlayer2();
                            map.put(SettingsTyp.SETTING_REMOTECONTROL,Integer.valueOf(code));
                            sFile.setPlayer2(map);
                        }
                    };
                    reContField.addKeyListener(keyControl);


                }
            }

            @Override
            protected void setRightField(JTextField rightField,boolean b) {
                int keycode= sFile.getPlayer2().get(SettingsTyp.DIRECTION_RIGHT).intValue();
                if(keycode==-1)
                    rightField.setText(null);
                else
                    rightField.setText(KeyEvent.getKeyText(keycode));
                if(b){
                    keyControl = new KeyControl(rightField){
                        @Override
                        protected boolean setCode(int code) {
                            code = getCode();
                            if(code != -1&& code != 8&&code !=sFile.getPlayer2().get(SettingsTyp.DIRECTION_RIGHT).intValue()){
                                return checkKey(code);
                            }
                            return true;
                        }

                        @Override
                        protected void setPlayerWert(int code) {
                            if(code==KeyEvent.VK_BACK_SPACE){
                                code = -1;
                            }
                            Map<SettingsTyp,Integer> map =sFile.getPlayer2();
                            map.put(SettingsTyp.DIRECTION_RIGHT,Integer.valueOf(code));
                            sFile.setPlayer2(map);
                        }
                    };
                    rightField.addKeyListener(keyControl);


                }
            }

            @Override
            protected void setLeftField(JTextField leftField,boolean b) {
                int keycode= sFile.getPlayer2().get(SettingsTyp.DIRECTION_LEFT).intValue();
                if(keycode==-1)
                    leftField.setText(null);
                else
                    leftField.setText(KeyEvent.getKeyText(keycode));
                if(b){
                    keyControl = new KeyControl(leftField){
                        @Override
                        protected boolean setCode(int code) {
                            code = getCode();
                            if(code != -1&& code != 8&&code !=sFile.getPlayer2().get(SettingsTyp.DIRECTION_LEFT).intValue()){
                                return checkKey(code);
                            }
                            return true;
                        }

                        @Override
                        protected void setPlayerWert(int code) {
                            if(code==KeyEvent.VK_BACK_SPACE){
                                code = -1;
                            }
                            Map<SettingsTyp,Integer> map =sFile.getPlayer2();
                            map.put(SettingsTyp.DIRECTION_LEFT,Integer.valueOf(code));
                            sFile.setPlayer2(map);
                        }
                    };
                    leftField.addKeyListener(keyControl);


                }
            }

            @Override
            protected void setDownField(JTextField downField,boolean b) {
                int keycode= sFile.getPlayer2().get(SettingsTyp.DIRECTION_DOWN).intValue();
                if(keycode==-1)
                    downField.setText(null);
                else
                    downField.setText(KeyEvent.getKeyText(keycode));
                if(b){
                    keyControl = new KeyControl(downField){
                        @Override
                        protected boolean setCode(int code) {
                            code = getCode();
                            if(code != -1&& code != 8&&code !=sFile.getPlayer2().get(SettingsTyp.DIRECTION_DOWN).intValue()){
                                return checkKey(code);
                            }
                            return true;
                        }

                        @Override
                        protected void setPlayerWert(int code) {
                            if(code==KeyEvent.VK_BACK_SPACE){
                                code = -1;
                            }
                            Map<SettingsTyp,Integer> map =sFile.getPlayer2();
                            map.put(SettingsTyp.DIRECTION_DOWN,Integer.valueOf(code));
                            sFile.setPlayer2(map);
                        }
                    };
                    downField.addKeyListener(keyControl);


                }
            }

            @Override
            protected void setUpField(JTextField upField,boolean b) {
                int keycode= sFile.getPlayer2().get(SettingsTyp.DIRECTION_UP).intValue();
                if(keycode==-1)
                    upField.setText(null);
                else
                    upField.setText(KeyEvent.getKeyText(keycode));
                if(b){
                    keyControl = new KeyControl(upField){
                        @Override
                        protected boolean setCode(int code) {
                            code = getCode();
                            if(code != -1&& code != 8&&code !=sFile.getPlayer2().get(SettingsTyp.DIRECTION_UP).intValue()){
                                return checkKey(code);
                            }
                            return true;
                        }

                        @Override
                        protected void setPlayerWert(int code) {
                            if(code==KeyEvent.VK_BACK_SPACE){
                                code = -1;
                            }
                            Map<SettingsTyp,Integer> map =sFile.getPlayer2();
                            map.put(SettingsTyp.DIRECTION_UP,Integer.valueOf(code));
                            sFile.setPlayer2(map);
                        }
                    };
                    upField.addKeyListener(keyControl);

                }
            }

        };
        player2.setBorder(BorderFactory.createTitledBorder("Player2"));
    }

    protected void setPlayer3(){
        player3 = new SettingsPlayer(playerHead.getSubimage(34,0,17,18)) {
            @Override
            protected void setPlayerTyp(JComboBox<String> playerTyp) {
                playerTyp = getPlayerTyp();
                playerTyp.setSelectedIndex(sFile.getBasicSetting().get(SettingsTyp.PLAYER3).intValue());
                checkEnabled();
                final JComboBox<String> finalPlayerTyp = playerTyp;
                playerTyp.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        Map<SettingsTyp, Integer> map = sFile.getBasicSetting();
                        map.put(SettingsTyp.PLAYER3, Integer.valueOf(finalPlayerTyp.getSelectedIndex()));
                        sFile.setBasicSetting(map);
                    }
                });
                super.setPlayerTyp(playerTyp);
            }

            @Override
            protected void setBombField(JTextField bombField,boolean b) {
                int keycode= sFile.getPlayer3().get(SettingsTyp.SETTINGS_BOMB).intValue();
                if(keycode==-1)
                    bombField.setText(null);
                else
                    bombField.setText(KeyEvent.getKeyText(keycode));
                if(b){
                    keyControl = new KeyControl(bombField){
                        @Override
                        protected boolean setCode(int code) {
                            code = getCode();
                            if(code != -1&& code != 8&&code !=sFile.getPlayer3().get(SettingsTyp.SETTINGS_BOMB).intValue()){
                                return checkKey(code);
                            }
                            return true;
                        }

                        @Override
                        protected void setPlayerWert(int code) {
                            if(code==KeyEvent.VK_BACK_SPACE){
                                code = -1;
                            }
                            Map<SettingsTyp,Integer> map =sFile.getPlayer3();
                            map.put(SettingsTyp.SETTINGS_BOMB,Integer.valueOf(code));
                            sFile.setPlayer3(map);
                        }
                    };
                    bombField.addKeyListener(keyControl);


                }
            }

            @Override
            protected void setReContField(JTextField reContField,boolean b) {
                int keycode= sFile.getPlayer3().get(SettingsTyp.SETTING_REMOTECONTROL).intValue();
                if(keycode==-1)
                    reContField.setText(null);
                else
                    reContField.setText(KeyEvent.getKeyText(keycode));
                if(b){
                    keyControl = new KeyControl(reContField){
                        @Override
                        protected boolean setCode(int code) {
                            code = getCode();
                            if(code != -1&& code != 8&&code !=sFile.getPlayer3().get(SettingsTyp.SETTING_REMOTECONTROL).intValue()){
                                return checkKey(code);
                            }
                            return true;
                        }

                        @Override
                        protected void setPlayerWert(int code) {
                            if(code==KeyEvent.VK_BACK_SPACE){
                                code = -1;
                            }
                            Map<SettingsTyp,Integer> map =sFile.getPlayer3();
                            map.put(SettingsTyp.SETTING_REMOTECONTROL,Integer.valueOf(code));
                            sFile.setPlayer3(map);
                        }
                    };
                    reContField.addKeyListener(keyControl);


                }
            }

            @Override
            protected void setRightField(JTextField rightField,boolean b) {
                int keycode= sFile.getPlayer3().get(SettingsTyp.DIRECTION_RIGHT).intValue();
                if(keycode==-1)
                    rightField.setText(null);
                else
                    rightField.setText(KeyEvent.getKeyText(keycode));
                if(b){
                    keyControl = new KeyControl(rightField){
                        @Override
                        protected boolean setCode(int code) {
                            code = getCode();
                            if(code != -1&& code != 8&&code !=sFile.getPlayer3().get(SettingsTyp.DIRECTION_RIGHT).intValue()){
                                return checkKey(code);
                            }
                            return true;
                        }

                        @Override
                        protected void setPlayerWert(int code) {
                            if(code==KeyEvent.VK_BACK_SPACE){
                                code = -1;
                            }
                            Map<SettingsTyp,Integer> map =sFile.getPlayer3();
                            map.put(SettingsTyp.DIRECTION_RIGHT,Integer.valueOf(code));
                            sFile.setPlayer3(map);
                        }
                    };
                    rightField.addKeyListener(keyControl);


                }
            }

            @Override
            protected void setLeftField(JTextField leftField,boolean b) {
                int keycode= sFile.getPlayer3().get(SettingsTyp.DIRECTION_LEFT).intValue();
                if(keycode==-1)
                    leftField.setText(null);
                else
                    leftField.setText(KeyEvent.getKeyText(keycode));
                if(b){
                    keyControl = new KeyControl(leftField){
                        @Override
                        protected boolean setCode(int code) {
                            code = getCode();
                            if(code != -1&& code != 8&&code !=sFile.getPlayer3().get(SettingsTyp.DIRECTION_LEFT).intValue()){
                                return checkKey(code);
                            }
                            return true;
                        }

                        @Override
                        protected void setPlayerWert(int code) {
                            if(code==KeyEvent.VK_BACK_SPACE){
                                code = -1;
                            }
                            Map<SettingsTyp,Integer> map =sFile.getPlayer3();
                            map.put(SettingsTyp.DIRECTION_LEFT,Integer.valueOf(code));
                            sFile.setPlayer3(map);
                        }
                    };
                    leftField.addKeyListener(keyControl);


                }
            }

            @Override
            protected void setDownField(JTextField downField,boolean b) {
                int keycode= sFile.getPlayer3().get(SettingsTyp.DIRECTION_DOWN).intValue();
                if(keycode==-1)
                    downField.setText(null);
                else
                    downField.setText(KeyEvent.getKeyText(keycode));
                if(b){
                    keyControl = new KeyControl(downField){
                        @Override
                        protected boolean setCode(int code) {
                            code = getCode();
                            if(code != -1&& code != 8&&code !=sFile.getPlayer3().get(SettingsTyp.DIRECTION_DOWN).intValue()){
                                return checkKey(code);
                            }
                            return true;
                        }

                        @Override
                        protected void setPlayerWert(int code) {
                            if(code==KeyEvent.VK_BACK_SPACE){
                                code = -1;
                            }
                            Map<SettingsTyp,Integer> map =sFile.getPlayer3();
                            map.put(SettingsTyp.DIRECTION_DOWN,Integer.valueOf(code));
                            sFile.setPlayer3(map);
                        }
                    };
                    downField.addKeyListener(keyControl);


                }
            }

            @Override
            protected void setUpField(JTextField upField,boolean b) {
                int keycode= sFile.getPlayer3().get(SettingsTyp.DIRECTION_UP).intValue();
                if(keycode==-1)
                    upField.setText(null);
                else
                    upField.setText(KeyEvent.getKeyText(keycode));
                if(b){
                    keyControl = new KeyControl(upField){
                        @Override
                        protected boolean setCode(int code) {
                            code = getCode();
                            if(code != -1&& code != 8&&code !=sFile.getPlayer3().get(SettingsTyp.DIRECTION_UP).intValue()){
                                return checkKey(code);
                            }
                            return true;
                        }

                        @Override
                        protected void setPlayerWert(int code) {
                            if(code==KeyEvent.VK_BACK_SPACE){
                                code = -1;
                            }
                            Map<SettingsTyp,Integer> map =sFile.getPlayer3();
                            map.put(SettingsTyp.DIRECTION_UP,Integer.valueOf(code));
                            sFile.setPlayer3(map);
                        }
                    };
                    upField.addKeyListener(keyControl);


                }
            }

        };
        player3.setBorder(BorderFactory.createTitledBorder("Player3"));
    }

    protected void setPlayer4(){

        player4 = new SettingsPlayer(playerHead.getSubimage(51,0,17,18)) {
            @Override
            protected void setPlayerTyp(JComboBox<String> playerTyp) {
                playerTyp = getPlayerTyp();
                playerTyp.setSelectedIndex(sFile.getBasicSetting().get(SettingsTyp.PLAYER4).intValue());
                checkEnabled();
                final JComboBox<String> finalPlayerTyp = playerTyp;
                playerTyp.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        Map<SettingsTyp, Integer> map = sFile.getBasicSetting();
                        map.put(SettingsTyp.PLAYER4, Integer.valueOf(finalPlayerTyp.getSelectedIndex()));
                        sFile.setBasicSetting(map);
                    }
                });
                super.setPlayerTyp(playerTyp);
            }

            @Override
            protected void setBombField(JTextField bombField,boolean b) {
                int keycode= sFile.getPlayer4().get(SettingsTyp.SETTINGS_BOMB).intValue();
                if(keycode==-1)
                    bombField.setText(null);
                else
                    bombField.setText(KeyEvent.getKeyText(keycode));
                if(b){
                    keyControl = new KeyControl(bombField){
                        @Override
                        protected boolean setCode(int code) {
                            code = getCode();
                            if(code != -1&& code != 8&&code !=sFile.getPlayer4().get(SettingsTyp.SETTINGS_BOMB).intValue()){
                                return checkKey(code);
                            }
                            return true;
                        }

                        @Override
                        protected void setPlayerWert(int code) {
                            if(code==KeyEvent.VK_BACK_SPACE){
                                code = -1;
                            }
                            Map<SettingsTyp,Integer> map =sFile.getPlayer4();
                            map.put(SettingsTyp.SETTINGS_BOMB,Integer.valueOf(code));
                            sFile.setPlayer4(map);
                        }
                    };
                    bombField.addKeyListener(keyControl);


                }
            }

            @Override
            protected void setReContField(JTextField reContField,boolean b) {
                int keycode= sFile.getPlayer4().get(SettingsTyp.SETTING_REMOTECONTROL).intValue();
                if(keycode==-1)
                    reContField.setText(null);
                else
                    reContField.setText(KeyEvent.getKeyText(keycode));
                if(b){
                    keyControl = new KeyControl(reContField){
                        @Override
                        protected boolean setCode(int code) {
                            code = getCode();
                            if(code != -1&& code != 8&&code !=sFile.getPlayer4().get(SettingsTyp.SETTING_REMOTECONTROL).intValue()){
                                return checkKey(code);
                            }
                            return true;
                        }

                        @Override
                        protected void setPlayerWert(int code) {
                            if(code==KeyEvent.VK_BACK_SPACE){
                                code = -1;
                            }
                            Map<SettingsTyp,Integer> map =sFile.getPlayer4();
                            map.put(SettingsTyp.SETTING_REMOTECONTROL,Integer.valueOf(code));
                            sFile.setPlayer4(map);
                        }
                    };
                    reContField.addKeyListener(keyControl);


                }
            }

            @Override
            protected void setRightField(JTextField rightField,boolean b) {
                int keycode= sFile.getPlayer4().get(SettingsTyp.DIRECTION_RIGHT).intValue();
                if(keycode==-1)
                    rightField.setText(null);
                else
                    rightField.setText(KeyEvent.getKeyText(keycode));
                if(b){
                    keyControl = new KeyControl(rightField){
                        @Override
                        protected boolean setCode(int code) {
                            code = getCode();
                            if(code != -1&& code != 8&&code !=sFile.getPlayer4().get(SettingsTyp.DIRECTION_RIGHT).intValue()){
                                return checkKey(code);
                            }
                            return true;
                        }

                        @Override
                        protected void setPlayerWert(int code) {
                            if(code==KeyEvent.VK_BACK_SPACE){
                                code = -1;
                            }
                            Map<SettingsTyp,Integer> map =sFile.getPlayer4();
                            map.put(SettingsTyp.DIRECTION_RIGHT,Integer.valueOf(code));
                            sFile.setPlayer4(map);
                        }
                    };
                    rightField.addKeyListener(keyControl);


                }
            }

            @Override
            protected void setLeftField(JTextField leftField,boolean b) {
                int keycode= sFile.getPlayer4().get(SettingsTyp.DIRECTION_LEFT).intValue();
                if(keycode==-1)
                    leftField.setText(null);
                else
                    leftField.setText(KeyEvent.getKeyText(keycode));
                if(b){
                    keyControl = new KeyControl(leftField){
                        @Override
                        protected boolean setCode(int code) {
                            code = getCode();
                            if(code != -1&& code != 8&&code!=sFile.getPlayer4().get(SettingsTyp.DIRECTION_LEFT).intValue()){
                                System.out.println("???");
                                return checkKey(code);
                            }
                            return true;
                        }

                        @Override
                        protected void setPlayerWert(int code) {
                            if(code==KeyEvent.VK_BACK_SPACE){
                                code = -1;
                            }
                            Map<SettingsTyp,Integer> map =sFile.getPlayer4();
                            map.put(SettingsTyp.DIRECTION_LEFT,Integer.valueOf(code));
                            sFile.setPlayer4(map);
                        }
                    };
                    leftField.addKeyListener(keyControl);


                }
            }

            @Override
            protected void setDownField(JTextField downField,boolean b) {
                int keycode= sFile.getPlayer4().get(SettingsTyp.DIRECTION_DOWN).intValue();
                if(keycode==-1)
                    downField.setText(null);
                else
                    downField.setText(KeyEvent.getKeyText(keycode));
                if(b){
                    keyControl = new KeyControl(downField){
                        @Override
                        protected boolean setCode(int code) {
                            code = getCode();
                            if(code != -1&& code != 8&&code !=sFile.getPlayer4().get(SettingsTyp.DIRECTION_DOWN).intValue()){
                                return checkKey(code);
                            }
                            return true;
                        }

                        @Override
                        protected void setPlayerWert(int code) {
                            if(code==KeyEvent.VK_BACK_SPACE){
                                code = -1;
                            }
                            Map<SettingsTyp,Integer> map =sFile.getPlayer4();
                            map.put(SettingsTyp.DIRECTION_DOWN,Integer.valueOf(code));
                            sFile.setPlayer4(map);
                        }
                    };
                    downField.addKeyListener(keyControl);


                }
            }

            @Override
            protected void setUpField(JTextField upField,boolean b) {
                int keycode= sFile.getPlayer4().get(SettingsTyp.DIRECTION_UP).intValue();
                if(keycode==-1)
                    upField.setText(null);
                else
                    upField.setText(KeyEvent.getKeyText(keycode));
                if(b){
                    keyControl = new KeyControl(upField){
                        @Override
                        protected boolean setCode(int code) {
                            code = getCode();
                            if(code != -1&& code != 8&&code !=sFile.getPlayer4().get(SettingsTyp.DIRECTION_UP).intValue()){
                                return checkKey(code);
                            }
                            return true;
                        }

                        @Override
                        protected void setPlayerWert(int code) {
                            if(code==KeyEvent.VK_BACK_SPACE){
                                code = -1;
                            }
                            Map<SettingsTyp,Integer> map =sFile.getPlayer4();
                            map.put(SettingsTyp.DIRECTION_UP,Integer.valueOf(code));
                            sFile.setPlayer4(map);
                        }
                    };
                    upField.addKeyListener(keyControl);


                }
            }
        };
        player4.setBorder(BorderFactory.createTitledBorder("Player4"));
    }

    private boolean checkKey(int code){

        if(sFile.getPlayer1().values().contains(Integer.valueOf(code))){
            if(sFile.getBasicSetting().get(SettingsTyp.PLAYER1)==1){
                for(SettingsTyp settingsTyp: sFile.getPlayer1().keySet()){
                    if(sFile.getPlayer1().get(settingsTyp).intValue() ==code)
                        sFile.getPlayer1().put(settingsTyp,Integer.valueOf(-1));
                }
                return true;
            }else
                JOptionPane.showMessageDialog(null,"The key cannot be repeated","Warning",JOptionPane.WARNING_MESSAGE);


        }else if(sFile.getPlayer2().values().contains(Integer.valueOf(code))){
            if(sFile.getBasicSetting().get(SettingsTyp.PLAYER2)==1){
                for(SettingsTyp settingsTyp: sFile.getPlayer2().keySet()){
                    if(sFile.getPlayer2().get(settingsTyp).intValue() ==code)
                        sFile.getPlayer2().put(settingsTyp,Integer.valueOf(-1));
                }
                return true;
            }else
                JOptionPane.showMessageDialog(null,"The key cannot be repeated","Warning",JOptionPane.WARNING_MESSAGE);


        }else if(sFile.getPlayer3().values().contains(Integer.valueOf(code))){
            if(sFile.getBasicSetting().get(SettingsTyp.PLAYER3)==1){
                for(SettingsTyp settingsTyp: sFile.getPlayer3().keySet()){
                    if(sFile.getPlayer3().get(settingsTyp).intValue() ==code)
                        sFile.getPlayer3().put(settingsTyp,Integer.valueOf(-1));
                }
                return true;
            }else
                JOptionPane.showMessageDialog(null,"The key cannot be repeated","Warning",JOptionPane.WARNING_MESSAGE);

        }else if(sFile.getPlayer4().values().contains(Integer.valueOf(code))){
            if(sFile.getBasicSetting().get(SettingsTyp.PLAYER4)==1){
                for(SettingsTyp settingsTyp: sFile.getPlayer4().keySet()){
                    if(sFile.getPlayer4().get(settingsTyp).intValue() ==code)
                        sFile.getPlayer4().put(settingsTyp,Integer.valueOf(-1));
                }
                return true;
            }else
                JOptionPane.showMessageDialog(null,"The key cannot be repeated","Warning",JOptionPane.WARNING_MESSAGE);

        }else
            return true;
        return false;
    }

}
