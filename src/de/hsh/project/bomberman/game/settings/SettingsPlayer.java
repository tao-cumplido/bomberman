package de.hsh.project.bomberman.game.settings;



import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by XER on 2015/11/13 0013.
 */
public class SettingsPlayer extends JPanel{

    private JComboBox<String> playerTyp;

    private JLabel upLabel;
    private JTextField upField;
    private JLabel downLabel;
    private JTextField downField;
    private JLabel leftLabel;
    private JTextField leftField;
    private JLabel rightLabel;
    private JTextField rightField;
    private JLabel reContLabel;
    private JTextField reContField;
    private JLabel bombLabel;
    private JTextField bombField;

    protected KeyControl keyControl;


    private SettingsTyp typ;
    private Map<SettingsTyp,Integer> map;
    private int playerNum;

    private Map<SettingsTyp,BufferedImage> settingTypPicture;

    public SettingsPlayer(SettingsTyp typ, BufferedImage bufferedImage,int playerNum,Map<SettingsTyp,Integer> map){

        this.typ = typ;
        this.map = map;
        this.playerNum = playerNum;

        settingTypPicture = new HashMap<>();

        JLabel headLabel = new JLabel();
        setSuitableLabel(headLabel,bufferedImage,30,30);

        this.playerTyp = new JComboBox<>();
        this.playerTyp.addItem("Human");
        this.playerTyp.addItem("Computer");


        setSettingTypPicture();
        setPlayerTyp(playerTyp);
        Border lineBorder = BorderFactory.createLineBorder(Color.GRAY,2);
        TitledBorder tb= new TitledBorder(lineBorder,"PLAYER"+String.valueOf(playerNum));
        //tb.setTitleFont(Font.createFont(Font.BOLD,null));
        tb.setTitleColor(Color.BLUE);

        setBorder(tb);

        this.setLayout(new GridLayout(7,2));
        this.add(headLabel);
        this.add(playerTyp);
        this.add(upLabel);
        this.add(upField);
        this.add(leftLabel);
        this.add(leftField);
        this.add(rightLabel);
        this.add(rightField);
        this.add(downLabel);
        this.add(downField);
        this.add(reContLabel);
        this.add(reContField);
        this.add(bombLabel);
        this.add(bombField);

        this.setOpaque(false);
    }

