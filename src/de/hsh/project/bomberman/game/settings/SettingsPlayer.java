package de.hsh.project.bomberman.game.settings;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by XER on 2015/11/13 0013.
 */
public abstract class SettingsPlayer extends JPanel{

    private JLabel headLabel;
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

    private Map<SettingsTyp,BufferedImage> settingTypPicture;

    public SettingsPlayer(BufferedImage bufferedImage){

        settingTypPicture = new HashMap<SettingsTyp,BufferedImage>();

        headLabel = new JLabel();
        setSuitableLabel(headLabel,bufferedImage,30,30);

        this.playerTyp = new JComboBox<>();
        this.playerTyp.addItem("Humanity");
        this.playerTyp.addItem("Computer");


        setSettingTypPicture();
        setPlayerTyp(playerTyp);

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
    }

    protected JLabel setSuitableLabel(JLabel jLabel,BufferedImage bi,int width,int heigh){
        Image image = bi.getScaledInstance(width, heigh, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(image);
        jLabel.setIcon(icon);
        return jLabel;
    }

    protected void setPlayerTyp(JComboBox<String> playerTyp){
        this.playerTyp = playerTyp;

        this.playerTyp.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {

                checkEnabled();
            }
        });
    }

    protected void setSettingTypPicture(){

        BufferedImage reContPicture = null, bombPicture = null,direPicture = null;
        try {
            direPicture = ImageIO.read(getClass().getResource("/res/images/settings/Direction.png"));
            reContPicture = ImageIO.read(getClass().getResource("/res/images/settings/remote_control.png"));
            bombPicture = ImageIO.read(getClass().getResource("/res/images/settings/bomb.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        settingTypPicture.put(SettingsTyp.DIRECTION_UP, direPicture.getSubimage(0,0,208,208));
        settingTypPicture.put(SettingsTyp.DIRECTION_LEFT,direPicture.getSubimage(208,0,208,208));
        settingTypPicture.put(SettingsTyp.DIRECTION_RIGHT,direPicture.getSubimage(416,0,208,208));
        settingTypPicture.put(SettingsTyp.DIRECTION_DOWN,direPicture.getSubimage(624,0,208,208));
        settingTypPicture.put(SettingsTyp.SETTINGS_BOMB,bombPicture);
        settingTypPicture.put(SettingsTyp.SETTING_REMOTECONTROL,reContPicture);

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

    protected abstract void setUpField(JTextField upField,boolean b);

    protected abstract void setDownField(JTextField downField,boolean b);

    protected abstract void setLeftField(JTextField leftField,boolean b);

    protected abstract void setRightField(JTextField rightField,boolean b);

    protected abstract void setReContField(JTextField reContField,boolean b);

    protected abstract void setBombField(JTextField bombField,boolean b);

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



}