    protected JLabel setSuitableLabel(JLabel jLabel,BufferedImage bi,int width,int heigh){
        Image image = bi.getScaledInstance(width, heigh, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(image);
        jLabel.setIcon(icon);
        return jLabel;
    }

    protected void setPlayerTyp(JComboBox<String> playerTyp){
        playerTyp.setSelectedIndex(Settings.getBasicSetting().get(typ));
        checkEnabled();
        playerTyp.addItemListener(e -> {
            Map<SettingsTyp, Integer> map = Settings.getBasicSetting();
            map.put(typ, playerTyp.getSelectedIndex());
            Settings.setBasicSetting(map);
            checkEnabled();
        });
    }

    protected void setSettingTypPicture(){

        BufferedImage reContPicture, bombPicture,direPicture;
        try {
            direPicture = ImageIO.read(SettingsPlayer.class.getResourceAsStream("/res/images/settings/Direction.png"));
            reContPicture = ImageIO.read(SettingsPlayer.class.getResourceAsStream("/res/images/settings/asset-power-up-remote-control.png"));
            bombPicture = ImageIO.read(SettingsPlayer.class.getResourceAsStream("/res/images/settings/asset-bombe.png"));
            settingTypPicture.put(SettingsTyp.DIRECTION_UP, direPicture.getSubimage(0,0,208,208));
            settingTypPicture.put(SettingsTyp.DIRECTION_LEFT,direPicture.getSubimage(208,0,208,208));
            settingTypPicture.put(SettingsTyp.DIRECTION_RIGHT,direPicture.getSubimage(416,0,208,208));
            settingTypPicture.put(SettingsTyp.DIRECTION_DOWN,direPicture.getSubimage(624,0,208,208));
            settingTypPicture.put(SettingsTyp.SETTINGS_BOMB,bombPicture);
            settingTypPicture.put(SettingsTyp.SETTING_REMOTECONTROL,reContPicture);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(SettingsTyp st: settingTypPicture.keySet()){
            setPicture(st,settingTypPicture.get(st));
        }
    }

    protected void setPicture(SettingsTyp st,BufferedImage bi){
        switch (st) {
            case DIRECTION_UP:
                upLabel = new JLabel();
                setSuitableLabel(upLabel,bi,50,50);
                upField = new JTextField();
                setUpField(upField,true);
                break;
            case DIRECTION_LEFT:
                leftLabel = new JLabel();
                setSuitableLabel(leftLabel,bi,50,50);
                leftField = new JTextField();
                setLeftField(leftField,true);
                break;
            case DIRECTION_RIGHT:
                rightLabel = new JLabel();
                setSuitableLabel(rightLabel,bi,50,50);
                rightField = new JTextField();
                setRightField(rightField,true);
                break;
            case DIRECTION_DOWN:
                downLabel = new JLabel();
                setSuitableLabel(downLabel,bi,50,50);
                downField = new JTextField();
                setDownField(downField,true);
                break;
            case SETTING_REMOTECONTROL:
                reContLabel = new JLabel();
                setSuitableLabel(reContLabel,bi,50,50);
                reContField = new JTextField();
                setReContField(reContField,true);
                break;
            case SETTINGS_BOMB:
                bombLabel = new JLabel();
                setSuitableLabel(bombLabel,bi,50,50);
                bombField = new JTextField();
                setBombField(bombField,true);
                break;
        }
    }


    protected JComboBox<String> getPlayerTyp() {
        return playerTyp;
    }

    protected void setUpField(JTextField upField,boolean b){
        int keycode= map.get(SettingsTyp.DIRECTION_UP);
        if(keycode==-1)
            upField.setText(null);
        else
            upField.setText(KeyEvent.getKeyText(keycode));
        setKeyControl(upField,SettingsTyp.DIRECTION_UP,b);
    }

    protected void setDownField(JTextField downField,boolean b){
        int keycode= map.get(SettingsTyp.DIRECTION_DOWN);
        if(keycode==-1)
            downField.setText(null);
        else
            downField.setText(KeyEvent.getKeyText(keycode));
        setKeyControl(downField,SettingsTyp.DIRECTION_DOWN,b);
    }

    protected void setLeftField(JTextField leftField,boolean b){
        int keycode= map.get(SettingsTyp.DIRECTION_LEFT);
        if(keycode==-1)
            leftField.setText(null);
        else
            leftField.setText(KeyEvent.getKeyText(keycode));
        setKeyControl(leftField,SettingsTyp.DIRECTION_LEFT,b);
    }

    protected void setRightField(JTextField rightField,boolean b){
        int keycode= map.get(SettingsTyp.DIRECTION_RIGHT);
        if(keycode==-1)
            rightField.setText(null);
        else
            rightField.setText(KeyEvent.getKeyText(keycode));
        setKeyControl(rightField,SettingsTyp.DIRECTION_RIGHT,b);
    }

    protected void setReContField(JTextField reContField,boolean b){
        int keycode= map.get(SettingsTyp.SETTING_REMOTECONTROL);
        if(keycode==-1)
            reContField.setText(null);
        else
            reContField.setText(KeyEvent.getKeyText(keycode));
        setKeyControl(reContField,SettingsTyp.SETTING_REMOTECONTROL,b);
    }

    protected void setBombField(JTextField bombField,boolean b){
        int keycode= map.get(SettingsTyp.SETTINGS_BOMB);
        if(keycode==-1)
            bombField.setText(null);
        else
            bombField.setText(KeyEvent.getKeyText(keycode));
        setKeyControl(bombField,SettingsTyp.SETTINGS_BOMB,b);
    }

    private void setKeyControl(JTextField jTextField,SettingsTyp settingsTyp,boolean b){
        if(b){
            keyControl = new KeyControl(jTextField){
                @Override
                protected boolean setCode(int code) {
                    code = getCode();
                    return !(code != -1&& code != 8&&code !=map.get(settingsTyp))|| checkKey(code);
                }
                @Override
                protected void setPlayerValue(int code) {
                    if(code==KeyEvent.VK_BACK_SPACE){
                        code = -1;
                    }
                    map.put(settingsTyp,code);
                }
            };
            jTextField.addKeyListener(keyControl);
        }
    }



    protected void checkEnabled(){
        if(this.playerTyp.getSelectedItem().equals("Computer")){
            upField.setText(null);
            downField.setText(null);
            leftField.setText(null);
            rightField.setText(null);
            reContField.setText(null);
            bombField.setText(null);
            upField.setEnabled(false);
            downField.setEnabled(false);
            leftField.setEnabled(false);
            rightField.setEnabled(false);
            reContField.setEnabled(false);
            bombField.setEnabled(false);

        }else{
            upField.setEnabled(true);
            downField.setEnabled(true);
            leftField.setEnabled(true);
            rightField.setEnabled(true);
            reContField.setEnabled(true);
            bombField.setEnabled(true);
            setUpField(upField,false);
            setDownField(downField,false);
            setRightField(rightField,false);
            setLeftField(leftField,false);
            setReContField(reContField,false);
            setBombField(bombField,false);
        }
    }


    protected Map<SettingsTyp, Integer> getPlayerMap() {
        return map;
    }

    private boolean checkKey(int code){
        switch (playerNum){
            case 1:
                Settings.setPlayer1(map);
                break;
            case 2:
                Settings.setPlayer2(map);
                break;
            case 3:
                Settings.setPlayer3(map);
                break;
            case 4:
                Settings.setPlayer4(map);
                break;
        }

        if(Settings.getPlayer1().values().contains(code)){
            if(Settings.getBasicSetting().get(SettingsTyp.PLAYER1)==1){
                for(SettingsTyp settingsTyp: Settings.getPlayer1().keySet()){
                    if(Settings.getPlayer1().get(settingsTyp) ==code)
                        Settings.getPlayer1().put(settingsTyp,-1);
                }
                return true;
            }else
                JOptionPane.showMessageDialog(null,"The key cannot be repeated","Warning",JOptionPane.WARNING_MESSAGE);


        }else if(Settings.getPlayer2().values().contains(code)){
            if(Settings.getBasicSetting().get(SettingsTyp.PLAYER2)==1){
                for(SettingsTyp settingsTyp: Settings.getPlayer2().keySet()){
                    if(Settings.getPlayer2().get(settingsTyp) ==code)
                        Settings.getPlayer2().put(settingsTyp,-1);
                }
                return true;
            }else
                JOptionPane.showMessageDialog(null,"The key cannot be repeated","Warning",JOptionPane.WARNING_MESSAGE);


        }else if(Settings.getPlayer3().values().contains(code)){
            if(Settings.getBasicSetting().get(SettingsTyp.PLAYER3)==1){
                for(SettingsTyp settingsTyp: Settings.getPlayer3().keySet()){
                    if(Settings.getPlayer3().get(settingsTyp) ==code)
                        Settings.getPlayer3().put(settingsTyp,-1);
                }
                return true;
            }else
                JOptionPane.showMessageDialog(null,"The key cannot be repeated","Warning",JOptionPane.WARNING_MESSAGE);

        }else if(Settings.getPlayer4().values().contains(code)){
            if(Settings.getBasicSetting().get(SettingsTyp.PLAYER4)==1){
                for(SettingsTyp settingsTyp: Settings.getPlayer4().keySet()){
                    if(Settings.getPlayer4().get(settingsTyp) ==code)
                        Settings.getPlayer4().put(settingsTyp,-1);
                }
                return true;
            }else
                JOptionPane.showMessageDialog(null,"The key cannot be repeated","Warning",JOptionPane.WARNING_MESSAGE);

        }else
            return true;
        return false;
    }
}
